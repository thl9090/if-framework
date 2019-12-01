var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate', 'laypage'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        submitUrl = parent.pageOperation === 1 ? "userInfo/add" : "userInfo/update";
        var check=$('input:radio[name="userType"]:checked').val();

        if(check=='1'){
            $('#companyName').hide()
        }
        $('.layui-unselect').on('click', function () {
            var val=$(this).find('div').text()
            if(val=='企业用户'){
                $('#companyName').show()
            }else{
                $('#companyName').hide()
            }
        })

    if (parent.pageOperation === 1 || parent.pageOperation === 2) {


        form.on('submit(addUser)', function (data) {
            if (typeof(data.field.enable) === "undefined" || data.field.enable === 'undefined') {
                data.field.enable = 0;
            }



            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                type: 'POST',
                url: submitUrl,
                data: JSON.stringify(data.field),
                success: function (data) {
                    if (data.code === 200) {
                        top.layer.close(index);
                        if (parent.pageOperation === 1) {
                            // // 重置表单
                            // $("#roleDiv").empty();
                            // form.render('checkbox');
                            // $("#userForm")[0].reset();
                            layer.msg('用户添加成功', {icon: 1});
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        } else if (parent.pageOperation === 2) {
                            setTimeout(function () {
                                top.layer.msg("用户修改成功！", {icon: 1});
                                layer.closeAll("iframe");
                                //刷新父页面
                                parent.location.reload();
                            }, 500);
                        }
                    } else {
                        top.layer.close(index);
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });

            //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            return false;
        });


    }

    if (parent.pageOperation === 0 || parent.pageOperation === 2) {
        // 页面赋值
        $.ajax({
            type: "GET",
            url: "userInfo/select/" + parent.dataId,
            success: function (data) {
                if (data.code === 200) {
                    var rest = data.data;
                    //循环实体
                    for (var i in rest) {
                        // console.log(i + '='+ rest[i]+ ' '+$("." + i).attr("type"));
                        //文本框赋值
                        if ($("." + i).attr("type") === "text" || $("." + i).attr("type") === "hidden") {
                            $("." + i).val(rest[i]);
                            if (parent.pageOperation === 0) {
                                $("." + i).prop("placeholder", "");
                            }
                            //复选框改变状态
                        } else if ($("." + i).attr("type") === "checkbox") {
                            // if ($("." + i).attr("name") === "enable" && rest[i] === 0) {
                            //     $("." + i).removeAttr("checked");
                            //     form.render('checkbox');
                            // }
                        } else if ($("." + i).attr("type") === "radio") {
                            if ($("." + i).attr("name") === "userType") {
                                $("input[name='userType'][value='" + rest[i] + "']").attr("checked", true);
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

    }

    if (parent.pageOperation === 0) {
        $(".layui-form input").prop("readonly", true);
        $(".userType").prop("disabled", "disabled");
        $('.layui-form button').hide();
    }





});

