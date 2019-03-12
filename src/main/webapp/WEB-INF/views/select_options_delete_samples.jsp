<%-- 
    Document   : select_list_group_samples
    Created on : 28.02.2019, 10:33:30
    Author     : smit
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="delete_samples" id="delete_samples" class="form-control" style="display: none;">
    <option value="delete_samples_by_min_percent_limit">Delete samples by min percent limit</option>
    <option value='delete_not_selected'>Delete not selected samples</option>
    <option value="delete_all_samples">Delete all samples (folder)</option>
</select>
