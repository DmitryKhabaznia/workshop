<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="captchaId" required="true" %>

<input type="hidden" name="captchaId" value="${captchaId}"/>
<div class="captcha d-flex justify-content-center"><img src="captcha?captchaId=${captchaId}"></div><br>