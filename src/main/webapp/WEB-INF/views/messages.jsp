<%-- 
    Document   : messages
    Created on : 18.06.2018, 14:20:40
    Author     : smit
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${not empty messages}">
    <c:forEach var="message" items="${messages}">
        <div class="alert alert-${message.getType()}">
          <strong>${message.getType()}!</strong> ${message.getText()}
        </div>
    </c:forEach>
</c:if>
