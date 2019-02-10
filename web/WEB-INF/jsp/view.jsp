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
    <p>
        <c:forEach var="contact" items="${resume.contacts}">
            <jsp:useBean id="contact"
                         type="java.util.Map.Entry<ru.topjava.basejava.model.ContactType, java.lang.String>"/>
            ${contact.key.toHtml(contact.value)} <br/>
        </c:forEach>
    <p>
</section>

<section>
    <c:forEach var="section" items="${resume.sections}">
    <jsp:useBean id="section"
                 type="java.util.Map.Entry<ru.topjava.basejava.model.SectionType, java.lang.String>"/>
    <h3>${section.key.title}</h3>
    <c:choose>
    <c:when test="${section.key.title.equals('Позиция') || section.key.title.equals('Личные качества')}">
    <p>${section.value}
        </c:when>

        <c:when test="${section.key.title.equals('Достижения') || section.key.title.equals('Квалификация')}">
            <c:set var="data" value="${fn:replace(section.value, '[', '')}"/>
            <c:set var="data" value="${fn:replace(data, ']', '')}"/>
    <p>${data}
        </c:when>

            <%--<c:when test="${section.title.equals('Опыт работы') || section.title.equals('Образование')}">
                <dl>
                    <dt>${section.title}</dt>
                    <dd><textarea name="${section.title}" rows="4" cols="75">${resume.getSection(section)}</textarea>
                    </dd>
                </dl>
            </c:when>--%>

        </c:choose>
        </c:forEach>
</section>
<br/>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>