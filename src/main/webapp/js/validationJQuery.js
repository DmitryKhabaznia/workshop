var first_name = "#first_name";
var second_name = "#second_name";
var login = "#login";
var email = "#email";
var password = "#password";
var password_confirm = "#password_confirm";
var gender = "input:radio[name='gender']";
var captcha = "#captcha";

var name_reg_ex = /^[A-Za-z\u0410-\u044F]{3,15}$/;
var captcha_reg_ex = /^[A-Za-z0-9]{6}$/
var login_reg_ex = /^[A-Za-z0-9_]{3,15}$/;
var email_reg_ex = /^[a-zA-Z0-9.%-]+@[a-zA-Z0-9.%-]+\.[a-zA-Z]{2,6}$/;
var password_reg_ex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\.@#$%^&+=])(?=\S+$).{8,}$/;

$(document).ready(function () {
	$("#register").on('submit', function () {
		var flag = true;
		flag = validateField(name_reg_ex, first_name) && flag;
		flag = validateField(name_reg_ex, second_name) && flag;
		flag = validateField(login_reg_ex, login) && flag;
		flag = validateField(email_reg_ex, email) && flag;
		flag = validateField(password_reg_ex, password) && flag;
		flag = validateField(password_reg_ex, password_confirm) && flag;
		flag = validateGenderField() && flag;
		flag = validateField(captcha_reg_ex, captcha) && flag;
		flag = isPasswordsEqual() && flag;
		return flag;
	});

	function validateField(pattern, val) {
		var elem = $(val);
		var elem_error = $(val + '_error');
		if (!isFieldEmpty(elem, elem_error) && isValidField(pattern, elem, elem_error)) {
			setValid(val);
			return true;
		} else {
			setInvalid(val);
			return false;
		}

	};

	function isValidField(pattern, elem, elem_error) {
		if (!pattern.test(elem.val())) {
			elem_error.text('Invalid input.');
			return false;
		} else {
			elem_error.text('');
			return true;
		}
	};

	function isFieldEmpty(elem, elem_error) {
		if (elem.val() == "") {
			elem_error.text('This field cannot be empty.');
			return true;
		} else {
			elem_error.text('');
			return false;
		}
	};

	function isPasswordsEqual() {
		if (!($(password).val() === $(password_confirm).val())) {
			setInvalid(password);
			setInvalid(password_confirm);
			$('#equals_error').addClass("invalid");
			$('#equals_error').text("Passwords should be equal.");
			return false;
		} else {
			$('#equals_error').text("");
		}
		return true;
	};

	function validateGenderField() {
		var elem = $(gender);
		var elem_error = $('#gender_error');
		var elem_div = $('#gender_div');
		if (elem.is(':checked')) {
			elem_error.text("");
			elem_div.addClass("valid");
			return true;
		} else {
			elem_error.text("Field should be checked.");
			elem_div.addClass("invalid");
			return false;
		}
	}

	function setValid(val) {
		var elem = $(val);
		var elem_div = $(val + '_div');
		elem.removeClass("invalidInput");
		elem_div.removeClass("invalid");
		elem.addClass("validInput");
		elem_div.addClass("valid");
	}

	function setInvalid(val) {
		var elem = $(val);
		var elem_div = $(val + '_div');
		elem.removeClass("validInput");
		elem_div.removeClass("valid");
		elem.addClass("invalidInput");
		elem_div.addClass("invalid");
	}

});


