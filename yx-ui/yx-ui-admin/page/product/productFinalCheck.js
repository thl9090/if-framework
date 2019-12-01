var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate','upload', 'laypage'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        submitUrl = "product/finalCheck";
    var urlIndex=0;

    if (parent.pageOperation === 1 || parent.pageOperation === 2) {


        form.on('submit(addBtn)', function (data) {

            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            var dataForm=data.field;
            console.log("==================:"+JSON.stringify(dataForm));

            $.ajax({
                type: 'POST',
                url: submitUrl,
                data: JSON.stringify(dataForm),
                success: function (data) {
                    if (data.code === 200) {
                        top.layer.close(index);
                        // if (parent.pageOperation === 1) {
                        //     layer.msg('添加成功', {icon: 1});
                        //     layer.closeAll("iframe");
                        //     //刷新父页面
                        //     parent.location.reload();
                        // } else if (parent.pageOperation === 2) {
                            setTimeout(function () {
                                top.layer.msg("操作成功！", {icon: 1});
                                layer.closeAll("iframe");
                                //刷新父页面
                                parent.location.reload();
                            }, 500);
                        // }
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
            url: "product/select/" + parent.dataId,
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
                        } else if ($("." + i).attr("type") === "checkbox") {
                            //复选框赋值

                            // if ($("." + i).attr("name") === "enable" && rest[i] === 0) {
                            //     $("." + i).removeAttr("checked");
                            //     form.render('checkbox');
                            // }
                        } else if ($("." + i).attr("type") === "radio") {
                            //单选框赋值

                            // if ($("." + i).attr("name") === "sex") {
                            //     $("input[name='sex'][value='" + rest[i] + "']").attr("checked", true);
                            //     form.render('radio');
                            // }
                        }else{
                            // //下拉选项赋值
                            // if($("." + i).attr("name") === "isCompany"){
                            //     $(".isCompany option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "repurchaseMode"){
                            //     $(".repurchaseMode option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "assetType"){
                            //     $(".assetType option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "useType"){
                            //     $(".useType option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "isTest"){
                            //     $(".isTest option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "isActivity"){
                            //     $(".isActivity option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            // if($("." + i).attr("name") === "isTransfer"){
                            //     $(".isTransfer option[value='" + rest[i] + "']").attr("selected", true);
                            //     form.render('select');
                            // }
                            //
                            // //文本域赋值
                            // if($("." + i).attr("name") === "borrowDesc"){
                            //     $("." + i).val(rest[i]);
                            // }
                            // if($("." + i).attr("name") === "guaranteeDesc"){
                            //     $("." + i).val(rest[i]);
                            // }
                            // if($("." + i).attr("name") === "riskDesc"){
                            //     $("." + i).val(rest[i]);
                            // }
                            if($("." + i).attr("name") === "firstCheckRemark"){
                                $("." + i).val(rest[i]);
                            }
                        }
                    }
                    //图片回显
                    // var fileList=data.data.fileList;
                    // for(var i in fileList){
                    //     $('#images').append('<img src="'+ fileList[i] +'" alt="" class="layui-upload-img" style="width: 100px;height: 100px;margin-left: 10px;">');
                    // }


                } else {
                    top.layer.msg(data.message, {icon: 2});
                }
            },
            contentType: "application/json"
        });

    }

    // if (parent.pageOperation === 0) {
    //     $(".layui-form input").prop("readonly", true);
    //     $(".layui-form select").prop("disabled", true);
    //     $('.layui-form button').hide();
    // }

});

