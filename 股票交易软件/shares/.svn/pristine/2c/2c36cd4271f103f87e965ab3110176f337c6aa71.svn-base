Q.grid_selector = null;
Q.pager_selector = null;

Q.jQgridInitOpt = {};

Q.jQgridInitOpt.mtype = "POST"; //请求方式
Q.jQgridInitOpt.datatype = "json";//数据格式
Q.jQgridInitOpt.page = 1;//初始页码
Q.jQgridInitOpt.styleUI = "Bootstrap";//UI样式
Q.jQgridInitOpt.autowidth = true;//是否自适应宽度
Q.jQgridInitOpt.height = 'auto';//表格高度
Q.jQgridInitOpt.multiselect = false;//是否可多选
Q.jQgridInitOpt.rowNum = 10;//页容量
Q.jQgridInitOpt.rowList = [10, 20, 30];//页容量选项
Q.jQgridInitOpt.viewrecords = true;//是否显示数据量
Q.jQgridInitOpt.rownumbers = true;//是否显示行号
Q.jQgridInitOpt.rownumWidth = 50;//行号宽度
Q.jQgridInitOpt.subGrid = false;//是否启用子表格
Q.jQgridInitOpt.data = [];//


Q.initOption = function(opt){
	
	if(opt==null)return Q.jQgridInitOpt;

	if(opt.mtype==null)opt.mtype = Q.jQgridInitOpt.mtype;
	if(opt.datatype==null)opt.datatype = Q.jQgridInitOpt.datatype;
	if(opt.data==null)opt.data = Q.jQgridInitOpt.data;
	if(opt.page==null)opt.page = Q.jQgridInitOpt.page;
	if(opt.styleUI==null)opt.styleUI = Q.jQgridInitOpt.styleUI;
	if(opt.autowidth==null)opt.autowidth = Q.jQgridInitOpt.autowidth;
	if(opt.height==null)opt.height = Q.jQgridInitOpt.height;
	if(opt.multiselect==null)opt.multiselect = Q.jQgridInitOpt.multiselect;
	if(opt.rowNum==null)opt.rowNum = Q.jQgridInitOpt.rowNum;
	if(opt.rowList==null)opt.rowList = Q.jQgridInitOpt.rowList;
	if(opt.viewrecords==null)opt.viewrecords = Q.jQgridInitOpt.viewrecords;
	if(opt.rownumbers==null)opt.rownumbers = Q.jQgridInitOpt.rownumbers;
	if(opt.rownumWidth==null)opt.rownumWidth = Q.jQgridInitOpt.rownumWidth;
	if(opt.subGrid==null)opt.subGrid = Q.jQgridInitOpt.subGrid;
	
	return opt;
}

/**
 * Q.grid_selector  tableid
 * Q.pager_selector 页码栏id
 * url 请求地址
 * postData 请求参数
 * colModel 表头
 * caption 表名 
 * opt 自定义表格设置
 * callback 回调函数
 */
Q.extJqGrid = function(grid_selector,pager_selector,url,postData,colModel,caption,opt,callback){
	
	opt = Q.initOption(opt);
	
	$("#"+grid_selector).jqGrid({
        url: url,
        postData:postData,
        mtype: opt.mtype,
        datatype: opt.datatype,
        data: opt.data,
        page: opt.page,
        styleUI: opt.styleUI,
        colModel: colModel,
        autowidth:opt.autowidth,
        height:opt.height,
        multiselect: opt.multiselect,
        rowNum: opt.rowNum,
        rowList: opt.rowList,
        viewrecords: opt.viewrecords,
        caption:caption,
        rownumbers: opt.rownumbers, // 显示行号  
        rownumWidth: opt.rownumWidth, // the width of the row numbers columns  
        //sortorder: "asc",
        //sortname: "ProvinceID",
        pager: pager_selector,
        subGrid: opt.subGrid,//是否启用子表格
        loadComplete : function(data) {
        	//console.info(data);
        	//session超时时，重新登录
        	 if( typeof(data)=='object' && data.op=='timeout' ){
 				location.reload();
 				return;
 			}
        	if(callback!=null)callback(data);
        	 
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
    });
	
	$("#"+grid_selector ).closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
	$('.ui-jqgrid-sortable').css({'text-align':'center'});
}

//grid,page,url,param,model,caption,opt,callback
Q.ExtJqGrid = function(o){
	var opt = o.opt;
	opt = Q.initOption(opt);
	
	$("#"+o.grid).jqGrid({
        url: o.url,
        postData:o.param,
        mtype: opt.mtype,
        datatype: opt.datatype,
        data: opt.data,
        page: opt.page,
        styleUI: opt.styleUI,
        colModel: o.model,
        autowidth:opt.autowidth,
        height:opt.height,
        multiselect: opt.multiselect,
        rowNum: opt.rowNum,
        rowList: opt.rowList,
        viewrecords: opt.viewrecords,
        caption:o.caption,
        rownumbers: opt.rownumbers, // 显示行号  
        rownumWidth: opt.rownumWidth, // the width of the row numbers columns  
        //sortorder: "asc",
        //sortname: "ProvinceID",
        pager: o.pager,
        subGrid: opt.subGrid,//是否启用子表格
        loadComplete : function(data) {
        	//console.info(data);
        	//session超时时，重新登录
        	 if( typeof(data)=='object' && data.op=='timeout' ){
 				location.reload();
 				return;
 			}
        	if(o.callback!=null){
        		var callback = o.callback;
        		callback(data);
        	}
        	 
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
    });
	
	$("#"+o.grid ).closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
	$('.ui-jqgrid-sortable').css({'text-align':'center'});
}


Q.page_content = "#page-content-index";

$("a[vhref]").click(function(){
	var _this = $(this);
	
	
	var sleep = _this.attr("sleep");
	if(sleep=='en')return;
	
	_this.attr("sleep","en");
	_this.css("color","gray");
	var vid = _this.attr("vid");
	setTimeout("unsleep("+vid+")",3000);
	var id = Q.page_content;
	var url = _this.attr("vhref");
	var vtid = _this.attr("vtid");
	if(vtid!=null && vtid.length>0)id=vtid;
	var vname = _this.attr("vname");
	
	$._ajax({
		url:Q.url+"/html/"+url,
		dataType:'text',
		success:function(data){
			$('#pageheader').html(vname);
			$(id).html(data);
		}
	}) 
	

});

var unsleep = function(vid){
	$("a[vid='"+vid+"']").attr("sleep","no");
	$("a[vid='"+vid+"']").css("color","#fff");
}

Array.prototype.indexOf = function (val) {
	
    for(var i = 0; i < this.length; i++){
    	if(this[i].sourcename!=null){
    		if(this[i].sourcename == val){return i;}
    	}else if(this[i].name!=null){
    		if(this[i].name == val){return i;}
    	}else{
    		if(this[i] == val){return i;}
    	}
        
    }
    return -1;
}
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if(index > -1){this.splice(index,1);}
}


Array.prototype.del=function(obj){
	for(var i =0;i <this.length;i++){
		var temp = this[i];
		if(!isNaN(obj)){
			temp=i;
		}
		if(temp == obj){
			for(var j = i;j <this.length;j++){
				this[j]=this[j+1];
			}
			this.length = this.length-1;
		}
	}
} 

Q.checkarrsame = function(oldarr,newarr){
	if(oldarr.length!=newarr.length)return false;
	
	var myold = [],mynew = [];
	
	for(var x = 0;x<oldarr.length;x++){
		if(oldarr[x].id!=null)myold[myold.length] = oldarr[x].id;
		else myold[myold.length] = oldarr[x];
	}
	
	for(var x = 0;x<newarr.length;x++){
		if(newarr[x].id!=null)mynew[mynew.length] = newarr[x].id;
		else mynew[mynew.length] = newarr[x];
	}
	
	myold.sort();
	mynew.sort();
	
	if(myold.join(",") == mynew.join(",")) return true;
	
	return false;
}

Q.gridbtn_change = function(tableid,dataid,state){
	 
	var msg = state=='正常'?'禁用':"恢复";
	
	var stated = state=='正常'?-1:0;
	return "<button title=\""+msg+"\" class=\"btn btn-inverse btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"change_"+tableid+"(" + dataid + ","+stated+")\">"+msg+"</button>";
}
Q.gridbtn_roledetail = function(tableid,dataid,roles){
	return "<button title=\"详情\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"roledetail_"+tableid+"(" + dataid +","+roles+ ")\"><span class=\"glyphicon glyphicon-list-alt\" aria-hidden=\"true\"></span></button>";
}


Q.gridbtn_ban = function(tableid,dataid){
	return "<button title=\"封禁\" class=\"btn btn-danger btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"ban_"+tableid+"(" + dataid + ")\">封禁</button>";
}
Q.gridbtn_unban = function(tableid,dataid){
	return "<button title=\"解禁\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"unban_"+tableid+"(" + dataid + ")\">解禁</button>";
}

Q.gridbtn_modify = function(tableid,dataid){
	return "<button title=\"编辑\" class=\"btn btn-warning btn-xs\" style=\"color:#f60\" onclick=\"modify_"+tableid+"(" + dataid+ ")\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>";
}
Q.gridbtn_del = function(tableid,dataid){
	return "<button title=\"删除\" class=\"btn btn-danger btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"del_"+tableid+"(" + dataid + ")\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>";
}
Q.gridbtn_detail = function(tableid,dataid){
	return "<button title=\"详情\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"detail_"+tableid+"(" + dataid + ")\"><span class=\"glyphicon glyphicon-list-alt\" aria-hidden=\"true\"></span></button>";
}
Q.gridbtn_send = function(tableid,dataid){
	return "<button title=\"发货\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"send_"+tableid+"(" + dataid + ")\"><span class=\"glyphicon glyphicon-list-alt\" aria-hidden=\"true\"></span>发货</button>";
}
Q.gridbtn_yifahuo = function(tableid,dataid){
	return "<a title=\"物流信息\" class=\"btn btn-info btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"yifahuo_"+tableid+"(" + dataid + ")\"><span class=\"glyphicon glyphicon-list-alt\" aria-hidden=\"true\"></span>物流信息</a>";
}

Q.gridbtn_pass = function(tableid,dataid){
	return "<button title=\"通过\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"pass_"+tableid+"(" + dataid + ")\">通过</button>";
}
Q.gridbtn_passb = function(tableid,dataid){
	return "<button title=\"解除禁言\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"passb_"+tableid+"(" + dataid + ")\">解除禁言</button>";
}
Q.gridbtn_pass1 = function(tableid,dataid){
	return "<button title=\"禁言\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"pass1_"+tableid+"(" + dataid + ")\">禁言</button>";
}
Q.gridbtn_notpass = function(tableid,dataid){
	return "<button title=\"不通过\" class=\"btn btn-pink btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"notpass_"+tableid+"(" + dataid + ")\">不通过</button>";
}
Q.gridbtn_shang = function(tableid,dataid){
	return "<button title=\"上架\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"shang_"+tableid+"(" + dataid + ")\">上架</button>";
}
Q.gridbtn_tutu = function(tableid,dataid){
	return "<button title=\"替换图标\" class=\"btn btn-success btn-xs\" style=\"color:red;margin-left:3px;\" onclick=\"tutu"+tableid+"(" + dataid + ")\">替换图标</button>";
}
Q.gridbtn_tutu1 = function(tableid,dataid){
	return "<button title=\"替换图标\" class=\"btn btn-success btn-xs\" style=\"color:red;margin-left:3px;\" onclick=\"tutu1"+tableid+"(" + dataid + ")\">添加图标</button>";
}
Q.gridbtn_xia = function(tableid,dataid){
	return "<button title=\"下架\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"xia_"+tableid+"(" + dataid + ")\">下架</button>";
}
Q.gridbtn_pay = function(tableid,dataid){
	return "<button title=\"\打款\" class=\"btn btn-purple btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"pay_"+tableid+"(" + dataid + ")\">打款</button>";
}

Q.gridbtn_give = function(tableid,dataid){
	return "<button title=\"发放\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"give_"+tableid+"(" + dataid + ")\">发放</button>";
}
Q.gridbtn_nopass = function(tableid,dataid){
	return "<button title=\"不通过\" class=\"btn btn-purple btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"nopass"+tableid+"(" + dataid + ")\">不通过</button>";
}
Q.gridbtn_confirm = function(tableid,dataid){
	return "<button title=\"确认打款\" class=\"btn btn-success btn-xs\" style=\"color:#f60;margin-left:3px;\" onclick=\"confirm"+tableid+"(" + dataid + ")\">确认打款</button>";
}
//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
/**
	$(table).find('input:checkbox').addClass('ace')
	.wrap('<label />')
	.after('<span class="lbl align-top" />')


	$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	.find('input.cbox[type=checkbox]').addClass('ace')
	.wrap('<label />').after('<span class="lbl align-top" />');
*/
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	var replacement = 
	{
		'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
		'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
		'ui-icon-disk' : 'ace-icon fa fa-check green',
		'ui-icon-cancel' : 'ace-icon fa fa-times red'
	};
	$(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
	*/
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}

Q.pageNavbutton = function(grid_selector,pager_selector){
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: true,
			editicon : 'ace-icon fa fa-pencil blue',
			add: true,
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: true,
			delicon : 'ace-icon fa fa-trash-o red',
			search: true,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: true,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: true,
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
			//edit record form
			//closeAfterEdit: true,
			//width: 700,
			recreateForm: true,
			beforeShowForm : function(e) {
				/*var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);*/
			},
			onClick : function(e) {
			}
		},
		{
			//new record form
			//width: 700,
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				/*var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
				.wrapInner('<div class="widget-header" />')
				style_edit_form(form);*/
			},
			onClick : function(e) {
			}
		},
		{
			//delete record form
			recreateForm: true,
			beforeShowForm : function(e) {
				/*var form = $(e[0]);
				if(form.data('styled')) return false;
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_delete_form(form);
				
				form.data('styled', true);*/
			},
			onClick : function(e) {
			}
		},
		{
			//search form
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				style_search_form(form);
			},
			afterRedraw: function(){
				style_search_filters($(this));
			}
			,
			multipleSearch: true,
			/**
			multipleGroup:true,
			showQuery: true
			*/
			
			onClick : function(e) {
			}
		},
		{
			//view record form
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			},
			
			onClick : function(e) {
			}
		}
	)
	
}


function style_edit_form(form) {
	//enable datepicker on "sdate" field and switches for "stock" field
	form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
	
	form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
			   //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
			  //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

			
	//update buttons classes
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
	buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
	
	buttons = form.next().find('.navButton a');
	buttons.find('.ui-icon').hide();
	buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
	buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');		
}

function style_delete_form(form) {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
	buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
}

function style_search_filters(form) {
	form.find('.delete-rule').val('X');
	form.find('.add-rule').addClass('btn btn-xs btn-primary');
	form.find('.add-group').addClass('btn btn-xs btn-success');
	form.find('.delete-group').addClass('btn btn-xs btn-danger');
}
function style_search_form(form) {
	var dialog = form.closest('.ui-jqdialog');
	var buttons = dialog.find('.EditTable')
	buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
	buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
	buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
}

function beforeDeleteCallback(e) {
	var form = $(e[0]);
	if(form.data('styled')) return false;
	
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_delete_form(form);
	
	form.data('styled', true);
}

function beforeEditCallback(e) {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_edit_form(form);
}

$._ajax = function(o){
	if(o.dataType==null)o.dataType='json';
	if(o.type==null)o.type='post';
	if(o.error==null)o.error = function(){Q.toastr('提示','操作失败！','error');}
	var succ = o.success;
	if(succ!=null){
		o.success = function(data){
			if( typeof(data)=='object' && data.op=='noright' ){
				Q.toastr('提示','没有此项操作权限','warning');
				return;
			}else if( typeof(data)=='object' && data.op=='timeout' ){
				location.reload();
				return;
			}
			succ(data);
		}
	}
	$.ajax(o);
}

//商品标签分类
function querygoodstag(selectedid){
	$._ajax({
		url: Q.url+"/goods/querygoodstag.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var goodstag  = data.rows[i].goodsTag;
				opts+= '<option value="'+goodstag.id+'">'+goodstag.tagname+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}


//一级分类
function querysupplier(selectedid){
	$._ajax({
		url: Q.url+"/goods/querysupplier.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var supplier = data.rows[i].supplier;
				opts+= '<option value="'+supplier.id+'">'+supplier.supplier+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}

//二级分类
function querysubtier(selectedid,supplierid){
	$._ajax({
		url: Q.url+"/goods/querysubtierbyid.action",
		data:{"supplierid":supplierid},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var subtier = data.rows[i].subtier;
				opts+= '<option value="'+subtier.id+'">'+subtier.subtier+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}

//三级分类
function querysumcier(selectedid,subtierid){
	$._ajax({
		url: Q.url+"/goods/querysumcierbyid.action",
		data:{"subtierid":subtierid},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = $(selectedid).html();
			for(var i=0;i<data.rows.length;i++){
				var sumcier = data.rows[i].sumcier;
				opts+= '<option value="'+sumcier.id+'">'+sumcier.sumcier+'</option>';
			}
			$(selectedid).html(opts);
			
		}
	});
	
}


Q.viewJsp = function(did,url){
	$._ajax({
		url: Q.url+"/html/"+url,
		dataType:"text",
		success: function(data){
			$(did).html(data);
		}
	});
}

Q.viewJspByParam = function(did,url,param){
	$._ajax({
		url: Q.url+"/html/"+url,
		data:param,
		dataType:"text",
		success: function(data){
			$(did).html(data);
		}
	});
}

Q.toastr = function(title,message,shortCutFunction){
	//success
	//info
	//warning
	//error
	
	toastr.options.timeOut=2000;
	toastr.options.progressBar=false;
	toastr.options.closeButton=true;
	toastr.options.positionClass="toast-top-center";
	shortCutFunction = shortCutFunction==null?"info":shortCutFunction;
	toastr[shortCutFunction](message,title);
}


Q.alert = function(title,message,type){
	type = type==null?"info":type;
	swal({
		title:title,
		text:message,
		type: type,
		confirmButtonText:"好"
	});
}	

Q.confirm = function(message,fn){
  /*  bootbox.confirm({
    	title: "提醒",
        message: message,
        buttons: {
            confirm: {
                label: '确定',
                className: 'btn-success'
            },
            cancel: {
                label: '取消',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
        	fn(result);
        }
    });*/
	
	swal(
			{
			title:"提醒",
			text:message,
			type:"warning",
			showCancelButton:true,
			confirmButtonColor:"#DD6B55",
			confirmButtonText:"确定",
			cancelButtonText:"取消",
			closeOnConfirm:true,
			closeOnCancel:true
			},
			function(isConfirm){
				fn(isConfirm)
			})
}



Q.filesarr = [];//新增图片
Q.logimgs = [];//历史图片

Q.dialog;

Q.loading =function(){
	Q.dialog = bootbox.dialog(

    		{
    			message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i>loading...</div>' ,
    			closeButton: false
    		}
    )
}
Q.loaded = function(){
	if(Q.dialog)Q.dialog.modal('hide');
}

Q.selected = function(id,selected){
	var opts = "";
	for(var i=0;i<selected.length;i++){
		opts+= '<option value="'+selected[i].id+'">'+selected[i].text+'</option>';
	}
	$('#'+id).html(opts);
}

Q.department = null;//缓存部门数据

Q.getDepartment = function(selecter){
	//检查缓存中是否有部门信息
	if(Q.department!=null){
		var opts = '<option value="0">请选择</option>';
		for(var i=0;i<Q.department.rows.length;i++){
			var department = Q.department.rows[i].department;
			opts+= '<option value="'+department.id+'">'+department.departmentname+'</option>';
		}
		$(selecter).html(opts);
		return;
	}
	
	$._ajax({
		url: Q.url+"/employee/querydepartment.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = '<option value="0">请选择</option>';
			for(var i=0;i<data.rows.length;i++){
				var department = data.rows[i].department;
				opts+= '<option value="'+department.id+'">'+department.departmentname+'</option>';
			}
			$(selecter).html(opts);
			
			Q.department = data;
		}
	});
	
}

Q.getdepartment = function(selecter){
	//检查缓存中是否有部门信息
	if(Q.department!=null){
		var opts = '<option value="-2">请选择</option><option value="-1">所有人</option><option value="0">所有员工</option>';
		for(var i=0;i<Q.department.rows.length;i++){
			var department = Q.department.rows[i].department;
			opts+= '<option value="'+department.id+'">'+department.departmentname+'</option>';
		}
		$(selecter).html(opts);
		return;
	}
	
	$._ajax({
		url: Q.url+"/employee/querydepartment.action",
		data:{},
		async:false,
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			
			var opts = '<option value="-2">请选择</option><option value="-1">所有人</option><option value="0">所有员工</option>';
			for(var i=0;i<data.rows.length;i++){
				var department = data.rows[i].department;
				opts+= '<option value="'+department.id+'">'+department.departmentname+'</option>';
			}
			$(selecter).html(opts);
			
			Q.department = data;
		}
	});
	
	
}

$.fn.serializeObject = function() {
	if ( !this.length ) { return false; }
	var $el = this;
	data = {};
	$el.find(':input[type!="button"]').each(function() {
		var _this = $(this);
		var key = _this.attr("name");
		var okey = key;
		var value = _this.val();
		if(key!=null&&key.length>0){
			if(key.indexOf('.')!=-1){
				var xkey = key.split('.')[0];
				var vtarget = _this.attr("vtarget");
				if( vtarget!=null && vtarget.length>0 )okey = vtarget;
				else okey = key.split('.')[1];
			}
			data[okey] = value;
		}
	});
	
	return data;
}

$.fn.resetObjectForm = function(o){
    if(o==null){
        var form=document.forms[$(this).attr('name')];
        if(form!==undefined){
            form.reset();
            return true;
        }else{
            //alert("重置表单名为"+$(this).attr('name')+"失败.");
            return false;
        }
    }
    var $this=this;
    var a = $this.serializeArray();
    $.each(a, function(i,n) {
        var $t=$('[name=\''+this.name+'\']',$this);
        	if(this.name.indexOf('.')!=-1){
        		var ns = this.name.split('.');
        		if( o[ns[0]]!=null && o[ns[0]][ns[1]]!=null ){
        			var value = o[ns[0]][ns[1]].valueOf();
        			$t.val(value);
        		}
        	}else if (o[this.name]!=null) {
                $t.val(o[this.name].valueOf());
            }
    });
};

//验证通过返回true  失败返回fasle
$.fn.checkForm = function(){
	if ( !this.length ) { return false; }
	var $el = this;
	var flag = true;
	$el.find(':input[type!="button"]').each(function(){
		var _this = $(this);
		var msg = _this.attr('msg');
		var rule = _this.attr('rule');
		if( msg!=null && msg.length>0 ){
			var vtype = _this.attr('vtype');
			var val = null;
			
			if( vtype == null || vtype.length==0 ){
				val = _this.val();
			}else if( vtype == 'date' ){
				val = _this.val();
			}else if( vtype == 'combo' ){
				val = _this.val();
			}
			
			if( val==null || val.length==0 ){
				Q.toastr('提示',msg,"warning");
				flag = false;
				return false;
			}
			
			if( vtype == 'combo' && val<=0 ){
				Q.toastr('提示',msg,"warning");
				flag = false;
				return false;
			}
		}
	});
	return flag;
}

//格式验证
Q.checkformatForm = function(){

	if ( !this.length ) { return false; }
	var $el = this;
	var flag = true;
	$el.find(':input[type!="button"]').each(function(){
		var _this = $(this);
		
		var rule=_this.attr('vrule');
		var vtype = _this.attr('vtype');
		var valu = null;
		
		if( vtype == null || vtype.length==0 ){
			valu = _this.val();
		}else if( vtype == 'date' ){
			valu = _this.val();
		}else if( vtype == 'combo' ){
			valu = _this.val();
		}
		if(rule!=null&&rule.length>0&&valu!=""){
			//手机格式验证
			if(rule=="tel"){
				var reg =/^[\d]{11}$/;
				if(!reg.test(valu)){
					Q.toastr("提示","手机号码无效",'warning');
					flag = false;
					return false;
				}
			}else if(rule=="phone"){
				var patt_p = /^0(\d{2}|\d{3})\-(\d{7}|\d{8})$/;
				var _patt_p8 = /^[\d]{8}$/;
				var _patt_p7 = /^[\d]{7}$/;
				var _patt_p9 = /^[\d]{9}$/;
				var _patt_p11 = /^[\d]{11}$/;
				if(!patt_p.test(valu)&&!_patt_p8.test(valu)&&!_patt_p7.test(valu)&&!_patt_p9.test(valu)&&!_patt_p11.test(valu)){
					Q.toastr("提示","座机号码无效",'warning');
					flag = false;
					return false;
				}
			}else if(rule=="email"){
				//邮箱验证
				var patt_e = /^([a-zA-Z0-9_-]){3,}@([a-zA-Z0-9_-])+(.[a-zA-Z0-9]+){1,2}/;
				if(!patt_e.test(valu)){
					Q.toastr("提示","邮箱无效");
					flag = false;
					return false;
				}
			}else if(rule=="age"&&valu!=null){
				//年龄验证
				var age=/^\d+$/;
				if( !age.test(valu) ||( valu < 0|| valu > 120)){
					Q.toastr('提示','请输入正确的年龄',"warning");
					flag = false;
					return false;
				}
			}else if(rule=="date"){
				//日期格式验证
		
				var dat = _this.datebox('getValue');
				if(dat!=null&&dat!=""){
					var patt_d = new RegExp("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$","g");
					if(!patt_d.test(dat)){
						var  a = _this.closest('td').prev().text();
						Q.toastr('提示',a+'日期格式不正确',"warning");
						flag = false;
						return false;
					}
				}
			}else if(rule=="card"){
				valu = valu.toUpperCase();
    			if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(valu))) {    
    				Q.toastr('提示','身份证号不正确',"warning");   
        			flag=false;
        			return false;        
    			}
    				var len, re; 
    				len = valu.length; 
    				if (len == 15) {
			        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			        var arrSplit = valu.match(re);  
			        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			        var bGoodDay; bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			        if (!bGoodDay) {        
			        	Q.toastr('提示','输入的身份证号里出生日期不对！',"warning");     
			            flag = false;
			            return false;
			        } 
			    }
			    if (len == 18) {
			        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			        var arrSplit = valu.match(re); 
			        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
			        var bGoodDay; bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			        if (!bGoodDay) {
			        	Q.toastr('提示','输入的身份证号里出生日期不对！',"warning");
			            flag = false;
			            return false;
			        }
			    }
			}else if(rule=="num"){
				//数字验证
				var patt_n = /^\d+$/;
				if (!patt_n.test(valu)){
					var  a = _this.closest('td').prev().text();
					Q.toastr('提示',a+'请填写正确的数字（整数）',"warning");
					flag = false;
					return false;
				}
			}else if(rule=="float"){
				//小数验证
				var patt_m = /^\d[\d,]*([.]\d{0,3})?$/;
				if (!patt_m.test(valu)){
					var  a = _this.closest('td').prev().text();
					Q.toastr('提示',a+'请填写正确的数字（整数或小数）',"warning");
					flag = false;
					return false;	
				}	
			}else if(rule=="money"&&valu!=null){
				//金额验证
				var patt_m = /^\d[\d,]*([.]\d{0,3})?$/;
				if (!patt_m.test(valu)){
					var  a = _this.closest('td').prev().text();
					Q.toastr('提示',a+'请填写正确的金额',"warning");
					flag = false;
					return false;	
				}	
			}else if(rule=="year"){
				//年份验证
				var patt_y = new RegExp("^[1-9]\\d{3}$", "g");
				if (!patt_y.test(valu)) {
					var  a = _this.closest('td').prev().text();
					Q.toastr('提示',a+"请输入正确的年份","warning");
					flag = false;
					return false;
				}
			}else if(rule=="month"){
				//月份验证
				var patt_mm = new RegExp("^(?:1[0-2]|[1-9])$","g");
				if (!patt_mm.test(valu)) {
					var  a = _this.closest('td').prev().text();
					Q.toastr('提示',a+"请输入正确的月份","warning");
					flag = false;
					return false;
				}
			}else if(rule=="code"){
				//邮编验证
				var patt_c = /^\d{6}$/;
				if(!patt_c.test(valu)){
					Q.toastr('提示',"邮编格式不正确","warning");
					flag = false;
					return false;
				}
			}else if(rule=="qq"){
				//验证QQ
				var patt_q = /^[1-9]\d{4,9}$/;
				if(!patt_q.test(valu)){
					Q.toastr('提示',"QQ格式不正确","warning");
					flag = false;
					return false;
				}
			}else if(rule=="msn"){
				//验证msn
				var patt_msn = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
				if(!patt_msn.test(valu)){
					Q.toastr('提示',"MSN格式不正确","warning");
					flag = false;
					return false;
				}
			}else if(rule=="post"){
				//验证传真号
				var patt_p = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				if(!patt_p.test(valu)){
					Q.toastr('提示',"传真号不正确","warning");
					flag = false;
					return false;
				}
			}
			else if(rule=="len"){
				//验证长度
				var patt_p =/^\w{5,18}$/;
				if(!patt_p.test(valu)){
					Q.toastr('提示',"用户名长度不正确","warning");
					flag = false;
					return false;
				}
			}
		}
	});
	return flag;
}

$.fn.checkformatForm = Q.checkformatForm;

Q.getObjById = function(tableName,id,callback){
	$._ajax({
		url: Q.url+"/static/general.action",
		data:{"tableName":tableName,id},
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'timeout' ){
				location.reload();
			}
			if(callback!=null)callback(data);
		}
	});
}

var para = {
		width            : "720px",  					// 宽度
		height           : "400px",  					// 高度
		itemWidth        : "150px",                     // 文件项的宽度
		itemHeight       : "150px",                     // 文件项的高度
		url              : "static/upload.action",  	// 上传文件的路径
		multiple         : true,  						// 是否可以多个文件上传
		dragDrop         : true,  						// 是否可以拖动上传文件
		del              : true,  						// 是否可以删除文件
		finishDel        : false
		};
var imgWidth = parseInt(para.itemWidth.replace("px", ""))-20;

Q.viewlogimg = function(preview){
		var xhtml = "";
		$(preview).html(xhtml);
		for(var i= 0 ;i<Q.logimgs.length;i++){
			xhtml += '<div id="uploadList_'+ i +'" class="upload_append_list">';
			xhtml += '	<div class="file_bar">';
			xhtml += '		<div style="padding:5px;">';
			xhtml += '			<p class="file_name">' + Q.logimgs[i].name + '</p>';
			xhtml += '<span class="file_del" preview="'+preview+'" onclick="del_file(this)" data-index="'+i+'" data-name= "'+Q.logimgs[i].name+'" data-url= "'+Q.logimgs[i].name+'" title="删除"></span>';   // 删除按钮的html
			xhtml += '		</div>';
			xhtml += '	</div>';
			xhtml += '	<a style="height:'+para.itemHeight+';width:'+para.itemWidth+';" href="#" class="imgBox">';
			xhtml += '		<div class="uploadImg" style="width:'+imgWidth+'px">';				
			xhtml += '			<img id="uploadImage_'+i+'" class="upload_image" src="' + Q.url+Q.logimgs[i].url + '" style="width:expression(this.width > '+imgWidth+' ? '+imgWidth+'px : this.width)" />';                                                                 
			xhtml += '		</div>';
			xhtml += '	</a>';
			xhtml += '</div>';
		}
		$(preview).append(xhtml);
		
		var add_img = "/html/zyUpload/control/images/add_img.png";
		var addbtn = '<div id="uploadList_add" class="upload_append_list" >';
		addbtn += '	<a style="height:'+para.itemHeight+';width:'+para.itemWidth+';" href="javascript:;" onclick="chooseimg(this);" class="imgBox">';
		addbtn += '		<div class="uploadImg" style="width:'+imgWidth+'px">';				
		addbtn += '			<img id="uploadImage_'+i+'" class="upload_image" src="' + Q.url+add_img + '" style="width:expression(this.width > '+imgWidth+' ? '+imgWidth+'px : this.width)" />';                                                                 
		addbtn += '		</div>';
		addbtn += '	</a>';
		addbtn += '</div>';
		addbtn += '<form id="file_img_form" name="file_img_form" method="POST"  enctype="multipart/form-data">'; 
		addbtn += '<input  id="file_img" type="file" name="filePath" preview="'+preview+'" onchange="file_upload(this)" style="display:none;">';
		addbtn += '</form>';
		$(preview).append(addbtn);
		
		funBindHoverEvent();
}

function chooseimg(o){
	
	$('#file_img').click();
	
}

function file_upload(o){
	var preview = $(o).attr("preview");
	var formname='file_img_form';
	Q.fn_uploadimg(formname,preview);
}


	function del_file(o){
		var index = parseInt($(o).attr("data-index"));
		var dataname = $(o).attr("data-name");
		var preview = $(o).attr("preview");
		//ZYFILE.funDeleteFile(parseInt($(this).attr("data-index")), true);
		//Q.logimgs.del(index);
		Q.logimgs.remove(dataname);
		$("#uploadList_" + index).fadeOut();
		for(var x = 0;x<Q.logimgs.length;x++){
			console.info(new Date().getTime()+"..."+Q.logimgs[x].id+":"+Q.logimgs[x].url);
		}
		Q.viewlogimg(preview);
		return true;	
	};
	


	var funBindHoverEvent = function(){
		$(".upload_append_list").hover(
			function (e) {
				$(this).find(".file_bar").addClass("file_hover");
			},function (e) {
				$(this).find(".file_bar").removeClass("file_hover");
			}
		);
	};
	


//初始化进度条
function initprogress(bar,percent){
	var percentVal = '0%';
    bar.width(percentVal)
    percent.html(percentVal);
}

Q.fn_uploadimg = function(formname,preview){
   // var status = $('#status');
	var bar = $('.bar');
	var percent = $('.percent');
	 var form = $("form[name="+formname+"]");  
	    var options  = {    
	        url:Q.url + '/static/upfileimage.action',    
	        type:'post',   
	        dataType:'json',
	        beforeSubmit:function(){
                //alert("表单提交前的操作");
               /* var filesize = $("input[type='file']")[0].files[0].size/1024/1024;
                if(filesize > 50){
                    alert("文件大小超过限制，最多50M");
                    return false;
                }*/
	        	var fileName = $("#file_img").val();
	        	var fileType = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length).toLocaleLowerCase();
	        	//var dom = document.getElementById("file_img");
	        	 //var fileSize = dom.files[0].size;
	        	if (fileType != 'jpg' && fileType != 'bmp' && fileType != 'png' && fileType != "jpeg") {
	        		Q.toastr('提示','图片必须为jpg、jpeg、bmp、png格式！','warning');
	        		return false;
	        	}
	        	return true;
            },
            beforeSend: function() {
                //status.empty();
            	initprogress(bar,percent);
            },
            uploadProgress: function(event, position, total, percentComplete) {//上传的过程
                //position 已上传了多少
                //total 总大小
                //已上传的百分数
                percentVal = percentComplete + '%';
                bar.width(percentVal)
                percent.html(percentVal);
                //console.log(percentVal, position, total);
            },
	        success:function(data){    
	        	//var jsondata =data.split(",");// eval("("+data+")");  
	        	
	        	if(data.op=='success'){
	        		
	        		//将上传成功的图片在展示区显示
	        		var xhtml = '<div id="uploadList_'+ (Q.logimgs.length+1) +'" class="upload_append_list">';
					xhtml += '	<div class="file_bar">';
					xhtml += '		<div style="padding:5px;">';
					xhtml += '			<p class="file_name">' + data.name + '</p>';
					xhtml += '<span class="file_del" preview="'+preview+'" onclick="del_file(this)" data-index="'+(Q.logimgs.length+1)+'" data-name= "'+data.name+'" title="删除"></span>';   // 删除按钮的html
					xhtml += '		</div>';
					xhtml += '	</div>';
					xhtml += '	<a style="height:'+para.itemHeight+';width:'+para.itemWidth+';" href="#" class="imgBox">';
					xhtml += '		<div class="uploadImg" style="width:'+imgWidth+'px">';				
					xhtml += '			<img id="uploadImage_'+(Q.logimgs.length+1)+'" class="upload_image" src="' + Q.url+data.url + '" style="width:expression(this.width > '+imgWidth+' ? '+imgWidth+'px : this.width)" />';                                                                 
					xhtml += '		</div>';
					xhtml += '	</a>';
					xhtml += '</div>';
	        		
	        		$("#uploadList_add").before(xhtml); 
	        		
	        		//上传成功的图片信息存入array中
	        		var imgObj = {};
	        		imgObj.id = data.fileid;
	        		imgObj.url = data.url;
	        		imgObj.name = data.name;
	        		Q.logimgs[Q.logimgs.length] = imgObj;
	        		
	        		//imgbindhoveranddel(preview);
	        		//为图片绑定鼠标经过事件
	    			funBindHoverEvent();
	        	}else if(data.op=='fail'){
	        		Q.toastr("提示",data.msg,'warning');
	        		initprogress(bar,percent);
	        	}else{
	        		Q.toastr("提示","图片上传失败",'warning');
	        		initprogress(bar,percent);
	        	}
	        },
            error:function(err){//失败
            	Q.toastr("提示","图片上传失败",'warning');
                initprogress(bar,percent);
            }    
	    };    
	    form.ajaxSubmit(options);  
}

$('#sys_exit').click(function(){
	$._ajax({
		url: Q.url+"/isw/exit.action",
		data:{},
		success: function(data){
			if( typeof(data) == 'object' && data.op == 'success' ){
				location.reload();
			}
		}
	});
});
function BigImg(x) {
    x.style.height = "100px";
    x.style.width = "100px";
}
function NormalImg(x) {
    x.style.height = "30px";
    x.style.width = "30px";
} 
