layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', "treecheck"], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        treeUrl = "menu/funcTree",
        treeData = "",
        treeCheckBoxAllDisable = false,
        submitUrl = "role/add";

    // 获取父页面的pageOperation，判断是查看、添加、修改
    if (parent.pageOperation === 1) { // 添加角色
    } else if (parent.pageOperation === 2) { // 修改角色
        treeUrl = "menu/roleFuncTree";
        treeData = JSON.stringify(parent.checkedRoleId);
        submitUrl = "role/modify";
    } else { // 查看角色
        treeUrl = "menu/roleFuncTree";
        treeData = JSON.stringify(parent.checkedRoleId);
        treeCheckBoxAllDisable = true;

        $(".layui-form input").prop("disabled", true);
        $('.layui-form button').hide();
    }

    // 初始化功能权限tree
    var tree = layui.treecheck({
        elem: 'funcAuthTree', // 放xtree的容器，id，不要带#号（必填）
        form: form, // layui form对象 （必填）
        url: treeUrl, // 服务端地址（必填）
        data: treeData, // 参数
        isopen: true, // 初次加载时全部展开，默认true
        color: "#000", // 图标颜色
        icon: { // 图标样式 （必填，不填会出点问题）
            open: "&#xe7a0;", // 节点打开的图标
            close: "&#xe622;", // 节点关闭的图标
            end: "&#xe621;" // 末尾节点的图标
        },
        allDisable: treeCheckBoxAllDisable // 全部checkbox是否不可用
    });

    if (parent.pageOperation === 2 || parent.pageOperation === 0) {
        $("#id").val(parent.checkedRoleId);
        // 查询角色
        $.ajax({
            type: 'POST',
            url: 'role/query',
            data: JSON.stringify(parent.checkedRoleId),
            success: function (data) {
                if (data.code === 200) {
                    if (data.data !== null) {
                        $("#roleName").val(data.data.roleName);
                        $("#deptId").val(data.data.deptId);
                        $("#deptName").val(data.data.deptName);
                        $("#remark").val(data.data.remark);
                    }
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

    // 监听submit
    form.on('submit(addFilter)', function (data) {
        var loadingIndex = base.loading(layer);
        var menuIdArray = tree.GetAllCheckedValue();
        data.field["menuIdList"] = menuIdArray;
        $.ajax({
            type: 'POST',
            url: submitUrl,
            data: JSON.stringify(data.field),
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    if (parent.pageOperation === 1) {
                        // 重置表单
                        // $("#roleForm")[0].reset();
                        top.layer.msg('角色添加成功', {icon: 1});
                    } else {
                        top.layer.msg('角色修改成功', {icon: 1});
                    }
                    layer.closeAll("iframe");
                    // 刷新父页面
                    parent.location.reload();
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });

        // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

    // 监听部门文本框单击事件
    $("#deptName").click(function () {
        // 不直接使用layer.open而使用layui.layer.open，是因为layer.open实际是调用父窗口的layer对象
        layui.layer.open({
            type: 2,
            title: '选择部门',
            shadeClose: true,
            shade: 0.5,
            area: ['320px', '70%'],
            content: '/page/dept/deptTree.html' //iframe的url
        });
    });

    // 选择部门树页面选中后回调函数
    deptTreeCallBack = function (deptId, deptName) {
        $("#deptId").val(deptId);
        $("#deptName").val(deptName);
    }
});

