<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">

 .select2{
   background: transparent;
   width: 180px;
   padding:0px;
   font-size: 16px;
   border: 1px solid #ccc;
   height: 34px;
   -webkit-appearance: none;
}
</style>
	<div class="row">
			<table  style="float: left;margin-right: 5px;">
			<tr id="tabletd">
			    

			   
			   <td >
			     <input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="starttime">
			     </td>
			     <td>
					<span style="width:40px height:50px; display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					</td>
					<td>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="endtime">
					</td>
					<td>
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			      
			    </td>
			    
	
	          <td style="width: 60px; text-align: center; ">
	          <button id="addtoyinfobtn" class="btn btn-purple btn-sm" onclick="querycompanybycondition()" data-placement="top" title="查询" data-toggle="modal" data-target="#queryuserinfo">查询
	          </button> 
	         </td> 
			
			</tr>
			<h4 id="today"></h4>
				<h4 id="allamount"></h4>
			</table>
				
	

</div>
                            
									<!-- jqgrid 用户列表-->
							<table id="company_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="company_grid_pager"></div>


				
									
<script>

 
  

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

  Q.grid_selector = "company_grid_table";
  Q.pager_selector = "company_grid_pager";

  var url = Q.url+"/orderinfo/queryCharge.action";

  var colModel = [
                  { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                 
                  { label: '买家', name: 'bnikename',align :'center', sortable : false },
                  { label: '卖家', name: 'mnickname',align :'center', sortable : false },
                  { label: '交易总金额', name: 'cost',align :'center', sortable : false },
                  { label: '交易的数量', name: 'amount',align :'center', sortable : false },
                  /* { label: '交易状态', name: 'state',align :'center', sortable : false }, */
                  { label: '手续费率', name: 'dcharge',align :'center', sortable : false },
                  { label: '手续费', name: 'camount',align :'center', sortable : false },
                  { label: '用户状态', name: 'state1',align :'center', hidden:true, sortable : false },
                 {label: '时间', name: 'time',align :'center',sortable : false },
                   {  
                      name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                      formatter:'actions',  
                      formatter: function (value, grid, rows, state) { 
                      	
                      	var btn = "";
                      	if(rows.state1=="3"){
                      		btn += Q.gridbtn_zhuan(Q.grid_selector,rows.id);
                      	}
                      	return  btn;
                      }  
                  } 
              	];

  var caption = "订单列表";

  var postData = {};

  /**
   * Q.grid_selector  tableid
   * Q.pager_selector 页码栏id
   * url 请求地址
   * postData 请求参数
   * colModel 表头
   * caption 表名 
   */
  Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
   
  /*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
  });

  //Q.pageNavbutton(grid_selector,pager_selector);
  function getcondition(){
  	var o = {};
  	o.starttime=$("#starttime").val();
	o.endtime=$("#endtime").val();
  	return o;
  }
	$._ajax({
		url: Q.url+"/orderinfo/queryToday.action",
		data:{},
		success: function(data){
			if( typeof(data) == 'object'){
				
					$('#today').html("今日手续费:"+data.amount);
					$('#allamount').html(" 历史总手续费:"+data.allamount);
					
					/* //reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); */ 
				
					
			
		}}
	});

  function querycompanybycondition(){
  	postData = getcondition();
  	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
  	//Q.extJqGrid(grid_selector,pager_selector,url,postData,colModel,caption);
  }

  function getSelectedRows() {
      var grid = $(Q.grid_selector);
      var rowKey = grid.getGridParam("selrow");

      if (!rowKey) {
          alert("No rows are selected");
      } else {
          var selectedIDs = grid.getGridParam("selarrrow");
          //var result = "";
          //for (var i = 0; i < selectedIDs.length; i++) {
          //    result += selectedIDs[i] + ",";
          //}
          var result = selectedIDs.join(",");
          alert(result);
      }
  }


  //详情函数----命名规则  detail_tableid
  function detail_company_grid_table(id){
  	var _url= 'usermanager/userdetail.jsp?userid='+id;
  	
  	Q.viewJsp(Q.page_content,_url);
  }

  /* function addcompany(){
  	var _url= 'company/addcompany.jsp';
  	
  	Q.viewJsp('#div_addcompany',_url);
  } */

  function filemanager(){
  	var _url= 'zyUpload/uploadimg.jsp';
  	
  	Q.viewJsp('#div_img',_url);
  }
  /* 禁用解禁函数 */
  function change_company_grid_table(id,stated){
  	$._ajax({
  		url: Q.url+"/company/forbiddencompany.action",
  		data:{"companyid":id,"state":stated},
  		success: function(data){
  			if( typeof(data) == 'object'){
  				if( data.op == 'success' ){
  					Q.toastr("提示","操作成功",'success');
  					//reloadgrid
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

</script>
<script src="js/jqgridresize.js"></script>