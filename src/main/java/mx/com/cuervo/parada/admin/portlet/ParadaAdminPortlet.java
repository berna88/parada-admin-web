package mx.com.cuervo.parada.admin.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import mx.com.cuervo.parada.admin.constants.ParadaAdminPortletKeys;
import mx.com.cuervo.rutas.transporte.model.Parada;
import mx.com.cuervo.rutas.transporte.model.Ruta;
import mx.com.cuervo.rutas.transporte.service.ParadaLocalService;
import mx.com.cuervo.rutas.transporte.service.RutaLocalService;


/**
 * @author Jonathan Cruz Sanchez
 */
@Component(
	    immediate = true,
	    property = {
	            "com.liferay.portlet.display-category=category.hidden",
	            "com.liferay.portlet.scopeable=true",
	            "javax.portlet.display-name=Paradas",
	            "javax.portlet.expiration-cache=0",
	            "javax.portlet.init-param.portlet-title-based-navigation=true",
	            "javax.portlet.init-param.template-path=/",
	            "javax.portlet.init-param.view-template=/view.jsp",
	            "javax.portlet.name=" + ParadaAdminPortletKeys.PARADAADMIN,
	            "javax.portlet.resource-bundle=content.Language",
	            "javax.portlet.security-role-ref=administrator",
	            "javax.portlet.supports.mime-type=text/html",
	            "com.liferay.portlet.add-default-resource=true"
	    },
	    service = Portlet.class
	)
public class ParadaAdminPortlet extends MVCPortlet {
	
	
	
	@SuppressWarnings({ "deprecation" })
	public void addParada(ActionRequest request, ActionResponse response)
            throws PortalException {

        ServiceContext serviceContext = ServiceContextFactory.getInstance(
            Parada.class.getName(), request);

        String nombreParada = ParamUtil.getString(request, "nombreParada");
        String descripcion = ParamUtil.getString(request, "descripcion");
        String horario = ParamUtil.getString(request, "horario");
        String nombreCarpeta = ParamUtil.getString(request, "nombreCarpeta");
        String nombreArchivo = ParamUtil.getString(request, "nombreArchivo");
      
        long rutaId = ParamUtil.getLong(request, "rutaId");
        long paradaId = ParamUtil.getLong(request, "paradaId");

    if (paradaId > 0) {

        try {

            _paradaLocalService.updateParada(
                serviceContext.getUserId(), paradaId, nombreParada,
                descripcion, horario,nombreCarpeta,nombreArchivo, serviceContext);
      
            response.setRenderParameter(
                "rutaId", Long.toString(rutaId));

        }
        catch (Exception e) {
            System.out.println(e);

            PortalUtil.copyRequestParameters(request, response);

            response.setRenderParameter(
                "mvcPath", "/edit_parada.jsp");
        }

    }
    else {

        try {
            _paradaLocalService.addParada(
                serviceContext.getUserId(), rutaId, nombreParada, descripcion,
                horario, nombreCarpeta, nombreArchivo, serviceContext);

            SessionMessages.add(request, "paradaAdded");

            response.setRenderParameter(
                "rutaId", Long.toString(rutaId));

        }
        catch (Exception e) {
            SessionErrors.add(request, e.getClass().getName());

            PortalUtil.copyRequestParameters(request, response);

            response.setRenderParameter(
                "mvcPath", "/edit_parada.jsp");
        }
    }
}
	
	
	@SuppressWarnings("deprecation")
	public void deleteParada(ActionRequest request, ActionResponse response) throws PortalException {
        long paradaId = ParamUtil.getLong(request, "paradaId");
        long rutaId = ParamUtil.getLong(request, "rutaId");

        ServiceContext serviceContext = ServiceContextFactory.getInstance(
            Parada.class.getName(), request);

        try {

            response.setRenderParameter(
                "rutaId", Long.toString(rutaId));

            _paradaLocalService.deleteParada(paradaId, serviceContext);
        }

        catch (Exception e) {
            Logger.getLogger(ParadaAdminPortlet.class.getName()).log(
                Level.SEVERE, null, e);
        }
}
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	        throws IOException, PortletException {

	        try {
	            ServiceContext serviceContext = ServiceContextFactory.getInstance(
	                Ruta.class.getName(), renderRequest);

	            long groupId = serviceContext.getScopeGroupId();

	            long rutaId = ParamUtil.getLong(renderRequest, "rutaId");

	         
	            List<Ruta> rutas = _rutaLocalService.getRutas(
	                groupId);

	            if (rutas.isEmpty()) {
	                Ruta ruta = 
	                		_rutaLocalService.addRuta(serviceContext.getUserId(), 
	                				"Main", 0, 0, serviceContext);
	                
	                rutaId = ruta.getRutaId();
	            }

	            if (rutaId == 0) {
	            	rutaId = rutas.get(0).getRutaId();
	            }

	            renderRequest.setAttribute("rutaId", rutaId);
	        }
	        catch (Exception e) {
	           System.out.println("Exception");
	        }

	        super.render(renderRequest, renderResponse);
	}


	@Reference(unbind = "-")
	private RutaLocalService _rutaLocalService;
	@Reference(unbind = "-")
	private ParadaLocalService _paradaLocalService;
}