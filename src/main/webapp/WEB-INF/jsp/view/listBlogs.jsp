<html>
<head>
    <title>Blog Posts</title>
</head>
<body>
<a href="<c:url value='/login'>  <c:param name='logout'/></c:url>">Logout</a>
    <h2>Blog Posts</h2>
    <a href="<c:url value='/blog'>
            <c:param name='action' value='createBlog'/>
            </c:url>">Create Post</a><br><br>
    <c:choose>
        <c:when test="${blogDB.size() == 0}">
            <p>There are no blog posts yet...</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="blog" items="${blogDB}">
                Blog #: <c:out value="${blog.key}"/>
                <a href="<c:url value='/blog' >
                    <c:param name='action' value='view'/>
                    <c:param name='blogId' value='${blog.key}'/>
                        </c:url>"><c:out value="${blog.value.title}"/></a><br>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</body>
</html>
