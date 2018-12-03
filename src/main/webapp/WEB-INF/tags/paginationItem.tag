<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="filterBean" type="com.epam.bean.FilterFormBean" required="true" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="active" required="true" %>
<%@ attribute name="itemName" required="false" %>

<a class="btn btn-outline-secondary ${active ? 'disabled' : ''}" href="?pageNumber=${pageNumber}&${not empty requestScope.queryString ? '&' : '' }${requestScope.queryString}">
    ${not empty itemName ? itemName : pageNumber}
</a>