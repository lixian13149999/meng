
/**
 * 用于排序的相关js
 */
var isort = new Object();

//一级栏目的移动
//- imenu.moveFirstMenu = function(){
isort.moveFirstMenu = function(){
	// console.log("一级栏目拖动");
	// 一级栏目的拖动
	$("#menu-cont").sortable({
		//            约束其运动方向(只能y轴运动)
		axis: "y",
		//设置把手
		handle: ".menu-move-handle",
		revert: true,
		//            约束其运动范围(只能在父元素中运动)
		//            containment: "parent",
		start: function(event, ui) {
			ui.item.addClass("move-box-start");
		},
		stop: function(event, ui) {
			ui.item.removeClass("move-box-start");

			var len = $(this).children('.menu-box').length;
			var ele;
			var menu;
			var menus = new Array();
			for (var i = 0; i < len; i++) {
				ele = $(this).children('.menu-box').eq(i).find('span.menu-tools-cont').children('span');
				menu = iutil.parseData(ele,{
			        dataName:"para-id",
			        objName:"id"
			    },{
					dataName:"para-name",
			        objName:"name"
				});

				menu.sort = i + 1;
				menus[i] = menu;
			};
			$.ajax({
				url: 'menu/sequence',
				type: 'post',
				dataType:"json",
				contentType : 'application/json',
				data: JSON.stringify(menus),
				success: function(data) {
				},
				err: function(jqXHR, textStatus, errorThrown) {
					console.log('error ' + textStatus + " " + errorThrown);
				}
			});

		}
	});
}

//二级栏目的移动
isort.moveSecondMenu = function(){
    // 二级栏目的拖动
	$(".menu-items-count").sortable({
		//            约束其运动方向(只能y轴运动)
		axis: "y",
		handle: ".item-move-handle",
		revert: true,
		// containment: ".column_items",
		//            约束其运动范围(只能在父元素中运动)
		// containment: "parent",
		start: function(event, ui) {
			// ui.item.addClass("sortable_start");
		},
		stop: function(event, ui) {
			var len = $(this).children('dd.menu-item').length;
			var ele;
			var menu;
			var menus = new Array();
			for (var i = 0; i < len; i++) {
				ele = $(this).children('dd').eq(i).children('span.menu-tools-cont').children('span');
				menu = iutil.parseData(ele,{
			        dataName:"para-id",
			        objName:"id"
			    },{
					dataName:"para-name",
			        objName:"name"
				});

				menu.sort = i + 1;
				menus[i] = menu;
			};
			// console.log(menus);
			$.ajax({
				url: 'menu/sequence',
				type: 'post',
				dataType:"json",
				contentType : 'application/json',
				data: JSON.stringify(menus),
				success: function(data) {
					// console.log(data);
				},
				err: function(jqXHR, textStatus, errorThrown) {
					console.log('error ' + textStatus + " " + errorThrown);
				}
			});
		}
	});
}


//权限的移动
isort.movePower = function(){
    // 权限列表的拖动排序
	$(".au-role-boxes").sortable({
		//            约束其运动方向(只能y轴运动)
		axis: "y",
		handle: ".item-move-handle",
		revert: true,
		// containment: ".column_items",
		//            约束其运动范围(只能在父元素中运动)
		// containment: "parent",
		start: function(event, ui) {
            ui.item.addClass("move-box-start");
			// ui.item.addClass("sortable_start");

			// ui.item.removeClass("add_transition");
			// $(this).addClass("sortable_start");
			// $("#sortable>.li").addClass("li_sor");
		},
		stop: function(event, ui) {
            ui.item.removeClass("move-box-start");
			var len = $(this).children('dd.menu_item').length;
			var ele;
			var menu;
			for (var i = 0; i < len; i++) {
				ele = $(this).children('dd').eq(i).children('span').children('span');
				menu = iutil.getDatas(ele, {
					dataName: 'para-_id',
					objName: '_id'
				}, {
					dataName: 'para-name',
					objName: 'name'
				})
				menu.order = i + 1
					// console.log(menu);
				$.ajax({
					url: 'menu/order',
					type: 'post',
					data: {
						_id: menu._id,
						order: menu.order
					},
					success: function(data) {
						// $("#column_list").html(data);
						// console.log(data);
					},
					err: function(jqXHR, textStatus, errorThrown) {
						console.log('error ' + textStatus + " " + errorThrown);
					}
				})
			};
		}
	});
}




$(function(){
	//监听一级栏目移动的方法
	isort.moveFirstMenu();

	//监听二级栏目移动的方法
	isort.moveSecondMenu();

	//监听权限移动的方法
	isort.movePower();
})

