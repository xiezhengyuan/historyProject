<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  		<!-- jqgrid 用户列表-->
							<table id="order2_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order2_grid_pager"></div>
							<div id="agentxiangqing"></div>	
													<div>
	
	
</div>
<script>


/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order2_grid_table";
Q.pager_selector = "order2_grid_pager";

var url = Q.url+"/postings/querybanneruser.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '用户', name: 'name',align :'center', sortable : false },
              
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                
               { label: '禁言时间', name: 'createtime',align :'center', sortable : false }, 
               
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_passb(Q.grid_selector,rows.id);
                    	
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
function detail_order2_grid_table(id){
	var _url= 'postings/topicdetail.jsp?id='+id;
	
	Q.viewJsp(Q.page_content,_url);
}
//删除
function passb_order2_grid_table(id){
	$._ajax({
		url: Q.url+"/postings/relievebanner.action",
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
function modify_order2_grid_table(dataid){
	$("#xiangqing").empty();
	$("#agentxiangqing").empty();
	$("#xiangqing4").empty();
	$("#diaagreexiangqing").empty();
	 var _url= 'agent/applyuserdetail.jsp?userinfoid='+dataid;
	 Q.viewJsp("#agentxiangqing",_url);
}

</script>

