<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="page-header">
	<h1>
		交易账户管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
	 <div class="modal-header" style="margin-top:20px;">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="keyword10" /></td>
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	

</div>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">用户列表</a></li>
  

</ul>

<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table4"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager4"></div>
							<div>
	
	
</div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state2"></div>
 
</div>		

<script>


//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table4";
Q.pager_selector = "order_grid_pager4";

var url = Q.url+"/trading/queryusertrading.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '昵称', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '金币', name: 'golds',align :'center', sortable : false },
                { label: '虚拟资金', name: 'virtualcapital',align :'center', sortable : false },
               
                 {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
                    	return  btn;
                    }  
                }
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
	o.keyword= $('#keyword10').val();
	o.state = 0;
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}




//详情函数----命名规则  detail_tableid
function detail_order_grid_table4(id){
var _url= 'trading/tradingdetail.jsp?UserinfoId='+id;
	
	Q.viewJsp(Q.page_content,_url);
}


function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}
//股票详情
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state1","postings/topic.jsp");
})


//外汇用户
$('#panel_state2').click(function(){
	Q.viewJsp("#tab_orderinfo_state2","postings/postingsbanned.jsp");
})


</script>
