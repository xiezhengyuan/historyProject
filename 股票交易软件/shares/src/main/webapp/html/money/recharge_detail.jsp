<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<div style="margin-top: -20px;margin-bottom: -15px">
			<span style="font-size: 15px">累计充值：</span><span id="totalrecharge"></span>
			</div>
</div><!-- /.page-header -->

		<div class="modal-header" style="height: 80px;">
			<div class="col-sm-10" style="width:15%;float:left">
                <div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd ">
					<input class="form-control" placeholder="选择日期" type="text" value=""  id="starttime">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
		
			
			<div class="col-sm-10" style="width:5%;float:left">
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="querycashstatistic()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>
			
			<div class="col-sm-10" style="width:30%;">
				<span style="font-size: 15px"></span><span id="thisrecharge"></span>
				<span style="font-size: 15px"></span><span id="thisincome"></span>
			</div>
			
		<!-- 	<div style="float:right;">
				<button id="returnbefore" class="btn btn-purple btn-sm" onclick="gotoback()" data-placement="top" title="返回">
					返回
				</button> 
			</div> -->
		</div>
	
	<div class="panel-body" style="text-align:center;">
		<div class="col-sm-7 infobox-container" id="echarts_walletstatistic"  style="width: 100%;height:600px;"></div>
	</div>
	
	

<script>

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



$("#starttime").val(enddate.getFullYear()+"-"+mm+"-01");

$("#starttime").change(function(){
	var time_start = $("#starttime").val();
	/* var time_end = $("#endtime").val(); */
	
	/* if(time_end!=null&&time_end.length>0){
		if(time_start.split("-")[0]!=time_end.split("-")[0]||time_start.split("-")[1]!=time_end.split("-")[1]){
			if(time_start.split("-")[1]=='02')$("#endtime").val(time_start.split("-")[0]+"-"+time_start.split("-")[1]+"-28");
			else $("#endtime").val(time_start.split("-")[0]+"-"+time_start.split("-")[1]+"-30");
		}
	} */
})


function gotoback(){
	var from = "wallet/walletmanager.jsp";
	Q.viewJsp(Q.page_content,from);
}

var myChart_transportlog = echarts.init(document.getElementById('echarts_walletstatistic'));


querycashstatistic();

function querycashstatistic(){
	var starttime = $("#starttime").val();
	var type = $("#types").val();
	var countxAxis = [];
	var countyAxis1 = [];
	
	
	$._ajax({
		url: Q.url+"/money/querycashstatistic.action",
		data:{"starttime":starttime},
		success: function(data){
			if( typeof(data) == 'object'){
				$("#totalrecharge").html(data.rows[0].totalrecharge);
				
				for(var i = 1;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].amount);
				}
				month = /* parseInt(starttime.split("-")[1]); */"";
				// 使用刚指定的配置项和数据显示图表。
			    myChart_transportlog.setOption(getoption_transportlogreport(month,countxAxis,countyAxis1));
			}
		}
	});
	
	
    
   
}

//基于准备好的dom，初始化echarts实例

//指定图表的配置项和数据
function getoption_transportlogreport(month,countxAxis,countyAxis1){
	var option = {
		    title : {
		        text: "股票"+month+"充值走势图",
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['充值金额']
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
 	            name:'充值金额',
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
	startView: 3,
    minView :3, 
	forceParse: 0
}); 
</script>		  