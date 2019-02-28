<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		娃娃机管理
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
			<!-- <td><label for="department" class="control-label" >部门：</label></td><td><select type="text" id="department" ></select></td> -->
			<td><label for="ftoysid" class="control-label" style="margin-left:5px;">类型：</label></td><td><select  id="ftoysid" onchange="showinput()" >
			  <option value="1">我的</option>
			  <option value="2">代理的</option>
			
			</select></td>
			<td id="addagentname"></td>
			</tr>
			</table>
			<span id="addbutton" style="opacity:0;" >
			  <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()" >
	          <span class="glyphicon glyphicon-search" ></span>
	          </button>
	         </span>
	<div style="float:right;">
	<button id="addmachinebtn" class="btn btn-purple btn-sm" onclick="addmachine()" data-placement="top" title="新增" data-toggle="modal" data-target="#addmachine">
	新增<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<!-- <button id="importempbtn" class="btn btn-danger btn-sm" onclick="$('input[id=importemp_excel]').click();" data-placement="top" title="导入">
	导入<span class="glyphicon glyphicon-import"></span>
	</button>
	<a id="personnelmodelclick" target="_blank" href="mould/emp_model.xls" style="padding-left:5px;">没有模板?</a>
	
	<form id="upload_emp_file_form" name="upload_emp_file_form" method="POST"  enctype="multipart/form-data"> 
	<input  id="importemp_excel" type="file" name="filePath" onchange="uploademp_excel()" style="display:none">
	</form> -->
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="mc_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="mc_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增人员（Modal） -->
<div id="div_addmachine" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_emp" ></div>		
<script>

//加载玩具下拉框

function showinput(){
	var value=$("#ftoysid").val();
	if(value=='2'){
		$("#addagentname").html('<label for="agentname" class="control-label" style="margin-left:5px;">代理人姓名：</label></td><td><input type="text" id="agentname" />');
	    $("#addbutton").css("opacity","1");
	}else{
		$("#addagentname").html('');
		 $("#addbutton").css("opacity","0");
	}
	queryempbycondition();
}



Q.grid_selector = "mc_grid_table";
Q.pager_selector = "mc_grid_pager";

var url = Q.url+"/machine/machineList.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '设备编号', name: 'machineno',align :'center', sortable : false },
                { label: '所属人', name: 'ordername',align :'center', sortable : false },
                { label: '玩具', name: 'toysname',align :'center', sortable : false },
                { label: '价格', name: 'price',align :'center', sortable : false },
                { label: '人气', name: 'popularity',align :'center', sortable : false },
                { label: '时间', name: 'createtime',align :'center', sortable : false },
                { label: '状态', name: 'state',align :'center',sortable : false,  
                    formatter: function (value, grid, rows, state) { 
                    	var status = rows.state==-1?'已下架':rows.state==0?'空闲中':'正在使用';
                    	return status;
                    }  
                },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	if(rows.ftoysid==null || rows.ftoysid==''){
                    		btn += Q.gridbtn_bindtoy(Q.grid_selector,rows.id) ;
                    	}else{
                    		btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
                    	}
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	
                    	return  btn;
                    }  
                }
            	];

var caption = "娃娃机列表";

var postData ={};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
 postData = getcondition();
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
	o.type = $('#ftoysid').val();
	o.agentname = $('#agentname').val();
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}



//详情函数----命名规则  detail_tableid
function detail_mc_grid_table(id){
	var _url= 'machine/machinedetail.jsp?machineinfoid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

function bind_mc_grid_table(id){
var _url= 'machine/bindtoys.jsp?machineinfoid='+id;
	
	Q.viewJsp(Q.page_content,_url);
	
	
	
}

function addmachine(){
	var _url= 'machine/addmachine.jsp';
	
	Q.viewJsp('#div_addmachine',_url);
}

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

</script>
<script src="js/jqgridresize.js"></script>