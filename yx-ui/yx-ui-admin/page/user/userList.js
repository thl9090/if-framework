layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table;
        //页面操作：0：查看，1：添加，2：修改
        pageOperation = 0;
        userId = "";
        deptId = "";

    onlineUserCount();

    function onlineUserCount() {
        $.ajax({
            type: 'POST',
            url: 'user/queryOnLineUserCount',
            success: function (data) {
                console.log(data);
                if (data.code == 200) {
                    $('#onlineUserCountLbl').html(data.data);
                }
            }
        });
    }

    var tableIns = table.render({
        //设置表头
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'account', title: '账号',width:150},
            {field: 'userName', title: '姓名',width:150},
            {field: 'roleNames', title: '角色',width:200},
            {field: 'sex', title: '性别', templet: '#sexTpl'},
            {field: 'phone', title: '手机号',width:150},
            {field: 'deptName', title: '部门',width:200},
            {field: 'position', title: '职位',width:150},
            {field: 'email', title: '邮箱',width:200},
            {field: 'idCard', title: '身份证',width:200},
            {field: 'enable', title: '状态', templet: '<div>{{d.enable === 1 ? "启用" : "禁用"}}</div>'},
            {field: 'opt', title: '操作', fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'user/listPage',
        method: 'post',
        contentType: 'application/json',
        request: {
            pageName: 'current', //页码的参数名称，默认：page
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        response: {
            statusCode: 200, //成功的状态码，默认：0
            msgName: 'message' //状态信息的字段名称，默认：msg
        },
        //toolbar: true,
        toolbar: '#headerToolbar',
        defaultToolbar :  ['filter'],
        elem: '#userTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        }
    });

    //监听工具条
    table.on('tool(tableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        userId = data.id;
        deptId = data.deptId;
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看用户",
                type: 2,
                content: "user.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            layui.layer.full(index);
        } else if (layEvent === 'del') { //删除
            var userIds = [data.id];
            layer.confirm('您确定要删除吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'POST',
                    url: 'user/delBatchByIds',
                    data: JSON.stringify(userIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                obj.del();
                                layer.msg("删除成功", {icon: 1, time: 2000});
                            }
                        } else {
                            layer.msg(data.message, {icon: 2});
                        }
                    }
                });
            });
        } else if (layEvent === 'edit') { //编辑
            pageOperation = 2;
            var index = layui.layer.open({
                title: "编辑用户",
                type: 2,
                content: "user.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            layui.layer.full(index);
        }else if (layEvent === 'enable') { //启用
            var userIds = [data.id];
            layer.confirm('您确定要启用该用户吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'POST',
                    url: 'user/enable',
                    data: JSON.stringify(userIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                layer.msg("操作成功", {icon: 1, time: 2000});
                                tableIns.reload({
                                    page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            }
                        } else {
                            layer.msg(data.message, {icon: 2});
                        }
                    }
                });
            });
        }else if (layEvent === 'disable') { //禁用
            var userIds = [data.id];
            layer.confirm('您确定要禁用该用户吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'POST',
                    url: 'user/disable',
                    data: JSON.stringify(userIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                layer.msg("操作成功", {icon: 1, time: 2000});
                                tableIns.reload({
                                    page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            }
                        } else {
                            layer.msg(data.message, {icon: 2});
                        }
                    }
                });
            });
        }
    });

    //监听表格复选框选择
    table.on('checkbox(tableFilter)', function (obj) {
    });

    //查询
    $(".search_btn").click(function () {
        onlineUserCount();
        var account = $(".account").val();
        var userName=$(".userName").val();
        var phone=$(".phone").val();
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    account: account,
                    userName:userName,
                    phone:phone
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //添加会员
    $(".usersAdd_btn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "user.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        layui.layer.full(index);
    });

    //批量删除
    $(".batchDel").click(function () {
        var checkStatus = table.checkStatus('userTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的用户", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定删除选中的信息？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            var userIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                userIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'user/delBatchByIds',
                data: JSON.stringify(userIds),
                success: function (data) {
                    if (data.code == 200) {
                        if (data.data === true) {
                            layer.close(indexMsg);
                            layer.msg("删除成功", {icon: 1, time: 2000});
                            tableIns.reload({
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            });
                        }
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        });
    })


    //实时在线用户列表
    $(".onlineUserList_btn").click(function () {
        var index = layui.layer.open({
            title: "查看在线用户",
            type: 2,
            content: "onlineUserList.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

});


