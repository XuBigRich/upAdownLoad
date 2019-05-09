<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
	<title>文件上传</title>
	<link type="text/css" rel="stylesheet" href="common.css">
</head>
<script language="javascript" src="time.js" charset="gbk"></script>
<script language="javascript">

</script>
<body>
<center>
<form action="com" method="post" enctype="multipart/form-data">
<table width=400 cellspacing=1>
	<tr><th colspan=2>文件上传</th></tr>
	<tr>
		<th>其他数据</th>
		<td><input type=text name="txt"></td>
	</tr>
	<tr>
		<th>附件</th>
		<td><input type=file name="fn"></td>
	</tr>
	<tr><th colspan=2><input type=submit value="提 交"></th></tr>
</table>
</form>
<a href="com?cmd=allFiles">文件下载列表</a>
</center>
</body>
</html>