<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		用户信息
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			查看
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_company" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_lower" role="presentation"><a href="#tab_lower" role="tab" data-toggle="tab">通告列表</a></li>
  <li id="panel_transaction" role="presentation"><a href="#tab_transaction" role="tab" data-toggle="tab">订单列表</a></li>
  <!-- <li id="panel_user" role="presentation"><a href="#tab_user" role="tab" data-toggle="tab">用户统计</a></li> -->
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_company">
   <form class="form-horizontal" role="form" id="form_editcompany">
   <input type="hidden" class="form-control" id="companyid" name="id" placeholder="">
   
   	<!-- <div class="form-group">
					<label for="photourl" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" disabled="disabled" style="border:1px dashed  #bbb"></div>
					</div>
					</div> -->
				
				
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10" id="photo">
					<!-- <input type="text" class="form-control" name="photo"  id="photo" disabled="disabled"  placeholder=""> -->
					</div>
					</div>
						
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">账号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username"  id="username" disabled="disabled"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password"  id="password" disabled="disabled"  placeholder="">
					</div>
					</div>
				  
				<!--   <div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户头像</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="heading" msg="请输入负责人" id="heading"  placeholder="">
					</div>
					</div> -->
				  
				  <div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">手机</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" disabled="disabled"  id="mobile" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">昵称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="nickname" disabled="disabled"  id="nickname" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name" disabled="disabled"  id="name" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">个人简介</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="intro" disabled="disabled"  id="intro" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="email" disabled="disabled"  id="email" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">好评度</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="praise" disabled="disabled"  id="praise" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">信任人数</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="trust" disabled="disabled"  id="trust" placeholder="">
					</div>
					</div>
					
						<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">累计交易数量</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="amount" disabled="disabled"  id="amount" placeholder="">
					</div>
					</div>
					
						<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">第一次交易时间</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="time" disabled="disabled"  id="time" placeholder="">
					</div>
					
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">身份证</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="paparno" disabled="disabled"  id="paparno" placeholder="">
					</div>
					</div>
					
						<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">比特币地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="address" disabled="disabled"  id="address" placeholder="">
					</div>
					</div>
					
					<div>
					<label for="username" class="col-sm-2 control-label">总资产</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="allgolds" disabled="disabled"  id="allgolds"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">可用资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="ugolds" disabled="disabled"   id="ugolds"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">冻结资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bgolds" disabled="disabled"  id="bgolds"  placeholder="">
					</div>
					</div>
					
					<hr style="height:1px;border:none;border-top:1px solid red;" />
				
				  <div class="form-group">
					<label for="username" class="col-sm-2 control-label">充值比特币</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="recharge"  id="recharge"  placeholder="">
					</div>
					</div>
					
				<!-- 	<div class="form-group">
					<label for="password" class="col-sm-2 control-label">提现比特币</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="withdraw"  id="withdraw"  placeholder="">
					</div>
					</div> -->
					
					
					
					
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editcompany">修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
  
   <!-- panel4 start -->
  <div role="tabpanel" class="tab-pane" id="tab_user"></div>
</div>
<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");
//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */

var userid = '<%=request.getParameter("userid")%>';

//根据ID查询信息
Q.getObjById("UserInfo",userid,function(d){
	
	$('#username').val(d.username);
	$('#password').val(d.password);
	$('#mobile').val(d.mobile);
	$('#nickname').val(d.nickname);
	$('#name').val(d.name);
	$('#intro').val(d.intro);
	$('#email').val(d.email);
	$('#paparno').val(d.paparno);
	$('#allgolds').val(d.allgolds);
	$('#ugolds').val(d.ugolds);
	$('#bgolds').val(d.bgolds);
	$('#address').val(d.address);
	var p=d.praise/(d.praise+d.badpraise);
	var p1=p.toFixed(2)*100+"%";
	$('#praise').val(p1);
	
	/* var rows =d.headimg;
	
		var o = {};
		o.id = d.id;
		var fileurl =d.headimg ;
		o.url = fileurl;
		o.name = fileurl.substr( fileurl.lastIndexOf("/")+1); 
		
		Q.logimgs[Q.logimgs.length] = o;
		myarr[myarr.length] = o;
	
	
	Q.viewlogimg("#goodspreview"); */
	var headimg;
	if(d.headimg==null){
		headimg=""
	}
	headimg=d.headimg
 	var photourl =headimg ;
   	var url=photourl.split(",");
   	var c='';
  	 c+='<img src = "'+Q.url+url[0]+'" width="100" height="100"'+'complete="complete">'; 
  	$('#photo').html(c);
	
})

$._ajax({
			 url:Q.url+"/manageuserinfo/querydetail.action",
			data:{"id":userid},
				  success:function(d){
				   
					   $('#trust').val(d.trust);
					   $('#amount').val(d.amount);
					   $('#time').val(d.time);
				    } 
				})

function returnbfbtn(){
	var from = "usermanager/userinfo.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editcompany').click(function(){
	/* if(!$('#form_editcompany').checkForm())return; */
	var o = {};
	o.userid = userid;
	/* o.allgolds=$("#allgolds").val();
	o.ugolds=$("#ugolds").val();
	o.bgolds=$("#bgolds").val();
	 */
	/*  if($("#recharge").val()!=""){
		
	 } */
	if(isNaN(Number($("#recharge").val()))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	/* if(isNaN(Number($("#withdraw").val()))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	} */
	 o.recharge=$("#recharge").val();
	 
	/*  o.withdraw=$("#withdraw").val(); */
	/* if($("#withdraw").val()!=""){
		 
	} */

	/* if($("#withdraw").val()!=""&&$("#recharge").val()!=""){
		Q.toastr("提示","不能同时修改",'error');
		return;
	} */
	if($("#recharge").val()==""){
		Q.toastr("提示","请输入数字",'error');
		return;
	}
	$._ajax({
		url: Q.url+"/manageuserinfo/modifyuserinfo.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
				}else if(data.op == 'fail')
					Q.toastr("提示",data.msg,'error');
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	});
	
	
})

//查看公司成员
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","usermanager/adver.jsp?userid="+userid);
})
//交易统计
$('#panel_transaction').click(function(){
	Q.viewJsp("#tab_transaction","usermanager/orderinfo.jsp?userid="+userid);
})
//用户统计
$('#panel_user').click(function(){
	Q.viewJsp("#tab_user","company/userstatistic.jsp?companyid="+companyid);
})
</script>