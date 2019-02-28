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
	              <select id="selectywy2"  class="select2" onchange="queryuserbyywy2()">
	              <option value="0">选择推广业务员</option>
	              </select>
			      </td>

			   <td style="width: 150px;"></td>
			   
			   <td >
			     <input style="width:150px" class="form_date" placeholder="起始日期" type="text" value=""  id="starttime1">
			     </td>
			     <td>
					<span style="width:40px height:50px; display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
					</td>
					<td>
					<input style="width:150px" class="form_date" placeholder="结束日期" type="text" value=""  id="endtime1">
					</td>
					<td>
					<span style="width:40px;display:inline" class="input-group-addon "><span class="glyphicon glyphicon-calendar"></span></span>
			      
			    </td>
			     <td style="width: 160px"> <input id="userinfo1" value="" type="text" placeholder="用户姓名" />
			   
			    </td>
	
	          <td style="width: 60px; text-align: center; ">
	          <button id="addtoyinfobtn" class="btn btn-purple btn-sm" onclick="querybycondition1()" data-placement="top" title="查询" data-toggle="modal" data-target="#queryuserinfo">查询
	          </button> 
	         </td> 
			
			</tr>
			</table>
			
			<button class="btn btn-info btn-sm" style="opacity:0;pointer-events:none" data-toggle="tooltip" data-placement="top" title="查询" >
	<span class="glyphicon glyphicon-search"></span>
	</button >
	<div style="float:right; margin-top:16px;">
	
	<button id="importempbtn" class="btn btn-danger btn-sm" onclick="outportexcel();" data-placement="top" title="导出">
	导出<span class="glyphicon glyphicon-import"></span>
	</button><a id="openexcel" href="#"><span id="exporttext"></span></a>
	
	<!-- <button id="importempbtn" class="btn btn-danger btn-sm" onclick="$('input[id=importemp_excel]').click();" data-placement="top" title="导出">
	导出<span class="glyphicon glyphicon-import"></span>
	</button> -->
	</div>

</div>
                            
								<!-- jqgrid 用户列表-->
							<table id="ti_grid_table1"></table>
							<!-- jqgrid 页码栏-->
							<div id="ti_grid_pager1"></div>

			
<script>


 
function queryuserbyjl2(){
	 $._ajax({
		 
		 url: Q.url+"/companychangeUser/queryywyonly.action",
			type:"post",
			dataType: "json",
			success: function(json){	
				var opts='<option value="0">选择推广业务员</option>';
				for(var i=0;i<json.rows.length;i++){
					opts+='<option value="'+json.rows[i].salesid+'">'+json.rows[i].salesname+'</option>';
				}
				
				$("#selectywy2").html(opts);		
			}
		});
}
 
queryuserbyjl2();
function queryuserbyywy2(){
	 querybycondition1();
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

Q.grid_selector1 = "ti_grid_table1";
Q.pager_selector1 = "ti_grid_pager1";

//var url1 = Q.url+"/reportforms/moneyreportforms.action";
var url1 = Q.url+"/reportforms/goldsreportforms.action";

var colModel1=[];

colModel1=getcolModel1();

function getcolModel1(){
	var mycolModel = [
                { label: '编号', name: 'id',align :'center',sortable : false,key:true ,width:50 },
                { label: whatwho1(), name: 'name',align :'center', sortable : false ,width:150},
                { label: '兑换', name: 's1',align :'center', sortable : false ,width:50},
                { label: '扣除', name: 's2',align :'center', sortable : false ,width:50},
                { label: '打赏', name: 's3',align :'center', sortable : false ,width:50},
                { label: '观摩', name: 's4',align :'center', sortable : false ,width:50},
                { label: '抢购', name: 's5',align :'center', sortable : false ,width:50},
                { label: '提现', name: 's6',align :'center', sortable : false ,width:50},
                { label: '订阅', name: 's7',align :'center', sortable : false ,width:50 },
                { label: '充值', name: 's8',align :'center', sortable : false ,width:50 },
                { label: '被赏', name: 's9',align :'center', sortable : false ,width:50},
                { label: '邀请', name: 's10',align :'center', sortable : false ,width:50},
                { label: '增加', name: 's11',align :'center', sortable : false ,width:50}
            	];
	 
	return mycolModel;
}            	
function  whatwho1(){
	var salesman=$("#selectywy2").val();
	if(salesman == '0'){
		return '业务员';

	}else{
		return '用户';
	}
}          	
var caption1 = "金币报表";

var postData1 ={};
postData1 = getcondition1();
/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id 
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 */
Q.ExtJqGrid({
	"grid":Q.grid_selector1,
	"pager":Q.pager_selector1,
	"url":url1,
	"param":postData1,
	"model":colModel1,
	"caption":caption1
});

//Q.pageNavbutton(grid_selector,pager_selector);
function getcondition1(){
	var o = {};
	o.salesmanid=$("#selectywy2").val();
	o.starttime=$("#starttime1").val();
	o.endtime=$("#endtime1").val();
	o.userinfo=$("#userinfo1").val();
	return o;
}


function querybycondition1(){
	postData1 = getcondition1();
	colModel1=getcolModel1();
	$("#"+Q.grid_selector1).GridUnload();
	Q.ExtJqGrid({
		"grid":Q.grid_selector1,
		"pager":Q.pager_selector1,
		"url":url1,
		"param":postData1,
		"model":colModel1,
		"caption":caption1
	})	 
}


//导出函数----命名规则  del_tableid
function outportexcel(){
	var da={};
	da=getcondition1();
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
					$('#openexcel').attr('href',Q.url+"/excel/"+data.excel);//.css({"display":"block"}).text("点我").click();
					//window.open(Q.url+"/excel/cus_excel.xls");
					$('#exporttext').click();
				}
			}
		}
	})
}


</script>
<script src="js/jqgridresize.js"></script>