<%-- 
    Document   : home
    Created on : 24.03.2017, 11:40:45
    Author     : smit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Set limits</title>
        <jsp:include page="headLinks.jsp" />
        
        <script src="${pageContext.request.contextPath}/resources/js/chart.js"></script>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jquery_ui/jquery-ui.theme.min.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jquery_ui/jquery-ui.min.css">
      <script src="${pageContext.request.contextPath}/resources/jquery_ui/jquery-ui.min.js"></script>
      
      <script>
  $( function() {
    $( "#slider-range" ).slider({
      range: true,
      min: 0,
      max: ${limits.getSsEnd()},
      values: [ ${limits.getSsStart()}, ${limits.getSsEnd()} ],
      slide: function( event, ui ) {
        $( "#amount" ).val( ui.values[ 0 ] + "-" + ui.values[ 1 ] );
        $( "#rage_time" ).val( ssToTime($( "#slider-range" ).slider( "values", 0 )) + "-" + ssToTime($( "#slider-range" ).slider( "values", 1 )));
      }
    });
    $( "#amount" ).val( $( "#slider-range" ).slider( "values", 0 ) + "-" + $( "#slider-range" ).slider( "values", 1 ) );
    $( "#rage_time" ).val( ssToTime($( "#slider-range" ).slider( "values", 0 )) + "-" + ssToTime($( "#slider-range" ).slider( "values", 1 )));
  } );
  
  function ssToTime(totalSeconds){
    let hours = Math.floor(totalSeconds / 3600);
    totalSeconds %= 3600;
    let minutes = Math.floor(totalSeconds / 60);
    let seconds = totalSeconds % 60;

    minutes = String(minutes).padStart(2, "0");
    hours = String(hours).padStart(2, "0");
    seconds = String(seconds).padStart(2, "0");
    
    return hours + ":" + minutes + ":" + seconds;
  }
  </script>
        
    </head>
    <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        <div style="width: 1700px;"><canvas id="myChart" width="undefined" height="undefined"></canvas></div>
        <div id="slider-range" style="width: 1665px; position: relative; left: 35px;"></div>
        <script>
            new Chart(document.getElementById("myChart"),
            {
                "type":"line",
                "data":{
                    "labels":[
                        <c:forEach var="sample" items="${samples}">
                                "${sample.getAvgPercent()}",
                            </c:forEach>
                    ],
                    "datasets":[
                        {"label":"Percent",
                            "data":[
                            <c:forEach var="sample" items="${samples}">
                                "${sample.getAvgPercent()}",
                            </c:forEach>
                            ],
                            "pointRadius": 1,
                            "borderWidth": 1,
                            "fill": true,
                            "backgroundColor": "rgb(50, 120, 120)",
                            "borderColor": "rgb(75, 192, 192)",
                            "lineTension":0.1
                        }
                    ]}
            });
        </script>
        
        <div style="padding: 10px; width: 350px; position: fixed; top: 100px; right: 10px; background-color:rgba(0,0,0,0.5); border-radius: 5px;">
            <form class="form-horizontal">
                <input type="hidden" name="folderName" value="${folderName}">
                <div class="form-group">
                    <label class="control-label col-sm-6" for="limit">Limit:</label>
                    <div class="col-sm-6"> 
                        <input type="text" class="form-control" id="limit" placeholder="%" required="required" name="limit" value="${limit}">
                    </div>
                </div>
                <div class="text-center"> 
                    <button type="submit" class="btn btn-default">Update</button>
                </div>
            </form>
                    
            <hr>
                    
            <form class="form-horizontal" action="save_limits" method="get">
                <div class="form-group">
                    <label class="control-label col-sm-4" for="amount">Rage ss: </label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="amount"  readonly name="rage">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4" for="rage_time">Rage time: </label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="rage_time"  readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4" for="percent">Min percent:</label>
                    <div class="col-sm-8"> 
                        <input type="text" class="form-control" 
                               id="percent" placeholder="%" required="required" 
                               name="minAvgPercent" value="${limits.getMinAvgPercent()}">
                    </div>
                </div>
                <div class="text-center"> 
                    <button type="submit" class="btn btn-success">Save</button>
                </div>
                <input type="hidden" name="folderName" value="${folderName}">
            </form>
        </div>

    </body>
</html>
