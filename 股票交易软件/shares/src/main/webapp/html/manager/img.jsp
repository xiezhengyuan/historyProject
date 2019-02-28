<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>

<ul class="nav nav-tabs" role="tablist">
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
   <form class="form-horizontal" role="form" id="form_editgoods" style="">
   <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
					<div class="form-group">
					<label for="photourl" class="col-sm-2 control-label">图标</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					
					
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editgoods">保存</button>
	</div>
	</form>
  </div>

</div>

<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");

var id= '<%=request.getParameter("id")%>';
querygoodsphotos();

function querygoodsphotos(){
	if(Q.logimgs.length==1){
	
	$._ajax({
		url:Q.url+"/managercontrol/queryPhoto.action",
		data:{"id":id},
		success:function(data){
			if(typeof(data)=='object'){
			/* 	$('#editgoods_form').resetObjectForm(data); */
				
				var rows = data.rows;
				for(var x= 0;x<rows.length;x++){
					var o = {};
					o.id = rows[x].shares.id;
					var fileurl;
					if(rows[x].shares.img==''){
						fileurl =null;
					}else{
						fileurl = rows[x].shares.img;
					}
					
					
					o.url = fileurl;
					o.name = fileurl.substr( fileurl.lastIndexOf("/")+1); 
					
					Q.logimgs[Q.logimgs.length] = o;
					myarr[myarr.length] = o;
				}
				
				Q.viewlogimg("#goodspreview");
			}
		}
	})
	}	
}
//////////////////
function returnbfbtn(){
	var from = "manager/manager.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editgoods').click(function(){
	/*  if(!$('#form_editgoods').checkForm())return; */
	var o = $('#form_editgoods').serializeObject(); 
	
	/* var postingscontent=$("#postingscontent").val();
	var name=$("#name").val();
	var money=$("#money").val(); */
	if(Q.logimgs.length>1){
		 Q.toastr("提示","只能上传一张图片",'warning');
		return; 
	} 
	if(Q.logimgs.length==0||Q.logimgs.length==null){
		 Q.toastr("提示","请上传图片",'warning');
		return; 
	} 
	//检测图片数组数据是否发生变化
	var flag = Q.checkarrsame(myarr,Q.logimgs);
	o.imgarr = $.toJSON(Q.logimgs);
	o.id = id;
	
	
	o.flag = flag;
	
	$._ajax({
		url: Q.url+"/managercontrol/modifyPhoto.action",
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


/* Q.viewlogimg("#proxypreview"); */

/* //查看下级成员
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","proxymanager/lowerproxy.jsp?userinfoid="+userinfoid);
}) */
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state5","postings/postingscomments.jsp");
})
</script>