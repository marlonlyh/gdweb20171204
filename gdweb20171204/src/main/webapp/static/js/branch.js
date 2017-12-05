$(document).ready(function() {
 //默认打开第一个
                        $(".branchlist li:first-child .txt").show();
                        //绑定展开和收缩事件
                        $(".branchlist li h4").bind("click", function() {
                            if ($(this).next(".txt").css("display") == "none") {
                                $(".branchlist .txt").hide();
                                $(this).next(".txt").show();
                            }
                            else {

                                $(this).next(".txt").hide();
                            }

                        });

                        var default_height = $(".branchlist").height();
                        function openAll() {
                            $(".branchlist .txt").show();
                        }
                        function closeAll() {
                            $(".branchlist .txt").hide();
                        }
						});	