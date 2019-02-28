<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
  #toly{
     width: 598px;
     height: 420px;
     margin: 0px;
     padding: 0px;
  
  }
  #tou{
     width: 598px;
     height: 60px;
     margin: 0px auto;
  }
  
  #tou div{
     width: 299px;
     height: 60px;
     float: left;
     text-align: center;
   	 border:1px solid #cccccc;	
   	 background-color:rgb(245,245,245);
  }
  #tou div:first-child{
  	border-right:0;	
  }
 
  #zhong{     
     width: 598px;
     height: 440px;
     margin: 0px auto;
     
  }
  .cur{
  		border-top:3px solid red !important;
  		background-color:white !important;
  		border-bottom:0 !important;
  }
  #table td{
     width: 30px;
     height: 20px;
     text-align: center;
  }
   #table input{
     width: 40px;
     height: 30px;
     color:black;
     font-weight:bolder;
     text-align: center;
  }
  #table button{
     width: 30px;
     height: 20px;
     text-align: center;
     font-weight:bolder;
  
  }
  #shangtcyy{
     height:  40px;
     text-align: center;
  }
  #shangtc{
     height:  50px;
     text-align: center;
     
  }
 
#jiabutton {
  display: inline-block;
  padding: 15px 25px;
  font-size: 15px;
  cursor: pointer;
  text-align: center;   
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color:orange;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}

#jiabutton:hover {background-color: #3e8e41}

#jiabutton:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
} 

#jianbutton {
  display: inline-block;
  padding: 15px 25px;
  font-size: 15px;
  cursor: pointer;
  text-align: center;   
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: red;
  border: none;
  border-radius: 15px;
  box-shadow: 0 9px #999;
}

#jianbutton:hover {background-color: #3e8e41}

#jianbutton:active {
  background-color: #3e8e41;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
</style>
<div class="modal  inmodal fade" id="edituser4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="height:  70px">
			
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h3 class="modal-title" id="myModalLabel">
					用户详情
		        <small>
			    <i class="ace-icon fa fa-angle-double-right"></i>
			        修改金币
		        </small>
				</h3>
			</div>
			
		    <div class="modal-footer" id="toly">
				<div id="tou">
				    <div id="addgolds" class="cur"><h3>增加金币</h3></div>
				    <div id="jiangolds"><h3>减少金币</h3></div>
				   
				</div>
				
				<div id="zhong">
				 
				<div id="shangtcyy"></div>
		            <div id="shangtc">
		                
		                       
		            </div>
				    
				  <div id=mytable>  
				     <div style="width: 60px; height:50px; float: left;"></div>
				     <div id="image" style="width: 60px; float: left; margin-top:11px;"><img  src="image/jiahao.jpg" width="50px;" height="50px;"/>
				       
				     </div>
				     
				     <div style="float: left; margin-left: -25px;">
				    <table id="table">
				      <tr>
				         <td></td>
				         <td><button type="button" value="gold1" onclick="jiayi(this.value)" >+</button></td>
				         
				         <td></td>
				         <td><button type="button" value="gold2" onclick="jiayi(this.value)"  >+</button></td>
				         <td></td>
				         <td><button type="button" value="gold3" onclick="jiayi(this.value)"  >+</button></td>
				         <td></td>
				         <td><button type="button" value="gold4" onclick="jiayi(this.value)" >+</button></td>
				         <td></td>
				         <td><button type="button" value="gold5" onclick="jiayi(this.value)"  >+</button></td>
				         <td></td>
				         <td><button type="button"  value="gold6" onclick="jiayi(this.value)" >+</button></td>
				         <td></td>
				        
				      </tr>
				      <tr>
				           <td>
				               <input type="hidden" value="+" id="isupdata"/>   
				           </td>
				           <td><input type="text" value="0" readonly="readonly" id="gold1" class="types"/></td>
				           <td>十万</td>
				           <td><input type="text" value="0" readonly="readonly" id="gold2" class="types"/></td>
				           <td>万</td>
				           <td><input type="text" value="0" readonly="readonly" id="gold3" class="types"/></td>
				           <td>千</td>
				           <td><input type="text" value="0" readonly="readonly" id="gold4" class="types"/></td>
				           <td>百</td>
				           <td><input type="text" value="0" readonly="readonly"  id="gold5" class="types"/></td>
				           <td>十</td>
				           <td><input type="text" value="0" readonly="readonly" id="gold6" class="types"/></td>
				           <td>个</td>
				         
				      </tr>
				      <tr>
				           <td></td>
				         <td><button type="button" value="gold1" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				         <td><button type="button" value="gold2" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				         <td><button type="button" value="gold3" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				         <td><button type="button" value="gold4" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				         <td><button type="button" value="gold5" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				         <td><button type="button" value="gold6" onclick="jianyi(this.value)">－</button></td>
				         <td></td>
				      </tr>
				   </table>
				   
				   </div>
			     </div>
			     
			     <div id="xiatc" style="height: 150px;"></div>	
			     
			     <div id="xuanzhe" style="height: 50px;">
			          <div style="height: 50px; width: 249px; float: left; "> 
			            
			             <button type="button" id="jiabutton">确认</button>
			          </div>
			             <div style="height: 50px; width: 100px;float: left; "></div>
			          <div style="height: 50px; width: 249px;  float: left; text-align: left; ">
			             <button type="button" id="jianbutton" >取消</button>
			          
			          </div>
			          
			        
			     </div>
			</div>
				
				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#edituser4').modal('show');
$('#tou div').click(function(){
	$(this).addClass('cur').siblings().removeClass('cur')
})
function jiayi(id){
	var num=$("#"+id).val();
	num=parseInt(num)+1;
	if(num>9){
		num=0;
	}
	$("#"+id).val(num);
}

function jianyi(id){
	var num=$("#"+id).val();
	num=parseInt(num)-1;
	if(num<0){
		num=9;
	}
	$("#"+id).val(num);
}

$("#addgolds").click(function(){
	$(".types").val("0");
	$("#image").html('<img  src="image/jiahao.jpg" width="50px;" height="50px;"/>');
	$("#shangtc").html('');
	$("#isupdata").val("+");
})
$("#jiangolds").click(function(){
	$(".types").val("0");
	$("#image").html('<img  src="image/jianhao.png" width="50px;" height="50px;"/>');
	$("#shangtc").html('<span style="font-size: 20px;">注意用户余额：</span> <span id="usergolds" style="font-size: 20px;"></span>');
	$("#usergolds").html($("#gold").val());
	$("#isupdata").val("-");
})


$("#jiabutton").click(function(){
    
	var isupdata=$("#isupdata").val();
	var golds="";
	for(i=1;i<=6;i++){
		if($("#gold"+String(i)).val()!='0'){
			for(j=i;j<=6;j++){ 
				golds+=$("#gold"+String(j)).val();  
			}
			
			break;
		}
	}

	if(isupdata=='+'){
		if(golds==''){
			Q.toastr("提示","请输入金额",'warning');
		}else{
			$._ajax({
				url: Q.url+"/manageuserinfo/updatagolds.action",
				data:{"userid":userid,"golds":golds,"isjia":"yes"},
				type:"post",
				dataType: "json",
				success: function(data){	
					if(data.op=="success"){
			    		Q.toastr("提示","以为该用户增加"+golds+"金币",'success');
			    		var oldgolds=Number($("#gold").val());
			    		var newgolds= oldgolds+Number(golds);
			    		$("#gold").val(newgolds);
			    		$("#jianbutton").click();
			    		
			    	}else{
			    		Q.toastr("提示","服务器忙",'warning');
			    	}
				}
			});
		}
	}else{
		if(golds==''){
			Q.toastr("提示","请输入金额",'warning');
		}else{
			var oldgolds=Number($("#gold").val());
			if(oldgolds<Number(golds)){
				Q.toastr("提示","用户余额不足",'warning');
			}else{
				$._ajax({
					url: Q.url+"/manageuserinfo/updatagolds.action",
					data:{"userid":userid,"golds":golds, "isjia":"no"},
					type:"post",
					dataType: "json",
					success: function(data){	
						if(data.op=="success"){
				    		Q.toastr("提示","以为该用户减少"+golds+"金币",'success');
				    		var oldgolds=Number($("#gold").val());
				    		var newgolds= oldgolds-Number(golds);
				    		$("#gold").val(newgolds);
				    		$("#jianbutton").click();
				    		
				    	}else{
				    		Q.toastr("提示","服务器忙",'warning');
				    	}
					}
				});
			}
		}
		
	}
	
	
})



$("#jianbutton").click(function(){
	
	$("#updatagolds").html("");
})

</script>