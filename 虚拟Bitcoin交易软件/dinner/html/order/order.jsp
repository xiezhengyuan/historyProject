<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		订单列表
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="row">
		<div class="col-xs-12">
			
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
		<!-- 	<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="username" /></td>
			<td><label for="usermobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="usermobile" /></td>
			</tr> -->
			</table>
			<label>请选择订单状态：</label>
			<select id="state"  class="select2"">
  				<!-- <option value="-1" >请选择</option> -->
	         <option value="0" >正在交易订单</option>
			<option value="1" >历史订单</option>
			<option value="2" >交易关闭</option>
			<!-- <option value="3" >待转币</option> -->
	         </select>
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querycompanybycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<!-- <div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addcompany()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus">新增公司</span>
	</button> 
	<button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> 
	</div> -->

</div>
								<!-- jqgrid 用户列表-->
							<table id="company_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="company_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增公司（Modal） -->
<div id="div_addcompany" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_company" ></div>		
<script>


Q.grid_selector = "company_grid_table";
Q.pager_selector = "company_grid_pager";

var url = Q.url+"/orderinfo/queryOrder.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
               
                { label: '买家', name: 'bnikename',align :'center', sortable : false },
                { label: '卖家', name: 'mnickname',align :'center', sortable : false },
                { label: '交易总金额', name: 'cost',align :'center', sortable : false },
                { label: '交易的数量', name: 'amount',align :'center', sortable : false },
                { label: '交易状态', name: 'state',align :'center', sortable : false },
                { label: '用户状态', name: 'state1',align :'center', hidden:true, sortable : false },
               {label: '时间', name: 'time',align :'center',sortable : false },
                /*  {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	if(rows.state1=="3"){
                    		btn += Q.gridbtn_zhuan(Q.grid_selector,rows.id);
                    	}
                    	return  btn;
                    }  
                }  */
            	];

var caption = "订单列表";

var postData = {};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
 
/*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.nickname = $('#username').val();
	o.mobile = $('#usermobile').val();
	o.state = $('#state').val();
	return o;
}
function zhuan_company_grid_table(id){
	Q.confirm("确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/orderinfo/zhuan.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已转币",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","服务器忙",'warning');
			    	}
			    }
			})
		}
	})

}

function querycompanybycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}

function getSelectedRows() {
    var grid = $(Q.grid_selector);
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


//详情函数----命名规则  detail_tableid
function detail_company_grid_table(id){
	var _url= 'usermanager/userdetail.jsp?userid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

/* function addcompany(){
	var _url= 'company/addcompany.jsp';
	
	Q.viewJsp('#div_addcompany',_url);
} */

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}
/* 禁用解禁函数 */
function change_company_grid_table(id,stated){
	$._ajax({
		url: Q.url+"/company/forbiddencompany.action",
		data:{"companyid":id,"state":stated},
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
</script>






<script src="js/jqgridresize.js"></script>