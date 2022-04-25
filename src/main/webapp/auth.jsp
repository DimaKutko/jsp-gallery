<%@ page import="com.example.orm.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
%>

<div class="card" style="margin-top: 20px;">
    <% if (user == null) { %>
    <%-- Not authed user --%>
    <div class="card-header">
        Authorization
    </div>
    <div class="card-body">
        <form method="post">
            <div class="row g-3">
                <div class="col mb-3">
                    <input name="userLogin" type="text"
                           class="form-control <% if(error != null) {%> <%= "is-invalid" %> <% }%>" placeholder="Login"
                           aria-label="Login"
                           style="margin-bottom: 0"
                           required>
                    <div class="invalid-feedback"><%=error%>
                    </div>
                </div>
                <div class="col">
                    <input name="userPass" type="password"
                           style="margin-bottom: 0"
                           class="form-control <% if(error != null) {%> <%= "is-invalid" %> <% }%>"
                           placeholder="Password"
                           aria-label="Password" required>
                </div>
                <div class="col">
                    <button class="btn btn-primary">Log In</button>
                </div>
            </div>
        </form>
    </div>

    <% } else { %>
    <%-- Authed user --%>
    <div class="card-header">
        Hello <%=user.getLogin()%>
    </div>
    <div class="card-body">
        <form method="post">
            <button name="logout" class="btn btn-secondary">Log Out</button>
        </form>
    </div>
    <% } %>
</div>


