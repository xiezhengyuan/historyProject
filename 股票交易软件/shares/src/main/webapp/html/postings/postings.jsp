<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="page-header">
	<h1>
		社交圈管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
	 <div class="modal-header" style="margin-top:20px; display:none">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="keyword" /></td>
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	

</div>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">用户列表</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">话题列表</a></li>
  <li id="panel_state2" role="presentation"><a href="#tab_orderinfo_state2" role="tab" data-toggle="tab">禁言列表</a></li>
 
  <li id="panel_state3" role="presentation"><a href="#tab_orderinfo_state3" role="tab" data-toggle="tab">新增话题</a></li>
 <li id="panel_state4" role="presentation"><a href="#tab_orderinfo_state4" role="tab" data-toggle="tab">新增分类</a></li>
</ul>

<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager"></div>
							<div>
	
	
</div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1">
  
  </div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state2"></div>
   <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state3"></div>
  <!-- panel4 start -->
   <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state4"></div>
  	
</div>		

<script>


//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/postings/querypostings.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '发帖人', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '发帖数量', name: 'countpostings',align :'center', sortable : false },
                { label: '回帖数量', name: 'comments',align :'center', sortable : false },
                { label: '打赏金额', name: 'reward',align :'center', sortable : false },
                { label: '被打赏金额', name: 'breward',align :'center', sortable : false },
                 {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_pass1(Q.grid_selector,rows.id) ;
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
	o.keyword= $('#keyword').val();
	o.state = 0;
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}




//详情函数----命名规则  detail_tableid
function pass1_order_grid_table(id){
	$._ajax({
		url: Q.url+"/postings/disable.action",
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


function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}
//帖子详情
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state1","postings/topic.jsp");
})


//禁言用户
$('#panel_state2').click(function(){
	Q.viewJsp("#tab_orderinfo_state2","postings/postingsbanned.jsp");
})

//新增帖子
$('#panel_state3').click(function(){
	Q.viewJsp("#tab_orderinfo_state3","postings/addpostings.jsp");
})
//新增分类
 $('#panel_state4').click(function(){
	Q.viewJsp("#tab_orderinfo_state4","postings/postingstyle.jsp");
}) 
</script>
