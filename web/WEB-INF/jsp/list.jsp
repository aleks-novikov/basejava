<%@ page import="ru.topjava.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Skype</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <%--добавление интеграции с Resume--%>
            <jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}"> ${resume.fullName} </a></td>
                <td> ${resume.getContact(ContactType.SKYPE)}</td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>