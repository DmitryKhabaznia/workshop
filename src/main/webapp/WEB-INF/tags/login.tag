<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="resources"/>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <table align="right">
            <tr>
                <td>${sessionScope.user.firstName} ${sessionScope.user.lastName}</td>
                <td><img src="userAvatar" class="avatar"></td>
                <form method="post" action="logout">
                    <fmt:setBundle basename="resources" var="lang"/>
                    <fmt:setLocale value="${pageContext.request.locale}"/>
                    <td><input type="submit" class="loginBtn" value="<fmt:message key='common.logout'/>"></td>
                </form>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <table align="right">
            <tr><td>
                <form method="post" class="form-inline" action="login">
                    <input type="login" name="login" class="" placeholder="Login">
                    <input type="password" name="password" class="" placeholder="Password">
                    <input type="submit" class="loginBtn" id="submit" value="<fmt:message key='common.login'/>"/>
                </form>
                    <div class="invalid loginError" >${sessionScope.login_error}</div>
                    <c:remove var="login_error" scope="session" />
            </td></tr>
        </table>
    </c:otherwise>
</c:choose>