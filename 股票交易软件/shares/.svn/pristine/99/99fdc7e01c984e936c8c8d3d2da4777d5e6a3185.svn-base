<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
<p>在线人数：（PC：，移动端：）股票下单：外汇下单：入金：出金：</p>
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<div class="row">
									<!-- <div class="space-6"></div> -->
<div class="panel panel-default" >
<div class="panel-body" style="text-align:center;background-color:#f5f5f5">

<div class="col-sm-7 infobox-container" id="echarts_userlog"  style="width: 100%;height:438px;">
<!-- 用户统计图 -->
</div>


</div>
</div>

<div class="panel panel-default" >
<div class="panel-body" style="text-align:center;background-color:#f5f5f5">
<div class="col-sm-7 infobox-container" id="echarts_incomelog"  style="width: 50%;height:438px;">
	<!--入金 统计图 -->
</div>

<div class="col-sm-7 infobox-container" id="echarts_paylog"  style="width: 50%;height:438px;">
	<!--出金 统计图 -->
</div>
</div>
</div>

</div>


<script type="text/javascript">
var myChart_userlog = echarts.init(document.getElementById('echarts_userlog'));
queryuser();

function queryuser(){
	var countxAxis = [];
	var countyAxis1 = [];
	
	$._ajax({
		url: Q.url+"/statistic/queryuser.action",
		data:{},
		success: function(data){
			if( typeof(data) == 'object'){
		/* 		$("#totaluser").html(data.rows[0].totaluser);
				$("#newadd").html(data.rows[0].newadd);
				var htm = '';
				if(type==1){
					htm ="本年推广用户："
				}
				if(type==2){
					htm ="本月推广用户：";
				}
				if(type==3){
					htm ="本日推广用户：";
				}
				$("#thisadd").html(htm+data.rows[0].thisadd); */
				
				for(var i = 0;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].nums);
				}
				var date = new Date();
				month = date.getMonth() + 1;
				// 使用刚指定的配置项和数据显示图表。
			    myChart_userlog.setOption(getoption_userlogreport(month,countxAxis,countyAxis1));
			}
		}
	});
   
}
//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_userlogreport(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: "股票"+month+"月推广用户走势图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['推广用户']
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
	            name:'推广用户',
	            symbol:'none',  //这句就是去掉点的  
	            type:'line',  
	            smooth:true,  //这句就是让曲线变平滑的 
	            data:countyAxis1,
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
	            }
	        }
		    ]
		};
		return option;
	}
</script>

<script type="text/javascript">
var myChart_incomelog = echarts.init(document.getElementById('echarts_incomelog'));
queryincome();

function queryincome(){
	var countxAxis = [];
	var countyAxis1 = [];
	
	$._ajax({
		url: Q.url+"/statistic/queryincome.action",
		data:{},
		success: function(data){
			if( typeof(data) == 'object'){
				
				for(var i = 0;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].nums);
				}
				var date = new Date();
				month = date.getMonth() + 1;
				// 使用刚指定的配置项和数据显示图表。
			    myChart_incomelog.setOption(getoption_incomelogreport(month,countxAxis,countyAxis1));
			}
		}
	});
   
}
//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_incomelogreport(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: "股票"+month+"月入金走势图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['入金金额']
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
	            name:'入金金额',
	            symbol:'none',  //这句就是去掉点的  
	            type:'line',  
	            smooth:true,  //这句就是让曲线变平滑的 
	            data:countyAxis1,
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
	            }
	        }
		    ]
		};
		return option;
	}
</script>

<script type="text/javascript">
var myChart_paylog = echarts.init(document.getElementById('echarts_paylog'));
querypay();

function querypay(){
	var countxAxis = [];
	var countyAxis1 = [];
	
	$._ajax({
		url: Q.url+"/statistic/querycash.action",
		data:{},
		success: function(data){
			if( typeof(data) == 'object'){
				
				for(var i = 0;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].nums);
				}
				var date = new Date();
				month = date.getMonth() + 1;
				// 使用刚指定的配置项和数据显示图表。
			    myChart_paylog.setOption(getoption_paylogreport(month,countxAxis,countyAxis1));
			}
		}
	});
   
}
//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_paylogreport(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: "股票"+month+"月出金走势图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['出金金额']
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
	            name:'出金金额',
	            symbol:'none',  //这句就是去掉点的  
	            type:'line',  
	            smooth:true,  //这句就是让曲线变平滑的 
	            data:countyAxis1,
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
	            }
	        }
		    ]
		};
		return option;
	}
</script>