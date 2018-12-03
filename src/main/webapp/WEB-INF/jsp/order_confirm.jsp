<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="order" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="content-wrapper">
        <div class="col-md-12">
            <div class="content-wrapper">
                <div class="inner-container container">
                    <div class="row">
                        <div class="section-header col-md-12">
                            <h2><fmt:message key='order.confirm.order'/></h2>
                            <span><fmt:message key='order.confirm.enterDate'/></span>
                        </div>
                        <!-- /.section-header -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <!-- /.service-item -->
                        <div class="col-md-12 service-item">
                            <div class="box-content">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.resultMessage}">
                                        <div class="center-block valid"><p class="h4 text-center">${sessionScope.resultMessage}</p></div>
                                        <hr>
                                    </c:when>
                                </c:choose>
                                <div class="row">
                                    <div class="col-md-7">
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.user'/>:</small> ${sessionScope.order.user.firstName} ${sessionScope.order.user.lastName}</p>
                                        <hr>
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.paymentType'/>:</small> ${sessionScope.order.paymentType} </p>
                                        <hr>
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.shippingType'/>:</small> ${sessionScope.order.shippingType} </p>
                                        <c:choose><c:when test="${not empty sessionScope.order.deliveryAddress}">
                                            <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.deliveryAddress'/>:</small> ${sessionScope.order.deliveryAddress} </p>
                                        </c:when></c:choose>
                                        <hr>
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.orderDate'/>:</small>
                                            ${sessionScope.order.orderDate.getMonth().toString().toLowerCase()} 
                                            ${sessionScope.order.orderDate.getDayOfMonth()}, 
                                            ${sessionScope.order.orderDate.getYear()}</p>
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.orderStatus'/>:</small> ${sessionScope.order.status} </p>
                                        <p class="h4"><small class="text-muted"><fmt:message key='order.confirm.description'/>:</small> ${sessionScope.order.statusDescription} </p>
                                    </div>
                                    <div class="col-md-5">
                                        <table id="cart" class="table table-hover table-condensed">
                                            <thead>
                                                <tr>
                                                    <th style="width:80%"><fmt:message key='order.product'/></th>
                                                    <th style="width:10%"><fmt:message key='order.quantity'/></th>
                                                </tr>
                                            </thead>
                                            <c:set var="sum" value="0" />
                                            <c:forEach var="item" items="${sessionScope.order.orderProducts}">
                                                <c:set var="sum" value="${sum + item.currentPrice*item.count}" />
                                                <tbody>
                                                    <tr>
                                                        <td data-th="Product">
                                                            <div class="row">
                                                                <div class="col-md-3">
                                                                    <img class="cart-element img-thumbnail" src="images/products/${item.product.imageName}.jpg" alt="">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <h4 class="nomargin">${item.product.name}</h4>
                                                                    <p>${item.product.description}</p>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td data-th="Quantity">${item.count}</td>
                                                    </tr>
                                                </tbody>
                                            </c:forEach>
                                            <tfoot>
                                                <tr><td>
                                                    <strong><fmt:message key='products.total'/>: $${sum}</strong>
                                                </td></tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                                <div>
                                    <c:choose>
                                        <c:when test="${empty sessionScope.resultMessage}">
                                            <hr>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <form action="order" method="get" id="back">
                                                        <button class="addBtn" form="back">
                                                            <i class="fa fa-arrow-left"></i>
                                                            back
                                                        </button>
                                                    </form>
                                                </div>
                                                <div class="col-md-6">
                                                    <form action="orderConfirm" method="post" id="confirm">
                                                        <button class="addBtn" form="confirm">
                                                            confirm
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose><c:when test="${empty sessionScope.SHOPPING_CART}">
                                                <c:remove var="order" scope="session"/>
                                            </c:when></c:choose>
                                            <hr>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <form action="products" method="get" id="products">
                                                        <button class="addBtn" form="products">
                                                            <fmt:message key="order.confirm.back"/>
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:remove var="resultMessage" scope="session"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.inner-content -->
            </div>
            <!-- /.content-wrapper -->
        </div>

    </div>
    <!-- /.projects-holder-2 -->
    </div>
    <!-- /.inner-content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>