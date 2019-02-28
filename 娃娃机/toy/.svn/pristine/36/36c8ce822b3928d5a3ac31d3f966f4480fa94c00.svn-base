<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>
<div class="page-header">
	<h1>
		娃娃机-查看/修改
		
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_machineinfo">
   <form class="form-horizontal" role="form" id="form_addmachineinfo" style="">
   <input type="hidden" class="form-control" id="id" name="id" placeholder="">
   
   				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">设备编号</label>
					<div class="col-sm-10" style="width: 20%">
						<input type="text" class="form-control" name="machineno" msg="请输入设备编号" id="machineno"  placeholder="">
					</div>
				</div>
   				
   				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">绑定玩具</label>
					<div class="col-sm-10" style="width: 20%">
						<select  class="form-control" id="ftoysid" name="ftoysid" msg="请选择玩具" vtype="combo" onchange="getprice()">
						</select> 
					</div>
				</div>
				
				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">价格(喵币)</label>
					<div class="col-sm-10" style="width: 20%">
						<input type="text" class="form-control" name="price"  msg="请输入价格" id="price"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="stock" class="col-sm-2 control-label">玩具数量</label>
					<div class="col-sm-10" style="width: 20%">
					<input type="text" class="form-control" name="stock" msg="请输入玩具数量" id="stock"  placeholder="">
					</div>	
				</div>
				
				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">人气</label>
					<div class="col-sm-10" style="width: 20%">
						<input type="text" class="form-control" name="popularity" id="popularity"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">状态</label>
					<div class="col-sm-10" style="width: 20%">
						<!-- <input type="text" class="form-control" name="state" id="state"  placeholder=""> -->
						<select id="state" name="state">
						<option value="-1">已下架</option>
						<option value="0">空闲中</option>
						<option value="1">正在使用</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">观看人数</label>
					<div class="col-sm-10" style="width: 20%">
						<input type="text" class="form-control" name="views" id="views"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="ftoysid" class="col-sm-2 control-label">预约人数</label>
					<div class="col-sm-10" style="width: 20%">
						<input type="text" class="form-control" name="subscribe" id="subscribe"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="facevideo" class="col-sm-2 control-label">正面直播流地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" value="正面直播流地址" name="facevideo"  readonly="readonly" msg="请输入正面直播流地址" id="macfacevideohineno"  placeholder="">
					</div>	
				</div>
			
				  <div class="form-group">
				    <label for="sidevideo" class="col-sm-2 control-label">侧面直播流地址</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" value="正面直播流地址" name="sidevideo" readonly="readonly" msg="请输入侧面直播流地址" id="sidevideo"  placeholder="">
				    </div>
				  </div>
				  
  <div class="modal-footer" style="text-align: center;">
		<button type="button" class="btn btn-primary btn-sm" onclick="returnbfbtn(this)">返回</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-primary btn-sm" id="addtoys">确认修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
</div>
<script>

var machineinfoid = '<%=request.getParameter("machineinfoid")%>'; 
var mydata=[];
//玩具类型
$._ajax({
		url: Q.url+"/machine/findAllToys.action",
		data:{"machineinfoid":machineinfoid},
		success: function(data){
			mydata=data.rows;
			var opts = '';
            if(data.rows.length>0){
            	
            	opts+='<option value="0" >请选择</option>';
            	for(var i=0;i<data.rows.length;i++){
    				var row = data.rows[i];
    				opts+= '<option value="'+row.id+'" >'+row.name+'</option>';
    			}
			}else{
				opts='<option value="0">该代理还没有添加任何娃娃</option>';
			}
			
			$("#ftoysid").html(opts);
			
		}
	});


function getprice(){
	var id =$("#ftoysid").val()

	for(var i=0;i<mydata.length;i++){
		if(mydata[i].id==id){
			
			$("#price").val(mydata[i].price);
			return;
		}
		
	}
}


$("#id").val(machineinfoid);

Q.getObjById("MachineInfo",machineinfoid,function(d){
	$('#form_addmachineinfo').resetObjectForm(d);
})




//根据ID查询部门信息
 /* Q.getObjById("UserInfo",userinfoid,function(d){
	
	$('#form_editemp').resetObjectForm(d);
})  */

function returnbfbtn(){
	var from = "machine/machine.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#addtoys').click(function(){
	if(!$('#form_addmachineinfo').checkForm())return;
	var o = $('#form_addmachineinfo').serializeObject();
	
if($("input[name='isnew']").is(':checked')){
		o.isnew = 1;
	}else{o.isnew = 0;}
	if(o.state==1){
		Q.toastr("提示",'不能手动将机器状态改为正在使用','error');
	}
	
	$._ajax({
		url: Q.url+"/machine/bindToys.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					var from = "machine/machine.jsp";
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