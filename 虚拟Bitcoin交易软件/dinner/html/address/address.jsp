<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		地址管理
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
			
			<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addcompany()" data-placement="top" title="新增" data-toggle="modal" data-target="#adduser">
	<span class="glyphicon glyphicon-plus">新增地址</span>
	</button> 
	<div style="float:right;">
	
	
	</div>
	

</div>
								<!-- jqgrid 用户列表-->
							<table id="company_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="company_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
					
<!-- 新增用户（Modal） -->
<div id="div_addcompany" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_company" ></div>		
<script>


Q.grid_selector = "company_grid_table";
Q.pager_selector = "company_grid_pager";

var url = Q.url+"/applyinfo/queryAdress.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '地址', name: 'address',align :'center', sortable : false },
              
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	/* if(rows.state=="0"){
                    		btn += Q.gridbtn_ban(Q.grid_selector,rows.id);
                    	}else{
                    		btn += Q.gridbtn_unban(Q.grid_selector,rows.id);
                    	}  */
                    	
                    	btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	
                    	
                    	return  btn;
                    }  
                }
            	];

var caption = "用户列表";

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
	return o;
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
function del_company_grid_table(id){
	
	
	$._ajax({
		url: Q.url+"/applyinfo/dealAdress.action",
		data:{"addressid":id},
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



function ban_company_grid_table(id){
		Q.confirm("确定吗？",function(r){
			if(r){
				$._ajax({
					url:Q.url+"/manageuserinfo/ban.action",
					data:{"id":id},
				    success:function(data){
				    	if(data.op=="success"){
				    		Q.toastr("提示","已封禁",'success');
				    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
				    	}else{
				    		Q.toastr("提示","服务器忙",'warning');
				    	}
				    }
				})
			}
		})
	
}


function addcompany(){
	var _url= 'address/addaddress.jsp';
	
	Q.viewJsp('#div_addcompany',_url);
}
function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

</script>






<script src="js/jqgridresize.js"></script>