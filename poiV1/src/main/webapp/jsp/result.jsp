<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="resource/jquery-1.9.1.min.js"></script>
	
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
<title>Result List</title>
</head>
<body>
	<w:wrapper>
		
		
		<h2>Select the item you would like to edit</h2>
		<div id="manageList">
			<table>
				<tr>
					<td>Name</td>
					<td>Address</td>
					<td>Type</td>
					<td>Rating</td>
				</tr>
			</table>
			
			<c:forEach var="listItem" items="${poiList}">
				<div class="manageListItem" onclick="window.location.replace('/poi/edit/' + ${listItem.id})">
					<table>
						<tr>
							<td><c:out value="${listItem.getName()}"/></td>
							<td><c:out value="${listItem.getAddress()}"/></td>
							<td><c:out value="${listItem.getType()}"/></td>
							<td><c:out value="${listItem.getRating()}"/></td>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</w:wrapper>	
	
	<script type="text/javascript">
		var poiList = ${poiList};
	
		jq(document).ready(function () {
			jq.each(poiList, function(index, listItem) {	
				alert(listItem.name);
			});
		});
	
		function selectItem(event) {
			alert(event);
			window.location.replace("/poi/display/");
	  	};
	
	   // jq(document).ready(function () {
	
	  //      jq("div.clickableDiv").click(function () {
	   //         alert("Peekaboo"); 
	   //     });
	  //  });
</script>
</body>
</html>