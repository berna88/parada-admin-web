package mx.com.cuervo.parada.admin.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import mx.com.cuervo.parada.admin.constants.ParadaAdminPortletKeys;

@Component(
	    immediate = true,
	    property = {
	        "panel.app.order:Integer=300",
	        "panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT
	    },
	    service = PanelApp.class
	)
public class ParadaAdminPanelApp extends BasePanelApp{

	@Override
    public String getPortletId() {
        return ParadaAdminPortletKeys.PARADAADMIN;
    }

    @Override
    @Reference(
        target = "(javax.portlet.name=" + ParadaAdminPortletKeys.PARADAADMIN + ")",
        unbind = "-"
    )
    public void setPortlet(Portlet portlet) {
        super.setPortlet(portlet);
    }


}
