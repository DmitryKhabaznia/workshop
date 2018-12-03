<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="order" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

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
            <div class="contact-form">
                <div class="box-content col-md-12">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="contact-form-inner">
                                <form method="post" action="order" name="contactform" id="checkout">
                                    <p>
                                        <h3 class="contact-title"><fmt:message key='order.confirm.paymentType'/>:</h3>
                                        <c:forEach var="paymentType" items="${requestScope.paymentTypes}">
                                            <label class="radio">
                                                ${paymentType.name}
                                                <input type="radio" value="${paymentType.name}" form="checkout" ${paymentType.name eq 'cash' ? 'checked' : ''} name="paymentType">
                                                <span class="checkround"></span>
                                            </label>
                                        </c:forEach>
                                    </p>
                                    <div id="cartInput" class="d-none">
                                        <p>
                                            <label for="card"><fmt:message key='order.confirm.card'/> :</label>
                                            <input name="card" type="text" form="checkout" pattern="[0-9]{16}">
                                        </p>
                                        <p>
                                            <label for="date"><fmt:message key='order.confirm.date'/> :</label>
                                            <input class="cardDate" form="checkout" name="date" type="date">
                                        </p>
                                        <p>
                                            <label for="cvv">CVV :</label>
                                            <input name="cvv" form="checkout" type="password" pattern="[0-9]{3}">
                                        </p>
                                    </div>
                                    <p>
                                        <h3 class="contact-title"><fmt:message key='order.confirm.shippingType'/>:</h3>
                                        <c:forEach var="shippingType" items="${requestScope.shippingTypes}">
                                            <label class="radio">${shippingType.name}
                                                <input type="radio" value="${shippingType.name}" form="checkout" ${shippingType.name eq "pick up" ? 'checked' : ''} name="deliveryType">
                                                <span class="checkround"></span>
                                            </label>
                                        </c:forEach>
                                    </p>
                                    <div id="deliveryInput" class="d-none">
                                        <p>
                                            <label for="deliveryAddress"><fmt:message key='order.confirm.deliveryAddress'/> :</label>
                                            <textarea name="deliveryAddress" form="checkout"></textarea>
                                        </p>
                                    </div>
                                        <div class="center-block invalid"><p class="h6 text-center">${sessionScope.error}</p></div>
                                        <c:remove var="error" scope="session"/><br>
                                    <input type="submit" class="mainBtn" id="submit" value="<fmt:message key='order.confirm'/>" />
                                </form>
                            </div>
                            <!-- /.contact-form-inner -->
                            <div id="message"></div>
                        </div>
                        <!-- /.col-md-7 -->

                        <div class="col-md-5">
                            <table id="cart" class="table table-hover table-condensed">
                                <thead>
                                    <tr>
                                        <th style="width:80%"><fmt:message key='order.product'/></th>
                                        <th style="width:20%"><fmt:message key='order.quantity'/></th>
                                    </tr>
                                </thead>
                                <c:forEach var="entry" items="${sessionScope.SHOPPING_CART.getAllProducts()}">
                                    <tbody>
                                        <tr>
                                            <td data-th="Product">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <img class="order-element img-thumbnail" src="images/products/${entry.key.imageName}.jpg" alt="">
                                                    </div>
                                                    <div class="col-md-8">
                                                        <h4 class="nomargin">${entry.key.name}</h4>
                                                        <p>${entry.key.description}</p>
                                                    </div>
                                                </div>
                                            </td>
                                            <td data-th="Quantity">${entry.value}</td>
                                        </tr>
                                    </tbody>
                                </c:forEach>

                                <tfoot>
                                    <tr class="visible-xs">
                                        <td class="text-center"></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <form id="back" action="shoppingCart" method="get"></form>
                                                    <button class="addBtn" form="back">
                                                        <i class="fa fa-arrow-left"></i>
                                                        <fmt:message key='order.back'/>
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="hidden-xs text-center">
                                            <strong class="totalSum">$${not empty sessionScope.SHOPPING_CART.getTotalSum()? sessionScope.SHOPPING_CART.getTotalSum()
                                                : 0}</strong>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.box-content -->
            </div>
            <!-- /.contact-form -->
        </div>
        <!-- /.inner-content -->
    </div>
    <!-- /.content-wrapper -->

    </div>
    <!-- /.projects-holder-2 -->
    </div>
    <!-- /.inner-content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>