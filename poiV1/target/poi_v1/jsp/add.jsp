<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags/" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
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
					<form:input path="name" maxlength="50"/><br/>
				</p>
				
				<p>
					<form:label for="address" path="address">Address</form:label><br/>
					<form:input path="address" maxlength="200"/><br/>
				</p>
				
				<p><!-- 
					<form:label for="type" path="address">Type</form:label><br/>
					<form:input path="type" maxlength="200"/><br/>
				</p>
				
				<p>
					<select name="type" path="type" >
						    <c:forEach items="${types}" var="type">
					        	<option value="${type}">${type}</option>
					        
				   		</c:forEach>
					</select> -->
					
					<form:select path="type">
						<form:options items="${types}" />
					</form:select>
				</p>
				
				<p>
					<form:label for="image" path="image">Image</form:label><br/>
					<form:input cssClass="fileInput" type="file" path="image"/><br/>
				</p>
				
				<p>
					<input type="submit" value="Upload"/>
				</p>
				
			</fieldset>
		</form:form>
	</div>

</w:wrapper>
</body>
</html>