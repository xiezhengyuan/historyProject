if (!$u.isEmpty($.fn.panel)) {
	$.fn.panel.defaults = $.extend({}, $.fn.panel.defaults, {
		onBeforeDestroy : function() {

			$(this).find(".combo-f").each(function() {

				var panel = $(this).data().combo.panel;

				panel.panel("destroy");

			});

		}
	});
}

/*
 $.fn.textbox.initdom = function(_2) {
 $(_2).addClass("textbox-f").hide();
 var _3 = $("<span class=\"textbox\">" + "<input class=\"textbox-text\" autocomplete=\"off\">" + "<input type=\"hidden\" class=\"textbox-value\">" + "</span>").insertAfter(_2);
 var _4 = $(_2).attr("name");
 if (_4) {
 _3.find("input.textbox-value").attr("name", _4);
 $(_2).removeAttr("name").attr("textboxName", _4);
 }
 return _3;
 };
 */
$.fn.textbox.setEdit = function(jq, flag) {
	var _3 = true;
	var _4 = $.data(jq, "textbox");
	if (flag === undefined) {

	} else {
		_3 = $.fn.textbox.isTrue(flag);
	}
	var w = _4.textbox.width();
	if (_3) {
		if (_4.options.displaybox) {
			_4.options.displaybox.hide();
		}
		_4.textbox.show();
		_4.options.editflag = true;
		//alert($(_4.textbox).textbox("getText"));
		//alert($.fn.textbox.methods.getValue(jq));
		//alert(_4.textbox.find(".textbox-value").val());
	} else {
		if (_4.options.displaybox) {
			var dsp = _4.options.displaybox;
		} else {
			var dsp = $("<p class='displaybox'></p>").insertAfter(_4.textbox);
		}

		//alert(this.getText(_4));

		//alert($.fn.textbox.htmlEncode(_4.textbox.find(".textbox-value").val()));
		dsp.width(w);
		dsp.text(this.getText(_4));
		_4.textbox.hide();
		dsp.show();

		_4.options.displaybox = dsp;
		_4.options.editflag = false;
	}
};
$.fn.textbox.getText = function(_1) {

	var _4a = _1.textbox;
	if (_4a.is(":focus")) {
		return _4a.val();
	} else {
		return _1.options.value;
	}
};
$.fn.textbox.methods.setEdit = function(jq, _45) {
	return jq.each(function() {
		//var _46 = $.data(this, "textbox");
		//_46.options.editflag = _45;
		$.fn.textbox.setEdit(this, _45);
	});
};
$.fn.textbox.setLabel = function(_3) {
	var _4 = $.data(_3, "textbox");
	var label;
	var templatehtml = _4.options.labelhtml || $.fn.textbox.defaults.labelhtml;
	if ($u.type(_4.options.label) === "function") {
		var label = _4.options.label.call(this, _3);
	} else if ($u.type(_4.options.label) === "string") {
		var label = _4.options.label;
	}
	if (label) {
		//alert('oo');
		if ($.fn.textbox.isTrue(_4.options.isrendered)) {

			var _47 = $(_3).parent();
			//$(templatehtml.replace("[label]", this.htmlEncode(label))).appendTo(_47.find('.label').first());
			_47.find('.label').text(label);
		} else {
			var _47 = $(_3).parent();
			//var _48 = $("<table style='width:100%' cellspacing=\"0\" cellpadding=\"0\"  border='0'><tr align=left><td class='label' style='white-space:nowrap' align=left></td><td align=left valign=middle style='width:100%' class='fieldcontainer'></td></tr></table>");
			//alert(templatehtml);
			var _48 = $(templatehtml);
			//alert("01");
			//alert(this.htmlEncode(label));
			//$(this.htmlEncode(label)).appendTo(_48.find('.label'));
			_48.find('.label').text(label);

			//$(templatehtml.replace("[label]", this.htmlEncode(label))).appendTo(_48.find('.label'));
			_47.children().appendTo(_48.find('.fieldcontainer'));
			_48.prependTo(_47);
			_4.textbox_all = _48;
			_4.options.isrendered = true;
		}

	}
};
$.fn.textbox.defaults.labelhtml = "<table style='width:100%' cellspacing=\"0\" cellpadding=\"0\"  border='0'><tr align=left><td class='label' style='white-space:nowrap' align=left></td><td align=left valign=middle style='width:100%' class='fieldcontainer'></td></tr></table>";
$.fn.textbox.methods.setLabel = function(jq, _45) {
	return jq.each(function() {
		var _46 = $.data(this, "textbox");
		_46.options.label = _45;
		$.fn.textbox.setLabel(this);
	});
};
$.fn.textbox.methods.hide = function(jq, _45) {
	return jq.each(function() {
		var _46 = $.data(this, "textbox");
		if (_46.textbox_all) {
			//_46.textbox_all.hide();
			//_46.textbox_all.get(0).style.display="none";
			if (_46.textbox_all.parent().hasClass("itemcontainer")) {
				_46.textbox_all.parent().hide();
				_46.options.ishide = true;
			} else {
				_46.textbox_all.hide();
				_46.options.ishide = true;
			}
		} else {
			_46.textbox.hide();
			_46.options.ishide = true;
		}

	});
};
$.fn.textbox.methods.show = function(jq, _45) {
	return jq.each(function() {
		var _46 = $.data(this, "textbox");
		if (_46.textbox_all) {
			//_46.textbox_all.hide();
			//_46.textbox_all.get(0).style.display="none";
			if (_46.textbox_all.parent().hasClass("itemcontainer")) {
				_46.textbox_all.parent().show();
				_46.options.ishide = false;
			} else {
				_46.textbox_all.show();
				_46.options.ishide = false;
			}
		} else {
			_46.textbox.show();
			_46.options.ishide = false;
		}

	});
};
$.fn.textbox.defaults.inputEvents.blur = function(e) {
	var t = $(e.data.target);
	var _53 = t.textbox("options");
	//alert("e");
	t.textbox("setValue", _53.value);
};
$.fn.textbox.defaults.onResize = function(_56, _57) {
	//alert(_57);
};
$.fn.validatebox.defaults.events = {
	focus : function() {
	}, //_a,
	blur : function(e) {

	}, //_f,
	mouseenter : function() {
	}, //_13,
	mouseleave : function() {
	}, //_16,
	click : function(e) {
		//var t = $(e.data.target);
		//if (t.attr("type") == "checkbox" || t.attr("type") == "radio") {
		//	t.focus().validatebox("validate");
		//}
	}
};
$.fn.textbox.methods.setValue = function(jq, _47) {
	return jq.each(function() {
		var _48 = $.data(this, "textbox").options;
		var _49 = $(this).textbox("getValue");
		$(this).textbox("initValue", _47);
		//alert(_47);
		if (_49 != _47) {
			_48.onChange.call(this, _47, _49);
			$(this).closest("form").trigger("_change", [this]);
		}
	});
};
$.fn.textbox.methods.initValue = function(jq, _45) {
	return jq.each(function() {
		var _46 = $.data(this, "textbox");
		_46.options.value = "";
		if (_46.options.isrendered) {

		} else {
			$.fn.textbox.setLabel(this);

		}

		/*
		 var _47 = $(this).parent();
		 //alert(_47.get(0).outerHTML);
		 var _48 = $("<table style='width:100%' cellspacing=\"0\" cellpadding=\"3\" border='0'><tr align=left><td class='label'></td><td class='fieldcontainer'></td></tr></table>");

		 //alert(_47);
		 $("<label>test</label>").appendTo(_48.find('.label'));
		 _46.textbox.appendTo(_48.find('.fieldcontainer'));
		 _48.prependTo(_47);
		 */
		if (_46.options.fitflag === undefined) {
			_46.options.fitflag = false;
		}
		if ($.fn.textbox.isTrue(_46.options.innfit)) {
			if (_46.options.fitflag == false) {
				//_46.textbox.css("width", "100%");
				_46.textbox.width("97%");
				//_46.textbox.find(".textbox-text").width(_46.textbox.width());
				//_46.textbox.find(".textbox-text").css("width", "100%");
				//_46.textbox.find(".textbox-text").width("100%").css(" box-sizing","border-box").css("-webkit-box-sizing","border-box;").css("-moz-box-sizing","border-box;");
				_46.textbox.find(".textbox-text").width("100%");
				//_46.textbox.find(".textbox-text").width("auto");
				//alert(_46.options.fitflag);
				_46.options.fitflag = true;
				//alert("0");
			}

		}
		//$(this).before($("<label>test</label>"));
		$(this).textbox("setText", _45);
		_46.textbox.find(".textbox-value").val(_45);
		$(this).val(_45);
	});
};
$.fn.textbox.htmlEncode = function(value) {
	var re = new RegExp(" ", "ig");
	return !value ? value : String(value).replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;").replace(re, "&nbsp;").replace(/\r\n/ig, "<br>");
};
$.fn.textbox.isTrue = function(o) {
	if ( typeof o != "undefined") {
		if ( typeof o == "function") {
			o = o();
		}
		if ( typeof o == "string") {

			if (o.toLowerCase() == "true") {
				return true;
			} else if (o == "0") {
				return true;
			} else {
				return false;
			}
		} else if ( typeof o == "boolean") {
			return o;
		} else if ( typeof o == "number") {
			if (o == 0) {
				return true;
			} else {
				return false;
			}
		}

	} else {
		return false;
	}
};
$.fn.datagrid.methods.setEdit = function(jq, f) {
	return jq.each(function() {
		var opts = $(this).datagrid("options");
		opts[$co.defaults.edit] = f;

	});
};
/*
 $.fn.datagrid.methods.getValue = function(jq, rownum) {
 return jq.each(function() {
 var eds = jq.datagrid('getEditors', rownum);
 //alert(eds);
 var o = {};
 for (var i = 0; i < eds.length; i++) {
 //alert(eds[i].field+eds[i].target+eds[i].type);
 //o[eds[i].field] = $(eds[i].target)[eds[i].type]("getValue");
 //alert(eds[i].field);
 o[eds[i].field] = $.fn.datagrid.defaults.editors[eds[i].type].getValue(eds[i].target);
 //			alert(o[eds[i].field]);
 }
 $u.applyIf(o, jq.datagrid("getSelected"));
 return o;

 });
 };
 */
$.fn.datagrid.defaults.onClickCell = function(index, field, value) {
	var jq = $(this);
	var opts = jq.datagrid("options");
	var hasChanged = function(rownum) {
		var o = getRowValues(rownum);
		for (var fn in o) {
			var oldfvalue = jq.datagrid('getRows')[rownum][fn];
			if ($u.isEmpty(oldfvalue) && ($u.isEmpty(o[fn]) == false)) {
				return true;
			} else if (o[fn] != oldfvalue) {
				return true;
			}
		}
		return false;
	};

	var getRowValues = function(rownum) {
		//alert($u.JSON.encode(jq.datagrid("getSelected")));
		var eds = jq.datagrid('getEditors', rownum);
		//alert(eds);
		var o = {};
		for (var i = 0; i < eds.length; i++) {
			//alert(eds[i].field+eds[i].target+eds[i].type);
			//o[eds[i].field] = $(eds[i].target)[eds[i].type]("getValue");
			//alert(eds[i].field);
			o[eds[i].field] = $.fn.datagrid.defaults.editors[eds[i].type].getValue(eds[i].target);
			//			alert(o[eds[i].field]);
		}
		//$u.applyIf(o, jq.datagrid("getSelected"));
		$u.applyIf(o, jq.datagrid("getRows")[rownum]);
		//alert($u.JSON.encode(jq.datagrid("getRows")[rownum]));
		//alert(jq.datagrid("getRowIndex",jq.datagrid("getRows")[rownum]));
		return o;
	};
	var saveerror = function(o) {
		if ($u.isEmpty(o.msg)) {
			$.messager.alert('提示', '保存失败!!!');
		} else {
			$.messager.alert('提示', o.msg);
		}

	};
	var save = function(rownum) {
		if ($u.isEmpty(opts.saveurl) == false) {
			var o = getRowValues(rownum);
			//var o = $.fn.datagrid.methods.getValue(jq,rownum);
			//alert($.fn.datagrid.defaults.onClickCell.getRowValues);
			var rt = $u.submit(opts.saveurl, o);
			if ($u.isEmpty(rt)) {
				saveerror(rownum);
				return false;
			} else {
				var ro = $u.parseJSON(rt);
				if ($u.isEmpty(ro)) {
					saveerror(rownum);
					return false;
				} else {
					if ($u.isEmpty(ro.status)) {
						saveerror(rownum);
						return false;
					} else {
						if (ro.status == "success") {
							return true;
						} else {
							saveerror(ro);
							return false;
						}
					}
				}
			}
		} else {
			return true;

		}

	};
	var endEditing = function() {
		if ($u.isEmpty(opts.editIndex)) {
			return true;
		} else {
			if (jq.datagrid('validateRow', opts.editIndex)) {
				if (hasChanged(opts.editIndex)) {
					if ($u.isFunction(opts.save)) {
						if (opts.save.call(jq, getRowValues(opts.editIndex), opts.editIndex, jq.datagrid('getEditors', opts.editIndex))) {
							jq.datagrid('endEdit', opts.editIndex);
							opts.editIndex = undefined;
							return true;
						} else {
							return false;
						}
					} else {
						if (save(opts.editIndex)) {
							jq.datagrid('endEdit', opts.editIndex);
							opts.editIndex = undefined;
							return true;
						} else {
							return false;
						}

					}
				} else {
					jq.datagrid('endEdit', opts.editIndex);
					opts.editIndex = undefined;
					return true;
				}

			} else {
				return false;
			}

		}
		/*
		 if ($('#dg').datagrid('validateRow', editIndex)) {
		 var ed = $('#dg').datagrid('getEditor', {
		 index : editIndex,
		 field : 'productid'
		 });
		 var productname = $(ed.target).combobox('getText');
		 $('#dg').datagrid('getRows')[editIndex]['productname'] = productname;
		 $('#dg').datagrid('endEdit', editIndex);
		 editIndex = undefined;
		 return true;
		 } else {
		 return false;
		 }
		 */
	};
	if ($u.isEmpty(opts) == false) {
		//alert(opts[$co.defaults.edit]);
		if ($u.isFunction(opts.onbeforeclickcell)) {
			opts.onbeforeclickcell.call(this, index, field, value);
		}
		if ($u.isTrue(opts[$co.defaults.edit])) {
			if (endEditing()) {
				jq.datagrid('selectRow', index).datagrid('beginEdit', index);
				var ed = jq.datagrid('getEditor', {
					index : index,
					field : field
				});
				if (ed) {
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				opts.editIndex = index;
			} else {
				jq.datagrid('selectRow', opts.editIndex);
			}

		}
		if ($u.isFunction(opts.onafterclickcell)) {
			opts.onafterclickcell.call(this, index, field, value);
		}
		if ($u.isFunction(opts.onclick)) {
			opts.onclick.call(this, index, field, value);
		}
	}

	//alert($(this).datagrid("options").url);
	//alert(index + field + value);
};
//$.fn._droppable=$.fn.droppable;
//$.fn._draggable=$.fn.draggable;
//$.fn.droppable=null;
//$.fn.draggable=null;
