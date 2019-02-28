<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		用户金币详情
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			查看
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_userinfo" role="tab" data-toggle="tab">基本信息</a></li>
   <li id="panel_lower" role="presentation"><a href="#tab_lower" role="tab" data-toggle="tab">正在持仓(股票)</a></li>
  <li id="panel_transaction" role="presentation"><a href="#tab_transaction" role="tab" data-toggle="tab">委托挂单(股票)</a></li>
  <li id="panel_transaction1" role="presentation"><a href="#tab_transaction1" role="tab" data-toggle="tab">历史交易(股票)</a></li>
  <li id="panel_user" role="presentation"><a href="#tab_user" role="tab" data-toggle="tab">正在持仓(外汇)</a></li>
  <li id="panel_user1" role="presentation"><a href="#tab_user1" role="tab" data-toggle="tab">委托挂单(外汇)</a></li>
   <li id="panel_user2" role="presentation"><a href="#tab_user2" role="tab" data-toggle="tab">历史交易(外汇)</a></li>
   
   <li id="panel_stocks" role="presentation"><a href="#tab_stocks" role="tab" data-toggle="tab">正在持仓(美股)</a></li>
  <li id="panel_stocks1" role="presentation"><a href="#tab_stocks1" role="tab" data-toggle="tab">委托挂单(美股)</a></li>
   <li id="panel_stocks2" role="presentation"><a href="#tab_stocks2" role="tab" data-toggle="tab">历史交易(美股)</a></li>
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_userinfo">
   <form class="form-horizontal" role="form" id="form_edituser">
   <input type="hidden" class="form-control" id="id" name="id" placeholder="">
   
   				<div class="form-group" style="display:none" >
					<label for="lefttitle" class="col-sm-2 control-label">id</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="id" msg="请输入名字" id="id"  placeholder="">
					</div>
					</div>
				<div class="form-group">
					<label for="lefttitle" class="col-sm-2 control-label">名字</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name" msg="请输入名字" id="name"  placeholder="">
					</div>
					</div>
			
				  
				  <div class="form-group">
					<label for="leftcontent" class="col-sm-2 control-label">金币</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="golds" msg="请输入金币" id="golds" placeholder="">
					</textarea>
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="righttitle" class="col-sm-2 control-label">虚拟资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="virtualcapital" msg="请输入虚拟资金" id="virtualcapital" placeholder="">
					</div>
					</div>
		
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="edituserinfo">修改</button>
	</div>
		</form>		
  </div>
   <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
  <div role="tabpanel" class="tab-pane" id="tab_transaction1"></div>
   <!-- panel4 start -->
  <div role="tabpanel" class="tab-pane" id="tab_user"></div>
  <div role="tabpanel" class="tab-pane" id="tab_user1"></div>
  <div role="tabpanel" class="tab-pane" id="tab_user2"></div>

<div role="tabpanel" class="tab-pane" id="tab_stocks"></div>
<div role="tabpanel" class="tab-pane" id="tab_stocks1"></div>
<div role="tabpanel" class="tab-pane" id="tab_stocks2"></div>

</div>

<script>

var UserinfoId = '<%=request.getParameter("UserinfoId")%>';

//根据ID查询信息
Q.getObjById("UserInfo",UserinfoId,function(d){
	
	$('#form_edituser').resetObjectForm(d);

})

function returnbfbtn(){
	var from = "trading/trading.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#edituserinfo').click(function(){
	if(!$('#form_edituser').checkForm())return;
	
	var o = $('#form_edituser').serializeObject();
	
	$._ajax({
		url: Q.url+"/trading/modifytrading.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
				}else if(data.op == 'fail')
					Q.toastr("提示",data.msg,'error');
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	});
	
	
})
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","trading/position.jsp?tradingId="+UserinfoId);
})
$('#panel_transaction').click(function(){
	Q.viewJsp("#tab_transaction","trading/orders.jsp?tradingId="+UserinfoId);
})
$('#panel_transaction1').click(function(){
	Q.viewJsp("#tab_transaction1","trading/historytrading.jsp?tradingId="+UserinfoId);
})
$('#panel_user').click(function(){
	Q.viewJsp("#tab_user","trading/forex.jsp?tradingId="+UserinfoId);
})
$('#panel_user1').click(function(){
	Q.viewJsp("#tab_user1","trading/forex1.jsp?tradingId="+UserinfoId);
})
$('#panel_user2').click(function(){
	Q.viewJsp("#tab_user2","trading/historyforex.jsp?tradingId="+UserinfoId);
})
$('#panel_stocks').click(function(){
	Q.viewJsp("#tab_stocks","trading/stocks.jsp?tradingId="+UserinfoId);
})
$('#panel_stocks1').click(function(){
	Q.viewJsp("#tab_stocks1","trading/stocksw.jsp?tradingId="+UserinfoId);
})
$('#panel_stocks2').click(function(){
	Q.viewJsp("#tab_stocks2","trading/stocksh.jsp?tradingId="+UserinfoId);
})
</script>