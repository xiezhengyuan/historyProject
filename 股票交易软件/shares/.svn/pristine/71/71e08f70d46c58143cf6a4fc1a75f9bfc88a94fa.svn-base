<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="page-header">
	<h1>
		委托挂单
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
	 <div class="modal-header" style="margin-top:20px;">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">股票名称：</label></td><td><input type="text" id="keyword5" /></td>
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	

</div>
</div><!-- /.page-header -->


<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order1_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order1_grid_pager"></div>
							<div>
	
	
</div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state2"></div>
 
</div>		

<script>

var tradingId = '<%=request.getParameter("tradingId")%>';
//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order1_grid_table";
Q.pager_selector = "order1_grid_pager";

var url = Q.url+"/trading/queryorders.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '股票名称', name: 'sharesname',align :'center', sortable : false },
               
                { label: '现价', name: 'hprice',align :'center', sortable : false },
                
                { label: '委托价', name: 'entrustprice',align :'center', sortable : false },
                { label: '委托数量', name: 'entrustnums',align :'center', sortable : false },
                { label: '冻结资金', name: 'frozenamount',align :'center', sortable : false },
                { label: '委托时间', name: 'time',align :'center', sortable : false },
               
            	];

var caption = "区域一";

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
/*  Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	});  */
	 
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
	$('#keyword').html("");
	o.keyword= $('#keyword5').val();
	o.state = 0;
	o.tradingId=tradingId;
	o.type=0;
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}




/* //详情函数----命名规则  detail_tableid
function detail_order1_grid_table(id){
var _url= 'trading/tradingdetail.jsp?UserinfoId='+id;
	
	Q.viewJsp(Q.page_content,_url);
} */


function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}
/* //股票详情
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state1","postings/topic.jsp");
})
 */

//外汇用户
$('#panel_state2').click(function(){
	Q.viewJsp("#tab_orderinfo_state2","postings/postingsbanned.jsp");
})


</script>
