<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  		   <div style="margin-left:1230px;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addappeal()" data-placement="top" title="新增系统申诉" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<!-- <button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> --> 
	</div>
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager"></div>
							<div id="div_addappeal" ></div>
							<div id="div_xiangqing" ></div>
  <script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/UserAppeal/querysystemappeal.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '定义申诉内容', name: 'content',align :'center', sortable : false },
                { label: '添加时间', name: 'createtime',align :'center', sortable : false, width:200 },
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:100,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += "<button title=\"修改申诉内容\" class=\"btn btn-warning btn-xs\" style=\"color:#f60\" onclick=\"modify_order_grid_table(" +rows.id+")\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>"
                    	btn += "<button title=\"删除\" class=\"btn btn-danger btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"del_order_grid_table(" + rows.id + ")\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>" ;
                    	return  btn;
                    }  
                }
            	];

var caption = "系统申诉列表";

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
	return o;
}


function queryorderbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
}



function addappeal(){
	var _url= 'complain/addappeal.jsp';
	
	Q.viewJsp('#div_addappeal',_url);
}

//详情
function modify_order_grid_table(id){
	 var _url= 'complain/updateappeal.jsp?id='+id;
	 Q.viewJsp("#div_xiangqing",_url);
}

//删除
function del_order_grid_table(dataid){
	$._ajax({
		url: Q.url+"/UserAppeal/deleteappeal.action",
		data:{"id":dataid},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					postData = getcondition();
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