layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', "treecheck"], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        submitUrl = "dic/add";

    // 获取父页面的pageOperation，判断是查看、添加、修改
    if (parent.pageOperation === 1) { // 添加
    } else if (parent.pageOperation === 2) { // 修改
        submitUrl = "dic/modify";
    } else { // 查看
        $(".layui-form input").prop("disabled", true);
        $('.layui-form button').hide();
    }

    if (parent.pageOperation === 2 || parent.pageOperation === 0) {
        $("#id").val(parent.checkedDicId);
        // 查询字典
        $.ajax({
            type: 'POST',
            url: 'dic/query',
            data: JSON.stringify(parent.checkedDicId),
            success: function (data) {
                if (data.code === 200) {
                    if (data.data !== null) {
                        $("#type").val(data.data.type);
                        $("#codeText").val(data.data.codeText);
                        $("#code").val(data.data.code);
                        $("#sortNo").val(data.data.sortNo);
                        $(':radio[name="enable"][value="' + data.data.enable + '"]').attr("checked", "checked");
                        form.render('radio');
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
        $.ajax({
            type: 'POST',
            url: submitUrl,
            data: JSON.stringify(data.field),
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    if (parent.pageOperation === 1) {
                        // 重置表单
                        // $("#dicForm")[0].reset();
                        top.layer.msg('字典添加成功', {icon: 1});
                    } else {
                        top.layer.msg('字典修改成功', {icon: 1});
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
});

