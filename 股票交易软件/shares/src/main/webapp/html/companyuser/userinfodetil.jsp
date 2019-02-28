<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<style type="text/css">
  .form-group input{
      width: 800px;
      background-color:white;
   }
  #gold{
     border-color: yellow;
     widows:750px;
     float: left; 
     
  }

</style>
<div class="page-header">
	<h1>
		用户管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			用户详情
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li id="panel_state0" role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">邀请成员</a></li>
  <li id="panel_state2" role="presentation"><a href="#tab_orderinfo_state2" role="tab" data-toggle="tab">金币明细</a></li>
  <li id="panel_state3" role="presentation"><a href="#tab_orderinfo_state3" role="tab" data-toggle="tab">资金明细</a></li>
 
</ul>

  
<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
  		
  	</div>
  
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state2"></div>
  
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state3"></div>
 
  	
</div>	
<script>

var userid = '<%=request.getParameter("userid")%>';

Q.viewJsp("#tab_orderinfo_state0","companyuser/baseuserinfodetile.jsp");

//邀请成员

$('#panel_state0').click(function(){
	    
 		$("#tab_orderinfo_state1").hide();
 		$("#tab_orderinfo_state2").hide();
 		$("#tab_orderinfo_state3").hide();
 		$("#tab_orderinfo_state0").show();
})

$('#panel_state1').click(function(){
	if($("#tab_orderinfo_state1").html()==""){
		Q.viewJsp("#tab_orderinfo_state1","usermanage/inviteotheruser.jsp");
	}else{

 		$("#tab_orderinfo_state2").hide();
 		$("#tab_orderinfo_state3").hide();
 		$("#tab_orderinfo_state0").hide();
 		$("#tab_orderinfo_state1").show();
	}
    
})

//金币明细
$('#panel_state2').click(function(){
	if($("#tab_orderinfo_state2").html()==""){
		Q.viewJsp("#tab_orderinfo_state2","usermanage/usergolddetil.jsp");
	}else{
		$("#tab_orderinfo_state1").hide();
 		$("#tab_orderinfo_state3").hide();
 		$("#tab_orderinfo_state0").hide();
		$("#tab_orderinfo_state2").show();
 		
	} 
    
    
});  

//资金明细
$('#panel_state3').click(function(){
	if($("#tab_orderinfo_state3").html()==""){
		  Q.viewJsp("#tab_orderinfo_state3","usermanage/usermoneydetil.jsp");
	}else{

		$("#tab_orderinfo_state1").hide();
 		$("#tab_orderinfo_state0").hide();
		$("#tab_orderinfo_state2").hide();
		$("#tab_orderinfo_state3").show();
	} 
  
    
})
</script>

