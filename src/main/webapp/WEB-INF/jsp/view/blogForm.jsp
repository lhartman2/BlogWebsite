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
    <h2>Create New Post</h2>
    <form method="POST" action="blog" enctype="multipart/form-data">
        <input type="hidden" name="action" value="create">
        Title:<br>
        <input type="text" name="title"><br><br>
        Body: <br>
        <textarea name="body" rows="15" cols="50"></textarea><br><br>
        Image: <br>
        <input type="file" name="file1"><br><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
