<%@page import="com.hxy.isw.entity.AccountInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	
			<span style="font-size: 20px">累计推广用户：</span><span id="totaluser" style="font-size: 16px"></span><br>
			<span style="font-size: 20px">累计总收益：</span><span id="totalrecharge" style="font-size: 16px"></span>
			<span style="font-size: 20px;margin-left: 30px;">累积净收益：</span><span id="totalincome" style="font-size: 16px"></span>
</div><!-- /.page-header -->
		
					<input style="width:150px" class="form_date" readonly="readonly" placeholder="起始日期" type="text" value=""  id="starttime">
					<span style="width:40px;display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
			        <select id="type">
			          <option value="2">按月统计</option>
			          <option value="1">按年统计</option>
			          <option value="3">按日统计</option>  
			        </select>
			
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querywalletstatistic()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
				
				<span style="margin-left: 150px;font-size: 18px;">本日总收益：</span> <span id="todayout" style="font-size: 16px"></span>
				 
				<span style="margin-left: 20px;font-size: 18px;">本日净收益：</span> <span id="todayin" style="font-size: 16px"></span>
				
	<div class="panel-body" style="text-align:center;">
		<div class="col-sm-7 infobox-container" id="echarts_walletstatistic"  style="width: 100%;height:600px;"></div>
	</div>

<script>

$._ajax({
	url: Q.url+"/statistic/queryuserandmoney.action",
	type:'post',
	dataType: "json",
	success: function(data){
		$("#totaluser").html(data.countusers+"人");
		$("#totalrecharge").html(data.countmoney+"元");
		$("#totalincome").html(data.myinmoney+"元");
		$("#todayout").html(data.todaymoney+"元");
		$("#todayin").html(data.todayinmoney+"元");
		
	}
	
})


var myChart_transportlog = echarts.init(document.getElementById('echarts_walletstatistic'));


querywalletstatistic();
function querywalletstatistic(){
	var starttime = $("#starttime").val();
	var type = $("#type").val();
	var countxAxis = [];
	var countyAxis1 = [];
	
	
	$._ajax({
		url: Q.url+"/statistic/querycompanywalletstatistic.action",
		type:'post',
		dataType: "json",
		data:{"starttime":starttime,"type":type},
		success: function(data){
			if( typeof(data) == 'object'){
	
				for(var i = 0;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].amount);
				}
				var mydata='';
				if(type=='1'){
					mydata='历年';
				}else if(type=='2'){
					if(starttime==''){
						mydata=new Date().getFullYear()+'年';
					}else{
						mydata=starttime+'年';
					}
				}else{
					if(starttime==''){
						mydata=new Date().getFullYear()+"-"+(new Date().getMonth()+1)+'月';
					}else{
						mydata=starttime+'月';
					}
				}
				if(data.role==2||data.role==3){
					mydata+="公司净收益走势图";
				}else{
					mydata+="我的净收益走势图";
				}
				month = /* parseInt(starttime.split("-")[1]); */"";
				// 使用刚指定的配置项和数据显示图表。
			    myChart_transportlog.setOption(getoption_transportlogreport(mydata,countxAxis,countyAxis1));
			}
		}
	});
	
	
    
   
}

//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_transportlogreport(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: month,
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['资金净收益']
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
 	            name:'资金净收益',
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

var myformat="yyyy";
var myminView=4;
var mystartView=4;



$('#type').on('change',function(){
	var type=$("#type").val();
	if(type=='1'){
		$(".form_date").datetimepicker("remove");
		$("#starttime").val("");
	}else if(type=='2'){
		myformat="yyyy";
		myminView=4;
		mystartView=4;
		$(".form_date").datetimepicker("remove");
		$("#starttime").val("");
		$(".form_date").datetimepicker({
			language:  'zh-CN',
		    format: myformat,
		    autoclose: true,
		    todayBtn: false,
		    pickerPosition: "bottom-left",
		    weekStart: 0,
		    todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView:myminView,
			minView :mystartView,
			forceParse: 0
		});
	}else{
		myformat="yyyy-MM";
		myminView=3;
		mystartView=3;
		$(".form_date").datetimepicker("remove");
		$("#starttime").val("");
		$(".form_date").datetimepicker({
			language:  'zh-CN',
		    format: myformat,
		    autoclose: true,
		    todayBtn: false,
		    pickerPosition: "bottom-left",
		    weekStart: 0,
		    todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView:myminView,
			minView :mystartView,
			forceParse: 0
		});
	}
		
})

$(".form_date").datetimepicker({
			language:  'zh-CN',
		    format: myformat,
		    autoclose: true,
		    todayBtn: false,
		    pickerPosition: "bottom-left",
		    weekStart: 0,
		    todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView:myminView,
			minView :mystartView,
			forceParse: 0
		});

 
</script>		  