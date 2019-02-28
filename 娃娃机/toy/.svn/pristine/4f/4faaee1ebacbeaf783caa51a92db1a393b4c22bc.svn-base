<%@ page language="java" isErrorPage="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="page-header">
	<h1>
		数据
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			统计
		</small>
	</h1>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12">
		<div class="modal-header" style="height: 80px;">
			<div class="col-sm-10" style="width:15%;float:left">
				<div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd hh:ii:ss">
					<input class="form-control" placeholder="起始日期" type="text" value=""  id="starttime">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			
			<div class="col-sm-10" style="width:15%;float:left">
				<div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd hh:ii:ss">
					<input class="form-control" placeholder="结束日期" type="text" value=""  id="endtime">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
			
			<div class="col-sm-10" style="width:5%;float:left">
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querybytime()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>
			
			<!-- <div style="float:right;">
				<button id="returnbefore" class="btn btn-purple btn-sm" onclick="gotoback()" data-placement="top" title="返回">
					返回
				</button> 
			</div> -->
		</div>
	</div>
	<div class="panel-body" style="text-align:center;">
	
		<div class="col-sm-7 infobox-container" id="echarts_userstatistic"  style="width: 100%;height:500px;"></div>
		<p>系统总用户<font id="totaluser">0</font>人，今日活跃用户<font id="actives">0</font>人</p>
		<hr>
		
		<div class="col-sm-7 infobox-container" id="echarts_toystatistic"  style="width: 100%;height:500px;"></div>
		<p>娃娃机抓取率<font id="totaltoy">0</font></p>
		<hr>
		
	</div>
	
	
</div>
    
<script type="text/javascript">
    
   var mydate = new Date();

  //var enddate = new Date(Date._parse(mydate)).addDays(-1);
  var enddate = mydate;
  var mm = "";
  var dd = "";
  if(enddate.getMonth()<9){
  	 mm = 0+""+(enddate.getMonth()+1);
  }else{
  	 mm = (enddate.getMonth()+1);
  }

  if(enddate.getDate()<10){
  	 dd = 0+""+enddate.getDate();
  }else{
  	 dd = enddate.getDate();
  }



  $("#endtime").val(enddate.getFullYear()+"-"+mm+"-"+dd);
  $("#starttime").val(enddate.getFullYear()+"-"+mm+"-01");

  $("#starttime").change(function(){
  	var time_start = $("#starttime").val();
  	var time_end = $("#endtime").val();
  	
  	if(time_end!=null&&time_end.length>0){
  		if(time_start.split("-")[0]!=time_end.split("-")[0]||time_start.split("-")[1]!=time_end.split("-")[1]){
  			if(time_start.split("-")[1]=='02')$("#endtime").val(time_start.split("-")[0]+"-"+time_start.split("-")[1]+"-28");
  			else $("#endtime").val(time_start.split("-")[0]+"-"+time_start.split("-")[1]+"-30");
  		}
  	}
  })

  $("#endtime").change(function(){
  	var time_start = $("#starttime").val();
  	var time_end = $("#endtime").val();
  	
  	if(time_end!=null&&time_end.length>0){
  		if(time_start.split("-")[0]!=time_end.split("-")[0]||time_start.split("-")[1]!=time_end.split("-")[1]){
  			$("#starttime").val(time_end.split("-")[0]+"-"+time_end.split("-")[1]+"-01");
  		}
  	}
  })

  var myChart_userlog = echarts.init(document.getElementById('echarts_userstatistic'));
  var myChart_toylog = echarts.init(document.getElementById('echarts_toystatistic'));
 
  
  querybytime();
  
function querybytime(){
	
	 queryuserstatistic();
	 querytoystatistic();
}

/*****************************************用户统计*************************************************************/
function queryuserstatistic(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	
	
	var countxAxis = [];
	var countyAxis1 = [];
	
	
	$._ajax({
		url: Q.url+"/statistic/queryuserstatistic.action",
		data:{"starttime":starttime,"endtime":endtime},
		success: function(data){
			
			if( typeof(data) == 'object'){
	
				for(var i = 0;i<data.rows.length;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseInt(data.rows[i].nums);
				}
				
				month = parseInt(endtime.split("-")[1]);
				
				$('#totaluser').html(data.totaluser);
				/* $('#actives').html(data.actives); */
				// 使用刚指定的配置项和数据显示图表。
			    myChart_userlog.setOption(getoption_userlog(month,countxAxis,countyAxis1));
			}
		}
	});
   
}

//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_userlog(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: "抓抓乐"+month+"月份用户指数图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['每日新增']
		    },
		    toolbox: {
		        show : false,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    color:['#fd8760'],
		    backgroundColor:'#fff',
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : countxAxis
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        
		        {
 	            name:'每日新增',
 	            symbol:'none',  //这句就是去掉点的  
 	            type:'line',  
 	            smooth:true,  //这句就是让曲线变平滑的 
 	            data:countyAxis1/* ,
 	            markPoint : {
 	                data : [
 	                    {type : 'max', name: '最大值'},
 	                    {type : 'min', name: '最小值'}
 	                ]
 	            },
 	            markLine : {
 	                data : [
 	                    {type : 'average', name : '平均值'}
 	                ]
 	            } */
 	        }
		    ]
		};
		return option;
	}
/*****************************************用户统计*************************************************************/

/*****************************************娃娃抓取统计*************************************************************/
function querytoystatistic(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	
	
	var countxAxis = [];
	var countyAxis1 = [];
	var countyAxis2 = [];
	
	
	$._ajax({
		url: Q.url+"/statistic/querytoystatistic.action",
		data:{"starttime":starttime,"endtime":endtime},
		success: function(data){
			
			if( typeof(data) == 'object'){
	
				for(var i = 0;i<data.rows.length;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].totalplay).toFixed(2);
					countyAxis2[countyAxis2.length] = parseFloat(data.rows[i].totalsuccess).toFixed(2);
				}
				
				month = parseInt(endtime.split("-")[1]);
				//countyAxis1 = [12,54,35,33,1,5,57,23,43,21,12,54,35,33,1,5,57,23,43,21,21,12,54,35,33];
				//countyAxis2 = [2,4,5,23,12,15,7,3,3,1,2,4,5,23,12,15,7,3,3,1,9,1,2,4,5,23];
				$('#totaltoy').html(data.playrate.toFixed(2));
				// 使用刚指定的配置项和数据显示图表。
			   myChart_toylog.setOption(getoption_toylog(month,countxAxis,countyAxis1,countyAxis2));
			}
		}
	});
   
}

//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_toylog(month,countxAxis,countyAxis1,countyAxis2){
	var option = {
		    title : {
		        text: "抓抓乐"+month+"月份抓取率指数图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['每日总抓取数','每日抓取成功']
		    },
		    toolbox: {
		        show : false,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    color:['#90EE90','#FF00FF'],
		    backgroundColor:'#fff',
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : countxAxis
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        
		        {
 	            name:'每日总抓取数',
 	            symbol:'none',  //这句就是去掉点的  
 	            type:'line',  
 	            smooth:true,  //这句就是让曲线变平滑的 
 	            data:countyAxis1/* ,
 	            markPoint : {
 	                data : [
 	                    {type : 'max', name: '最大值'},
 	                    {type : 'min', name: '最小值'}
 	                ]
 	            },
 	            markLine : {
 	                data : [
 	                    {type : 'average', name : '平均值'}
 	                ]
 	            } */
 	        },
 	       {
 	            name:'每日抓取成功',
 	            symbol:'none',  //这句就是去掉点的  
 	            type:'line',  
 	            smooth:true,  //这句就是让曲线变平滑的 
 	            data:countyAxis2/* ,
 	            markPoint : {
 	                data : [
 	                    {type : 'max', name: '最大值'},
 	                    {type : 'min', name: '最小值'}
 	                ]
 	            },
 	            markLine : {
 	                data : [
 	                    {type : 'average', name : '平均值'}
 	                ]
 	            } */
 	        }
		    ]
		};
		return option;
	}
/*****************************************芝麻统计*************************************************************/

</script>

<script>
$(".form_date").datetimepicker({
	language:  'zh-CN',
    format: 'yyyy-MM-dd',
    autoclose: true,
    todayBtn: false,
    pickerPosition: "bottom-left",
    weekStart: 0,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView :2,
	forceParse: 0
}); 
</script>		  