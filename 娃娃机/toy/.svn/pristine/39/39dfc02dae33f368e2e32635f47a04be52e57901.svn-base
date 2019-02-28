<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		玩具分类管理
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
			<tr>
			<!-- <td><label for="department" class="control-label" >部门：</label></td><td><select type="text" id="department" ></select></td> -->
			<td><label for="name" class="control-label" style="margin-left:5px;">分类名称：</label></td><td><input type="text" id="name" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryttbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
<!-- 	<div style="float:right;">
	<button id="addtoystypebtn" class="btn btn-purple btn-sm" onclick="addtoystype()" data-placement="top" title="新增" data-toggle="modal" data-target="#addtoystype">
	新增<span class="glyphicon glyphicon-plus"></span>
	</button> 
	</div> -->

</div>
								<!-- jqgrid 用户列表-->
							<table id="tt_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="tt_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增人员（Modal） -->
<div id="div_addtoystype" ></div>
			
<script>

Date.prototype.format = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var i in args) {
        var n = args[i];
        if (new RegExp("(" + i + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};
//加载部门下拉框
/* Q.getDepartment("#department"); */

Q.grid_selector = "tt_grid_table";
Q.pager_selector = "tt_grid_pager";

var url = Q.url+"/device/toysTypeList.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '分类名称', name: 'name',align :'center', sortable : false },
                { label: '时间', name: 'createtime',align :'center', sortable : false,
                	 formatter: function (value, grid, rows, state) { 
                		 var d = new Date(rows.createtime);
                		 return new Date(d).format("yyyy-MM-dd hh:mm:ss");
                	}
                },
                { label: '状态', name: 'state',align :'center',sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	var btn = "";
                    	btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	//btn += "<button title=\"编辑\" class=\"btn btn-warning btn-xs\" style=\"color:#f60\" onclick=\"modify_tt_grid_table(" + row.name+ ")\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>";
                    	//btn += Q.gridbtn_detail(Q.grid_selector,rows.id) ;
                    	return  btn;
                    }  
                }
            	];

var caption = "分类列表";

var postData ={};

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
	o.name = $('#name').val();
	return o;
}


function queryttbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}



//详情函数----命名规则  detail_tableid
function modify_tt_grid_table(id){
	var _url= 'device/addtoystype.jsp?id='+id;
	
	Q.viewJsp('#div_addtoystype',_url);
}

//删除分类 
function del_tt_grid_table(id){
	Q.confirm("确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/toy/delToysType.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已删除",'success');
			    		$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
		
	})
	
}

function addtoystype(){
	var _url= 'device/addtoystype.jsp';
	
	Q.viewJsp('#div_addtoystype',_url);
}

function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
}

</script>
<script src="js/jqgridresize.js"></script>