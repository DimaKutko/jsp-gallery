<%@ page import="step.example.orm.AuthData" %>
<%@ page import="step.example.orm.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
%>

<div class="container">
    <% if (user == null) { %>
    <%-- Not authed user --%>
    <form method="post">
        Authorization <br/>
        <label>Login <input name="userLogin" type="password" class="form-control"
                            aria-describedby="passwordHelpInline"/></label>
        <label>Password <input name="userPass" type="password" class="form-control"
                               aria-describedby="passwordHelpInline"/></label>
        <button>Log In</button>
    </form>
    <% if (error != null) { %>
    <b style="color: maroon"><%=error%>
    </b>
    <% }%>
    <% } else { %>
    <%-- Authed user --%>
    Hello <%=user.getLogin()%>
    <form method="post" style="display: inline">
        <button name="logout">Log Out</button>
    </form>
    <% } %>
</div>


