<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Samples folder</title>
      
      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-sm-10">
                        <kbd><span class="text-success">Folder: <b>${folderName}</b></span></kbd> 
                        <kbd><span class="text-warning">Time rage: <b><span id="rageMin"></span>-<span id="rageMax"></span></b></span></kbd> 
                        <kbd><span class="text-danger">Percent limit: <b>${limits.getMinAvgPercent()}%</b></span></kbd> 
                    </div>
                    <div class="col-sm-2">
                        <a href="set_limits?folderName=${folderName}" class="btn btn-primary btn-xs btn-block">
                            Change limits <span class="glyphicon glyphicon-stats"></span>
                        </a>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <form action="process_selected" method="post">
                    <input type="hidden" name="folderName" value="${folderName}">
                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-2"></div>
                        <div class="col-sm-2">
                            <select id="samples_action" class="form-control" name="selected_action" required="required" onchange="showSamplesGroupsToForm()">
                                <option disabled="" selected="">Choose action</option>
                                <option value='delete_samples'>Delete samples</option>
                                <option value='add_to_samples_group'>Add selected samples to group</option>
                                <option value='add_folder_to_titles_group'>Add folder to titles group</option>
                                <option value='export_selected'>Export selected to folder</option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <jsp:include page="select_list_group_samples.jsp" />
                            <jsp:include page="select_list_group_titles.jsp" />
                            <jsp:include page="select_options_delete_samples.jsp" />
                        </div>
                        <div class="col-sm-2">
                            <input type="submit" class="btn btn-success" value="Submit">
                        </div>
                        <div class="col-sm-2"></div>
                    </div>
                    <hr>
                    <jsp:include page="list_samples.jsp" />
                </form>
            </div>
        </div>
            
        <!-- manual -->
        <script src="${pageContext.request.contextPath}/resources/js/amvsamples_suit_scripts.js"></script>
        <script>
            var timeRageMin = secondsToHms(${limits.getSsStart()});
            $("#rageMin").text(timeRageMin);
            var timeRageMax = secondsToHms(${limits.getSsEnd()});
            $("#rageMax").text(timeRageMax);
        </script>
        <script src="${pageContext.request.contextPath}/resources/js/export_queue_info_ajax.js"></script>
        <jsp:include page="export_samples_info.jsp" />
   </body>
</html>
