<%@ page import="ru.topjava.basejava.model.ContactType" %>
<%@ page import="ru.topjava.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <%--создание формы. Введённые данные будут передаваться по методу POST, результат--%>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <%--uuid скрыт, передаём его в запросе в качестве параметра--%>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Характеристика:</h3>
        <c:forEach var="section" items="<%=SectionType.values()%>">
            <c:choose>

                <c:when test="${section.title.equals('Позиция') || section.title.equals('Личные качества')}">
                    <dl>
                        <dt>${section.title}</dt>
                        <dd><input type="text" name="${section.name()}" size="80"
                                   value="${resume.getSection(section)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${section.title.equals('Достижения') || section.title.equals('Квалификация')}">
                    <dl>
                        <dt>${section.title}</dt>
                        <c:set var="data" value="${fn:replace(resume.getSection(section), '[', '')}"/>
                        <c:set var="data" value="${fn:replace(data, ']', '')}"/>
                        <dd><textarea name="${section.name()}" rows="4"
                                      cols="75">${data}</textarea></dd>
                    </dl>
                </c:when>

                <c:when test="${section.title.equals('Опыт работы') || section.title.equals('Образование')}">
                    <dl>
                        <dt>${section.title}</dt>
                        <dd><textarea name="${section.title}" rows="7"
                                      cols="75">${resume.getSection(section)}</textarea></dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <br/>
        <%--при нажатии кнопки Сохранить post-запрос будет отправлен на сервер--%>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>