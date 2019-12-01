layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table'], function() {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table;

    // 页面操作：0：查看，1：添加，2：修改
    pageOperation = 0;
    menuId = "";


    //列表加载
    var tableIns = table.render({
        //设置表头
        cols: [
            [{
                    type: 'checkbox',
                    fixed: 'left'
                },
                {
                    field: 'id',
                    title: '菜单ID',
                    sort: true,
                    width: 100
                },
                {
                    field: 'menuName',
                    title: '菜单名称',
                    width: 150
                },
                {
                    field: 'parentName',
                    title: '上级菜单',
                    sort: true,
                    width: 150
                },
                {
                    field: 'menuType',
                    title: '类型',
                    sort: true,
                    templet: '<div>{{d.menuType === 0 ? "目录" : d.menuType === 1 ? "菜单" : "按钮"}}</div>',
                    width: 100
                },
                {
                    field: 'iconcls',
                    title: '菜单图标样式',
                    sort: false,
                    width: 150,
                    templet: function(res) {
                        return `<i class="icon iconfont ${res.iconcls}"></i>`
                    }
                },
                {
                    field: 'sortNo',
                    title: '排序',
                    sort: true,
                    width: 100
                },
                {
                    field: 'request',
                    title: '请求地址',
                    sort: false,
                    width: 300
                },
                {
                    field: 'permission',
                    title: '权限标识',
                    sort: false
                },
                {
                    field: 'opt',
                    title: '操作',
                    fixed: 'right',
                    width: 160,
                    align: 'center',
                    toolbar: '#toolBar'
                }
            ]
        ],
        url: 'menu/queryListPage',
        method: 'post',
        contentType: 'application/json',
        response: {
            statusCode: 200 //成功的状态码，默认：0
        },
        request: {
            pageName: 'current', //页码的参数名称，默认：page
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        },
        toolbar: true,
        defaultToolbar: ['filter'],
        elem: '#menuTable'
    });
    //查询
    $(".search_btn").click(function() {
        var menuName = $(".menuName").val();
        var menuType = $(".menuType").val();
        var parentName = $(".parentName").val();

        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    menuName: menuName,
                    menuType: menuType,
                    parentName: parentName
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //监听状态操作
    form.on('checkbox(enableCbx)', function(obj) {
        // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    });

    //监听单元格编辑
    table.on('edit(menuTable)', function(obj) {
        var value = obj.value //得到修改后的值
            ,
            data = obj.data //得到所在行所有键值
            ,
            field = obj.field; //得到字段
        // layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
        var modData = {};
        modData["id"] = data.id;
        modData[field] = value;
        modData["parentId"] = data.parentId;
        modMenuData(modData);
    });

    //监听工具条
    table.on('tool(menuTable)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        menuId = data.id;
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看菜单",
                type: 2,
                content: "menu.html",
                success: function(layero, index) {
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
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
            layer.confirm('您确定要删除吗？', {
                icon: 3,
                title: '确认'
            }, function() {
                $.ajax({
                    type: 'DELETE',
                    url: 'menu/delete',
                    data: menuId,
                    success: function(data) {
                        if (data.code === 200) {
                            if (data.data === true) {
                                obj.del();
                                layer.msg("删除成功", {
                                    icon: 1,
                                    time: 2000
                                });
                            } else {
                                layer.msg(data.message, {
                                    icon: 2
                                });
                            }
                        } else {
                            layer.msg(data.message, {
                                icon: 2
                            });
                        }
                    }
                });
            });
        } else if (layEvent === 'edit') { //编辑
            pageOperation = 2;
            var index = layui.layer.open({
                title: "编辑菜单",
                type: 2,
                content: "menu.html",
                success: function(layero, index) {
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
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
    table.on('checkbox(menuTable)', function(obj) {});

    //添加菜单
    $(".menuAdd_btn").click(function() {
        pageOperation = 1;
        menuId = "";
        var index = layui.layer.open({
            title: "添加菜单",
            type: 2,
            content: "menu.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
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
    $(".batchDel").click(function() {
        var checkStatus = table.checkStatus('menuTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的菜单", {
                icon: 0,
                time: 2000
            });
            return;
        }
        layer.confirm('确定删除选中的信息？', {
            icon: 3,
            title: '确认'
        }, function(index) {
            var indexMsg = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            var menuIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                menuIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'menu/deleteBatchIds',
                data: JSON.stringify(menuIds),
                success: function(data) {
                    if (data.code === 200) {
                        //layer.close(indexMsg);
                        layer.msg("操作成功，成功删除记录数" + data.data, {
                            icon: 1,
                            time: 2000
                        });
                        tableIns.reload();
                    } else {
                        layer.msg(data.message, {
                            icon: 2
                        });
                    }
                }
            });
        });
    })

    function modMenuData(modData) {
        var index = layer.msg('修改中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            type: "POST",
            url: "menu/modify",
            data: JSON.stringify(modData),
            success: function(data) {
                if (data.code === 200) {
                    setTimeout(function() {
                        layer.close(index);
                        layer.msg("修改成功！", {
                            icon: 1
                        });
                    }, 500);
                } else {
                    top.layer.close(index);
                    layer.msg(data.message, {
                        icon: 2
                    });
                }
            },
            contentType: "application/json"
        });
    }

});