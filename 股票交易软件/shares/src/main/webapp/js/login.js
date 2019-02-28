$(function(){
	/*背景全屏*/
	bgimg();
		
	/*div居中*/
	landCenter(".login");
	landCenter(".register");
	landCenter(".find-password");
	
	/*inpt框内提示*/
	promptInpt()
	
	login.init();
	//regist.submit();
	login.submit();
	//findPassword.submit();
});

//var regist = regist || {};
var login = login || {};
//var findPassword = findPassword || {};


//登陆提交
login.submit = function(){
	// 点击注册
	$('#btn-login').click(function(){
		login._proc();
	});
	
	// 按回车
	$('#login-form').bind('keyup', function(event) {   
		if (event.keyCode == 13) {
			login._proc();
		}  
	}); 
}

//登陆
login._proc = function() {
	var params = {};
	params.username  = $('.login-email').val();
	params.password    = $('.login-pwd').val();
	//params.wxid   = $('.login-wxid').val();
	params.code_id = 'login';
	
	
	/*if (!params.wxid) {
		login.showError('请输入微信号');
		$('.login-email').focus();
		return false;
	}*/
	if (!params.username) {
		login.showError('请输入用户名');
		$('.login-email').focus();
		return false;
	}
	if (!params.password) {
		login.showError('请输入密码');
		$('.login-pwd').focus();
		return false;
	}
	/*if (!params.code) {
		login.showError('请输入验证码');
		$('.login-code').focus();
		return false;
	}*/
	
	login.hideError();
	
	// 提交数据
	$.ajax({
		url : 'isw/doLogin.action',
		type: 'post',
		dataType: 'json',
		data: params,
		async: false,
		success: function(json) {
			if (json.status == 1) {
				// 清空表单
				location.reload();
			} else {
				login.showError(json.info);
			}
		}
	});
}

login.showError = function(msg){
	$('#login-form .prompt').show();
	$('#login-form .prompt-error').html(msg);
}

login.hideError = function() {
	$('#login-form .prompt').hide();
	$('#login-form .prompt-error').html('');
}

//防止后退到该页面时，输入框内有数据，但仍显示提示的问题。
login.init = function(){
	$('.inptText').each(function(index, value){
		if ($(this).val() == '') {
			$(this).prev('.promptInpt').children('.promptTxt').show();
		} else {
			$(this).prev('.promptInpt').children('.promptTxt').hide();
		}
	});
}


