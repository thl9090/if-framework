layui.config({
    base : "../../js/"
}).use(['base','form','layer','jquery'],function() {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //新增则提交到新增url，编辑则提交到修改url
    //submitUrl = "product/flowProduct";

    form.on("submit(loanProduct)", function (data) {

        var id = parent.dataId;
        var smsCode=$(".smsCode").val();
        var captchaId=$(".captchaId").val();
        if(captchaId==null||captchaId==''){
            layer.msg("没有获取到验证码id,请重新获取短信验证码！", {icon: 2});
            return false;
        }

        var data = {
            id: id,
            smsCode:smsCode,
            captchaId:captchaId
        };

        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            type: 'POST',
            url: 'product/loan',
            data: JSON.stringify(data),
            success: function (data) {
                console.log(data);
                top.layer.close(index);
                if (data.code === 200) {

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

    $("#sendSms").click(function () {

        $.ajax({
            type: 'POST',
            url: 'common/sendSms',
            async:false,
            success: function (data) {
                if (data.code === 200) {
                    $(".captchaId").val(data.data.captchaId);

                    setTimeout(function () {
                        top.layer.msg("短信发送成功！", {icon: 1});
                    }, 500);

                    $("#sendSms").css({'display': 'none'});
                    $(".smsTime").css({'display': 'inline-block'});
                    getSmsCodeTime();
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    });

    // 短信验证码时间
    var smsCodeTime = 120;
    function getSmsCodeTime () {
        if (smsCodeTime === 0) {
            smsCodeTime = 120
            $("#sendSms").css({'display': 'inline-block'});
            $(".smsTime").css({'display': 'none'});
            $('.smsTime font').html(smsCodeTime)
        } else {
            smsCodeTime--
            $('.smsTime font').html(smsCodeTime)
            setTimeout(() => {
                getSmsCodeTime()
            }, 1000)
        }
    }
});
