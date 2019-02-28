<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		员工管理
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
			<td><label for="department" class="control-label" >部门：</label></td><td><select type="text" id="department" ></select></td>
			<td><label for="empname" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="empname" /></td>
			<td><label for="empmobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="empmobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<div style="float:right;">
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="adduser()" data-placement="top" title="新增" data-toggle="modal" data-target="#addemp">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<button id="importempbtn" class="btn btn-danger btn-sm" onclick="$('input[id=importemp_excel]').click();" data-placement="top" title="导入">
	导入<span class="glyphicon glyphicon-import"></span>
	</button>
	<a id="personnelmodelclick" target="_blank" href="mould/emp_model.xls" style="padding-left:5px;">没有模板?</a>
	
	<form id="upload_emp_file_form" name="upload_emp_file_form" method="POST"  enctype="multipart/form-data"> 
	<input  id="importemp_excel" type="file" name="filePath" onchange="uploademp_excel()" style="display:none">
	</form>
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="emp_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="emp_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增人员（Modal） -->
<div id="div_addemp" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_emp" ></div>		
<script>

//加载部门下拉框
Q.getDepartment("#department");

Q.grid_selector = "emp_grid_table";
Q.pager_selector = "emp_grid_pager";

var url = Q.url+"/employee/queryemployee.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '部门', name: 'departmentname',align :'center', sortable : false },
                { label: '融金宝', name: 'amountpay',align :'center', sortable : false },
                { label: '团队成员', name: 'teamnums',align :'center', sortable : false },
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

var caption = "员工列表";

var postData = {};

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
	"caption":caption
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.name = $('#empname').val();
	o.mobile = $('#empmobile').val();
	o.department = $('#department').val();
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}



//详情函数----命名规则  detail_tableid
function detail_emp_grid_table(id){
	var _url= 'employee/employeedetail.jsp?userinfoid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

function adduser(){
	var _url= 'employee/addemployee.jsp';
	
	Q.viewJsp('#div_addemp',_url);
}

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

//导入员工信息
function uploademp_excel(){
	 	var form = $("form[name=upload_emp_file_form]");  
	    var options  = {    
	        url:Q.url + '/employee/importemployeebyexcel.action',    
	        type:'post',   
	        dataType:'json',
	        beforeSubmit:function(){
	        	var fileName = $("#importemp_excel").val();
	        	var fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length).toLocaleLowerCase();
	        	if (fileType != 'xls') {
	        		Q.toastr('提示','文件必须为xls格式！','warning');
	        		return false;
	        	}
	        	return true;
            },
	        success:function(data){    
	        	if(data.op=="timeout"){
	        		location.reload();
					return;
	        	}
	        	if(data.op=='success'){
	        		Q.toastr("提示",data.msg,'success');
	        		//重新加载jqgrid
	        		postData = {};
	        		$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	        	}else{
	        		Q.toastr("提示",data.msg,'error');
	        	}
	        	Q.loaded();
	        }    
	    };    
	    Q.loading();
	    form.ajaxSubmit(options);  
}
</script>
<script src="js/jqgridresize.js"></script>