<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Groups by ${groupType}</title>

      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            
            <h1>Groups by ${groupType}</h1>
            <hr>
            
            <form action="create_group" method="get">    
                <div class="row">
                    <div class="col-sm-6">

                    </div>
                    <div class="col-sm-4">
                        <input type="text" name="groupName" class="form-control" required="required" placeholder="Name for new group">
                        <input type="hidden" name="groupType" value="${groupType}">
                    </div>
                    <div class="col-sm-2">
                        <input type="submit" value="Create" class="btn btn-success btn-block">
                    </div>
                </div>
            </form>
                
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th class="col-sm-1">View</th>
                        <th class="col-sm-10">Name</th>
                        <th class="col-sm-1">Manage</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="group" items="${groups}">
                        <tr>
                            <td>
                                <a href="view_samples_in_group?groupType=${groupType}&groupName=${group}" class="btn btn-success btn-xs">view</a>
                            </td>
                            <td>
                                <c:if test="${groupType == 'titles'}">
                                    <a href="view_group?groupType=${groupType}&groupName=${group}">${group}</a>
                                </c:if>
                                <c:if test="${groupType == 'tags'}">
                                    ${group}
                                </c:if>
                            </td>
                            <td>
                                <a href="delete_group?groupType=${groupType}&groupName=${group}" class="btn btn-danger btn-xs btn-block">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        </div>
   </body>
</html>
