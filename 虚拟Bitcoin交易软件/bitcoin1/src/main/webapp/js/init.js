//init data
Q.gridData = {};
Q.fn = function(){};

$('#index_ul li').die('click').live('click',function(){
	var url = $(this).attr("vurl");
	$('#index_center').load(Q.url+"/html/"+url);
});

$.fn.grid = function(opts){
	if( opts.pageSize==null )opts.pageSize = 10;
	if( opts.pageNumber==null )opts.pageNumber = 1;
	
	var _this = $(this);
	var id = _this[0].id;
	_this.attr({border:0,cellpadding:0,cellspacing:1,width:"100%"});
	_this.addClass("table_layout");
	var cap = $("<caption></caption>").appendTo(_this);
	
	var btns = opts.btns;
	if( btns!=null ){
		var div = $('<div class="btnposition"></div>').appendTo(cap);
		for( var i=0;i<btns.length;i++ ){
			var btn = $('<input type="button" value="'+btns[i].text+'" class="btn"/>').appendTo(div);
			if(btns[i].click!=null)$(btn).click(btns[i].click);
		}
	}
	
	$(cap).append(opts.title);
	
	var cols = opts.cols;
	var thd = $("<thead></thead>").appendTo(_this);
	var tr = $("<tr></tr>").appendTo(thd);
	for( var i=0;i<cols.length;i++ ){$("<th>"+cols[i].text+"</th>").appendTo(tr);}
	
	Q.gridData[id] = opts;
}

$.fn.loadData = function(data){
	var _this = $(this);
	var id = _this[0].id;
	var cols = Q.gridData[id].cols;
	
	var tf = $("<tfoot></tfoot>").appendTo(_this);
	var ftr = $("<tr></tr>").appendTo(tf);
	var ftd = $("<td colspan='"+cols.length+"'></td>").appendTo(ftr);
	
	var a = (Q.gridData[id].pageNumber-1)*Q.gridData[id].pageSize+1;
	var c = data.total;
	var b = (c/Q.gridData[id].pageNumber)>10?a+Q.gridData[id].pageSize-1:a+c%Q.gridData[id].pageSize-1;
	$("<span>显示"+a+"到"+b+"，共"+c+"条记录</span>").appendTo(ftd);
	
	var fdiv = $("<div></div>").appendTo(ftd);
	$("<a href='javascript:;'>首页</a>").appendTo(fdiv);
	$("<a href='javascript:;'>上一页</a>").appendTo(fdiv);
	$("<a href='javascript:;'>下一页</a>").appendTo(fdiv);
	$("<a href='javascript:;'>末页</a>").appendTo(fdiv);
	
	for( var i=0;i<data.rows.length;i++ ){
		var tr = $("<tr style='background:#fff' i='"+i+"'></tr>").appendTo(_this);
		for( var j=0;j<cols.length;j++){
			var d = data.rows[i][cols[j].field];
			d = d==null?"":d;
			$("<td>"+d+"</td>").appendTo(tr);
		}
		
		$(tr).click(function(){
			if( Q.gridData[id].selected!=null )Q.gridData[id].selected.css("background","#fff");
			var i = $(this).attr("i");
			var row = data.rows[i];
			$(this).css("background","#ebf3fd");
			Q.gridData[id].selected = $(this);
			Q.gridData[id].onClick(row);
		});
	}
	Q.gridData[id].data = data;
}

$.fn.getSelected = function(){
	var _this = $(this);
	var id = _this[0].id;
	var i = Q.gridData[id].selected.attr("i");
	return Q.gridData[id].data.rows[i];
}

Q.needjs = function(url){
	if( Q.needjsArr[url] )return;
	$.ajax({
		url:url,
		async:false,
		dataType:'text',
		success:function(text){
			$("<script>"+text+"</script>").appendTo($(document.body));
			Q.needjsArr[url] = true;
		}
	});
}

$.ajax = function(o){
	if(o.dataType==null)o.dataType='json';
	if(o.type==null)o.type='post';
	if(o.error==null)o.error = function(){Q.alert('提示','操作失败！');}
	var succ = o.success;
	if(succ!=null){
		o.success = function(data){
			if( typeof(data)=='object' && data.op=='noright' ){
				Q.alert('提示','没有此项操作权限');
				return;
			}else if( typeof(data)=='object' && data.op=='timeout' ){
				location.reload();
				return;
			}
			succ(data);
		}
	}
	$._ajax(o);
}
$.fn.window.defaults=$.extend({},$.fn.panel.defaults,{onClose:function(){$(".validatebox-tip").remove();},zIndex:9000,resizable:false,shadow:true,modal:true,inline:false,title:"New Window",collapsible:false,minimizable:false,maximizable:false,closable:true,closed:false});
$.fn.combobox.defaults.editable = false;
$.fn.combobox.defaults.panelHeight="auto";
$.fn.combobox.defaults.onSelect = function(record){
	var valuefield = $(this).combobox('options').valueField;
	$(this).val(record[valuefield]);
}

Q.alert = function(title,msg){
	$.messager.show({
		title:title,
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}

$.fn.smsLoading = function(){
	var wrap = $(this);
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:wrap.width(),height:wrap.height()}).appendTo(wrap);
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候...").appendTo(wrap).css({display:"block",left:(wrap.width()-$("div.datagrid-mask-msg",wrap).outerWidth())/2,top:(wrap.height()-$("div.datagrid-mask-msg",wrap).outerHeight())/2});
}


$.fn.smsLoaded = function(){
	var wrap = $(this);
	wrap.children("div.datagrid-mask-msg").remove();
	wrap.children("div.datagrid-mask").remove();
}