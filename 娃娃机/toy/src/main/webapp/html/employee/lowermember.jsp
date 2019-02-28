<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<!-- <div class="alert alert-info">
				<button class="close" data-dismiss="alert">
					<i class="ace-icon fa fa-times"></i>
				</button>

				<i class="ace-icon fa fa-hand-o-right"></i>
				Please note that demo server is not configured to save the changes, therefore you may see an error message.
			</div> -->
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="proxylevel" class="control-label" >代理级别：</label></td><td><select type="text" id="proxylevel" ><option value="1" selected>一级</option><option value="2" >二级</option></select></td>
			<td><label for="lowerempname" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="lowerempname" /></td>
			<td><label for="lowerempmobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="lowerempmobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querylowerbycondition()">
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
							<table id="lower_emp_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="lower_emp_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 用户详情（Modal） -->
<div id="div_userdetail" ></div>
			
<!-- 导入（Modal） -->
<!-- <div id="div_import_emp" ></div> -->		
<script>
var userinfoid = '<%=request.getParameter("userinfoid")%>';

Q.grid_selector = "lower_emp_grid_table";
Q.pager_selector = "lower_emp_grid_pager";

var url = Q.url+"/employee/querylower.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '级别', name: 'level',align :'center', sortable : false },
                { label: '注册时间', name: 'time',align :'center',sortable : false },
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

var caption = "下级成员";

var postData = {"userinfoid":userinfoid,"proxylevel":$('#proxylevel').val()};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
//Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption);

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
	o.name = $('#lowerempname').val();
	o.mobile = $('#lowerempmobile').val();
	o.proxylevel = $('#proxylevel').val();
	o.userinfoid = userinfoid;
	return o;
}


function querylowerbycondition(){
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
function detail_lower_emp_grid_table(id){
	var _url= 'employee/lowerdetail.jsp?userinfoid='+id;
	
	Q.viewJsp('#div_userdetail',_url);
}



/* Q.getObjById("UserInfo",2,function(d){
	console.info(d);
}) */
</script>
