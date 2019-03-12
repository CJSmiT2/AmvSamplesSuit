<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>List of folders with samples</title>

      <jsp:include page="headLinks.jsp" />
      <script src="${pageContext.request.contextPath}/resources/js/amvsamples_suit_scripts.js"></script>
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <h1>List of folders with samples</h1>
            <hr>
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th class="col-sm-1">Manage</th>
                        <th class="col-sm-1">Sort</th>
                        <th class="col-sm-1">Delete</th>
                        <th class="col-sm-9">Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="folder" items="${foldersWithSplitedFiles}">
                        <tr>
                            <td>
                                <a href="samples_folder?folderName=${folder.getName()}" 
                                   class="btn btn-success btn-xs btn-block">
                                    View <span class="glyphicon glyphicon-folder-open"></span>
                                </a>
                            </td>
                            <td>
                                <a href="set_limits?folderName=${folder.getName()}" class="btn btn-primary btn-xs btn-block">
                                    Set limits <span class="glyphicon glyphicon-stats"></span>
                                </a>
                            </td>
                            <td>
                                <a href="delete?folderName=${folder.getName()}" class="btn btn-danger btn-xs btn-block">
                                    Delete <span class="glyphicon glyphicon-remove"></span>
                                </a>
                            </td>
                            <td>
                                ${folder.getName()}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <script src="${pageContext.request.contextPath}/resources/js/export_queue_info_ajax.js"></script>
        <jsp:include page="export_samples_info.jsp" />
   </body>
</html>
