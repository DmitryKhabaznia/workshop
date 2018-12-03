<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix = "ct" %>
<%@ attribute name="filterBean" type="com.epam.bean.FilterFormBean" required="true" %>
<%@ attribute name="lastPage" required="true" %>

<center>
    <div class=" btn-group">
        <ct:paginationItem filterBean="${filterBean}" pageNumber="${filterBean.pageNumber - 1}" active="${filterBean.pageNumber eq 1}" itemName="Previous" />
        <c:forEach begin="1" end="${lastPage}" varStatus="loop">
            <ct:paginationItem filterBean="${filterBean}" pageNumber="${loop.index}" active="${loop.index eq filterBean.pageNumber}"/>
        </c:forEach>
        <ct:paginationItem filterBean="${filterBean}" pageNumber="${filterBean.pageNumber + 1}" active="${filterBean.pageNumber eq lastPage}" itemName="Next" />
    </div>
</center>