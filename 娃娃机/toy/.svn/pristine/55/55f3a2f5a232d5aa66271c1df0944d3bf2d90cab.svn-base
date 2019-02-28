<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>
<div class="page-header">
	<h1>
		绑定玩具
		
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_userinfo">
   <form class="form-horizontal" role="form" id="form_addtoys" style="">
   <input type="hidden" class="form-control" id="id" name="id" placeholder="">
   
   				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">绑定玩具</label>
					<div class="col-sm-10" style="width: 20%">
						<select  class="form-control" id="ftoysid" name="ftoysid" msg="请选择玩具" vtype="combo">
						</select> 
					</div>
					<div class="col-sm-10" style="width: 20%"> 
				      <input id="isnew" name="isnew" type="checkbox" value="" />是否上新
					</div>
				</div>
				
				<div class="form-group">
					<label for="stock" class="col-sm-2 control-label">玩具数量</label>
					<div class="col-sm-10" style="width: 20%">
					<input type="text" class="form-control" name="stock" msg="请输入玩具数量" id="stock"  placeholder="">
					</div>	
				</div>
   
				<div class="form-group">
					<label for="facevideo" class="col-sm-2 control-label">正面直播流地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="facevideo" msg="请输入正面直播流地址" id="macfacevideohineno"  placeholder="">
					</div>	
				</div>
			
				  <div class="form-group">
				    <label for="sidevideo" class="col-sm-2 control-label">侧面直播流地址</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" name="sidevideo" msg="请输入侧面直播流地址" id="sidevideo"  placeholder="">
				    </div>
				  </div>
				  
  <div class="modal-footer" style="text-align: center;">
		<button type="button" class="btn btn-primary btn-sm" id="addtoys">确认添加</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
</div>
<script>
Q.logimgs = [];
Q.viewlogimg("#toysspreview");

//玩具类型
/* Q.getToys1("#ftoysid"); */
 $._ajax({
		url: Q.url+"/device/findAllToys.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = '<option value="0">请选择</option>';
			for(var i=0;i<data.rows.length;i++){
				var row = data.rows[i];
				opts+= '<option value="'+row.id+'">'+row.name+'</option>';
			}
			$("#ftoysid").html(opts);
			
			Q.toys = data;
		}
	});
 

var machineinfoid = '<%=request.getParameter("machineinfoid")%>'; 

$("#id").val(machineinfoid);

//根据ID查询部门信息
 /* Q.getObjById("UserInfo",userinfoid,function(d){
	
	$('#form_editemp').resetObjectForm(d);
})  */

function returnbfbtn(){
	var from = "device/machine.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#addtoys').click(function(){
	if(!$('#form_addtoys').checkForm())return;
	var o = $('#form_addtoys').serializeObject();
	
if($("input[name='isnew']").is(':checked')){
		o.isnew = 1;
	}else{o.isnew = 0;}
	/* 
	if(Q.logimgs.length==0){
		Q.toastr("提示","请至少上传一张图片",'warning');
		return;
	}
	o.imgarr = $.toJSON(Q.logimgs);
	o.thumbnail = Q.logimgs[0].url;
	o.ffileinfoid = Q.logimgs[0].id; */
	$._ajax({
		url: Q.url+"/device/bindToys.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					var from = "device/machine.jsp";
					Q.viewJsp(Q.page_content,from);
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