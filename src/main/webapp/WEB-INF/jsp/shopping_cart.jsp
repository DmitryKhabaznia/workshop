<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="cart" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="content-wrapper">
        <div class="col-md-12">
            <div class="content-wrapper">
                <div class="inner-container container">
                    <div class="row">
                        <div class="section-header col-md-12 row justify-content-md-center">
                            <div class="col-md-10 header-margin">
                                <h2><fmt:message key='order.yourCart'/></h2>
                                <span><fmt:message key='order.chooseQuantity'/></span>
                            </div>
                        </div>
                        <!-- /.section-header -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <form id="back" action="products" method="get"></form>
                        <div class="col-md-12 service-item">
                            <!-- /.service-item -->
                            <c:choose>
                                <c:when test="${not empty sessionScope.SHOPPING_CART.getAllProducts()}">
                                    <div class="box-content productsTable">
                                        <table id="cart" class="table table-hover table-condensed">
                                            <thead>
                                                <tr>
                                                    <th style="width:50%"><fmt:message key='order.product'/></th>
                                                    <th style="width:10%"><fmt:message key='order.price'/></th>
                                                    <th style="width:15%"><fmt:message key='order.quantity'/></th>
                                                    <th style="width:15%" class="text-center"><fmt:message key='order.subtotal'/></th>
                                                    <th style="width:10%"></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="entry" items="${sessionScope.SHOPPING_CART.getAllProducts()}">
                                                    <div class="productItem">
                                                        <tr>
                                                            <td data-th="Product">
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <img class="cart-element img-thumbnail" src="images/products/${entry.key.imageName}.jpg" alt="">
                                                                    </div>
                                                                    <div class="col-md-8">
                                                                        <h4 class="nomargin">${entry.key.name}</h4>
                                                                        <p>${entry.key.description}</p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td data-th="Price" class="productPrice">${entry.key.price}</td>
                                                            <td data-th="Quantity">
                                                                <input type="number" min="1" class="form-control text-center countInput" value="${entry.value}" productId="${entry.key.id}">
                                                            </td>
                                                            <td data-th="Subtotal" class="text-center subtotal">${entry.value * entry.key.price}</td>
                                                            <td class="actions" data-th="">
                                                                <button class="deleteBtn deleteItem" productId="${entry.key.id}">
                                                                    <i class="fa fa-trash-o"></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </div>
                                                </c:forEach>
                                                <tr>
                                                    <td></td><td></td><td></td>
                                                    <td colspan="2">
                                                        <button class="deleteBtn deleteAll" productId="-1">
                                                            <span><fmt:message key='order.removeAll'/></span>
                                                            <i class="fa fa-trash-o"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>

                                            <tfoot>
                                                <tr class="visible-xs">
                                                    <td class="text-center">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <button class="addBtn" form="back">
                                                                    <i class="fa fa-arrow-left"></i>
                                                                    <fmt:message key='order.back'/>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="hidden-xs">
                                                        <strong><fmt:message key='order.total'/>: </strong>
                                                    </td>
                                                    <td colspan="1" class="hidden-xs text-center cartCount">
                                                        <strong>${not empty sessionScope.SHOPPING_CART.getCount()? sessionScope.SHOPPING_CART.getCount()
                                                            : 0}</strong>
                                                    </td>
                                                    <td class="hidden-xs text-center cartTotal">
                                                        <strong class="totalSum">$${not empty sessionScope.SHOPPING_CART.getTotalSum()? sessionScope.SHOPPING_CART.getTotalSum()
                                                            : 0}</strong>
                                                    </td>
                                                    <td>
                                                        <form id="checkout" action="order" method="get"></form>
                                                        <button class="addBtn" form="checkout"><fmt:message key='order.next'/></button>
                                                    </td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center error-page noProducts">
                                        <h1><fmt:message key='order.message'/></h1><span><fmt:message key='order.errorMessage'/></span>
                                        <button class="addBtn" form="back"><i class="fa fa-arrow-left"></i> <fmt:message key='order.back'/></button>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="text-center error-page noProducts d-none">
                                <h1><fmt:message key='order.message'/></h1><span><fmt:message key='order.errorMessage'/></span>
                                <button class="addBtn" form="back"><i class="fa fa-arrow-left"></i> <fmt:message key='order.back'/></button>
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