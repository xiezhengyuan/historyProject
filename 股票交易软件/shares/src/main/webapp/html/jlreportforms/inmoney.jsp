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
			     <td style="width: 30px;"></td>
	              <td>
	              <select id="selectywy1"  class="select2" onchange="queryuserbyywy1()">
	              <option value="0">选择推广业务员</option>
	              </select>
			      </td>

			   <td style="width: 350px;"></td>
			   
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
			     <td style="width: 160px"> <input id="userinfo" value="" type="text" placeholder="用户姓名" />
			   
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
	<div style="float:right; margin-top:16px; ">
	
	<button id="importempbtn" class="btn btn-danger btn-sm" onclick="outportexcel1()" data-placement="top" title="导出">
	导出<span class="glyphicon glyphicon-import"></span>
	</button><a id="openexcel1" href="#"><span id="exporttext1"></span></a>
	</div>

</div>
                            
								<!-- jqgrid 用户列表-->
							<table id="ti_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="ti_grid_pager"></div>

				
									
<script>

 
  function queryuserbyjl1(){
	 $._ajax({
		 
		 url: Q.url+"/companychangeUser/queryywyonly.action",
			type:"post",
			dataType: "json",
			success: function(json){	
				var opts='<option value="0">选择推广业务员</option>';
				for(var i=0;i<json.rows.length;i++){
					opts+='<option value="'+json.rows[i].salesid+'">'+json.rows[i].salesname+'</option>';
				}
				
				$("#selectywy1").html(opts);		
			}
		});
 }
  
 queryuserbyjl1();
 function queryuserbyywy1(){
	 querybycondition();
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


Q.grid_selector = "ti_grid_table";
Q.pager_selector = "ti_grid_pager";

var url = Q.url+"/reportforms/moneyreportforms.action";

var colModel=[];

colModel=getcolModel();

function getcolModel(){
	var mycolModel = [
                { label: '编号', name: 'id',align :'center',sortable : false,key:true },
                { label: whatwho(), name: 'whatwho',align :'center', sortable : false },
                { label: '入金总额', name: 'totalmoney',align :'center', sortable : false }
            	];
	
	return mycolModel;
}            	
function  whatwho(){
	var salesman=$("#selectywy1").val();
	var userinfo=$("#userinfo").val();
   if(userinfo != ''){
	  	return '用户';
	}else if(salesman == '0'){
		return '业务员';
	}else{
		return '用户';
	}
}          	
var caption = "入金报表";

var postData ={};
postData = getcondition();
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id 
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
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
	o.salesmanid=$("#selectywy1").val();
	o.starttime=$("#starttime").val();
	o.endtime=$("#endtime").val();
	o.userinfo=$("#userinfo").val();
	return o;
}


function querybycondition(){
	postData = getcondition();
	colModel=getcolModel();
	$("#"+Q.grid_selector).GridUnload();
	Q.ExtJqGrid({
		"grid":Q.grid_selector,
		"pager":Q.pager_selector,
		"url":url,
		"param":postData,
		"model":colModel,
		"caption":caption
	})	 
}

//详情函数----命名规则  detail_tableid
//查看详情
function detail_ti_grid_table(id){
	var _url= 'usermanage/userinfodetil.jsp?userid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

function outportexcel1(){
	var da={};
	da=getcondition();
	$._ajax({
		url: Q.url+"/reportforms/outportgoldslog.action",
		data:da,
		success: function(data){
			if(typeof(data)=='object'){
				if(data.op=='timeout' ){
					location.reload();
					return;
				}
				if(data.op=='success'){
					$('#openexcel1').attr('href',Q.url+"/excel/"+data.excel);//.css({"display":"block"}).text("点我").click();
					//window.open(Q.url+"/excel/cus_excel.xls");
					$('#exporttext1').click();
				}
			}
		}
	})
}



</script>
<script src="js/jqgridresize.js"></script>