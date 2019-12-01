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
	exportType = 1;
	realName="";
	phone="";
    accountStatus="";

    //日期初始化
    laydate.render({
        elem: '#createTime',
        range: true
    });

    aliveLoginCount();

    function aliveLoginCount() {
        $.ajax({
            type: 'POST',
            url: 'userInfo/aliveLoginCount',
            success: function (data) {
                console.log(data);
                if (data.code == 200) {
                    $('#aliveLoginCountLbl').html(data.data);
                }
            }
        });
    }

    var tableIns = table.render({
        //设置表头
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'phone', title: '手机号码',width:150},
            {field: 'userType', title: '用户类型',templet: '<div>{{d.userType === 2 ? "企业用户" : "个人用户"}}</div>',width:150},
            {field: 'userName', title: '用户名',width:150},
            {field: 'email', title: '邮箱',width:150},
            {field: 'realName', title: '真实姓名',width:100},
            {field: 'idType', title: '证件类型',templet: '<div>{{d.idType === 2 ? "营业执照" : "身份证"}}</div>',width:150},
            {field: 'idNumber', title: '证件号码',width:180},
            {field: 'acno', title: '存管账号',width:180},
            {field: 'status', title: '用户状态',templet: '<div>{{d.status === 1 ? "有效" : "禁用"}}</div>',width:100},
            {field: 'accountStatus', title: '存管账户状态',templet:'#accountStatusTpl',width:150},
            {field: 'custType', title: '客户存管类型',templet: '#custTypeTpl',width:150},
            {field: 'companyName', title: '企业名称',width:180},
            {field: 'refPhone', title: '推荐人手机号码',width:180},
            {field: 'source', title: '注册来源',templet:'#sourceTpl',width:100},
            {field: 'createTime', title: '注册时间',width:180},
            {field: 'openAuthStr', title: '已开通授权',width:150},
            {field: 'custNo', title: '客户号码',width:180},
            {field: 'registerPhone', title: '银行注册手机号码',width:150},
            {field: 'bankName', title: '银行名称',width:150},
            {field: 'cardNo', title: '绑定银行卡号',width:180},
            {field: 'bankMobile', title: '银行预留手机号',width:150},
            {field: 'isRisk', title: '是否参与风险测评',templet: '<div>{{d.isRisk === 1 ? "是" : "否"}}</div>',width:150},
            {field: 'custType', title: '法大大认证',templet: '<div>{{d.custType === 2 ? d.fddStatusStr : ""}}</div>',width:150},

            {field: 'opt', title: '操作', fixed: 'right', width: 300, align: 'center', toolbar: '#toolBar'}
        ]],
        url: 'userInfo/selectPage',
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
        toolbar: '#headerToolbar',
        defaultToolbar :  ['filter'],
        elem: '#UserInfoTable',
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

        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'detail') { //查看
            pageOperation = 0;
            var index = layui.layer.open({
                title: "查看用户",
                type: 2,
                content: "userInfo.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
            var dataIds = [data.id];
            layer.confirm('您确定要删除吗？', {icon: 3, title: '确认'}, function () {
                var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});

                $.ajax({
                    type: 'POST',
                    url: 'userInfo/delete',
                    data: JSON.stringify(dataIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                layer.close(indexMsg);
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
                content: "userInfo.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
        }else if (layEvent === 'enable') { //启用
            var dataIds = [data.id];
            layer.confirm('您确定要启用该用户吗？', {icon: 3, title: '确认'}, function () {
                var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});

                $.ajax({
                    type: 'POST',
                    url: 'userInfo/enable',
                    data: JSON.stringify(dataIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                layer.close(indexMsg);
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
            var dataIds = [data.id];
            layer.confirm('您确定要禁用该用户吗？', {icon: 3, title: '确认'}, function () {
                var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});

                $.ajax({
                    type: 'POST',
                    url: 'userInfo/disable',
                    data: JSON.stringify(dataIds),
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.data === true) {
                                layer.close(indexMsg);
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
        }else if(layEvent === 'updateUserReferee'){//修改推荐人
                var dataIds = [data.id];
                var index = layui.layer.open({
                    title:'修改推荐人',
                    type: 2,
                    content: 'updateUserReferee.html',
                    area: ['50%', '50%'],
                    success: function (layero, index) {
                        setTimeout(function () {
                            layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
        }else if(layEvent === 'searchUserAccount'){//查看用户账户
                var dataIds = [data.id];
                var index = layui.layer.open({
                    title:'用户账户',
                    type: 2,
                    content: 'searchUserAccount.html',
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
        var phone = $(".phone").val();
        var userName=$(".userName").val();
        var realName=$(".realName").val();
        var createTime=$(".createTime").val();
        var custType=$(".custType").val();
        var accountStatus=$(".accountStatus").val();
        var email=$(".email").val();
        var refPhone=$(".refPhone").val();
        var userType=$(".userType").val();
        var source=$(".source").val();

        console.log("createTime:"+createTime);
        var createTimeStart;
        var createTimeEnd;
        if(createTime!=''){
            var strings = createTime.split(" - ");
            createTimeStart=strings[0];
            createTimeEnd=strings[1];
        }

        aliveLoginCount();

        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                condition: {
                    phone: phone,
                    userName:userName,
                    realName:realName,
                    custType:custType,
                    accountStatus:accountStatus,
                    createTimeStart:createTimeStart,
                    createTimeEnd:createTimeEnd,
                    email:email,
                    refPhone:refPhone,
                    userType:userType,
                    source:source
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
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
    })

    //重置密码
    $(".batchPassWord").click(function (obj) {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        if (checkStatus.data.length >= 2) {
            layer.msg("只能选择一个操作的用户", {icon: 0, time: 2000});
            return;
        }
        layer.confirm('确定将选中的用户重置密码 ？', {icon: 3, title: '确认'}, function (index) {
            var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dataIds = checkStatus.data[0].id;
            $.ajax({
                type: 'POST',
                url: 'userInfo/resetPassWord',
                data: JSON.stringify(dataIds),
                success: function (data) {
                    if (data.code == 200) {
                        if (data.data != "" && data.data != null) {
                            layer.close(indexMsg);
                            layer.confirm("新密码为:"+data.data);
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
	//数据导出
	$(".exportBtn").click(function () {
		var indexMsg = layer.msg('操作中，请稍候', {icon: 16, time: false, shade: 0.8});
		var phone = $(".phone").val();
		var userName=$(".userName").val();
		var realName=$(".realName").val();
		var custType=$(".custType").val();
		var createTime=$(".createTime").val();
		var source = $(".source").val();
		var accountStatus = $(".accountStatus").val();
		var email = $(".email").val();
		var refPhone = $(".refPhone").val();
		var userType = $(".userType").val();

		var createTimeStart;
		var createTimeEnd;
		if(createTime!=''){
			var strings = createTime.split(" - ");
			createTimeStart=strings[0];
			createTimeEnd=strings[1];
		}
		var jsonStr = {
			type: exportType,
			phone: phone,
			userName: userName,
			realName: realName,
            custType:custType,
			createTimeStart: createTimeStart,
			createTimeEnd: createTimeEnd,
            source:source,
            accountStatus:accountStatus,
            email:email,
            refPhone:refPhone,
            userType:userType
		}
		$.ajax({
			type: 'POST',
			url: 'excel/export',
			data: JSON.stringify(jsonStr),
			success: function (data) {
				if (data.code == 200) {
					if (data.data === true) {
						layer.close(indexMsg);
						layer.msg("请查看导出结果", {icon: 1, time: 2000});
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
		// });
	});

	//导出结果
	$(".exportLogBtn").click(function () {
		pageOperation = 1;
		var index = layui.layer.open({
			title: "导出结果",
			type: 2,
			area: ['80%', '80%'],
			content: "../export/exportLogList.html",
			success: function (layero, index) {
				setTimeout(function () {
					layui.layer.tips('点击此处返回用户信息列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		});
	});


    //账户信息
    $(".accountInfo").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        if (checkStatus.data.length >= 2) {
            layer.msg("只能选择一个操作的用户", {icon: 0, time: 2000});
            return;
        }
        for (var i = 0; i < checkStatus.data.length; i++) {
            dataId = checkStatus.data[i].id;
            phone = checkStatus.data[i].phone;
            realName = checkStatus.data[i].realName;
            accountStatus = checkStatus.data[i].accountStatus;
        }
        if(accountStatus!=1){
            layer.msg("用户未开通存管", {icon: 0, time: 2000});
            return;
        }
        pageOperation = 1;
        var index = layui.layer.open({
            title: "账户信息",
            type: 2,
            area: ['40%', '70%'],
            content: "accountInfo.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户信息列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    });
    //账户解冻
    $(".accountFreeze").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        if (checkStatus.data.length >= 2) {
            layer.msg("只能选择一个操作的用户", {icon: 0, time: 2000});
            return;
        }
        for (var i = 0; i < checkStatus.data.length; i++) {
            dataId = checkStatus.data[i].id;
            phone = checkStatus.data[i].phone;
            realName = checkStatus.data[i].realName;
            accountStatus = checkStatus.data[i].accountStatus;
        }
        if(accountStatus!=1){
            layer.msg("用户未开通存管", {icon: 0, time: 2000});
            return;
        }
        pageOperation = 1;
        var index = layui.layer.open({
            title: "账户解冻",
            type: 2,
            area: ['40%', '70%'],
            content: "accountFreeze.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户信息列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    });
    //账户冻结
    $(".accountThawing").click(function () {
        var checkStatus = table.checkStatus('UserInfoTable');
        if (checkStatus.data.length === 0) {
            layer.msg("请选择要操作的用户", {icon: 0, time: 2000});
            return;
        }
        if (checkStatus.data.length >= 2) {
            layer.msg("只能选择一个操作的用户", {icon: 0, time: 2000});
            return;
        }
        for (var i = 0; i < checkStatus.data.length; i++) {
            dataId = checkStatus.data[i].id;
            phone = checkStatus.data[i].phone;
            realName = checkStatus.data[i].realName;
            accountStatus = checkStatus.data[i].accountStatus;
        }
        if(accountStatus!=1){
            layer.msg("用户未开通存管", {icon: 0, time: 2000});
            return;
        }
        pageOperation = 2;
        var index = layui.layer.open({
            title: "账户冻结",
            type: 2,
            area: ['40%', '70%'],
            content: "accountFreeze.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回用户信息列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
    });

});
