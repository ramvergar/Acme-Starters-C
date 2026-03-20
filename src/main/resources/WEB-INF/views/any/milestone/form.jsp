<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.form.label.title" path="title"/>
	<acme:form-textbox code="spokesperson.milestone.form.label.achievements" path="achievements"/>
	<acme:form-textbox code="spokesperson.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="spokesperson.milestone.form.label.kind" path="kind" choices="${kinds}"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="spokesperson.milestone.form.button.create" action="/spokesperson/milestone/create?masterId=${masterId}"/>
		</jstl:when>

		<jstl:when test="${_command == 'update'}">
			<acme:submit code="spokesperson.milestone.form.button.update" action="/spokesperson/milestone/update"/>
		</jstl:when>

		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="spokesperson.milestone.form.button.delete" action="/spokesperson/milestone/delete"/>
		</jstl:when>

		<jstl:when test="${_command == 'show' && draftMode == true}">
			<acme:button code="spokesperson.milestone.form.button.update-link" action="/spokesperson/milestone/update?id=${id}"/>
			<acme:button code="spokesperson.milestone.form.button.delete-link" action="/spokesperson/milestone/delete?id=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>