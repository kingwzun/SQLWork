<%--
  Created by IntelliJ IDEA.
  User: Gao
  Date: 2022/12/15
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../header.jsp" %>
</head>
<body>
<%--表格内嵌工具条--%>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<%--顶部工具条--%>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
</script>

<div class="demoTable">
    搜索名字：
    <div class="layui-inline">
        <input class="layui-input" name="name" id="nameId" autocomplete="off">
    </div>
    邮箱：
    <div class="layui-inline">
        <input class="layui-input" name="email" id="emailId" autocomplete="off">
    </div>
    手机：
    <div class="layui-inline">
        <input class="layui-input" name="phone" id="phoneId" autocomplete="off">
    </div>
    开始时间
    <div class="layui-inline">
        <input type="text" class="layui-input" id="beginDateId" placeholder="yyyy-MM-dd HH:mm:ss">
    </div>
    结束时间
    <div class="layui-inline">
        <input type="text" class="layui-input" id="endDateId" placeholder="yyyy-MM-dd HH:mm:ss">
    </div>
    <button class="layui-btn" data-type="reload">搜索</button>
</div>

<table class="layui-hide" id="test" lay-filter="test"></table>

<script>
    layui.use(['table','laydate'], function(){
        var table = layui.table;
        var laydate = layui.laydate;

        //日期时间选择器
        laydate.render({
            elem: '#beginDateId'
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#endDateId'
            ,type: 'datetime'
        });
        //table 方法级渲染
        table.render({
            elem: '#test'
            , url: '${path}/user?method=selectByPage'
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , cols: [[
                {checkbox: true, fixed: true}
                , {field: 'id', title: 'ID', sort: true}
                , {field: 'name', title: '用户名'}
                , {field: 'password', title: '密码'}
                , {field: 'email', title: '邮箱'}
                , {field: 'phone', title: '电话'}
                ,{field:'status', title: '状态'}
                ,{field:'gmtCreate', title: '创建时间',templet:"<div>{{!d.gmtCreate.time?'':layui.util.toDateString(d.gmtCreate.time, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
                ,{field:'gmtModified', title: '更新时间',templet:"<div>{{!d.gmtModified.time?'':layui.util.toDateString(d.gmtModified.time, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
                ,{field:'', title: '操作', toolbar: '#barDemo'}
                , {field: 'phone', title: '操作', toolbar: '#barDemo'}
            ]]
            , id: 'tableId'
            , page: true,
        });
        //头工具栏事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    //转跳到新页面打开
                <%--location.href='${path}/user/user_add.jsp'--%>
                    layer.open({
                        type: 2,
                        area: ['700px', '400px'],
                        content: '${path}/user/user_add.jsp'
                    })
                    break;
                case 'deleteAll':
                    var data = checkStatus.data
                    var idArray = new Array()
                    console.log(data)
                    for (var i = 0; i < data.length; i++) {
                        idArray.push(data[i].id);
                    }
                    //[14,25]-->'14,15'
                    var ids = idArray.join(',')
                    layer.confirm('真的删除行么', function (index) {
                        $.post(
                            '${path}/user?method=deleteAll  ', //发送的请求
                            {'ids': ids},
                            function (jsonResult) {     //返回的参数
                                console.log(jsonResult);
                                if (jsonResult.ok) {

                                    mylayer.okMsg('批量删除成功') //后台传入一样 mylayer.okMsg(jsonResult.msg)
                                    //删除之后刷新表格展示最新的数据
                                    table.reload('tableId');
                                } else {
                                    mylayer.errorMsg('批量删除失败')//后台传入一样 mylayer.errorMsg(jsonResult.msg)
                                }
                            },
                            'json'
                        );

                        layer.close(index);
                    });
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选' : '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            }
            ;
        });
        //监听右侧工具条
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    $.post(
                        '${path}/user?method=deleteById', //发送的请求
                        {'id': data.id},
                        function (jsonResult) {     //返回的参数
                            console.log(jsonResult);
                            if (jsonResult.ok) {
                                mylayer.okMsg('删除成功')
                                //删除之后刷新表格展示最新的数据
                                table.reload('tableId');
                            } else {
                                mylayer.errorMsg('删除失败')
                            }
                        },
                        'json'
                    );

                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                // layer.alert('编辑行：<br>' + JSON.stringify(data))
                layer.open({
                    type: 2,
                    area: ['700px', '400px'],
                    // content: '${path}/user/user_update.jsp'
                    content: '${path}/user?method=getUserUpdatePage&id=' + data.id
                });
            }
        });

        var $ = layui.$, active = {
            reload: function () {
                var demoReload = $('#demoReload');

                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: {
                            id: demoReload.val()
                        }
                    }
                });
            }
        };
        //多条件搜索
        var $ = layui.$, active = {
            reload: function(){
                //执行重载
                table.reload('tableId', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        name: $('#nameId').val(),
                        email: $('#emailId').val(),
                        phone: $('#phoneId').val(),
                        beginDate: $('#beginDateId').val(),
                        endDate: $('#endDateId').val(),                    }
                });
            }
        };
        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>
