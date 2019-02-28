<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>
<div class="page-header">
	<h1>
		新增帖子
		
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
					<label for="postingscontent" class="col-sm-2 control-label">标题</label>
					<div class="col-sm-10">
				
					<input type="text" class="form-control" name="title" msg="请输入内容" id="title"  placeholder="">
					</div>
					</div>
				 <div class="form-group" >
					<label for="righttitle" class="col-sm-2 control-label">帖子分类</label>
					<div class="col-sm-10">
					<select  id="target1" class="selectpicker" >
						<option value="0" selected>请选择</option>
					</select>
					
					
						
					</div>
					</div> 
				<div class="form-group">
					<label for="postingscontent" class="col-sm-2 control-label">帖子内容</label>
					<div class="col-sm-10">
					<textarea rows="" cols=""  style="width:800px" name="postingscontent" msg="请输入内容" id="postingscontent" placeholder=""></textarea>
					
					<!-- <input type="text" class="form-control" name="postingscontent" msg="请输入内容" id="postingscontent"  placeholder=""> -->
					</div>
					</div>
			
					<div class="form-group">
					<label for="photourl1" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					
					
					<div class="form-group">
					<label for="" class="col-sm-2 control-label">下面为选填(请输入数字)：</label>
					<div class="col-sm-10">
				
					</div>
					</div>
					
					<div class="form-group">
					<label for="praise" class="col-sm-2 control-label">点赞数</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" style="width:100px" name="praise" msg="请输入点赞数" id="praise"  placeholder="">
					
					</div>
					
					</div>
					
					<div class="form-group">
					<label for="praise" class="col-sm-2 control-label">打赏数额</label>
					<div class="col-sm-10">
				
					<input type="text" class="form-control" style="width:100px" name="reward" msg="请输入打赏数额" id="reward"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="share" class="col-sm-2 control-label">分享数</label>
					<div class="col-sm-10">
				
					<input type="text" class="form-control" style="width:100px" name="share" msg="请输入分享数" id="share"  placeholder="">
					</div>
					</div>
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editgoods1">保存</button>
	</div>
	</form>
  </div>
 
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
</div>
<script>
querysupplier("#target1");
function querysupplier(selectedid){
	$._ajax({
		url: Q.url+"/postings/queryPostingStyle.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var postingstyle = data.rows[i].postingStyle;
				opts+= '<option value="'+postingstyle.id+'">'+postingstyle.name+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");

var id= '<%=request.getParameter("id")%>';
/* querygoodsphotos(); */


function querygoodsphotos(){
	$._ajax({
		url:Q.url+"/postings/queryTopicbyid.action",
		data:{"id":id},
		success:function(data){
			if(typeof(data)=='object'){
				$('#editgoods_form').resetObjectForm(data);
				
				var rows = data.rows;
				for(var x= 0;x<rows.length;x++){
					var o = {};
					o.id = rows[x].postingsPhotos.id;
					var fileurl = rows[x].postingsPhotos.photourl;
					o.url = fileurl ;
					o.name = fileurl.substr( fileurl.lastIndexOf("/")+1); 
					
					Q.logimgs[Q.logimgs.length] = o;
					myarr[myarr.length] = o;
				}
				
				Q.viewlogimg("#goodspreview");
			}
		}
	})
	$._ajax({
		url:Q.url+"/postings/queryTopicbyid2.action",
		data:{"id":id},
		success:function(data){
				
				/* $('#editgoods_form').resetObjectForm(data); */
				
				var rows = data.rows[0];
				
				$("#postingscontent").val(rows.postingscontent);
				
			
		}
	})
}
//////////////////
function returnbfbtn(){
	var from = "postings/postings.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editgoods1').click(function(){
	/*  if(!$('#form_editgoods').checkForm())return; */
	
	 var o = $('#form_editgoods').serializeObject();  
	
	var postingscontent=$("#postingscontent").val();
	var title=$("#title").val();
	var praise=$("#praise").val();
	var target1=$("#target1").val();
	if(target1==0){
		Q.toastr("提示","请选择分类",'warning');
		return;
	}
	if(title==null||title==""){
		
		Q.toastr("提示","请输入标题",'warning');
		return;
	}
if(postingscontent==null||postingscontent==""){
		
		Q.toastr("提示","请输入内容",'warning');
		return;
	}
	if(praise==null){
		praise=0;
	}else if(isNaN(Number(praise))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	var reward=$("#reward").val();
	if(reward==null){
		reward=0;
	}else if(isNaN(Number(reward))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	var share=$("#share").val();
	if(share==null){
		share=0;
	}else if(isNaN(Number(share))){
		Q.toastr("提示","请输入数字",'warning');
		return;
	}
	if(Q.logimgs.length==0){
		
	}
	//检测图片数组数据是否发生变化
	var flag = Q.checkarrsame(myarr,Q.logimgs);
	o.imgarr = $.toJSON(Q.logimgs);
	o.id = 0;
	o.postingscontent=postingscontent;
	o.praise=praise;
	o.share=share;
	o.reward=reward;
	o.flag = flag;
	o.title=title;
	o.fpostingstyleid=target1;
	$._ajax({
		url: Q.url+"/postings/addpostings.action",
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