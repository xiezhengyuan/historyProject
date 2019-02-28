<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="row">
		<div class="col-xs-12">
			
			<div class="modal-header">
			<div style="margin-top: -20px;margin-bottom: 20px">
			<span>公司成员：</span><span id="totalall"></span>
			<span>推广经理：</span><span id="totalrole4"></span>
			<span>推广业务员：</span><span id="totalrole5"></span>
			</div>
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<!-- <td><span>公司成员</span></td><td id="totalall"></td>
			<td><span>推广经理</span></td><td id="totalrole4"></td>
			<td><span>推广业务员</span></td><td id="totalrole5"></td> -->
			</tr>
			<tr><td></td></tr>
			<tr>
			<td><label for="memberlevel" class="control-label" >成员分类：</label></td>
			<td><select type="text" id="memberlevel" >
			<option  value="-1" selected>请选择</option>
			<option value="1" >推广经理</option>
			<option value="2" >推广业务员</option>
			</select></td>
			<td><label for="name" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="accountname" /></td>
			<td><label for="mobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="accountmobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querymemberbycondition()">
			<span class="glyphicon glyphicon-search"></span>
			</button>
	<!-- <div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="adduser()" data-placement="top" title="新增" data-toggle="modal" data-target="#addemp">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> 
	</div> -->

</div>
								<!-- jqgrid 用户列表-->
							<table id="member_user_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="member_user_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 用户详情（Modal） -->
<div id="div_memberdetail" ></div>
			
<!-- 导入（Modal） -->
<!-- <div id="div_import_emp" ></div> -->		
<script>
var companyid = '<%=request.getParameter("companyid")%>';

Q.grid_selector = "member_user_grid_table";
Q.pager_selector = "member_user_grid_pager";

var url = Q.url+"/company/querymember.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '成员分类', name: 'role',align :'center', sortable : false },
                { label: '创建时间', name: 'time',align :'center',sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_roledetail(Q.grid_selector,rows.id,rows.roles) ;
                    	btn += Q.gridbtn_change(Q.grid_selector,rows.id,rows.state) ; 
                    	return  btn;
                    }  
                }
            	];

var caption = "公司成员";

var postData = {"companyid":companyid,"memberlevel":$('#memberlevel').val()};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	 $('#totalall').html(d.totalrole4+d.totalrole5);
	 $('#totalrole4').html(d.totalrole4);
	 $('#totalrole5').html(d.totalrole5);
	});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.companyid = companyid;
	o.name = $('#accountname').val();
	o.mobile = $('#accountmobile').val();
	o.memberlevel = $('#memberlevel').val();
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
function roledetail_member_user_grid_table(id,roles){
	var _url = "";
	if(roles==4){
	 _url= 'company/managerdetail.jsp?accountinfoid='+id+'&companyid='+companyid;
	 }
	if(roles==5){
		 _url= 'company/salesmandetail.jsp?accountinfoid='+id+'&companyid='+companyid;
		 }
	
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
