<%@ page import="ru.topjava.basejava.model.ListSection" %>
<%@ page import="ru.topjava.basejava.model.OrganizationSection" %>
<%@ page import="ru.topjava.basejava.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<c:choose>
    <c:when test="${!resume.fullName.equals('Новиков Александр')}">
        <h2>${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"
                                                                                     alt="Редактировать резюме"></a>
        </h2>
    </c:when>
    <c:otherwise><h2>${resume.fullName} </h2></c:otherwise>
</c:choose>

<section>
    <c:forEach var="contact" items="${resume.contacts}">
        <jsp:useBean id="contact"
                     type="java.util.Map.Entry<ru.topjava.basejava.model.ContactType, java.lang.String>"/>

        <p style="margin: 5px 0">
            <c:if test="${contact.key.name() == 'PHONE'}"><img class="icon" src="img/phone.png" alt="Тел.:"></c:if>
            <c:if test="${contact.key.name() == 'HOMEPAGE'}"><a href="${contact.key.toHtml(contact.value)}">
                <img class="icon" src="img/home.png" alt="Домашняя стр.:"></a>
            </c:if>
                ${contact.key.toHtml(contact.value)}
        </p>
    </c:forEach>
</section>
<hr align="left" width="250" size="1"/>

<section>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.topjava.basejava.model.SectionType, ru.topjava.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key.title}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.topjava.basejava.model.AbstractSection"/>

        <c:choose>
            <c:when test="${type.equals('Позиция')}">
                <h2 style="font-size: 22px"> ${type} — <%=((TextSection) section).getText()%>
                </h2>
            </c:when>
            <c:when test="${type.equals('Личные качества')}">
                <h3>${type}</h3>
                <%=((TextSection) section).getText()%>
            </c:when>

            <c:when test="${type.equals('Достижения') || type.equals('Квалификация')}">
                <h3>${type}</h3>
                <ul>
                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </c:when>

            <c:when test="${type.equals('Опыт работы') || type.equals('Образование')}">
                <h3>${type}</h3>
                <c:forEach var="item" items="<%=((OrganizationSection) section).getOrganisations() %>">
                    <c:if test="${!empty item.homePage.name}">
                        <c:choose>
                            <c:when test="${empty item.homePage.url}">
                                <h3>${item.homePage.name}</h3>
                            </c:when>
                            <c:otherwise>
                                <h3><a href="${item.homePage.url}">${item.homePage.name}</a></h3>
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="data" items="${item.positions}">
                            <c:set var="startDate" value="${data.startDate}"/>
                            <c:set var="endDate" value="${data.endDate}"/>
                            <c:set var="title" value="${data.title}"/>
                            <c:set var="description" value="${data.description}"/>
                            <c:if test="${!empty startDate}">
                                ${startDate}— ${endDate}
                                <b>${title}</b>
                                <p>${description}</p>
                                <br/>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<br/>
<button type="button" onclick="window.history.back()">Назад</button>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>