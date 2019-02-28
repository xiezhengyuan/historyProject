<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="height:45px">
	
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addStyle()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<!-- <button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> --> 
	</div>
  		<!-- jqgrid 用户列表-->
							<table id="order11_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order11_grid_pager"></div>
							<div id="agentxiangqing"></div>	
													<div>
	
	
</div>
<div id="styleadd"></div>
<script>


/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order11_grid_table";
Q.pager_selector = "order11_grid_pager";

var url = Q.url+"/postings/queryStyle.action";

var colModel = [
				{ label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
				{ label: '分类名字', name: 'name',align :'center', sortable : false },
				
				{ label: '创建时间', name: 'createtime',align :'center', sortable : false },
			
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id);
                    	/* btn +=Q.gridbtn_del(Q.grid_selector,rows.id); */
                    	return  btn;
                    }  
                }
            	];

var caption = "话题列表";

var postData = {};
postData = getcondition();
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
/* Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	}); */
	
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
	o.keyword= $('#keyword').val();
	return o;
}


function queryorderbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	Q.ExtJqGrid({
		"grid":Q.grid_selector,
		"pager":Q.pager_selector,
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	});
}
//详情函数----命名规则  detail_tableid
function detail_order11_grid_table(id){
	var _url= 'postings/styleDetail.jsp?id='+id;
	
	Q.viewJsp(Q.page_content,_url);
}
//删除
function del_order11_grid_table(id){
	$._ajax({
		url: Q.url+"/postings/deleteStyle.action",
		data:{"id":id},
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
//查看详情
function modify_order11_grid_table(dataid){
	$("#xiangqing").empty();
	$("#agentxiangqing").empty();
	$("#xiangqing4").empty();
	$("#diaagreexiangqing").empty();
	 var _url= 'agent/applyuserdetail.jsp?userinfoid='+dataid;
	 Q.viewJsp("#agentxiangqing",_url);
}
function addStyle(){
	var _url= 'postings/addStyle.jsp';
	
	Q.viewJsp('#styleadd',_url);
}
</script>

