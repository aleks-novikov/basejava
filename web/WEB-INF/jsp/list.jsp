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
            <th>ФИО</th>
            <th>Контакты</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <%--добавление интеграции с Resume--%>
            <jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view"> ${resume.fullName} </a></td>
                <td><%=ContactType.PHONE.toHtml(resume.getContact(ContactType.PHONE))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="Удалить резюме"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt="Редактировать резюме"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <p><b>Добавить новое резюме</b>
        <a href="resume?action=add"><img src="img/add.png" sizes="50" alt="Добавить резюме" align="center"></a>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>