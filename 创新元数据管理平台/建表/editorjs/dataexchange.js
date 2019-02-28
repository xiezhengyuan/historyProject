// Program starts here. Creates a sample graph in the
// DOM node with the specified ID. This function is invoked
// from the onLoad event handler of the document (see below).
var editor;
var openfilename = "";

var input_dataobject = $u.urlDecode(window.location.href.substring(window.location.href.indexOf("?") + 1, window.location.href.length));
var data_define_base = "../";
if (!$u.isEmpty(input_dataobject.filename)) {
	var data_define_filename = input_dataobject.filename;
}
function main(container, outline, toolbar, sidebar, status) {
	// Checks if the browser is supported
	if (!mxClient.isBrowserSupported()) {
		// Displays an error message if the browser is not supported.
		mxUtils.error('Browser is not supported!', 200, false);
	} else {
		// Specifies shadow opacity, color and offset
		mxConstants.SHADOW_OPACITY = 0.5;
		mxConstants.SHADOWCOLOR = '#C0C0C0';
		mxConstants.SHADOW_OFFSET_X = 5;
		mxConstants.SHADOW_OFFSET_Y = 6;

		// Table icon dimensions and position
		mxSwimlane.prototype.imageSize = 20;
		mxSwimlane.prototype.imageDx = 16;
		mxSwimlane.prototype.imageDy = 4;

		// Changes swimlane icon bounds
		mxSwimlane.prototype.getImageBounds = function(x, y, w, h) {
			return new mxRectangle(x + this.imageDx, y + this.imageDy, this.imageSize, this.imageSize);
		};

		// Defines an icon for creating new connections in the connection handler.
		// This will automatically disable the highlighting of the source vertex.
		mxConnectionHandler.prototype.connectImage = new mxImage('images/connector.gif', 16, 16);

		// Prefetches all images that appear in colums
		// to avoid problems with the auto-layout
		var keyImage = new Image();
		keyImage.src = "images/key.png";

		var plusImage = new Image();
		plusImage.src = "images/plus.png";

		var checkImage = new Image();
		checkImage.src = "images/check.png";

		// Workaround for Internet Explorer ignoring certain CSS directives
		if (mxClient.IS_QUIRKS) {
			document.body.style.overflow = 'hidden';
			new mxDivResizer(container);
			new mxDivResizer(outline);
			new mxDivResizer(toolbar);
			new mxDivResizer(sidebar);
			new mxDivResizer(status);
		}

		// Creates the graph inside the given container. The
		// editor is used to create certain functionality for the
		// graph, such as the rubberband selection, but most parts
		// of the UI are custom in this example.
		editor = new mxEditor();
		var graph = editor.graph;
		var model = graph.model;

		// Disables some global features
		graph.setConnectable(true);
		graph.setCellsDisconnectable(false);
		graph.setCellsCloneable(false);
		graph.swimlaneNesting = false;
		graph.dropEnabled = true;

		// Does not allow dangling edges
		graph.setAllowDanglingEdges(false);

		// Forces use of default edge in mxConnectionHandler
		graph.connectionHandler.factoryMethod = null;

		// Only tables are resizable
		graph.isCellResizable = function(cell) {
			return this.isSwimlane(cell);
		};

		// Only tables are movable
		graph.isCellMovable = function(cell) {
			return this.isSwimlane(cell);
		};

		// Sets the graph container and configures the editor
		editor.setGraphContainer(container);
		var config = mxUtils.load('config/keyhandler-minimal.xml').getDocumentElement();
		editor.configure(config);

		// Configures the automatic layout for the table columns
		editor.layoutSwimlanes = true;
		editor.createSwimlaneLayout = function() {
			var layout = new mxStackLayout(this.graph, false);
			layout.fill = true;
			layout.resizeParent = true;

			// Overrides the function to always return true
			layout.isVertexMovable = function(cell) {
				return true;
			};

			return layout;
		};

		// Text label changes will go into the name field of the user object
		graph.model.valueForCellChanged = function(cell, value) {
			if (value.name != null) {
				return mxGraphModel.prototype.valueForCellChanged.apply(this, arguments);
			} else {
				var old = cell.value.name;
				cell.value.name = value;
				return old;
			}
		};

		// Columns are dynamically created HTML labels
		graph.isHtmlLabel = function(cell) {
			return !this.isSwimlane(cell) && !this.model.isEdge(cell);
		};

		// Edges are not editable
		graph.isCellEditable = function(cell) {
			return !this.model.isEdge(cell);
		};

		// Returns the name field of the user object for the label
		graph.convertValueToString = function(cell) {
			if (cell.value != null && cell.value.name != null) {
				return cell.value.name;
			}

			return mxGraph.prototype.convertValueToString.apply(this, arguments);
			// "supercall"
		};

		// Returns the type as the tooltip for column cells
		graph.getTooltip = function(state) {
			if (this.isHtmlLabel(state.cell)) {
				return '类型: ' + state.cell.value.type;
			} else if (this.model.isEdge(state.cell)) {
				var source = this.model.getTerminal(state.cell, true);
				var parent = this.model.getParent(source);
				var target = this.model.getTerminal(state.cell, false);
				var targetparent = this.model.getParent(target);
				//return parent.value.name + '.' + source.value.name;
				return parent.value.label + '.' + source.value.label + '－>' + targetparent.value.label + '.' + target.value.label;
			}

			return mxGraph.prototype.getTooltip.apply(this, arguments);
			// "supercall"
		};

		// Creates a dynamic HTML label for column fields
		graph.getLabel = function(cell) {
			if (this.isHtmlLabel(cell)) {
				var label = '';

				if (cell.value.primaryKey) {
					label += '<img title="Primary Key" src="images/key.png" width="16" height="16" align="top">&nbsp;';
				} else {
					label += '<img src="images/spacer.gif" width="16" height="1">&nbsp;';
				}

				if (cell.value.autoIncrement) {
					label += '<img title="Auto Increment" src="images/plus.png" width="16" height="16" align="top">&nbsp;';
				} else if (cell.value.unique) {
					label += '<img title="Unique" src="images/check.png" width="16" height="16" align="top">&nbsp;';
				} else {
					label += '<img src="images/spacer.gif" width="16" height="1">&nbsp;';
				}
				if (cell.value.label) {
					return label + mxUtils.htmlEntities(cell.value.label, false) + ': ' + mxUtils.htmlEntities(cell.value.type, false);
				} else {
					return label + mxUtils.htmlEntities(cell.value.name, false) + ': ' + mxUtils.htmlEntities(cell.value.type, false);
				}
				//return label + mxUtils.htmlEntities(cell.value.name, false) + ': ' + mxUtils.htmlEntities(cell.value.type, false);
			} else if (cell.value instanceof Table) {
				if (cell.value.label) {
					return cell.value.label;
				} else {
					return cell.value.name;
				}
			}

			return mxGraph.prototype.getLabel.apply(this, arguments);
			// "supercall"
		};

		// Removes the source vertex if edges are removed
		graph.addListener(mxEvent.REMOVE_CELLS, function(sender, evt) {
			var cells = evt.getProperty('cells');

			for (var i = 0; i < cells.length; i++) {
				var cell = cells[i];

				if (this.model.isEdge(cell)) {
					var terminal = this.model.getTerminal(cell, true);
					var parent = this.model.getParent(terminal);
					this.model.remove(terminal);
				}
			}
		});

		// Disables drag-and-drop into non-swimlanes.
		graph.isValidDropTarget = function(cell, cells, evt) {
			return this.isSwimlane(cell);
		};

		// Installs a popupmenu handler using local function (see below).
		graph.panningHandler.factoryMethod = function(menu, cell, evt) {
			createPopupMenu(editor, graph, menu, cell, evt);
		};

		// Adds all required styles to the graph (see below)
		configureStylesheet(graph);

		// Adds sidebar icon for the table object
		var tableObject = new Table('TABLENAME');
		var table = new mxCell(tableObject, new mxGeometry(0, 0, 200, 28), 'table');

		table.setVertex(true);
		//table.setConnectable(true);
		table.setConnectable(false);
		//不允许表连接
		addSidebarIcon(graph, sidebar, table, 'images/icons48/table.png');

		// Adds sidebar icon for the column object
		//var columnObject = new Column('COLUMNNAME');
		var columnObject = new Column('数据元1');
		var column = new mxCell(columnObject, new mxGeometry(0, 0, 0, 26));

		column.setVertex(true);
		//column.setConnectable(false);
		column.setConnectable(true);
		//允许列连接

		addSidebarIcon(graph, sidebar, column, 'images/icons48/column.png');

		// Adds primary key field into table
		var firstColumn = column.clone();

		firstColumn.value.name = 'TABLENAME_ID';
		firstColumn.value.type = 'INTEGER';
		firstColumn.value.primaryKey = true;
		firstColumn.value.autoIncrement = true;

		table.insert(firstColumn);

		// Adds child columns for new connections between tables
		graph.addEdge = function(edge, parent, source, target, index) {
			// Finds the primary key child of the target table
			/*
			 var primaryKey = null;
			 var childCount = this.model.getChildCount(target);

			 for (var i = 0; i < childCount; i++) {
			 var child = this.model.getChildAt(target, i);

			 if (child.value.primaryKey) {
			 primaryKey = child;
			 break;
			 }
			 }

			 if (primaryKey == null) {
			 mxUtils.alert('Target table must have a primary key');
			 return;
			 }

			 this.model.beginUpdate();
			 try {
			 var col1 = this.model.cloneCell(column);
			 col1.value.name = primaryKey.value.name;
			 col1.value.type = primaryKey.value.type;

			 this.addCell(col1, source);
			 source = col1;
			 target = primaryKey;

			 return mxGraph.prototype.addEdge.apply(this, arguments);
			 // "supercall"
			 } finally {
			 this.model.endUpdate();
			 }

			 return null;
			 */
			return mxGraph.prototype.addEdge.apply(this, arguments);
		};

		// Displays useful hints in a small semi-transparent box.
		var hints = document.createElement('div');
		hints.style.position = 'absolute';
		hints.style.overflow = 'hidden';
		hints.style.width = '230px';
		hints.style.bottom = '56px';
		hints.style.height = '86px';
		hints.style.right = '20px';

		hints.style.background = 'black';
		hints.style.color = 'white';
		hints.style.fontFamily = 'Arial';
		hints.style.fontSize = '10px';
		hints.style.padding = '4px';

		mxUtils.setOpacity(hints, 50);

		mxUtils.writeln(hints, '- 拖动一个图标从工具栏到绘图区');
		mxUtils.writeln(hints, '- 双击表格或者列可以编辑该元素');
		//mxUtils.writeln(hints, '- Shift- or Rightclick and drag for panning');
		mxUtils.writeln(hints, '- 移动鼠标到元素上显示提示信息');
		//mxUtils.writeln(hints, '- Click and drag a table to move and connect');
		mxUtils.writeln(hints, '- 按Shift或鼠标右键显示下拉菜单');
		document.body.appendChild(hints);

		// Creates a new DIV that is used as a toolbar and adds
		// toolbar buttons.
		var spacer = document.createElement('div');
		spacer.style.display = 'inline';
		spacer.style.padding = '8px';

		addToolbarButton(editor, toolbar, 'properties', '属性', 'images/properties.gif');

		// Defines a new export action
		editor.addAction('properties', function(editor, cell) {
			if (cell == null) {
				cell = graph.getSelectionCell();
			}

			if (graph.isHtmlLabel(cell)) {
				
				showProperties(graph, cell);
			} else if (cell.value instanceof Table) {
				showProperties(graph, cell);
			}
		});

		addToolbarButton(editor, toolbar, 'delete', '删除', 'images/delete2.png');

		toolbar.appendChild(spacer.cloneNode(true));

		addToolbarButton(editor, toolbar, 'undo', '', 'images/undo.png');
		addToolbarButton(editor, toolbar, 'redo', '', 'images/redo.png');

		toolbar.appendChild(spacer.cloneNode(true));

		addToolbarButton(editor, toolbar, 'show', '预览', 'images/camera.png');
		addToolbarButton(editor, toolbar, 'print', '打印', 'images/printer.png');

		toolbar.appendChild(spacer.cloneNode(true));

		// Defines a create SQK action
		editor.addAction('showSql', function(editor, cell) {
			var sql = createSql(graph);

			if (sql.length > 0) {
				var textarea = document.createElement('textarea');
				textarea.style.width = '400px';
				textarea.style.height = '400px';

				textarea.value = sql;
				showModalWindow('SQL', textarea, 410, 440);
			} else {
				mxUtils.alert('Schema is empty');
			}
		});

		// Defines a new export action
		editor.addAction('createGears', function(editor, cell) {
			var sql = createSql(graph);

			if (sql.length > 0) {
				loadGoogleGears();

				try {
					var db = google.gears.factory.create('beta.database', '1.0');
					var name = mxUtils.prompt('Enter name of new database', 'MyDatabase');

					if (name != null) {
						db.open(name);

						// Checks if database is empty
						var rs = db.execute('SELECT * FROM sqlite_master');

						if (rs.isValidRow()) {
							if (mxUtils.confirm(name + ' exists. Do you want to continue? This will replace existing tables.') == 0) {
								return;
							}
						}

						try {
							db.execute(sql);
							mxUtils.alert(name + ' successfully created');
						} catch (e) {
							mxUtils.alert('SQL Error: ' + e.message);
						}
					}
				} catch (e) {
					mxUtils.alert('Google Gears is not available: ' + e.message);
				}
			} else {
				mxUtils.alert('Schema is empty');
			}
		});

		addToolbarButton(editor, toolbar, 'showSql', '显示SQL', 'images/export1.png');
		//addToolbarButton(editor, toolbar, 'createGears', 'Create in Google Gears', 'images/export1.png');

		// Defines export XML action
		editor.addAction('export', function(editor, cell) {
			var textarea = document.createElement('textarea');
			textarea.style.width = '400px';
			textarea.style.height = '400px';
			var enc = new mxCodec(mxUtils.createXmlDocument());
			var node = enc.encode(editor.graph.getModel());
			textarea.value = mxUtils.getPrettyXml(node);
			showModalWindow('XML', textarea, 410, 440);
		});

		addToolbarButton(editor, toolbar, 'export', '导出XML', 'images/export1.png');
		toolbar.appendChild(spacer.cloneNode(true));
		addToolbarButton(editor, toolbar, 'open', '打开', 'images/open.gif');
		editor.addAction('open', function(editor, cell) {
			//editor.open(mxUtils.prompt('Enter filename', 'workflow.xml'));
			//alert(cell);
			//$.messager.alert('提示',"用户不能为空");
			if (!$u.isEmpty(data_define_filename)) {
				//alert(flow_define_base+flow_define_filename);
				try {
					editor.open(data_define_base + data_define_filename);
				} catch(e) {

				}

			} else {
				var win = $win.open('metadata_management/fileselect.js', 'newwin', {
					title : '请选择',
					width : '300',
					height : '220',
					collapsible : false,
					minimizable : false,
					maximizable : false,
					closed : false
				});
			}

		});
		addToolbarButton(editor, toolbar, 'save', '保存', 'images/save.gif');
		editor.addAction('save', function(editor, cell) {
			//var enc = new mxCodec(mxUtils.createXmlDocument());
			//var node = enc.encode(editor.graph.getModel());
			//alert(mxUtils.getPrettyXml(node));
			//editor.open(mxUtils.prompt('Enter filename', 'workflow.xml'));
			//alert(cell);
			//$.messager.alert('提示',"用户不能为空");
			if (!$u.isEmpty(data_define_filename)) {
				//alert(flow_define_base+flow_define_filename);

				var enc = new mxCodec();
				var node = enc.encode(editor.graph.getModel());
				var t_source = mxUtils.getPrettyXml(node);
				$u.ajax.ajaxdata({
					data : {
						dataconfig : "formeditor/formeditor",
						action : "savefile",
						content : t_source,
						filename : data_define_filename
					}
				}, "saveajax");

			} else {
				var win = $win.open('metadata_management/filesave.js', 'newwin', {
					title : '请填写文件名',
					width : '300',
					height : '220',
					collapsible : false,
					minimizable : false,
					maximizable : false,
					closed : false
				});
				win.setValue({
					s_filename : openfilename
				});
			}

		});
		// Adds toolbar buttons into the status bar at the bottom
		// of the window.
		addToolbarButton(editor, status, 'collapseAll', '折叠', 'images/navigate_minus.png', true);
		addToolbarButton(editor, status, 'expandAll', '展开', 'images/navigate_plus.png', true);

		status.appendChild(spacer.cloneNode(true));

		addToolbarButton(editor, status, 'zoomIn', '', 'images/zoom_in.png', true);
		addToolbarButton(editor, status, 'zoomOut', '', 'images/zoom_out.png', true);
		addToolbarButton(editor, status, 'actualSize', '', 'images/view_1_1.png', true);
		addToolbarButton(editor, status, 'fit', '', 'images/fit_to_size.png', true);

		// Creates the outline (navigator, overview) for moving
		// around the graph in the top, right corner of the window.
		var outln = new mxOutline(graph, outline);

		// Fades-out the splash screen after the UI has been loaded.
		var splash = document.getElementById('splash');
		if (splash != null) {
			try {
				mxEvent.release(splash);
				mxEffects.fadeOut(splash, 100, true);
			} catch (e) {

				// mxUtils is not available (library not loaded)
				splash.parentNode.removeChild(splash);
			}
		}
	}
	if (!$u.isEmpty(data_define_filename)) {
		//alert(data_define_base + data_define_filename);
		try {
			editor.open(data_define_base + data_define_filename);
		} catch(e) {

		}

	}
};

function addToolbarButton(editor, toolbar, action, label, image, isTransparent) {
	var button = document.createElement('button');
	button.style.fontSize = '10';
	if (image != null) {
		var img = document.createElement('img');
		img.setAttribute('src', image);
		img.style.width = '16px';
		img.style.height = '16px';
		img.style.verticalAlign = 'middle';
		img.style.marginRight = '2px';
		button.appendChild(img);
	}
	if (isTransparent) {
		button.style.background = 'transparent';
		button.style.color = '#FFFFFF';
		button.style.border = 'none';
	}
	mxEvent.addListener(button, 'click', function(evt) {
		editor.execute(action);
	});
	mxUtils.write(button, label);
	toolbar.appendChild(button);
};

function showModalWindow(title, content, width, height) {
	var background = document.createElement('div');
	background.style.position = 'absolute';
	background.style.left = '0px';
	background.style.top = '0px';
	background.style.right = '0px';
	background.style.bottom = '0px';
	background.style.background = 'black';
	mxUtils.setOpacity(background, 50);
	document.body.appendChild(background);

	if (mxClient.IS_QUIRKS) {
		new mxDivResizer(background);
	}

	var x = Math.max(0, document.body.scrollWidth / 2 - width / 2);
	var y = Math.max(10, (document.body.scrollHeight || document.documentElement.scrollHeight) / 2 - height * 2 / 3);
	var wnd = new mxWindow(title, content, x, y, width, height, false, true);
	wnd.setClosable(true);

	// Fades the background out after after the window has been closed
	wnd.addListener(mxEvent.DESTROY, function(evt) {
		if (content.value != content.originalValue) {
			var odoc = mxUtils.parseXml(content.value);
			var odec = new mxCodec(odoc);
			odec.decode(odoc.documentElement, editor.graph.getModel());
		}

		content.originalValue = null;

		mxEffects.fadeOut(background, 50, true, 10, 30, true);
	});

	wnd.setVisible(true);

	return wnd;
};

function addSidebarIcon(graph, sidebar, prototype, image) {
	// Function that is executed when the image is dropped on
	// the graph. The cell argument points to the cell under
	// the mousepointer if there is one.
	var funct = function(graph, evt, cell) {
		graph.stopEditing(false);

		var pt = graph.getPointForEvent(evt);

		var parent = graph.getDefaultParent();
		var model = graph.getModel();

		var isTable = graph.isSwimlane(prototype);
		var name = null;

		if (!isTable) {
			parent = cell;
			var pstate = graph.getView().getState(parent);

			if (parent == null || pstate == null) {
				mxUtils.alert('Drop target must be a table');
				return;
			}

			pt.x -= pstate.x;
			pt.y -= pstate.y;

			var columnCount = graph.model.getChildCount(parent) + 1;
			name = mxUtils.prompt('Enter name for new column', '数据元' + columnCount);
		} else {
			var tableCount = 0;
			var childCount = graph.model.getChildCount(parent);

			for (var i = 0; i < childCount; i++) {
				if (!graph.model.isEdge(graph.model.getChildAt(parent, i))) {
					tableCount++;
				}
			}

			var name = mxUtils.prompt('Enter name for new table', '数据元集' + (tableCount + 1));
		}

		if (name != null) {
			var v1 = model.cloneCell(prototype);

			model.beginUpdate();
			try {
				if (!isTable) {
					var n = 'COLUMN' + columnCount;
				} else {
					var n = 'TABLE' + (tableCount + 1);
				}

				//v1.value.name = name;
				v1.value.name = n;
				v1.value.label = name;
				v1.geometry.x = pt.x;
				v1.geometry.y = pt.y;

				graph.addCell(v1, parent);

				if (isTable) {
					v1.geometry.alternateBounds = new mxRectangle(0, 0, v1.geometry.width, v1.geometry.height);
					//v1.children[0].value.name = name + '_ID';
					v1.children[0].value.name = 'COLUMN1';
				}
			} finally {
				model.endUpdate();
			}

			graph.setSelectionCell(v1);
		}
	};
	// Creates the image which is used as the sidebar icon (drag source)
	var img = document.createElement('img');
	img.setAttribute('src', image);
	img.style.width = '48px';
	img.style.height = '48px';
	img.title = 'Drag this to the diagram to create a new vertex';
	sidebar.appendChild(img);

	// Creates the image which is used as the drag icon (preview)
	var dragImage = img.cloneNode(true);
	var ds = mxUtils.makeDraggable(img, graph, funct, dragImage);

	// Adds highlight of target tables for columns
	ds.highlightDropTargets = true;
	ds.getDropTarget = function(graph, x, y) {
		if (graph.isSwimlane(prototype)) {
			return null;
		} else {
			var cell = graph.getCellAt(x, y);

			if (graph.isSwimlane(cell)) {
				return cell;
			} else {
				var parent = graph.getModel().getParent(cell);

				if (graph.isSwimlane(parent)) {
					return parent;
				}
			}
		}
	};
};

function configureStylesheet(graph) {
	var style = new Object();
	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_LEFT;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
	style[mxConstants.STYLE_FONTCOLOR] = '#000000';
	style[mxConstants.STYLE_FONTSIZE] = '11';
	style[mxConstants.STYLE_FONTSTYLE] = 0;
	style[mxConstants.STYLE_SPACING_LEFT] = '4';
	style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
	style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
	graph.getStylesheet().putDefaultVertexStyle(style);

	style = new Object();
	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
	style[mxConstants.STYLE_GRADIENTCOLOR] = '#41B9F5';
	style[mxConstants.STYLE_FILLCOLOR] = '#8CCDF5';
	style[mxConstants.STYLE_SWIMLANE_FILLCOLOR] = '#ffffff';
	style[mxConstants.STYLE_STROKECOLOR] = '#1B78C8';
	style[mxConstants.STYLE_FONTCOLOR] = '#000000';
	style[mxConstants.STYLE_STROKEWIDTH] = '2';
	style[mxConstants.STYLE_STARTSIZE] = '28';
	style[mxConstants.STYLE_VERTICAL_ALIGN] = 'middle';
	style[mxConstants.STYLE_FONTSIZE] = '12';
	style[mxConstants.STYLE_FONTSTYLE] = 1;
	style[mxConstants.STYLE_IMAGE] = 'images/icons48/table.png';
	// Looks better without opacity if shadow is enabled
	//style[mxConstants.STYLE_OPACITY] = '80';
	style[mxConstants.STYLE_SHADOW] = 1;
	graph.getStylesheet().putCellStyle('table', style);

	style = graph.stylesheet.getDefaultEdgeStyle();
	style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
	style[mxConstants.STYLE_STROKEWIDTH] = '2';
	style[mxConstants.STYLE_ROUNDED] = true;
	style[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
};

// Function to create the entries in the popupmenu
function createPopupMenu(editor, graph, menu, cell, evt) {
	if (cell != null) {
		if (graph.isHtmlLabel(cell)) {
			menu.addItem('属性', 'images/properties.gif', function() {
				editor.execute('properties', cell);
			});

			menu.addSeparator();
			menu.addItem('删除', 'images/delete2.png', function() {

				editor.execute('delete', cell);
			});
		} else if (cell.value instanceof Table) {
			menu.addItem('属性', 'images/properties.gif', function() {
				editor.execute('properties', cell);
			});

			menu.addSeparator();
			menu.addItem('删除', 'images/delete2.png', function() {

				editor.execute('delete', cell);
			});

		} else if (graph.model.isEdge(cell))//显示连接线的属性
		{
			menu.addItem('属性', 'images/properties.gif', function() {
				editor.execute('properties', cell);
			});

			menu.addSeparator();
			menu.addItem('删除', 'images/delete2.png', function() {

				editor.graph.model.remove(cell);
			});
		}
		/*
		 menu.addItem('删除', 'images/delete2.png', function() {
		 //cell.remove();
		 //cell.removeFromTerminal();
		 //editor.execute('delete', cell);
		 editor.graph.removeCells(null,false);
		 });
		 */
		menu.addSeparator();
	}

	menu.addItem('回退', 'images/undo.png', function() {
		editor.execute('undo', cell);
	});

	menu.addItem('前进', 'images/redo.png', function() {
		editor.execute('redo', cell);
	});

	menu.addSeparator();

	menu.addItem('显示SQL', 'images/export1.png', function() {
		editor.execute('showSql', cell);
	});
};

function showProperties(graph, cell) {
	// Creates a form for the user object inside
	// the cell

	if (cell.value instanceof Table) {
		var form = new mxForm('properties');

		// Adds a field for the columnname
		var nameField = form.addText('数据元集代码', cell.value.name);
		var labelField = form.addText('数据元集名称', cell.value.label || cell.value.name);
		//var typeField = form.addText('Type', cell.value.type);
		var memoField = form.addText('备注', cell.value.memo);

		var wnd = null;

		// Defines the function to be executed when the
		// OK button is pressed in the dialog
		var okFunction = function() {
			var clone = cell.value.clone();

			clone.name = nameField.value;
			clone.label = labelField.value;
			clone.memo = memoField.value;
			//clone.type = typeField.value;

			//if (useDefaultField.checked)
			//{
			//	clone.defaultValue = defaultField.value;
			//}
			//else
			//{
			//	clone.defaultValue = null;
			//}

			//clone.primaryKey = primaryKeyField.checked;
			//clone.autoIncrement = autoIncrementField.checked;
			//clone.notNull = notNullField.checked;
			//clone.unique = uniqueField.checked;

			graph.model.setValue(cell, clone);

			wnd.destroy();
		};

		// Defines the function to be executed when the
		// Cancel button is pressed in the dialog
		var cancelFunction = function() {
			wnd.destroy();
		};
		form.addButtons(okFunction, cancelFunction);

		var parent = graph.model.getParent(cell);
		//var name = parent.value.name + '.' + cell.value.name;
		if (cell.value.label) {
			var name = cell.value.label;
		} else {
			var name = cell.value.name;
		}

		wnd = showModalWindow(name, form.table, 220, 144);
	} else {
		/*
		 var win = $win.OpenForm("metadata_management/dataelementform.js", "newwin", {
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
		 }, {
		 graph : graph,
		 cell : cell
		 });
		 var o = $u.apply({}, cell.value);
		 o.dataelement_code = cell.value.name;
		 o.dataelement_name = cell.value.label || cell.value.name;
		 o.dataelement_type = cell.value.type;
		 o.dataelement_primary = cell.value.primaryKey;
		 o.dataelement_increasing = cell.value.autoIncrement;
		 o.dataelement_nullvalue = cell.value.notNull;
		 o.dataelement_unique = cell.value.unique;
		 o.dataelement_defaultvalue = cell.value.defaultValue || '';
		 o.dataelement_note = cell.value.memo;
		 win.setValue(o);
		 */
		var form = new mxForm('properties');
		// Adds a field for the columnname

		var nameField = form.addText('数据元代码', cell.value.name);
		var labelField = form.addText('数据元名称', cell.value.label || cell.value.name);
		var typeField = form.addText('数据元类型', cell.value.type);
		var primaryKeyField = form.addCheckbox('主键', cell.value.primaryKey);
		var autoIncrementField = form.addCheckbox('自动增长', cell.value.autoIncrement);
		var notNullField = form.addCheckbox('非空', cell.value.notNull);
		var uniqueField = form.addCheckbox('唯一', cell.value.unique);
		var defaultField = form.addText('缺省值', cell.value.defaultValue || '');
		var useDefaultField = form.addCheckbox('使用缺省值', (cell.value.defaultValue != null));
		var memoField = form.addText('备注', cell.value.memo);

		//备注
		/*
		 var nameField = form.addText('Name', cell.value.name);
		 var typeField = form.addText('Type', cell.value.type);

		 var primaryKeyField = form.addCheckbox('Primary Key', cell.value.primaryKey);
		 var autoIncrementField = form.addCheckbox('Auto Increment', cell.value.autoIncrement);
		 var notNullField = form.addCheckbox('Not Null', cell.value.notNull);
		 var uniqueField = form.addCheckbox('Unique', cell.value.unique);

		 var defaultField = form.addText('Default', cell.value.defaultValue || '');
		 var useDefaultField = form.addCheckbox('Use Default', (cell.value.defaultValue != null));
		 */

		var wnd = null;

		// Defines the function to be executed when the
		// OK button is pressed in the dialog
		var okFunction = function() {
			var clone = cell.value.clone();
			clone.label = labelField.value;
			//数据元名称
			clone.name = nameField.value;
			clone.type = typeField.value;
			clone.memo = memoField.value;

			if (useDefaultField.checked) {
				clone.defaultValue = defaultField.value;
			} else {
				clone.defaultValue = null;
			}

			clone.primaryKey = primaryKeyField.checked;
			clone.autoIncrement = autoIncrementField.checked;
			clone.notNull = notNullField.checked;
			clone.unique = uniqueField.checked;

			graph.model.setValue(cell, clone);

			wnd.destroy();
		};
		// Defines the function to be executed when the
		// Cancel button is pressed in the dialog
		var cancelFunction = function() {
			wnd.destroy();
		};
		form.addButtons(okFunction, cancelFunction);

		var parent = graph.model.getParent(cell);
		var name = parent.value.name + '.' + cell.value.name;
		wnd = showModalWindow(name, form.table, 220, 310);

	}
};

function createSql(graph) {
	var sql = [];
	var parent = graph.getDefaultParent();
	var childCount = graph.model.getChildCount(parent);

	for (var i = 0; i < childCount; i++) {
		var child = graph.model.getChildAt(parent, i);

		if (!graph.model.isEdge(child)) {
			sql.push('CREATE TABLE IF NOT EXISTS ' + child.value.name + ' (');

			var columnCount = graph.model.getChildCount(child);

			if (columnCount > 0) {
				for (var j = 0; j < columnCount; j++) {
					var column = graph.model.getChildAt(child, j).value;

					sql.push('\n    ' + column.name + ' ' + column.type);

					if (column.notNull) {
						sql.push(' NOT NULL');
					}

					if (column.primaryKey) {
						sql.push(' PRIMARY KEY');
					}

					if (column.autoIncrement) {
						sql.push(' AUTOINCREMENT');
					}

					if (column.unique) {
						sql.push(' UNIQUE');
					}

					if (column.defaultValue != null) {
						sql.push(' DEFAULT ' + column.defaultValue);
					}

					sql.push(',');
				}

				sql.splice(sql.length - 1, 1);
				sql.push('\n);');
			}

			sql.push('\n');
		}
	}

	return sql.join('');
};

function loadGoogleGears() {
	// We are already defined. Hooray!
	if (window.google && google.gears) {
		return;
	}

	var factory = null;

	// Firefox
	if ( typeof GearsFactory != 'undefined') {
		factory = new GearsFactory();
	} else {
		// IE
		try {
			factory = new ActiveXObject('Gears.Factory');
		} catch (e) {
			// Safari
			if (navigator.mimeTypes["application/x-googlegears"]) {
				factory = document.createElement("object");
				factory.style.display = "none";
				factory.width = 0;
				factory.height = 0;
				factory.type = "application/x-googlegears";
				document.documentElement.appendChild(factory);
			}
		}
	}

	// *Do not* define any objects if Gears is not installed. This mimics the
	// behavior of Gears defining the objects in the future.
	if (!factory) {
		return;
	}

	// Now set up the objects, being careful not to overwrite anything.
	if (!window.google) {
		window.google = {};
	}

	if (!google.gears) {
		google.gears = {
			factory : factory
		};
	}
};

// Defines the column user object
function Column(name) {
	this.name = name;
	this.label = name;
	this.memo = "";
};

Column.prototype.type = 'TEXT';

Column.prototype.defaultValue = null;

Column.prototype.primaryKey = false;

Column.prototype.autoIncrement = false;

Column.prototype.notNull = false;

Column.prototype.unique = false;

Column.prototype.clone = function() {
	return mxUtils.clone(this);
};

// Defines the table user object
function Table(name) {
	this.name = name;
	this.memo = "";
};

Table.prototype.clone = function() {
	return mxUtils.clone(this);
};

