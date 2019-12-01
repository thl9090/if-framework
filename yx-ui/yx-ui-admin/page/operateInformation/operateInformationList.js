layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'laydate','jquery', 'table','layedit'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        layedit=layui.layedit,
        table = layui.table;
    //页面操作：0：查看，1：添加，2：修改
    pageOperation = 0;
    dataId = "";
    deptId = "";

    var tableIns = table.render({
        //设置表头
        cols: [[
            {field: 'dataTimeStr', title: '数据截止时间',width: 200},
            {field: 'borrowingAmount', title: '自成立累计借贷金额（元）',width: 200},
            {field: 'borrowingNum', title: '自成立累计借贷笔数',width: 200},
            {field: 'borrowSurplus', title: '借贷余额（万元）',width: 200},
            {field: 'interestBalance', title: '利息余额（万元）',width: 200},
            {field: 'toBePaidNum', title: '待还笔数',width: 200},
            {field: 'lenderTotal', title: '累计出借人数量（人）',width: 200},
            {field: 'borrowerTotal', title: '累计借款人数量（人）',width: 200},
            {field: 'monthLenderTotal', title: '当期出借人数量（人）',width: 200},
            {field: 'monthBorrowingNum', title: '当期借款人数量（人）',width: 200},
            {field: 'topTenBorrowed', title: '前十大借款人待还金额占比',width: 200},
            {field: 'topOneBorrowed', title: '最大单一借款人待还金额占比',width: 200},
            {field: 'relationSurplus', title: '关联关系借款余额',width: 200},
            {field: 'relationNum', title: '关联关系借款笔数',width: 200},
            {field: 'overdueAmount', title: '逾期金额（本+息）（万元）',width: 200},
            {field: 'overdueNum', title: '逾期笔数（本+息）',width: 200},
            {field: 'overdueCapital', title: '逾期金额（90天以上，本金）（元）',width: 200},
            {field: 'overdueCapitalNum', title: '逾期笔数（90天以上，本金）',width: 200},
            {field: 'compensationAmount', title: '代偿金额（本+息）（万元）',width: 200},
            {field: 'compensationNum', title: '代偿笔数（本+息）',width: 200},
            {field: 'replaceRepayAmount', title: '累计代偿金额（万元）',width: 200},
            {field: 'replaceRepayNum', title: '累计代偿笔数',width: 200},
            {field: 'opt', title: '操作', fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'operateInformation/selectPage',
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
        elem: '#ActivityMsgTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        },
        done: function (res,curr,count) {
            //res为异步请求返回数据，curr 当前页，count数量总量
            currPage = curr;
        }
    });

    //监听工具条
    table.on('tool(tableFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data;
        dataId = data.id;
        deptId = data.deptId;
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看活动消息",
                type: 2,
                content: "operateInformation.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回活动消息列表', '.layui-layer-setwin .layui-layer-close', {
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
        } else if (layEvent === 'edit') { //编辑
            pageOperation = 2;
            var index = layui.layer.open({
                title: "编辑活动消息",
                type: 2,
                content: "operateInformation.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回活动消息列表', '.layui-layer-setwin .layui-layer-close', {
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
        var dataTime = $(".dataTime").val();
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    dataTime: dataTime,
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //添加
    $(".SysSlideshowAdd_btn").click(function () {
        pageOperation = 1;
        var index = layui.layer.open({
            title: "添加平台经营信息",
            type: 2,
            content: "operateInformation.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回平台经营信息列表', '.layui-layer-setwin .layui-layer-close', {
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



});