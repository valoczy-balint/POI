<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display a poi</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<td>
					<p><c:out value="${poi.id}" /></p>
				</td>
				
				<td><c:out value="${poi.name}" />
				</td>
				
				<td>
				<c:out value="${poi.image.getBytes()}" /><br/>
				<img src="${poi.image.getBytes()}"/><br/>
				<img src="data:image/jpg;base64,<c:out value='${poi.image.getBytes().toString()}'/>" /><br/>
				<img src="data:image;base64,<c:out value='${imageString}'/>" />
				</td>
			</tr>
		</table>
	
	</div>
	

</body>
</html>