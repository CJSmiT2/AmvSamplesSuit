<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>view sample without tag</title>
      
      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        <div class="container">
            
            <div class="img-thumbnail">
                <img src="gif?path=${base64.encode(sample.getGif())}" >
                    <div>
                        <kbd><span class="text-warning" style="font-size: 9pt">${sample.getTime()}</span></kbd> 
                        <kbd><span class="text-danger" style="font-size: 9pt">${sample.getAvgPercent()}%</span></kbd>
                    </div>
            </div>

            <hr>

            <form action="add_sample_to_tag_groups" method="post">
                <input type="hidden" name="titleFolder" value="${sample.getTitle()}">
                <input type="hidden" name="ssFolder" value="${sample.getSs()}">
                <div><input type="submit" class="btn btn-success btn-xs"></div>
                <c:forEach var="group" items="${groups}">
                    <div style="float: left; border: 1px solid yellowgreen;  border-radius: 2px; margin: 5px; padding: 3px;">
                        <kbd><b class="text-success">${group}</b></kbd> 
                        <input type="checkbox" 
                           class="checkbox" 
                           name="${group}" 
                           value="1">
                    </div>
                </c:forEach>
            </form>
            
        </div>
   </body>
</html>
