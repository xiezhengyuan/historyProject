//resize to fit page size
$(window).on('resize.jqGrid', function () {
	$("#"+Q.grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
})
//resize on sidebar collapse/expand
var parent_column = $("#"+Q.grid_selector).closest('[class*="col-"]');
$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
	if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
		//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
		setTimeout(function() {
			$("#"+Q.grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
		}, 0);
	}
})

//if your grid is inside another element, for example a tab pane, you should use its parent's width:
/**
$(window).on('resize.jqGrid', function () {
	var parent_width = $(grid_selector).closest('.tab-pane').width();
	$(grid_selector).jqGrid( 'setGridWidth', parent_width );
})
//and also set width when tab pane becomes visible
$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
  if($(e.target).attr('href') == '#mygrid') {
	var parent_width = $(grid_selector).closest('.tab-pane').width();
	$(grid_selector).jqGrid( 'setGridWidth', parent_width );
  }
})
*/

$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size



$(document).one('ajaxloadstart.page', function(e) {
	$("#"+Q.grid_selector).jqGrid('GridUnload');
	$('.ui-jqdialog').remove();
});

				
//$(grid_selector).closest("div.ui-jqgrid-view").children("div.ui-jqgrid-titlebar").children("span.ui-jqgrid-title").css("background-color", "goldenrod");
				
			