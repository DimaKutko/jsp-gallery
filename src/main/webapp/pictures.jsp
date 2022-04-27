<%@ page import="com.example.orm.User" %>
<%@ page import="com.example.orm.Picture" %>
<%@ page import="com.example.orm.PictureView" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PictureView[] pictures = (PictureView[]) request.getAttribute("pictures");
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
                    <p class="card-text"><%=picture.getDescription()%>
                    </p>
                </div>
            </div>
        </div>
        <% }%>
        <% }%>
    </div>
</div>
