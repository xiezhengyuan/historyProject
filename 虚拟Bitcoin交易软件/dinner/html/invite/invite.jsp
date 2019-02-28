<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		随机值设定
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			查看
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_company">
   <form class="form-horizontal" role="form" id="form_editcompany">
   <input type="hidden" class="form-control" id="companyid" name="id" placeholder="">
   
   	
			
					<!--  <div class="form-group">
					<label for="username" class="col-sm-2 control-label">邀请成功获得比特币数量随机值设定</label>
					<h2 style="text-align:center">邀请成功获得比特币奖励值</h2>
					</div> -->
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">比特币手续费</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="charge"  id="charge" style="width:100px;  placeholder="">
					</div>
					</div>
					<div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editcharge">修改</button>
	</div>
					<hr style="height:1px;border:none;border-top:1px solid red;" />
					
				 
					<div class="form-group">
					<label for="company" class="col-sm-2 control-label">比特币奖励值</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="lowest"  id="lowest" style="width:100px;  placeholder="">
					</div>
					</div>
			
			
			<div class="form-group" style="display:none">
					<label for="company" class="col-sm-2 control-label">id</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bonusid"  id="bonusid" style="width:100px;  placeholder="">
					</div>
					</div>
					
					
					
  
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







$._ajax({
			 url:Q.url+"/orderinfo/querybonus.action",
			data:{},
				  success:function(d){
				   
					   $('#lowest').val(d.lowest);
					  
					   $('#bonusid').val(d.bonusid);
				    } 
				})
				
	$._ajax({
			 url:Q.url+"/orderinfo/querycharge.action",
			data:{},
				  success:function(d){
				   
					   $('#charge').val(d.charge);
					  
					   
				    } 
				})

function returnbfbtn(){
	var from = "usermanager/userinfo.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editcompany').click(function(){
	if(!$('#form_editcompany').checkForm())return;
	var o = {};
	
	o.lowest=$("#lowest").val();
	if(isNaN(Number($("#lowest").val()))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	o.bonusid=$("#bonusid").val();
	
	
	$._ajax({
		url: Q.url+"/orderinfo/modifybonus.action",
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


$('#editcharge').click(function(){
	if(!$('#form_editcompany').checkForm())return;
	var o = {};
	
	o.charge=$("#charge").val();
	if(isNaN(Number($("#charge").val()))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	
	
	
	$._ajax({
		url: Q.url+"/orderinfo/modifycharge.action",
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
</script>