<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="page-header">
	<h1>
		牛人管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist">
	<li id="panel_state0" role="presentation" class="active"><a href="#tab_example_state0" role="tab" data-toggle="tab">牛人列表</a></li>
	<li id="panel_state1" role="presentation"><a href="#tab_example_state1" role="tab" data-toggle="tab">待审核</a></li>
	
	<li style="position: relative;float: right;">
	<input type="text" id="keyword" align="right" placeholder="搜索昵称/手机号"/>
	<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryexbycondition()" align="right" >
		<span class="glyphicon glyphicon-search"></span>
	</button>
	</li>
</ul>
<div class="tab-content row">
	<div role="tabpanel" class="tab-pane active" id="tab_example_state0">
		<button id="addexamplebtn" class="btn btn-purple btn-sm"  onclick="addexample()" data-placement="top" title="新增牛人" data-toggle="modal" data-target="addexample">
			新增牛人<span class="glyphicon glyphicon-plus"></span>
		</button> 
	
		<table id="ex_grid_table"></table>
		<div id="ex_grid_pager"></div>
	</div>
	<div role="tabpanel" class="tab-pane active" id="tab_example_state1"></div>
</div>	

<!-- 新增牛人（Modal） -->
<div id="div_addexample" ></div>
			
<script>

//返回时进入的页码
var page = <%=request.getParameter("page")%>;
var opt={};
if(page!=null && page !=''){
	opt.page=page;
}

Q.grid_selector = "ex_grid_table";
Q.pager_selector = "ex_grid_pager";

var url = Q.url+"/example/findExampleList.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '昵称', name: 'nickname',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '金币', name: 'golds',align :'center', sortable : false },
                { label: '虚拟资金', name: 'virtualcapital',align :'center', sortable : false },
                { label: '时间', name: 'time',align :'center', sortable : false },
                { label: '状态', name: 'state',align :'center',sortable : false },
                {
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    formatter: function (value, grid, rows, state) {
                    	
                    	var btn = "";
                    	if(rows.state=='正常'){
                			btn += Q.gridbtn_ban(Q.grid_selector,rows.id);
                		}else{
                			btn += Q.gridbtn_unban(Q.grid_selector,rows.id);
                		}
                   		btn +=" "+ Q.gridbtn_detail(Q.grid_selector,rows.id);
                   		btn += Q.gridbtn_del(Q.grid_selector,rows.id);
                    	return  btn;
                    }
                }
            	];

var caption = "牛人列表";

var postData ={};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption,
	"opt":opt
});


function getcondition(){
	var o = {};
	o.keyword = $('#keyword').val();
	return o;
}

function queryexbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
}

//详情函数----命名规则  detail_tableid
function detail_ex_grid_table(id){
	var page=$('#'+Q.grid_selector).getGridParam('page');
	var _url= 'example/detail.jsp?id='+id+'&page='+page;
	Q.viewJsp(Q.page_content,_url);
}

function addexample(){
	var _url= 'example/add.jsp';
	Q.viewJsp('#div_addexample',_url); 
}


function del_ex_grid_table(id){
	Q.confirm("删除后该账户将不再显示且不能登录，确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/manageuserinfo/delectuser.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已删除",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:$('#'+Q.grid_selector).getGridParam('page')}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
	})
	
	/* var _url= 'permissions/add.jsp?id='+id;
	Q.viewJsp('#div_addpermissions',_url); */
}

/* 封禁 */
function ban_ex_grid_table(id){
	Q.confirm("确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/example/ban.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已封禁",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:$('#'+Q.grid_selector).getGridParam('page')}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
	})
}

/* 解禁 */
function unban_ex_grid_table(id){
	Q.confirm("确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/example/unban.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已解禁",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:$('#'+Q.grid_selector).getGridParam('page')}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
	})
}

//查看牛人审核
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_example_state1","example/authstr.jsp");
})

</script>
<script src="js/jqgridresize.js"></script>