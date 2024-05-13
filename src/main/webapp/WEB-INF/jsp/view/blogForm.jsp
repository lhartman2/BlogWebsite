<%--
  Created by IntelliJ IDEA.
  User: lhartman2
  Date: 4/17/2024
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a New Blog</title>
</head>
<body>
    <a href="<c:url value='/login'>  <c:param name='logout'/></c:url>">Logout</a>
    <h2>Create New Post</h2>
    <form:form method="POST" action="create" modelAttribute="blog" enctype="multipart/form-data">
        <form:label path="title">Title:</form:label><br>
        <form:input path="title"/> <br><br>
        <form:label path="body:">Body: </form:label><br>
        <form:input path="body" rows="15" cols="50"/><br><br>
        <form:label path="image">Image: </form:label><br>
        <form:input path="image" type="file"/><br><br>
        <input type="submit" value="Submit">
    </form:form>
</body>
</html>
