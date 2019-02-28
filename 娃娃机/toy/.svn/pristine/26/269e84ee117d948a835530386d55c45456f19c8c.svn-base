<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

   <div>
       <table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="username" class="control-label" style="margin-left:5px;">姓名：</label></td><td><input type="text" id="username" /></td>
			<td><label for="usermobile" class="control-label" style="margin-left:5px;">手机：</label></td><td><input type="text" id="usermobile" /></td>
			<td style="width: 10px;"></td>
			<td> 
			 <select id="appealtype" style="width: 200px; height: 35px; " onchange="queryuserappeal()">
			     <option value="-1">请选择申诉类型</option>
			 </select> 
			
			 </td>
			</tr>
			
			
			</table>
			
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryuserappeal()">
	<span class="glyphicon glyphicon-search"></span>
	</button>
   
   </div>
  		<!-- jqgrid 用户列表-->
							<table id="order1_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="order1_grid_pager"></div>
							<div id="newappealdetail"></div>	
<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */


$._ajax({
		url: Q.url+"/UserAppeal/querysystemappeal.action",
		data:{"rows":100},
		success: function(data){
			if( typeof(data) == 'object'){
				var values= " <option value='-1'>请选择申诉类型</option> "
	            for(var i=0;i<data.rows.length;i++){
	            	values+=" <option value='"+data.rows[i].id+"'>"+data.rows[i].content+"</option> ";
	            } 
				values+="<option value='0'>其他类型</option>";
				$("#appealtype").html(values);
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	});	


Q.grid_selector = "order1_grid_table";
Q.pager_selector = "order1_grid_pager";

var url = Q.url+"/UserAppeal/queryuserappeal.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '用户昵称', name: 'nickname',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '申诉理由', name: 'substance',align :'center', sortable : false },
                { label: '申诉时间', name: 'createtime',align :'center', sortable : false }, 
                { label: '申诉娃娃', name: 'toyname',align :'center', sortable : false }, 
                { label: '娃娃图片', name: 'toyphoto',align :'center', sortable : false,
                	formatter: function (value) { 
                		return '<img src = "'+Q.url+value+'" width="30%">';
                	}	
                
                }, 
                { label: '抓取成败', name: 'issuccess',align :'center', sortable : false },
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:100, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn +='<button title=\"查看详情\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"modify_order1_grid_table('+rows.id+')\"><span class=\"glyphicon glyphicon-list-alt\" aria-hidden=\"true\"></span></button>' ;
                    	btn += '<button title=\"删除\" class=\"btn btn-danger btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"del_order1_grid_table('+rows.id+')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>' ;
                    	return  btn;
                    }  
                }
            	];

var caption = "未处理申请列表";

var postData = {};
postData = getcondition();
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
/* Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	}); */
	

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.username= $('#username').val();
	o.moblie= $('#usermobile').val();
	o.appealtype=$('#appealtype').val();
	o.neworold="new";
	return o;
}

Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption
});


function queryuserappeal(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	Q.ExtJqGrid({
		"grid":Q.grid_selector,
		"pager":Q.pager_selector,
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	});
}

//查看详情
function modify_order1_grid_table(dataid){
	$("#newappealdetail").empty();
	$("#oldappealdetail").empty();
	 var _url= "complain/appealdetail.jsp?appealid="+dataid+"&type=1";
	 Q.viewJsp("#newappealdetail",_url);
}

//删除
function del_order1_grid_table(id){
	
	Q.confirm("确定删除么？",function(r){
		if(r){
	$._ajax({
		url: Q.url+"/UserAppeal/deleteuserappeal.action",
		data:{"id":id,"type":"-1"},
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

