<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		banner管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="row">
		<div class="col-xs-12">
			
			<div class="modal-header">
			
			
		
<!-- 	<div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addcompany()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus">新增图片</span>
	</button>  -->
	
	<!-- </div>
	<div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="modifycompany()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus">修改图片</span>
	</button> 
	
	</div> -->
	</div>
<form class="form-horizontal" role="form" id="form_editgoods" style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					修改添加图片
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addcompany1">
				
				<div class="form-group">
					<label for="photourl" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					
				
	
			</div>
			</div>
		<div class="modal-footer">
				<!-- <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button> -->
				<button type="button" class="btn btn-primary btn-sm" id="savecompany">确认修改</button>
			</div>
			</form>
<!-- 
								jqgrid 用户列表
							<table id="company_grid_table"></table>
							jqgrid 页码栏
							<div id="company_grid_pager"></div>

							PAGE CONTENT ENDS
						</div>/.col
					</div>/.row
					 -->
<!-- 新增用户（Modal） -->
<div id="div_addcompany" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_company" ></div>		
<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");
$('#addcompany1').modal('show');
querygoodsphotos();


function querygoodsphotos(){
	$._ajax({
		url:Q.url+"/orderinfo/querybanner.action",
		data:{},
		success:function(data){
			if(typeof(data)=='object'){
				$('#editgoods_form').resetObjectForm(data);
				
				var rows = data.rows;
				for(var x= 0;x<rows.length;x++){
					var o = {};
					o.id = rows[x].banner.id;
					var fileurl = rows[x].banner.photourl;
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


$('#savecompany').click(function(){
	/*  if(!$('#form_editgoods').checkForm())return; */
	var o = $('#form_editgoods').serializeObject(); 
	
	
	 if(Q.logimgs.length==0){
		 Q.toastr("提示","请至少上传一张图片",'warning');
		return; 
	}  
	//检测图片数组数据是否发生变化
	var flag = Q.checkarrsame(myarr,Q.logimgs);
	o.imgarr = $.toJSON(Q.logimgs);
	
	
	
	o.flag = flag;
	
	$._ajax({
		url: Q.url+"/orderinfo/modifybanner.action",
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
 
function addcompany(){
	var _url= 'bannerimg/addbanner.jsp';
	
	Q.viewJsp('#div_addcompany',_url);
}
function modifycompany(){
	var _url= 'bannerimg/modifybanner.jsp';
	
	Q.viewJsp('#div_import_company',_url);
}


</script>






<script src="js/jqgridresize.js"></script>