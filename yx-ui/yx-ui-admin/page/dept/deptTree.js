layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'tree'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    // var deptId = window.parent.document.getElementById("deptId").value;
    var treeUrl = parent.deptParentId===undefined||parent.deptParentId==='' ? "dept/queryTree" : "dept/queryTree/" + parent.deptParentId;
    // 新增、编辑跳转则加载部门树
    $.ajax({
        type: "GET",
        url: treeUrl,
        success: function (data) {
            if (data.code === 200) {
                layui.tree({
                    elem: '#demo', //传入元素选择器
                    nodes: data.data,
                    click: function (node) {
                        /*var ifrc = window.parent.frames[0];
                         var winc = ifrc.window || ifrc.contentWindow;
                         winc.document.getElementById("parentId").value = node.id;
                         winc.document.getElementById("parentName").value = node.name;*/
                        parent.deptTreeCallBack(node.id, node.name);
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                });
            } else {
                layer.msg("部门加载失败！", {icon: 2});
            }
        }
    });
})
