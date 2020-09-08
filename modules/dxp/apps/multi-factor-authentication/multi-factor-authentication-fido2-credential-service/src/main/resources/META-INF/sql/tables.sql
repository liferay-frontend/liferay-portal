create table MFAFIDO2CredentialEntry (
	mvccVersion LONG default 0 not null,
	mfaFIDO2CredentialEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	credentialId VARCHAR(75) null,
	credentialType INTEGER,
	publicKeyCose VARCHAR(75) null,
	signatureCount LONG,
	failedAttempts INTEGER,
	lastFailDate DATE null,
	lastFailIP VARCHAR(75) null,
	lastSuccessDate DATE null,
	lastSuccessIP VARCHAR(75) null
);