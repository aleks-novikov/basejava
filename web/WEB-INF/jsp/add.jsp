<%@ page import="ru.topjava.basejava.model.ContactType" %>
<%@ page import="ru.topjava.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Создание резюме</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <dl>
        <dt>ФИО<span class="red">*</span></dt>
        <dd><input class="inputLine" type="text" name="fullName" required></dd>
    </dl>

    <section>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <c:choose>
                    <c:when test="${type.name() == 'EMAIL'}">
                        <dt>${type.title}<span class="red">*</span></dt>
                        <dd><input class="inputLine" type="text" name="${type.name()}" required></dd>
                    </c:when>
                    <c:otherwise>
                        <dt>${type.title}</dt>
                        <dd><input class="inputLine" name="${type.name()}" type="text"></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
    </section>

    <section>
        <h3>Характеристика:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${!type.title.equals('Позиция')}">
                    <h3>${type.title}</h3>
                </c:when>
                <c:otherwise>
                    <h3>${type.title}<span class="red">*</span></h3>
                    <input class="inputPosition" type="text" name="${type}" required></c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${type.title.equals('Личные качества')}">
                    <textarea name="${type}"></textarea>
                </c:when>

                <c:when test="${type.title.equals('Достижения') || type.title.equals('Квалификация')}">
                    <textarea name="${type}"></textarea>
                </c:when>

                <c:when test="${type.title.equals('Опыт работы') || type.title.equals('Образование')}">
                    <dl>
                        <dt>Название организации:</dt>
                        <dd><input class="inputLine" type="text" name="${type}"></dd>
                    </dl>
                    <dl>
                        <dt>Ссылка:</dt>
                        <dd><input class="inputLine" type="text" name="${type}url"></dd>
                    </dl>

                    <dl>
                        <dt>Начальная дата:</dt>
                        <dd><input class="inputDateLine" type="text" name="${type}startDate" placeholder="MM/yyyy">
                        </dd>
                    </dl>
                    <dl>
                        <dt>Конечная дата:</dt>
                        <dd><input class="inputDateLine" type="text" name="${type}endDate" placeholder="MM/yyyy">
                        </dd>
                    </dl>

                    <dt>Позиция</dt>
                    <dd><input class="inputLine" type="text" name="${type}title"></dd>
                    <dl>
                        <dt>Описание</dt>
                        <p><textarea
                                name="${type}description"></textarea>
                        </p>
                    </dl>
                    <br/>
                </c:when>
            </c:choose>
        </c:forEach>
    </section>
    <br/>
    <button type="submit">Сохранить</button>
    <button type="button" onclick="window.history.back()">Назад</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>