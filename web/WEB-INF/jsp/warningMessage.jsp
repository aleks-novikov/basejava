<%@ page import="javax.swing.*" %>
<%@ page import="java.awt.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<jsp:useBean id="message" type="java.lang.String" scope="request"/>
<% JOptionPane.showConfirmDialog(new JOptionPane(new FlowLayout()), message, "Warning!",
        JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
    response.sendRedirect("resume");
%>
</body>
</html>