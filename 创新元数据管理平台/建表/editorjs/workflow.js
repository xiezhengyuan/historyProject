if (innsoft) {

} else {
	var innsoft = {
		createjs : "editor"
	};
}
if (innsoft.editor) {

} else {
	innsoft.editor = {
		createjs : "editor",
		ver : "1.0"
	};
}
var $editor=innsoft.editor;
innsoft.editor.workflow = {
	editProject : function(graph, cell) {
		this.editProperty.openProperty(graph, cell, "conf/workflow_project_property.js");
	},
	editNode : function(graph, cell) {
		this.editProperty.openProperty(graph, cell, "conf/workflow_node_property.js");
	},
	editConnect : function(graph, cell) {
		this.editProperty.openProperty(graph, cell, "conf/workflow_connect_property.js");
	},
	editProperty : {

		openProperty : function(graph, cell, form, win) {
			var win = win || {
				comptype : 'easyui-window',
				title : "属性",
				width : '770',
				height : '550',
				inline : true,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				resizable : false,
				//overflow : "hidden",
				//id:"window1",
				//modal : false,
				iconCls : 'icon-save'
			};
			var newwin = $win.openform({}, "properties_window", win, {
				graph : graph,
				cell : cell,
				editProperty : this
			});
			var newcell = $u.apply({}, cell);
			newwin.setValue(newcell);
		},
		saveProperty : function() {
			var thisform = $co.getFormCP(this);
			//获取当前表单

			if (!$u.isEmpty(thisform)) {
				var s = thisform.getOpener();
				var t_graph = s.graph;
				var t_cell = s.cell;
				var d = thisform.getValue();
				t_graph.model.setValue(t_cell, d);
				//--------获取当前窗口-----------------
				var thiswin = $co.getWindowJQUERY(this);
				//alert(thiswin.getObject(0).get(0).outerHTML);
				//------------关闭窗口--------------------
				if (!$u.isEmpty(thiswin)) {
					//thiswin.runCommand("close");
					thiswin.window("close");
				}
			}
		},
		closeProperty : function() {
			var thiswin = $co.getWindowJQUERY(this);
			//alert(thiswin.getObject(0).get(0).outerHTML);
			//------------关闭窗口--------------------
			if (!$u.isEmpty(thiswin)) {
				//thiswin.runCommand("close");
				thiswin.window("close");
			}
		}
	}

};
