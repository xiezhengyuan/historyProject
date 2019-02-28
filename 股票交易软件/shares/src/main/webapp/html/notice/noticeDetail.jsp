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
					<label for="postingscontent" class="col-sm-2 control-label">帖子内容</label>
					<div class="col-sm-10">
					
					<textarea rows="" cols=""  style="width:800px" name="postingscontent" disabled="disabled"  id="postingscontent" placeholder="">
					</textarea>
					</div>
					</div>
			
	
	</form>
  </div>
 
</div>

<script>


var id= '<%=request.getParameter("id")%>';
querygoodsphotos();


function querygoodsphotos(){

	$._ajax({
		url:Q.url+"/notic/queryNoticeById.action",
		data:{"id":id},
		success:function(data){
			
				
				$("#postingscontent").val(data.detail);
				
			
		}
	})
}
//////////////////
function returnbfbtn(){
	var from = "notice/notice.jsp";
	Q.viewJsp(Q.page_content,from);
}







</script>