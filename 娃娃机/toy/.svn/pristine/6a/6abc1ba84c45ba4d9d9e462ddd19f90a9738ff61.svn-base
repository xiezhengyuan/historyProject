/**
 * Easy Tree 简易的jquery树插件，将一个无序列表转化成树
 * 支持单选、新增、编辑、删除
 * @Copyright yuez.me 2014
 * @Author yuez
 * @Version 0.1
 * 
 * edit by lcc 2015.05.13 
 * add some functions for myself business 
 */
(function ($) {
	
    $.fn.EasyTree = function (options) {
        var defaults = {
            selectable: true,
            deletable: false,
            editable: false,
            addable: false,
            i18n: {
                deleteNull: '请选择要删除的项。',
                deleteConfirmation: '您确认要执行删除操作吗？',
                confirmButtonLabel: '确认',
                addCannot2: '最多只能添加二级菜单。',
                addCannot1: '最多只能添加3个一级菜单按钮。',
                addCannot3: '最多只能添加5个二级菜单按钮。',
                editNull: '请选择要编辑的项。',
                editMultiple: '一次只能编辑一项',
                addMultiple: '请选择一项添加',
                collapseTip: '收起分支',
                expandTip: '展开分支',
                selectTip: '选择',
                unselectTip: '取消选择',
                editTip: '编辑',
                addTip: '添加',
                deleteTip: '删除',
                cancelButtonLabel: '取消'
            }
        };

        var warningAlert = $('<div class="alert alert-warning alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong></strong><span class="alert-content"></span> </div> ');
        var dangerAlert = $('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong></strong><span class="alert-content"></span> </div> ');

        var createInput = $('<div class="input-group"><input type="text" class="form-control"><span class="input-group-btn"><button type="button" class="btn btn-default btn-success confirm"></button> </span><span class="input-group-btn"><button type="button" class="btn btn-default cancel"></button> </span> </div> ');

        options = $.extend(defaults, options);

        this.each(function () {
            var easyTree = $(this);
            $.each($(easyTree).find('ul > li'), function() {
                var text;
                if($(this).is('li:has(ul)')) {
                    var children = $(this).find(' > ul');
                    $(children).remove();
                    text = $(this).text();
                    $(this).html('<span><span class="glyphicon"></span><a href="javascript: void(0);"></a> </span>');
                    $(this).find(' > span > span').addClass('glyphicon-folder-open');
                    $(this).find(' > span > a').text(text);
                    $(this).append(children);
                }
                else {
                    text = $(this).text();
                    $(this).html('<span><span class="glyphicon"></span><a href="javascript: void(0);"></a> </span>');
                    $(this).find(' > span > span').addClass('glyphicon-file');
                    $(this).find(' > span > a').text(text);
                }
            });

            $(easyTree).find('li:has(ul)').addClass('parent_li').find(' > span').attr('title', options.i18n.collapseTip);

            // add easy tree toolbar dom
            if (options.deletable || options.editable || options.addable) {
                $(easyTree).prepend('<div class="easy-tree-toolbar"></div> ');
            }

            // addable
            if (options.addable) {
                $(easyTree).find('.easy-tree-toolbar').append('<div class="create"><button class="btn btn-default btn-sm btn-success"><span class="glyphicon glyphicon-plus"></span></button></div> ');
                $(easyTree).find('.easy-tree-toolbar .create > button').attr('title', options.i18n.addTip).click(function () {
                    var createBlock = $(easyTree).find('.easy-tree-toolbar .create');
                    $(createBlock).append(createInput);
                    $(createInput).find('input').focus();
                    $(createInput).find('.confirm').text(options.i18n.confirmButtonLabel);
                    $(createInput).find('.confirm').click(function () {
                        if ($(createInput).find('input').val() === '')
                            return;
                        
                        var level = 0;
                        var selected = getSelectedItems();
                        var item = $('<li><span><span class="glyphicon glyphicon-file"></span><a href="javascript: void(0);">' + $(createInput).find('input').val() + '</a> </span></li>');
                        $(item).find(' > span > span').attr('title', options.i18n.collapseTip);
                        $(item).find(' > span > a').attr('title', options.i18n.selectTip);
                        if (selected.length <= 0) {
                        	//检查一级菜单数量 不能超过三个
                            if(fn_checklv1menus())return;
                            $(easyTree).find(' > ul').append($(item));
                            level = 1;
                        } else if (selected.length > 1) {
                            $(easyTree).prepend(warningAlert);
                            $(easyTree).find('.alert .alert-content').text(options.i18n.addMultiple);
                        } else {
                            if ($(selected).hasClass('parent_li')) {
                            	//检查二级菜单数量 不能超过五个
                            	if(fn_checklv2menus(selected))return;
                            	//不能创建三级菜单
                            	if(fn_checkmenuislv3(selected))return;
                            	
                                $(selected).find(' > ul').append(item);
                            } else {
                            	//检查二级菜单数量 不能超过五个
                            	if(fn_checklv2menus(selected))return;
                            	//不能创建三级菜单
                            	if(fn_checkmenuislv3(selected))return;
                            	
                                $(selected).addClass('parent_li').find(' > span > span').addClass('glyphicon-folder-open').removeClass('glyphicon-file');
                                $(selected).append($('<ul></ul>')).find(' > ul').append(item);
                            }
                            level = 2;
                        }
                        fn_addmenu($(createInput).find('input').val(),level,selected);
                       
                        $(createInput).find('input').val('');
                        
                        $('.cont').html('请选中菜单为其添加相应的动作事件');
                        
                        if (options.selectable) {
                            $(item).find(' > span > a').attr('title', options.i18n.selectTip);
                            $(item).find(' > span > a').click(function (e) {
                                var li = $(this).parent().parent();
                               
                                if (li.hasClass('li_selected')) {
                                    $(this).attr('title', options.i18n.selectTip);
                                    $(li).removeClass('li_selected');
                                }
                                else {
                                    $(easyTree).find('li.li_selected').removeClass('li_selected');
                                    $(this).attr('title', options.i18n.unselectTip);
                                    $(li).addClass('li_selected');
                                }

                               
                                
                                if (options.deletable || options.editable || options.addable) {
                                    var selected = getSelectedItems();
                                    if (options.editable) {
                                        if (selected.length <= 0 || selected.length > 1)
                                            $(easyTree).find('.easy-tree-toolbar .edit > button').addClass('disabled');
                                        else
                                            $(easyTree).find('.easy-tree-toolbar .edit > button').removeClass('disabled');
                                    }

                                    if (options.deletable) {
                                        if (selected.length <= 0 || selected.length > 1)
                                            $(easyTree).find('.easy-tree-toolbar .remove > button').addClass('disabled');
                                        else
                                            $(easyTree).find('.easy-tree-toolbar .remove > button').removeClass('disabled');
                                    }

                                }
                               

                                e.stopPropagation();
                                
                                fn_click_event(this);
                            });
                            
                        }
                        $(createInput).remove();
                    });
                    $(createInput).find('.cancel').text(options.i18n.cancelButtonLabel);
                    $(createInput).find('.cancel').click(function () {
                        $(createInput).remove();
                    });
                });
                
            }

            // editable
            if (options.editable) {
                $(easyTree).find('.easy-tree-toolbar').append('<div class="edit"><button class="btn btn-default btn-sm btn-primary disabled"><span class="glyphicon glyphicon-edit"></span></button></div> ');
                $(easyTree).find('.easy-tree-toolbar .edit > button').attr('title', options.i18n.editTip).click(function () {
                    $(easyTree).find('input.easy-tree-editor').remove();
                    $(easyTree).find('li > span > a:hidden').show();
                    var selected = getSelectedItems();
                    if (selected.length <= 0) {
                        $(easyTree).prepend(warningAlert);
                        $(easyTree).find('.alert .alert-content').html(options.i18n.editNull);
                    }
                    else if (selected.length > 1) {
                        $(easyTree).prepend(warningAlert);
                        $(easyTree).find('.alert .alert-content').html(options.i18n.editMultiple);
                    }
                    else {
                        var value = $(selected).find(' > span > a').text();
                        $(selected).find(' > span > a').hide();
                        $(selected).find(' > span').append('<input type="text" class="easy-tree-editor">');
                        var editor = $(selected).find(' > span > input.easy-tree-editor');
                        $(editor).val(value);
                        $(editor).focus();
                        $(editor).keydown(function (e) {
                            if (e.which == 13) {
                                if ($(editor).val() !== '') {
                                	
                                    $(selected).find(' > span > a').text($(editor).val());
                                    $(editor).remove();
                                    $(selected).find(' > span > a').show();
                                    
                                    $('.cont').html('如要修改动作事件请选中菜单');
                                }
                            }
                        });
                    }
                });
            }

            // deletable
            if (options.deletable) {
                $(easyTree).find('.easy-tree-toolbar').append('<div class="remove"><button class="btn btn-default btn-sm btn-danger disabled"><span class="glyphicon glyphicon-remove"></span></button></div> ');
                $(easyTree).find('.easy-tree-toolbar .remove > button').attr('title', options.i18n.deleteTip).click(function () {
                    var selected = getSelectedItems();
                    if (selected.length <= 0) {
                        $(easyTree).prepend(warningAlert);
                        $(easyTree).find('.alert .alert-content').html(options.i18n.deleteNull);
                    } else {
                        $(easyTree).prepend(dangerAlert);
                        $(easyTree).find('.alert .alert-content').html(options.i18n.deleteConfirmation)
                            .append('<a style="margin-left: 10px;" class="btn btn-default btn-danger confirm"></a>')
                            .find('.confirm').html(options.i18n.confirmButtonLabel);
                        $(easyTree).find('.alert .alert-content .confirm').on('click', function () {
                            $(selected).find(' ul ').remove();
                            if($(selected).parent('ul').find(' > li').length <= 1) {
                                $(selected).parents('li').removeClass('parent_li').find(' > span > span').removeClass('glyphicon-folder-open').addClass('glyphicon-file');
                                $(selected).parent('ul').remove();
                            }
                            $(selected).remove();
                            $(dangerAlert).remove();
                        });
                    }
                });
            }

            // collapse or expand
            $(easyTree).delegate('li.parent_li > span', 'click', function (e) {
                var children = $(this).parent('li.parent_li').find(' > ul > li');
                if (children.is(':visible')) {
                    children.hide('fast');
                    $(this).attr('title', options.i18n.expandTip)
                        .find(' > span.glyphicon')
                        .addClass('glyphicon-folder-close')
                        .removeClass('glyphicon-folder-open');
                } else {
                    children.show('fast');
                    $(this).attr('title', options.i18n.collapseTip)
                        .find(' > span.glyphicon')
                        .addClass('glyphicon-folder-open')
                        .removeClass('glyphicon-folder-close');
                }
                e.stopPropagation();
            });

            // selectable, only single select
            if (options.selectable) {
                $(easyTree).find('li > span > a').attr('title', options.i18n.selectTip);
                $(easyTree).find('li > span > a').click(function (e) {
                    var li = $(this).parent().parent();
                    if (li.hasClass('li_selected')) {
                        $(this).attr('title', options.i18n.selectTip);
                        $(li).removeClass('li_selected');
                    }
                    else {
                        $(easyTree).find('li.li_selected').removeClass('li_selected');
                        $(this).attr('title', options.i18n.unselectTip);
                        $(li).addClass('li_selected');
                    }

                    if (options.deletable || options.editable || options.addable) {
                        var selected = getSelectedItems();
                        if (options.editable) {
                            if (selected.length <= 0 || selected.length > 1)
                                $(easyTree).find('.easy-tree-toolbar .edit > button').addClass('disabled');
                            else
                                $(easyTree).find('.easy-tree-toolbar .edit > button').removeClass('disabled');
                        }

                        if (options.deletable) {
                            if (selected.length <= 0 || selected.length > 1)
                                $(easyTree).find('.easy-tree-toolbar .remove > button').addClass('disabled');
                            else
                                $(easyTree).find('.easy-tree-toolbar .remove > button').removeClass('disabled');
                        }
                        
                    }

                    e.stopPropagation();
                    
                    fn_click_event(this);
                });
            }

            // Get selected items
            var getSelectedItems = function () {
                return $(easyTree).find('li.li_selected');
            };
            
            //write   by  lcc  -----start------
            var fn_checklv1menus = function(){
            	var flag = false;
            	if($(easyTree).find(' > ul > li').length>= 3){
                	$(easyTree).prepend(warningAlert);
                    $(easyTree).find('.alert .alert-content').text(options.i18n.addCannot1);
                    flag = true;
                }
            	return flag;
            }
            
            var fn_checklv2menus = function(selected){
            	var flag = false;
            	if($(selected).find(' > ul > li').length>= 5){
            		$(easyTree).prepend(warningAlert);
                    $(easyTree).find('.alert .alert-content').text(options.i18n.addCannot3);
                    flag = true;
            	}
            	return flag;
            }
            
            var fn_checkmenuislv3 = function(selected){
            	var flag = false;
            	if($(selected).parent().parent().hasClass('parent_li')){
            		$(easyTree).prepend(warningAlert);
            		$(easyTree).find('.alert .alert-content').text(options.i18n.addCannot2);
            		flag = true;
            	}
            	return flag;
            }
            
            
            var fn_click_event = function(o){
            	if($(o).parent().parent().hasClass('li_selected')){
            		var l = $(o).parent().parent().attr('level');
            		//if($(o).parent().parent().parent().parent().hasClass('parent_li')){
            		//alert(l);
            		if(l==2){
                		//二级菜单
                		var menuid = $(o).parent().parent().attr('menuid');
                		//alert("menuid:"+menuid);
                		if(menuid!=null)fn_queryMenuInfoById(menuid,l,o);
                	}else if(l==1){
                		//一级菜单
                		var menuid = $(o).parent().parent().attr('menuid');
                		//alert("menuid:"+menuid);
                		if(menuid!=null){
                			
                			fn_queryMenuInfoById(menuid,l,o);
                		}
                	}
            	}
            }
            
            var fn_addmenu = function(val,level,selected){
            	var parentId = 0;
            	if(level==2){
            		parentId = $(selected).attr('menuid');
            	}
            	
            	$._ajax({
            		url:'../../model/addmenu.action',
            		type:'post',
            		dataType:'json',
            		data:{'menuname':val,'level':level,"parentId":parentId},
            		success:function(data){
            			
            			if(data.op=='success'){
            				if(level == 1){
                				$(easyTree).find(' > ul > li').attr('menuid',data.menuid);
            					$(easyTree).find(' > ul > li').attr('level',1);
            				}
                			else if(level == 2){
                				$(selected).find(' > ul > li').attr('menuid',data.menuid);
            					$(selected).find(' > ul > li').attr('level',2);
                			}
            			}else{
            				
            			}
            			
            		}
            	})
            }
            
            var fn_queryMenuInfoById = function(menuid,level,o){
            	$._ajax({
            		url:'../../model/queryMenuInfoById.action',
            		type:'post',
            		dataType:'json',
            		data:{'menuid':menuid},
            		success:function(data){
            			if(data.op=='timeout'){
            				 $('.cont').html('等待超时，请重新<a href="http://121.41.118.148">登录</a>。');
            				 return;
            			}
            			
            			if(data.op=='noexist'){
            				if(level==2){
            					$('.cont').html('请选择订阅者点击此菜单后，公众号做出的响应动作<br><br><button  class="button glow button-rounded button-flat-primary" id="sendmsg" menuid="'+menuid+'">发送信息</button>&nbsp;<button  class="button glow button-rounded button-flat-royal" id="viewurl" menuid="'+menuid+'">跳转到网页</button>');
            				}else if(level==1){
            					if($(o).parent().parent().find(' > ul > li').length>0){
   	                   			 	var lilen = $(o).parent().parent().find(' > ul > li').length;
   	                   			 	var cont = "已经为一级菜单添加了二级菜单，无法设置其他动作。<br>";
   	                   			 	if(lilen<5)
   	                   				 cont += "还可以为其添加"+(5-lilen)+"个菜单。";
   	                   			 
   	                   			 	$('.cont').html(cont);
   	                   			 	return;
   	                   			}else{
   	                   				var cont = "请为一级菜单添加二级菜单，或者<button class = 'btn' id='setmotion' menuid= '"+menuid+"'>设置动作</button>。";
   	                   				$('.cont').html(cont);
   	                   			}
            				}
           				 	return;
            			}
            			
            			if(data.id!=null){
            				if(data.type==null){
            					if(level==2){
                					$('.cont').html('请选择订阅者点击此菜单后，公众号做出的响应动作<br><br><button  class="button glow button-rounded button-flat-primary" id="sendmsg" menuid="'+menuid+'">发送信息</button>&nbsp;<button  class="button glow button-rounded button-flat-royal" id="viewurl" menuid="'+menuid+'">跳转到网页</button>');
                				}else if(level==1){
                					if($(o).parent().parent().find(' > ul > li').length>0){
       	                   			 	var lilen = $(o).parent().parent().find(' > ul > li').length;
       	                   			 	var cont = "已经为一级菜单添加了二级菜单，无法设置其他动作。<br>";
       	                   			 	if(lilen<5)
       	                   				 cont += "还可以为其添加"+(5-lilen)+"个菜单。";
       	                   			 
       	                   			 	$('.cont').html(cont);
       	                   			 	return;
       	                   			}else{
       	                   				var cont = "请为一级菜单添加二级菜单，或者<button class = 'btn' id='setmotion' menuid= '"+menuid+"'>设置动作</button>。";
       	                   				$('.cont').html(cont);
       	                   			}
                				}
               				 	return;
            				}else{
            					if(data.type=='click'){
            						if(level==2){
            							var mid = data.id;
                		            	
                		            	var cont = '订阅者点击该菜单会收到以下消息<br>(目前只支持文本消息，更多消息类型待开发)，<br><br>';
                		            	cont += '<textArea menuid= "'+mid+'"></textArea>';
//                		            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_click">确定</button>&nbsp;<button  class="button glow button-rounded button-flat-caution" id="return_click">返回</button>';
                		            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_click">确定</button>';
                		            	$('.cont').html(cont);
                		            	
                		            	$._ajax({
                		            		url:'../../model/queryMenuMotion.action',
                		            		type:'post',
                		            		dataType:'json',
                		            		data:{'motion':data.motion},
                		            		success:function(d){
                		            			if(d.op=='success')
                		            			$('textArea').val(d.motion);
                		            		}
                		            	})
                    				}else if(level==1){
                    					if($(o).parent().parent().find(' > ul > li').length>0){
           	                   			 	var lilen = $(o).parent().parent().find(' > ul > li').length;
           	                   			 	var cont = "已经为一级菜单添加了二级菜单，无法设置其他动作。<br>";
           	                   			 	if(lilen<5)
           	                   				 cont += "还可以为其添加"+(5-lilen)+"个菜单。";
           	                   			 
           	                   			 	$('.cont').html(cont);
           	                   			 	return;
           	                   			}else{
           	                   				var cont = "请为一级菜单添加二级菜单，或者<button class = 'btn' id='setmotion' menuid= '"+menuid+"'>设置动作</button>。";
           	                   				$('.cont').html(cont);
           	                   			}
                    				}
            						
            					}else if(data.type=='view'){
            		            	if(level==2){
            		            		var mid = data.id;
                		            	
                		            	var cont = '订阅者点击该菜单会跳到以下链接，<br><br>';
                		            	cont += '<input type= "text" menuid= "'+menuid+'" id = "urlcontent">';
//                		            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_view">确定</button>&nbsp;<button  class="button glow button-rounded button-flat-caution" id="return_view">返回</button>';
                		            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_view">确定</button>';
                		            	$('.cont').html(cont);
                		            	
                		            	$('#urlcontent').val(data.motion);
                    				}else if(level==1){
                    					if($(o).parent().parent().find(' > ul > li').length>0){
           	                   			 	var lilen = $(o).parent().parent().find(' > ul > li').length;
           	                   			 	var cont = "已经为一级菜单添加了二级菜单，无法设置其他动作。<br>";
           	                   			 	if(lilen<5)
           	                   				 cont += "还可以为其添加"+(5-lilen)+"个菜单。";
           	                   			 
           	                   			 	$('.cont').html(cont);
           	                   			 	return;
           	                   			}else{
           	                   				var cont = "请为一级菜单添加二级菜单，或者<button class = 'btn' id='setmotion' menuid= '"+menuid+"'>设置动作</button>。";
           	                   				$('.cont').html(cont);
           	                   			}
                    				}
            		            	
            					}
            				}
            			}
            		}
            	})
            
            }
            
            $('#setmotion').die('click').live('click', function(){
            	var menuid = $(this).attr('menuid');
            	$('.cont').html('请选择订阅者点击此菜单后，公众号做出的响应动作<br><br><button  class="button glow button-rounded button-flat-primary" id="sendmsg" menuid="'+menuid+'">发送信息</button>&nbsp;<button  class="button glow button-rounded button-flat-royal" id="viewurl" menuid="'+menuid+'">跳转到网页</button>');
            })
            
            $('#sendmsg').die('click').live('click', function(){
            	var menuid = $(this).attr('menuid');
            	
            	var cont = '订阅者点击该菜单会收到以下消息<br>(目前只支持文本消息，更多消息类型待开发)，<br><br>';
            	cont += '<textArea menuid= "'+menuid+'"></textArea>';
//            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_click">确定</button>&nbsp;<button  class="button glow button-rounded button-flat-caution" id="return_click">返回</button>';
            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_click">确定</button>';
            	$('.cont').html(cont);
            })
            
            $('#viewurl').die('click').live('click', function(){
            	var menuid = $(this).attr('menuid');
            	
            	var cont = '订阅者点击该菜单会跳到以下链接，<br><br>';
            	cont += '<input type= "text" menuid= "'+menuid+'" id = "urlcontent">';
//            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_view">确定</button>&nbsp;<button  class="button glow button-rounded button-flat-caution" id="return_view">返回</button>';
            	cont += '<br><br><button  class="button glow button-rounded button-flat-royal" id="sure_view">确定</button>';
            	$('.cont').html(cont);
            })
            
            
            $('#sure_click').die('click').live('click', function(){
            	var click_content = $('textArea').val();
            	if($.trim(click_content).length==0){
            		return;
            	}
            	
            	var menuId = $('textArea').attr('menuid');
            	
            	$._ajax({
            		url:'../../model/addMenuClick.action',
            		type:'post',
            		dataType:'json',
            		data:{'menuid':menuId,'click_content':$.trim(click_content)},
            		success:function(data){
            			if(data.op=='timeout'){
           				 $('.cont').html('等待超时，请重新<a href="http://121.41.118.148">登录</a>。');
           				 return;
            			}
            			
            			if(data.op=='success'){
            				$('.cont').html('设置成功');
            			}else{
            				$('.cont').html('设置失败');
            			}
            		}
            	})
            })
            
            
            
            $('#sure_view').die('click').live('click', function(){
            	var urlcontent = $('#urlcontent').val();
            	if($.trim(urlcontent).length==0){
            		return;
            	}
            	
            	var menuId = $('#urlcontent').attr('menuid');
            	
            	$._ajax({
            		url:'../../model/addMenuView.action',
            		type:'post',
            		dataType:'json',
            		data:{'menuid':menuId,'urlcontent':$.trim(urlcontent)},
            		success:function(data){
            			if(data.op=='timeout'){
           				 $('.cont').html('等待超时，请重新<a href="http://121.41.118.148">登录</a>。');
           				 return;
            			}
            			
            			if(data.op=='success'){
            				$('.cont').html('设置成功');
            			}else{
            				$('.cont').html('设置失败');
            			}
            		}
            	})
            })
            
            $('#publish').die('click').live('click', function(){
            	$._ajax({
            		url:'../../model/publishMenu.action',
            		type:'post',
            		dataType:'json',
            		success:function(data){
            			if(data.errcode=='-40004'){
            				$('.cont').html('一级菜单至少需要设置动作或者有二级菜单');
            				return;
            			}
            			
            			$._ajax({
                    		url:'../../model/createMenu.action',
                    		type:'post',
                    		dataType:'json',
                    		data:{"p":$.toJSON(data)},
                    		success:function(d){
                    			if(d.errcode=='0'){
                    				$('.cont').html('发布成功，24小时内生效');
                    			}else{
                    				$('.cont').html(d.errmsg);
                    			}
                    		}
                    	})
            			
            			/*if(data.errcode=='0'){
            				$('.cont').html('发布成功，24小时内生效');
            			}else{
            				$('.cont').html(data.errmsg);
            			}*/
            		}
            	})
            })
            
            //write   by  lcc  -----end------
        });
    };
})(jQuery);

var fn_querymenu = function(){
	$._ajax({
		url:'../../model/queryMenuInfo.action',
		type:'post',
		dataType:'json',
		data:{"level":1},
		async: false,
		success:function(data){
			if(data.op=='timeout'){
			 $('.cont').html('等待超时，请重新<a href="http://121.41.118.148">登录</a>。');
			 return;
			}
			
			
			var html = '';
			if(data.total>0)$('.cont').html(html);
			$('#easy-tree-ul').html(html);
			for(var i = 0 ; i < data.total; i++){
				var menuConf = data.rows[i].menuConf;
				var id = menuConf.id;
				var buttonName = menuConf.buttonName;
				html += "<li menuid='"+id+"' level = '1'>"+buttonName;
				$._ajax({
					url:'../../model/queryMenuInfo.action',
					type:'post',
					dataType:'json',
					data:{"level":2,"parentId":id},
					async: false,
					success:function(d){
						if(d.op=='timeout'){
						 $('.cont').html('等待超时，请重新<a href="http://121.41.118.148">登录</a>。');
						 return;
						}
						html += "<ul>";
						for(var i = 0 ; i < d.total; i++){
							menuConf = d.rows[i].menuConf;
							level = menuConf.level;
							id = menuConf.id;
							parentId = menuConf.parentId;
							buttonName = menuConf.buttonName;
							
							html += "<li menuid='"+id+"' level = '2'>"+buttonName+"</li>";
						}
						html += "</ul>";
						html += "</li>"
					}
				})
			}
			$('#easy-tree-ul').html(html);
		}
	})
}