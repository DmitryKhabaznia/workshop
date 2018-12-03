<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="products" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="content-wrapper">
        <div class="col-md-12">
            <div class="section-header row justify-content-md-center">
                <div class="col-md-2 header-margin">
                    <h2><fmt:message key='products.filters'/></h2>
                    <span><fmt:message key='products.filters'/></span>
                </div>
                <div class="col-md-5 header-margin">
                    <h2><fmt:message key='common.products'/></h2>
                    <span><fmt:message key='common.products'/></span>
                </div>
                <div class="col-md-2">
                    <div class="container cart-wrapper">
                        <button class="cartBtn" form="cart">
                            <i class="fa fa-shopping-cart fa-lg"></i>
                            <span class="items cartCount">${not empty sessionScope.SHOPPING_CART.getCount()? sessionScope.SHOPPING_CART.getCount() : 0}</span>
                            <span class="items">|</span>
                            <span class="items"><fmt:message key='products.total'/>:</span>
                            <span class="items cartTotal">$${not empty sessionScope.SHOPPING_CART.getTotalSum()? sessionScope.SHOPPING_CART.getTotalSum() : 0}</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.section-header -->
        <div class="col-md-12">
            <div class="col-md-2 offset-md-1">
                <form id="sorting" action="products" method="GET">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.countsOfProducts'/></p>
                            <select class="form-control custom-select" name="numberOfProducts" form="sorting">
                                <option value="3" ${requestScope.formBean.productCountOnPage eq 3? 'selected' : ''}>3 at page</option>
                                <option value="6" ${requestScope.formBean.productCountOnPage eq 6? 'selected' : ''}>6 at page</option>
                                <option value="9" ${requestScope.formBean.productCountOnPage eq 9? 'selected' : ''}>9 at page</option>
                            </select>
                        </li>
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.sorting'/></p>
                            <select class="form-control custom-select" name="sortingType" form="sorting">
                                <option value="name_asc" ${requestScope.formBean.sortingType eq "name_asc"? 'selected' : ''}>Name: low to high</option>
                                <option value="name_desc" ${requestScope.formBean.sortingType eq "name_desc"? 'selected' : ''}>Name: high to low</option>
                                <option value="price_asc" ${requestScope.formBean.sortingType eq "price_asc"? 'selected' : ''}>Price: low to high</option>
                                <option value="price_desc" ${requestScope.formBean.sortingType eq "price_desc"? 'selected' : ''}>Price: high to low</option>
                            </select>
                        </li>
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.manufacturerUP'/></p>
                            <select class="form-control" name="manufacturer" form="sorting">
                                <option value="-1" class="f-label">
                                    <span class="f-label"> </span>
                                </option>
                                <c:forEach var="manufacturer" items="${requestScope.manufacturers}">
                                    <option value="${manufacturer.id}" class="f-label" ${requestScope.formBean.manufacturer eq manufacturer.id? 'selected' :
                                        ''}>
                                        <span class="f-label">${manufacturer.name}</span>
                                    </option>
                                </c:forEach>
                            </select>
                        </li>
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.categoryUP'/></p>
                            <c:forEach var="category" items="${requestScope.categories}">
                                <c:set var="containsInParam" value="false" />
                                <c:forEach var="categoryInParam" items="${requestScope.formBean.categories}">
                                    <c:if test="${categoryInParam eq category.id}">
                                        <c:set var="containsInParam" value="true" />
                                    </c:if>
                                </c:forEach>
                                <div class="form-check">
                                    <label class="form-check-label">
                                        <input type="checkbox" name="category" value="${category.id}" class="f-label" form="sorting" ${containsInParam ?
                                            'checked' : ''}>
                                        <span class="f-label"> ${category.name}</span>
                                    </label>
                                </div>
                            </c:forEach>
                        </li>
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.priceInterval'/></p>
                            <div class="row">
                                <div class="col-8 col-sm-3">
                                    <label for="min" class="f-label">Min</label>
                                    <input id="min" type="text" name="minPrice" size="4" form="sorting" value="${requestScope.formBean.minPrice > 0 ? requestScope.formBean.minPrice : ''}">
                                </div>
                                <div class="col-8 col-sm-3 offset-md-1">
                                    <label for="max" class="f-label">Max</label>
                                    <input id="max" type="text" name="maxPrice" size="4" form="sorting" value="${requestScope.formBean.maxPrice > 0 ? requestScope.formBean.maxPrice : ''}">
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <p class="filter-label"><fmt:message key='products.specifiedName'/></p>
                            <input type="text" name="name" class="nameInput" form="sorting" value="${not empty requestScope.formBean.nameInput ? requestScope.formBean.nameInput : ''}">
                        </li>
                        <li class="list-group-item">
                            <input type="submit" class="applyBtn" value="<fmt:message key='products.apply'/>" form="sorting">
                        </li>
                    </ul>
                </form>
            </div>
            <!-- /.row -->
            <div class="col-md-8 projects-holder">
                <c:choose>
                    <c:when test="${not empty requestScope.products}">
                        <div class="row">
                            <c:forEach var="product" items="${requestScope.products}">
                                <div class="col-md-4 project-item mix table">
                                    <div class="box-content project-detail">
                                        <div class="row">
                                            <div class="col-md-4 price-margin">
                                                <h2><fmt:message key='products.price'/>: ${product.price}</h2>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="number" class="form-control text-center productCount" value="1" min="1">
                                            </div>
                                            <div class="col-md-4">
                                                <button class="addBtn addToCart" productId="${product.id}">
                                                <i class="fa fa-plus"></i> <fmt:message key='products.add'/></button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="project-thumb">
                                        <img src="images/products/${product.imageName}.jpg" alt="">
                                        <div class="overlay-b">
                                            <div class="overlay-inner">
                                                <a href="images/products/${product.imageName}.jpg" class="fancybox fa fa-expand" title="${product.name}"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-content project-detail">
                                        <h2>
                                            <a href="product-details.html">${product.name}</a>
                                        </h2>
                                        <p>${product.description}</p>
                                        <p>
                                            <em><fmt:message key='products.manufacturer'/>:</em> ${product.manufacturer.name}</p>
                                        <p>
                                            <em><fmt:message key='products.category'/>:</em> ${product.category.name}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <ct:pagination filterBean="${requestScope.formBean}" lastPage="${requestScope.lastPage}" />
                        <br>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center error-page">
                            <h1>Ooops...</h1>
                            <span>There no products that met this filters.</span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
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