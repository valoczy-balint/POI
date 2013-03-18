<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add POI</title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
</head>

<body>
	<h1>ADD</h1>

	<div id="form">
		<form>
			<input type="text" id="name" /> 
			<input type="button" value="Add" onclick="add()" />
		</form>
	</div>
	
	<div id="result">
		
	</div>
	
	<script type="text/javascript"> 
	
	function add() {
		jq(function() {
			// Call a URL and pass two arguments
			// Also pass a call back function
			// See http://api.jquery.com/jQuery.post/
			// See http://api.jquery.com/jQuery.ajax/
			// You might find a warning in Firefox: Warning: Unexpected token in attribute selector: '!' 
			// See http://bugs.jquery.com/ticket/7535
			jq.post("/poi/add",	
					{ 	
						name:  jq("#name").val() 
					},
					function(data){
						jq("#result").replaceWith('<div id="result">'+ data + '</div>');
					});
		});
	}
	
	</script>
	
</body>
</html>