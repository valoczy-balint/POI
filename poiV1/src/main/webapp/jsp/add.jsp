<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
	
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	
	<title>Add POI</title>
</head>

<body>

<w:wrapper>
	<h1>Add Poi</h1>

	<div id="form">
		<form:form action="/add" modelAttribute="poi" method="post" enctype="multipart/form-data">
			<fieldset>
			
				<legend>Poi fields</legend>
				
				<p>
					<form:label for="name" path="name">Name</form:label><br/>
					<form:input path="name" maxlength="50" cssClass="textInput"/><br/>
				</p>
				
				<p>
					<form:label for="address" path="address">Address</form:label><br/>
					<form:input path="address" maxlength="200" cssClass="textInput"/><br/>
				</p>
				
				<p>					
					<form:select path="type" cssClass="dropDown">
						<form:options items="${types}"/>
					</form:select>
				</p>
				
				<p>
					<form:label for="longitude" path="longitude">Longitude</form:label><br/>
					<form:input path="longitude" maxlength="15" cssClass="textInput"/><br/>
				</p>
				
				<p>
					<form:label for="latitude" path="latitude">Latitude</form:label><br/>
					<form:input path="latitude" maxlength="15" cssClass="textInput"/><br/>
				</p>
				
				<p>
					<form:label for="image" path="image">Image</form:label><br/>
					<form:input cssClass="fileInput" type="file" path="image"/><br/>
				</p>
				
				<p>
					<form:select path="rating">
						<form:options items="${rating}" />
					</form:select>
				<p>
					<input type="submit" value="Upload"/>
				</p>
				
			</fieldset>
		</form:form>
	</div>

</w:wrapper>
</body>
</html>