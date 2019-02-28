<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>
<div class="page-header">
	<h1>
		评论详情
		
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
   <form class="form-horizontal" role="form" id="form_editgoods" style="">
   <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="postingscontent" class="col-sm-2 control-label">评论内容</label>
					<div class="col-sm-10">
					
					<textarea rows="" cols=""  style="width:800px" name="postingscontent" msg="请输入内容" id="postingscontent" placeholder="">
					</textarea>
					</div>
					</div>
			
					
					
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editgoods">修改</button>
	</div>
	</form>
  </div>
 
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
</div>
<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");

var id= '<%=request.getParameter("id")%>';
querygoodsphotos();


function querygoodsphotos(){

	$._ajax({
		url:Q.url+"/postings/querycommentbyid.action",
		data:{"id":id},
		success:function(data){
				
				/* $('#editgoods_form').resetObjectForm(data); */
				
				var name = data.name;
				
				$("#postingscontent").val(name);
				
			
		}
	})
}
//////////////////
function returnbfbtn(){
	var from = "postings/postings.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editgoods').click(function(){
	/*  if(!$('#form_editgoods').checkForm())return; */
	var o = $('#form_editgoods').serializeObject(); 
	
	var postingscontent=$("#postingscontent").val();
	
	o.id = id;
	o.postingscontent=postingscontent;
	
	
	
	$._ajax({
		url: Q.url+"/postings/modifypostingcoment.action",
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


Q.viewlogimg("#proxypreview");

//查看下级成员
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","proxymanager/lowerproxy.jsp?userinfoid="+userinfoid);
})
</script>