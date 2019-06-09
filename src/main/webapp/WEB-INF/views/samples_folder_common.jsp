<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Samples folder by group</title>

      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="panel panel-default">
            <div class="panel-heading">
                Samples from group ${group}
            </div>
            <div class="panel-body">
                <form action="process_selected_group" method="post">
                    <input type="hidden" name="groupName" value="${groupName}">
                    <input type="hidden" name="groupType" value="${groupType}">
                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-2"></div>
                        <div class="col-sm-2">
                            <select class="form-control" name="selected_action" required="required">
                                <option disabled="" selected="">Choose action</option>
                                <option value='export_selected'>Export selected to folder</option>
                                <option value='remove_from_samples_group'>Remove selected from (${groupName}) group!</option>
                                <option value='delete_selected'>DELETE selected samples!!!</option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <input type="submit" class="btn btn-success" value="Submit">
                        </div>
                        <div class="col-sm-2"></div>
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
   </body>
</html>
