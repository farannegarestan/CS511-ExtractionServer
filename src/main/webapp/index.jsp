<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body style="padding:20px;">
<% Set<String> columns = (Set<String>) session.getAttribute("columns"); %>
<% List<Map<String,String>> values = (List<Map<String,String>>) session.getAttribute("values"); %>
<h3>Results:</h3>
<table class="table table-bordered">
<tr>

<% for (String column: columns ) { %>
	<th><% out.print(column); %></th>
<%} %>
</tr>
<% for (Map<String,String> row: values ) { %>
<tr>
	<% for (String column: columns ) { %>
		<td><% out.print(row.getOrDefault(column, "N/A")); %></td>
	<%} %>
</tr>
<%} %>
</table>
</body>
</html>
