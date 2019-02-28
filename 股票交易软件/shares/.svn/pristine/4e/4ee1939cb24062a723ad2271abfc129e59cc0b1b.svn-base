<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="div" style="height:45px">
	<div class="row">
			<table  style="float: left;margin-right: 5px;">
			<tr id="tabletd">
			   

			   <td style="width: 150px;"></td>
			   
			   <td >
			     <input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="gstarttime">
			     </td>
			     <td>
					<span style="width:40px height:50px; display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					</td>
					<td>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="gendtime">
					</td>
					<td>
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			      
			    </td>
			     <td style="width: 160px"> <input id="querybyuserinfo" value="" type="text" placeholder="用户姓名" />
			   
			    </td>
	
	          <td style="width: 60px; text-align: center; ">
	          <button id="addtoyinfobtn" class="btn btn-purple btn-sm" onclick="querybycondition()" data-placement="top" title="查询" data-toggle="modal" data-target="#queryuserinfo">查询
	          </button> 
	         </td> 
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" style="opacity:0;pointer-events:none" data-toggle="tooltip" data-placement="top" title="查询" >
	<span class="glyphicon glyphicon-search"></span>
	</button >
	

</div>
        
		
</div>
  
  		<!-- jqgrid 用户列表-->
							<table id="order1_grid_table"></table>
							<!-- jqgrid 页码栏-->
						<div id="order1_grid_pager"></div>
							<div id="agentxiangqing"></div>	
													<div>

		
	
</div>

<script>


/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order1_grid_table";
Q.pager_selector = "order1_grid_pager";

var url = Q.url+"/managercontrol/queryRech.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
              
               { label: '充值金额', name: 'amount',align :'center', sortable : false }, 
               { label: '发帖人', name: 'name',align :'center', sortable : false }, 
               { label: '充值时间', name: 'createtime',align :'center', sortable : false },
             
            	];

var caption = "充值明细";

var postData = {};
postData = getcondition();
function queryempbycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	
}
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
	
Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.gstarttime= $('#gstarttime').val(); 
	o.gendtime=$('#gendtime').val();
	o.querybyuserinfo=$('#querybyuserinfo').val();
	return o;
}


function queryorderbycondition(){
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


function querybycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
}
$(".form_date").datetimepicker({
	language:  'zh-CN',
    format: 'yyyy-MM-dd',
    autoclose: true,
    todayBtn: false,
    pickerPosition: "bottom-left",
    weekStart: 0,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView :2,
	forceParse: 0
}); 

</script>

