var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate', 'laypage'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate;

    var userId = layui.data("yx_UMP").CUURENT_USER.id;
    //日期
    laydate.render({
        elem: '#date'
    });

    form.on('submit(addUser)', function (data) {
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            type: 'POST',
            url: "user/modifyMySelf",
            data: JSON.stringify(data.field),
            success: function (data) {
                if (data.code === 200) {
                    top.layer.close(index);
                    top.layer.msg("个人资料修改成功！", {icon: 1});
                } else {
                    top.layer.close(index);
                    layer.msg(data.message, {icon: 2});
                }
            }
        });

        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
    // 页面赋值
    $.ajax({
        type: "GET",
        url: "user/query/" + userId,
        success: function (data) {
            if (data.code === 200) {
                var rest = data.data;
                //循环实体
                for (var i in rest) {
                    // console.log(i + '='+ rest[i]+ ' '+$("." + i).attr("type"));
                    //文本框赋值
                    if ($("." + i).attr("type") === "text" || $("." + i).attr("type") === "hidden") {
                        if ($("." + i).attr("name") === "birthDay") {
                            rest[i] = rest[i].substring(0, 10);
                        }
                        $("." + i).val(rest[i]);
                        if (parent.pageOperation === 0) {
                            $("." + i).prop("placeholder", "");
                        }
                        //复选框改变状态
                    } else if ($("." + i).attr("type") === "checkbox") {
                        if ($("." + i).attr("name") === "enable" && rest[i] === 0) {
                            $("." + i).removeAttr("checked");
                            form.render('checkbox');
                        }
                    } else if ($("." + i).attr("type") === "radio") {
                        if ($("." + i).attr("name") === "sex") {
                            $("input[name='sex'][value='" + rest[i] + "']").attr("checked", true);
                            form.render('radio');
                        }
                    }
                }
            } else {
                top.layer.msg(data.message, {icon: 2});
            }
        },
        contentType: "application/json"
    });
});

