<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Groups list by ${groupType}</title>

      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <h3>Adding '${folderName}' to group</h3>
            <hr>
            <ul class="list-group">
                <c:forEach var="group" items="${groups}">
                    <a href="add_in_group?groupType=${groupType}&groupName=${group}&folderName=${folderName}" class="list-group-item">${group}</a> 
                </c:forEach>
            </ul>
        </div>
        
        <script src="${pageContext.request.contextPath}/resources/js/export_queue_info_ajax.js"></script>
        <jsp:include page="export_samples_info.jsp" />
   </body>
</html>
