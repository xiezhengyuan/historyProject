<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	#tablebox tr td{border:0;!important}
</style>
<div class="page-header">
	<h1>
		牛人管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			详情
		</small>
	</h1>
</div><!-- /.page-header -->

<ul class="nav nav-tabs" role="tablist">
	<li id="panel_shares_state0" role="presentation" class="active"><a href="#tab_shares_state0" role="tab" data-toggle="tab">股票计划</a></li>
	<li id="panel_shares_state1" role="presentation"><a href="#tab_shares_state1" role="tab" data-toggle="tab">外汇计划</a></li>
	<li id="panel_shares_state2" role="presentation"><a href="#tab_shares_state2" role="tab" data-toggle="tab">美股计划</a></li>
	<li style="position: relative;float: right;">
		<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content row">
	<!-- 股票计划 -->
	<div role="tabpanel" class="tab-pane active" id="tab_shares_state0">
		<ul class="nav nav-tabs" role="tablist">
			<li id="panel_plan_state0" role="presentation" class="active"><a href="#tab_panicbuying" role="tab" data-toggle="tab">抢购中</a></li>
			<li id="panel_plan_state1" role="presentation"><a href="#tab_running" role="tab" data-toggle="tab">运行中</a></li>
			<li id="panel_plan_state2" role="presentation"><a href="#tab_success" role="tab" data-toggle="tab">往期成功</a></li>
		</ul>
		计划总收益：15%          计划成功率：70%
		<div class="tab-content row">
			<!-- 抢购中 -->
			<div role="tabpanel" class="tab-pane active" id="tab_panicbuying">
				<table id="pb_grid_table"></table>
				<div id="pb_grid_pager"></div>
			</div>
			<!-- 运行中 -->
			<div role="tabpanel" class="tab-pane" id="tab_running">
				<table id="running" class="table table-condensed" align="center" style="width: 80%;font-size: 20px;text-align: center;">
				</table>
				<div role="tabpanel" class="tab-pane" id="tab_running_state0">
			 		<span style="font-size: 20px;"></span>
					<table id="running_grid_table0"></table>
					<div id="running_grid_pager0"></div>
				</div>
				 	
				<div role="tabpanel" class="tab-pane" id="tab_running_state1">
					<span style="font-size: 20px"></span>
					<table id="running_grid_table1"></table>
					<div id="running_grid_pager1"></div>
				</div>
			</div>
			<!-- 往期成功 -->
			<div role="tabpanel" class="tab-pane" id="tab_success">
				<div role="tabpanel" class="tab-pane" id="tab_success_state0">
					<table id="success_grid_table"></table>
					<div id="success_grid_pager"></div>
  				</div>
			
			</div>
		</div>
	</div>
	
	<!-- 外汇计划 -->
	<div role="tabpanel" class="tab-pane" id="tab_shares_state1">
		<ul class="nav nav-tabs" role="tablist">
			<li id="panel_fe_plan_state0" role="presentation" class="active"><a href="#tab_fe_panicbuying" role="tab" data-toggle="tab">抢购中</a></li>
			<li id="panel_fe_plan_state1" role="presentation"><a href="#tab_fe_running" role="tab" data-toggle="tab">运行中</a></li>
			<li id="panel_fe_plan_state2" role="presentation"><a href="#tab_fe_success" role="tab" data-toggle="tab">往期成功</a></li>
		</ul>
		计划总收益：15%          计划成功率：70%
		<div class="tab-content row">
			<!-- 抢购中 -->
			<div role="tabpanel" class="tab-pane active" id="tab_fe_panicbuying">
				<table id="fe_grid_table"></table>
				<div id="fe_grid_pager"></div>
			</div>
			<!-- 运行中 -->
			<div role="tabpanel" class="tab-pane" id="tab_fe_running">
				<table id="fe_running" class="table table-condensed" align="center" style="width: 80%;font-size: 20px;text-align: center;">
				</table>
				<div role="tabpanel" class="tab-pane" id="tab_fe_running_state0">
			 		<span style="font-size: 20px;"></span>
					<table id="fe_running_grid_table0"></table>
					<div id="fe_running_grid_pager0"></div>
				</div>
				 	
				<div role="tabpanel" class="tab-pane" id="tab_fe_running_state1">
					<span style="font-size: 20px"></span>
					<table id="fe_running_grid_table1"></table>
					<div id="fe_running_grid_pager1"></div>
				</div>
			</div>
			<!-- 往期成功 -->
			<div role="tabpanel" class="tab-pane" id="tab_fe_success">
				<div role="tabpanel" class="tab-pane" id="tab_fe_success_state0">
					<table id="fe_success_grid_table"></table>
					<div id="fe_success_grid_pager"></div>
  				</div>
			
			</div>
		</div>
	</div>
		
	
	<!-- 美股计划 -->
	<div role="tabpanel" class="tab-pane" id="tab_shares_state2">
		<ul class="nav nav-tabs" role="tablist">
			<li id="panel_us_plan_state0" role="presentation" class="active"><a href="#tab_us_panicbuying" role="tab" data-toggle="tab">抢购中</a></li>
			<li id="panel_us_plan_state1" role="presentation"><a href="#tab_us_running" role="tab" data-toggle="tab">运行中</a></li>
			<li id="panel_us_plan_state2" role="presentation"><a href="#tab_us_success" role="tab" data-toggle="tab">往期成功</a></li>
		</ul>
		计划总收益：15%          计划成功率：70%
		<div class="tab-content row">
			<!-- 抢购中 -->
			<div role="tabpanel" class="tab-pane active" id="tab_us_panicbuying">
				<table id="us_grid_table"></table>
				<div id="us_grid_pager"></div>
			</div>
			<!-- 运行中 -->
			<div role="tabpanel" class="tab-pane" id="tab_us_running">
				<table id="us_running" class="table table-condensed" align="center" style="width: 80%;font-size: 20px;text-align: center;">
				</table>
				<div role="tabpanel" class="tab-pane" id="tab_us_running_state0">
			 		<span style="font-size: 20px;"></span>
					<table id="us_running_grid_table0"></table>
					<div id="us_running_grid_pager0"></div>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab_us_running_state1">
					<span style="font-size: 20px"></span>
					<table id="us_running_grid_table1"></table>
					<div id="us_running_grid_pager1"></div>
				</div>
			</div>
			<!-- 往期成功 -->
			<div role="tabpanel" class="tab-pane" id="tab_us_success">
				<div role="tabpanel" class="tab-pane" id="tab_us_success_state0">
					<table id="us_success_grid_table"></table>
					<div id="us_success_grid_pager"></div>
  				</div>
			</div>
		</div>
	</div>
</div>	

<script>
var id = <%=request.getParameter("id")%>;
//返回到进入时的页码
var page = <%=request.getParameter("page")%>;

Q.grid_selector = "pb_grid_table";
Q.pager_selector = "pb_grid_pager";

var url = Q.url+"/example/panicBuying.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '目标收益', name: 'targetprofit',align :'center', sortable : false },
                { label: '计划详情', name: 'detail',align :'center', sortable : false ,formatter:function(value, grid, rows, state){
                	var detail="<table id='tablebox' align='center' width='80%' border=0>";
                	detail+="<tr><td><span>大盘同期：</span></td><td width='30%'>"+value.big+"</td><td><span>创建计划时间：</span></td><td>"+value.time+"</td>";
                	detail+="<tr><td><span>止损线：</span></td><td>"+value.stopLine+"</td><td><span>计划开始时间：</span></td><td>"+value.startTime+"</td>";
                	detail+="<tr><td><span>最长时限：</span></td><td>"+value.days+"</td><td><span>计划结束时间：</span></td><td>"+value.endTime+"</td>";
                	detail+="</table>";
                	return detail;
                }},
                { label: '购买人数', name: 'peopleCount',align :'center', sortable : false },
                {
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    formatter: function (value, grid, rows, state) {
                    	var btn = "";
                    	if(rows.ischoiceness==0){
                   			btn += "<button title=\"设为精选\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"select_pb_grid_table(" + rows.id + ","+Q.grid_selector+")\">设为精选</button>";
                    	}else{
                    		btn += "-";
                    	}
                    	return  btn;
                    }
                }
            	];

var caption = "抢购中";

var postData ={"id":id,"type":0};

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


function getcondition(){
	var o = {};
	o.keyword = $('#keyword').val();
	return o;
}

function queryexbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
}

//详情函数----命名规则  detail_tableid
function select_pb_grid_table(id,grid_table){
	Q.confirm("设为精选，确定吗？",function(r){
		if(r){
			$._ajax({
				url:Q.url+"/example/select.action",
				data:{"id":id},
			    success:function(data){
			    	if(data.op=="success"){
			    		Q.toastr("提示","已精选",'success');
			    		$("#"+grid_table).setGridParam({postData:postData,page:$('#'+grid_table).getGridParam('page')}).trigger("reloadGrid"); 
			    	}else{
			    		Q.toastr("提示","操作失败",'warning');
			    	}
			    }
			})
		}
	})
}

function returnbfbtn(){
	var _url = "example/info.jsp?page="+page;
	Q.viewJsp(Q.page_content,_url);
}

//查看股票计划
$('#panel_shares_state0').click(function(){
	//抢购中 table刷新
	$("#pb_grid_table").trigger("reloadGrid");
	//运行中 table刷新
	//$("#running").trigger("reloadGrid");
	//$("#running_grid_table0").trigger("reloadGrid"); 
	//$("#running_grid_table1").trigger("reloadGrid"); 
	//Q.grid_selector = "running_grid_table0";
	//Q.pager_selector = "running_grid_pager0";
	//Q.viewJsp("#tab_running","example/running.jsp");
	$("#running").empty();
	running_table();
	$("#running_grid_table0").trigger("reloadGrid");
	$("#running_grid_table1").trigger("reloadGrid");
	
	//计划成功 table刷新
	
	
	$("#running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
})

//查看外汇计划
$('#panel_shares_state1').click(function(){
	//Q.grid_selector = "pb_grid_table";
	//Q.pager_selector = "pb_grid_pager";
	var url = Q.url+"/example/panicBuying.action";
	var colModel = [
	                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	                { label: '目标收益', name: 'targetprofit',align :'center', sortable : false },
	                { label: '计划详情', name: 'detail',align :'center', sortable : false ,formatter:function(value, grid, rows, state){
	                	var detail="<table id='tablebox' align='center' width='80%' border=0>";
	                	detail+="<tr><td><span>大盘同期：</span></td><td width='30%'>"+value.big+"</td><td><span>创建计划时间：</span></td><td>"+value.time+"</td>";
	                	detail+="<tr><td><span>止损线：</span></td><td>"+value.stopLine+"</td><td><span>计划开始时间：</span></td><td>"+value.startTime+"</td>";
	                	detail+="<tr><td><span>最长时限：</span></td><td>"+value.days+"</td><td><span>计划结束时间：</span></td><td>"+value.endTime+"</td>";
	                	detail+="</table>";
	                	return detail;
	                }},
	                { label: '购买人数', name: 'peopleCount',align :'center', sortable : false },
	                {
	                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
	                    formatter: function (value, grid, rows, state) {
	                    	var btn = "";
	                    	if(rows.ischoiceness==0){
	                   			btn += "<button title=\"设为精选\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"select_pb_grid_table(" + rows.id + ",'fe_grid_table')\">设为精选</button>";
	                    	}else{
	                    		btn += "-";
	                    	}
	                    	return  btn;
	                    }
	                }
	            	];
	var caption = "抢购中";
	var postData ={"id":id,"type":1};
	Q.ExtJqGrid({
		"grid":"fe_grid_table",
		"pager":"fe_grid_pager",
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	});
	//抢购中 table刷新
	$("#fe_grid_table").trigger("reloadGrid");
	//运行中 table刷新
	$("#fe_running").empty();
	fe_running_table();
	$("#fe_running_grid_table0").trigger("reloadGrid");
	$("#fe_running_grid_table1").trigger("reloadGrid");
	
	//计划成功 table刷新
	
	$("#fe_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	
})

//查看美股计划
$('#panel_shares_state2').click(function(){
	//Q.viewJsp("#tab_shares_state2","example/plan.jsp");
	var url = Q.url+"/example/panicBuying.action";
	var colModel = [
	                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	                { label: '目标收益', name: 'targetprofit',align :'center', sortable : false },
	                { label: '计划详情', name: 'detail',align :'center', sortable : false ,formatter:function(value, grid, rows, state){
	                	var detail="<table id='tablebox' align='center' width='80%' border=0>";
	                	detail+="<tr><td><span>大盘同期：</span></td><td width='30%'>"+value.big+"</td><td><span>创建计划时间：</span></td><td>"+value.time+"</td>";
	                	detail+="<tr><td><span>止损线：</span></td><td>"+value.stopLine+"</td><td><span>计划开始时间：</span></td><td>"+value.startTime+"</td>";
	                	detail+="<tr><td><span>最长时限：</span></td><td>"+value.days+"</td><td><span>计划结束时间：</span></td><td>"+value.endTime+"</td>";
	                	detail+="</table>";
	                	return detail;
	                }},
	                { label: '购买人数', name: 'peopleCount',align :'center', sortable : false },
	                {
	                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
	                    formatter: function (value, grid, rows, state) {
	                    	var btn = "";
	                    	if(rows.ischoiceness==0){
	                   			btn += "<button title=\"设为精选\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"select_pb_grid_table(" + rows.id + ",'us_grid_table')\">设为精选</button>";
	                    	}else{
	                    		btn += "-";
	                    	}
	                    	return  btn;
	                    }
	                }
	            	];

	var caption = "抢购中";
	var postData ={"id":id,"type":2};
	Q.ExtJqGrid({
		"grid":"us_grid_table",
		"pager":"us_grid_pager",
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	});
	
	//Q.viewJsp("#tab_shares_state1","example/plan.jsp");
	
	//抢购中 table刷新
	$("#us_grid_table").trigger("reloadGrid");
	//运行中 table刷新
	$("#us_running").empty();
	us_running_table();
	$("#us_running_grid_table0").trigger("reloadGrid");
	$("#us_running_grid_table1").trigger("reloadGrid");
	
	//计划成功 table刷新
	$("#us_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
})



//股票抢购中
$('#panel_plan_state0').click(function(){
	$("#pb_grid_table").trigger("reloadGrid"); 
	//Q.viewJsp("#tab_running","example/running.jsp");
})
//外汇抢购中
$('#panel_fe_plan_state0').click(function(){
	$("#fe_grid_table").trigger("reloadGrid"); 
	//Q.viewJsp("#tab_running","example/running.jsp");
})
//美股抢购中
$('#panel_us_plan_state0').click(function(){
	$("#us_grid_table").trigger("reloadGrid"); 
	//Q.viewJsp("#tab_running","example/running.jsp");
})


//股票运行中
$('#panel_plan_state1').click(function(){
	$("#running").empty();
	plan_running();
})
//外汇运行中
$('#panel_fe_plan_state1').click(function(){
	$("#fe_running").empty();
	fe_plan_running();
})
//美股运行中
$('#panel_us_plan_state1').click(function(){
	$("#us_running").empty();
	us_plan_running();
})


//股票往期成功
$('#panel_plan_state2').click(function(){
})
//外汇往期成功
$('#panel_fe_plan_state2').click(function(){
})
//美股往期成功
$('#panel_us_plan_state2').click(function(){
})

//股票计划
function running_table(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":0},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    	}else if(data.op=="empty"){
	    		$("#running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
function plan_running(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":0},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    		//最近交易
	    		//Q.grid_selector = "running_grid_table0";
	    		//Q.pager_selector = "running_grid_pager0";
				//$("#tab_running_state0 span").html("最近交易");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '买入价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '数量', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '时间', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "最近交易";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'running_grid_table0',
	    			"pager":'running_grid_pager0',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    			
	    		//当前持仓
	    		//Q.grid_selector = "running_grid_table1";
	    		//Q.pager_selector = "running_grid_pager1";
	    		//$("#tab_running_state1 span").html("当前持仓");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '成本价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '浮动盈亏', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '盈亏比例', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "当前持仓";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'running_grid_table1',
	    			"pager":'running_grid_pager1',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    	}else if(data.op=="empty"){
	    		$("#running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
//股票计划结束

//外汇计划
function fe_running_table(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":1},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#fe_running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    	}else if(data.op=="empty"){
	    		$("#fe_running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
function fe_plan_running(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":1},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#fe_running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    		//最近交易
	    		//Q.grid_selector = "running_grid_table0";
	    		//Q.pager_selector = "running_grid_pager0";
				//$("#tab_running_state0 span").html("最近交易");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '买入价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '数量', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '时间', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "最近交易";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'fe_running_grid_table0',
	    			"pager":'fe_running_grid_pager0',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    			
	    		//当前持仓
	    		//Q.grid_selector = "running_grid_table1";
	    		//Q.pager_selector = "running_grid_pager1";
	    		//$("#tab_running_state1 span").html("当前持仓");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '成本价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '浮动盈亏', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '盈亏比例', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "当前持仓";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'fe_running_grid_table1',
	    			"pager":'fe_running_grid_pager1',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    	}else if(data.op=="empty"){
	    		$("#fe_running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
//外汇计划结束

//美股计划
function us_running_table(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":2},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#us_running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    	}else if(data.op=="empty"){
	    		$("#us_running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
function us_plan_running(){
	$._ajax({
		url:Q.url+"/example/planRunning.action",
		data:{"id":id,"type":2},
	    success:function(data){
	    	if(data.op=="success"){
	    		var btn1="";
	    		if(data.ischoiceness==0){
	    			btn1+="<button title='设为精选' class='btn btn-success btn-xs' style='color:#f60;margin-left:3px;' onclick='change("+id+")'>设为精选</button>";
	    		}
	    		$("#us_running").append(
   				"<tr>"
				+"	<td rowspan='3' style='vertical-align: middle;'>目标收益："+data.targetprofit+"</td>"
				+"	<td>大盘同期：15%</td>"
				+"	<td>创建计划时间："+data.time+"</td>"
				+"	<td>已运行："+data.before+"天</td>"
				+"	<td rowspan='3' width='10%'>"+btn1+"</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>止损线："+data.stopline+"</td>"
				+"	<td>计划开始时间："+data.starttime+"</td>"
				+"	<td rowspan='2'>剩余："+data.after+"天</td>"
				+"</tr>"
				+"<tr>"
				+"	<td>最长时限："+data.days+"天</td>"
				+"	<td>计划结束时间："+data.endtime+"</td>"
				+"</tr>"
	    		);
	    		//最近交易
	    		//Q.grid_selector = "running_grid_table0";
	    		//Q.pager_selector = "running_grid_pager0";
				//$("#tab_running_state0 span").html("最近交易");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '买入价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '数量', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '时间', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "最近交易";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'us_running_grid_table0',
	    			"pager":'us_running_grid_pager0',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    			
	    		//当前持仓
	    		//Q.grid_selector = "running_grid_table1";
	    		//Q.pager_selector = "running_grid_pager1";
	    		//$("#tab_running_state1 span").html("当前持仓");
	    		var url = Q.url+"/example/findAuthstrList.action";
	    		var colModel = [
	    		                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	    		                { label: '名称', name: 'nickname',align :'center', sortable : false },
	    		                { label: '成本价', name: 'mobile',align :'center', sortable : false },
	    		                { label: '仓位', name: 'golds',align :'center', sortable : false },
	    		                { label: '浮动盈亏', name: 'virtualcapital',align :'center', sortable : false },
	    		                { label: '盈亏比例', name: 'time',align :'center', sortable : false },
	    		            	];
	    		var caption = "当前持仓";
	    		var postData = {};
	    		Q.ExtJqGrid({
	    			"grid":'us_running_grid_table1',
	    			"pager":'us_running_grid_pager1',
	    			"url":url,
	    			"param":postData,
	    			"model":colModel,
	    			"caption":caption
	    		});
	    	}else if(data.op=="empty"){
	    		$("#us_running").append(data.msg);
	    	}else{
	    		Q.toastr("提示","操作失败",'warning');
	    	}
	    }
	})
}
//美股计划结束
	/* $("#pb_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	
	$("#fe_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#fe_success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	
	$("#us_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_running_grid_table0").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_running_grid_table1").jqGrid( 'setGridWidth', $(".page-content").width() );
	$("#us_success_grid_table").jqGrid( 'setGridWidth', $(".page-content").width() ); */

</script>
<script src="js/jqgridresize.js"></script>