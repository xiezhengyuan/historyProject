<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="modal inmodal fade" id="filemanager" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="width:900px;">
		<div class="modal-content" >
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					文件管理<font color="green" id="dialog_file_msg"></font>
				</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
			<div class="form-horizontal" role="form" id="adduser_form">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.username" msg="请输入用户名" id="username" vrule="tel" placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">性别</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="sex" name="sex">
				      <option value="1" selected>男</option>
				      <option value="2" >女</option>
				      </select>
				    </div>
				  </div>
				  <div class="form-group">
					<label for="msgconent" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
					<textarea rows="3" id="msgconent" style="width: 100%;" msg="请填写备注" placeholder="" name="msgconent"></textarea>
					</div>
					</div>
					<div class="form-group">
					<label for="msgconent" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="mypreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					 <!-- <div class="form-group">
					<label for="msgconent" class="col-sm-2 control-label">上传文件</label>
					<div class="col-sm-10">
				<div id="demo" class="demo"></div>   
				</div>
				</div>  -->
				
				
			</div>
			
			</div>
			<!-- 图片上传进度条 -->
			<div class="progress">
			    <div class="bar"></div >
			    <div class="percent">0%</div >
			</div>
			<div class="modal-footer ">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="addfile">提交</button>
			</div>
		</div>
		</div>
		</div>
<script>
$('#filemanager').modal('show');

Q.logimgs = [];
var oldarr = [];
var o = {};
o.id = 1;
o.url = "/file/1492564013623.jpg";
o.name = "1492564013623.jpg";
var o1 = {};
o1.id = 221;
o1.url = "/file/1492564013769.jpg";
o1.name = "1492564013769.jpg";
//oldarr[oldarr.length] = o1;
var o2 = {};
o2.id = 12;
o2.url = "/file/1492564013875.png";
o2.name = "1492564013875.png";
oldarr[oldarr.length] = o2;

var o3 = {};
o3.id = 21;
o3.url = "/file/1492564013875.png";
o3.name = "1492564013875.png";
Q.logimgs[Q.logimgs.length] = o;
Q.logimgs[Q.logimgs.length] = o1;
oldarr[oldarr.length] = o2;
oldarr[oldarr.length] = o3;

console.info("check..."+Q.checkarrsame(oldarr,Q.logimgs)); 


Q.viewlogimg("#mypreview");

//Q.zyUpload('#demo');

$('#addfile').click(function(){
	for(var x = 0;x< Q.logimgs.length;x++){
		console.info(Q.logimgs[x].id+":"+Q.logimgs[x].url);
	}
})
</script>
