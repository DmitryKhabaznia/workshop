<%@ include file="/WEB-INF/jspf/directive.jspf"%>
<html class="no-js">
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <c:set var="title" value="registration" scope="page" />
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="content-wrapper">
        <div class="inner-container container">
            <div class="row">
                <div class="section-header col-md-12">
                    <h2><fmt:message key='signup'/></h2>
                </div>
                <!-- /.section-header -->
            </div>
            <!-- /.row -->
            <div class="contact-form">
                <div class="box-content col-md-12">
                    <div class="row">
                        <div class="col-md-11">
                            <div class="contact-form-inner text-center">
                                <form method="post" action="registration" name="contactform" id="register" enctype="multipart/form-data">
                                    <div id='first_name_div' class=${not empty sessionScope.errors[ 'first_name_error']? 'invalid' : ''}>
                                        <label for="first_name"><fmt:message key='signup.firstName'/>:</label>
                                        <input name="firstName" value="${sessionScope.formBean.firstName}" class=${not empty sessionScope.errors[
                                            'first_name_error']? 'invalidInput' : ''} type="text" id="first_name">
                                        <div class="text-center invalid" id="first_name_error">${sessionScope.errors['first_name_error']}</div>
                                    </div>
                                    <br>
                                    <div id='second_name_div' class=${not empty sessionScope.errors[ 'second_name_error']? 'invalid' : ''}>
                                        <label for="second_name"><fmt:message key='signup.lastName'/>:</label>
                                        <input name="secondName" value="${sessionScope.formBean.secondName}" class=${not empty sessionScope.errors[
                                            'second_name_error']? 'invalidInput' : ''} type="text" id="second_name">
                                        <div class="text-center invalid" id="second_name_error">${sessionScope.errors['second_name_error']}</div>
                                    </div>
                                    <br>
                                    <div id='login_div' class=${not empty sessionScope.errors[ 'login_error']? 'invalid' : ''}>
                                        <label for="login"><fmt:message key='signup.login'/>:</label>
                                        <input name="login" value="${sessionScope.formBean.login}" class=${not empty sessionScope.errors[ 'login_error']?
                                            'invalidInput' : ''} type="text" id="login">
                                        <div class="text-center invalid" id="login_error">${sessionScope.errors['login_error']}</div>
                                    </div>
                                    <br>
                                    <div id='email_div' class=${not empty sessionScope.errors[ 'email_error']? 'invalid' : ''}>
                                        <label for="email"><fmt:message key='signup.email'/>:</label>
                                        <input name="email" value="${sessionScope.formBean.email}" class=${not empty sessionScope.errors[ 'email_error']?
                                            'invalidInput' : ''} type="text" id="email">
                                        <div class="text-center invalid" id="email_error">${sessionScope.errors['email_error']}</div>
                                    </div>
                                    <br>
                                    <div id='password_div' class=${not empty sessionScope.errors[ 'password_error']? 'invalid' : ''}>
                                        <label for="password"><fmt:message key='signup.password'/>:</label>
                                        <input name="password" class="${not empty sessionScope.errors['password_error']? 'invalidInput' : ''}" type="password" id="password">
                                        <div class="text-center invalid" id="password_error">${sessionScope.errors['password_error']}</div>
                                    </div>
                                    <br>
                                    <div id='password_confirm_div' class=${not empty sessionScope.errors[ 'password_confirm_error']? 'invalid' : ''}>
                                        <label for="password_confirm"><fmt:message key='signup.passwordConfirm'/>:</label>
                                        <input name="passwordConfirm" class="${not empty sessionScope.errors['password_confirm_error']? 'invalidInput' : ''}" type="password"
                                            id="password_confirm">
                                        <div class="text-center invalid" id="password_confirm_error">${sessionScope.errors['password_confirm_error']}</div>
                                    </div>
                                    <div class="text-center invalid" id="equals_error">${sessionScope.errors['equals_error']}</div>
                                    <div id="gender_div" class=${not empty sessionScope.errors[ 'gender_error']? 'invalid' : ''}>
                                        <p></p>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="gender" value="male" id="gender_male" autocomplete="off" ${sessionScope.formBean.gender=='male'
                                                ? 'checked' : ''} ${not empty sessionScope.formBean.gender ? '' : 'checked'}> Male
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="gender" value="female" id="gender_female" autocomplete="off" ${sessionScope.formBean.gender=='female'
                                                ? 'checked' : ''}> Female
                                        </label>
                                    </div>
                                    <p></p>
                                    <div class="text-center invalid" id="gender_error">${sessionScope.errors['gender_error']}</div>
                                    <div id='captcha_div' class=${not empty sessionScope.errors[ 'captcha_error']? 'invalid' : ''}>
                                        <ct:captcha captchaId="${requestScope.captchaId}" />
                                        <label for="captcha"><fmt:message key='signup.enterSymbols'/>:</label>
                                        <input name="captcha" class=${not empty sessionScope.errors[ 'captcha_error']? 'invalidInput' : ''} type="text" id="captcha">
                                        <div class="text-center invalid" id="captcha_error">${sessionScope.errors['captcha_error']}</div>
                                    </div>
                                    <div class="text-center invalid" id="error">${sessionScope.errors['error']}</div>
                                    <br>
                                    <div class="">
                                        <label for="imageName"><fmt:message key='signup.avatar'/>:</label>
                                        <input class="" id="uploadFile" type="file" name="imageName" accept="image/jpeg, image/jpg" required/>
                                    </div>
                                    <br>
                                    <c:remove var="errors" scope="session" />
                                    <c:remove var="formBean" scope="session" />
                                    <input type="submit" class="mainBtn" id="submit" value="<fmt:message key='signup'/>" />
                                </form>
                            </div>
                            <!-- /.contact-form-inner -->
                        </div>
                        <!-- /.col-md-7 -->
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

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>