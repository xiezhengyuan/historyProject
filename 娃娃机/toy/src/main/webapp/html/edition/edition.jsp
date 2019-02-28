<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		版本管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li id="panel_state0" role="presentation" class="active"><a href="#tab_orderinfo_state0" role="tab" data-toggle="tab">andorid版本管理</a></li>
  <li id="panel_state1" role="presentation"><a href="#tab_orderinfo_state1" role="tab" data-toggle="tab">apple版本管理</a></li>

</ul>
<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state0">
 
  	</div>
  	
  	<!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_orderinfo_state1"></div>
  

 
  	
</div>		







<<script>


$('#panel_state0').click(function(){
	
    Q.viewJsp("#tab_orderinfo_state0","edition/andorid.jsp");
})

$('#panel_state0').click();

$('#panel_state1').click(function(){
	
	Q.viewJsp("#tab_orderinfo_state1","edition/ios.jsp");
})


</script>

