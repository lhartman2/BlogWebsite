<html>
<head>
    <title>Blog Login</title>
</head>
<body>
    <h2>Login</h2>
    <c:if test="${loginFailed == true}">
        <c:out value = "The username or password you entered are not correct."/>
    </c:if>
    <form method="POST" action="<c:url value='/login'/>">
        Username: <input type="text" name="username"><br><br>
        Password: <input type="password" name="password"><br><br>
        <input type="submit" value="Log In">
    </form>
</body>
</html>
