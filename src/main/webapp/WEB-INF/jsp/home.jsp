<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>github brancher</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.4.js">
</script>
</head>
<body>
	<div>
		<table>
			<tr>
				<th>Repository</th>
				<th>From Branch</th>
				<th>New Branch</th>
			</tr>
			<tr>
				<td><input type="text" name="repo" maxlength="100" /></td>
				<td></td>
				<td></td>

			</tr>
		</table>

	</div>
	<div></div>
	
	<script type="text/javascript" defer="defer">
		alert('hi jquery is: ' + jQuery);
	</script>
</body>
</html>