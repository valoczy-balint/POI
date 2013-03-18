<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add POI</title>
</head>

<body>
	<h1>ADD</h1>

	<div id="form">
		<form:form action="/add" modelAttribute="poi" method="post" enctype="multipart/form-data">
			<fieldset>
			
				<legend>Poi fields</legend>
				
				<p>
					<form:label for="name" path="name">Name</form:label><br/>
					<form:input path="name"/>
				</p>
				
				<p>
					<form:label for="image" path="image">Image</form:label><br/>
					<form:input type="file" path="image"/>
				</p>
				
				<p>
					<input type="submit" value="Upload"/>
				</p>
				
			</fieldset>
		</form:form>
	</div>

</body>
</html>