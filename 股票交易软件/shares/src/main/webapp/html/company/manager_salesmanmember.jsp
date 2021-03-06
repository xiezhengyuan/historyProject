<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="row">
		<div class="col-xs-12">
			
			<div class="modal-header">
			
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="salesman" class="control-label" >推广业务员：</label></td><td><span id="salesman"></span></td>
			
			<td><label for="name" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="accountname" /></td>
			<td><label for="mobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="accountmobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querymemberbycondition()">
			<span class="glyphicon glyphicon-search"></span>
			</button>
	

</div>
								<!-- jqgrid 用户列表-->
							<table id="salesman_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="salesman_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 业务员详情（Modal） -->
<div id="div_salesmandetail" ></div>
			
<!-- 导入（Modal） -->
<!-- <div id="div_import_emp" ></div> -->		
<script>
var accountinfoid = '<%=request.getParameter("accountinfoid")%>';

Q.grid_selector = "salesman_grid_table";
Q.pager_selector = "salesman_grid_pager";

var url = Q.url+"/company/querysalesmanbymanager.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '推广链接', name: 'extensionurl',align :'center', sortable : false },
                { label: '创建时间', name: 'time',align :'center',sortable : false }
               /*  {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id,rows.role) ;
                    	btn += Q.gridbtn_change(Q.grid_selector,rows.id,rows.state) ; 
                    	return  btn;
                    }  
                } */
            	];

var caption = "业务员";

var postData = {"accountinfoid":accountinfoid};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	 $('#salesman').html(d.records);
	});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.accountinfoid = accountinfoid;
	o.name = $('#accountname').val();
	o.mobile = $('#accountmobile').val();
	return o;
}


function querymemberbycondition(){
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
function detail_member_user_grid_table(id,role){
	var _url = 'company/managerdetail.jsp?accountinfoid='+id;
	if(role==4){
	 _url= 'company/managerdetail.jsp?accountinfoid='+id;}
	
	Q.viewJsp(Q.page_content,_url);
}

function change_member_user_grid_table(id,stated){
	$._ajax({
		url: Q.url+"/company/forbiddenmember.action",
		data:{"accountinfoid":id,"state":stated},
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
