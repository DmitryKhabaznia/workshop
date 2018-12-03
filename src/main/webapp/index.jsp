<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="index" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="swiper-container">
        <div class="swiper-wrapper">

            <div class="swiper-slide" style="background-image: url(images/slide1.jpg);">
                <div class="overlay-s"></div>
                <div class="slider-caption">
                    <div class="inner-content">
                        <h2>Earth New House Project</h2>
                        <p>Credit goes to
                            <a rel="nofollow" href="http://unsplash.com">Unsplash</a>
                            for photos.</p>
                        <a href="products" class="main-btn white">View Products</a>
                    </div>
                    <!-- /.inner-content -->
                </div>
                <!-- /.slider-caption -->
            </div>
            <!-- /.swier-slide -->

            <div class="swiper-slide" style="background-image: url(images/slide2.jpg);">
                <div class="overlay-s"></div>
                <div class="slider-caption">
                    <div class="inner-content">
                        <h2>Hotel and Residence Concept in Montenegro</h2>
                        <p>We come with new fresh and unique ideas.</p>
                        <a href="products" class="main-btn white">View Products</a>
                    </div>
                    <!-- /.inner-content -->
                </div>
                <!-- /.slider-caption -->
            </div>
            <!-- /.swier-slide -->

            <div class="swiper-slide" style="background-image: url(images/slide3.jpg);">
                <div class="overlay-s"></div>
                <div class="slider-caption">
                    <div class="inner-content">
                        <h2>Natural 3d Architecture Design</h2>
                        <p>Natural concrete is a material which is calm and clean.</p>
                        <a href="products" class="main-btn white">View Products</a>
                    </div>
                    <!-- /.inner-content -->
                </div>
                <!-- /.slider-caption -->
            </div>
            <!-- /.swier-slide -->

        </div>
        <!-- /.swiper-wrapper -->
    </div>
    <!-- /.swiper-container -->

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>