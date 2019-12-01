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
    checkedDicId = "";

    // 渲染表格
    var tableIns = table.render({
        //设置表头
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'type', title: '字典类型', align: 'center'},
            {field: 'codeText', title: '字典名称', align: 'center'},
            {field: 'code', title: '关键字', align: 'center'},
            {field: 'sortNo', title: '排序', align: 'center'},
            {field: 'remark', title: '备注', align: 'center'},
            {
                field: 'enable',
                title: '启用状态',
                sort: true,
                align: 'center',
                templet: '<div>{{d.enable === 1 ? "启用" : "禁用"}}</div>'
            },
            {field: 'createTime', title: '创建时间', align: 'center'},
            {field: 'opt', title: '操作', fixed: 'right', width: 160, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'dic/listPage',
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
        elem: '#dicTable',
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
            checkedDicId = data.id;
            var index = layui.layer.open({
                title: "查看字典",
                type: 2,
                content: "dic.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回字典列表', '.layui-layer-setwin .layui-layer-close', {
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
        } else if (layEvent === 'del') { //删除
            checkedDicId = data.id;
            layer.confirm('您确定要删除吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'POST',
                    url: 'dic/delBatchByIds',
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
            checkedDicId = data.id;
            var index = layui.layer.open({
                title: "修改字典",
                type: 2,
                content: "dic.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回字典列表', '.layui-layer-setwin .layui-layer-close', {
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

    //查询
    $(".search_btn").click(function () {
        if (base.fastClickCheck(".search_btn")) {
            var type_ = $(".type_").val();
            var code_text=$(".code_text").val();

            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    condition: {
                        code_text: code_text,
                        type_:type_
                    }
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    });

    // 添加字典
    $(".addBtn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "添加字典",
            type: 2,
            content: "dic.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回字典列表', '.layui-layer-setwin .layui-layer-close', {
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
        var checkStatus = table.checkStatus('dicTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的字典", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定删除选中的信息？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dicIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                dicIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'dic/delBatchByIds',
                data: JSON.stringify(dicIds),
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
