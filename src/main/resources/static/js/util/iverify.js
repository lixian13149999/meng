/**
 * 自己写的一些验证字段的文件
 * 用于对val值的验证
 */
 var v = new Object();

/*
 * 检测是否为空
 * 为空时返回  true
 * 不为空是返回  false
 */
 v.isNull = function(str) {
	// str = str.trim();
	return str == '' || str == undefined || str == NaN ? true : false;
}

//检测是否有中文
v.hasChain = function(str) {
	var reg = new RegExp("[\u4e00-\u9fa5]");
	console.log(reg.test(str));
	return reg.test(str);
}

//是否大于...
v.longThan = function(str, length) {
	return str.length > length ? true : false;
}

/*
  * 是否小于...
  * 小于时返回  true
  * 不小于时返回  false
  */

  v.shortThan = function(str, length) {
  	return str.length < length ? true : false;
  }

/*
  * 是否包含特殊字符
  * 包含时返回  true
  * 不包含时返回  false
  */
  v.hasSpecialChar = function(str) {
  	var reg = new RegExp("[^a-zA-Z0-9`~!@#$%^&*()\-=_+\{\};:\'\"|/*,.<>?]");
  	return reg.test(str);

  }

// 两个值是否相等
v.isSame = function(str1, str2) {
	return str1 == str2;
}

// 是否是数字
v.isNumber = function(str) {
	var reg = new RegExp("^[0-9]+([.]{1}[0-9]+){0,1}$");
	return reg.test(str);
}

//是否是整数
v.isWholeNumber = function(str) {
	var reg = new RegExp("^[0-9]*$");
	return reg.test(str);
}

//是否是非负浮点数(正浮点数 + 0)
v.isPostiveNumber = function(str) {
	var reg = /^\d+(\.\d+)?$/;
	return reg.test(str);
}

//验证是否是手机号
v.isMobile = function(str) {
	// var reg = new RegExp("/^1\d{10}$/");
	var reg = /^1\d{10}$/;
	// console.log(reg.test(str));
	return reg.test(str);
}

/*
 * 验证是否是邮箱
 * 是邮箱时返回  true
 * 不是邮箱时返回  false
 */
 v.isEmail = function(str) {
	// var reg = new RegExp("^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i");
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	// console.log(reg.test(str));
	return reg.test(str);
}

v.isUrl = function(str) {
	// var reg = new RegExp("^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i");
//	var reg = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
var reg = new RegExp("(\/[a-z\sA-Z]+)+");
	// console.log(reg.test(str));
	return reg.test(str);
}

v.isHttp = function(str) {
	var reg = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	// console.log(reg.test(str));
	return reg.test(str);
}

//验证是否为六位的数字(验证码)
v.isCode = function(str) {
	var reg = /^\d{6}$/;
	return reg.test(str);
}

//验证是否是版本号
v.isVersion = function(str) {
    var reg = /(\d+\.){2}\d+/;
    // console.log(reg.test(str));
    return reg.test(str);
}

//显示错误提示效果
v.toError = function(ele,name,errorMsg) {
	var input = $(ele).find("[name="+name+"]");
	input.nextAll('.tips').text(errorMsg);
	input.parents('.mygroup').addClass('danger');
}

//消除错误提示效果
v.toSuccess = function(ele,name) {
	var input = $(ele).find("[name="+name+"]");
	input.nextAll('.tips').text('');
	input.parents('.mygroup').removeClass('danger');
}


/*
 * 金额至少为3千
 */
 v.isMoreThan = function(str) {
 	var reg = /^\d{0}[3-9]\d{3,}$/;
 	return reg.test(str);
 }


/*
 *  检测时候是身份证的方法
 */

 v.isIdCard = function(str) {
 	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
 	return reg.test(str);
 }


/*
 *  检测手机号或者固定电话
 */
 v.isTel = function(str) {
 	var reg1 = /^0\d{2,3}-?\d{7,8}$/;
 	var reg2 = /^1\d{10}$/;
 	var flag = reg1.test(str) || reg2.test(str);
 	return flag;
 }


 /*
  *  统一社会编码
  */
 v.isCreditCode = function(str) {
 	var reg = /[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}/;
 	return reg.test(str);
 }

 /*
  *  金额
  */
 v.isMoney = function(str) {
 	var reg = /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/;
 	return reg.test(str);
 }