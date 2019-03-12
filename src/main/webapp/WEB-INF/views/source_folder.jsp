<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Source folder</title>

      <jsp:include page="headLinks.jsp" />
      <script src="${pageContext.request.contextPath}/resources/js/amvsamples_suit_scripts.js"></script>
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <form action="add_in_queue" method="post">
                <h1>Source folder</h1>
                <hr>
                <c:if test="${files.size() > 0}">
                    <div class="row">
                        <div class="col-sm-4">
                        </div>
                        <div class="text-center col-sm-4">
                            <input type="submit" value="Add selected in queue" class="btn btn-success btn-xs">
                        </div>
                        <div class="ttext-right col-sm-4">
                            <a href="add_all_in_queue" class="btn btn-success btn-xs">
                                <span class="glyphicon glyphicon-plus"></span> Add all
                            </a>
                        </div>
                    </div>
                </c:if>
                
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th class="col-sm-1">Select</th>
                            <th class="col-sm-11">Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="file" items="${files}">
                            <tr>
                                <td>
                                    <input type="checkbox" 
                                    class="checkbox"
                                    id="${file.getName()}"
                                    name="${file.getName()}" 
                                    value="${file.getName()}"
                                    style="margin: 0 auto">
                                </td>
                                <td onclick="selectItem('${file.getName()}')">
                                ${file.getName()}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        
        <script src="${pageContext.request.contextPath}/resources/js/export_queue_info_ajax.js"></script>
        <jsp:include page="export_samples_info.jsp" />
   </body>
</html>
