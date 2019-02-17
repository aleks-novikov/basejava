<%@ page import="javax.swing.*" %>
<%@ page import="java.awt.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<jsp:useBean id="resume" type="ru.topjava.basejava.model.Resume" scope="request"/>
<jsp:useBean id="message" type="java.lang.String" scope="request"/>
<jsp:useBean id="storage" type="ru.topjava.basejava.storage.Storage" scope="request"/>
<% int result = JOptionPane.showConfirmDialog(new JOptionPane(new FlowLayout()),
        message, "Resume removing", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == 0) {
        storage.delete(resume.getUuid());
    }
    response.sendRedirect("resume");
%>
</body>
</html>