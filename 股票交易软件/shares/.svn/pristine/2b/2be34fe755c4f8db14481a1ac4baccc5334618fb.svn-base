<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="col-sm-10" style="width:5%;float:left;margin-bottom: -100px;padding-top: 4px;margin-left:650px;">
				<button class="btn btn-purple btn-sm" id = "outport" onclick="outportexcel()" style="z-index:100">导出</button> <a id="openexcel" href="#"><span id="exporttext"></span></a>
			</div>
<div role="tabpanel" class="tab-pane active" id="tab_cash_state1">
								<!-- jqgrid 用户列表-->
							<table id="cashstate1_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="cashstate1_grid_pager"></div>

</div>

<script>
Q.grid_selector = "cashstate1_grid_table";
Q.pager_selector = "cashstate1_grid_pager";

var url = Q.url+"/money/querycash.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '联系方式', name: 'mobile',align :'center', sortable : false },
                { label: '提现金币', name: 'amount',align :'center', sortable : false },
                { label: '对应额度', name: 'rmbamount',align :'center', sortable : false },
                { label: '审核时间', name: 'time',align :'center',sortable : false },
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	//btn += Q.gridbtn_modify(Q.grid_selector,rows.id) ;
                    	//btn += Q.gridbtn_del(Q.grid_selector,rows.id) ;
                    	/*  btn += Q.gridbtn_change(Q.grid_selector1,rows.id,rows.state) ;  */
                    /* 	btn += Q.gridbtn_pass(Q.grid_selector,rows.id) ;
                    	btn += Q.gridbtn_nopass(Q.grid_selector,rows.id) ; */
                    	btn += Q.gridbtn_confirm(Q.grid_selector,rows.id) ;
                    	
                    	return  btn;
                    }  
                }
            	];

var caption = "提现审核列表";

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
//Q.extJqGrid(Q.grid_selector1,Q.pager_selector1,url,postData,colModel,caption,null,function(d){
	
/*  $("#"+Q.grid_selector).setCaption("总用户："+d.total+"，今日新增："+d.newadd); */
//});

Q.ExtJqGrid({
	"grid":Q.grid_selector,
	"pager":Q.pager_selector,
	"url":url,
	"param":postData,
	"model":colModel,
	"caption":caption,
	'opt':{"height":300} 
})

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition(){
	var o = {};
	o.name = $('#username').val();
	o.mobile = $('#usermobile').val();
	o.state = 1;
	return o;
}


function querycashbycondition(){
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

function outportexcel(){
	/* var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if((starttime==null||starttime.length==0)||(endtime==null||endtime.length==0)){
		Q.toastr("提示","请选择导出时间段",'warning');
		return;
	} */
	$._ajax({
		url: Q.url+"/money/outportcashlog.action",
		data:getcondition(),
		success: function(data){
			if(typeof(data)=='object'){
				if(data.op=='timeout' ){
					location.reload();
					return;
				}
				
				if(data.op=='success'){
					$('#openexcel').attr('href',Q.url+"/excel/"+data.excel);//.css({"display":"block"}).text("点我").click();
					//window.open(Q.url+"/excel/cus_excel.xls");
					$('#exporttext').click();
				}
			}
		}
	})
}

//打款
function confirmcashstate1_grid_table(id){
	var state=2;
	changecashstate(id,state);
}

function changecashstate(id,state){
	$._ajax({
		url:Q.url+"/money/updatecashinfo.action",
		data:{"cashinfoid":id,"state":state},
		success:function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					
					queryorderbycondition();
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