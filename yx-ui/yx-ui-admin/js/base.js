/*
 @author TangHuaLiang
 @Time: 2017-12-03
 @Tittle: base
 @Description: 基础配置
 */
layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    // 初始化页面权限
    var permissions = window.sessionStorage.getItem("yx_UMP_USER_PERMISSIONS");
    var permissionDoms = $("permission");
    permissionDoms.each(function () {
        if (permissions.indexOf($(this).attr("value")) < 0) {
            $(this).hide();
        }
    });

    // 设置jquery的ajax请求
    $.ajaxSetup({
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function (evt, request, settings) {
            // request.url = 'http://localhost:8089/' + request.url;
            request.url = '/' + request.url;
        },
        dataFilter: function (result) {
            try {
                var resultObj = JSON.parse(result);
                // 没有登录
                if (resultObj.code === 401) {
                    window.top.location.href = "/page/login/login.html";
                    return null;
                }
                if (resultObj.code === 300) {
                    alert("您的账号在其它地方登陆，请重新登陆！如非本人操作请及时修改登陆密码,或者联系平台系统管理员！");
                    window.top.location.href = "/page/login/login.html";
                    return null;
                }
                return result;
            } catch (e) {
                return result;
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            switch (jqXHR.status) {
                case (404):
                    toaster.clear('*');
                    toaster.pop('error', '', "未找到请求的资源");
                    break;
                case (405):
                    window.location.href = "page/login/login.html";
                    break;
            }
        }
    });

    var obj = {
        loading: function (layer) {
            var loadingIndex = layer.msg('数据请求中', {
                icon: 16,
                shade: 0.01,
                time: 30000
            });
            return loadingIndex;
        },
        fastClickCheck: function (elem) {
            if ($(elem).attr("clickPassValue") === "1") {
                $(elem).attr("clickPassValue", "0");
                setTimeout(function () {
                    $(elem).attr("clickPassValue", "1");
                }, 5000);
                return true;
            } else {
                layui.layer.tips("您点击太快，稍等五秒再点击吧！", elem, {tips: [3, '#009688'], time: 1000});
                return false;
            }
        }
    };

    exports("base", obj);
})