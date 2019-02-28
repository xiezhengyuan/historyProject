<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addorder1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" style="width: 800px">
		<div class="modal-content"  >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					物流信息
				</h4>
			</div>
			<div class="modal-body" >
					<!-- <label for="buyer" class="col-sm-2 control-label">物流信息</label> -->
					<table class="table table-hover">
					<thead>
				     <tr>
					<th>物流时间</th>
					<th>物流详情</th>
					</tr>
					</thead>
					<tbody  id= "tbody">
			        </tbody>
					</table>
					
			
					
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div><!-- /.modal-content -->
		
	</div><!-- /.modal -->
</div>

<script>
var expressageinfoid = '<%=request.getParameter("expressageinfoid")%>';
queryexpressagedetail();

$('#addorder1').modal('show');


function queryexpressagedetail(){
	$._ajax({
		url: Q.url+"/device/queryexpressagedetail.action",
		data:{"expressageinfoid":expressageinfoid},
		success: function(data){
		
			var tr = '';
			for(var c = 0;c <data.Traces.length;c++){
				var Traces = data.Traces[c];
				 
				tr+='<tr xc="len">'+
			    '<td >'+Traces.AcceptTime+'</td><td>'+Traces.AcceptStation+'</td>';
			}
			$('#tbody').html(tr);
				
			
			/* $('#expressage').val(data); */
			
		}
		
	});
	
}




</script>