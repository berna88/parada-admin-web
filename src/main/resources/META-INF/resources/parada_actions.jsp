<%@include file="init.jsp"%>

    <%
    String mvcPath = ParamUtil.getString(request, "mvcPath");

    ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

    Parada parada = (Parada)row.getObject(); 
    %>

    <liferay-ui:icon-menu>

            <portlet:renderURL var="editURL">
                <portlet:param name="paradaId"
                    value="<%= String.valueOf(parada.getParadaId()) %>" />
                <portlet:param name="mvcPath" value="/edit_parada.jsp" />
            </portlet:renderURL>

            <liferay-ui:icon image="edit" message="Edit"
                url="<%=editURL.toString() %>" />
      
            <portlet:actionURL name="deleteEntry" var="deleteURL">
                <portlet:param name="paradaId"
                    value="<%= String.valueOf(parada.getParadaId()) %>" />
                <portlet:param name="rutaId"
                    value="<%= String.valueOf(parada.getRutaId()) %>" />
            </portlet:actionURL>

            <liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
      
    </liferay-ui:icon-menu>