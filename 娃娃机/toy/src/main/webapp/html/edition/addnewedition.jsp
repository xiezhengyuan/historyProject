<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
    
   
    
    .button{
     
      background-color:#ccc;
      margin-left:5px;
      border-radius: 8px;
      padding:8px 20px;
      cursor:pointer;
    } 
</style>

<div class="modal  inmodal fade" id="edituser4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
				添加新版本
		        <small>
			    <i class="ace-icon fa fa-angle-double-right"></i>
			        上传
		        </small>
				</h4>
			</div>
			<div class="modal-body"  style="overflow-y: auto;">
			 <form class="form-horizontal" role="form" id="form_edituser">
            <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
            
                    <div class="form-group">
					<label for="loginpwd" class="col-sm-2 control-label">选择app：</label>
					<div id="vid" class="col-sm-10 myclass" style="width: 472px;height:180px;margin-left: 10px;">
						<!-- <a class="upapp" style="text-align: center;line-height:30px;display:block;width:160px;position:relative;">
						       点击选择要上传的app
							<input style="opacity:0;display:inline-block;width:100%;height:100%;position:absolute;top:0;left:0;" type="file" value="请选择要上传的app" name="请选择要上传的app"/>
						</a> -->
						<a href="javascript:;" class="file">选择app(小于1G)
                               <input class="a-upload" type="file" name="filePath" id="appfile">
                        </a>
                        
                        <span class="image"></span>
                        <br/>
                        <span class="fileerrorTip"></span>
                        <span class="showFileName"></span>
                        <span class="size"></span> 
                        <br/>
                       <div style="float: left; margin-top:7px;">
					   <span id="delete" class="button btn btn-danger btn-xs" onclick="deletethis()" >删除</span>
					   </div>
					   <div style="float: left; margin-top:7px;">
					   <span id="upload" class="button btn btn-success btn-xs" onclick="uploadthis()">上传</span>
					   </div>
					    <span id="title" style="font-size:20px;font-style:italic; color: red;"></span>
					</div>
					</div>
                  
            
				    <div class="form-group">
					<label for="name" class="col-sm-2 control-label">版本号：</label>
					<div class="col-sm-10  myclass">
					<input type="text" class="form-control" name="login"  id="editionno" placeholder="请一定要填入正确版本号">
					</div>
					</div>
			
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">下载地址：</label>
					<div class="col-sm-10 myclass">
					<input type="text" class="form-control" name="url"  id="url"  readonly="readonly">
					</div>
					</div>
			
			 		<div class="form-group">
				    <label for="paparno" class="col-sm-2 control-label">更新描述：</label>
				    <div class="col-sm-10 myclass">
				     <textarea id="description" rows="5" cols="73" placeholder="字数不多于200" style=" resize: none;"></textarea>
				    </div>
				    </div>
			
			         
					
					
					
			</form>
			</div>
		<div class="modal-footer">
				
				<button id="hehehe" title="更新版本" class="btn btn-success btn-xs" style="color:#f60;margin-left:3px; width: 60PX; height:35px; " onclick="updateedition()">更新版本</button> 
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#edituser4').modal('show');

var type = '<%=request.getParameter("type")%>';

var filename='';
if(type=='1'){
	filename='apk';
}else{
	filename='ipa';
}

var editionid='';

$(".button").hide();

$(".a-upload").on("change",function(){
    var filePath=$(this).val();
    //var app=$(this).files[0].size
    if(filePath.indexOf(filename)!=-1){
        $(".fileerrorTip").html("").hide();
        var arr=filePath.split('\\');
        var fileName=arr[arr.length-1];
        $(".showFileName").html(fileName);
        $(".image").html('<img  src="image/96.png">');
        //$(".size").html("大小："+app+"Mb")
        $(".button").show();
        $('.file').hide();
    }else{
        $(".showFileName").html("");
        $(".fileerrorTip").html("您未上传文件，或者您上传文件类型有误！").show();
        return false 
    }
})   

function uploadthis(){
	 var form = $("form[id='form_edituser']");
	 var options  = {    
		        url:Q.url + '/edition/updateapp.action',    
		        type:'post',   
		        dataType:'json',
		        beforeSubmit:function(){
	               
		  
	            },
	            beforeSend: function() {
	              
	            },
	            uploadProgress: function(event, position, total, percentComplete) {//上传的过程
	            	$(".button").hide();
	            	$("#title").html("正在上传");
	                
	            },
		        success:function(data){    
		        	if(data.op=='success'){
		        		$("#title").html("成功");
		        		$("#url").val(data.url);
		        		editionid=data.editionid;
		        	}
		        		
		        },
	            error:function(err){//失败
	            	
	            }    
		    };    
		    form.ajaxSubmit(options); 
	 
}




//使用
function updateedition(){
	
	if(editionid==''){
		Q.toastr("提示","未成功上传文件",'error');
		return;
	}
	
	var editionno=$("#editionno").val();
	var description=$("#description").val();
	
	if(editionno==''){
		Q.toastr("提示","请填写正确版本号",'error');
		return;
	}
	if(description.length>200){
		Q.toastr("提示","更新字段描述太长，当前字数"+description.length,'error');
		return;
	}
	Q.confirm("运行该版本将导致其他版本停止使用，确定使用该版本么？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/edition/updateedition.action",
				data:{"editionid":editionid,"type":"1","editionno":editionno,"description":description},
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.op == 'success' ){
							Q.toastr("提示","操作成功",'success');
							postData = getcondition();
							$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
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
	})
	
}



</script>