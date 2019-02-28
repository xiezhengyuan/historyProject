<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		帖子管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<!-- <div class="alert alert-info">
				<button class="close" data-dismiss="alert">
					<i class="ace-icon fa fa-times"></i>
				</button>

				<i class="ace-icon fa fa-hand-o-right"></i>
				Please note that demo server is not configured to save the changes, therefore you may see an error message.
			</div> -->
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="fpostingstyle" class="control-label"  style="margin-left:5px;">一级分类：</label></td><td><select type="text" id="fpostingstyle" ><option value="-1"  selected>请选择</option></select></td>
			<td>标题<label for="postingsname" class="control-label" style="margin-left:5px;"></label></td><td><input type="text" id="goodsname" /></td>
			<td>发帖人<label for="fuser" class="control-label" style="margin-left:5px;"></label></td><td><input type="text" id="goodsname" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querypostingbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<div style="float:right;">
	<!-- <button id="addgoodsbtn" class="btn btn-purple btn-sm" onclick="addgoods()" data-placement="top" title="商品发布" data-toggle="modal" data-target="#addgoods">商品发布
	<span class="glyphicon glyphicon-plus"></span>
	</button>  -->
	<button id="addpostingstylebtn" class="btn btn-purple btn-sm" onclick="addpostingstyle()" data-placement="top" title="添加分类" data-toggle="modal" data-target="#addpostingstyle">添加分类
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="posting_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="posting_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					

<!-- 新增分类（Modal） -->
<div id="div_addpostingstyle" ></div>
<div id="div_postingdetail1" ></div>

<!-- 新增商品分区（Modal） -->
<!-- <div id="div_addgoodstag" ></div> -->
			
<!-- 导入（Modal） -->
<div id="div_import_goods" ></div>		
<script>

//加载商品一级分类
querypostingstyle("#fpostingstyle");


Q.grid_selector = "posting_grid_table";
Q.pager_selector = "posting_grid_pager";

var url = Q.url+"/posting/queryposting.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '帖子分类', name: 'fpostingstyle',align :'center', sortable : false},
                { label: '标题', name: 'postingsname',align :'center', sortable : false },
                { label: '内容', name: 'postingscontent',align :'center', sortable : false,width:380},
                { label: '发帖人', name: 'fuser',align :'center', sortable : false },
                { label: '状态', name: 'state',align :'center', sortable : false },
                { label: '发帖时间', name: 'createtime',align :'center', sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_pass(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_nopass(Q.grid_selector,rows.id) ;
                    	
                    	return  btn;
                    }  
                }
            	];

var caption = "帖子列表";

var postData = {};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
/* Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption); */

Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.name = $('#postingsname').val();
	o.fpostingstyleid = $('#fpostingstyleid').val();
	o.fuser = $('#fuser').val();
	return o;
}


function querypostingbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}
//删除函数
function del_goods_grid_table(id){
	Q.confirm("确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/goods/delgoods.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已删除",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
		
	})
}

//详情函数----命名规则  detail_tableid
function detail_posting_grid_table(id){
	var _url= 'posting/postingdetail.jsp?postingid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}


function addpostingstyle(){
	var _url= 'posting/addpostingstyle.jsp';
	
	Q.viewJsp('#div_addpostingstyle',_url);
}

/* function addgoods(){
	var _url= 'goods/addgoods.jsp';
	
	Q.viewJsp(Q.page_content,_url);
}

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
} */

//不通过审核函数----命名规则  detail_tableid
function nopassposting_grid_table(id){

 $._ajax({
		url: Q.url+"/posting/nopassposting.action",
		data:{"postingid":id},
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
	
}

//通过审核函数----命名规则  detail_tableid
function pass_posting_grid_table(id){

 $._ajax({
		url: Q.url+"/posting/passposting.action",
		data:{"postingid":id},
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
	
}




</script>
<script src="js/jqgridresize.js"></script>