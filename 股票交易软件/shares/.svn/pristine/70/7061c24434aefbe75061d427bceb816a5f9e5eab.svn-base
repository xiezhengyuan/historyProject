<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">

</style>
<div class="modal  inmodal fade" id="edituser4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content" style="width:652px">
			<div class="modal-header" style="height:  70px">
			
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h3 class="modal-title" id="myModalLabel">
					业务员转移
		        <small>
			    <i class="ace-icon fa fa-angle-double-right"></i>
			              转移详情
		        </small>
				</h3>
			</div>
	<div class="row">
		<div class="col-xs-12">
			<h4>&nbsp;&nbsp;&nbsp;&nbsp;转入公司：<span id="incompany" style="font-size: 15px;"></span>&nbsp;&nbsp;&nbsp;&nbsp;转入经理：<span id="insalesman" style="font-size: 15px;"></span>
			&nbsp;&nbsp;&nbsp;&nbsp;操作人：<span id="doitman" style="font-size: 15px;"></span>
			</h4> 
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
			<tr id="tabletd">
			    
			    <td style="width: 160px"> <input id="querybysalesmaninfo" value="" type="text" placeholder="业务员姓名/手机号" />
			   
			   </td>
			   
	          <td style="width: 60px; text-align: center; ">
	          <button id="addtoyinfobtn" class="btn btn-purple btn-sm" onclick="querybycondition()" data-placement="top" title="查询" data-toggle="modal" data-target="#queryuserinfo">查询
	          </button> 
	         </td> 
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" style="opacity:0;pointer-events:none" data-toggle="tooltip" data-placement="top" title="查询" >
	<span class="glyphicon glyphicon-search"></span>
	</button >
	<div style="float:right;">
	</div>
	</div>
	</div>
	</div>
	
	                       <table id="order_grid_table4" ></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager4"></div>
			
		   
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#edituser4').modal('show');

$._ajax({
	url:Q.url+"/salesmanchange/querytop.action",
	data:{"id":<%= request.getParameter("id") %>},
	type:'post',
    success:function(data){
    	$("#incompany").html(data.incompany);
    	$("#insalesman").html(data.inaccount);
    	$("#doitman").html(data.doitman);
    }
})

Q.grid_selector4= "order_grid_table4";
Q.pager_selector4= "order_grid_pager4";
var postData4 = {};
var url4 = Q.url+"/salesmanchange/querychangedetli.action";

var colModel4 = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'moblie',align :'center', sortable : false },
                { label: '转出公司', name: 'outcompany',align :'center', sortable : false,},
                { label: '转出经理', name: 'outjl',align :'center', sortable : false,},
            	];

var caption4 = "转移详情表";


postData4 = getcondition4();
function getcondition4(){
	var o = {};
	o.querybysalesmaninfo= $('#querybysalesmaninfo').val();
	o.id=<%= request.getParameter("id") %>;
	return o;
}
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
/* Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	}); */
	
Q.ExtJqGrid({
	"grid":Q.grid_selector4,
	"pager":Q.pager_selector4,
	"url":url4,
	"param":postData4,
	"model":colModel4,
	"caption":caption4,
});

	function querybycondition(){
		postData4 = getcondition4();
		$("#"+Q.grid_selector4).setGridParam({postData:postData4,page:1}).trigger("reloadGrid"); 
	}
</script>