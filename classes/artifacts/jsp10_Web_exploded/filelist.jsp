<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>文件下载列表</title>
	<link type="text/css" rel="stylesheet" href="common.css">
</head>
<script language="javascript" src="time.js" charset="gbk"></script>
<script language="javascript">

</script>
<body>
<center>
<h2>文件下载列表</h2>
<c:forEach items="${fnames}" var="f">
	<a href="com?cmd=download&fn=${f }">${f}</a><br>
</c:forEach>
</center>
</body>
</html>