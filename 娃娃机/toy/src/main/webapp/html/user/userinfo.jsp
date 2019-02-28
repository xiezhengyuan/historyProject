<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		用户管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

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
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="username" /></td>
			<td><label for="usermobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="usermobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="adduser()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<!-- <button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> --> 
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="user_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="user_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增人员（Modal） -->
<div id="div_adduser" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_user" ></div>		
<script>


Q.grid_selector = "user_grid_table";
Q.pager_selector = "user_grid_pager";

var url = Q.url+"/userinfo/queryuserinfo.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '电话', name: 'mobile',align :'center', sortable : false },
                { label: '金币数量', name: 'balance',align :'center', sortable : false },
                { label: '用户等级', name: 'level',align :'center', sortable : false },
                { label: '游戏次数', name: 'totalgame',align :'center', sortable : false },
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

var caption = "用户列表";

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
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
 
/*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.name = $('#username').val();
	o.mobile = $('#usermobile').val();
	return o;
}


function queryempbycondition(){
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
function detail_user_grid_table(id){
	var _url= 'user/userinfodetail.jsp?userinfoid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

function adduser(){
	var _url= 'user/adduserinfo.jsp';
	
	Q.viewJsp('#div_adduser',_url);
}

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

/* Q.getObjById("UserInfo",2,function(d){
	console.info(d);
}) */
</script>
<script src="js/jqgridresize.js"></script>