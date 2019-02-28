<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<br/>
  &nbsp;&nbsp;&nbsp;<span style="font-size: 15px;">姓名/手机号：</span><input type="text" id="nameorphone" align="right" />
  <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryorderbycondition()" align="right" >
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<br/>

  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table1"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager1"></div>
					

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector1 = "order_grid_table1";
Q.pager_selector1 = "order_grid_pager1";

var url1 = Q.url+"/manageuserinfo/queryinviteusers.action";

var colModel1 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '累计充值', name: 'totalrecharge',align :'center', sortable : false },
                { label: '金币数量', name: 'golds',align :'center', sortable : false }, 
                { label: '虚拟资金', name: 'virtualcapital',align :'center', sortable : false }, 
                { label: '注册时间', name: 'createtime',align :'center', sortable : false }, 
             
            	];

var caption1 = "邀请成员列表";

var postData1 = {};
postData1 = getcondition1();
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
	"grid":Q.grid_selector1,
	"pager":Q.pager_selector1,
	"url":url1,
	"param":postData1,
	"model":colModel1,
	"caption":caption1
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition1(){
	var o = {};
	o.nameorphone= $('#nameorphone').val();
	o.userid=userid;
	return o;
}


function queryorderbycondition(){
	postData1 = getcondition1();
	$("#"+Q.grid_selector1).setGridParam({postData:postData1,page:1}).trigger("reloadGrid"); 
	Q.ExtJqGrid({
		"grid":Q.grid_selector1,
		"pager":Q.pager_selector1,
		"url":url1,
		"param":postData1,
		"model":colModel1,
		"caption":caption1
	});
}

</script>

