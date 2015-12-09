<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>github brancher</title>
<script type="text/javascript">
<%--
@TODO need to protect context path
--%>
var Context = {
	path : "<%=request.getContextPath()%>"
};
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/jquery-2.1.4.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/page/home.js"></script>
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
				<td>
					<select id="repositoryName" title="Select a Repository" disabled="disabled">
					</select>
				</td>
				<td>
					<select id="baseBranch" disabled="disabled" title="Please Select a Base Branch">
						<option>Please Select a Base Branch</option>
					</select>
				</td>
				<td align="left">
					<input type="text" id="newBranchName" name="newBranchName" maxlength="100" title="New Branch name" placeholder="Enter branch name" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<button id="createButton">Create</button>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div id="result">
					
					</div>
				</td>
			</tr>
		</table>

	</div>
</body>
</html>