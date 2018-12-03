<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-localization">
    <a class="btn ${active ? 'disabled' : ''}" href="?lang=ru${not empty requestScope.queryString ? '&' : '' }${requestScope.queryString}">Ru</a>
    <a class="btn ${active ? 'disabled' : ''}" href="?lang=en${not empty requestScope.queryString ? '&' : '' }${requestScope.queryString}">En</a>
</div>
