var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate','upload', 'laypage'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate;
        pageOperation = 2;
    parent.dataId = 1;
    upload = layui.upload,
        submitUrl = pageOperation === 1 ? "company/add" : "company/update";
    var urlIndex=0;

    if (pageOperation === 1 || pageOperation === 2) {
        //日期
        laydate.render({
            elem: '#stopBidTime'
        });

        //图片上传
        upload.render({
            elem: '#fileUpload'
            ,url: 'sysOss/upload'
            ,multiple: true
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){

                    $('#images').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 100px;height: 100px;margin-left: 10px;">');
                });
            }
            ,done: function(res){
                //上传完毕
                console.log("结果："+res.data.url);
                $("#urls").append('<input type="hidden" name="url" value="'+res.data.url+'">');
            }
        });


        form.on('submit(addBtn)', function (data) {
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            var fileList=[];
            $("input[name='url']").each(function () {
                fileList.push($(this).val());
            });
            var dataForm=data.field;
            dataForm.fileList=fileList;

            $.ajax({
                type: 'POST',
                url: submitUrl,
                data: JSON.stringify(dataForm),
                success: function (data) {
                    if (data.code === 200) {
                        top.layer.close(index);
                        if (pageOperation === 1) {
                            layer.msg('添加成功', {icon: 1});
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        } else if (pageOperation === 2) {
                            setTimeout(function () {
                                top.layer.msg("修改成功！", {icon: 1});
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
    if (pageOperation === 0 || pageOperation === 2) {
        // 页面赋值
        $.ajax({
            type: "get",
            url: "company/select/" + parent.dataId,
            success: function (data) {
                if (data.code === 200) {
                    var rest = data.data;
                    //循环实体
                    for (var i in rest) {
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
    }

    if (pageOperation === 0) {
        $(".layui-form input").prop("readonly", true);
        $(".layui-form textarea").prop("readonly", true);
        $(".type").prop("disabled", "disabled");
        $(".status").prop("disabled", "disabled");
        $(".role").prop("disabled", "disabled");
        $("#addBtn").hide();
        $("#primary").hide();
    }

    form.verify({
        /*telPhone: function(value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp(/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/).test(value)) {
                return '请输入正确座机号码！';
            }
        },*/
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

