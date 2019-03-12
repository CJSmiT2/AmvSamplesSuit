<%-- 
    Document   : select_list_group_samples
    Created on : 28.02.2019, 10:33:30
    Author     : smit
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="title_group" id="title_group" class="form-control" style="display: none;">
    <c:forEach var="group" items="${titlesGroups}">
        <option value="${group}">${group}</option>
    </c:forEach>
</select>
