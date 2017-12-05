$(document).ready(function(){
	
	var galleries = $('.ad-gallery').adGallery();
	 //配置相册切换特效
	 // slide-hori  滚动切换
     // slide-vert  上下切换
     // resize  缩小切换
     // fade  淡入淡出切换
	$(function(){galleries[0].settings.effect = "fade"})
    $('#toggle-slideshow').click(
      function() {
        galleries[0].slideshow.toggle();
        return false;
      }
    );
    $('#toggle-description').click(
      function() {
        if(!galleries[0].settings.description_wrapper) {
          galleries[0].settings.description_wrapper = $('#descriptions');
        } else {
          galleries[0].settings.description_wrapper = false;
        }
        return false;
      }
    );
})