<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		代理管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li id="panel_state0" role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">代理申请</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">代理列表</a></li>
 
 
  <li style="position: relative;float: right;">
 <span style="font-size: 15px;">姓名：</span> <input type="text" id="username" align="right" />
  <span style="font-size: 15px;">手机号：</span><input type="text" id="usermobile" align="right" />
  <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryorderbycondition()" align="right" >
	<span class="glyphicon glyphicon-search"></span>
	</button>
  </li>
</ul>
<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager"></div>
							<div id="xiangqing"></div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  

 
  	
</div>		

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/agentinfo/querynewagent.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '住址', name: 'address',align :'center', sortable : false },
                { label: '申请时间', name: 'createtime',align :'center', sortable : false }, 
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_bohui(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_tongyi(Q.grid_selector,rows.id);
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
	Q.ExtJqGrid({
		"grid":Q.grid_selector,
		"pager":Q.pager_selector,
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	});
}


//驳回
function del_order_grid_table(dataid){
	$._ajax({
		url: Q.url+"/agentinfo/disagreeapply.action",
		data:{"fuserinfoid":dataid},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
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

//同意
function pass_order_grid_table(dataid){
	
	Q.viewJsp("#xiangqing","agent/applyuserdetail.jsp?id="+dataid+"");
}





//申请表
$('#panel_state0').click(function(){
	$('#username').val("");
    $('#usermobile').val("");
    Q.viewJsp("#tab_orderinfo_state0","agent/applyuser.jsp");
})

//代理表
$('#panel_state1').click(function(){
	$('#username').val("");
    $('#usermobile').val("");
	Q.viewJsp("#tab_orderinfo_state1","agent/agentuser.jsp");
})

</script>

