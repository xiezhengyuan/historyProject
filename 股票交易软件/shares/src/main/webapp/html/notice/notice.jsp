<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.hxy.isw.entity.AccountInfo"%>
<link rel="stylesheet" href="css/bootstrap-select.css">
  <script src="js/bootstrap-select.js"></script>
<div class="page-header">
	<h1>
		发布公告
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
	 <div class="modal-header" style="margin-top:20px;">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">公告标题：</label></td><td><input type="text" id="keyword" /></td>
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryempbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	

</div>
<div style="float:right;">
<label Style="color:red" id="abcd">新增定时公告:</label>
	<button id="addempbtn1" class="btn btn-purple btn-sm" onclick="addnotice1()" data-placement="top" title="新增定时公告" data-toggle="modal" data-target="#addnotice1">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	<label Style="color:red" id="abcdd">新增即时公告:</label>
	<button id="addempbtn" class="btn btn-purple btn-sm" onclick="addnotice()" data-placement="top" title="新增即时公告" data-toggle="modal" data-target="#addnotice">
	<span class="glyphicon glyphicon-plus"></span>
	</button> 
	
	<!-- <button id="filemanagerbtn" class="btn btn-danger btn-sm" onclick="filemanager()" data-placement="top" title="文件管理" data-toggle="modal" data-target="#filemanager">
	<span class="glyphicon glyphicon-file"></span>
	</button> --> 
	</div>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist" >
  <li role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">公告列表</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">待发布</a></li>
  
 
 
</ul>

<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager"></div>
							<div>
	
	
</div>
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  
   <!-- panel3 start -->
 <!--  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state2"></div> -->

  	
</div>		
<!-- 新增即时公告（Modal） -->
<div id="addnotice" ></div>
<!-- 新增定时公告（Modal） -->
<div id="addnotice1" ></div>
<script>


//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order_grid_table";
Q.pager_selector = "order_grid_pager";

var url = Q.url+"/notic/queryhnotice.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '公告标题', name: 'noticename',align :'center', sortable : false },
                { label: '发布类型', name: 'target',align :'center', sortable : false },
                { label: '消息类型', name: 'state',align :'center', sortable : false },
                { label: '创建时间', name: 'createtime',align :'center', sortable : false },
                { label: '发送时间', name: 'sendtime',align :'center', sortable : false },
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id);
                    	
                    	return  btn;
                    }  
                }
                
            	];

var caption = "历史发布";

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
/*  Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	});  */
	 
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
	/* $('#keyword').value=""; */
	o.keyword= $('#keyword').val();
	o.state = 0;
	return o;
}


function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
}





/* function filemanager(){
	var _url= 'zyUpload/uploadimg.jsp';
	
	Q.viewJsp('#div_img',_url);
} */

$('#panel_state1').click(function(){
	Q.viewJsp("#tab_orderinfo_state1","notice/waitnotice.jsp");
})
function addnotice(){
	var _url= 'notice/addhnotice.jsp';
	
	Q.viewJsp('#addnotice',_url);
}
function addnotice1(){
	var _url= 'notice/addtimnotice.jsp';
	
	Q.viewJsp('#addnotice1',_url);
}
function detail_order_grid_table(id){
	var _url= 'notice/noticeDetail.jsp?id='+id;
	
	Q.viewJsp(Q.page_content,_url);
}
<%-- var roles = '<%=((AccountInfo)session.getAttribute("loginEmp")).getRole()%>'; --%>
var roles = ${loginEmp.role};
if(roles==2||roles==4||roles==5||role==3){
	$("#addempbtn1").css("display", "none");
	$("#addempbtn").css("display", "none");
	$("#panel_state1").css("display", "none");
	$("#abcd").css("display", "none");
	$("#abcdd").css("display", "none");
}
/* //禁言用户
$('#panel_state2').click(function(){
	Q.viewJsp("#tab_orderinfo_state2","postings/postingsbanned.jsp");
})

//新增帖子
$('#panel_state3').click(function(){
	Q.viewJsp("#tab_orderinfo_state3","postings/addpostings.jsp");
})
//评论列表
$('#panel_state4').click(function(){
	Q.viewJsp("#tab_orderinfo_state4","postings/postingscomments.jsp");
}) */
</script>
