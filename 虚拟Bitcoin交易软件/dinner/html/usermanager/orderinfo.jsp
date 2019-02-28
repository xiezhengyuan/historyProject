<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="row">
		<div class="col-xs-12">
			
			<div class="modal-header">
			<label>请选择订单状态：</label>
			<select id="state1"  class="select2"">
  				<!-- <option value="-1" >请选择</option> -->
	         <option value="0" >正在交易订单</option>
			<option value="1" >历史订单</option>
			<option value="2" >交易关闭</option>
			<option value="3" >待转币</option>
	         </select>
	         
		 <label>请选择订单类型：</label>
	    	<select id="type1"  class="select2"">
  				<!-- <option value="-1" >请选择</option> -->
	        <option value="0" >购买</option>
			<option value="1" >出售</option>
	         </select>
			<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querymemberbycondition1()">
			<span class="glyphicon glyphicon-search"></span>
			</button>
	

</div>
								<!-- jqgrid 用户列表-->
							<table id="member_user_grid_table2"></table>
							<!-- jqgrid 页码栏-->
							<div id="member_user_grid_pager2"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 用户详情（Modal） -->
<div id="div_memberdetail" ></div>
			
<!-- 导入（Modal） -->
<!-- <div id="div_import_emp" ></div> -->		
<script>
var userid = '<%=request.getParameter("userid")%>';

Q.grid_selector = "member_user_grid_table2";
Q.pager_selector = "member_user_grid_pager2";

var url = Q.url+"/manageuserinfo/queryOrderinfo.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '买家', name: 'bnikename',align :'center', sortable : false },
                { label: '卖家', name: 'mnickname',align :'center', sortable : false },
                { label: '交易总金额', name: 'cost',align :'center', sortable : false },
                { label: '交易的数量', name: 'amount',align :'center', sortable : false },
                { label: '交易状态', name: 'state',align :'center', sortable : false },
                { label: '订单状态', name: 'type',align :'center', sortable : false },
               {label: '时间', name: 'time',align :'center',sortable : false }
                 /* {   
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                     	btn += Q.gridbtn_roledetail(Q.grid_selector,rows.id,rows.roles) ;
                    	btn += Q.gridbtn_change(Q.grid_selector,rows.id,rows.state) ;  
                    	return  btn;
                    }  
                }  */
            	];

var caption = "订单信息";

var postData = {"userid":userid/* ,"state":$('#state1').val(),"type":$('#type1').val() */};

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.extJqGrid(Q.grid_selector,Q.pager_selector,url,postData,colModel,caption,null,function(d){
	 $('#totalall').html(d.totalrole4+d.totalrole5);
	 $('#totalrole4').html(d.totalrole4);
	 $('#totalrole5').html(d.totalrole5);
	});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.userid = userid;
	o.state=$('#state1').val();
	o.type=$('#type1').val();
	return o;
}


function querymemberbycondition1(){
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
function roledetail_member_user_grid_table(id,roles){
	var _url = "";
	if(roles==4){
	 _url= 'company/managerdetail.jsp?accountinfoid='+id+'&companyid='+companyid;
	 }
	if(roles==5){
		 _url= 'company/salesmandetail.jsp?accountinfoid='+id+'&companyid='+companyid;
		 }
	
	Q.viewJsp(Q.page_content,_url);
}

function change_member_user_grid_table(id,stated){
	$._ajax({
		url: Q.url+"/company/forbiddenmember.action",
		data:{"accountinfoid":id,"state":stated},
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
