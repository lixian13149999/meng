var iform = new Object();

//用于封装form表单中的数据
//传入一个表单id
//返回一个包装好的数据包
iform.parseForm = function(formId) {

    var data = new Object();

    $("#" + formId + " [name]:hidden").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });
    
    //处理输入框(普通输入框text)
    //筛选在传入的id下,有name属性,类型是text
    $("#" + formId + " [name]:text").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });
    

    //文本域
    //筛选在传入的id下,有name属性,类型是textarea
    $("#" + formId + " textarea").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });


    //处理密码输入框(file)
    //筛选在传入的id下,有name属性,类型是file
    $("#" + formId + " [name]:file").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });



    //处理密码输入框(password)
    //筛选在传入的id下,有name属性,类型是password
    $("#" + formId + " [name]:password").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });

    //处理单选框(radio)
    //筛选在传入的id下,有name属性,类型是radio,状态为已选中
    $("#" + formId + " [name]:radio:checked").each(function(index, item) {
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[item.name] = value;
    });

    //处理复选框(checkbox)
    //筛选在传入的id下,有name属性,类型是checkbox,状态为已选中
    $("#" + formId + " [name]:checkbox:checked").each(function(index, item) {
        //因为复选框的值有多个元素,
        //先查看data中是否有此变量
        if (data[item.name] == undefined) {
            //如果没有,设置此变量为一个数组
            data[item.name] = new Array();
        }
        var value = iform.valAutoParseData(item, item.value);
        //添加值到上一步处理的数组中
        data[item.name].push(value);
    });

    //处理下拉选择框(select)
    //筛选在传入的id下,有name属性,类型是select,状态为已选中
    $("#" + formId + " :selected").each(function(index, item) {
        //获取select的名字
        var seleName = $(item).parent("select").get(0).name;
        var value = iform.valAutoParseData(item, item.value);
        //用元素的名字作为变量名,元素的value作为变量值
        data[seleName] = value;
    });
    return data;
};

//根据传入的元素,和要转换类型的数据,自动转换成相应的数据类型
//ele是含有name和data-val-dataType的元素
//value是其对应的值
iform.valAutoParseData = function(ele, value) {
    var dataType = $(ele).data('val-data-type');
    return iform.parseData(dataType, value);
};

//根据传入的数据类型和数据,把传入的数据转换成对应的数据类型并输出
//dataType是将要转成的数据类型,
//value是源数据
iform.parseData = function(dataType, value) {
    //如果数据类型不存在,直接返回传入的值
    if (dataType == undefined || dataType == null || dataType == '') {
        return value;
    }
    //如果是int,则转成integer类型并输出
    if (dataType == 'int') {
        return parseInt(value);
    }
    //如果是float,则转换成float类型并输出
    if (dataType == 'float') {
        return parseFloat(value);
    }
    //如果是boolean,则转换成boolean类型并输出
    if (dataType == 'boolean') {
        return Boolean(value);
    }
    //如果以上条件均不满足,则直接返回传入值
    return value;
};


//自动填充数据的方法
//根据表单的id和数据集
//注意:存储数据集合的元素中,data的命名需要和name一致,且数据集中的data名的单词用横线隔开,数据元素中name使用驼峰命名
//例如:数据集中<span data-is-show="1" data-id="123123">
//输入数据元素中<input name=isShow>,<input name="id">
//注意:在checkbox中,有多数据,当前只支持以逗号的方式分割数据
iform.pushForm = function(formId, datas) {
    //循环传入的数据集
    for (var key in datas) {
        //根据数据集中的key(对应到输入元素的name),筛选当前id下的name==key的数据元素
        var ele = $("#" + formId + " [name=" + key + "]");
        //如果获取到的元素<=0说明不合法,则不做处理
        if (ele.length > 0) {
        	
            //或数据元素的名称
            var firstEle = ele.get(0);
            var tagName = firstEle.tagName;
            var eleType = firstEle.type;
            //console.log(ele);
            //如果是input
            if (tagName == "INPUT") {
                //如果数据元素的类型是文本或密码
                if (eleType == "text" || eleType == "hidden" || eleType == "password") {
                    //设置值到数据元素中
                    $(firstEle).val(datas[key]);
                    //如果数据元素的类型是单选框
                } else if (eleType == "radio") {
                    //获取data中的value值,用于下方做筛选
                    var value = datas[key];
                    //筛选出下列条件的数据元素并设置成选中状态
                    //1.当前id范围下
                    //2.名称等于data集中的key值,
                    //3.value值等于data集中对应此key的value值,
                    //4.数据元素类型为单选框
                    //5.集合中的第一个
                    $("#" + formId + " [name=" + key + "][value=" + value + "]:radio").get(0).checked = true;
                    //如果数据元素的类型是复选框
                } else if (eleType == "checkbox") {
                	//数字或者 没有,号的情况下只有一个
                	if(!isNaN(datas[key]) || datas[key].indexOf(",")==-1){//只有一个
                			if(datas[key]==1)//做成如果是1 就执行选中，否则就执行不选中
                		  $("#" + formId + " [name=" + key + "][value=" + datas[key] + "]:checkbox").get(0).checked = true;
                	}else{ //获取checkbox的数据集,并以逗号的方式切割成数组
                		 var valuesArr = datas[key].split(",");
                         //循环切割的数组,以处理相关的数据元素
                         for (var i = 0; i < valuesArr.length; i++) {
                             //筛选出下列条件的数据元素并设置成选中状态
                             //1.当前id范围下
                             //2.名称等于data集中的key值,
                             //3.value值等于data集中对应此key的value值,
                             //4.数据元素类型为复选框
                             //5.集合中的第一个
                             $("#" + formId + " [name=" + key + "][value=" + valuesArr[i] + "]:checkbox").get(0).checked = true;
                         }
                	}
                   
                }
                //如果是select
                //暂时不考虑多选的select
            } else if (tagName == "SELECT") {
                var value = datas[key];
                //筛选出下列条件的数据元素并设置成选中状态
                //1.当前id范围下
                //2.名称等于data集中的key值,
                //3.option的值等于data集中key对应的value
                //4.集合中的第一个
                $("#" + formId + " [name=" + key + "] option[value=" + value + "]").get(0).selected = true;


                //如果是文本域
            } else if (tagName == "TEXTAREA") {
                $(firstEle).text(datas[key]);
            } else if (tagName == "P"||tagName == "p") {
                $(firstEle).html(datas[key]);
            } 
        }
    }
};

//用于清除form表单中原有数据的方法
iform.cleanForm = function(formId) {
    //处理输入框(普通输入框text)
    //筛选在传入的id下,有name属性,类型是text或者是password
    $("#" + formId + " [name]:text" + "," + "#" + formId + " [name]:password" + "," + "#" + formId + " [name]:password" + "," + "#" + formId + " textarea").each(function(index, item) {
        //设置当前元素的值为空
        $(item).val("");
    });

    //处理单选框(radio)和复选框(checkbox)
    //筛选在传入的id下,有name属性,类型是radio,状态为已选中
    $("#" + formId + " [name]:radio:checked" + "," + "#" + formId + " [name]:checkbox:checked").each(function(index, item) {
        //把已选中的单选框设置为未选中状态
        $(item).get(0).checked = false;
    });

    //处理下拉选择框(select)
    //筛选在传入的id下,有name属性,类型是select,状态为已选中
    $("#" + formId + " :selected").each(function(index, item) {
        $(item).get(0).selected = false;
    });
};


//用于清除form表单中原有数据的方法
iform.cleanMyForm = function(formId) {
    //处理输入框(普通输入框text)
    //筛选在传入的id下,有name属性,类型是text或者是password
    $(formId + " [name]:text" + "," + formId + " [name]:password" + ","  + formId + " [name]:password" + ","  + formId + " textarea").each(function(index, item) {
        //设置当前元素的值为空
        $(item).val("");
    });

    //处理单选框(radio)和复选框(checkbox)
    //筛选在传入的id下,有name属性,类型是radio,状态为已选中
    $( formId + " [name]:radio:checked" + "," + formId + " [name]:checkbox:checked").each(function(index, item) {
        //把已选中的单选框设置为未选中状态
        $(item).get(0).checked = false;
    });

    //处理下拉选择框(select)
    //筛选在传入的id下,有name属性,类型是select,状态为已选中
    $( formId + " :selected").each(function(index, item) {
        $(item).get(0).selected = false;
    });
};