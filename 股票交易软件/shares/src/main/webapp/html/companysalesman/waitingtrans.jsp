<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <style type="text/css">
   
   #waitsel{
        width: 49.2%; 
        height: auto;
        float: left;
  
     
   }
   
   #aftersel{
       width: 49.2%; 
       height: auto;
    
       float: left;
     
     
   }
 #zoumm{
 	    width: 0.8%; 
        height: 600px;
        border-right: 2px solid black; 
        float: left;
 
 }
  
   #youmm{
 	    width: 0.8%; 
         height: auto;
       height: 600px;
        float: left;
 }
 
  #selectcompany {
   background: transparent;
   width: 180px;
   padding:0px;
   font-size: 16px;
   border: 1px solid #ccc;
   height: 34px;
   -webkit-appearance: none;
 
}

 .select2{
   background: transparent;
   width: 180px;
   padding:0px;
   font-size: 16px;
   border: 1px solid #ccc;
   height: 34px;
   -webkit-appearance: none;
}

#tochange {
    background-color: #00bfff; /* Green */
    border: none;
    color: white;
    height:40px;
    margin-left:71.6%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
}

#promisstochange{
    background-color: #00bfff; /* Green */
    border: none;
    color: white;
    height:40px;
    margin-left:74%;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
}


#tochange:hover {
    box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
 
 </style>

       
       <div id="waitsel">
       
         <h4>被选业务员： </h4>
       
	     <br/>
	         <select id="selectjl"  class="select2" onchange="queryuserbyjl()">
	         <option value="0">选择推广经理</option>
	         </select>
	    <br/>
	    <br/>
        <button type="button" id="tochange" onclick="getSelectedRows()">选入预转移列表》》》</button>
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table1"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager1"></div>
         
       </div>
       <div id="zoumm"></div>
       <div id="youmm"></div>
       <div id="aftersel">
             <h4>指定经理： </h4>
      
	         <br/>
	         <select id="selectjl1"  class="select2" onchange="queryuserbyjl1()">
	         <option value="0">选择推广经理</option>
	         </select>
	        <br/>
	        <br/>
           <button type="button" id="promisstochange" onclick="confirmtochange()">》确认转入该经理《</button>
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table2"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager2"></div>
         
       </div> 
       
       
      
<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

 function queryjl(){
	$._ajax({
		url: Q.url+"/companychangeUser/queryjl.action",
		type:"post",
		dataType: "json",
		success: function(json){	
			var opts='<option value="0">选择推广经理</option>';
			for(var i=0;i<json.rows.length;i++){
				opts+='<option value="'+json.rows[i].gengenlid+'">'+json.rows[i].gengenlname+'</option>';
			}
			
			$("#selectjl").html(opts);
			$("#selectjl1").html(opts);
				
		}
	});	
}
 
 queryjl();
 
 function  queryuserbyjl(){
	 querysalesmanbycon();
 }
 
 function table1(){
	 Q.grid_selector1 = "order_grid_table1";
	 Q.pager_selector1= "order_grid_pager1";
	 var postData1 = {};
	 var url1 = Q.url+"/salesmanchange/querysalesman.action";

	 var colModel1 = [
	                 { label: 'ID', name: 'salesmanid',align :'center',hidden:true,sortable : false,key:true },
	                 { label: '姓名', name: 'salesmanname',align :'center', sortable : false },
	                 { label: '手机号', name: 'salesmanmobile',align :'center', sortable : false },
	                 { label: '所属经理', name: 'jlname',align :'center', sortable : false,},
	                 { label: '所属公司', name: 'company',align :'center', sortable : false,},
	                 { label: '所属经理id', name: 'jlid',align :'center', sortable : false,hidden:true},
	                 { label: '所属公司id', name: 'companyid',align :'center', sortable : false,hidden:true}
	                 ];

	 var caption1 = "待选列表";


	 postData1 = getcondition1();
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
	 	"grid":Q.grid_selector1,
	 	"pager":Q.pager_selector1,
	 	"url":url1,
	 	"param":postData1,
	 	"model":colModel1,
	 	"caption":caption1,
	 	"opt":{"multiselect":true,"rownumbers":false,"height":350}
	 });
 }

 table1();
 
 function querysalesmanbycon(){
		postData1 = getcondition1();
		$("#"+Q.grid_selector1).setGridParam({postData:postData1,page:1}).trigger("reloadGrid"); 
	}
 
 
 function getcondition1(){
		var o = {};
		o.jlid=$("#selectjl").val();
		return o;
}
 //////////////////////////////


function table2(){
	 Q.grid_selector2 = "order_grid_table2";
	 Q.pager_selector2= "order_grid_pager2";
	 var postData2 = {};
	 var url2 = Q.url+"/ChnUser/querserbcomy.action";

	 var colModel2 = [
					{ label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
					{ label: '姓名', name: 'salesmanname',align :'center', sortable : false },
					{ label: '手机号', name: 'salesmanmobile',align :'center', sortable : false },
					{ label: '所属经理', name: 'jlname',align :'center', sortable : false,},
					{ label: '所属公司', name: 'company',align :'center', sortable : false,},
					{ label: '所属经理id', name: 'jlid',align :'center', sortable : false,hidden:true},
					{ label: '所属公司id', name: 'companyid',align :'center', sortable : false,hidden:true},
	                 {  
	                     name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
	                     //formatter:'actions',  
	                     formatter: function (value, grid, rows, state) { 
	                     	
	                     	var btn = "";
	                     	btn += Q.gridbtn_del(Q.grid_selector2,rows.id) ;
	                     	return  btn;
	                     }  
	                 }
	             	];

	 var caption2 = "预转移列表";


	 postData2 = getcondition1();
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
	 	"grid":Q.grid_selector2,
	 	//"pager":Q.pager_selector2,
	 	"url":null,
	 	"param":postData2,
	 	"model":colModel2,
	 	"caption":caption2,
	 	"data":[],
	 	"datatype":"local",
	 	"opt":{"height":400,"rowNum":10000000}
	 });
	 	
	
}

table2();	

//得到选择的用户

var mydata=[];

function getSelectedRows() {
    var grid = $("#"+Q.grid_selector1);
    var rowKey = grid.getGridParam("selrow");

    if (!rowKey) {
    	Q.toastr("提示","请选择用户",'error');
    } else {
    	var aginuser="";
        var selectedIDs = grid.getGridParam("selarrrow");
        for (var i = 0; i < selectedIDs.length; i++) {
        	var boolen=true;
        	for(var j=0;j<mydata.length;j++){
        		if(selectedIDs[i]==mydata[j].id){
        			aginuser+=grid.jqGrid("getRowData",selectedIDs[i]).salesmanname+",";
        			boolen=false;
        			break;
        		}
        		
        	}
        	if(boolen){
        	 var rows={};
           	 var rowData = grid.jqGrid("getRowData",selectedIDs[i]);
           	 rows.id=selectedIDs[i];
           	 rows.salesmanname=rowData.salesmanname;
           	 rows.salesmanmobile=rowData.salesmanmobile;
           	 rows.jlname=rowData.jlname;
           	 rows.company=rowData.company;
           	 rows.jlid=rowData.jlid;
           	 rows.companyid=rowData.companyid;
           	 mydata.push(rows);	
        	}
        	         		 	        		 
        }
        $("#"+Q.grid_selector2).setGridParam({data:mydata,datatype:"local"}).trigger("reloadGrid"); 
        if(aginuser!=""){
        	 Q.toastr("提示","业务员"+aginuser+"已存在请不要重复添加",'error');
        }
       
    }
}

//删除已选择的用户
function del_order_grid_table2(id){
	for(var i=0;i<mydata.length;i++){
		if(id==mydata[i].id){
			mydata.splice(i, 1);
		    break;
		}
	}
	$("#"+Q.grid_selector2).jqGrid("clearGridData"); 
	$("#"+Q.grid_selector2).setGridParam({data:mydata,datatype:"local"}).trigger("reloadGrid"); 
}

//确认
function confirmtochange(){
	var jlid=$("#selectjl1").val();
	if(jlid=='0'){
		Q.toastr("提示","请选择经理",'warning');
	}else{
		var salesmans='';
		for(var i=0;i<mydata.length;i++){
			if(i==mydata.length-1){
				salesmans+=mydata[i].id+'-'+mydata[i].jlid+'-'+mydata[i].companyid
			}else{
				salesmans+=mydata[i].id+'-'+mydata[i].jlid+'-'+mydata[i].companyid+',';
			}
		}
		if(salesmans==''){
			Q.toastr("提示","您还没有选择任何用户",'warning');
		}else{
			Q.confirm("将转移"+mydata.length+"条用户到"+$("#selectyl1 option:selected").text()+"经理名下，确定吗？",function(r){
				if(r){
					$._ajax({
						url:Q.url+"/salesmanchange/confirmchange.action",
						data:{"salesmans":salesmans,"injlid":jlid },
						type:'post',
					    success:function(data){
					    	if(data.op=="success"){
					    		Q.toastr("提示","转移成功",'success');
					    		querysalesmanbycon();
					    		mydata=[];
					    		$("#"+Q.grid_selector2).jqGrid("clearGridData"); 
					    		$("#"+Q.grid_selector2).setGridParam({data:mydata,datatype:"local"}).trigger("reloadGrid"); 
					    	}else{
					    		Q.toastr("提示","服务器忙",'warning');
					    	}
					    }
					})
				}
			})
		}
	}
}



</script>

