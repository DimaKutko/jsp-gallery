<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>JSP - Gallery</title>
</head>
<body>
<div class="container">
    <jsp:include page="auth.jsp"/>
    <jsp:include page="admin.jsp"/>

    <%= request.getAttribute("rnd1")%><br/>
    <%= request.getAttribute("rnd2")%><br/>
    <%= request.getAttribute("rnd3")%><br/>
</div>
</body>
</html>
