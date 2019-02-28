<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

           <br/>
       
					<input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="mstarttime">
					<span style="width:40px;display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="mendtime">
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="mquerybytime()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
          

							<table id="order_grid_table3" ></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager3"></div>
							
 
    

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector3 = "order_grid_table3";
Q.pager_selector3 = "order_grid_pager3";

var url3 = Q.url+"/manageuserinfo/querycapitaldetil.action";

var colModel3 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '使用类型', name: 'type',align :'center', sortable : false },
                { label: '资金增减', name: 'capital',align :'center', sortable : false },
                { label: '交易时间', name: 'createtime',align :'center', sortable : false }
            	];

var caption3 = "资金明细表";

var postData3 = {};
postData3 = getcondition3();
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
	"grid":Q.grid_selector3,
	"pager":Q.pager_selector3,
	"url":url3,
	"param":postData3,
	"model":colModel3,
	"caption":caption3
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition3(){
	var o = {};
	o.mstarttime= $('#mstarttime').val();
	o.mendtime= $('#mendtime').val();
	o.userid=userid;
	return o;
}


function mquerybytime(){
	postData3 = getcondition3();
	$("#"+Q.grid_selector3).setGridParam({postData:postData3,page:1}).trigger("reloadGrid"); 
	Q.ExtJqGrid({
		"grid":Q.grid_selector3,
		"pager":Q.pager_selector3,
		"url":url3,
		"param":postData3,
		"model":colModel3,
		"caption":caption3
	});
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
</script>