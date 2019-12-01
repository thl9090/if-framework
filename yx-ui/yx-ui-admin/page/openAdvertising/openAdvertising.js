layui.config({
    base : "../../js/"
}).use(['base','form','layer','jquery','laydate','tree', 'upload'],function(){
	var base = layui.base,
		form = layui.form,
		upload = layui.upload,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
        //新增则提交到新增url，编辑则提交到修改url
        submitUrl = parent.pageOperation === 1?"sysOpenAdvertising/add":"sysOpenAdvertising/update";
        submitType = parent.pageOperation === 1?"POST":"POST";
	    deptParentId = "";
    function phones() {
        //相册层
        layui.layer.photos({
            photos:'#pdiv'
            ,shift:5
        });
    }
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
            url: "sysOpenAdvertising/select/"+parent.dataId,
            success: function(data){
                if(data.code==200){
                    form.render();
                    var rest = data.data;
                    console.log(data);
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
                            if ($("." + i).attr("name") === "status") {
                                $("input[name='status'][value='" + rest[i] + "']").attr("checked", true);
                                form.render('radio');
                            }
                        }
                    }
                    if(!$.isEmptyObject(rest.filePath)){
                        $('#fileShow').empty();
                        $('#filePath').attr('value', rest.filePath); //图片链接（base64）
                        $('#fileShow').append('<img src="'+ rest.filePath +' " class="layui-upload-img" style="width: 100px;height: 100px;margin-left: 10px;">');
                        phones();
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
        form.on("submit(adddept)",function(data){
            /*if($.isEmptyObject(data.filePath)){
                top.layer.msg("图片不能为空！", {icon: 1});
                return;
            }*/
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

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#fileUpload'
        ,url: 'sysOss/upload'
        ,done: function(res){
            //上传成功
            if(res.code == 200){
                layer.msg(res.message, {icon: 1});
                $('#fileShow').empty();
                $('#filePath').attr('value', res.data.url); //图片链接（base64）
                $('#fileShow').append('<img src="'+ res.data.url +' " class="layui-upload-img" style="width: 100px;height: 100px;margin-left: 10px;">');
                phones();
            }else{
                layer.msg(res.message, {icon: 2});
            }
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#fileShow');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    //自定义验证规则
    form.verify({
        filePath: function(value){
            if($.isEmptyObject(value)){
                return '请上传图片';
            }
        }
    });

    //重置
    $(".layui-btn-primary").click(function () {
        $('#fileShow').empty();
        $('#filePath').attr('value', ''); //图片链接（base64）
    });

})
