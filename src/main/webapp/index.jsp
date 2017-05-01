<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="padding:20px;">
<% Set<String> columns = (Set<String>) session.getAttribute("columns"); %>
<% List<Map<String,String>> values = (List<Map<String,String>>) session.getAttribute("values"); %>
<div class="alert alert-success" style="display:none;" role="alert" id="feedback">Your feedback is submitted successfully!</div>
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
		<td><% out.print(row.getOrDefault(column, "N/A")); %>
			<div class="dropdown">
			  <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    Correction
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="padding:10px; text-align: center;">
			    <% for (String col: columns ) { %>
					<a href="javascript:void(0);" onclick="sendFeedback();"><% out.print(col); %></a><br/>
				<%} %> 
			  </ul>
			</div>
		</td>
	<%} %>
</tr>
<%} %>
</table>
<script type="text/javascript">
function sendFeedback() {
	$("#feedback").show(100, function() {
		setTimeout(function() {$("#feedback").hide(100);} , 5000)
	});
}
</script>
</body>
</html>
