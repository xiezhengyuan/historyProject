<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<div class="page-header">
	<h1>
		统计
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			统计
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li id="panel_state0" role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">收益统计</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">用户统计</a></li>
  
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


Q.viewJsp("#tab_orderinfo_state0","companystatistics/transactionstatistic.jsp");

//邀请成员

$('#panel_state0').click(function(){
	    
	Q.viewJsp("#tab_orderinfo_state0","companystatistics/transactionstatistic.jsp");
})

$('#panel_state1').click(function(){
	
	Q.viewJsp("#tab_orderinfo_state1","companystatistics/userstatistic.jsp");

})
 
</script>

