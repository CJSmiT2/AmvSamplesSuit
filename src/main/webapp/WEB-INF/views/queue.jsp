<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Queue</title>
      <jsp:include page="headLinks.jsp" />
      <script src="${pageContext.request.contextPath}/resources/js/amvsamples_suit_scripts.js"></script>

   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <h1>Queue list</h1>
            <hr>
            <div id="process" style="display: none">
                <div class="row">
                    <div class="col-sm-2">
                        <div class="sk-cube-grid">
                          <div class="sk-cube sk-cube1"></div>
                          <div class="sk-cube sk-cube2"></div>
                          <div class="sk-cube sk-cube3"></div>
                          <div class="sk-cube sk-cube4"></div>
                          <div class="sk-cube sk-cube5"></div>
                          <div class="sk-cube sk-cube6"></div>
                          <div class="sk-cube sk-cube7"></div>
                          <div class="sk-cube sk-cube8"></div>
                          <div class="sk-cube sk-cube9"></div>
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <p><b>File name: </b> <span id="fileName" class="text-success"></span></p>
                        <p><b>Sample time: </b><span id="ss" class="text-success"></span></p>
                        <p><b>Samples count: </b><span id="samplesCount" class="text-success"></span></p>
                    </div>
                    <div class="col-sm-2">
                        <img id="lastGif" class="img-thumbnail">
                    </div>
                </div>
            </div>
            <hr>
            <c:if test="${files.size() > 0}">
            <div class="text-right">
                <a href="remove_all_from_queue" class="btn btn-warning btn-xs">Cancel all</a>
            </div> 
            </c:if>
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th class="col-sm-1">Manage</th>
                        <th class="col-sm-11">Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="file" items="${files}">
                        <tr>
                            <td>
                                <a href="remove_from_queue?fileName=${file.getName()}" class="btn btn-warning btn-xs">
                                    cancel <span class="glyphicon glyphicon-remove-sign"></span>
                                </a>
                            </td>
                            <td>
                                ${file.getName()}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
   </body>
</html>
