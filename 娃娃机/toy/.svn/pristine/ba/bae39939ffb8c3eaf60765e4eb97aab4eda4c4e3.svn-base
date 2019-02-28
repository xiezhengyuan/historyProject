<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="tab-content">
<!-- panel1 start -->
 
  		<!-- jqgrid 用户列表-->
							<table id="orderstate1_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="orderstate1_grid_pager"></div>
							<div id="div_expressagedetail" ></div>
  	
  	
</div>		

<!-- 物流详情（Modal） -->

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector1 = "orderstate1_grid_table";
Q.pager_selector1 = "orderstate1_grid_pager";

var url1 = Q.url+"/ServiceDeliveryapplyControl/queryexpressageinfo.action";

var colModel1 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '订单号', name: 'orderno',align :'center', sortable : false },
                { label: '玩具名称', name: 'toysname',align :'center', sortable : false },
                { label: '收货人名称', name: 'consigneename',align :'center', sortable : false },
                { label: '收货人电话', name: 'consigneemobile',align :'center', sortable : false },
                { label: '发货人', name: 'empname',align :'center', sortable : false },
                
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	
                    	btn += Q.gridbtn_yifahuo(Q.grid_selector1,rows.id) ;
                    	return  btn;
                    }  
                }
            	];

var caption1 = "订单列表11123";

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
	empid=$('#empid').val()==null?1:$('#empid').val();
	o.empid= empid;
	o.keyword= $('#keyword').val();
	o.toyname = $('#toyname').val();
	return o;
}


function queryorderbycondition(){
	postData1 = getcondition();
	$("#"+Q.grid_selector1).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	
	}

function getSelectedRows() {
    var grid = $(Q.grid_selector1);
    var rowKey = grid.getGridParam("selrow");

    if (!rowKey) {
        alert("No rows are selected");
    } else {
        var selectedIDs = grid.getGridParam("selarrrow");
        //var result = "";
        //for (var i = 0; i < selectedIDs.length; i++) {
        //    result += selectedIDs[i] + ",";
        //}
        var result = selectedIDs.join(",");
        alert(result);
    }
}


function yifahuo_orderstate1_grid_table(id){
	var _url= 'deliveryapply/expressagedetail.jsp?expressageinfoid='+id;
	
	Q.viewJsp('#div_expressagedetail',_url);
}
</script>
