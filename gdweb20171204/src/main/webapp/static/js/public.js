(function() {
	$(".proProvinceSelAll").click(function(event){
		$(this).select();
		$(".provinceCity").hide();
		$(".provinceCityAll").hide();
		$("#dimCityQuery").hide();
		var o2 = $(this).offset();
		var l2 = o2.left;
		var t2 = o2.top;
		var h2 = $(this).height();
		$(".provinceCityAll").css("top", t2 + h2 - 1).css("left", l2).toggle();
		$(".provinceCityAll").click(function(event) {
			event.stopPropagation();
		});
		event.stopPropagation();
		$("html").click(function() {
			$(".provinceCityAll").hide();
		});
		$("input.proProvinceSelAll").removeClass("current2");
		$(this).addClass("current2");
		$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
		$(".provinceCityAll").find(".tabs").find("a[tb=provinceAll]").addClass("current");
		$(".provinceCityAll").find(".con").children().hide();
		$(".provinceCityAll").find(".con").find(".provinceAll").show();
		if ($("body").data("allProvinces") == null) {
			sendAllProvinceAjax();
		}
		$(".provinceCityAll").find(".tabs").find("a").click(function() {
			if ($(this).attr("tb") == "cityAll" && $(".provinceAll .list .current").val() == null) {
				return;
			};
			if ($(this).attr("tb") == "countyAll" && $(".cityAll .list .current").val() == null) {
				return;
			};
			$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
			$(this).addClass("current");
			var tb = $(this).attr("tb");
			$(".provinceCityAll").find(".con").children().hide();
			$(".provinceCityAll").find(".con").find("." + tb).show();
		});
	});
})(); 
(function() {
	$(".proCitySelAll_Img").click(function(event) {
		event.stopPropagation();
		$(this).prev().trigger("click");
	});
})();
function wrongMsg(object, msg) {
	$(".wrongMsg").text(msg);
	object.addClass("wrong");
}
 (function() {
	var picNum = $("div.flashPic img").size();
	var isNum = 0;
	var str = [];
	var imgDiv = $("div.flashPic");
	var $div = $("div.picNum");
	imgDiv.find("img").hide().eq(0).show();
	for (var i = 0; i < picNum; i++) {
		str[i] = "<a href='javascript:'>" + (i + 1) + "</a>";
	}
	$div.html(str.join(""));
	$div.find("a:eq(0)").addClass("on");
	function MovePic() {
		if ((isNum + 1) >= picNum) {
			isNum = 0;
		}
		 else {
			isNum = isNum + 1;
		}
		imgDiv.find("img").hide().eq(isNum).fadeIn(500);
		$div.find("a").removeClass("on").eq(isNum).addClass("on");
	}
	var setFn = setInterval(MovePic, 4000);
	$div.find("a").click(function() {
		clearInterval(setFn);
		var j = $(this).index();
		$div.find("a").removeClass("on").siblings().eq(j).addClass("on");
		imgDiv.find("img").hide();
		imgDiv.find("img").eq(j).fadeIn(500);
		isNum = j;
		setFn = setInterval(MovePic, 4000);
	});
})();

(function() {
	var clkIndex;
	var currentClass;
	var allCitys;
	var allProvinces;
	var allCountys;
	var thisObj;
	var dimCityDiv = "<div id='dimCityQuery'><ul></ul></div>";
	$("body").append(dimCityDiv);
	$("body").delegate(".proCityQuery,.proCityQueryAll", ($.browser.opera ? "keypress": "keyup"),
	function(event) {
		if ($("#dimCityQuery:visible").size() == 0) {
			$(".backifname").hide();
		}
		$(".provinceCity").hide();
		$(".provinceCityAll").hide();
		if ($(this).hasClass("proCityQueryAll"))
		 {
			if ($("body").data("allProvinces") == null) {
				sendAllProvinceAjax();
			}
			currentClass = "proCityQueryAll";
			clkIndex = $("body").find(".proCityQueryAll").index(this);
			allCitys = $("body").data("CitysAll");
			allProvinces = $("body").data("allProvinces");
			allCountys = $("body").data("allCountys");
			thisObj = $(this);
		}
		if ($(this).hasClass("proCityQuery"))
		 {
			if ($("body").data("allExistProvinces") == null) {
				sendProvinceAjax();
			}
			currentClass = "proCityQuery";
			clkIndex = $("body").find(".proCityQuery").index(this);
			allCitys = $("body").data("allExistCitys");
			allProvinces = $("body").data("allExistProvinces");
			allCountys = $("body").data("allExistCountys");
			thisObj = $(this);
		}
		lastKeyPressCode = event.keyCode;
		switch (lastKeyPressCode) {
		case 40:
			$("#dimCityQuery").trigger("selNext");
			return false;
			break;
		case 38:
			$("#dimCityQuery").trigger("selPrev");
			return false;
			break;
		case 13:
			$("#dimCityQuery").trigger("enter");
			return false;
			break;
		}
		v = $.trim($(this).val());
		if (v == "" || v == null) {
			return false;
		}
		$(".provinceCity").hide();
		var o = $(this).offset();
		var l = o.left;
		var t = o.top;
		var w = $(this).width();
		var h = $(this).height();
		var htmlArr = [];
		var autoWidth;
		for (i = 0; i < allCountys.length; i++) {
			if (v.toUpperCase() === allCountys[i].pinYinChar.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCountys[i].provinceId + " cityId=" + allCountys[i].cityId + " countyId=" + allCountys[i].id + ">" + allCountys[i].cityName + "-" + allCountys[i].areaName + " (<span style='color:red'>" + v.toUpperCase() + "</span>" + allCountys[i].pinYinChar.substring(v.length, allCountys[i].pinYinChar.length) + ")</a></li>";
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYinChar).length ? (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYinChar).length: autoWidth;
				continue;
			};
			if (v === allCountys[i].areaName.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCountys[i].provinceId + " cityId=" + allCountys[i].cityId + " countyId=" + allCountys[i].id + ">" + allCountys[i].cityName + "-" + "<span style='color:red'>" + v + "</span>" + allCountys[i].areaName.substring(v.length, allCountys[i].areaName.length) + " (" + allCountys[i].pinYinChar + ")</a></li>";
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYinChar).length ? (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYinChar).length: autoWidth;
				continue;
			};
			if (v.toLowerCase() === allCountys[i].pinYin.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCountys[i].provinceId + " cityId=" + allCountys[i].cityId + " countyId=" + allCountys[i].id + ">" + allCountys[i].cityName + "-" + allCountys[i].areaName + " (<span style='color:red'>" + v.toLowerCase() + "</span>" + allCountys[i].pinYin.substring(v.length, allCountys[i].pinYin.length) + ")</a></li>"
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYin).length ? (allCountys[i].cityName + allCountys[i].areaName + allCountys[i].pinYin).length: autoWidth;
				continue;
			};
		}
		for (i = 0; i < allCitys.cities.length; i++) {
			if (v.toUpperCase() === allCitys.cities[i].cityShortPY.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCitys.cities[i].provinceId + " cityId=" + allCitys.cities[i].id + ">" + allCitys.cities[i].name + " (<span style='color:red'>" + v.toUpperCase() + "</span>" + allCitys.cities[i].cityShortPY.substring(v.length, allCitys.cities[i].cityShortPY.length) + ")</a></li>";
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCitys.cities[i].name + allCitys.cities[i].cityShortPY).length ? (allCitys.cities[i].name + allCitys.cities[i].cityShortPY).length: autoWidth;
				continue;
			};
			if (v === allCitys.cities[i].name.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCitys.cities[i].provinceId + " cityId=" + allCitys.cities[i].id + "><span style='color:red'>" + v + "</span>" + allCitys.cities[i].name.substring(v.length, allCitys.cities[i].name.length) + " (" + allCitys.cities[i].cityShortPY + ")</a></li>";
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCitys.cities[i].name + allCitys.cities[i].cityShortPY).length ? (allCitys.cities[i].name + allCitys.cities[i].cityShortPY).length: autoWidth;
				continue;
			};
			if (v.toLowerCase() === allCitys.cities[i].cityPinyin.substring(0, v.length)) {
				htmlArr[htmlArr.length] = "<li><a class='allcityClass' href='javascript:' provinceId=" + allCitys.cities[i].provinceId + " cityId=" + allCitys.cities[i].id + ">" + allCitys.cities[i].name + " (<span style='color:red'>" + v.toLowerCase() + "</span>" + allCitys.cities[i].cityPinyin.substring(v.length, allCitys.cities[i].cityPinyin.length) + ")</a></li>"
				if (htmlArr.length > 9) {
					break;
					return false;
				}
				autoWidth = autoWidth < (allCitys.cities[i].name + allCitys.cities[i].cityPinyin).length ? (allCitys.cities[i].name + allCitys.cities[i].cityPinyin).length: autoWidth;
				continue;
			};
		};
		if (htmlArr == "" || htmlArr == null) {
			$("#dimCityQuery ul").html("<li class='none'>对不起,没有找到该城市</li>");
			return false;
		} else {
			$("#dimCityQuery ul").html(htmlArr.join("")).find("li:first").addClass("current");
		};
		if (autoWidth < 200) {
			autoWidth = 200;
		}
		$("#dimCityQuery").css("width", autoWidth).css("top", t + h - 1).css("left", l).show();
		$(".backifname").show();
		$("html").click(function() {
			$("#dimCityQuery").hide();
			$(".backifname").hide();
		});
	});
	$("body").delegate("#dimCityQuery li", "hover",
	function() {
		$(this).addClass("current").siblings().removeClass("current");
	},
	function() {
		$(this).removeClass("current");
	});
	$("#dimCityQuery").delegate("", "selNext",
	function() {
		var next = $(this).find("li.current").next();
		if (next.size() > 0) {
			next.addClass("current").siblings().removeClass("current");
		}
		 else {
			$("#dimCityQuery li").removeClass("current").first().addClass("current");
		};
	});
	$("#dimCityQuery").delegate("", "selPrev",
	function() {
		var prev = $(this).find("li.current").prev();
		if (prev.size() > 0) {
			prev.addClass("current").siblings().removeClass("current");
		}
		 else {
			$("#dimCityQuery li").removeClass("current").last().addClass("current");
		};
	});
	$("#dimCityQuery").delegate("", "enter",
	function(event) {
		var cur = $(this).find("li.current");
		if (cur.size() > 0) {
			cur.find("a").trigger("click");
		};
	});
	$("body").delegate("#dimCityQuery li a.allcityClass", "click",
	function() {
		var vm = $(this).text();
		var provinceId = $(this).attr("provinceId");
		var cityId = $(this).attr("cityId");
		var countyId = $(this).attr("countyId");
		var provinceName;
		var cityName;
		var rtn;
		for (i = 0; i < allProvinces.length; i++) {
			if (allProvinces[i].id == provinceId) {
				provinceName = allProvinces[i].provinceName;
			};
		}
		for (i = 0; i < allCitys.cities.length; i++) {
			if (allCitys.cities[i].id == cityId) {
				cityName = allCitys.cities[i].name;
			}
		}
		if (currentClass == "proCityQueryAll") {
			$("body").data("pAllId", provinceId);
			$("body").data("cAllId", cityId);
			$("body").data("aAllId", countyId);
			$("body").data("pAllName", provinceName);
			$("body").data("nameOfCityAll", cityName);
		}
		if (currentClass == "proCityQuery") {
			$("body").data("pId", provinceId);
			$("body").data("cId", cityId);
			$("body").data("aId", countyId);
			$("body").data("pName", provinceName);
			$("body").data("nameOfCity", cityName);
		}
		vm = vm.split("(");
		countyName = $.trim(vm[0]);
		if (countyId == null || countyName == cityName)
		 {
			if (currentClass == "proCityQuery")
			 {
				thisObj.trigger("click");
				counties = [];
				var j = 0;
				$.each(allCountys,
				function(i, county) {
					if (county.cityId == cityId) {
						counties[j++] = county;
					}
				});
				countyTotalPage = Math.ceil(counties.length / p_pageSize);
				$(".provinceCity").find(".tabs").find("a").removeClass("current");
				$(".provinceCity .tabs").find("#county").addClass("current");
				$(".con .city .list a").removeClass("current");
				$(".provinceCity").find(".con").children().hide();
				$(".provinceCity").find(".con").find(".county").show();
				$(".con .provinceAll .list a").removeClass("current");
				countyPage(1);
			}
			 else if (currentClass == "proCityQueryAll")
			 {
				thisObj.trigger("click");
				countiesAll = [];
				var j = 0;
				$.each(allCountys,
				function(i, county) {
					if (county.cityId == cityId && county.areaName != cityName) {
						countiesAll[j++] = county;
					}
				});
				countyTotalPageAll = Math.ceil(countiesAll.length / p_pageSize);
				$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
				$(".provinceCityAll .tabs").find("#countyAll").addClass("current");
				$(".con .cityAll .list a").removeClass("current");
				$(".provinceCityAll").find(".con").children().hide();
				$(".provinceCityAll").find(".con").find(".countyAll").show();
				$(".con .provinceAll .list a").removeClass("current");
				allCountyPage(1);
			}
		}
		 else
		 {
			rtn = provinceName + "-" + countyName;
			if (currentClass == "proCityQueryAll")
			 {
				$("body").find(".proCityQueryAll").eq(clkIndex).val(rtn);
				$("body").find(".proCityQueryAll").eq(clkIndex).trigger("change");
				$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
				$(".provinceCityAll").find(".tabs").find("a[tb=hotCityAll]").addClass("current");
				$(".provinceCityAll .con .list a").removeClass("current");
				$(".provinceCityAll .con .list a input").removeClass("current");
			}
			if (currentClass == "proCityQuery")
			 {
				$("body").find(".proCityQuery").eq(clkIndex).val(rtn);
				$("body").find(".proCityQuery").eq(clkIndex).trigger("change", [cityId, countyId]);
				$(".provinceCity").find(".tabs").find("a").removeClass("current");
				$(".provinceCity").find(".tabs").find("a[tb=hotCity]").addClass("current");
				$(".provinceCity .con .list a").removeClass("current");
				$(".provinceCity .con .list a input").removeClass("current");
			}
		}
		$("#dimCityQuery").hide();
		$(".backifname").hide();
		return false;
	});
	$(".nomarl").live("focus",
	function() {
		var ov = $.trim($(this).attr("ov"));
		var val = $.trim($(this).val());
		$(this).css({
			"color": "#000"
		});
		if (val == ov) {
			$(this).val("");
		}
	});
	$(".nomarl").live("blur",
	function() {
		var ov = $.trim($(this).attr("ov"));
		var val = $.trim($(this).val());
		if (val == "" || val == ov) {
			$(this).val(ov).css({
				"color": "#aaa"
			});
		}
	});
})();
function addFavorite()
 {
	var sURL = "http://www.gdweb.com";
	var sTitle = "计算机科学学院";
	try
	 {
		window.external.addFavorite(sURL, sTitle);
	}
	 catch(e)
	 {
		try
		 {
			window.sidebar.addPanel(sTitle, sURL, "");
		}
		 catch(e)
		 {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}
function queryData() {
	var obj1 = $(".ts-usermessage").find("#transOrder");
	var obj2 = $(".ts-usermessage").find("#payOrder");
	var obj3 = $("#unreadMessages");
	$.ajax({
		type: "post",
		url: "/user/queryPayOderTranOder.action",
		dataType: "json",
		beforeSend: function(XMLHttpRequest) {
			obj1.html("<img src='../theme/default/images/loading.gif' height=20 width=20/>");
			obj2.html("<img src='../theme/default/images/loading.gif' height=20 width=20/>");
			obj3.html("<img src='../theme/default/images/loading.gif' height=20 width=20/>");
		},
		success: function(msg) {
			if (msg.user != null) {
				obj1.html("(" + msg.user.transportingOrder + ")");
				obj2.html("(" + msg.user.refundPaymentOrder + ")");
				obj3.html("(" + msg.user.siteMessage + ")");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown)
		 {
			obj1.html("");
			obj2.html("");
			obj3.html("");
		}
	});
}

$(".province .pre a").bind('click',
function() {
	var provincePage = parseInt($('#provincePage').html());
	if (provincePage == 1) {
		return;
	}
	viewProvince(provincePage - 1);
});
$(".city .pre a").bind('click',
function() {
	var cityPages = parseInt($('#cityPage').html());
	if (cityPages == 1) {
		return;
	}
	cityPage(cityPages - 1);
});
$(".county .pre a").bind('click',
function() {
	var countyPages = parseInt($('#countyPage').html());
	if (countyPages == 1) {
		return;
	}
	countyPage(countyPages - 1);
});
$(".province .next a").bind('click',
function() {
	var provincePage = parseInt($('#provincePage').html());
	provinceTotalPage = countProvincePages();
	if (provincePage == provinceTotalPage) {
		return;
	}
	viewProvince(provincePage + 1);
});
$(".city .next a").bind('click',
function() {
	if ($(this).hasClass("can")) {
		var cityPages = parseInt($('#cityPage').html());
		cityPage(cityPages + 1);
	}
});
$(".county .next a").bind('click',
function() {
	if ($(this).hasClass("can")) {
		var countyPages = parseInt($('#countyPage').html());
		countyPage(countyPages + 1);
	}
});
function json2str(o) {
	var arr = [];
	var fmt = function(s) {
		if (typeof s == 'object' && s != null) return json2str(s);
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'": s;
	};
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
	return '{' + arr.join(',') + '}';
}


var allProvinces = null;
var allCities = null;
var allAreas = null;
var allProId = null;
var cityIdAll = null;
var provinceAllTotalPage = null;
var pa_pageSize = 12;
var pa_currentPage = 1;
function sendAllProvinceAjax() {
	var showonlyourco=$("input.current2").attr("showonlyourco");
	var showonlysend=$("input.current2").attr("showonlysend");
	var showonlyarrived=$("input.current2").attr("showonlyarrived");
	if (showonlyourco=="undefined") {showonlyourco="";};
	if (showonlysend=="undefined") {showonlysend="";};
	if (showonlyarrived=="undefined") {showonlyarrived="";};
	$.ajax({
		type: "post",
		url: '/api/getprovince',		
		async: false,
		data: {"showonlyourco":showonlyourco,"showonlysend":showonlysend,"showonlyarrived":showonlyarrived},
		dataType: "json",
		success: function(result) {
			allProvinces = result.data;
			$("body").data("allProvinces", allProvinces);
			viewAllProvince(1);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown)
		 {
			alert(textStatus);
		}
	});
}

$(".provinceAll .pre a").bind('click',
function() {
	var provincePage1 = parseInt($('#provincePage1').html());
	if (provincePage1 == 1) {
		return;
	}
	viewAllProvince(provincePage1 - 1);
});
$(".cityAll .pre a").bind('click',
function() {
	var cityPages1 = parseInt($('#cityPage1').html());
	if (cityPages1 == 1) {
		return;
	}
	allCityPage(cityPages1 - 1);
});
$(".countyAll .pre a").bind('click',
function() {
	var countyPages = parseInt($('#countyPage1').html());
	if (countyPages == 1) {
		return;
	}
	allCountyPage(countyPages - 1);
});
$(".provinceAll .next a").bind('click',
function() {
	var provincePage1 = parseInt($('#provincePage1').html());
	provinceAllTotalPage = countAllProvincePages();
	if (provincePage1 >= provinceAllTotalPage) {
		return;
	}
	viewAllProvince(provincePage1 + 1);
});
$(".cityAll .next a").bind('click',
function() {
	if ($(this).hasClass("can")) {
		var cityPages1 = parseInt($('#cityPage1').html());
		allCityPage(cityPages1 + 1);
	}
});
$(".countyAll .next a").bind('click',
function() {
	if ($(this).hasClass("can")) {
		var countyPages = parseInt($('#countyPage1').html());
		allCountyPage(countyPages + 1);
	}
});
function json2str(o) {
	var arr = [];
	var fmt = function(s) {
		if (typeof s == 'object' && s != null) return json2str(s);
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'": s;
	};
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
	return '{' + arr.join(',') + '}';
}
function countAllProvincePages() {
	provinceAllTotalPage = Math.ceil(allProvinces.length / pa_pageSize);
	return provinceAllTotalPage;
}
function viewAllProvince(page) {
	$(".provinceAll .list ul li").remove();
	if (page == 1) {
		$(".provinceAll .pre a").removeClass("can");
		$(".provinceAll .next a").addClass("can");
	} else {
		$(".provinceAll .pre a").addClass("can");
		$(".provinceAll .next a").addClass("can");
	}
	var end;
	var start;
	if (page == provinceAllTotalPage) {
		start = (page - 1) * pa_pageSize;
		end = allProvinces.length;
		$(".provinceAll .next a").removeClass("can");
	} else {
		start = (page - 1) * pa_pageSize;
		end = start + pa_pageSize;
	}
	for (var i = start; i < end; i++) {
		var p_id = allProvinces[i].PID;
		var p_name = allProvinces[i].PNAME;
		if (allProvinces[i].PNAME == '内蒙古自治区') {
			p_name = '内蒙古';
		} else if (allProvinces[i].PNAME == '黑龙江省') {
			p_name = '黑龙江';
		} else {
			p_name = allProvinces[i].PNAME.substr(0, 2);
		}
		var li = $('<li><a style="background: none repeat scroll 0% 0% transparent; border: 0px none;" href="javascript:onclick=viewAllCities(' + i + ');" id="' + p_id + '">' + p_name + '</a></li>');
		$(".provinceAll .list ul").append(li);
	}
	$(".provinceAll .list #provincePage1").remove();
	$(".provinceAll .list").append("<label id='provincePage1' style='display:none;'>" + page + "</label>");
}

function viewAllCities(i) {
	allProId = allProvinces[i].PID;
	$("body").data("pAllName", allProvinces[i].PNAME);
	$("body").data("pAllId", allProId);	
	var showonlyourco=$("input.current2").attr("showonlyourco");
	var showonlysend=$("input.current2").attr("showonlysend");
	var showonlyarrived=$("input.current2").attr("showonlyarrived");
	if (showonlyourco=="undefined") {showonlyourco="";};
	if (showonlysend=="undefined") {showonlysend="";};
	if (showonlyarrived=="undefined") {showonlyarrived="";};
	$.ajax({
		type: "POST",
		url: "/api/getcity",
		data: {"pid":allProId,"showonlyourco":showonlyourco,"showonlysend":showonlysend,"showonlyarrived":showonlyarrived},
		dataType: "json",
		async:false,
		success: function(result) {
			if (result.code=="0"){
				allCities=result.data;
				$("body").data("CitysAll", result.data);
				allCitys = [];
				var j = 0;
				$.each(allCities,
				function(i, city) {
					if (city.PID == allProId) {
						allCitys[j++] = city;
					}
				});
				allCityTotalPage = Math.ceil(allCitys.length / pa_pageSize);
				$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
				$(".provinceCityAll .tabs").find("#cityAll").addClass("current");
				$(".con .provinceAll .list a").removeClass("current");
				$(".con .provinceAll .list a[id='" + allProId + "']").addClass("current");
				$(".provinceCityAll").find(".con").children().hide();
				$(".provinceCityAll").find(".con").find(".cityAll").show();
				
				allCityPage(1);	
			}else{
				alert(result.message);
				return false;
			}
		}
	});
}
function allCityPage(page) {
	var nameOfProvince = $("body").data("pAllName");
	$("input.current2").removeClass("iGrays");
	$("input.current2").val(nameOfProvince);
	
	$(".cityAll .list ul li").empty();
	$(".cityAll .list ul li").remove();
	if (page == 1) {
		$(".cityAll .pre a").removeClass("can");
	} else {
		$(".cityAll .pre a").addClass("can");
	}
	var start;
	var end;
	if (page <= 1) {
		page = 1;
		$(".cityAll .pre a").removeClass("can");
		$(".cityAll .next a").addClass("can");
	}
	if (allCityTotalPage == 1) {
		$(".cityAll .next a").removeClass("can");
		$(".cityAll .pre a").removeClass("can");
	}
	if (page >= allCityTotalPage) {
		page = allCityTotalPage;
		$(".cityAll .next a").removeClass("can");
		start = (page - 1) * pa_pageSize;
		end = allCitys.length;
	} else if (page == 1) {
		start = (page - 1) * pa_pageSize;
		end = start + pa_pageSize;
		$(".cityAll .pre a").removeClass("can");
		$(".cityAll .next a").addClass("can");
	} else {
		start = (page - 1) * pa_pageSize;
		end = start + pa_pageSize;
		$(".cityAll .next a").addClass("can");
		$(".cityAll .pre a").addClass("can");
	}
	for (var i = start; i < end; i++) {
		var c_id = allCitys[i].CID;
		var cityName = allCitys[i].CNAME.substr(0, 4);
		var li = $('<li><a href="javascript:onclick=viewAllCounties(' + i + ')" id="' + c_id + '">' + cityName + '</a></li>');
		$(".cityAll .list ul").append(li);
	}
	$(".cityAll .list #cityPage1").remove();
	$(".cityAll .list").append("<label id='cityPage1' style='display:none;'>" + page + "</label>");
}
function viewAllCounties(i) {
	cityIdAll = allCitys[i].CID;
	$("body").data("cAllId", cityIdAll);
	var cityname = $.trim(allCitys[i].CNAME);
	$("body").data("nameOfCityAll", cityname);
	var showonlyourco=$("input.current2").attr("showonlyourco");
	var showonlysend=$("input.current2").attr("showonlysend");
	var showonlyarrived=$("input.current2").attr("showonlyarrived");
	if (showonlyourco=="undefined") {showonlyourco="";};
	if (showonlysend=="undefined") {showonlysend="";};
	if (showonlyarrived=="undefined") {showonlyarrived="";};
	$.ajax({
		type: "POST",
		url: "/api/getquyu",
		data: {"pid":cityIdAll,"showonlyourco":showonlyourco,"showonlysend":showonlysend,"showonlyarrived":showonlyarrived},
		dataType: "json",
		async:false,
		success: function(result) {
			if (result.code=="0"){
				allAreas = result.data;
				$("body").data("allCountys", result.data);
				
				countiesAll = [];
				var j = 0;
				$.each(allAreas,
				function(i, countys) {
					if (countys.PID == cityIdAll) {
						countiesAll[j++] = countys;
					}
				});
				countyTotalPageAll = Math.ceil(countiesAll.length / pa_pageSize);
				$(".provinceCityAll").find(".tabs").find("a").removeClass("current");
				$(".provinceCityAll .tabs").find("#countyAll").addClass("current");
				$(".con .cityAll .list a").removeClass("current");
				$(".con .cityAll .list a[id='" + cityIdAll + "']").addClass("current");
				$(".provinceCityAll").find(".con").children().hide();
				$(".provinceCityAll").find(".con").find(".countyAll").show();
				allCountyPage(1);
				
			}else{
				alert(result.message);
				return false;
			}
		}
	});
	
	
	
}
function allCountyPage(page) {
	var nameOfProvince = $("body").data("pAllName");
	var cityCurrentName = $("body").data("nameOfCityAll");
	$("input.current2").removeClass("iGrays");
	$("input.current2").val(nameOfProvince + "-" + cityCurrentName);
	
	//在此处给城市ID赋值
	var cityid_name=$("input.current2").attr("id")+"_cid";
	if ($("#"+cityid_name).length>0){
		$("#"+cityid_name).val($("body").data("cAllId"));
	};
	
	//在此处获取城市的网点
	var select_network_name=$("input.current2").attr("id")+"_select_network";
	if ($("#"+select_network_name).length>0){
		var temp="<option>——请选择网点——<\/option>";
		$("#"+select_network_name).html(temp);
		$.ajax({
			type: "POST",
			url: "/api/getbranchinfos",
			data: {"province":nameOfProvince,"city":cityCurrentName},
			dataType: "json",
			async:false,
			success: function(result) {
				var allNetWorks=result.data;
				temp="<option>——请选择网点——<\/option>";
				$.each(allNetWorks,function(i, network) {
						temp=temp+"<option style=\"color:#000\" value=\""+network.BRANCHNAME+"\" data=\""+network.BRANCHNO+"\" lng=\""+network.LNT+"\" lat=\""+network.LAT+"\">"+network.BRANCHNAME+"<\/option>";
						
				});
				$("#"+select_network_name).html(temp);
			}
		});
		
		
	};
	
	$(".countyAll .list ul li").remove();
	if (page == 1) {
		$(".countyAll .pre a").removeClass("can");
	} else {
		$(".countyAll .pre a").addClass("can");
	}
	var start;
	var end;
	if (page <= 1) {
		page = 1;
		$(".countyAll .pre a").removeClass("can");
		$(".countyAll .next a").addClass("can");
	}
	if (countyTotalPageAll == 1) {
		$(".countyAll .next a").removeClass("can");
		$(".countyAll .pre a").removeClass("can");
	}
	if (page >= countyTotalPageAll) {
		page = countyTotalPageAll;
		$(".countyAll .next a").removeClass("can");
		start = (page - 1) * pa_pageSize;
		end = countiesAll.length;
	} else if (page == 1) {
		start = (page - 1) * pa_pageSize;
		end = start + pa_pageSize;
		$(".countyAll .pre a").removeClass("can");
		$(".countyAll .next a").addClass("can");
	} else {
		start = (page - 1) * pa_pageSize;
		end = start + pa_pageSize;
		$(".countyAll .next a").addClass("can");
		$(".countyAll .pre a").addClass("can");
	}
	for (var i = start; i < end; i++) {
		var c_id = countiesAll[i].LEVEL_CODE;
		var countyName = countiesAll[i].NAME.substr(0, 4);;
		var li = $('<li><a href="javascript:onclick=addrInputAll(' + i + ')" id="' + c_id + '">' + countyName + '</a></li>');
		$(".countyAll .list ul").append(li);
	}
	$(".countyAll .list #countyPage1").remove();
	$(".countyAll .list").append("<label id='countyPage1' style='display:none;'>" + page + "</label>");
}
function addrInputAll(i) {
	var countyId = $.trim(countiesAll[i].LEVEL_CODE);
	$(".con .hotCityAll .list a input").removeClass("current");
	$(".con .hotCityAll .list a input[id='" + cityIdAll + "']").addClass("current");
	$(".con .countyAll .list a").removeClass("current");
	$(".con .countyAll .list a[id='" + countyId + "']").addClass("current");
	allProId = $("body").data("pAllId");
	cityIdAll = $("body").data("cAllId");
	
	var p = null;
	$.each(allProvinces,
	function(i, province) {
		if (province.PID == allProId) {
			p = province.PNAME;
			return false;
		}
	});

	var c = null;
	$.each(allCities,
	function(i, city) {
		if (city.CID == cityIdAll) {
			c = city.CNAME;
			return false;
		}
	});
	var a = null;
	$.each(countiesAll,
	function(i, county) {
		if (county.LEVEL_CODE == countyId) {
			a = county.NAME;
			return false;
		}
	});
	
	//在此处获取区的网点
	var select_network_name=$("input.current2").attr("id")+"_select_network";
	if ($("#"+select_network_name).length>0){
		var temp="<option>——请选择网点——<\/option>";
		$("#"+select_network_name).html(temp);
		$.ajax({
			type: "POST",
			url: "/api/getbranchinfos",
			data: {"province":p,"city":c,"area":a},
			dataType: "json",
			async:false,
			success: function(result) {
				var allNetWorks=result.data;
				temp="<option>——请选择网点——<\/option>";
				$.each(allNetWorks,function(i, network) {
						temp=temp+"<option style=\"color:#000\" value=\""+network.BRANCHNAME+"\" data=\""+network.BRANCHNO+"\" lng=\""+network.LNT+"\" lat=\""+network.LAT+"\">"+network.BRANCHNAME+"<\/option>";
						
				});
				$("#"+select_network_name).html(temp);
			}
		});
		
		
	};
	
	var nameValue = $("input.current2");
	nameValue.removeClass("iGrays");
	$(".provinceCityAll").hide();
	var rtn = p + "-" + c + "-" + a;
	$("input.current2").val(rtn);
	$(".backifname").hide();
	var nameValue = $("input.current2").attr("name");
	if (nameValue == "consignor.addrProCity") {
		$("#provinceId").val(allProId);
		$("#cityId").val(cityIdAll);
	}
	if (nameValue == "order.caddrProCity")
	 {
		$("input[name='order.caddrProCity']").trigger("change");
	}
	if (nameValue == "consigneeInfo.addrProCity")
	 {
		$("input[name='consigneeInfo.addrProCity']").trigger("change");
	}
	if (nameValue == 'template.caddrProCity')
	 {
		$("input[name='template.caddrProCity']").trigger("change");
	}
}
