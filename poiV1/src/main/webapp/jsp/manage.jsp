<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	<title>Edit poi</title>
</head>

<body>
	<w:wrapper>
		<form:form action="/manage" modelAttribute="poi" method="post" enctype="multipart/form-data" id="editForm">
		<fieldset>
		
			<legend>Poi information</legend>
			
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
				<input type="submit" value="Search"/>
			</p>
			
		</fieldset>
		</form:form>
	</w:wrapper>
</body>
</html>