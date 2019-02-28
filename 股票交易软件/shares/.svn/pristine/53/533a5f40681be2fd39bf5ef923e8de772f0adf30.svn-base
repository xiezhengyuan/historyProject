<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	
			<span style="font-size: 20px">累计推广用户：</span><span id="totalusers" style="font-size: 16px"></span>
			<span style="font-size: 20px">今日新增用户：</span><span id="todayusers" style="font-size: 16px"></span>
</div><!-- /.page-header -->
		
					<input style="width:150px" class="form_date" readonly="readonly" placeholder="起始日期" type="text" value=""  id="starttime1">
					<span style="width:40px;display:inline" class="input-group-addon " ><span class="glyphicon glyphicon-calendar"></span></span>
			        <select id="type1">
			          <option value="2">按月统计</option>
			          <option value="1">按年统计</option>
			          <option value="3">按日统计</option>  
			        </select>
			
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querywalletstatistic1()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
				
				<span style="margin-left: 100px;font-size: 18px;">本月推广用户：</span> <span id="monthusers" style="font-size: 16px"></span>
				
	<div class="panel-body" style="text-align:center;">
		<div class="col-sm-7 infobox-container" id="echarts_walletstatistic1"  style="width: 100%;height:600px;"></div>
	</div>

<script>

$._ajax({
	url: Q.url+"/statistic/querymyadduser.action",
	type:'post',
	dataType: "json",
	success: function(data){
		$("#totalusers").html(data.allusers+"人");
		$("#todayusers").html(data.todayusers+"人");
		$("#monthusers").html(data.monthusers+"人");
		
		
	}
	
})

var myChart_transportlog1 = echarts.init(document.getElementById('echarts_walletstatistic1'));


querywalletstatistic1();

function querywalletstatistic1(){
	var starttime = $("#starttime1").val();
	var type = $("#type1").val();
	var countxAxis = [];
	var countyAxis1 = [];
	
	
	$._ajax({
		url: Q.url+"/statistic/queryusersstatistic.action",
		type:'post',
		dataType: "json",
		data:{"starttime":starttime,"type":type},
		success: function(data){
		
			if( typeof(data) == 'object'){
	
				for(var i = 0;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = data.rows[i].amount;
				}
			
				var data='';
				if(type=='1'){
					data='历年';
				}else if(type=='2'){
					if(starttime==''){
						data=new Date().getFullYear()+'年';
					}else{
						data=starttime+'年';
					}
				}else{
					if(starttime==''){
						data=new Date().getFullYear()+"-"+(new Date().getMonth()+1)+'月';
					}else{
						data=starttime+'月';
					}
				}
				if(data.role==2||data.role==3){
					data+="公司推广用户走势图";
				}else{
					data+="我的推广用户走势图";
				}
				// 使用刚指定的配置项和数据显示图表。
			    myChart_transportlog1.setOption(getoption_transportlogreport1(data,countxAxis,countyAxis1));
			}
		}
	});
	
	
    
   
}

//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_transportlogreport1(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: month,
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

var myformat="yyyy";
var myminView=4;
var mystartView=4;



$('#type1').on('change',function(){
	var type=$("#type1").val();
	if(type=='1'){
		$(".form_date").datetimepicker("remove");
		$("#starttime1").val("");
	}else if(type=='2'){
		myformat="yyyy";
		myminView=4;
		mystartView=4;
		$(".form_date").datetimepicker("remove");
		$("#starttime1").val("");
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
		$("#starttime1").val("");
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