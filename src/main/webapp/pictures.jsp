<%@ page import="com.example.orm.User" %>
<%@ page import="com.example.orm.Picture" %>
<%@ page import="com.example.orm.PictureView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PictureView[] pictures = (PictureView[]) request.getAttribute("pictures");
    User user = (User) request.getAttribute("user");

%>
<div style="margin-top: 20px">
    <div class="row row-cols-3">
        <% if (pictures != null) {%>
        <% for (PictureView picture : pictures) { %>
        <div class="col">
            <div class="card" style="width: 18rem;">
                <img src="<%=picture.getPictureUrl()%>" class="card-img-top" alt="<%=picture.getUserLogin()%>">
                <div class="card-body">
                    <h5 class="card-title"><%=picture.getUserLogin()%>
                    </h5>
                    <% if (user == null) { %>
                    <p class="card-text"><%=picture.getDescription()%>
                    </p>
                    <% } else { %>
                    <form action="EditPicture" method="post">
                        <input name="description" class="form-control" aria-label="Description"
                               value="<%=picture.getDescription()%>"/>
                        <div style="margin-top: 10px">
                            <button type="submit" class="btn btn-primary" name="update">Update</button>
                            <button type="submit" class="btn btn-danger" name="delete">Delete</button>
                        </div>
                        <input type="hidden" name="pictureID" value="<%= picture.getId() %>"/>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>
        <% }%>
        <% }%>
    </div>
</div>
