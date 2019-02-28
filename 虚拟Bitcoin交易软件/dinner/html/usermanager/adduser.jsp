<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addcompany" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增用户
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addcompany">
				
				<div class="form-group">
					<label for="photourl" class="col-sm-2 control-label">头像</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					
					
				<div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">帐号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username1" msg="请输入密码" id="username1"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password1" msg="请输入密码" id="password1"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="name" class="col-sm-2 control-label">手机号码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile1" msg="请输入手机号码" id="mobile1"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">昵称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="nickname1" msg="请输入昵称" id="nickname1" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name1" msg="请输入姓名" id="name1" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="email1" msg="请输入邮箱" id="email1" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">身份证</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="paparno1" msg="请输入身份证" id="paparno1" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">比特币地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="address" msg="请输入身份证" id="address" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="postingscontent" class="col-sm-2 control-label">个人简介</label>
					<div class="col-sm-10">
					
					<textarea rows="" cols=""  style="width:450px" name="intro1"   id="intro1" placeholder=""></textarea>
					</div>
					</div>
					
					
					
					<hr style="height:1px;border:none;border-top:1px solid red;" />
					 <!-- <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">部门</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="fdepartmentid" name="fdepartmentid" msg="请选择部门" vtype="combo">
				      </select>
				    </div> 
				  </div>--> 
				   <div class="form-group">
					<label for="username" class="col-sm-2 control-label">总资产</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="allgolds1" msg="请输入总资产"  id="allgolds1"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">可用资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="ugolds1" msg="请输入可用资金"  id="ugolds1"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">冻结资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bgolds1" msg="请输入冻结资金"  id="bgolds1"  placeholder="">
					</div>
					</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary btn-sm" id="savecompany">确认新增</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");
$('#addcompany').modal('show');

$('#savecompany').click(function(){
	if(!$('#form_addcompany').checkForm())return;
	
	var o = {};
	o.username=$("#username1").val();
	o.password=$("#password1").val();
	o.mobile=$("#mobile1").val();
	o.nickname=$("#nickname1").val();
	o.name=$("#name1").val();
	o.email=$("#email1").val();
	o.paparno=$("#paparno1").val();
	o.intro=$("#intro1").val();
	o.ugolds=$("#ugolds1").val();
	o.allgolds=$("#allgolds1").val();
	o.bgolds=$("#bgolds1").val();
	o.address=$("#address").val();
	if(Q.logimgs.length>1){
		 Q.toastr("提示","只能上传一张哦",'warning');
		return; 
	}
	if(isNaN(o.mobile)){
		 Q.toastr("提示","请输入数字",'warning');
			return; 
	}
	if(isNaN(o.ugolds)){
		 Q.toastr("提示","请输入数字",'warning');
			return; 
	}
	if(isNaN(o.allgolds)){
		 Q.toastr("提示","请输入数字",'warning');
			return; 
	}
	if(isNaN(o.bgolds)){
		 Q.toastr("提示","请输入数字",'warning');
			return; 
	}
	o.imgarr = $.toJSON(Q.logimgs);
	$._ajax({
		url: Q.url+"/manageuserinfo/adduserinfo.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
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
</script>