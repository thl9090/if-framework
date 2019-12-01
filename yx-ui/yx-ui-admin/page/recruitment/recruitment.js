layui.config({
    base : "../../js/"
}).use(['base','form','layer','jquery','laydate','tree','layedit'],function(){
	var base = layui.base,
		form = layui.form,
        laydate = layui.laydate,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
        layedit=layui.layedit,
        $ = layui.jquery;
        //新增则提交到新增url，编辑则提交到修改url
        submitUrl = parent.pageOperation === 1?"sysRecruitment/add":"sysRecruitment/update";
        submitType = parent.pageOperation === 1?"POST":"POST";
	    deptParentId = "";


    //富文本编辑器 构建一个默认的编辑器
    var active = {
        content: function(){
            layedit.getContent(index); //获取编辑器内容
            $("#content").val(content)
        }
    };
    layedit.set({
        uploadImage:{
            url:'sysOss/layEditUpload',
            type:'post'
        }
    });
    var index = layedit.build('content');
    //查看操作，设置页面元素不可编辑
    if (parent.pageOperation === 0) {
        $(".layui-form input").attr("readonly", true);
        $(".layui-form input").attr("placeholder", "");
        $('.layui-form button').hide();
        $(".layui-form input:radio").attr("disabled", true);
    }
    //查看 修改 页面回显
    if(parent.pageOperation === 0 || parent.pageOperation === 2) {
        deptParentId = parent.dataId;
        $.ajax({
            type: "GET",
            url: "sysRecruitment/select/"+parent.dataId,
            success: function(data){
                if(data.code==200){
                    form.render();
                    var rest = data.data;
                    //循环实体
                    for (var i in rest) {
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
                            if ($("." + i).attr("name") === "status") {
                                $("input[name='status'][value='" + rest[i] + "']").attr("checked", true);
                                form.render('radio');
                            }
                        }
                        //var index111 = layedit.build('content');
                        $("#content").html(rest.content);
                        layedit.build('content');
                    }
                }else{
                    top.layer.msg("查询异常！", {icon: 2});
                }
            },
            contentType: "application/json"
        });
    }

    //新增数据库、修改数据库
    if(parent.pageOperation===1 || parent.pageOperation===2) {
        //日期
        laydate.render({
            elem: '#modelTime'
        });
        form.on("submit(adddept)",function(data){
            if(parent.pageOperation===2){
                data.field.content=$("#LAY_layedit_16").contents().find("body").html()
            }
            var editIndex2 = layedit.build('content');
            var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            $.ajax({
                type: submitType,
                url: submitUrl,
                data: JSON.stringify(data.field),
                success: function(data){
                    if(data.code==200){
                        //弹出loading
                        setTimeout(function(){
                            top.layer.close(index);
                            top.layer.msg("操作成功！", {icon: 1});
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        },500);
                    }else{
                        top.layer.close(index);
                        layer.msg(data.message, {icon: 2});
                    }
                },
                contentType: "application/json"
            });
            return false;
        });
    }
    //重置
    $(".layui-btn-primary").click(function () {
        $("#content").html('');
        layedit.build('content');
    });
    //自定义验证规则
    form.verify({
        news_name: function(value){
            if(value.length < 5){
                return '标题至少得5个字符啊';
            }
        }
        ,article_desc: function(value){
            layedit.sync(index);
        }
    });
})
