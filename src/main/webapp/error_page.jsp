<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}" />
    <c:set var="message" value="${requestScope['javax.servlet.error.message']}" />
    <div class="content-wrapper">
        <div class="inner-container container">
            <div class="row">
                <div class="section-header col-md-12">
                    <h2>
                        <c:if test="${not empty code}">${code}</c:if> - Error was occurred !</h2>
                </div>
                <!-- /.section-header -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-md-12">
                    <div class="box-content">
                        <div class="text-center error-page">
                            <h1>
                                <c:if test="${not empty code}">${code}</c:if>
                            </h1>
                            <span>
                                <c:if test="${not empty message}">${message}</c:if>
                            </span>
                            <p>${error_message}</p>
                            <c:remove var="error_message"/>
                            <p>
                                <a href="index.jsp">&larr; Go back Home</a>
                            </p>
                        </div>
                        <!-- /.text-center -->
                    </div>
                    <!-- /.box-content -->
                </div>
                <!-- /.col-md-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.inner-content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>