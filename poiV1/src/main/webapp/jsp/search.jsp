<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <!DOCTYPE html> -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	<script src="http://yandex.st/json2/2011-10-19/json2.js"></script>
	<title>Search</title>
</head>
<body>

	<w:wrapper>
		<div id="searchContent">
			<form:form>
				<fieldset id="searchFieldSet">
				
					<legend>Please provide search criteria</legend>
				<p>
					<label>Name</label><br/>
					<input  id="name" maxlength="50" class="text"/><br/>
				</p>
				
				<p>
					<label>Address</label><br/>
					<input  id="address" maxlength="200" class="text"/><br/>
				</p>
					
				<p>					
					<select class="dropDown" id="type">
						<c:forEach items="${types}" var="type">
							<option><c:out value="${type}"/></option>
						</c:forEach>
					</select>
				</p>
					
				<p>		
					<input type="button" value="Search" onclick="searchJSON()" />
				</p>
					
				</fieldset>
			</form:form>
		
			<div id="result" >
			</div>
		</div>
	</w:wrapper>

	<script type="text/javascript"> 
		var poiList = new Array();
		var markers = new Array();
		var infoWindows = new Array();
		   
		function searchJSON() {
			var params= {
				name:  jq("#name").val(),
				address: jq("#address").val(),
				type: jq("#type").val()
			};
			
			jq(function() {
				jq.ajax({
					url: "/poi/search",
					type: "POST",
					data: JSON.stringify(params),
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					beforeSend: function(x) {
						if (x && x.overrideMimeType) {
							x.overrideMimeType("application/j-son;charset=UTF-8");
						}
						jq("#result").replaceWith('<div id="result">Working</div>');
			        },
					success: function (data) {	
						jq.extend(true, poiList, data);										// Deep copy for usage outside the function
						
						jq("#searchFieldSet").fadeOut("slow", function() {					// Fade out the search field
							jq("#result").empty();
							jq("#result").append('<div id="list"> </div>' +
												 '<div id="multimedia"> </div>');
							
							jq("#list").hide();												// Needed fo the fade in effect
							jq("#multimedia").hide();
							
							jq.each(poiList, function(index, listItem) {					// Process result list items
								jq("#list").append(
									'<div class="listItem"><table>' + 
										'<tr>' + 
										'<td>' + listItem.name + '</td>' + 
										'<td>' + listItem.address + '</td>' + 
										'</tr><tr>' +
										'<td>' + listItem.type + '</td>' +
										'<td>' + listItem.rating + '</td>' +
										'</tr><tr>' +
										'<td><input type="button" value="Image"' + 
												   'class="imageBtn" id="imageBtn' + index + '"/></td>' + 
										'<td><input type="button" value="Video" class="videoBtn"/></td>' + 
										'</tr>' +
									'</table></div>'
								);
								jq("#imageBtn" + index).bind("click", function(event) {
								    imgBtnClick(listItem.imagePath);
								});
							});	
							
										
							jq("#list").fadeIn("slow");										//fade in the created list
							
							var script = document.createElement("script");
							script.type = "text/javascript";
							script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyCBgeK9uzOoF0DNa69eXp6i-nd9N7EZczg&sensor=false&callback=initialize";
							document.body.appendChild(script);
							jq("#multimedia").fadeIn("slow");
						});
					},
					error: function (data) {
						jq("#list").replaceWith('<div id="list">Error</div>');
					}
				});
			});
		}
					
		function initialize() {
			var mapProp = {
				center:new google.maps.LatLng(51.508742,-0.120850),
				zoom:5,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById("multimedia"), mapProp);
		    
			jq.each(poiList, function(index, poiListItem) {	
				if((poiListItem.latitude == null) || (poiListItem.longitude == null)) {
					//if statement somehow fucks up the eventlistener. Tried return true, false
					//the marker in east africa is becouse of a lat=lon=null
				} 
					var position = new google.maps.LatLng(poiListItem.latitude, poiListItem.longitude);
					
					var marker = new google.maps.Marker({
				        position: position,
				        map: map,
				        infoWindowIndex : index
				    });
		
					var infoWindow = new google.maps.InfoWindow({
			            content : poiListItem.name
			        });
					
					infoWindows.push(infoWindow);
			        markers.push(marker);
				
					google.maps.event.addListener(marker, 'click', 
						function(event) {
							infoWindows[this.infoWindowIndex].open(map, this);
				    });	
			});	
		}
		
		function imgBtnClick(imagePath) {

			//jq("#multimedia").empty();
			//var img = jq('<img class="poiImg">'); //Equivalent: $(document.createElement('img'))
			//jq(document.createElement('img'));
			//img.attr('src', "C:\Users\Mercury\Desktop\ZPics\Sun\1349894937789.jpg");
			//var img = document.createDocumentFragment('img');
			//var img = new Element('img', 
            //  { src: 'C:\Users\Mercury\Desktop\ZPics\Sun\1349894937789.jpg', alt: 'alternate text' }); 
			//var img = new Image(500, 500);
			//alert(imagePath);

			//img.appendTo("#multimedia");
			
			//jq("#multimedia").append('<img src="C:\Users\Mercury\Desktop\ZPics\Sun\1349894937789.jpg"/>');
			
			
			
			jq("#multimedia").fadeOut("slow", function() {
				jq("#multimedia").empty();
				
				var img = new Image(500, 500);
				img.src = imagePath;
				alert(imagePath);
				jq("#multimedia").append(img);
				jq("#multimedia").fadeIn("slow");
			});
		}		
	</script>

</body>
</html>