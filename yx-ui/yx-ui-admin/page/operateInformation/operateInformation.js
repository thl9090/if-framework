layui.config({
    base : "../../js/"
}).use(['base','form','layer','jquery','laydate','tree','laypage','layedit', 'upload'],function(){
    var base = layui.base,
        form = layui.form,
        upload = layui.upload,
        layedit=layui.layedit,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    laydate = layui.laydate;
    //新增则提交到新增url，编辑则提交到修改url
    submitUrl = parent.pageOperation === 1?"operateInformation/add":"operateInformation/update";
    submitType = parent.pageOperation === 1?"POST":"POST";
    deptParentId = "";

    //日期初始化
    laydate.render({
        elem: '#dataTime',
        range: true
    });
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
            url: "operateInformation/select/"+parent.dataId,
            contentType: 'application/json',
            success: function(data){
                if(data.code==200){
                    form.render();
                    var rest = data.data;
                    var time = rest.dataTime.substring(0,10);
                    $(".modelTime").val(time);
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
                        }else{
                        }
                    }
                    form.render('select');
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
            var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            var dataTime = $(".modelTime").val();
            var borrowingAmount = $(".borrowingAmount").val();
            var borrowingNum = $(".borrowingNum").val();
            var borrowSurplus = $(".borrowSurplus").val();
            var toBePaidNum = $(".toBePaidNum").val();
            var lenderTotal = $(".lenderTotal").val();
            var borrowerTotal = $(".borrowerTotal").val();
            var monthLenderTotal = $(".monthLenderTotal").val();
            var monthBorrowingNum = $(".monthBorrowingNum").val();
            var topTenBorrowed = $(".topTenBorrowed").val();
            var topOneBorrowed = $(".topOneBorrowed").val();
            var relationSurplus = $(".relationSurplus").val();
            var relationNum = $(".relationNum").val();
            var overdueAmount = $(".overdueAmount").val();
            var overdueNum = $(".overdueNum").val();
            var overdueCapital = $(".overdueCapital").val();
            var overdueCapitalNum = $(".overdueCapitalNum").val();
            var replaceRepayAmount = $(".replaceRepayAmount").val();
            var replaceRepayNum = $(".replaceRepayNum").val();
            var compensationAmount = $(".compensationAmount").val();
            var compensationNum = $(".compensationNum").val();
            var interestBalance = $(".interestBalance").val();
            if(parent.pageOperation===2){
                var jsonStr={id:parent.dataId,dataTime:dataTime,borrowingAmount:borrowingAmount,borrowingNum:borrowingNum,borrowSurplus:borrowSurplus,
                    toBePaidNum:toBePaidNum,lenderTotal:lenderTotal,borrowerTotal:borrowerTotal,monthLenderTotal:monthLenderTotal,
                    monthBorrowingNum:monthBorrowingNum,topTenBorrowed:topTenBorrowed,topOneBorrowed:topOneBorrowed,relationSurplus:relationSurplus,
                    overdueAmount:overdueAmount,overdueNum:overdueNum,overdueCapital:overdueCapital,overdueCapitalNum:overdueCapitalNum,
                    replaceRepayAmount:replaceRepayAmount,replaceRepayNum:replaceRepayNum,relationNum:relationNum,compensationAmount:compensationAmount,
                    compensationNum:compensationNum,interestBalance:interestBalance}
            }else{
                var jsonStr={dataTime:dataTime,borrowingAmount:borrowingAmount,borrowingNum:borrowingNum,borrowSurplus:borrowSurplus,
                    toBePaidNum:toBePaidNum,lenderTotal:lenderTotal,borrowerTotal:borrowerTotal,monthLenderTotal:monthLenderTotal,
                    monthBorrowingNum:monthBorrowingNum,topTenBorrowed:topTenBorrowed,topOneBorrowed:topOneBorrowed,relationSurplus:relationSurplus,
                    overdueAmount:overdueAmount,overdueNum:overdueNum,overdueCapital:overdueCapital,overdueCapitalNum:overdueCapitalNum,
                    replaceRepayAmount:replaceRepayAmount,replaceRepayNum:replaceRepayNum,relationNum:relationNum,compensationAmount:compensationAmount,
                    compensationNum:compensationNum,interestBalance:interestBalance}
            }
            $.ajax({
                type: submitType,
                url: submitUrl,
                data: JSON.stringify(jsonStr),
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

})
