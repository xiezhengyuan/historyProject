<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  		<!-- jqgrid 用户列表-->
							<table id="order1_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order1_grid_pager"></div>
							<div id="agentxiangqing"></div>	
<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order1_grid_table";
Q.pager_selector = "order1_grid_pager";

var url = Q.url+"/agentinfo/queryagentuser.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '住址', name: 'address',align :'center', sortable : false },
                { label: '成为代理时间', name: 'createtime',align :'center', sortable : false }, 
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_xiangqing1(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_bohui1(Q.grid_selector,rows.id) ;
                    	return  btn;
                    }  
                }
            	];

var caption = "代理申请列表";

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
	o.name= $('#username').val();
	o.mobile= $('#usermobile').val();
	return o;
}


function queryorderbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
}

//查看详情
function modify_order1_grid_table(dataid){
	
		var _url= 'agent/agentdetail.jsp?id='+dataid;
		
		Q.viewJsp(Q.page_content,_url);

}

function del_order1_grid_table(dataid){
	Q.confirm("取消代理将下架该代理的所有娃娃机和绑定的玩具？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/agentinfo/deleteagent.action",
				data:{"id":dataid},
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.op == 'success' ){
							Q.toastr("提示","操作成功,改代理的"+data.machinenumber+"娃娃机"+data.toynumber+"玩具下架",'success');
							queryorderbycondition();
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

