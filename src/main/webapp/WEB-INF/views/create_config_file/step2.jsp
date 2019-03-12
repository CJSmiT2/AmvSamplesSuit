<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Step 2</title>
      <jsp:include page="../headLinks.jsp" />

   </head>
   <body>
        <div class="container">
            
            <jsp:include page="../messages.jsp" />
            
            <div class="panel panel-default">
                <div class="panel-body">
                    Configuration: Step 2
                </div>
                <div class="panel-footer">
                    <p>description description description description description 
                        description description description description description </p>
                    <form class="form-horizontal" method="post">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="base_of_samples">Base of samples: </label>
                            <div class="col-sm-10">
                                <input type="text" 
                                       class="form-control" 
                                       id="src_folder" 
                                       name="base_of_samples" 
                                       placeholder="example - D:\folder_for_my_amvsamples" 
                                       required="required">
                            </div>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-success">Next</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
   </body>
   
</html>

