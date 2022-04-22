<%@ page import="step.example.orm.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String fromServlet = (String) request.getAttribute("fromServlet");
    User user = (User) request.getAttribute("user");
%>
<div style="margin-top: 20px">
    <% if (user == null) { %>
    <%-- Not authed user --%>
    <div class="alert alert-primary" role="alert">
        Only authorized users can add pictures
    </div>
    <% } else { %>
    <%-- Authed user --%>
    <div class="card" style="margin-top: 20px;">
        <div class="card-header">
            Upload your new picture
        </div>
        <div class="card-body">
            <form action="" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label class="form-label">New picture</label>
                    <input name="picture" type="file" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <input name="description" type="text" class="form-control" aria-label="Description">
                </div>
                <button type="submit" class="btn btn-primary">Upload</button>
            </form>
        </div>
    </div>
    <% } %>
</div>
