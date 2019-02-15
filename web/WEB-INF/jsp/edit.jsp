<%@ page import="ru.topjava.basejava.model.ContactType" %>
<%@ page import="ru.topjava.basejava.model.ListSection" %>
<%@ page import="ru.topjava.basejava.model.OrganizationSection" %>
<%@ page import="ru.topjava.basejava.model.SectionType" %>
<%@ page import="ru.topjava.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume" scope="request"/>

    <title>Резюме ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<%--создание формы. Введённые данные будут передаваться по методу POST, результат--%>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <%--uuid скрыт, передаём его в запросе в качестве параметра--%>
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Имя:</dt>
        <dd><input type="text" name="fullName" size="45" value="${resume.fullName}"></dd>
    </dl>

    <section>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="45" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
    </section>

    <section>
        <h3>Характеристика:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.topjava.basejava.model.AbstractSection"/>

            <h3> ${type.title} </h3>
            <c:choose>
                <c:when test="${type.title.equals('Позиция')}">
                    <input type="text" name="${type}" size="60" value="${section}">
                </c:when>

                <c:when test="${type.title.equals('Личные качества')}">
                    <textarea name="${type}">${section}</textarea>
                </c:when>

                <c:when test="${type.title.equals('Достижения') || type.title.equals('Квалификация')}">
                    <textarea name="${type}"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>

                <c:when test="${type.title.equals('Опыт работы') || type.title.equals('Образование')}">

                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganisations()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><input type="text" name="${type}" size="45" value="${org.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Ссылка:</dt>
                            <dd><input type="text" name="${type}" size="45" value="${org.homePage.url}"></dd>
                        </dl>

                        <c:forEach var="position" items="${org.positions}">
                            <jsp:useBean id="position" type="ru.topjava.basejava.model.Organization.Position"/>

                            <dl>
                                <dt>Начальная дата:</dt>
                                <dd><input type="text" name="${type}${counter.index}startdate" size="6"
                                           value="<%=DateUtil.format(position.getStartDate())%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="${type}${counter.index}enddate" size="6"
                                           value="<%=DateUtil.format(position.getEndDate())%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>

                            <dt>Позиция</dt>
                            <dd><input type="text" name="${type}${counter.index}title" size="45" value="${position.title}"></dd>
                            <dl>
                                <dt>Описание</dt>
                                <p><textarea name="${type}${counter.index}description">${position.description}</textarea></p>
                            </dl>
                        </c:forEach>
                        <hr/>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </section>
    <br/>
    <%--при нажатии кнопки Сохранить post-запрос будет отправлен на сервер--%>
    <button type="submit">Сохранить</button>
    <button type="button" onclick="window.history.back()">Назад</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>