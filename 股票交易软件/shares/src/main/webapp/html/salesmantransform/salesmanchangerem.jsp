<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

           <br/>
       
					<input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="gstarttime">
					<span style="width:40px;display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="gendtime">
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="gquerybytime()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
          

							<table id="order_grid_table3" ></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager3"></div>
							<div id="salesmanchangedetail"></div>
 
    

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector3= "order_grid_table3";
Q.pager_selector3= "order_grid_pager3";

var url3 = Q.url+"/salesmanchange/querychangerem.action";

var colModel3 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '转移人数', name: 'number',align :'center', sortable : false },
                { label: '转入公司', name: 'incompany',align :'center', sortable : false },
                { label: '转入经理', name: 'inaccount',align :'center', sortable : false },
                { label: '操作人', name: 'doitman',align :'center', sortable : false, },
                { label: '转移时间', name: 'createtime',align :'center', sortable : false, },
                { name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    formatter: function (value, grid, rows, state) { 
                    	var btn = "";
                    	btn += Q.gridbtn_detail(Q.grid_selector3,rows.id);
                    	return  btn;
                    }  
                }
                ];

var caption3 = "转移记录表";

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
	o.starttime= $('#gstarttime').val();
	o.endtime= $('#gendtime').val();
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
	postData3 = getcondition3();
	$("#"+Q.grid_selector3).setGridParam({postData:postData3,page:1}).trigger("reloadGrid"); 
	
}

function detail_order_grid_table3(id){

	var _url= 'salesmantransform/salemanchangedetail.jsp?id='+id;
	
	Q.viewJsp("#salesmanchangedetail",_url);
}


</script>

