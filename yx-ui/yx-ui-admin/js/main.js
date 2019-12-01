layui.config({
    base: "js/"
}).use(['element'], function () {
    var layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        $ = layui.jquery;

})
	var str = '欢迎使用，YX-信息技术平台-后台管理系统';
    var i = 0;
    function typing(){
        var divTyping = document.getElementById('typing');
        if (i <= str.length) {
            divTyping.innerHTML = str.slice(0, i++) + '|';
            setTimeout('typing()', 200);
        }
        else{
            divTyping.innerHTML = str;
        }
    }
    typing();