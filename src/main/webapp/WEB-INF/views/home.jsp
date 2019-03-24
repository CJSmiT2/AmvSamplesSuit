<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>AmvSampler</title>
      <jsp:include page="headLinks.jsp" />

   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <div class="alert alert-warning">
                You use alpha version of application. This means they have many problems, errors, etc.
            </div>
            
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h4>Statistics info</h4>
                            <p>Samples in groups: <b class="text-success">${samplesInGroupsSize}</b></p>
                            <p>Not sorted samples: <b class="text-warning">${statisticsInfo.getUnprocessedSamplesCount()}</b></p>
                            <p>Created samples: <b class="text-primary">${statisticsInfo.getCreatedSamplesCount()}</b></p>
                            <p>Removed samples: <b>${statisticsInfo.getRemoveSamplesCount()}</b></p>
                            <p>Processed files: <b>${statisticsInfo.getProcessedFilesCount()}</b></p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-6">
                    <h4>Groups by titles</h4>
                    <div class="list-group">
                        <c:forEach var="group" items="${groupsInfoTitles}">
                            <a href="view_samples_in_group?groupType=titles&groupName=${group.getName()}" class="list-group-item">
                                ${group.getName()} <span class="badge">${group.getGroupSize()}</span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-sm-6">
                    <h4>Groups by samples</h4>
                    <div class="list-group">
                        <c:forEach var="group" items="${groupsInfoSamples}">
                            <a href="view_samples_in_group?groupType=samples&groupName=${group.getName()}" class="list-group-item">
                                ${group.getName()} <span class="badge">${group.getGroupSize()}</span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            
        </div>

        <script src="${pageContext.request.contextPath}/resources/js/export_queue_info_ajax.js"></script>
        <jsp:include page="export_samples_info.jsp" />
   </body>
   
</html>

