<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

           <br/>
       
					<input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="gstarttime">
					<span style="width:40px;display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="gendtime">
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="gquerybytime()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
          

							<table id="order_grid_table2" ></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager2"></div>
							
 
    

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector2 = "order_grid_table2";
Q.pager_selector2= "order_grid_pager2";

var url2 = Q.url+"/manageuserinfo/querygolddetil.action";

var colModel2 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '使用类型', name: 'type',align :'center', sortable : false },
                { label: '金币增减', name: 'golds',align :'center', sortable : false },
                { label: '交易时间', name: 'createtime',align :'center', sortable : false, width:170 }
            	];

var caption2 = "金币明细表";

var postData2 = {};
postData2 = getcondition2();
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
	"grid":Q.grid_selector2,
	"pager":Q.pager_selector2,
	"url":url2,
	"param":postData2,
	"model":colModel2,
	"caption":caption2
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition2(){
	var o = {};
	o.gstarttime= $('#gstarttime').val();
	o.gendtime= $('#gendtime').val();
	o.userid=userid;
	return o;
}



$(".form_date").datetimepicker({
	language:  'zh-CN',
    format: 'yyyy-MM-dd',
    autoclose: true,
    todayBtn: false,
    pickerPosition: "bottom-left",
    weekStart: 0,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView :2,
	forceParse: 0
});


function gquerybytime(){
	postData2 = getcondition2();
	$("#"+Q.grid_selector2).setGridParam({postData:postData2,page:1}).trigger("reloadGrid"); 
	Q.ExtJqGrid({
		"grid":Q.grid_selector2,
		"pager":Q.pager_selector2,
		"url":url2,
		"param":postData2,
		"model":colModel2,
		"caption":caption2
	});
}
</script>

