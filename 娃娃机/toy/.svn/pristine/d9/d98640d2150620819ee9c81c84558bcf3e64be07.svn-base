<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		意见反馈管理
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
//Q.getDepartment("#department");

Q.grid_selector = "emp_grid_table";
Q.pager_selector = "emp_grid_pager";

var url = Q.url+"/ServiceFeedBack/info.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '标题', name: 'title',align :'center', sortable : false },
                { label: '内容', name: 'content',align :'center', sortable : false },
                { label: '时间', name: 'createtime',align :'center', sortable : false },
                { label: '回复', name: 'replayto',align :'center', sortable : false },
               
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
                    	return  btn;
                    }  
                }
            	];

var caption = "意见反馈列表";

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
	//o.name = $('#empname').val();
	//o.mobile = $('#empmobile').val();
	//o.department = $('#department').val();
	return o;
}


//function queryempbycondition(){
//	postData = getcondition();
//	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
//}







</script>
<script src="js/jqgridresize.js"></script>