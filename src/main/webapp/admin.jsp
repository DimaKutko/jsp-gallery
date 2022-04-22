<%@ page import="step.example.orm.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String fromServlet = (String) request.getAttribute("fromServlet");
    User user = (User) request.getAttribute("user");
%>
<div>
    <% if (user != null) { %>
    <form action="" method="post" enctype="multipart/form-data">
        <label>Picture <input type="file" name="picture"></label>
        <br/>
        <label>Description<input type="text" name="description"></label>
        <br/>
        <button>Upload</button>
    </form>
    <% } else { %>
    <pre>Only authorized users can add pictures</pre>
    <% } %>
</div>
