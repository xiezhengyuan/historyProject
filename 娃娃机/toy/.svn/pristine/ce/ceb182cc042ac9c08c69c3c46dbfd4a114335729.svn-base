<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.hxy.isw.entity.BusinessInfo"%>
<c:set var="app"><%= request.getContextPath() %></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>娃娃机管理系统</title>
<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/font-awesome/4.2.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="assets/css/datepicker.min.css" />
		<link rel="stylesheet" href="assets/css/ui.jqgrid.css" />
		<link href="css/animate.min.css" rel="stylesheet">
		<link href="css/toastr.min.css" rel="stylesheet">
		<link href="css/sweetalert.css" rel="stylesheet">
		<!-- text fonts -->
		<link rel="stylesheet" href="assets/fonts/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<!-- <link rel="stylesheet" href="assets/css/jquery-ui-1.8.4.custom.css" class="ace-main-stylesheet" id="main-ace-style" /> -->

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="no-skin">
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header pull-left">
					<a href="javascript:;" class="navbar-brand" onclick="fileupload();">
						<small>
							<i class="fa fa-leaf"></i>
							娃娃机管理系统
						</small>
					</a>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎您,</small>
									${loginEmp.name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<!-- <li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										设置
									</a>
								</li>
-->
								<li>
									<a href="javascript:;" id="update_password">
										<i class="ace-icon fa fa-edit"></i>
										修改密码
									</a>
								</li> 

								<!-- <li class="divider"></li> -->

								<li>
									<a href="javascript:;" id="sys_exit">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<ul class="nav nav-list">
					<li class="">
						<a href="javascript:;">
							<i class="menu-icon fa fa-tachometer" style="text-align: center;color:#ffb752 ;"></i>
							<span class="menu-text" style="text-align: center;color: #ffb752 ;"> 导航菜单 </span>
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="javascript:;" vhref="user/userinfo.jsp" vid="1" vname="用户管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-group"></i>
							<span class="menu-text">
								用户管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="agent/agentapply.jsp" vid="2" vname="代理管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-user"></i>
							<span class="menu-text">
								代理管理
							</span>
						</a>

					</li>
					
					<li class="">
						<a href="javascript:;" vhref="posting/posting.jsp" vid="9" vname="帖子管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-list-alt"></i>
							<span class="menu-text">
								帖子管理
							</span>
						</a>

					</li>
					
					
					<li class="">
						<a class="dropdown-toggle" >
							<i class="menu-icon fa fa-money"></i>
							<span class="menu-text">
								设备管理
							</span>
						</a>
						<ul class="submenu nav-show" style="display: none;">
                        	<li class="">
								<a href="javascript:;" vhref="machine/machine.jsp" vid="3" vname="娃娃机管理" class="dropdown-toggle">
									<i class="menu-icon fa fa-user"></i>
									<span class="menu-text">
										娃娃机管理
									</span>
								</a>
							</li>
							<li class="">
								<a href="javascript:;" vhref="toy/toy.jsp" vid="4" vname="玩具管理" class="dropdown-toggle">
									<i class="menu-icon fa fa-user"></i>
									<span class="menu-text">
										玩具管理
									</span>
								</a>
							</li>
							<li>
								<a href="javascript:;" vid="5" vhref="toy/toystype.jsp" >
							      	<i class="menu-icon fa fa-money"></i>
							       	<span class="menu-text">
										玩具分类管理
									</span>
								</a>
								<b class="arrow"></b>
							</li>
                         </ul>
					</li>
					
					<!-- <li class="">
						<a href="javascript:;" vhref="user/userinfo.jsp" vid="2" vname="设备管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-user"></i>
							<span class="menu-text">
								设备管理
							</span>
						</a>
					</li> -->
					
					
					<li class="">
						<a href="javascript:;" vhref="deliveryapply/deliveryapply.jsp" vid="6" vname="物流管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-random"></i>
							<span class="menu-text">
								物流管理
							</span>
						</a>

					</li>
					
					<li class="">
						<a href="javascript:;" vhref="feedBack/feedBack.jsp" vid="7" vname="意见反馈" class="dropdown-toggle">
							<i class="menu-icon fa fa-share-square-o"></i>
							<span class="menu-text">
								意见反馈
							</span>
						</a>

					</li>
					
					<li class="">
						<a href="javascript:;" vhref="complain/systemappeal.jsp" vid="8" vname="申诉" class="dropdown-toggle">
							<i class="menu-icon fa fa-thumbs-down"></i>
							<span class="menu-text">
								申诉
							</span>
						</a>

					</li>
					
					<li class="">
						<a href="javascript:;" vhref="statistic/statistic.jsp" vid="9" vname="统计" class="dropdown-toggle">
							<i class="menu-icon fa fa-clock-o"></i>
							<span class="menu-text">
								统计
							</span>
						</a>

					</li>
					
					
					<li class="">
						<a href="javascript:;" vhref="edition/edition.jsp" vid="10" vname="版本管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-plus-square"></i>
							<span class="menu-text">
								版本管理
							</span>
						</a>

					</li>
					
					<li class="">
						<a href="javascript:;" vhref="banner/bannerimg.jsp" vid="11" vname="banner管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-plus-square"></i>
							<span class="menu-text">
								banner管理
							</span>
						</a>

					</li>

					
					<!-- <li class="">
						<a class="dropdown-toggle" >
							<i class="menu-icon fa fa-money"></i>
							<span class="menu-text">
								财务管理
							</span>
						</a>
						<ul class="submenu nav-show" style="display: none;">
                                       <li>
                                       <a href="javascript:;"  vid="16" vhref="wage/advance.jsp" >
                                          <i class="menu-icon fa fa-money"></i>
                                              工资预支管理
                                       </a>
                                       <b class="arrow"></b>
                                       </li>
                                       
                                       <li>
                                       <a href="javascript:;" vid="17" vhref="wage/wagesinfo.jsp" >
                                             <i class="menu-icon fa fa-money"></i>
                                              工资结算管理
                                       </a>
                                       <b class="arrow"></b>
                                       </li>
                         </ul>

					</li> -->
					
				</ul><!-- /.nav-list -->

				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="">首页</a>
							</li>

							
							<li class="active" id="pageheader">
							</li>
						</ul><!-- /.breadcrumb -->
						
					</div>
					<div class="page-content">
						<!-- <div class="ace-settings-container" id="ace-settings-container">
							<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
								<i class="ace-icon fa fa-cog bigger-130"></i>
							</div>

							<div class="ace-settings-box clearfix" id="ace-settings-box">
								

								<div class="pull-left width-50">
									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
										<label class="lbl" for="ace-settings-hover"> 子菜单悬停</label>
									</div>

									<div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
										<label class="lbl" for="ace-settings-compact"> 侧边栏紧凑</label>
									</div>

									
								</div>
							</div>
						</div> --><!-- /.ace-settings-container -->
						<div id="page-content-index">
						
						</div>
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="assets/js/jquery.2.1.1.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="assets/js/jquery.1.11.1.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		
			var Q = {};
			Q.url = "${app}";
			</script>
			<style>body{ overflow: auto !important;padding-right: 0!important;} /* .modal{ overflow: auto !important;} */ </style>
			<style>
        /*  body { padding: 30px }
        form { display: block; margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px }  */

        .progress { position:relative; width:100%; border: 1px solid #fff; padding: 0px; /* border-radius: 3px ;*/margin-bottom: -1px;height: 5px; }
        .bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
        .percent { position:absolute; display:inline-block; top:3px; left:48%; }
    </style>
		<script src="assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->
		<script src="assets/js/jquery.jqGrid.min.js"></script>
		<script src="assets/js/grid.locale-en.js"></script>
		<script src="js/bootbox.min.js"></script>
		<script src="js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
		<script src="js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" language="javascript" src="js/echarts.min.js"></script>
		<!-- ace scripts -->
		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		<script src="js/jquery.json-2.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/jquery-form.js"></script>
		   <script src="js/toastr.min.js"></script>
		<script src="js/sweetalert.min.js"></script>
		<script src="js/index.js"></script>
		<!-- inline scripts related to this page -->
		<!-- 引用控制层插件样式 -->
		<link rel="stylesheet" href="html/zyUpload/control/css/zyUpload.css" type="text/css">
		
		<!-- <script type="text/javascript" src="zyUpload/jquery-1.7.2.js"></script> -->
		<!-- 引用核心层插件 -->
		<script type="text/javascript" src="html/zyUpload/core/zyFile.js"></script>
		<!-- 引用控制层插件 -->
		<script type="text/javascript" src="html/zyUpload/control/js/zyUpload.js"></script>
		<!-- 引用初始化JS -->
		<script type="text/javascript" src="html/zyUpload/uploadimg.js"></script>
		 <script type="text/javascript">
		 
		 
		/* index();
		function index(){
			$._ajax({
				url:Q.url+"/html/wallet/walletstatistic.jsp",
				dataType:'text',
				success:function(html){
					$('#pageheader').html("统计");
					$(Q.page_content).html(html);
				}
			}) 
		} */
			
			
			welcome();
			function welcome(){
			$(Q.page_content).html("<div align='center'  style='color: blue;font-size:xx-large;padding: 15% 10% 10% 10%;'>欢迎访问抓抓乐管理系统</div>");
			}
		</script> 
		
		
	</body>
</html>
