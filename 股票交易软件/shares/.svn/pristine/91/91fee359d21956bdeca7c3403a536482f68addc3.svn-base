<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<div style="margin-top: -20px;margin-bottom: -15px">
			<span style="font-size: 15px">累计推广用户：</span><span id="totalusers"></span>
			<span style="font-size: 15px">今日新增用户：</span><span id="newadd"></span>
			</div>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12">
		<div class="modal-header" style="height: 80px;">
			<div class="col-sm-10" style="width:15%;float:left">
                <div class="input-group date form_date" data-date=""  data-date-format="yyyy-MM-dd hh:ii:ss">
					<input class="form-control" placeholder="选择日期" type="text" value=""  id="endtime">
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>
		   <div style="width: 15%;float:left">
		   <select type="text" id="types" >
			<option  value="1" selected>按年统计</option>
			<option value="2" >按月统计</option>
			<option value="3" >按日统计</option>
			</select>
		   </div>
			
			<div class="col-sm-10" style="width:5%;float:left">
				<button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="查询" onclick="queryuserstatistic()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>
			
			<div class="col-sm-10" style="width:30%;">
				<span style="font-size: 15px"></span><span id="thisadd"></span>
			</div>
			
		<!-- 	<div style="float:right;">
				<button id="returnbefore" class="btn btn-purple btn-sm" onclick="gotoback()" data-placement="top" title="返回">
					返回
				</button> 
			</div> -->
		</div>
	</div>
	<div class="panel-body" style="text-align:center;">
		<div class="col-sm-7 infobox-container" id="echarts_userstatistic"  style="width: 100%;height:600px;"></div>
	</div>
	
	
</div>
<script>
var companyid = '<%=request.getParameter("companyid")%>';
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



$("#endtime").val(enddate.getFullYear()+"-"+mm+"-01");

$("#endtime").change(function(){
	var time_start = $("#endtime").val();
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

var myChart_transportlog = echarts.init(document.getElementById('echarts_userstatistic'));


queryuserstatistic();

function queryuserstatistic(){
	var starttime = $("#endtime").val();
	var type = $("#types").val();
	var countxAxis = [];
	var countyAxis1 = [];
	
	
	$._ajax({
		url: Q.url+"/statistic/queryuserstatistic.action",
		data:{"starttime":starttime,"type":type,"companyid":companyid},
		success: function(data){
			if( typeof(data) == 'object'){
				$("#totalusers").html(data.rows[0].totaluser);
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
				$("#thisadd").html(htm+data.rows[0].thisadd);
				
				for(var i = 1;i<data.total;i++){
					countxAxis[countxAxis.length] = data.rows[i].time;
					countyAxis1[countyAxis1.length] = parseFloat(data.rows[i].nums);
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
		        text: "股票"+month+"推广用户走势图",
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