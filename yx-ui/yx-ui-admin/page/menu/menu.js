layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate', 'tree'], function() {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        //layedit = layui.layedit,
        //laydate = layui.laydate,
        $ = layui.jquery;
    menuId = parent.menuId;
    pageOpt = parent.pageOperation;
    //查看操作，设置页面元素不可编辑
    if (pageOpt === 0) {
        $(".layui-form input").attr("readonly", true);
        $(".layui-form input").attr("placeholder", "");
        $('.layui-form button').hide();
        $(".layui-form input:radio").attr("disabled", true);
    }
    //查看、编辑，初始化页面属性
    if (pageOpt === 0 || pageOpt === 2) {
        $("#id").val(menuId);
        $.ajax({
            type: "GET",
            url: "menu/query/" + menuId,
            success: function(data) {
                if (data.code == 200) {
                    //$(".layui-form input:radio[name='menuType']").eq(data.data.menuType).attr("checked",'checked');
                    $(':radio[name="menuType"]').eq(data.data.menuType).attr("checked", "checked");
                    form.render('radio');
                    changeForm({
                        "value": data.data.menuType.toString()
                    });
                    $(".layui-input.menuName").val(data.data.menuName);
                    $(".layui-input.parentId").val(data.data.parentId);
                    $(".layui-input.parentName").val(data.data.parentName);
                    $(".layui-input.request").val(data.data.request);
                    $(".layui-input.permission").val(data.data.permission);
                    $(".layui-input.sortNo").val(data.data.sortNo);
                    $(".layui-input.iconcls").val(data.data.iconcls);
                } else {
                    top.layer.close(index);
                    top.layer.msg("查询异常！", {
                        icon: 1
                    });
                }
            },
            contentType: "application/json"
        });

    }

    //新增、编辑
    if (pageOpt === 1 || pageOpt === 2) {
        //新增则提交到新增url，编辑则提交到修改url
        var url = pageOpt === 1 ? "menu/add" : "menu/modify";
        form.on("submit(btn_submit)", function(data) {
            var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(data.field),
                success: function(data) {
                    if (data.code == 200) {
                        //弹出loading
                        setTimeout(function() {
                            top.layer.close(index);
                            top.layer.msg("操作成功！", {
                                icon: 1
                            });
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        }, 1000);
                    } else {
                        top.layer.close(index);
                        layer.msg(data.message, {
                            icon: 2
                        });
                    }
                },
                contentType: "application/json"
            });

            return false;
        });
        //上级菜单选择事件监听
        $(".parentName").click(function() {
            layui.layer.open({
                type: 2,
                title: '选择上级菜单',
                shadeClose: true,
                shade: 0.5,
                area: ['320px', '70%'],
                content: 'menuTree.html'
            });
        });


    }

    form.verify({
        not_required_number: [
            /^(0|[1-9][0-9]*)?$/, '排序号必须为数字'
        ]
    });

    // 选择菜单树页面选中后回调函数
    menuTreeCallBack = function(menuId, menuName) {
        $("#parentId").val(menuId);
        $("#parentName").val(menuName);
    };

    //菜单类型选择事件监听，不同选项显示不同字段
    form.on('radio(menuType)', changeForm);

    function changeForm(data) {
        switch (data.value) {
            case '0':
                $(".layui-form-item.request").hide();
                $(".layui-input.request").attr("disabled", "disabled");
                $(".layui-form-item.permission").hide();
                $(".layui-input.permission").attr("disabled", "disabled");

                $(".layui-form-item.iconcls").show();
                $(".layui-input.iconcls").removeAttr("disabled");
                $(".layui-form-item.sortNo").show();
                $(".layui-input.sortNo").removeAttr("disabled");
                break;
            case '1':
                $(".layui-form-item.request").show();
                $(".layui-input.request").removeAttr("disabled");
                $(".layui-form-item.permission").show();
                $(".layui-input.permission").removeAttr("disabled");
                $(".layui-form-item.iconcls").show();
                $(".layui-input.iconcls").removeAttr("disabled");
                $(".layui-form-item.sortNo").show();
                $(".layui-input.sortNo").removeAttr("disabled");
                break;
            case '2':
                $(".layui-form-item.request").hide();
                $(".layui-input.request").attr("disabled", "disabled");
                $(".layui-form-item.permission").show();
                $(".layui-input.permission").removeAttr("disabled");
                $(".layui-form-item.iconcls").hide();
                $(".layui-input.iconcls").removeAttr("disabled");
                $(".layui-form-item.sortNo").hide();
                $(".layui-input.sortNo").removeAttr("disabled");
                break;
            default:
        }
    };


    var icon
    $("input[name='iconcls']").focus(function() {
        $(".maskbox").fadeIn(1000)
        $('.mask').show()
        $('.maskbox').show()
        $('.maskbox ul').on('click', 'li', function() {
            var fileDir = $(this).find('.fontclass').html()
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            icon = suffix.substring(1)
            $('#blockicon').val(icon)
            $('.mask').hide()
        })
    })
    $('#maskclose').click(function() {
        $('.mask').hide()
    })
})