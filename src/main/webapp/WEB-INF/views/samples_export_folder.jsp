<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Samples export folder</title>
      
      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="panel panel-default">
            <h3>${exportFolder}</h3>
            <div class="panel-body">
                <c:forEach var="sampleExport" items="${samplesExport}">
                    <div class="img-thumbnail">
                        <a onClick="openExportSampleFolderInExplorer('${sampleExport.getSampleName()}')">
                            <img src="gif?path=${base64.encode(sample.getGif())}">
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
            
        <script>
            function openExportSampleFolderInExplorer(sampleFolder){
                console.log("try open: " + sampleFolder);
                $.ajax({
                    type: 'get',
                    url: 'open_export_sample_folder_in_explorer/' + sampleFolder,
                    success: function(response){
                        console.log("response " + response);
                    },
                    error: function(err){
                        alert(err);
                    }
                });
            }
        </script>

   </body>
</html>
