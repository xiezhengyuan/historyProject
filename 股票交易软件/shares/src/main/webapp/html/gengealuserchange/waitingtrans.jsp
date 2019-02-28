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
       
	         <select id="selectywy1"  class="select2" onchange="queryuserbysel()" >
	         <option value="0">选择推广业务员</option>
	         </select>
	        
	          <input type="text" id="accname" placeholder="业务员姓名" />
             <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryuserbybot()"  >
	         <span class="glyphicon glyphicon-search"></span>
	         </button>
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
             <h4>指定业务员： </h4>
	         <select id="selectywy2"  class="select2" >
	         <option value="0">选择推广业务员</option>
	         </select>
	        
	 
	        <br/>
	        <br/>
           <button type="button" id="promisstochange" onclick="confirmtochange()">》确认转入业务员《</button>
  		<!-- jqgrid 用户列表-->
							<table id="order_grid_table2"></table>
							<!-- jqgrid 页码栏-->
							<div id="order_grid_pager2"></div>
         
       </div> 
       
       
      
<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */

 function queryywy(){
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
			$("#selectywy2").html(opts);
				
		}
	});	
}

 queryywy();

 function table1(){
	 Q.grid_selector1 = "order_grid_table1";
	 Q.pager_selector1= "order_grid_pager1";
	 var postData1 = {};
	 var url1 = Q.url+"/companychangeUser/queryuser.action";

	 var colModel1 = [
	                 { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	                 { label: '昵称', name: 'nickname',align :'center', sortable : false },
	                 { label: '手机号', name: 'mobile',align :'center', sortable : false },
	                 { label: '所属经理', name: 'jlname',align :'center', sortable : false,},
	                 { label: '所属业务员', name: 'ywyname',align :'center', sortable : false,},
	                 { label: '所属公司id', name: 'companyid',align :'center', sortable : false,hidden:true},
	                 { label: '所属业务员id', name: 'faccountid',align :'center', sortable : false,hidden:true}
	             	];

	 var caption1 = "待选列表";
	 	
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

	
function getconditionsel(){
	var o = {};
	o.salesid=$("#selectywy1").val();
	o.type='1';
	return o;
}
function getconditionbot(){
	var o = {};
	o.accname=$("#accname").val();
	o.type='4';
	return o;
}

table1();


function queryuserbysel(){
	postData1 = getconditionsel();
	$("#"+Q.grid_selector1).setGridParam({postData:postData1,page:1}).trigger("reloadGrid"); 
}
function queryuserbybot(){
	postData2 = getconditionbot();
	$("#"+Q.grid_selector1).setGridParam({postData:postData2,page:1}).trigger("reloadGrid"); 
}




function table2(){
	 Q.grid_selector2 = "order_grid_table2";
	 Q.pager_selector2= "order_grid_pager2";
	 var postData2 = {};
	 var url2 = Q.url+"/ChnUser/querserbcomy.action";

	 var colModel2 = [
	                 { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
	                 { label: '昵称', name: 'nickname',align :'center', sortable : false },
	                 { label: '手机号', name: 'mobile',align :'center', sortable : false },
	                 { label: '所属经理', name: 'jlname',align :'center', sortable : false,},
	                 { label: '所属业务员', name: 'ywyname',align :'center', sortable : false,},
	                 { label: '所属公司id', name: 'companyid',align :'center', sortable : false,hidden:true},
	                 { label: '所属业务员id', name: 'faccountid',align :'center', sortable : false,hidden:true},
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
        			aginuser+=grid.jqGrid("getRowData",selectedIDs[i]).nickname+",";
        			boolen=false;
        			break;
        		}
        		
        	}
        	if(boolen){
        	 var rows={};
           	 var rowData = grid.jqGrid("getRowData",selectedIDs[i]);
           	 rows.id=selectedIDs[i];
           	 rows.nickname=rowData.nickname;
           	 rows.mobile=rowData.mobile;
           	 rows.ywyname=rowData.ywyname;
           	 rows.jlname=rowData.jlname;
           	 rows.companyid=rowData.companyid;
           	 rows.faccountid=rowData.faccountid;
           	 mydata.push(rows);	
        	}
        	         		 	        		 
        }
        $("#"+Q.grid_selector2).setGridParam({data:mydata,datatype:"local"}).trigger("reloadGrid"); 
        if(aginuser!=""){
        	 Q.toastr("提示","用户"+aginuser+"已存在请不要重复添加",'error');
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
	var ywyid=$("#selectywy2").val();
	if(ywyid=='0'){
		Q.toastr("提示","请选择业务员",'warning');
	}else{
		var users='';
		for(var i=0;i<mydata.length;i++){
			if(i==mydata.length-1){
				users+=mydata[i].id+'-'+mydata[i].companyid+'-'+mydata[i].faccountid
			}else{
				users+=mydata[i].id+'-'+mydata[i].companyid+'-'+mydata[i].faccountid+',';
			}
		}
		if(users==''){
			Q.toastr("提示","您还没有选择任何用户",'warning');
		}else{
			Q.confirm("将转移"+mydata.length+"条用户到"+$("#selectywy2 option:selected").text()+"业务员名下，确定吗？",function(r){
				if(r){
					$._ajax({
						url:Q.url+"/companychangeUser/confirmtochange.action",
						data:{"users":users,"ywyid":ywyid},
						type:'post',
					    success:function(data){
					    	if(data.op=="success"){
					    		Q.toastr("提示","转移成功",'success');
					    		$("#"+Q.grid_selector1).jqGrid("clearGridData"); 
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

