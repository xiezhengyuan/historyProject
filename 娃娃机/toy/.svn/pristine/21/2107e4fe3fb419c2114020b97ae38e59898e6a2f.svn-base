<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  	<div style="margin-left:95%;">
	<button id="addandorid" class="btn btn-purple btn-sm" onclick="addandorid()" data-placement="top" title="更新安卓版本" data-toggle="modal" data-target="#adduser">
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
							<div id="div_addandorid" ></div>
				
  <script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/edition/queryedition.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '版本号', name: 'editionno',align :'center', sortable : false },
                { label: '下载地址', name: 'url',align :'center', sortable : false, width:200 ,
                	formatter: function (value) { 
                		return '<a href = "'+Q.url+value+'" >'+Q.url+value+'</a>';
                	}	
                
                },
                { label: '更新时间', name: 'time',align :'center', sortable : false, width:200 },
                { label: '状态', name: 'state',align :'center', sortable : false, width:200 },
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:100,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	if(rows.state=='停用'){
                    		btn += "<button title=\"使用该版本\" class=\"btn btn-warning btn-xs\" style=\"color:#f60\" onclick=\"modify_order_grid_table(" +rows.id+")\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>"
                    	}
                
                    	return  btn;
                    }  
                }
            	];

var caption = "安卓版本列表";

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
	o.type='1';
	return o;
}



function addandorid(){
	var _url= 'edition/addnewedition.jsp?type=1';
	
	Q.viewJsp('#div_addandorid',_url);
}


//删除
function modify_order_grid_table(dataid){
	Q.confirm("运行该版本将导致其他版本停止使用，确定使用该版本么？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/edition/usethisedition.action",
				data:{"id":dataid,"type":"1"},
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
	})
	
}

</script>  	