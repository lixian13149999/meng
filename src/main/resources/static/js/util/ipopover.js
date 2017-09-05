/**
 * 用于处理警告框公用样式的文件
 *
 */
var ipopover = new Object();

ipopover.listenerPromptCont = function(){
//    console.log("执行了监听");
    $('[data-toggle="prompt-cont"]').each(function() {
//        console.log("进入each");
		var ele = $(this); //获取定义了弹出框的对象
		var id = ele.attr('id'); //获取id
		var txt = ele.html(); //获取其中的html标签
		ele.popover({
			trigger: 'manual', //触发方式
			placement: 'bottom', //top, bottom, left or right,显示在当前按钮的什么位置
			title: ipopover.getTitle(ele), //标题
			html: 'true', //为true的话，data-content里就能放html代码了
			content: ipopover.getContent(ele), //这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
			// }).on("click", function() { //鼠标穿过事件
		}).on("mouseenter", function() { //鼠标穿过事件
			var _this = this;
			$(this).popover("show"); //显示当前的提示
			//当遍历弹出框有穿出事件时,隐藏当前的弹出框
			$(this).siblings(".popover").on("mouseleave", function() {
				$(_this).popover('hide');
			});
			//当鼠标穿出当前按钮时
			// }).on("click", function() {
		}).on("mouseleave", function() {
			var _this = this;
			//设定一个延时器,防止滑动的时候,触发到边界值
			setTimeout(function() {
				// console.log($(".popover:hover"));
				if (!$(".popover:hover").length) {
					$(_this).popover("hide");
				}
			}, 100);
		});
	});
};

//获取提示标签的titile
ipopover.getTitle = function(ele){
    var contentFunc = $(ele).data("title-func-mapping");
    return eval(contentFunc);
};
//获取提示标签的内容(根据id)
ipopover.getContent = function(ele){
    var contentFunc = $(ele).data("content-func-mapping");
//    console.log(contentFunc);
    return eval(contentFunc);
};
