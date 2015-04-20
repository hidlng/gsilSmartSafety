<!doctype html>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<meta charset="utf-8" />
		<!-- 구 버전의 인터넷 익스플로러에서 HTML5 태그를 인식하게 합니다. -->
		<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<!-- ex pie -->
		<!-- vender freefix -->
		<script src="prefixfree.min.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimun-scale=1.0, user-scalable=no" /> 
		<link rel="stylesheet" type="text/css" href="css/home_layout.css">
		<!-- <link rel="stylesheet" media="only screen and (min-width:600px) and (max-width:999px)" type="text/ccss/home_layoutcss/home_layout_999.css" /> -->
		<link rel="stylesheet" media="only screen and (min-width:320px) and (max-width:480px)" type="text/css" href="css/home_layout_320.css" />
		<title>SmartSafety</title>

		
		<!-- 갤러리 시작 -->
		<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
			$(function() {

				var curIndex = 0;
				var nImage = $("#mover").children("li").length;
				curIndex = $(".view").index();
				var timer = setInterval(moveGallery, 50000);

				function moveGallery() {
					//alert("move")
					var prevSelBtnIdx = $(".now").index();
					curIndex++;

					setClassName();

					slideImage();

					setBottomBtns(prevSelBtnIdx, curIndex);
				}


				$("#lft").click(function() {

					var prevSelBtnIdx = $(".now").index();
					console.log(prevSelBtnIdx);
					curIndex++;

					setClassName();

					slideImage();

					setBottomBtns(prevSelBtnIdx, curIndex);
				});
				$("#rgt").click(function() {
					var prevSelBtnIdx = $(".now").index();
					console.log(prevSelBtnIdx);
					curIndex--;

					setClassName();

					slideImage();

					setBottomBtns(prevSelBtnIdx, curIndex);
				});
				function setClassName() {
					console.log("scn: " + curIndex);
					$("#outer").children("ul").children("li").removeClass("view");
					$("#outer").children("ul").children("li").eq(curIndex).addClass("view");
				}

				function slideImage() {

					if(curIndex < 0) {
						curIndex = nImage - 1;

					} else if(curIndex > nImage - 1) {
						curIndex = 0;

					}
					$("#mover").animate({
						left : -800 * curIndex
					});
				}

				function setBottomBtns(prevIndex, selectIndex) {

					$("ol").children("li").eq(prevIndex).removeClass("now");

					$("ol").children("li").eq(selectIndex).addClass("now");
				}


				$("ol").children("li").click(function() {

					var selIndex = $(this).index();
					var prevSelIndex = $(".now").index();

					setBottomBtns(prevSelIndex, selIndex);
					curIndex = selIndex;

					slideImage();
					clearInterval(timer);
					timer = setInterval(moveGallery, 50000);

				});
			});

		</script>
		<!-- 갤러리 끝 -->
		

	</head>
	<body>
		<header>
			<h1><img src="images/smartlogo.png" width="100px" height="50px"></h1>
			<nav>
				<ul id="nav">
					<li class="current">	<a href="#section-1">home</a></li>
					<li ><a href="#section-5">contact</a></li>
				</ul>
			</nav>
		</header>
		
		<section id="container"  >
			<div class="section" id="section-1" >				
				<img src="images/home/smartsafety.png" width="280px" height="80px">
				<p class="logoB" ><img src="images/home/logo_bg.png" alt="logo"  style="max-width:800px;width:80%"/></p>
			</div>

			<div class="section" id="section-5">
				<div id="contOuter">
					<h2><strong>contact</strong></h2>					
					<iframe src="http://dna.daum.net/include/tools/routemap/map_view.php?width=800&height=350&latitude=37.524836070239935&longitude=127.02876822141655&zoom=3" width="800" height="385" scrolling="no" frameborder="0"></iframe>
					
					<div class="bg_tr">
						<p class="add">
							<strong>주소</strong> : 서울특별시 강남구 논현로 854 KT신사지사 4층<br />
							<strong>전화번호</strong> : TEL. 02-2233-1038  /  FAX. 02-2233-1059<br />							
						</p>
						<p class="trap" >
							<strong>오시는 길</strong><br />	지하철 : 압구정역(3호선) 도보 1분<br />
							버스 : 압구정역 3번출구 - 간선 147,148,463 / 지선 4211 / 직행 6800
						</p>
					</div>
				</div>
			</div>
		</section>
		<footer>
			<div id="footer">
				<div id="contOuter-bottom">
					<p >
						<span class="copy"> Copyright(c) SmartSafety All Right Reserved.</span>
						<br />
						SmartSafety Co., Ltd.(주) 스마트세이프티 T:02-2233-1038  F:02-2233-1059  서울특별시 강남구 논현로 854 KT신사지사 4층
						<br />
						www.smart-safety.co.kr / ssssbw12@naver.com / T : 010-8584-0900
					</p>
				</div>
		</footer>


		<!-- 원페이지 스크롤 시작 -->
		<script src="js/jquery.js"></script>
		<script src="js/jquery.scrollTo.js"></script>
		<script src="js/jquery.nav.js"></script>
		<script>
			$(document).ready(function() {
				$('#nav').onePageNav({
					begin : function() {
						console.log('start');
					},
					end : function() {
						console.log('stop');
					},
					scrollOffset : 30
				});

			});

		</script>
		<!-- 원페이지 스크롤 끝 -->
		
    
		   
	<script type="text/javascript">

	function openLayer(targetID, options){
	
		var $layer = $('#poster');
		var $close = $layer.find('.close');
		var width = $layer.outerWidth();
		var ypos = options.top;
		var xpos = options.left;
		var marginLeft = 0;
		
		if(xpos==undefined){
			xpos = '50%';
			marginLeft = -(width/2);
		}

		
			$layer.css({'top':ypos+'px','left':xpos,'margin-left':marginLeft})
				if(targetID=="poster") {
					var src = "//www.youtube.com/embed/gEKLgC8EPe8?feature=player_detailpage&autoplay=1";
				} else if(targetID=="poster2") {
					var src = "//www.youtube.com/embed/71PORhZy39A?feature=player_detailpage&autoplay=1";
				} else if(targetID=="poster3") {
					var src = "//www.youtube.com/embed/ZQzsOU6MAKg?feature=player_detailpage&autoplay=1";
				} else if(targetID=="poster4") {
					var src = "//www.youtube.com/embed/qJWgRIAvUmM?feature=player_detailpage&autoplay=1";
				}
				document.getElementById("i_poster").src = src;
				$layer.show();

				
		

		$close.bind('click',function(){
			if($layer.is(':visible')){
				document.getElementById("i_poster").src = "";
				$layer.hide();
			}
			return false;
		});
	}

</script>

</div>
</body>
</html>
