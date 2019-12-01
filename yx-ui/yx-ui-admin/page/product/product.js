var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate', 'upload', 'laypage'], function() {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        submitUrl = parent.pageOperation === 1 ? "product/add" : "product/update";
    var urlIndex = 0;


    form.on('select(isAddProfit)', function(data) {
        var val = data.value;
        val == 1 ? $('#addProfitDiv').show() : $('#addProfitDiv').hide();
        $('#addProfit').attr('lay-verify', val == 1 ? 'required' : '');
    });

    form.on('select(isJoinSmart)', function(data) {
        var val = data.value;
        val == 1 ? $('.joinSmartDiv').show() : $('.joinSmartDiv').hide();
        $('#smartName').attr('lay-verify', val == 1 ? 'required' : '');
        $('#smartBidPlanId').attr('lay-verify', val == 1 ? 'required' : '');
    });

    $(".isJoinSmart").click();

    function phones() {
        //相册层
        layui.layer.photos({
            photos: '#images',
            shift: 5
        });
    }


    if (parent.pageOperation === 1 || parent.pageOperation === 2) {
        //日期
        laydate.render({
            elem: '#stopBidTime'
        });

        //图片上传
        upload.render({
            elem: '#fileUpload',
            url: 'sysOss/upload',
            multiple: true,
            before: function(obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result) {
                    // var imgHtml = '';
                    // imgHtml += '<div class="upload-img">'+
                    // '<img layer-src=" '+ result + '" src="' + result + '" alt="' + file.name + '" class="layui-upload-img" />'+
                    // '<div class="delete">'+
                    // '<span class="layui-btn layui-btn-sm delete-btn"><i class="layui-icon"></i></span>'+
                    // '<span class="layui-btn layui-btn-sm advance-btn"><i class="layui-icon"></i></span>'+
                    // '<span class="layui-btn layui-btn-sm recede-btn"><i class="layui-icon"></i></span>'+
                    // '</div></div>';
                    // $('#images').append(imgHtml);
                });
            },
            done: function(res) {
                //上传完毕
                console.log("结果：" + res.data.url);

                var imgHtml = '';
                imgHtml += '<div class="upload-img">' +
                    '<img layer-src=" ' + res.data.url + '" src="' + res.data.url + '" alt="' + 11 + '" class="layui-upload-img" />' +
                    '<div class="delete">' +
                    '<span class="layui-btn layui-btn-sm delete-btn"><i class="layui-icon"></i></span>' +
                    '<span class="layui-btn layui-btn-sm advance-btn"><i class="layui-icon"></i></span>' +
                    '<span class="layui-btn layui-btn-sm recede-btn"><i class="layui-icon"></i></span>' +
                    '</div>' +
                    '<input type="hidden" name="url" value="' + res.data.url + '">' +
                    '</div>';
                $('#images').append(imgHtml);

                phones();
            }
        });


        form.on('submit(addBtn)', function(data) {

            var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            var fileList = [];
            $("input[name='url']").each(function() {
                fileList.push($(this).val());
            });

            var dataForm = data.field;
            dataForm.fileList = fileList;
            $.ajax({
                type: 'POST',
                url: submitUrl,
                data: JSON.stringify(dataForm),
                success: function(data) {
                    if (data.code === 200) {
                        top.layer.close(index);
                        if (parent.pageOperation === 1) {
                            layer.msg('添加成功', {
                                icon: 1
                            });
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        } else if (parent.pageOperation === 2) {
                            setTimeout(function() {
                                top.layer.msg("修改成功！", {
                                    icon: 1
                                });
                                layer.closeAll("iframe");
                                //刷新父页面
                                parent.location.reload();
                            }, 500);
                        }
                    } else {
                        top.layer.close(index);
                        layer.msg(data.message, {
                            icon: 2
                        });
                    }
                }
            });

            //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            return false;
        });

    }


    //图片删除按钮滑过展示，离开隐藏
    $("#images").on('mouseenter', ".upload-img", function() {
        $(this).find(".delete").animate({
            bottom: 0
        });
    }).on('mouseleave', ".upload-img", function() {
        $(this).find(".delete").animate({
            bottom: '-40px'
        })
    });
    // 图片删除
    $("#images").on('click', ".upload-img .delete-btn", function() {
        $(this).parents('.upload-img').remove();
    });
    // 图片向前/向后移动
    $("#images").on('click', ".upload-img .advance-btn", function() {
        $(this).parents('.upload-img').prev().before($(this).parents('.upload-img'));
    });
    $("#images").on('click', ".upload-img .recede-btn", function() {
        $(this).parents('.upload-img').next().after($(this).parents('.upload-img'));
    });


    if (parent.pageOperation === 0 || parent.pageOperation === 2) {
        // 页面赋值
        $.ajax({
            type: "GET",
            url: "product/select/" + parent.dataId,
            success: function(data) {
                if (data.code === 200) {
                    var rest = data.data;
                    console.log(rest);
                    //循环实体
                    for (var i in rest) {
                        // console.log(i + '='+ rest[i]+ ' '+$("." + i).attr("type"));
                        //文本框赋值
                        if ($("." + i).attr("type") === "text" ||
                            $("." + i).attr("type") === "number" ||
                            $("." + i).attr("type") === "hidden") {
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
                        } else {
                            //下拉选项赋值
                            $('.' + i + ' option[value=\'' + rest[i] + '\']').attr("selected", true);
                            form.render('select');
                            if (i == 'isAddProfit') {
                                rest[i] == 1 ? $('#addProfitDiv').show() : $('#addProfitDiv').hide();
                                $('#addProfit').attr('lay-verify', rest[i] == 1 ? 'required' : '');
                            }

                            if (i == 'isJoinSmart') {
                                rest[i] == 1 ? $('.joinSmartDiv').show() : $('.joinSmartDiv').hide();
                                $('#smartBidPlanId').attr('lay-verify', rest[i] == 1 ? 'required' : '');
                                $('#smartName').attr('lay-verify', rest[i] == 1 ? 'required' : '');
                            }

                            //文本域赋值
                            if ($("." + i).attr("name") === "borrowDesc") {
                                $("." + i).val(rest[i]);
                            }
                            if ($("." + i).attr("name") === "guaranteeDesc") {
                                $("." + i).val(rest[i]);
                            }
                            if ($("." + i).attr("name") === "riskDesc") {
                                $("." + i).val(rest[i]);
                            }
                        }
                    }
                    //图片回显
                    var fileList = data.data.fileList;
                    for (var i in fileList) {
                        var imgHtml = '';
                        imgHtml += '<div class="upload-img">' +
                            '<img layer-src="' + fileList[i] + '" src="' + fileList[i] + '" alt="" class="layui-upload-img">' +
                            '<div class="delete">' +
                            '<span class="layui-btn layui-btn-sm delete-btn"><i class="layui-icon"></i></span>' +
                            '<span class="layui-btn layui-btn-sm advance-btn"><i class="layui-icon"></i></span>' +
                            '<span class="layui-btn layui-btn-sm recede-btn"><i class="layui-icon"></i></span>' +
                            '</div>' +
                            '<input type="hidden" name="url" value="' + fileList[i] + '">' +
                            '</div>';
                        $('#images').append(imgHtml);

                    }
                    phones();

                } else {
                    top.layer.msg(data.message, {
                        icon: 2
                    });
                }
            },
            contentType: "application/json"
        });

    }

    if (parent.pageOperation === 0) {
        $(".layui-form input").prop("disabled", true);
        $(".layui-form select").prop("disabled", true);
        $(".layui-form textarea").prop("disabled", true);
        $('.layui-form button').hide();
    }


    //弹框选择原始借款人
    $(".oriBorrowers").click(function() {
        layui.layer.open({
            type: 2,
            title: '选择借款人',
            area: ['80%', '80%'],
            content: "selectBorrower.html",
        });
    });

    // 选择借款人后回调函数
    selectBorrowerCallBack = function(dataIds, dataNames) {
        var oriBorrowerIds = "";
        var oriBorrowers = "";
        for (var i = 0; i < dataIds.length; i++) {
            oriBorrowerIds += dataIds[i] + ",";
        }
        for (var j = 0; j < dataNames.length; j++) {
            oriBorrowers += dataNames[j] + ",";
        }

        $("#oriBorrowerIds").val(oriBorrowerIds);
        $("#oriBorrowers").val(oriBorrowers);

    };

    //弹框选择受托人
    $(".borrowUserName").click(function() {
        layui.layer.open({
            type: 2,
            title: '选择受托人',
            area: ['80%', '80%'],
            content: "selectDepositary.html",
        });
    });

    // 选择受托人后回调函数
    selectDepositaryCallBack = function(borrowUserId, borrowUserName) {
        $("#borrowUserId").val(borrowUserId);
        $("#borrowUserName").val(borrowUserName);

    };

    //弹框选择保障机构
    $(".guaranteeName").click(function() {
        layui.layer.open({
            type: 2,
            title: '选择借款推荐方',
            area: ['80%', '80%'],
            content: "selectGuarantee.html",
        });
    });

    // 选择保障机构后回调函数
    selcetGuaranteeCallBack = function(guaranteeId, guaranteeName) {
        $("#guaranteeId").val(guaranteeId);
        $("#guaranteeName").val(guaranteeName);

    };

    //弹框选择智投
    $(".smartName").click(function() {
        layui.layer.open({
            type: 2,
            title: '选择智投项目',
            area: ['80%', '80%'],
            content: "selectSmartBid.html",
        });
    });

    // 选择受托人后回调函数
    selectSmartBidCallBack = function(smartBidPlanId, smartName) {
        $("#smartBidPlanId").val(smartBidPlanId);
        $("#smartName").val(smartName);

    };


    form.on('select(fit)', function(data) {
        if (data.value == '1' || data.value == '2') {
            $('#unit').text('月')
        } else if (data.value == '3') {
            $('#unit').text('日')
        }
    });
});