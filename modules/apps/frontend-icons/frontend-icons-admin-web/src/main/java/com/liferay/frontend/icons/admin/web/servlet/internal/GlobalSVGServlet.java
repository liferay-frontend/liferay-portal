package com.liferay.frontend.icons.admin.web.servlet.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.frontend.icons.admin.web.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.frontend.icons.admin.web.internal.constants.IconAdminKeys;
import com.liferay.portal.kernel.util.ContentTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
    immediate = true,
    property = {
        "osgi.http.whiteboard.context.path=/",
        "osgi.http.whiteboard.servlet.pattern=/" + IconAdminKeys.GLOBAL_SPRITE_MAP
    },
    service = Servlet.class
)
public class GlobalSVGServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(ContentTypes.IMAGE_SVG_XML);
			response.setStatus(HttpServletResponse.SC_OK);
			
            response.getWriter().write(_iconResourceHelper.getGlobalSpriteContent());
        }
        catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        }
    }

	@Reference
	private IconResourceHelper _iconResourceHelper;

}