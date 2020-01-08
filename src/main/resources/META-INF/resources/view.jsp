<%@include file="init.jsp"%>

<%
long rutaId = Long.valueOf((Long) renderRequest
        .getAttribute("rutaId"));
%>


<aui:nav cssClass="nav-tabs">

    <%
        List<Ruta> rutas = RutaLocalServiceUtil.getRutas(scopeGroupId);

            for (int i = 0; i < rutas.size(); i++) {

                Ruta curRuta = rutas.get(i);
                String cssClass = StringPool.BLANK;

                if (curRuta.getRutaId() == rutaId) {
                    cssClass = "active";
                }

 
    %>

    <portlet:renderURL var="viewPageURL">
        <portlet:param name="mvcPath" value="/view.jsp" />
        <portlet:param name="rutaId"
            value="<%=String.valueOf(curRuta.getRutaId())%>" />
    </portlet:renderURL>


    <aui:nav-item cssClass="<%=cssClass%>" href="<%=viewPageURL%>"
        label="<%=HtmlUtil.escape(curRuta.getNombreRuta())%>" />

    <%  
                

            }
    %>

</aui:nav>


<aui:button-row cssClass="parada-buttons">

    <portlet:renderURL var="addParadaURL">
        <portlet:param name="mvcPath" value="/edit_parada.jsp" />
        <portlet:param name="rutaId"
            value="<%=String.valueOf(rutaId)%>" />
    </portlet:renderURL>

    <aui:button onClick="<%=addParadaURL.toString()%>" value="Agregar Parada"></aui:button>

</aui:button-row>

<liferay-ui:search-container total="<%=ParadaLocalServiceUtil.getParadasCount()%>">
<liferay-ui:search-container-results
    results="<%=ParadaLocalServiceUtil.getParadas(scopeGroupId.longValue(),
                    rutaId, searchContainer.getStart(),
                    searchContainer.getEnd())%>" />

<liferay-ui:search-container-row
    className="mx.com.cuervo.rutas.transporte.model.Parada" modelVar="parada">

    <liferay-ui:search-container-column-text property="nombreParada" />

    <liferay-ui:search-container-column-text property="descripcion" />

	<liferay-ui:search-container-column-text property="nombreCarpeta" />
	
	<liferay-ui:search-container-column-text property="nombreArchivo" />
	
	<liferay-ui:search-container-column-jsp path="/parada_actions.jsp" align="right" />
	
</liferay-ui:search-container-row>

<liferay-ui:search-iterator />

</liferay-ui:search-container>