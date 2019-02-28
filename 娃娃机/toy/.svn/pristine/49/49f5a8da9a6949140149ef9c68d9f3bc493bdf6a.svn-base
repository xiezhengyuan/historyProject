<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addmachine" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增设备
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addmachine">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">设备编号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="machineno" msg="请输入设备编号" id="machineno"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">机器所属人</label>
					<div class="col-sm-10">
					<select id="empid" >
					  
					</select>
					</div>
				</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="savemachine">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addmachine').modal('show');

//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */
 
 $._ajax({
		url: Q.url+"/machine/selectagent.action",
		data:{},
		success: function(data){
			
			var option='<option value="1">我的 </option> ';
			for(var i=0;i<data.rows.length;i++){
				option+='<option value="'+data.rows[i].id+'">'+data.rows[i].name+' </option>';
			}
			$("#empid").html(option);
		}
	});

$('#savemachine').click(function(){
	if(!$('#form_addmachine').checkForm())return;
	
	var machineno=$.trim($("#machineno").val());
	var empid=$("#empid").val();
	if(machineno==''){
		Q.toastr("提示","请输入机器编号",'error');
		return;
	}
	
	Q.confirm("确定为 "+$("#empid").find("option:selected").text()+" 添加该机器么？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/machine/addmachine.action",
				data:{"machineno":machineno,"empid":empid},
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.op == 'success' ){
							Q.toastr("提示","操作成功",'success');
							//reloadgrid
							$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
							$('.close').click();
						}else if(data.op == 'fail')
							Q.toastr("提示",data.msg,'error');
						else
							Q.toastr("提示","操作失败",'error');
					}else {
						Q.toastr("提示","操作失败",'error');
					}
				}
			});
	
		}
	})
	
	
	
	
})
</script>