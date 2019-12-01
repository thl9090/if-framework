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
    dataId = "";
    deptId = "";

    var tableIns = table.render({
        //设置表头
        cols: [[
            // {type: 'checkbox', fixed: 'left'},
            {field: 'smartName', title: '智投名称',width: 150},
            {field: 'smartTotalAmount', title: '投标金额',width: 150},
            {field: 'borrowDeadlineStr', title: '匹配期限（月）',width: 200},
            {field: 'status', title: '状态',templet: '#statusTpl',width: 100},
            {field: 'createTime', title: '创建时间',width: 180},
            {field: 'opt', title: '操作', fixed: 'right', width: 160, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'smartBidPlan/selectBidingSmart',
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
        elem: '#SmartBidTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        }
    });

    //监听工具条
    table.on('tool(tableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        dataId = data.id;
        deptId = data.deptId;
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看

        }else if (layEvent === 'select') { //选择受托人
            var smartBidPlanId=data.id;
            var smartName=data.smartName;

            parent.selectSmartBidCallBack(smartBidPlanId, smartName);
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });

    //监听表格复选框选择
    table.on('checkbox(tableFilter)', function (obj) {
    });

    //查询
    $(".search_btn").click(function () {
        var phone = $(".phone").val();
        var userName=$(".userName").val();
        var realName=$(".realName").val();

        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    phone: phone,
                    userName:userName,
                    realName:realName
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //添加会员
    $(".UserInfoAdd_btn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "userInfo.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        /*$(window).resize(function () {
            layui.layer.full(index);
        });*/
        layui.layer.full(index);
    });

    //批量删除
    $(".batchDel").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的用户", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定删除选中的信息？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dataIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                dataIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'userInfo/delete',
                data: JSON.stringify(dataIds),
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
    });

    //设为借款人
    $(".batchBow").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定将选中的用户设置为借款人？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dataIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                dataIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'userInfo/setBorrower',
                data: JSON.stringify(dataIds),
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
    });

    //设为受托人
    $(".batchDep").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定将选中的用户设置为受托人？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dataIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                dataIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'userInfo/setDepositary',
                data: JSON.stringify(dataIds),
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





});
