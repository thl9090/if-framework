var $;
layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate','upload', 'laypage','layedit'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        layedit=layui.layedit,
        submitUrl = parent.pageOperation === 1 ? "product/add" : "product/update";
    var urlIndex=0;

    function phones() {
        //相册层
        layui.layer.photos({
            photos:'#images'
            ,shift:5
        });
    }

    function phones1() {
        //相册层
        layui.layer.photos({
            photos:'#images1'
            ,shift:5
        });
    }

        //日期
        laydate.render({
            elem: '#stopBidTime'
        });

    //单张图片上传
    upload.render({
        elem: '#fileUpload1'
        ,url: 'sysOss/upload'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#images1').empty();
                $('#images1').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 100px;height: 100px;margin-left: 10px;">');
            });
        }
        ,done: function(res){
            //上传完毕
            console.log("结果："+res.data.url);

            $("#urls1").append('<input type="hidden" name="url" value="'+res.data.url+'">');

            phones1();
        }
    });

    //多图片上传
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
            phones();
        }
    });

    //视频上传
    upload.render({
        elem: '#videoUpload'
        ,url: 'sysOss/upload'
        ,accept: 'video' //视频
        ,done: function(res){

            //上传完毕
            console.log("结果："+res.data.url);
            $("#videoUrl").val(res.data.url);
            var videoHtml='<video style="width: 100%;height: 300px;"  controls="controls" autobuffer="autobuffer"  autoplay="autoplay" loop="loop" >'+
            '<source  src="'+res.data.url+'" ></source>'+
            '</video>';
            $("#videoHtml").empty();
            $("#videoHtml").html(videoHtml);

        }
    });






    //富文本编辑器
    //构建一个默认的编辑器

    layedit.set({
        uploadImage:{
            url:'sysOss/layEditUpload',
            type:'post'
        }

    });
    var index = layedit.build('lay_edit');
    //编辑器外部操作
    var active = {
        content: function(){
            alert(layedit.getContent(index)); //获取编辑器内容
        }
        ,text: function(){
            alert(layedit.getText(index)); //获取编辑器纯文本内容
        }
        ,selection: function(){//获取编辑器选中选中内容
            alert(layedit.getSelection(index));
        }
    };

    $('.site-demo-layedit').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });





});

