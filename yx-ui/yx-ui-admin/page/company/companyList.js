layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'table'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        table = layui.table;
    //页面操作：0：查看，1：添加，2：修改
    pageOperation = 0;
    dataId = 1;
    deptId = "";

    $.ajax({
        type: "get",
        url: "company/select/" + dataId,
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
                        if (pageOperation === 0) {
                            $("." + i).prop("placeholder", "");
                        }
                        //复选框改变状态
                    } else if ($("." + i).attr("type") === "checkbox") {
                        if ($("." + i).attr("name") === "enable" && rest[i] === 0) {
                            $("." + i).removeAttr("checked");
                            form.render('checkbox');
                        }
                    } else if ($("." + i).attr("type") === "radio") {
                        if ($("." + i).attr("name") === "type") {
                            $("input[name='type'][value='" + rest[i] + "']").attr("checked", true);
                            form.render('radio');
                        }
                        if ($("." + i).attr("name") === "status") {
                            $("input[name='status'][value='" + rest[i] + "']").attr("checked", true);
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

    $(".layui-form input").prop("readonly", true);
    $(".layui-form textarea").prop("readonly", true);
    $(".type").prop("disabled", "disabled");
    $(".status").prop("disabled", "disabled");
    $(".role").prop("disabled", "disabled");
    $("#addBtn1").hide();
    $("#primary").hide();

    form.on('submit(addBtn)', function (data) {
        var index = layui.layer.open({
            title: "编辑公司信息",
            type: 2,
            content: "company.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
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

        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

    form.verify({
        telPhone: function(value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp(/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/).test(value)) {
                return '请输入正确座机号码！';
            }
        },
        url: function(value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*$").test(value)) {
                return '请输入正确的域名！';
            }
        },
        servicePhone : function(value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp(/^400-([0-9]){1}([0-9-]{7})$/).test(value)) {
                return '请输入正确的电话号码！';
            }
        }

    });
});
