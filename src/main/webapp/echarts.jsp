<%--
  Created by IntelliJ IDEA.
  User: kingwzun
  Date: 2022/12/21
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="header.jsp"%>
    <script src="${path}/static/echarts.min.js"></script>

</head>
<body>
<!-- 为 ECharts 准备一个定义了宽高的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<div id="main2" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    $.post(
        '${path}/dept?method=selectDeptCount', //发送的请求
        function (jsonResult) {     //返回的参数
            console.log(jsonResult);
            var array=jsonResult.data
            // 指定图表的配置项和数据
            // 0:{name: '客房部', value: 1}1:{name: '保洁部', value: 2}
            var xArray=new Array()
            var yArray=new Array()
            for (var i = 0; i < array.length; i++) {
                xArray.push(array[i].name)
                yArray.push(array[i].value)
            }
            var option = {
                title: {
                    text: '部门人数分布'
                },
                tooltip: {},
                legend: {
                    data: ['人数']
                },
                xAxis: {
                    data: xArray
                },
                yAxis: {},
                series: [
                    {
                        name: '部门人数',
                        type: 'bar',
                        data: yArray
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

            //饼状图
           var option2 = {
               title: {
                   text: 'Referer of a Website',
                   subtext: 'Fake Data',
                   left: 'center'
               },
               tooltip: {
                   trigger: 'item'
               },
               legend: {
                   orient: 'vertical',
                   left: 'left'
               },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: array,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart2.setOption(option2);
        },

    );


</script>
</body>
</html>
