layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table;
    // 页面操作：0：查看，1：添加，2：修改
    pageOperation = 0;
    checkedparamId = "";

    // 渲染表格
    var tableIns = table.render({
        //设置表头
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'paramKey', title: '参数名', align: 'center', event: 'editColumn',width:200},
            {field: 'paramValue', title: '参数值', align: 'center', event: 'editColumn'},
            {field: 'remark', title: '备注', align: 'center', event: 'editColumn'},
            {
                field: 'enable',
                title: '启用状态',
                align: 'center',
                //templet: '<div>{{d.enable === 1 ? "启用" : "禁用"}}</div>'
                templet: '#switchTpl', event: 'editColumn'
            },
            {field: 'createTime', title: '创建时间', align: 'center'},
            {field: 'opt', title: '操作', fixed: 'right', width: 160, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'param/listPage',
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
        toolbar: true,
        defaultToolbar :  ['filter'],
        elem: '#paramTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        }
    });

    //监听工具条
    table.on('tool(tableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            checkedparamId = data.id;
            var index = layui.layer.open({
                title: "查看参数",
                type: 2,
                content: "param.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回参数列表', '.layui-layer-setwin .layui-layer-close', {
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
        } else if (layEvent === 'del') { //删除
            checkedparamId = data.id;
            layer.confirm('您确定要删除吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'DELETE',
                    url: 'param/deleteBatchByIds',
                    data: JSON.stringify([data.id]),
                    success: function (data) {
                        if (data.code === 200) {
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
            checkedparamId = data.id;
            var index = layui.layer.open({
                title: "修改参数",
                type: 2,
                content: "param.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回参数列表', '.layui-layer-setwin .layui-layer-close', {
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
        }
    });

    //监听表格复选框选择
    table.on('checkbox(tableFilter)', function (obj) {
    });

    //点击查询按钮事件监听
    $(".search_btn").click(function () {
        var searchKey = $(".search_input").val();
        var remark_=$(".remark_").val();
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    param_key: searchKey,
                    remark_:remark_
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    // 添加参数按钮监听
    $(".addBtn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "添加参数",
            type: 2,
            content: "param.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回参数列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
       /* $(window).resize(function () {
            layui.layer.full(index);
        });*/
        layui.layer.full(index);
    });

    //批量删除按钮监听
    $(".batchDel").click(function () {
        var checkStatus = table.checkStatus('paramTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的参数", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定删除选中的信息？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            var paramIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                paramIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'DELETE',
                url: 'param/deleteBatchByIds',
                data: JSON.stringify(paramIds),
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

    //监听单元格编辑
    table.on('edit(tableFilter)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        var modData = {};
        modData["id"] = data.id;
        modData[field] = value;
        modMenuData(modData);
    });

    function modMenuData(modData) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            type: "POST",
            url: "param/modify",
            data: JSON.stringify(modData),
            success: function (data) {
                if (data.code === 200) {
                    setTimeout(function () {
                        layer.close(index);
                        layer.msg("修改成功！", {icon: 1});

                    }, 500);
                } else {
                    top.layer.close(index);
                    layer.msg(data.message, {icon: 2});
                    tableIns.reload({
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                }
            },
            contentType: "application/json"
        });
    }

    //监听启用状态操作
    form.on('switch(enable)', function (obj) {
        //layer.msg('value: ' + this.value + ',name: ' + this.name + ',开关是否选中：' + (this.checked ? 'true' : 'false'));
        var modData = {};
        modData["id"] = this.value;
        modData[this.name] = (this.checked ? '1' : '0');
        modMenuData(modData);
    });
});
