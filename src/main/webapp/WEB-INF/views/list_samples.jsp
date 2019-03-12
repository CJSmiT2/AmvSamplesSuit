<%-- 
    Document   : list_samples
    Created on : 20.02.2019, 12:22:57
    Author     : smit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach var="sample" items="${samples}">
    <div class="img-thumbnail">
        <img src="gif?path=${base64.encode(sample.getGif())}" onclick="selectItem('${base64.encode(sample.getMp4())}')" >
            <div>
                <kbd><span class="text-warning" style="font-size: 9pt">${sample.getTime()}</span></kbd> 
                <kbd><span class="text-danger" style="font-size: 9pt">${sample.getAvgPercent()}%</span></kbd>
                <input type="checkbox" 
                   class="checkbox" 
                   id="${base64.encode(sample.getMp4())}" 
                   name="${base64.encode(sample.getMp4())}" 
                   value="${base64.encode(sample.getMp4())}">
            </div>
    </div>
</c:forEach>
