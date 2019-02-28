<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  
       
     
  		<div class="modal-body"  style="overflow-y: auto;">
			 <form class="form-horizontal" role="form" id="form_edituser">
            <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">微信号</label>
					<div class="col-sm-10">
					<input type="text"  class="form-control" name="wxid"  id="wxid"  readonly="readonly">
					</div>
					</div>
			
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">昵称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="nickname"  id="nickname"  readonly="readonly">
					</div>
					
					</div>
			
			         <div class="form-group">
				    <label for="paparno" class="col-sm-2 control-label">电话</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" name="mobile" id="mobile"  readonly="readonly">
				    </div>
				    </div>
			
					<div class="form-group">
					<label for="loginpwd" class="col-sm-2 control-label">金币</label>
					<div class="col-sm-10"> 
					<input type="text"  value=""  class="form-control" name="golds"  id="gold"  readonly="readonly">
					</div>
					</div>
			
					<div class="form-group">
					<label for="paypwd" class="col-sm-2 control-label">累计充值</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="totalrecharge"  id="totalrecharge"  readonly="readonly">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="wxid" class="col-sm-2 control-label">虚拟资金</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="virtualcapital"  id="virtualcapital"  readonly="readonly">
					</div>
					</div>
				  
					<div class="form-group">
				    <label for="bank" class="col-sm-2 control-label">邮箱</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" name="email"  id="email"  readonly="readonly">
				    </div>
				    </div>
				    
				    <div class="form-group">
					<label for="sesame" class="col-sm-2 control-label">地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="address" id="address"  readonly="readonly">
					</div>
					</div>
					
						
					<div class="form-group">
					<label for="alipayno" class="col-sm-2 control-label">开户行</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bank"  id="bank"  readonly="readonly">
					</div>
					</div>
					
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行账户</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bankaccount"  id="bankaccount"  readonly="readonly">
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行卡号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="cardno"  id="cardno"  readonly="readonly">
					</div>
					</div>

					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">身份证</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="Paparno"  id="Paparno"  readonly="readonly">
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">加入时间</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="createtime"  id="createtime"  readonly="readonly">
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">推广业务员</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="empname"  id="empname"  readonly="readonly">
					</div>
					</div>
					
			</form>
			</div>
  
<script>

$._ajax({
	url: Q.url+"/manageuserinfo/queryuserbyid.action",
	data:{"userid":userid},
	type:"post",
	dataType: "json",
	success: function(user){	
		
		$("#wxid").val(user.wxid);
		$("#nickname").val(user.nickname);
		$("#mobile").val(user.mobile);
		$("#gold").val(user.golds);
		$("#totalrecharge").val(user.totalrecharge);
		$("#virtualcapital").val(user.virtualcapital);
		$("#email").val(user.email);
		$("#address").val(user.address);
		$("#bank").val(user.bank);
		$("#bankaccount").val(user.bankaccount);
		$("#cardno").val(user.cardno); 	
		$("#Paparno").val(user.Paparno); 	
		$("#createtime").val(user.createtime); 	
		$("#empname").val(user.empname); 	
	}
});	

</script>

