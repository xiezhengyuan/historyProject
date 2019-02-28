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
  <li role="presentation" class="active"><a href="#tab_userinfo" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_record" role="presentation"><a href="#tab_record" role="tab" data-toggle="tab">获得娃娃记录</a></li>
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_userinfo">
   <form class="form-horizontal" role="form" id="form_edituser">
   <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username" msg="请输入姓名" id="username"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">性别</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="sex" name="sex">
				      <option value="男" selected>男</option>
				      <option value="女" >女</option>
				      </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
					<label for="birthday" class="col-sm-2 control-label">生日</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="birthday" msg="请输入生日" id="birthday"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" msg="请输入手机号" id="mobile" placeholder="">
					</div>
					</div>
					
					 <div class="form-group">
					<label for="level" class="col-sm-2 control-label">等级</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="level" msg="请输入等级" id="level"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="balance" class="col-sm-2 control-label">喵币</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="balance" msg="请输入喵币" id="balance"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="freevoucher" class="col-sm-2 control-label">免玩劵数量</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="freevoucher" msg="请输入免玩劵数量" id="freevoucher"  placeholder="">
					</div>
					</div>
					
				  <div class="form-group">
					<label for="selfinfo" class="col-sm-2 control-label">个人说明</label>
					<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="selfinfo" msg="请输入个人说明" id="selfinfo"  placeholder=""> -->
					<textarea rows="" cols=""  name="selfinfo" msg="请输入个人说明" id="selfinfo"  placeholder="">
					</textarea>
					</div>
					</div>
					
					
					
					<div class="form-group">
					<label for="invitername" class="col-sm-2 control-label">邀请人</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="invitername" msg="请输入邀请人姓名" id="invitername"  placeholder="" readonly="readonly">
					</div>
					</div>
					
					<div class="form-group">
					<label for="invitertel" class="col-sm-2 control-label">邀请人电话</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="invitertel" msg="请输入邀请人电话" id="invitertel"  placeholder="" readonly="readonly">
					</div>
					</div>
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="edituserinfo">修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_totalgame"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_record"></div>
</div>
<script>

var userinfoid = '<%=request.getParameter("userinfoid")%>';

//根据ID查询信息
Q.getObjById("UserInfo",userinfoid,function(d){
	
	$('#form_edituser').resetObjectForm(d);
	var inviter = d.inviter;
	if(inviter==0){
		$('#invitername').val("无");
		$('#invitertel').val("无");
		return ;
	}
	
	Q.getObjById("UserInfo",inviter,function(da){
		$('#invitername').val((da.username)==null?"无":da.username);
		$('#invitertel').val((da.mobile)==null?"无":da.mobile);
	})
	
})

function returnbfbtn(){
	var from = "user/userinfo.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#edituserinfo').click(function(){
	if(!$('#form_edituser').checkForm())return;
	
	var o = $('#form_edituser').serializeObject();
	
	$._ajax({
		url: Q.url+"/userinfo/modifyuserinfo.action",
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

//查看下级成员
$('#panel_record').click(function(){
	Q.viewJsp("#tab_record","user/record.jsp?userinfoid="+userinfoid);
})
</script>