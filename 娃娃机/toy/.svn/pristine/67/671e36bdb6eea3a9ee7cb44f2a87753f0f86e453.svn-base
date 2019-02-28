<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
input{width:50%}
/* input:nth-child(2){width:80%} */
</style>
<div class="page-header">
	<h1 id="toystitle">
		修改banner
	</h1>
</div><!-- /.page-header -->

<div class="tab-content">

	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_userinfo">
   <form class="form-horizontal" role="form" id="form_addtoys" style="" >
   <input type="hidden" class="form-control" id="toysid" name="id" placeholder="">
				
					<div class="form-group">
					<label for="msgconent" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="toysspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
	
					</div>
  
  <div class="modal-footer" style="text-align: center;">
      <span style="margin-left: -80%;">请选择性输入图片跳转地址</span><br/><br/>
        <div id="div0">
             
        </div >
         <div id="div1">
             
           
        </div>
         <div id="div2">
              
            
          
        </div>
         <div id="div3">
              
            
        </div>
        <div id="div4">
              
        </div>
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
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#toysspreview"); 
/* 查询图片 */
$._ajax({
	url:Q.url+"/toy/querybannerimg.action",
	data:{},
	success:function(data){
		if(typeof(data)=='object'){
			$('#form_addtoys').resetObjectForm(data);
			
			var rows = data.rows;
			for(var x= 0;x<rows.length;x++){
				var o = {};
				o.id = rows[x].fileinfoid
				o.url = rows[x].url ;
				o.name = rows[x].url.substr( rows[x].url.lastIndexOf("/")+1); 
				
				Q.logimgs[Q.logimgs.length] = o;
				myarr[myarr.length] = o;
			}
			
			Q.viewlogimg("#toysspreview"); 
			textchange();
		}
	}
}) 

function textchange(){
	
	for(var i=0 ;i<Q.logimgs.length;i++){
		var html=$("#div"+i+"").html();
		if($.trim(html)==''){
			html+='图片'+Number(i+1)+'<select id="select'+i+'"  onchange="showinput(this)" >';
			html+='<option value="1">未选择</option> <option value="2">跳转到app内页面</option>'
			html+='<option value="3">手动输入url地址</option>  </select>  <span id="'+i+'"></span>';	 
	            
			$("#div"+i+"").html(html);	
		}
		

	}
	for(var i=Q.logimgs.length ;i<5;i++){
		
		$("#div"+i+"").html("");	

	}
}

function showinput(o){

	var value=$(o).val();
	if(value=='3'){
		 var id=$(o).next().attr("id");
		 $(o).next().html('<input  id="inp'+id+'" />')
	}else{
		 $(o).next().html('')
	}
}

$('#addtoys').click(function(){
		/* 添加 */
		if(!$('#form_addtoys').checkForm())return;
	
		if(Q.logimgs.length==0){
			Q.toastr("提示","请至少上传一张图片",'warning');
			return;
		}
		if(Q.logimgs.length>5){
			Q.toastr("提示","banner图片不可以多于5张",'warning');
			return;
		}
		var imgs='';
		for(var i=0 ;i<Q.logimgs.length;i++){
			var type= $('#select'+i+'').val();
			var url=$('#inp'+i+'').val();
			if(i==Q.logimgs.length-1){
				imgs+=Q.logimgs[i].id+"-"+Q.logimgs[i].url+"-"+type+"-"+url;
			}else{
				imgs+=Q.logimgs[i].id+"-"+Q.logimgs[i].url+"-"+type+"-"+url+",";
			}
		}
		
		$._ajax({
			url: Q.url+"/toy/updatebannerimgs.action",
			data:{"imgs":imgs},
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