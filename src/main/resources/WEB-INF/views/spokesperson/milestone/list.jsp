<%@page%>

<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<acme:list>
	<acme:list-column code="spokesperson.milestone.list.label.title" path="title" width="40%"/>
	<acme:list-column code="spokesperson.milestone.list.label.effort" path="effort" width="30%"/>
	<acme:list-column code="spokesperson.milestone.list.label.kind" path="kind" width="30%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
    <acme:button code="spokesperson.milestone.list.button.create" action="/spokesperson/milestone/create?masterId=${masterId}"/>
</jstl:if>