<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal  inmodal fade" id="adduser1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增定时公告
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_adduser1">
				
			
					<div class="form-group">
					<label for="righttitle" class="col-sm-2 control-label">菜单名称</label>
					<div class="col-sm-10">
					<select type="text" id="target3"  >
					<option value="0"  selected>所有人</option>
					
					<option value="1"  >所有用户  </option>
					<option value="2"  >所有公司 </option>
					<option value="3"  >指定公司 </option>
					</select>
						
					</div>
					</div>
					
					 <div class="form-group" style="display:none" id="ndiv">
					<label for="righttitle" class="col-sm-2 control-label">公司名称</label>
					<div class="col-sm-10">
					<select  id="target1" class="selectpicker" multiple data-hide-disabled="true" data-size="5" >
						<option value="0" selected>请选择</option>
					</select>
					
					</div>
					</div> 
					 <div class="form-group">
				<label for="noticename" class="col-sm-2 control-label" style="padding-left:0">发送时间</label>
				<div class="col-sm-10">
				<div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd HH:ii:ss">
					<input class="form-control" type="text" value=""  id="starttime" >
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
				</div>
			</div>
			<!-- 		<tr>
			<td><label for="fsupplier" class="control-label"  style="margin-left:5px;">公司名称：</label></td><td><select type="text" id="target1" ><option value="-1"  selected>请选择</option></select></td>
			
			</tr> -->
			
					 <div class="form-group">
					<label for="noticename" class="col-sm-2 control-label">公告标题</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="noticename" msg="请输入标题" id="noticename" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="noticecontent" class="col-sm-2 control-label">公告内容</label>
					<div class="col-sm-10">
					<textarea  class="form-control" name="noticecontent" msg="请输入内容" id="noticecontent" placeholder=""></textarea>
					
					</div>
					</div>
					<!-- <div class="form-group">
					<label for="msgconent" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div> -->
				
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveuser">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
querysupplier("#target1");
function querysupplier(selectedid){
	$._ajax({
		url: Q.url+"/notic/querycompony.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var company = data.rows[i].company;
				opts+= '<option value="'+company.id+'">'+company.company+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}
$("#target3").change(function(){
	if($("#target3").val()==3){
		$("#ndiv").show()
	}else{
		$("#ndiv").hide();
	}
	});

$('#target1').selectpicker({
    'selectedText': 'cat'
});
$('.dropdown-menu').css('overflow-y','')
$('.dropdown-menu').css('max-height','')
$('.dropdown-menu span').removeClass('pull-left')
$('#target1 option').eq(0).hide()

$('#adduser1').modal('show');

$('#saveuser').click(function(){
	if(!$('#form_adduser1').checkForm())return;
	if(!$('#form_adduser1').checkformatForm())return;
	
	
	var o = $('#form_adduser1').serializeObject();
	o.target=$("#target3").val();
	o.noticename=$("#noticename").val();
	if(o.noticename==null||o.noticename==""){
		Q.toastr("提示","操作失败",'error');
		return;
	}
	o.noticecontent=$("#noticecontent").val();
	if(o.noticecontent==null||o.noticecontent==""){
		Q.toastr("提示","操作失败",'error');
		return;
	}
	o.array=$("#target1").val()+"";
	o.sendtime=$("#starttime").val()+"";
	
	$._ajax({
		url: Q.url+"/notic/addtimnotice.action",
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
<script>
$(".form_date").datetimepicker({
	language:  'zh-CN',
    format: 'yyyy-MM-dd HH:ii:ss',
    autoclose: true,
    todayBtn: false,
    pickerPosition: "bottom-left",
    weekStart: 0,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0
}); 
</script>	