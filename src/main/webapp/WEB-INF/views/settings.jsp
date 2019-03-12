<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Settings</title>

      <jsp:include page="headLinks.jsp" />
   </head>
   <body>
        <jsp:include page="menu.jsp" />
        <jsp:include page="messages.jsp" />
        
        <div class="container">
            <h1>Settings</h1>
            <hr>
            <form class="form-horizontal" action="save_settings">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="src_folder">Source folder: </label>
                    <div class="col-sm-10">
                        <input type="text" 
                               value="${srcFolder}"
                               class="form-control" 
                               id="src_folder" 
                               name="src_folder" 
                               placeholder="example - D:\folder_with_my_anime" 
                               required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="base_of_samples">Base of samples: </label>
                    <div class="col-sm-10">
                        <input type="text" 
                               value="${baseOfSamplesFolder}"
                               class="form-control" 
                               id="base_of_samples" 
                               name="base_of_samples"
                               placeholder="example - D:\folder_for_my_amvsamples" 
                               required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="export_folder">Folder for export: </label>
                    <div class="col-sm-10">
                        <input type="text" 
                               value="${exportFolder}"
                               class="form-control" 
                               id="base_of_samples" 
                               name="export_folder"
                               placeholder="example - D:\folder_for_my_exported_amvsamples" 
                               required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="samples_length">Samples length in sec: </label>
                    <div class="col-sm-10">
                        <select class="form-control" id="samples_length" name="samples_length">
                            <option value="3" <c:if test="${sampleLengthInSec == 3}">selected</c:if> >3 sec</option>
                            <option value="5" <c:if test="${sampleLengthInSec == 5}">selected</c:if> >5 sec</option>
                            <option value="7" <c:if test="${sampleLengthInSec == 7}">selected</c:if> >7 sec</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="resolution_gif_analyzing">Resolution for gif and analyzing (px): </label>
                    <div class="col-sm-10">
                        <select class="form-control" id="resolution_gif_analyzing" name="resolution_gif_analyzing">
                            <option value="128*72" <c:if test="${resolutionForGifAndAnalyzing.getWidth() == 128 && resolutionForGifAndAnalyzing.getHeight() == 72}">selected</c:if> >128*72</option>
                            <option value="192*108" <c:if test="${resolutionForGifAndAnalyzing.getWidth() == 192 && resolutionForGifAndAnalyzing.getHeight() == 108}">selected</c:if>>192*108</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="localhostOnly">Access from localhost only: </label>
                    <div class="col-sm-10">
                        <select class="form-control" id="localhostOnly" name="localhostOnly">
                            <option value="true" <c:if test="${localhostOnly == true}">selected</c:if> >Yes</option>
                            <option value="false" <c:if test="${localhostOnly == false}">selected</c:if> >No</option>
                        </select>
                    </div>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-success">Save</button>
                </div>
            </form>
        </div>
      
   </body>
</html>
