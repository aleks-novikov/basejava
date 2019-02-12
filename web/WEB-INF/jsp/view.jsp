<%@ page import="ru.topjava.basejava.model.ListSection" %>
<%@ page import="ru.topjava.basejava.model.OrganizationSection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">

    <%--scope="request" означает, что resume будет браться из запроса--%>
    <jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"></a></h2>
    <c:forEach var="contact" items="${resume.contacts}">
        <jsp:useBean id="contact"
                     type="java.util.Map.Entry<ru.topjava.basejava.model.ContactType, java.lang.String>"/>
        ${contact.key.toHtml(contact.value)} <br/>
    </c:forEach>
</section>

<section>
    <c:forEach var="section" items="${resume.sections}">
        <jsp:useBean id="section"
                     type="java.util.Map.Entry<ru.topjava.basejava.model.SectionType, java.lang.String>"/>
        <h3>${section.key.title}</h3>
        <c:choose>
            <c:when test="${section.key.title.equals('Позиция') || section.key.title.equals('Личные качества')}">
                <p>${section.value}</p>
            </c:when>

            <c:when test="${section.key.title.equals('Достижения') || section.key.title.equals('Квалификация')}">
                <%
                    List<String> sectionData = ((ListSection) resume.getSection(section.getKey())).getItems();
                    request.setAttribute("sectionData", sectionData);
                %>
                <ul>
                    <c:forEach var="item" items="${sectionData}">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </c:when>

            <c:when test="${section.key.title.equals('Опыт работы') || section.key.title.equals('Образование')}">
                <%
                    OrganizationSection sectionData = (OrganizationSection) resume.getSection(section.getKey());
                    request.setAttribute("sectionData", sectionData.getOrganisations());
                %>

                <c:forEach var="item" items="${sectionData}">
                    <c:set var="organization" value="${item.homePage.name}"/>
                    <c:set var="url" value="${item.homePage.url}"/>
                    <p><a href="${url}">${organization}</a></p>

                    <c:forEach var="data" items="${item.positions}">
                        <c:set var="startDate" value="${data.startDate}"/>
                        <c:set var="endDate" value="${data.endDate}"/>
                        <c:set var="title" value="${data.title}"/>
                        <c:set var="description" value="${data.description}"/>
                        ${startDate} - ${endDate}, <b>${title}</b>
                        <p>${description}</p>
                    </c:forEach>
                    <br/>
                </c:forEach>

            </c:when>
        </c:choose>
    </c:forEach>
</section>
<br/>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>