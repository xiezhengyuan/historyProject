<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="div" style="height:45px">
<table  style="float: left;margin-right: 5px;">
			<tr>
			<td><label for="fsupplier" class="control-label"  style="margin-left:5px;">帖子分类：</label></td><td><select type="text" id="fsupplier" ><option value="-1"  selected>请选择</option></select></td>
			
			</tr>
			</table>
		
</div>
  
  		<!-- jqgrid 用户列表-->
							<table id="order1_grid_table"></table>
							<!-- jqgrid 页码栏-->
						<div id="order1_grid_pager"></div>
							<div id="agentxiangqing"></div>	
													<div>

		
	
</div>

<script>
querysupplier("#fsupplier");
function querysupplier(selectedid){
	$._ajax({
		url: Q.url+"/postings/queryPostingStyle.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var postingstyle = data.rows[i].postingStyle;
				opts+= '<option value="'+postingstyle.id+'">'+postingstyle.name+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}

$('#fsupplier').change(function(){
	
	queryempbycondition();
})
/* Q.getProxylevel("#proxylevel"); */

Q.grid_selector = "order1_grid_table";
Q.pager_selector = "order1_grid_pager";

var url = Q.url+"/postings/queryTopic.action";

var colModel = [
                { label: 'ID', name: 'id',align :'center',hidden:true,sortable : false,key:true },
                { label: '标题', name: 'postingstitle',align :'center', sortable : false },
                { label: '内容', name: 'postingscontent',align :'center', sortable : false },
              
               { label: '打赏金币', name: 'money',align :'center', sortable : false }, 
               { label: '发帖人', name: 'name',align :'center', sortable : false }, 
               { label: '发帖时间', name: 'createtime',align :'center', sortable : false }, 
               { label: '图片', name: 'photourl',align :'center', sortable : false,  
                   formatter: function (value, grid, rows, state) { 
                   	var photourl = rows.photourl;
                   	var url=photourl.split(",");
                   	var c='';
                  for(var i=0;i<url.length;i++){
                   		/* c+='<img src = "'+Q.url+url[i]+'" width="20%">'; */
                   		/* <img src="/images/m02.jpg" onclick="this.style.zoom='2'" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/> */
                   		/*  c+='<img src = "'+Q.url+url[i]+'" width="20%"'+'onclick="this.width+=100;this.height+=100" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/>'; 
                   		*/
                   	 c+='<img src = "'+Q.url+url[i]+'" width="50" height="30"'+'onmouseout="NormalImg(this)" onmousemove="BigImg(this)" complete="complete">'; 

                   	}
                  return c;
                   }
                 },
                {  name: '操作' ,align :'center', fixed: true, sortable: false, resize: false, width:200, 
                    //formatter:'actions',  
                    formatter: function (value, grid, rows, state) { 
                    	
                    	var btn = "";
                    	btn += Q.gridbtn_detail(Q.grid_selector,rows.id);
                    	btn +=Q.gridbtn_del(Q.grid_selector,rows.id);
                    	return  btn;
                    }  
                }
            	];

var caption = "话题列表";

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
	o.keyword= $('#keyword').val(); 
	o.fpostingstyleid=$('#fsupplier').val();
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
//详情函数----命名规则  detail_tableid
function detail_order1_grid_table(id){
	var _url= 'postings/topicdetail.jsp?id='+id;
	
	Q.viewJsp(Q.page_content,_url);
}
//删除
function del_order1_grid_table(id){
	$._ajax({
		url: Q.url+"/postings/deleteTopic.action",
		data:{"id":id},
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
//查看详情
function modify_order1_grid_table(dataid){
	$("#xiangqing").empty();
	$("#agentxiangqing").empty();
	$("#xiangqing4").empty();
	$("#diaagreexiangqing").empty();
	 var _url= 'agent/applyuserdetail.jsp?userinfoid='+dataid;
	 Q.viewJsp("#agentxiangqing",_url);
}


</script>

