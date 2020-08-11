/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.fido2.web.internal.checker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.service.MFAFIDO2CredentialEntryLocalService;
import com.liferay.multi.factor.authentication.fido2.web.internal.configuration.MFAFIDO2Configuration;
import com.liferay.multi.factor.authentication.fido2.web.internal.constants.MFAFIDO2WebKeys;
import com.liferay.multi.factor.authentication.fido2.web.internal.credential.repository.MFAFIDO2CredentialRepository;
import com.liferay.multi.factor.authentication.fido2.web.internal.util.ConvertUtil;
import com.liferay.multi.factor.authentication.spi.checker.browser.BrowserMFAChecker;
import com.liferay.multi.factor.authentication.spi.checker.setup.SetupMFAChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.exception.AssertionFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.fido2.web.internal.configuration.MFAFIDO2Configuration.scoped",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class FIDO2BrowserSetupMFAChecker
	implements BrowserMFAChecker, SetupMFAChecker {

	@Override
	public void includeBrowserVerification(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		String assertionRequest = _startAuthentication(userId);

		httpServletRequest.setAttribute("assertionReqest", assertionRequest);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/mfa_fido2_checker/verify_browser.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		httpSession.setAttribute("assertionRequest", assertionRequest);
	}

	@Override
	public void includeSetup(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByUserId(userId);

		if (mfaFIDO2CredentialEntry != null) {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/mfa_fido2_checker/setup_completed.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);

			return;
		}

		String pkccOptions = _startRegistration(userId);

		httpServletRequest.setAttribute("pkccOptions", pkccOptions);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/mfa_fido2_checker/setup.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		httpSession.setAttribute("pkccOptions", pkccOptions);
	}

	@Override
	public boolean isAvailable(long userId) {
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByUserId(userId);

		if (mfaFIDO2CredentialEntry != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isBrowserVerified(
		HttpServletRequest httpServletRequest, long userId) {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession(false);

		if (isVerified(httpSession, userId)) {
			return true;
		}

		return false;
	}

	@Override
	public void removeExistingSetup(long userId) {
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.
				fetchMFAFIDO2CredentialEntryByUserId(userId);

		if (mfaFIDO2CredentialEntry != null) {
			_mfaFIDO2CredentialEntryLocalService.deleteMFAFIDO2CredentialEntry(
				mfaFIDO2CredentialEntry);
		}
	}

	@Override
	public boolean setUp(HttpServletRequest httpServletRequest, long userId) {
		try {
			RegistrationResult registrationResult = _finishRegistration(
				httpServletRequest);

			PublicKeyCredentialDescriptor publicKeyCredentialDescriptor =
				registrationResult.getKeyId();

			ByteArray credentialIdByteArray =
				publicKeyCredentialDescriptor.getId();

			String credentialId = credentialIdByteArray.getBase64();

			ByteArray publicKeyCoseByteArray =
				registrationResult.getPublicKeyCose();

			String publicKeyCose = publicKeyCoseByteArray.getBase64();

			_mfaFIDO2CredentialEntryLocalService.addMFAFIDO2CredentialEntry(
				userId, credentialId, 0, publicKeyCose);

			return true;
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to setup FIDO2 for user ", userId, ": ",
					exception.getMessage()),
				exception);
		}

		return false;
	}

	@Override
	public boolean verifyBrowserRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested FIDO2 verification for nonexistent user " +
						userId);
			}

			return false;
		}

		if (!isAvailable(user.getUserId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested FIDO2 verfication for user " + userId +
						" with incomplete configuration");
			}

			return false;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String userIP = originalHttpServletRequest.getRemoteAddr();

		try {
			AssertionResult assertionResult = _finishAuthentication(
				httpServletRequest);

			_mfaFIDO2CredentialEntryLocalService.updateAttempts(
				userId, userIP, assertionResult.getSignatureCount());

			return true;
		}
		catch (Exception exception) {
			_mfaFIDO2CredentialEntryLocalService.updateAttempts(
				userId, userIP, 0);

			return false;
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_mfaFIDO2Configuration = ConfigurableUtil.createConfigurable(
			MFAFIDO2Configuration.class, properties);

		_jsonMapper = new ObjectMapper();
		_jsonMapper = _jsonMapper.configure(
			SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		_jsonMapper = _jsonMapper.setSerializationInclusion(
			JsonInclude.Include.NON_ABSENT);
		_jsonMapper = _jsonMapper.registerModule(new Jdk8Module());

		RelyingPartyIdentity relyingPartyIdentity =
			RelyingPartyIdentity.builder(
			).id(
				_mfaFIDO2Configuration.rpId()
			).name(
				_mfaFIDO2Configuration.rpName()
			).build();

		MFAFIDO2CredentialRepository mfaFIDO2CredentialRepository =
			new MFAFIDO2CredentialRepository(
				_mfaFIDO2CredentialEntryLocalService, _userLocalService);

		Stream<String> originsStream = Arrays.stream(
			_mfaFIDO2Configuration.origins());

		_relyingParty = RelyingParty.builder(
		).identity(
			relyingPartyIdentity
		).credentialRepository(
			mfaFIDO2CredentialRepository
		).origins(
			originsStream.collect(Collectors.toSet())
		).allowOriginPort(
			_mfaFIDO2Configuration.allowOriginPort()
		).allowOriginSubdomain(
			_mfaFIDO2Configuration.allowOriginSubdomain()
		).build();

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
				Arrays.asList(
					PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

			sessionPhishingProtectedAttributes.add(
				MFAFIDO2WebKeys.MFA_FIDO2_VALIDATED_AT_TIME);
			sessionPhishingProtectedAttributes.add(
				MFAFIDO2WebKeys.MFA_FIDO2_VALIDATED_USER_ID);

			PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
				sessionPhishingProtectedAttributes.toArray(new String[0]);
		}

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				BrowserMFAChecker.class.getName(),
				SetupMFAChecker.class.getName()
			},
			this, new HashMapDictionary<>(properties));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
				Arrays.asList(
					PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

			sessionPhishingProtectedAttributes.remove(
				MFAFIDO2WebKeys.MFA_FIDO2_VALIDATED_AT_TIME);
			sessionPhishingProtectedAttributes.remove(
				MFAFIDO2WebKeys.MFA_FIDO2_VALIDATED_USER_ID);

			PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
				sessionPhishingProtectedAttributes.toArray(new String[0]);
		}
	}

	protected boolean isVerified(HttpSession httpSession, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested FIDO2 verification for nonexistent user " +
						userId);
			}

			return false;
		}

		if (httpSession == null) {
			return false;
		}

		Object mfaFIDO2ValidatedUserId = httpSession.getAttribute(
			MFAFIDO2WebKeys.MFA_FIDO2_VALIDATED_USER_ID);

		if (mfaFIDO2ValidatedUserId == null) {
			return false;
		}

		if (!Objects.equals(mfaFIDO2ValidatedUserId, userId)) {
			return false;
		}

		return true;
	}

	private AssertionResult _finishAuthentication(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String responseJSON = ParamUtil.getString(
			httpServletRequest, "responseJSON");

		TypeReference
			<PublicKeyCredential
				<AuthenticatorAssertionResponse,
				 ClientAssertionExtensionOutputs>> typeReference =
					new TypeReference
						<PublicKeyCredential
							<AuthenticatorAssertionResponse,
							 ClientAssertionExtensionOutputs>>() {
					};

		PublicKeyCredential
			<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs>
				publicKeyCredential = _jsonMapper.readValue(
					responseJSON, typeReference);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		AssertionRequest assertionRequest = _jsonMapper.readValue(
			(String)httpSession.getAttribute("assertionRequest"),
			AssertionRequest.class);

		FinishAssertionOptions finishAssertionOptions =
			FinishAssertionOptions.builder(
			).request(
				assertionRequest
			).response(
				publicKeyCredential
			).build();

		AssertionResult assertionResult = _relyingParty.finishAssertion(
			finishAssertionOptions);

		if (!assertionResult.isSuccess()) {
			throw new AssertionFailedException("Assertion failed");
		}

		return assertionResult;
	}

	private RegistrationResult _finishRegistration(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String responseJSON = ParamUtil.getString(
			httpServletRequest, "responseJSON");

		TypeReference
			<PublicKeyCredential
				<AuthenticatorAttestationResponse,
				 ClientRegistrationExtensionOutputs>> typeReference =
					new TypeReference
						<PublicKeyCredential
							<AuthenticatorAttestationResponse,
							 ClientRegistrationExtensionOutputs>>() {
					};

		PublicKeyCredential
			<AuthenticatorAttestationResponse,
			 ClientRegistrationExtensionOutputs> publicKeyCredential =
				_jsonMapper.readValue(responseJSON, typeReference);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		PublicKeyCredentialCreationOptions pkccOptions = _jsonMapper.readValue(
			(String)httpSession.getAttribute("pkccOptions"),
			PublicKeyCredentialCreationOptions.class);

		FinishRegistrationOptions finishRegistrationOptions =
			FinishRegistrationOptions.builder(
			).request(
				pkccOptions
			).response(
				publicKeyCredential
			).build();

		return _relyingParty.finishRegistration(finishRegistrationOptions);
	}

	private String _startAuthentication(long userId) throws Exception {
		User user = _userLocalService.fetchUserById(userId);

		StartAssertionOptions startAssertionOptions =
			StartAssertionOptions.builder(
			).username(
				Optional.of(user.getScreenName())
			).build();

		AssertionRequest assertionRequest = _relyingParty.startAssertion(
			startAssertionOptions);

		return _jsonMapper.writeValueAsString(assertionRequest);
	}

	private String _startRegistration(long userId) throws Exception {
		User user = _userLocalService.fetchUserById(userId);

		ByteArray userHandle = ConvertUtil.longToByteArray(userId);

		UserIdentity uid = UserIdentity.builder(
		).name(
			user.getScreenName()
		).displayName(
			user.getFullName()
		).id(
			userHandle
		).build();

		StartRegistrationOptions startRegistrationOptions =
			StartRegistrationOptions.builder(
			).user(
				uid
			).build();

		PublicKeyCredentialCreationOptions pkccOptions =
			_relyingParty.startRegistration(startRegistrationOptions);

		return _jsonMapper.writeValueAsString(pkccOptions);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FIDO2BrowserSetupMFAChecker.class);

	private ObjectMapper _jsonMapper;
	private MFAFIDO2Configuration _mfaFIDO2Configuration;

	@Reference
	private MFAFIDO2CredentialEntryLocalService
		_mfaFIDO2CredentialEntryLocalService;

	@Reference
	private Portal _portal;

	private RelyingParty _relyingParty;
	private ServiceRegistration<?> _serviceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.fido2.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}