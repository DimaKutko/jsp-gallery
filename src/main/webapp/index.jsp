<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>JSP - Gallery</title>
</head>
<body>
<div class="container">
    <% if (message != null) {%>
    <div class="alert alert-success" role="alert" style="margin-top: 20px">
        <%=message%>
    </div>
    <% }%>
    <% if (error != null) {%>
    <div class="alert alert-danger" role="alert" style="margin-top: 20px">
        <%=error%>
    </div>
    <% }%>
    <jsp:include page="auth.jsp"/>
    <jsp:include page="admin.jsp"/>
    <jsp:include page="pictures.jsp"/>
</div>
</body>
</html>
