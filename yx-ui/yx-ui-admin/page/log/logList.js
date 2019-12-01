layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table;

    //列表加载
    var tableIns = table.render({
        //设置表头
        cols: [[
            {field: 'source', title: '来源', templet: '#sourceTpl', width: 90},
            {field: 'userName', title: '用户名', width: 80},
            {field: 'operation', title: '用户操作', width: 90},
            {field: 'operationType', title: '操作类型', templet: '#operationTypeTpl', width: 90},
            {field: 'method', title: '请求方法'},
            {field: 'params', title: '请求参数'},
            {field: 'result', title: '结果', templet: '<div>{{d.result === 1 ? "成功" : "失败"}}</div>', width: 60},
            {field: 'time', title: '执行时长', width: 100},
            {field: 'ip', title: 'IP地址', width: 120},
            {field: 'createTime', title: '创建时间', width: 160}
        ]],
        url: 'log/queryListPage',
        method: 'POST',
        contentType: 'application/json',
        request: {
            pageName: 'current', //页码的参数名称，默认：page
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        response: {
            statusCode: 200, //成功的状态码，默认：0
            msgName: 'message', //状态信息的字段名称，默认：msg
        },
        toolbar: true,
        defaultToolbar :  ['filter'],
        elem: '#logTable',
        page: {
            elem: 'pageDiv',
            limit: 10,
            limits: [10, 20, 30, 40, 50]
        }
    });


    //查询
    $(".search_btn").click(function () {
        var userName = $(".userName").val();
        var operation_=$(".operation_").val();
        var source=$(".source").val();

        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    user_name: userName,
                    operation: operation_,
                    source:source
                }
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

});
