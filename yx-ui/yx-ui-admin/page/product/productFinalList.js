layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table','laydate'], function () {
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
    productTitle="";

    //日期初始化
    laydate.render({
        elem: '#createTime',
        range: true
    });

    var tableIns = table.render({
        //设置表头
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'title', title: '借款标题',width:150,
                templet: function(res) {
                    return '<a style="text-decoration:underline;cursor:pointer" href="'+server_url+'/layout/investment-detail?id='+res.id+'" target="_blank">' + res.title + '</a>';
                }},
            {field: 'productAmount', title: '借款金额',width:150},
            {field: 'productDeadline', title: '借款期限',width:150},
            {field: 'profit', title: '年收益',width:150},
            {field: 'guaranteeName', title: '保障机构',width:150},
            {field: 'borrowUserRealName', title: '借款人',width:150},
            {field: 'borrowUserPhone', title: '借款人手机号',width:150},
            {field: 'repurchaseMode', title: '还款方式',templet:'#repurchaseModeTpl',width:150},
            {field: 'assetType', title: '借款类型',templet:'#assetTypeTpl',width:150},
            {field: 'useType', title: '借款用途',templet:'#useTypeTpl',width:150},
            {field: 'productStatus', title: '标的状态',templet:'#productStatusTpl',width:150},
	        {field: 'productRating', title: '风险评级', templet: '#productRatingTpl', width: 150},
            {field: 'createTime', title: '创建时间',width:180},
            {field: 'firstCheckTime', title: '初审时间', width: 180},
            {field: 'opt', title: '操作', fixed: 'right', width: 280, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'product/selectFinalPage',
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
        elem: '#ProductTable',
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
        productTitle=data.title;

        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看标的",
                type: 2,
                content: "product.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回标的终审列表', '.layui-layer-setwin .layui-layer-close', {
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
        }else if(layEvent === 'checkDetail'){//审核详情
            pageOperation = 2;
            var index = layui.layer.open({
                title: "审核详情",
                type: 2,
                area:['70%','90%'],
                content: "productCheckDetail.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
        }  else if (layEvent === 'del') { //删除
            var dataIds = [data.id];
            layer.confirm('您确定要删除吗？', {icon: 3, title: '确认'}, function () {
                $.ajax({
                    type: 'POST',
                    url: 'product/delete',
                    data: JSON.stringify(dataIds),
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
        }else if(layEvent === 'check'){//审核
            pageOperation = 2;
            var index = layui.layer.open({
                title: "标的终审",
                type: 2,
                area:['70%','90%'],
                content: "productFinalCheck.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回标的列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            // $(window).resize(function () {
            //     layui.layer.full(index);
            // });
            // layui.layer.full(index);
        } else if (layEvent === 'edit') { //编辑
            pageOperation = 2;
            var index = layui.layer.open({
                title: "编辑标的",
                type: 2,
                content: "product.html",
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
        }else if(layEvent === 'updateProductTitle'){//修改标的名称
            var dataIds = [data.id];
            var index = layui.layer.open({
                title:'修改标的名称',
                type: 2,
                content: 'updateProductTitle.html',
                area: ['50%', '50%'],
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
        }
    });

    //监听表格复选框选择
    table.on('checkbox(tableFilter)', function (obj) {
    });

    //查询
    $(".search_btn").click(function () {

        var title = $(".title").val();
        var borrowUserPhone=$(".borrowUserPhone").val();
        var borrowUserName=$(".borrowUserName").val();
        var createTime=$(".createTime").val();
        var assetType=$(".assetType").val();
        var productStatus=$(".productStatus").val();
        var repurchaseMode = $(".repurchaseMode").val();
        console.log("createTime:"+createTime);
        var createTimeStart;
        var createTimeEnd;
        if(createTime!=''){
            var strings = createTime.split(" - ");
            createTimeStart=strings[0];
            createTimeEnd=strings[1];
        }
        console.log("start:"+createTimeStart);
        console.log("end:"+createTimeEnd);

        var searchKey = $(".search_input").val();
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    title: title,
                    borrowUserPhone:borrowUserPhone,
                    borrowUserName:borrowUserName,
                    assetType:assetType,
                    productStatus:productStatus,
                    repurchaseMode:repurchaseMode,
                    createTimeStart:createTimeStart,
                    createTimeEnd:createTimeEnd
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //添加
    $(".ProductAdd_btn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "发布借款标",
            type: 2,
            content: "product.html",
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
        var checkStatus = table.checkStatus('ProductTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要删除的数据", {icon: 0, time: 2000});
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
                url: 'product/delete',
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
