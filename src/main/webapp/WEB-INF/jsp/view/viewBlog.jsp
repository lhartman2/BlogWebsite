<html>
<head>
    <title>Blog Post</title>
</head>
<body>
    <h2>Blog Post</h2>
    <h3>Blog #<c:out value="${blogId}"/> : <c:out value="${blog.title}"/></h3>
    <p>Date: <c:out value="${blog.date}"/></p>
    <p><c:out value="${blog.body}"/></p>
    <c:if test="${blog.hasImage()}">
        <a href="<c:url value='/blog' >
            <c:param name='action' value='download' />
            <c:param name='blogId' value='${blogId}' />
            <c:param name='image' value='${blog.image.name}' />
        </c:url>"><c:out value="${blog.image.name}"/></a>
    </c:if>
    <br><a href="blog">Return to blog list</a>
</body>
</html>
