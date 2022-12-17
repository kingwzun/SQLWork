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
    <%@ include file="header.jsp"%>
</head>
<body>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<table class="layui-hide" id="LAY_table_user" lay-filter="layFilter"></table>

<script>
    layui.use('table', function(){
        var table = layui.table;

        //table 方法级渲染
        table.render({
            elem: '#LAY_table_user'
            ,url: '${path}/user?method=selectByPage'
            ,cols: [[
                {checkbox: true, fixed: true}
                ,{field:'id', title: 'ID', sort: true}
                ,{field:'name', title: '用户名'}
                ,{field:'password', title: '密码'}
                ,{field:'email', title: '邮箱'}
                ,{field:'phone', title: '电话'}
                ,{field:'phone', title: '操作', toolbar: '#barDemo'}
            ]]
            ,id: 'tableId'
            ,page: true,
        });

        //监听右侧工具条
        table.on('tool(layFilter)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.post(
                        '${path}/user?method=deleteById', //发送的请求
                        {'id':data.id},
                        function(jsonResult) {     //返回的参数
                            console.log(jsonResult);
                            if (jsonResult.ok) {
                                layer.msg('删除成功');
                                //删除之后刷新表格展示最新的数据
                                table.reload('tableId');
                            } else {
                                layer.msg('删除失败');
                            }
                        },
                        'json'
                    );

                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.alert('编辑行：<br>'+ JSON.stringify(data))
            }
        });

        var $ = layui.$, active = {
            reload: function(){
                var demoReload = $('#demoReload');

                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        key: {
                            id: demoReload.val()
                        }
                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>






<%--
   <table class="table table-striped table-bordered table-hover table-condensed">
        <tr>
            <td>ID</td>
            <td>名字</td>
            <td>密码</td>
            <td>邮箱</td>
            <td>电话</td>
            <td>删除</td>
        </tr>
        <c:forEach items="${list}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                &lt;%&ndash;<td><a href="${path}/user?method=deleteById&id=${user.id}">删除</a></td>&ndash;%&gt;
                <td><a href="javascript:deleteById(${user.id})">删除</a></td>
            </tr>
        </c:forEach>
    </table>

    <script>
        function deleteById(id) {
            var isDelete = confirm('您确认要删除么？');
            if (isDelete) {
                location.href = "${path}/user?method=deleteById&id=" + id;
            }
        }
    </script>--%>
</body>
</html>
