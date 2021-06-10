<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload Demo</title>
</head>

<body>
	<%
		String contextPath = request.getContextPath().replace("/", "");
	%>
	<div>File Upload Demo</div>
	<form method="post" action="uploadFile" enctype="multipart/form-data">
		Choose a file: <input type="file" name="uploadFile" /><input
			type="submit" value="Upload" />
	</form>
	
	<br />
	
	${requestScope.message}
	
	<br/>
	
	<h1>Files</h1>

	<ul>
		<c:forEach var="file" items="${requestScope.fileList}">
			<li><a href="/<%=contextPath%>/upload/${file}">${file}</a></li>
		</c:forEach>
	</ul>
</body>

</html>