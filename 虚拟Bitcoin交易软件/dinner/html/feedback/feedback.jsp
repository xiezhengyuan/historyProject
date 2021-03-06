<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		意见反馈
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">标题：</label></td><td><input type="text" id="title" /></td>
			<td><label for="usermobile" class="control-label" style="margin-left:5px;">内容：</label></td><td><input type="text" id="content1" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryfeedbackbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	<div style="float:right;">
	<!-- <button id="addempbtn" class="btn btn-purple btn-sm" onclick="addcompany()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus">新增公司</span>
	</button>  -->
	
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="feedback_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="feedback_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
					
					
<!-- 意见反馈详情（Modal） -->
<div id="div_feedbackdetail" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_company" ></div>		

<div id="div_import_company" ></div>		
<script>


Q.grid_selector = "feedback_grid_table";
Q.pager_selector = "feedback_grid_pager";

var url = Q.url+"/feedback/queryfeedback.action";

var colModel = [
                { label: '序号', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '标题', name: 'title',align :'center', sortable : false }, 
                { label: '内容', name: 'content',align :'center', sortable : false },
                { label: '昵称', name: 'nickname',align :'center', sortable : false },
                { label: '时间', name: 'time',align :'center',sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	// btn += Q.gridbtn_change(Q.grid_selector,rows.id,rows.state) ; 
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
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
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
 
/*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.title = $('#title').val();
	o.content = $('#content1').val();
	return o;
}


function queryfeedbackbycondition(){
	
	postData = getcondition();
	//$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	
	
	/* var my_data = [
		            {"id":1,"title":"title1","content":"content1","nickname":"nickname1","time":"2017-07-24"},
		            {"id":2,"title":"title2","content":"content2","nickname":"nickname2","time":"2017-07-24"},
		            {"id":3,"title":"title3","content":"content3","nickname":"nickname3","time":"2017-07-24"},
		            {"id":4,"title":"title4","content":"content4","nickname":"nickname4","time":"2017-07-24"},
		            {"id":5,"title":"title5","content":"content5","nickname":"nickname5","time":"2017-07-24"}
		            ];
	$("#"+Q.grid_selector).setGridParam({data:my_data,datatype:"local"}).trigger("reloadGrid");  */
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
function detail_feedback_grid_table(id){
	var _url= 'feedback/feedbackdetail.jsp?feedbackid='+id;
	
	Q.viewJsp('#div_feedbackdetail',_url);
}

function addcompany(){
	var _url= 'company/addcompany.jsp';
	
	Q.viewJsp('#div_addcompany',_url);
}

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