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
<%--создание формы. Введённые данные будут передаваться по методу POST--%>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <%--uuid скрыт, передаём его в запросе в качестве параметра--%>
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>ФИО<span class="red">*</span></dt>
        <dd><input class="inputLine" type="text" name="fullName" value="${resume.fullName}" required></dd>
    </dl>

    <section>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <c:choose>
                    <c:when test="${type.name() == 'EMAIL'}">
                        <dt>${type.title}<span class="red">*</span></dt>
                        <dd><input class="inputLine" type="text" name="${type.name()}" value="${resume.getContact(type)}" required></dd>
                    </c:when>
                    <c:otherwise>
                        <dt>${type.title}</dt>
                        <dd><input class="inputLine" type="text" name="${type.name()}" value="${resume.getContact(type)}"></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
    </section>

    <section>
        <h3>Характеристика:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>

            <c:choose>
                <c:when test="${!type.title.equals('Позиция')}">
                    <h3>${type.title}</h3>
                </c:when>
                <c:otherwise>
                    <h3>${type.title}<span class="red">*</span></h3>
                    <input class="inputPosition" type="text" name="${type}" value="${section}" required></c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${type.title.equals('Личные качества')}">
                    <textarea name="${type}">${section}</textarea>
                </c:when>

                <c:when test="${type.title.equals('Достижения') || type.title.equals('Квалификация')}">
                    <c:choose>
                        <c:when test="${!empty section}">
                            <jsp:useBean id="section" type="ru.topjava.basejava.model.AbstractSection"/>
                            <textarea
                                    name="${type}"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea name="${type}"></textarea>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:when test="${type.title.equals('Опыт работы') || type.title.equals('Образование')}">
                    <c:if test="${!empty section}">
                        <c:set var="organizationSection" value="${resume.getSection(type)}"/>
                        <jsp:useBean id="organizationSection" type="ru.topjava.basejava.model.AbstractSection"/>

                        <c:forEach var="org" items="<%=((OrganizationSection) organizationSection).getOrganisations()%>"
                                   varStatus="counter">
                            <dl>
                                <dt>Название организации:</dt>
                                <dd><input class="inputLine" type="text" name="${type}" value="${org.homePage.name}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Ссылка:</dt>
                                <dd><input class="inputLine" type="text" name="${type}url" value="${org.homePage.url}"></dd>
                            </dl>

                            <c:forEach var="position" items="${org.positions}">
                                <jsp:useBean id="position" type="ru.topjava.basejava.model.Organization.Position"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd><input class="inputDateLine" type="text" name="${type}${counter.index}startDate"
                                               value="<%=DateUtil.format(position.getStartDate())%>"
                                               placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd><input class="inputDateLine" type="text" name="${type}${counter.index}endDate"
                                               value="<%=DateUtil.format(position.getEndDate())%>"
                                               placeholder="MM/yyyy">
                                    </dd>
                                </dl>

                                <dt>Позиция</dt>
                                <dd><input class="inputLine" type="text" name="${type}${counter.index}title"
                                           value="${position.title}"></dd>
                                <dl>
                                    <dt>Описание</dt>
                                    <p><textarea name="${type}${counter.index}description">${position.description}</textarea>
                                    </p>
                                </dl>
                            </c:forEach>
                            <br/>
                        </c:forEach>
                    </c:if>
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