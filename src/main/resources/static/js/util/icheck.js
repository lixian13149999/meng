var c = new Object();




/**
 * 检查密码的合法性
 * @param object     当前页面用来存错误消息的对象
 * @param pwd        用来检测的密码
 * @param name       用来存放错误信息的具体位置
 */
 c.checkPassword = function (object,pwd,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var userpwdIsOk = false;

    /*
     * 密码不能为空
     */
     var isNullOk = !v.isNull(pwd);
     if (!isNullOk) {
     	object['errorMsg'][name] = "密码不能为空";
     	return false;
     }

    /*
     * 密码不能包含特殊字符
     */
     var hasSpecialCharOk = !v.hasSpecialChar(pwd);
     if (!hasSpecialCharOk) {
     	object['errorMsg'][name]= "密码不能包含特殊字符";
     	return false;
     }

    /*
     * 密码不能少于6位
     */
     var shortThanOk = !v.shortThan(pwd, 6);
     if (!shortThanOk) {
     	object['errorMsg'][name] = "密码不能小于六位";
     	return false;
     }

    /*
     * 密码不能大于32位
     */
     var longThanOk = !v.longThan(pwd, 32);
     if (!longThanOk) {
     	object['errorMsg'][name] = "密码不能大于32位";
     	return false;
     }


    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && hasSpecialCharOk && shortThanOk && longThanOk) {
     	userpwdIsOk = true;
     	object['errorMsg'][name] = "密码验证通过";
     }

     return userpwdIsOk;
 }


 c.checkMyPassword = function(form,object,pwd,name) {
     var pwdOk = c.checkPassword(object,pwd,name);
     if(!pwdOk) {
      v.toError(form,name,object["errorMsg"][name]);
  } else {
      v.toSuccess(form,name);
  }
  return pwdOk;
}





/**
 *   检查两次输入的密码是否相同
 *   @param form,object,pwd1,pwd2,name
 */
 c.checkAllPwd = function(form,object,pwd1,pwd2,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var allPwdIsOk = false;

    // 判断两次输入的密码是否一致
    if(pwd1 != pwd2) {
        object['errorMsg'][name] = "两次输入的密码不一致";
        return false;
    }
    
    // 判断两次输入的密码是否一致
    if(pwd1 == pwd2) {
        object['errorMsg'][name] = "密码验证通过";
        allPwdIsOk = true;
    }

    return allPwdIsOk;
}

c.checkMyAllPwd = function(form,object,pwd1,pwd2,name) {
    var pwdOk = c.checkAllPwd(form,object,pwd1,pwd2,name);
    if(!pwdOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return pwdOk;
}


/*
 *   检测值不能相同
 */
 c.checkEqual = function(form,object,pwd1,pwd2,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var allPwdIsOk = false;

    // 判断两次输入是否一致
    if(pwd1 == pwd2) {
        object['errorMsg'][name] = "不能与值相同";
        return false;
    }
    
    // 判断两次输入是否一致
    if(pwd1 != pwd2) {
        object['errorMsg'][name] = "通过";
        allPwdIsOk = true;
    }

    return allPwdIsOk;
}

c.checkMyEqual = function(form,object,pwd1,pwd2,name) {
    var pwdOk = c.checkEqual(form,object,pwd1,pwd2,name);
    if(!pwdOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return pwdOk;
}



/**
 * 检查手机号的合法性
 * @param form,object,phone,name
 */
 c.checkPhone = function (form,object,phone,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var phoneIsOk = false;
    /*
     * 手机号不能为空
     */
     var isNullOk = !v.isNull(phone);
     if (!isNullOk) {
        object['errorMsg'][name] = "手机号不能为空";
        return false;
    }

    /*
     * 必须是手机号
     */
     var isPhoneOk = v.isMobile(phone);
     if (!isPhoneOk) {
        object['errorMsg'][name] = "请填写正确的手机号";
    }


    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && isPhoneOk) {
        phoneIsOk = true;
        object['errorMsg'][name] = "";
    }

    return phoneIsOk;
}

c.checkMyPhone = function(form,object,phone,name) {
    var phoneOk = c.checkPhone(form,object,phone,name);
    if(!phoneOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return phoneOk;
}



 /**
 * 检查邮箱的合法性
 * @param form,object,email,name
 */
 c.checkEmail = function(form,object,email,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var emailIsOk = false;
    /*
     * 邮箱不能为空
     */
     var isNullOk = !v.isNull(email);
     if (!isNullOk) {
        object['errorMsg'][name] = "邮箱不能为空";
        return false;
    }

    /*
     * 必须是邮箱
     */
     var isEmailOk = v.isEmail(email);
     if (!isEmailOk) {
        object['errorMsg'][name] = "请填写正确的邮箱";
    }


    /*
     * 判断当前邮箱是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && isEmailOk) {
        emailIsOk = true;
        object['errorMsg'][name] = "";
    }

    return emailIsOk;
}

c.checkMyEmail = function(form,object,email,name) {
    var emailOk = c.checkEmail(form,object,email,name);
    if(!emailOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return emailOk;
}




/**
 * 检查用户名的合法性
 * @param form,object,username,name
 */
 c.checkUsername = function (form,object,username,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var usernameIsOk = false;
    /*
     * 用户名不能为空
     */
     var isNullOk = !v.isNull(username);
     if (!isNullOk) {
        object['errorMsg'][name] = "用户名不能为空";
        return false;
    }

    /*
     * 用户名不能包含特殊字符
     */
     var hasSpecialCharOk = !v.hasSpecialChar(username);
     if (!hasSpecialCharOk) {
        object['errorMsg'][name] = "用户名不能包含特殊字符";
        return false;
    }

    /*
     * 用户名不能少于6位
     */
     var shortThanOk = !v.shortThan(username, 6);
     if (!shortThanOk) {
        object['errorMsg'][name] = "用户名不能小于六位";
        return false;
    }

    /*
     * 用户名不能大于32位
     */
     var longThanOk = !v.longThan(username, 32);
     if (!longThanOk) {
        object['errorMsg'][name]= "用户名不能大于32位";
        return false;
    }


    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && hasSpecialCharOk && shortThanOk && longThanOk) {
        usernameIsOk = true;
        object['errorMsg'][name] = "";
    }

    return usernameIsOk;
}

c.checkMyUsername = function(form,object,username,name) {
    var usernameOk = c.checkUsername(form,object,username,name);
    if(!usernameOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return usernameOk;
}



/**
 * 检查验证码的合法性
 * @param form,object,code,name
 */

 c.checkCode = function(form,object,code,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var codeIsOk = false;

    /*
     * 验证码不能为空
     */
     var isNullOk = !v.isNull(code);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

    /*
     * 验证码必须为6位数字
     */
     var isNumOk = v.isCode(code);
     if (!isNumOk) {
        object['errorMsg'][name] = "必须为6位数字";
        return false;
    }

    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && isNumOk) {
        codeIsOk = true;
        object['errorMsg'][name] = "";
    }

    return codeIsOk;
}

c.checkMyCode = function(form,object,code,name) {
    var codeOk = c.checkCode(form,object,code,name);
    if(!codeOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return codeOk;
}


 /*
  *    获取验证码的倒计时
  */
  c.wait = 60;
  c.time = function(o) {
    if (c.wait == 0) {
        o.removeAttribute("disabled");
        o.value = "获取验证码";
        c.wait = 60;

    } else {
        o.setAttribute("disabled", true);
        o.value = "重新发送(" + c.wait + ")";
        c.wait--;
        setTimeout(function () {
            c.time(o)
        }, 1000)
    }
}






/**
 * 检查名称的合法性
 * @param form,object,des,name
 */
 c.checkDes = function (form,object,des,name,min,max) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var desIsOk = false;
    /*
     * 不能为空
     */
     var isNullOk = !v.isNull(des);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

    /*
     * 不能少于min位
     */
     var shortThanOk = !v.shortThan(des,min);
     if (!shortThanOk) {
        object['errorMsg'][name] = "不能小于"+min+"位";
        return false;
    }

    /*
     * 不能大于max位
     */
     var longThanOk = !v.longThan(des,max);
     if (!longThanOk) {
        object['errorMsg'][name] = "不能大于"+max+"位";
        return false;
    }


    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && shortThanOk && longThanOk) {
        desIsOk = true;
        object['errorMsg'][name] = "";
    }

    return desIsOk;
}

c.checkMyDes = function(form,object,des,name,min,max) {
    var desOk = c.checkDes(form,object,des,name,min,max);
    if(!desOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return desOk;
}



 /**
 * 检查http的合法性
 * @param form,object,http,name
 */

 c.checkHttp = function (form,object,http,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值

    var httpOk = false;
    /*
     * 不能为空
     */
     var isNullOk = !v.isNull(http);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

     /*
      * 必须是http(https)
      */
      var isHttpOk = v.isHttp(http);
      if(!isHttpOk) {
        object['errorMsg'][name] = "必须是http(s)格式";
        return false
    }
    

    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if ( isNullOk && isHttpOk ) {
        httpOk = true;
        object['errorMsg'][name] = "";
    }

    return httpOk;
}
c.checkMyHttp = function(form,object,http,name) {
    var httpOk = c.checkHttp(form,object,http,name);
    if(!httpOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return httpOk;
}




 /**
 * 检查版本号的合法性
 * @param form,object,version,name
 */

 c.checkVersion = function (form,object,version,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值

    var versionOk = false;
    /*
     * 不能为空
     */
     var isNullOk = !v.isNull(version);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

     /*
      * 必须是版本号
      */
      var isVersionOk =v.isVersion(version);
      if(!isVersionOk) {
        object['errorMsg'][name] = "请输入正确的版本号";
        return false
    }
    

    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if ( isNullOk && isVersionOk ) {
        versionOk = true;
        object['errorMsg'][name] = "";
    }

    return versionOk;
}
c.checkMyVersion = function(form,object,version,name) {
    var versionOk = c.checkVersion(form,object,version,name);
    if(!versionOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return versionOk;
}




 /**
 * 检查数量或者金额(整数)的合法性
 * @param form,object,num,name,min,max
 * 必须小于min 必须大于max
 * 如果只判断大于多少给min Infinity
 * 如果只判断小于多少给max -Infinity
 */
 
 c.checkNum = function(form,object,num,name,min,max) {
    var numOk = false;
    /*
     * 不能为空
     */
     var isNullOk = !v.isNull(num);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

     /*
     * 必须是整数
     */
     var isWholeNumOk = v.isWholeNumber(num);
     if(!isWholeNumOk) {
        object['errorMsg'][name] = "输入不合法";
        return false;
    }

     /*
     * 必须少于或等于max
     */
     var lessThanOk = ( num < max || num == max ) ?  true : false;
     if (!lessThanOk) {
        object['errorMsg'][name] = "必须小于或等于" + max;
        return false;
    }


     /*
     * 必须大于或等于min
     */
     var moreThanOk = ( num > min || num == min ) ? true:false;
     if (!moreThanOk) {
        object['errorMsg'][name] = "必须大于或等于" + min;
        return false;
    }


    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if ( isNullOk && isWholeNumOk && lessThanOk && moreThanOk ) {
        numOk = true;
        object['errorMsg'][name] = "";
    }

    return numOk;
}

c.checkMyNum = function(form,object,num,name,min,max) {
    var numOk = c.checkNum(form,object,num,name,min,max);
    if(!numOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return numOk;
}



/*
 *  检测正数范围的合法性(正数) 
 *  param form,object,positive,name,min,max
 *  只判断小于多少min给-Infinity
 *  只判断大于多少max给Infinity
 */
 c.checkPositive = function(form,object,positive,name,min,max,text) {
    var flag = false;
    /*
     * 不能为空
     */
     var isNullOk = !v.isNull(positive);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

     /*
      * 必须是非负浮点数
      */
      var numOk = v.isPostiveNumber(positive);
      if(!numOk) {
        object['errorMsg'][name] = "输入不合法";
        return false;
    }

    /*
     *  必须是合法金额
     */
      var moneyOk = v.isMoney(positive);
      if(!moneyOk) {
        object['errorMsg'][name] = '小数点最多只能两位';
        return false;
      }


     /*
     * 必须少于或等于max
     */
     var lessThanOk = ( positive < max || positive == max ) ?  true : false;
     if (!lessThanOk) {
        object['errorMsg'][name] = text || "必须小于或等于" + max;
        return false;
    }


     /*
     * 必须大于或等于min
     */
     var moreThanOk = ( positive > min || positive == min ) ? true:false;
     if (!moreThanOk) {
        object['errorMsg'][name] = "必须大于或等于" + min;
        return false;
    }

    /*
     *  所有条件满足返回true
     */
     if( isNullOk && numOk && lessThanOk && moreThanOk && moneyOk) {
        object['errorMsg'][name] = '';
        flag = true;
    }
    return flag;

}

c.checkMyPositive = function(form,object,positive,name,min,max,text) {
    var flag = c.checkPositive(form,object,positive,name,min,max,text);
    if(!flag) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return flag;
}




/*
 * 验证图片的大小和类型
 */

 c.checkImg = function(form,object,img,name) {
    var flag = false;

    /*
     *   检测图片是否为空 
     */
    var isNullOk = !v.isNull(img);
    if (!isNullOk) {
        object['errorMsg'][name] = "请上传图片";
        return false;
    }
    
    /*
     *   检测图片类型是否正确
     */
    var imgTypeOk = false;
    var extStart = img.lastIndexOf(".");
    var ext = img.substring(extStart,img.length).toUpperCase();
    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG") {
        object['errorMsg'][name] = "图片限于bmp,png,gif,jpeg,jpg格式";
        return false;
    } else {
        imgTypeOk = true;
    }

    /*
     *   全部通过flag为true
     */
     if( isNullOk && imgTypeOk ) {
        object['errorMsg'][name] = '';
        flag = true;
    }
    return flag;
}

c.checkMyImg = function(form,object,img,name) {
    var flag = c.checkImg(form,object,img,name);
    if(!flag) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return flag;
}



/*
 *  检查身份证号的合法性
 */
c.checkIdCard = function(form,object,idCard,name) {
    var flag = false;
    /*
     *   检测是否为空 
     */
    var isNullOk = !v.isNull(idCard);
    if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }
    
    /*
     *   检测是否为身份证号码
     */
    var isIdCardOk = v.isIdCard(idCard);
    if(!isIdCardOk) {
        object['errorMsg'][name] = "身份证号不合法";
        return false;
    }

    /*
     *   全部通过flag为true
     */
     if( isNullOk && isIdCardOk ) {
        object['errorMsg'][name] = '';
        flag = true;
    }
    return flag;
}

c.checkMyIdCard = function(form,object,idCard,name) {
    var flag = c.checkIdCard(form,object,idCard,name);
    if(!flag) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return flag;
}




/**
 * 检查电话号码的合法性
 * @param form,object,tel,name
 */
 c.checkTel = function (form,object,tel,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var phoneIsOk = false;
    /*
     * 手机号不能为空
     */
     var isNullOk = !v.isNull(tel);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

    /*
     * 必须是手机号
     */
     var isPhoneOk = v.isTel(tel);
     if (!isPhoneOk) {
        object['errorMsg'][name] = "请填写正确的电话号码";
    }
    
    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && isPhoneOk) {
        phoneIsOk = true;
        object['errorMsg'][name] = "";
    }

    return phoneIsOk;
}

c.checkMyTel = function(form,object,tel,name) {
    var phoneOk = c.checkTel(form,object,tel,name);
    if(!phoneOk) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return phoneOk;
}




/**
 * 检查统一社会信用代码的合法性
 * @param form,object,code,name
 */
 c.checkCreditCode = function (form,object,code,name) {
    //定义一个全局的检测值,用于存储当前方法的最终返回值
    var flag = false;
    /*
     * 编码不能为空
     */
     var isNullOk = !v.isNull(code);
     if (!isNullOk) {
        object['errorMsg'][name] = "不能为空";
        return false;
    }

    /*
     * 必须是正确的编码
     */
     var isCodeOk = v.isCreditCode(code);
     if (!isCodeOk) {
        object['errorMsg'][name] = "请填写正确的编码";
    }
    
    /*
     * 判断当前用户名是否满足所有验证条件
     * 是  ==>  true
     * 否  ==>  false
     */
     if (isNullOk && isCodeOk) {
        flag = true;
        object['errorMsg'][name] = "";
    }

    return flag;
}

c.checkMyCreditCode = function(form,object,code,name) {
    var flag = c.checkCreditCode(form,object,code,name);
    if(!flag) {
        v.toError(form,name,object['errorMsg'][name]);
    } else {
        v.toSuccess(form,name);
    }
    return flag;
}