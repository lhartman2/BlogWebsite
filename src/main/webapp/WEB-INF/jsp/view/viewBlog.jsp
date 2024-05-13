<html>
<head>
    <title>Blog Post</title>
</head>
<body>
    <a href="<c:url value='/login'>  <c:param name='logout'/></c:url>">Logout</a>
    <h2>Blog Post</h2>
    <h3>Blog #<c:out value="${blogId}"/> : <c:out value="${blog.title}"/></h3>
    <p>Date: <c:out value="${blog.date}"/></p>
    <p><c:out value="${blog.body}"/></p>
    <c:if test="${blog.hasImage()}">
        <a href="<c:url value='/blog/${blogId}/image/${blog.image.name}'/>">
            <c:out value="${blog.image.name}"/></a>
    </c:if>
    <br><a href="<c:url value='/blog/list'/>">Return to blog list</a>
</body>
</html>
