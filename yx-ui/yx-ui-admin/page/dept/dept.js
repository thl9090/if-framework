layui.config({
    base : "../../js/"
}).use(['base','form','layer','jquery','laydate','tree'],function(){
	var base = layui.base,
		form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
        //新增则提交到新增url，编辑则提交到修改url
        submitUrl = parent.pageOperation === 1?"dept/add":"dept/modify";
        submitType = parent.pageOperation === 1?"POST":"PUT";
	    deptParentId = "";
    //新增、编辑跳转则加载部门树
    if(parent.pageOperation===1||parent.pageOperation===2) {
        form.on("submit(adddept)",function(data){
            var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            // alert(JSON.stringify(data.field));
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
    if(parent.pageOperation===2) {
        deptParentId = parent.deptId;
        $.ajax({
            type: "GET",
            url: "dept/query/"+parent.deptId,
            success: function(data){
                if(data.code==200){
                    var rest = data.data;
                    //循环实体
                    for (var i in rest) {
                        // console.log(i + '='+ rest[i]+ ' '+$("." + i).attr("type"));
                        //文本框赋值
                        if($("." + i).attr("type")=="text"||$("." + i).attr("type")=="hidden"){
                            $("." + i).val(rest[i]);
                            // if(parent.pageOperation===0){
                            //     $("." + i).prop("placeholder","");
                            // }
                        //复选框改变状态
                        }else if($("." + i).attr("type")=="checkbox"){
                            if(rest[i]==0){
                                $("." + i).removeAttr("checked");
                                form.render('checkbox');
                            }
                        }
                    }
                }else{
                    top.layer.msg("查询异常！", {icon: 2});
                }
            },
            contentType: "application/json"
        });
        // if(pageOpt=='detail') {
        //     $(".layui-form input").prop("readonly", true);
        //     $(".enable").prop("disabled", "disabled");
        //     $('.layui-form button').hide();
        // }
    }
    $(".parentName").click(function(){
        layui.layer.open({
            type: 2,
            title: '选择部门',
            shadeClose: true,
            shade: 0.5,
            area: ['320px', '70%'],
            content: 'deptTree.html' //iframe的url
        });
    });

    // 选择部门树页面选中后回调函数
    deptTreeCallBack = function (deptId, deptName) {
        $("#parentId").val(deptId);
        $("#parentName").val(deptName);
    }
})
