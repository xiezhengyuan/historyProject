<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<div class="page-header">
	<h1>
		充值明细
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			明细
		</small>
	</h1><br>
	<h1 id="p_mingxi"></h1>>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li id="panel_state0" role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">充值明细</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">充值统计</a></li>
  
</ul>

  
<div class="tab-content"
style="overflow:hidden">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
   
       
  		
  	</div>
  
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
   	
</div>	
<script>


Q.viewJsp("#tab_orderinfo_state0","recharge/info.jsp");
queryMoney();
function queryMoney(){
	$._ajax({
		url: Q.url+"/managercontrol/queryRecharge.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = "累计充值：";
			
				opts+= data.money+"";
			
			$(p_mingxi).html(opts);
			
		}
	});
}



$('#panel_state1').click(function(){
	
	Q.viewJsp("#tab_orderinfo_state1","recharge/statistics.jsp");

})
 
</script>

