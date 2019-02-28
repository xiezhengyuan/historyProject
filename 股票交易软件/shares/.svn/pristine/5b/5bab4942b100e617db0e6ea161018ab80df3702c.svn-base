<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		提现审核管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_cash" role="tab" data-toggle="tab">待审核</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_cash_state1" role="tab" data-toggle="tab">待打款</a></li>
  <li id="panel_state2" role="presentation"><a href="#tab_cash_state2 " role="tab" data-toggle="tab">已打款</a></li>
			<li style="position: relative;float: right;">
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="username" /></td>
			<td><label for="usermobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="usermobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querycashbycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	 </li>
</ul>

<div class="tab-content">
<div role="tabpanel" class="tab-pane active" id="tab_cash">
								<!-- jqgrid 用户列表-->
							<table id="cash_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="cash_grid_pager"></div>

</div>

	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_cash_state1"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_cash_state2"></div>					
					
</div>						
					
					
<div class="page-header">
	<h1>
		充值管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->
<div><span style="font-size: 15px">累计充值：</span><span id="totalrecharge"></span></div>
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_recharge" role="tab" data-toggle="tab">充值明细</a></li>
  <li id="panel_detail" role="presentation"><a href="#tab_recharge_detail" role="tab" data-toggle="tab">充值统计</a></li>
			<li style="position: relative;float: right;">
		
			<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="name" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="name" /></td>
			<td><label for="mobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="mobile" /></td>
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryrechargebycondition()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
	 </li>
</ul>
<div class="tab-content">

<div role="tabpanel" class="tab-pane active" id="tab_recharge">
<div class="col-xs-12">
		
			<div class="col-sm-10" style="width:15%;float:left ;margin-bottom:-100px;margin-left:300px;padding-top: 4px; z-index:100" >
				<div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd hh:ii:ss">
					<input class="form-control" placeholder="起始日期" type="text" value=""  id="starttime">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			
			<div class="col-sm-10" style="width:15%;float:left;margin-bottom:-100px;margin-left:450px;padding-top: 4px;z-index:100">
				<div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd hh:ii:ss">
					<input class="form-control" placeholder="结束日期" type="text" value=""  id="endtime">
					<span class="input-group-addon "  ><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			
			<!-- <div class="col-sm-10" style="width:5%;float:left;margin-bottom: -100px;padding-top: 4px;margin-left:650px;">
				<button class="btn btn-purple btn-sm" id = "outport" onclick="outportexcel()" style="z-index:100">选择时间导出</button> <a id="openexcel" href="#"><span id="exporttext"></span></a>
			</div> -->
	</div>

								<!-- jqgrid 用户列表-->
							<table id="recharge_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="recharge_grid_pager"></div>
</div>

	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_recharge_detail"></div>
  	
					
</div>	
					
	
<script>


Q.grid_selector1 = "cash_grid_table";
Q.pager_selector1 = "cash_grid_pager";

var url = Q.url+"/money/querycash.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '提现金币', name: 'amount',align :'center', sortable : false },
                { label: '对应额度', name: 'rmbamount',align :'center', sortable : false },
                { label: '申请时间', name: 'time',align :'center',sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	/*  btn += Q.gridbtn_change(Q.grid_selector1,rows.id,rows.state) ;  */
                    	btn += Q.gridbtn_pass(Q.grid_selector1,rows.id) ;
                    	btn += Q.gridbtn_nopass(Q.grid_selector1,rows.id) ;
                    	return  btn;
                    }  
                }
            	];

var caption = "提现审核列表";

var postData = {};
postData = getcondition1();
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
//Q.extJqGrid(Q.grid_selector1,Q.pager_selector1,url,postData,colModel,caption,null,function(d){
	
/*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
//});

Q.ExtJqGrid({
	"grid":Q.grid_selector1,
	"pager":Q.pager_selector1,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption,
	'opt':{"height":300} ,
  callback:function(d){
	
 }
})

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition1(){
	var o = {};
	o.name = $('#username').val();
	o.mobile = $('#usermobile').val();
	o.state = 0;
	return o;
}


function querycashbycondition(){
	postData = getcondition1();
	$("#"+Q.grid_selector1).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
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

//通过
function pass_cash_grid_table(id){
	var state=1;
	changecashstate(id,state);
}

//不通过
function notpass_cash_grid_table(id){
	var state=-1;
	changecashstate(id,state);
}


function changecashstate(id,state){
	$._ajax({
		url:Q.url+"/money/updatecashinfo.action",
		data:{"cashinfoid":id,"state":state},
		success:function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					
					queryorderbycondition();
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

//查看待打款
$('#panel_state1').click(function(){
	Q.viewJsp("#tab_cash_state1","money/cash_daidakuan.jsp");
})
//查看已打款
$('#panel_state2').click(function(){
	Q.viewJsp("#tab_cash_state2","money/cash_yidakuan.jsp");
})
</script>
<!--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------  -->
<!--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------  -->
<script>
Q.grid_selector = "recharge_grid_table";
Q.pager_selector = "recharge_grid_pager";

var url = Q.url+"/money/queryrecharge.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '充值金额', name: 'amount',align :'center', sortable : false },
                { label: '充值金币', name: 'golds',align :'center', sortable : false },
                { label: '充值时间', name: 'time',align :'center',sortable : false },
                /* {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	  btn += Q.gridbtn_change(Q.grid_selector,rows.id,rows.state) ; 
                    	btn += Q.gridbtn_pass(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_nopass(Q.grid_selector,rows.id) ;
                    	return  btn;
                    }  
                } */
            	];

var caption = "充值列表";

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
/* Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
 
  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); 
}); */

Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption,
	'opt':{"height":300},
	callback:function(d){
		$('#totalrecharge').html(d.totalrecharge);
	 }
})

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.name = $('#name').val();
	o.mobile = $('#mobile').val();
	o.starttime = $('#starttime').val();
	o.endtime = $('#endtime').val();
	return o;
}


function queryrechargebycondition(){
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


//充值明细
$('#panel_detail').click(function(){
	Q.viewJsp("#tab_recharge_detail","money/recharge_detail.jsp");
})
</script>
<script>
$(".form_date").datetimepicker({
	language:  'zh-CN',
    format: 'yyyy-MM-dd',
    autoclose: true,
    todayBtn: false,
    pickerPosition: "bottom-left",
    weekStart: 0,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
 	minView :2, 
	forceParse: 0
}); 
</script>	
<script src="js/jqgridresize.js"></script>