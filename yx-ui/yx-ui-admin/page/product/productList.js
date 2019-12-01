layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table', 'laydate'], function() {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table,
        laydate = layui.laydate;
    //页面操作：0：查看，1：添加，2：修改
    pageOperation = 0;
    dataId = "";
    deptId = "";
    exportType = 2;

    productId = "";
    //日期初始化
    laydate.render({
        elem: '#createTime',
        range: true
    });

    var tableIns = table.render({
        //设置表头
        cols: [
            [{
                    type: 'checkbox',
                    fixed: 'left'
                },
                {
                    field: 'title',
                    title: '借款标题',
                    width: 150,
                    templet: function(res) {
                        if(res.productType==2){
                            return '<a style="text-decoration:underline;cursor:pointer" href="' + server_url + '/layout/investment-detail?id=' + res.id + '" target="_blank">' + res.title + '</a>';
                        }else{
                            return '<a style="text-decoration:underline;cursor:pointer" href="' + server_url + '/layout/transfer-detail?id=' + res.id + '" target="_blank">' + res.title + '</a>';
                        }
                    }
                },
                {
                    field: 'productAmount',
                    title: '借款金额',
                    width: 150
                },
                {
                    field: 'bidAmount',
                    title: '实际招标金额',
                    width: 150
                },
                {
                    field: 'productDeadline',
                    title: '借款期限',
                    width: 150
                },
                {
                    field: 'profit',
                    title: '年收益（%）',
                    width: 150
                },
                {
                    field: 'guaranteeName',
                    title: '借款推荐方',
                    width: 150
                },
                {
                    field: 'borrowUserRealName',
                    title: '借款人',
                    width: 150
                },
                {
                    field: 'borrowUserPhone',
                    title: '借款人手机号',
                    width: 150
                },
                {
                    field: 'productType',
                    title: '标的类型',
                    templet: '#productTypeTpl',
                    width: 150
                },
                {
                    field: 'repurchaseMode',
                    title: '还款方式',
                    templet: '#repurchaseModeTpl',
                    width: 150
                },
                {
                    field: 'assetType',
                    title: '借款类型',
                    templet: '#assetTypeTpl',
                    width: 150
                },
                {
                    field: 'useType',
                    title: '借款用途',
                    templet: '#useTypeTpl',
                    width: 150
                },
                {
                    field: 'productStatus',
                    title: '标的状态',
                    templet: '#productStatusTpl',
                    width: 150
                },
                {
                    field: 'productRating',
                    title: '风险评级',
                    templet: '#productRatingTpl',
                    width: 150
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 180
                },
                {
                    field: 'firstCheckTime',
                    title: '标的审核时间',
                    width: 180
                },
                {
                    field: 'finalCheckTime',
                    title: '标的复核时间',
                    width: 180
                },
                {
                    field: 'opt',
                    title: '操作',
                    fixed: 'right',
                    width: 210,
                    align: 'left',
                    toolbar: '#toolBar'
                }
            ]
        ],
        url: 'product/selectPage',
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
        defaultToolbar: ['filter'],
        elem: '#ProductTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        }
    });

    //监听工具条
    table.on('tool(tableFilter)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        dataId = data.id;

        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        console.log(layEvent)
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看标的",
                type: 2,
                content: "product.html",
                success: function(layero, index) {
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            /*$(window).resize(function() {
                layui.layer.full(index);
            });*/
            layui.layer.full(index);
            //点击标题
        } else if (layEvent === 'checkDetail') { //审核详情
            pageOperation = 2;
            var index = layui.layer.open({
                title: "审核详情",
                type: 2,
                area: ['70%', '90%'],
                content: "productCheckDetail.html",
                success: function(layero, index) {
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
        } else if (layEvent === 'del') { //删除
            var dataIds = [data.id];
            layer.confirm('您确定要删除吗？', {
                icon: 3,
                title: '确认'
            }, function() {
                $.ajax({
                    type: 'POST',
                    url: 'product/delete',
                    data: JSON.stringify(dataIds),
                    success: function(data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                obj.del();
                                layer.msg("删除成功", {
                                    icon: 1,
                                    time: 2000
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
                title: "编辑标的",
                type: 2,
                content: "product.html",
                success: function(layero, index) {
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            /* $(window).resize(function() {
                 layui.layer.full(index);
             });*/
            layui.layer.full(index);
        } else if (layEvent === 'flow') { //流标
            pageOperation = 3;
            dataId = data.id;
            layui.layer.open({
                type: 2,
                title: '流标',
                shadeClose: true,
                shade: 0.5,
                area: ['70%', '70%'],
                content: 'flowProduct.html' //iframe的url
            });
        } else if (layEvent === 'loan') { //放款
            dataId = data.id;

            layui.layer.open({
                type: 2,
                title: '放款',
                shadeClose: true,
                shade: 0.5,
                area: ['50%', '40%'],
                content: 'loanProduct.html' //iframe的url
            });
            /*layer.confirm('您确定要放款吗？', {
                icon: 3,
                title: '确认'
            }, function() {
                var index = top.layer.msg('数据提交中，请稍候', {
                    icon: 16,
                    time: false,
                    shade: 0.8
                });
                var product = {};
                product.id = data.id;
                $.ajax({
                    type: 'POST',
                    url: 'product/loan',
                    data: JSON.stringify(product),
                    dataType: 'json',
                    success: function(data) {
                        top.layer.close(index);
                        if (data.code != 200) {
                            layer.msg(data.message, {
                                icon: 2,
                                time: 3000
                            });
                            return;
                        }
                        layer.msg("放款请求处理成功", {
                            icon: 1,
                            time: 2000
                        });
                        setTimeout(window.location.reload(),2000);
                    },
                    error: function(error) {
                        top.layer.close(index);
                    }
                });
            });*/
        } else if (layEvent === 'failBidOrder') {
            // 关闭处理中的出借订单（放款时使用）
            dataId = data.id;
            if (data.processBidOrderCount <= 0) {
                layer.msg('没有需要关闭的订单信息', {
                    icon: 1
                });
                return;
            }
            layer.confirm('您确定要关闭【' + data.title + '】下处理中的订单吗？', {
                icon: 3,
                title: '确认'
            }, function() {
                $.ajax({
                    type: 'post',
                    url: 'product/failBidOrder/' + dataId,
                    success: function(data) {
                        if (data.code !== 200) {
                            layer.msg(data.message, {
                                icon: 2
                            });
                            return;
                        }
                        layer.msg('关闭申请已提交', {
                            icon: 1
                        });
                        $('.search_btn').click();
                        return;
                    },
                    error: function(error) {
                        layer.msg('调用服务器异常', {
                            icon: 2
                        });
                        return;
                    }
                });
            });
        }
    });

    //监听表格复选框选择
    table.on('checkbox(tableFilter)', function(obj) {});

    //查询
    $(".search_btn").click(function() {
        var title = $(".title").val();
        var borrowUserPhone = $(".borrowUserPhone").val();
        var borrowUserName = $(".borrowUserName").val();
        var createTime = $(".createTime").val();
        var assetType = $(".assetType").val();
        var productStatus = $(".productStatus").val();
        var productType = $(".productType").val();
        var repurchaseMode = $(".repurchaseMode").val();
        console.log("createTime:" + createTime);
        var createTimeStart;
        var createTimeEnd;
        if (createTime != '') {
            var strings = createTime.split(" - ");
            createTimeStart = strings[0];
            createTimeEnd = strings[1];
        }
        console.log("start:" + createTimeStart);
        console.log("end:" + createTimeEnd);

        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    title: title,
                    borrowUserPhone: borrowUserPhone,
                    borrowUserName: borrowUserName,
                    assetType: assetType,
                    productStatus: productStatus,
                    productType: productType,
                    repurchaseMode: repurchaseMode,
                    createTimeStart: createTimeStart,
                    createTimeEnd: createTimeEnd
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //添加
    $(".ProductAdd_btn").click(function() {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "发布借款标",
            type: 2,
            content: "product.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        /* $(window).resize(function() {
             layui.layer.full(index);
         });*/
        layui.layer.full(index);
    });

    //批量删除
    $(".batchDel").click(function() {
        var checkStatus = table.checkStatus('ProductTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的数据", {
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
            var dataIds = [];
            for (var i = 0; i < checkStatus.data.length; i++) {
                dataIds[i] = checkStatus.data[i].id;
            }
            $.ajax({
                type: 'POST',
                url: 'product/delete',
                data: JSON.stringify(dataIds),
                success: function(data) {
                    if (data.code == 200) {
                        if (data.data === true) {
                            layer.close(indexMsg);
                            layer.msg("删除成功", {
                                icon: 1,
                                time: 2000
                            });
                            tableIns.reload({
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                }
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
    })

    //数据导出
    $(".exportBtn").click(function() {
        // var checkStatus = table.checkStatus('ProductTable');

        // layer.confirm('确定执行导出操作吗？', {icon: 3, title: '确认'}, function (index) {
        var indexMsg = layer.msg('操作中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        // var dataIds = [];
        // for (var i = 0; i < checkStatus.data.length; i++) {
        //     dataIds[i] = checkStatus.data[i].id;
        // }
        var title = $(".title").val();
        var borrowUserPhone = $(".borrowUserPhone").val();
        var borrowUserName = $(".borrowUserName").val();
        var createTime = $(".createTime").val();
        var assetType = $(".assetType").val();
        var productStatus = $(".productStatus").val();
        console.log("createTime:" + createTime);
        var createTimeStart;
        var createTimeEnd;
        if (createTime != '') {
            var strings = createTime.split(" - ");
            createTimeStart = strings[0];
            createTimeEnd = strings[1];
        }
        var jsonStr = {
            type: exportType,
            title: title,
            borrowUserPhone: borrowUserPhone,
            borrowUserName: borrowUserName,
            assetType: assetType,
            productStatus: productStatus,
            createTimeStart: createTimeStart,
            createTimeEnd: createTimeEnd
        }
        console.log
        $.ajax({
            type: 'POST',
            url: 'excel/export',
            data: JSON.stringify(jsonStr),
            success: function(data) {
                if (data.code == 200) {
                    if (data.data === true) {
                        layer.close(indexMsg);
                        layer.msg("请查看导出结果", {
                            icon: 1,
                            time: 2000
                        });
                        tableIns.reload({
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });
                    }
                } else {
                    layer.msg(data.message, {
                        icon: 2
                    });
                }
            }
        });
        // });
    });

    //导出结果
    $(".exportLogBtn").click(function() {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "导出结果",
            type: 2,
            area: ['80%', '80%'],
            content: "../export/exportLogList.html",
            success: function(layero, index) {
                setTimeout(function() {
                    layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    });

    //流标
    $(".flowProduct_btn").click(function() {
        pageOperation = 3;
        var checkStatus = table.checkStatus('ProductTable');

        if (checkStatus.data.length === 0) {
            layer.msg("请选择要流标的数据", {
                icon: 0,
                time: 2000
            });
            return;
        }

        if (checkStatus.data.length > 1) {
            layer.msg("流标只能操作单个标的", {
                icon: 0,
                time: 2000
            });
            return;
        }
        dataId = checkStatus.data[0].id;
        layui.layer.open({
            type: 2,
            title: '流标',
            shadeClose: true,
            shade: 0.5,
            area: ['70%', '70%'],
            content: 'flowProduct.html' //iframe的url
        });
    })

    //关闭处理中的订单
    $(".failBidOrder").click(function() {

        var checkStatus = table.checkStatus('ProductTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {
                icon: 0,
                time: 2000
            });
            return;
        }
        if (checkStatus.data.length >= 2) {
            layer.msg("只能选择一个操作的用户", {
                icon: 0,
                time: 2000
            });
            return;
        }
        for (var i = 0; i < checkStatus.data.length; i++) {
            dataId = checkStatus.data[i].id;
            productStatus = checkStatus.data[i].productStatus;
            processBidOrderCount = checkStatus.data[i].processBidOrderCount;
        }

        // 关闭处理中的出借订单（放款时使用）
        if (!(productStatus == 14 || productStatus == 20 || productStatus == 21) || processBidOrderCount <= 0) {
            layer.msg('没有需要关闭的订单信息', {
                icon: 1
            });
            return;
        }
        /*
        if (processBidOrderCount <= 0) {
            layer.msg('没有需要关闭的订单信息2', {icon: 1});
            return;
        }*/
        layer.confirm('您确定要关闭【' + checkStatus.data[0].title + '】下处理中的订单吗？', {
            icon: 3,
            title: '确认'
        }, function() {
            $.ajax({
                type: 'post',
                url: 'product/failBidOrder/' + dataId,
                success: function(data) {
                    if (data.code !== 200) {
                        layer.msg(data.message, {
                            icon: 2
                        });
                        return;
                    }
                    layer.msg('关闭申请已提交', {
                        icon: 1
                    });
                    $('.search_btn').click();
                    return;
                },
                error: function(error) {
                    layer.msg('调用服务器异常', {
                        icon: 2
                    });
                    return;
                }
            });
        });
    })


});
