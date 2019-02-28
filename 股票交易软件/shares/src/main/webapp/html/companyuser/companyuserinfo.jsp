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
<div class="page-header">
	<h1>
		用户管理
		<small>
	        <i class="ace-icon fa fa-angle-double-right"></i>
			列表 
			 
		</small>
		
	</h1>
</div><!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<h4>&nbsp;&nbsp;&nbsp;&nbsp;总用户：<span id="olduser"></span>&nbsp;&nbsp;&nbsp;&nbsp;今日新增：<span id="todayuser"></span></h4> 
			<div class="modal-header">
			<table  style="float: left;margin-right: 5px;">
			<tr id="tabletd">
			     <td style="width: 30px;"></td>
	              <td>
	              <select id="selectjl1"  class="select2" onchange="queryuserbyjl1()">
	              <option value="0">选择推广经理</option>
	              </select>
	              </td>
	              <td>
	              <select id="selectywy1"  class="select2" onchange="queryuserbyywy1()">
	              <option value="0">选择推广业务员</option>
	              </select>
			      </td>
			   
			    <td style="width: 160px"> <input id="querybyuserinfo" value="" type="text" placeholder="用户姓名/手机号" />
			   
			   </td>
			   <td style="width: 60px">
			      <select style=" height: 33px; " id="selecttype" onchange="changetype()">
			          <option value="1">正常用户</option>
			          <option value="2">禁用用户</option>
			      </select>
			      
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
	<div style="float:right;">
	
	<!-- <button id="importempbtn" class="btn btn-danger btn-sm" onclick="$('input[id=importemp_excel]').click();" data-placement="top" title="导入">
	导入<span class="glyphicon glyphicon-import"></span>
	</button>
	<a id="personnelmodelclick" target="_blank" href="mould/emp_model.xls" style="padding-left:5px;">没有模板?</a>
	
	<form id="upload_emp_file_form" name="upload_emp_file_form" method="POST"  enctype="multipart/form-data"> 
	<input  id="importemp_excel" type="file" name="filePath" onchange="uploademp_excel()" style="display:none">
	</form> -->
	</div>

</div>
								<!-- jqgrid 用户列表-->
							<table id="ti_grid_table"></table>
							<!-- jqgrid 页码栏-->
							<div id="ti_grid_pager"></div>

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
					
<!-- 新增人员（Modal） -->
<div id="div_addtoy" ></div>
			
<!-- 导入（Modal） -->
<div id="div_import_emp" ></div>		
<script>

function queryuserbycompany1(){
	 $._ajax({
		    url: Q.url+"/companychangeUser/queryjl.action",
			type:"post",
			dataType: "json",
			success: function(jingli){	
				var opts='<option value="0">选择推广经理</option>';
				for(var i=0;i<jingli.rows.length;i++){
					opts+='<option value="'+jingli.rows[i].gengenlid+'">'+jingli.rows[i].gengenlname+'</option>';
				}
				
				$("#selectjl1").html(opts);
				$("#selectywy1").html('<option value="0">选择推广业务员</option>');	
				querybycondition();
			}
		});
}

queryuserbycompany1();

 function queryuserbyjl1(){
	 $._ajax({
		    url: Q.url+"/companychangeUser/queryywy.action",
			data:{"jlid":$("#selectjl1").val()},
			type:"post",
			dataType: "json",
			success: function(yewuyuan){	
				var opts='<option value="0">选择推广业务员</option>';
				for(i=0;i<yewuyuan.rows.length;i++){
					opts+='<option value="'+yewuyuan.rows[i].salesid+'">'+yewuyuan.rows[i].salesname+'</option>';
				}
				
				$("#selectywy1").html(opts);
				querybycondition();
					
			}
		});
}
 
function queryuserbyywy1(){
	 querybycondition();
} 

$._ajax({
	url:Q.url+"/companyuser/comqueryquerynewlod.action",
	dataType:'json',
	type:'post',
	success:function(data){
		$('#olduser').html(data.olduser);
		$('#todayuser').html(data.todayuser);
	}
})

Q.grid_selector = "ti_grid_table";
Q.pager_selector = "ti_grid_pager";

var url = Q.url+"/manageuserinfo/ptqueryuserinfo.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '昵称', name: 'nickname',align :'center', sortable : false },
                { label: '姓名', name: 'name',align :'center', sortable : false },
                { label: '手机号', name: 'mobile',align :'center', sortable : false },
                { label: '累计充值', name: 'totalrecharge',align :'center', sortable : false,width:80 },
                { label: '金币', name: 'golds',align :'center', sortable : false,width:80 },
                { label: '虚拟资金', name: 'virtualcapital',align :'center',sortable : false,width:80 },
                { label: '注册时间', name: 'createtime',align :'center',sortable : false },
                { label: '所属经理', name: 'gengerl',align :'center',sortable : false ,width:80},
                { label: '所属业务员', name: 'salesman',align :'center',sortable : false,width:80 },
                { label: '用户状态', name: 'state',align :'center', hidden:true, sortable : false },
                
                {  
                    name: '操作' ,align :'center', fixed: true, sortable: false, resize: false,  
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	var btn = "";
                    	btn = Q.gridbtn_detail(Q.grid_selector,rows.id);
                    	return  btn;
                    }  
                }
            	];

var caption = "用户列表";

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
	o.company = $('#selectcompany1').val();
	o.jl = $('#selectjl1').val();
	o.ywy = $('#selectywy1').val();
	o.querybyuserinfo = $('#querybyuserinfo').val();
	o.selecttype=$("#selecttype").val();
	return o;
}


function querybycondition(){
	postData = getcondition();
	$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
	 
}

//详情函数----命名规则  detail_tableid
//查看详情
function detail_ti_grid_table(id){
	var _url= 'companyuser/userinfodetil.jsp?userid='+id;
	
	Q.viewJsp(Q.page_content,_url);
}

</script>
<script src="js/jqgridresize.js"></script>