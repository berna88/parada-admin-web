<%@include file="init.jsp" %>

<% 

long paradaId = ParamUtil.getLong(renderRequest, "paradaId");

Parada parada = null;
if (paradaId > 0) {
  parada = ParadaLocalServiceUtil.getParada(paradaId);
}

long rutaId = ParamUtil.getLong(renderRequest, "rutaId");

%>

<portlet:renderURL var="viewURL">

<portlet:param name="mvcPath" value="/view.jsp"></portlet:param>

</portlet:renderURL>

<portlet:actionURL name="addParada" var="addParadaURL"></portlet:actionURL>

<aui:form action="<%= addParadaURL %>" name="<portlet:namespace />fm">

<aui:model-context bean="<%= parada %>" model="<%= Parada.class %>" />

    <aui:fieldset>

        <aui:input name="nombreParada" />
        <aui:input name="descripcion" />
        <aui:input name="nombreCarpeta" />
        <aui:input name="nombreArchivo" />
        <aui:input name="paradaId" type="hidden" />
        <aui:input name="rutaId" type="hidden" value='<%= parada == null ? rutaId : parada.getRutaId() %>'/>

    </aui:fieldset>

    <aui:button-row>

        <aui:button type="submit"></aui:button>
        <aui:button type="cancel" onClick="<%= viewURL.toString() %>"></aui:button>

    </aui:button-row>
</aui:form>