<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

								<!-- jqgrid 用户列表-->
							<table id="record_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="record_grid_pager"></div>

<!-- 新增人员（Modal） -->
<div id="div_adduser" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_user" ></div>		
<script>
var userinfoid = '<%=request.getParameter("userinfoid")%>';

Q.grid_selector = "record_grid_table";
Q.pager_selector = "record_grid_pager";

var url = Q.url+"/userinfo/queryrecord.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '用户名称', name: 'username',align :'center', sortable : false },
                { label: '玩具名称', name: 'toyname',align :'center', sortable : false },
                { label: '设备编号', name: 'machineno',align :'center', sortable : false },
                { label: '玩具图片', name: 'photo',align :'center', sortable : false },
       
                { label: '发货状态', name: 'state',align :'center', sortable : false },
                { label: '时间', name: 'time',align :'center',sortable : false },
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

var caption = "获得娃娃记录表";

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
	o.userinfoid= userinfoid;
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