$(document).ready(function(){
$(".selectmenu").click(function(event){
$(this).find(".popmenu").toggle();
	if ($(this).find(".popmenu").css("display")=="none") {$(this).find('p a').removeClass("xia");}

if ($(this).find(".popmenu").css("display")!="none") {
	$(this).find('p a').attr("class", "xia");
$(this).find(".popmenu a").click(function(){
var value=$(this).text();
$(this).parent().parent().find("p").find("a").text(value);

})

}


});
})