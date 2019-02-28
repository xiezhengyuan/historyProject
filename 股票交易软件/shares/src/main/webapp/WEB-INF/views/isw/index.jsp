<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.hxy.isw.entity.AccountInfo"%>
<c:set var="app"><%= request.getContextPath() %></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理系统</title>
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
		
		<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		
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
							管理系统
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

								<li>
									<a href="profile.html">
										<i class="ace-icon fa fa-user"></i>
										文件
									</a>
								</li> -->

								<!-- <li class="divider"></li> -->
								<li>
									<a href="javascript:;" id="update_password">
										<i class="ace-icon fa fa-edit"></i>
										修改密码
									</a>
								</li> 

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
					
					<c:forEach items="${menu}" var="menu">
						<li class="">
							<a href="javascript:;" vhref="${menu.url }" vid="1" vname="${menu.menuname }" class="dropdown-toggle">
								<i class="menu-icon fa ${menu.img }"></i>
								<span class="menu-text">
									${menu.menuname }
								</span>
	
							</a>
	
						</li>
					</c:forEach>
					
					
					<!-- <li class="">
						<a href="javascript:;" vhref="employee/employee.jsp" vid="1" vname="员工管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-group"></i>
							<span class="menu-text">
								员工管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="user/userinfo.jsp" vid="2" vname="用户管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-user"></i>
							<span class="menu-text">
								用户管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="proxymanager/proxymanager.jsp" vid="3" vname="代理管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-refresh"></i>
							<span class="menu-text">
								代理管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="wallet/walletmanager.jsp" vid="4" vname="融金宝管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-random"></i>
							<span class="menu-text">
								融金宝管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="goods/goodsmanager.jsp" vid="5" vname="商品管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-gift"></i>
							<span class="menu-text">
								商品管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="model/userinfo.jsp" vid="5" vname="小店管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-shopping-cart"></i>
							<span class="menu-text">
								小店管理
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="order/orderinfo.jsp" vid="6" vname="订单物流" class="dropdown-toggle">
							<i class="menu-icon fa fa-list-alt"></i>
							<span class="menu-text">
								订单物流
							</span>

						</a>

					</li>

					<li class="">
						<a href="javascript:;" vhref="order/orderinfo_tuihuo.jsp" vid="7" vname="退款售后" class="dropdown-toggle">
							<i class="menu-icon fa fa-backward"></i>
							<span class="menu-text">
								退款售后
							</span>

						</a>

					</li>

					

					<li class="">
						<a href="javascript:;" vhref="cash/cashinfo.jsp" vid="8" vname="提现管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-clock-o"></i>
							<span class="menu-text">
								提现管理
							</span>

						</a>

					</li>
					
					<li class="">
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
                                      
                                      
                                      <li class="">
						<a href="javascript:;" vhref="cash/cashinfo.jsp" vid="8" vname="提现管理" class="dropdown-toggle">
							<i class="menu-icon fa fa-clock-o"></i>
							<span class="menu-text">
								提现管理
							</span>

						</a>

					</li> 
                                       
                         </ul>

					</li>
					
					
					
					
					<li class="">
						<a href="javascript:;" vhref="notice/noticeinfo.jsp" vid="10" vname="发布公告" class="dropdown-toggle">
							<i class="menu-icon fa fa-share-square-o"></i>
							<span class="menu-text">
								发布公告
							</span>

						</a>

					</li>
					<li class="">
						<a href="javascript:;" vhref="systemset/set.jsp" vid="11" vname="设置" class="dropdown-toggle">
							<i class="menu-icon fa fa-cog"></i>
							<span class="menu-text">
								设置
							</span>

						</a>

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

							
							<li class="active" id="pageheader"></li>
						</ul><!-- /.breadcrumb -->

						<!-- <div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div> --><!-- /.nav-search -->
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
		
		
		<div class="modal fade" id="updatepwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h4 class="modal-title" id="myModalLabel">
								修改密码
							</h4>
						</div>
						<div class="modal-body" >
							<div class="form-horizontal" role="form" id="form_updatepwd">
								<div class="form-group">
									<label for="name" class="col-sm-2 control-label">原密码</label>
									<div class="col-sm-10">
										<input type="password" class="form-control" name="password" msg="请输入原密码" id="password"  placeholder="请输入原密码">
									</div>
								</div>
							
								<div class="form-group">
									<label for="username" class="col-sm-2 control-label">新密码</label>
									<div class="col-sm-10">
										<input type="password" class="form-control" name="newpassword" msg="请输入新密码" id="newpassword"  placeholder="请输入新密码">
									</div>
								</div>
								 
								<div class="form-group">
									<label for="mobile" class="col-sm-2 control-label">确认密码</label>
									<div class="col-sm-10">
										<input type="password" class="form-control" name="newpassword1" msg="请确认新密码" id="newpassword1" placeholder="请确认新密码">
									</div>
								</div>
							</div>
						</div>
					<div class="modal-footer">
							<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary btn-sm" id="updatepassword">提交</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal -->
			</div>
		
		
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
			//$(Q.page_content).html("<div align='center'  style='color: blue;font-size:xx-large;padding: 15% 10% 10% 10%;'>欢迎访问管理系统</div>");
			//$(Q.page_content).append("");	
			var role='${sessionScope.loginEmp.role}'
			if(role=='0'||role=='1'){
				$(Q.page_content).html("<div align='center'  style='color: blue;font-size:xx-large;padding: 15% 10% 10% 10%;'>欢迎访问管理系统</div>");
			}else{
				$(Q.page_content).append("<table class='table table-hover' style='width: 40%;text-align: center;'>"
				+"<tr>"
				+"<th colspan='2' style='text-align: center;'>基本信息</th>"
				+"</tr>"
				+"<tr>"
				+"<td>名称</td>"
				+"<td>${sessionScope.loginEmp.name }</td>"
				+"</tr>"
				+"<tr>"
				+"<td>联系方式</td>"
				+"<td>${sessionScope.loginEmp.mobile }</td>"
				+"</tr>"
				+"<tr>"
				+"<td>账号</td>"
				+"<td>${sessionScope.loginEmp.username }</td>"
				+"</tr>"
				+"<tr>"
				+"<td>分成比例</td>"
				+"<td>${sessionScope.loginEmp.proportion }</td>"
				+"</tr>"
				+"</table>");	
			}
			
			/* 
			<table class="table table-striped" style="width: 40%;text-align: center;">
			<tr>
				<th colspan="2" style="text-align: center;">基本信息</th>
			</tr>
			<tr>
				<td>经理名称</td>
				<td>${sessionScope.loginEmp.name }</td>
			</tr>
			<tr>
				<td>联系方式</td>
				<td>${sessionScope.loginEmp.mobile }</td>
			</tr>
			<tr>
				<td>账号</td>
				<td>${sessionScope.loginEmp.username }</td>
			</tr>
			<tr>
				<td>分成比例</td>
				<td>${sessionScope.loginEmp.proportion }</td>
			</tr>
		</table>
			 */
		}
		
		
		$("#update_password").click(function(){
			$('#updatepwd').modal('show');
		});
			
		$('#updatepassword').click(function(){
			if(!$('#form_updatepwd').checkForm())return;
			var o = $('#form_updatepwd').serializeObject();
			$._ajax({
				url: Q.url+"/isw/updatepwd.action",
				data:o,
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.status == '1' ){
							Q.toastr("提示","操作成功,请重新登录",'success');
							//reload
							$('#updatepwd').modal('hide');
							window.location.reload();
						}else if(data.status == '-3')
							Q.toastr("提示",data.info,'error');
						else
							Q.toastr("提示","操作失败",'error');
					}else {
						Q.toastr("提示","操作失败",'error');
					}
				}
			});
		})
		</script>
		
	</body>
</html>
