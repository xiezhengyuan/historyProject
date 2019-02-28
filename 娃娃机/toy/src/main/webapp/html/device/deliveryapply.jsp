<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		发货管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">待发货</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">已发货</a></li>
 
 <!--  <li style="position: relative;float: right;">
  <input type="text" id="keyword" align="right" />
  <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryorderbycondition()" align="right" >
	<span class="glyphicon glyphicon-search"></span>
	</button>
  </li> -->
</ul>
<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager"></div>
							<div id="passqu"></div>
							<div id="sendtouser"></div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  
   <!-- panel3 start -->
  
  	
</div>		

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/device/searcInfo.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'consigneename',align :'center', sortable : false, width:60 },
                { label: '电话', name: 'consigneemobile',align :'center', sortable : false,width:60 },
                { label: '详细地址', name: 'address',align :'center', sortable : false },
                { label: '待发货娃娃', name: 'toys',align :'center', sortable : false },
                { label: '申请时间', name: 'createtime',align :'center', sortable : false,width:60 },
                
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += '<button title=\"回复该用户\" class=\"btn btn-warning btn-xs\" style=\"color:#f60\" onclick=\"modify_order_grid_table('+ rows.id+')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>';
                    	btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_pass(Q.grid_selector,rows.id);
                    	return  btn;
                    }  
                }
            	];

var caption = "发货列表";

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
	o.state = 0;
	return o;
}


function queryorderbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}




//详情函数----命名规则  detail_tableid
function pass_order_grid_table(id){
	var _url= 'device/deliverypass.jsp?id='+id;
	
	Q.viewJsp("#passqu",_url);
}

function del_order_grid_table(id){
	Q.confirm("确定删除么？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/device/deleteapp.action",
				data:{"id":id},
				
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.op == 'success' ){
							Q.toastr("提示","操作成功",'success');
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


function modify_order_grid_table(id){
    var _url= 'device/sendtouser.jsp?id='+id;
	
	Q.viewJsp("#sendtouser",_url);
}


function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

//查看已发货
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state1","device/deliveryifahuo.jsp");
})

</script>
