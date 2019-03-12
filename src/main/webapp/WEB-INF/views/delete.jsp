<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Delete</title>

      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container text-center">
            <h1>Do you really want to delete '${folderName}'?</h1>
            <a href="delete_confirm?folderName=${folderName}" class="btn btn-danger"> <span class="glyphicon glyphicon-remove"></span> DELETE</a> 
        </div>
        
   </body>
</html>
