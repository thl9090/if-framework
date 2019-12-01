layui.define(["layer", "jquery"], function (exports) {
    var $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        TreeCheck = function (options) {
            this.options = options;
        };

    // 参数设置
    TreeCheck.prototype.set = function (options) {
        var _this = this;
        _this._container = document.getElementById(options.elem); //容器

        _this.xloading = document.createElement("span"); //创建加载对象
        _this.xloading.setAttribute('class', 'layui-icon layui-anim layui-anim-rotate layui-anim-loop');
        _this.xloading.innerHTML = '&#xe63e;';
        _this.xloading.style.fontSize = "50px";
        _this.xloading.style.color = "#009688";
        _this.xloading.style.fontWeight = "bold";
        _this.xloading.style.marginLeft = _this._container.offsetWidth / 2 - 25 + 'px';
        _this.xloading.style.marginTop = _this._container.offsetHeight / 2 - 50 + 'px';

        _this._container.appendChild(_this.xloading); // 加载显示

        $.ajax({
            type: 'POST',
            url: options.url,
            data: options.data,
            success: function (data) {
                if (data.code == 200) {
                    // _this._dataJson = eval('(' + data.data + ')'); //将返回的数据转为json
                    _this._dataJson = data.data;

                    _this._form = options.form; //layui from对象
                    _this._domStr = "";  //结构字符串
                    _this._isopen = options.isopen != null ? options.isopen : true;
                    _this._color = options.color != null ? options.color : "#2F4056"; //图标颜色
                    if (options.icon == null) options.icon = {};
                    _this._iconOpen = options.icon.open != null ? options.icon.open : "&#xe625;"; //打开图标
                    _this._iconClose = options.icon.close != null ? options.icon.close : "&#xe623;"; //关闭图标
                    _this._iconEnd = options.icon.end != null ? options.icon.end : "&#xe65f;"; //末级图标
                    _this._allDisable = options.allDisable ? options.allDisable : false;

                    _this.dataBind(_this._dataJson);
                    _this.Rendering();
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    };

    // 生产结构
    TreeCheck.prototype.dataBind = function (d) {
        var _this = this;

        if (d.length > 0) {
            for (i in d) {
                var xtree_isend = '';
                var xtree_ischecked = '';
                var xtree_isdisabled = d[i].disabled || _this._allDisable ? ' disabled="disabled" ' : '';
                _this._domStr += '<div class="layui-xtree-item">';
                if (d[i].children.length > 0)
                    _this._domStr += '<i class="layui-icon layui-xtree-icon" data-xtree="' + (_this._isopen ? '1' : '0') + '">' + (_this._isopen ? _this._iconOpen : _this._iconClose) + '</i>';
                else {
                    _this._domStr += '<i class="layui-icon layui-xtree-icon-null">' + _this._iconEnd + '</i>';
                    xtree_isend = 'data-xend="1"';
                    xtree_ischecked = d[i].checked ? ' checked ' : '';
                    xtree_isdisabled = d[i].disabled || _this._allDisable ? ' disabled="disabled" ' : '';
                }
                _this._domStr += '<input type="checkbox" class="layui-xtree-checkbox" ' + xtree_isend + xtree_ischecked + xtree_isdisabled + ' value="' + d[i].id + '" title="' + d[i].name + '" lay-skin="primary" lay-filter="xtreeck">';
                _this.dataBind(d[i].children);
                _this._domStr += '</div>';
            }
        }
    };

    // 渲染呈现
    TreeCheck.prototype.Rendering = function () {
        var _this = this;
        _this._container.innerHTML = _this._domStr;
        _this._domStr = "";

        //检查选中状态
        var xtree_ckitems = document.getElementsByClassName('layui-xtree-checkbox');

        for (var i = 0; i < xtree_ckitems.length; i++) {
            if (xtree_ckitems[i].getAttribute('data-xend') == '1' && xtree_ckitems[i].checked) {
                _this.ParentCheckboxChecked(xtree_ckitems[i]);
            }
        }

        _this._form.render('checkbox'); //layui渲染

        var xtree_items = document.getElementsByClassName('layui-xtree-item');
        var xtree_icons = document.getElementsByClassName('layui-xtree-icon');
        var xtree_nullicons = document.getElementsByClassName('layui-xtree-icon-null');

        for (var i = 0; i < xtree_items.length; i++) {
            if (xtree_items[i].parentNode == _this._container)
                xtree_items[i].style.margin = '5px 0 0 10px';
            else {
                xtree_items[i].style.margin = '5px 0 0 45px';
                if (!_this._isopen) xtree_items[i].style.display = 'none';
            }
        }
        for (var i = 0; i < xtree_icons.length; i++) {
            xtree_icons[i].style.position = "relative";
            xtree_icons[i].style.top = "3px";
            xtree_icons[i].style.margin = "0 5px 0 0";
            xtree_icons[i].style.fontSize = "18px";
            xtree_icons[i].style.color = _this._color;
            xtree_icons[i].style.cursor = "pointer";

            xtree_icons[i].onclick = function () {
                var xtree_chi = this.parentNode.childNodes;
                if (this.getAttribute('data-xtree') == 1) {
                    for (var j = 0; j < xtree_chi.length; j++) {
                        if (xtree_chi[j].getAttribute('class') == 'layui-xtree-item')
                            xtree_chi[j].style.display = 'none';
                    }
                    this.setAttribute('data-xtree', '0')
                    this.innerHTML = _this._iconClose;
                } else {
                    for (var j = 0; j < xtree_chi.length; j++) {
                        if (xtree_chi[j].getAttribute('class') == 'layui-xtree-item')
                            xtree_chi[j].style.display = 'block';
                    }
                    this.setAttribute('data-xtree', '1')
                    this.innerHTML = _this._iconOpen;
                }
            }
        }
        for (var i = 0; i < xtree_nullicons.length; i++) {
            xtree_nullicons[i].style.position = "relative";
            xtree_nullicons[i].style.top = "3px";
            xtree_nullicons[i].style.margin = "0 5px 0 0";
            xtree_nullicons[i].style.fontSize = "18px";
            xtree_nullicons[i].style.color = _this._color;
        }

        _this._form.on('checkbox(xtreeck)', function (da) {
            //获取当前点击复选框的容器下面的所有子级容器
            var xtree_chis = da.elem.parentNode.getElementsByClassName('layui-xtree-item');
            //遍历它们，选中状态与它们的父级一致（类似全选功能）
            for (var i = 0; i < xtree_chis.length; i++) {
                if (!xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].disabled) {
                    xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].checked = da.elem.checked;
                    if (da.elem.checked) xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.add('layui-form-checked');
                    else xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.remove('layui-form-checked');
                }
            }
            _this.ParendCheck(da.elem);
        });

        var _xtree_disableds = document.getElementsByClassName('layui-disabled');
        for (var i = 0; i < _xtree_disableds.length; i++) {
            _xtree_disableds[i].getElementsByTagName('span')[0].style.color = "#B5B5B5";
        }
    };

    // 子节点选中改变，父节点更改自身状态
    TreeCheck.prototype.ParendCheck = function (ckelem) {
        var xtree_p = ckelem.parentNode.parentNode;
        if (xtree_p.getAttribute('class') == 'layui-xtree-item') {
            var xtree_all = xtree_p.getElementsByClassName('layui-xtree-item');
            var xtree_count = 0;

            for (var i = 0; i < xtree_all.length; i++) {
                if (xtree_all[i].getElementsByClassName('layui-xtree-checkbox')[0].checked) {
                    xtree_count++;
                }
            }

            if (xtree_count <= 0) {
                xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].checked = false;
                xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.remove('layui-form-checked');
            } else {
                xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].checked = true;
                xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.add('layui-form-checked');
            }
            this.ParendCheck(xtree_p.getElementsByClassName('layui-xtree-checkbox')[0]);
        }
    };

    // 渲染之前按照选中的末级去改变父级选中状态
    TreeCheck.prototype.ParentCheckboxChecked = function (e) {
        if (e.parentNode.parentNode.getAttribute('class') == 'layui-xtree-item') {
            var _pe = e.parentNode.parentNode.getElementsByClassName('layui-xtree-checkbox')[0];
            _pe.checked = true;
            this.ParentCheckboxChecked(_pe);
        }
    };

    // 获取全部选中的末级checkbox对象
    TreeCheck.prototype.GetChecked = function () {
        var arr = new Array();
        var arrIndex = 0;
        var cks = document.getElementsByClassName('layui-xtree-checkbox');
        for (var i = 0; i < cks.length; i++) {
            if (cks[i].checked && cks[i].getAttribute('data-xend') == '1') {
                arr[arrIndex] = cks[i];
                arrIndex++;
            }
        }
        return arr;
    };

    // 获取全部选中的checkbox对象value
    TreeCheck.prototype.GetAllCheckedValue = function () {
        var arr = new Array();
        var arrIndex = 0;
        var cks = document.getElementsByClassName('layui-xtree-checkbox');
        for (var i = 0; i < cks.length; i++) {
            if (cks[i].checked) {
                arr[arrIndex] = cks[i].value;
                arrIndex++;
            }
        }
        return arr;
    };

    // 获取全部的原始checkbox对象
    TreeCheck.prototype.GetAllCheckBox = function () {
        var arr = new Array();
        var arrIndex = 0;
        var cks = document.getElementsByClassName('layui-xtree-checkbox');
        for (var i = 0; i < cks.length; i++) {
            arr[arrIndex] = cks[i];
            arrIndex++;
        }
        return arr;
    };

    // 根据 [值] 来获取其父级的checkbox原dom对象
    TreeCheck.prototype.GetParent = function (a) {
        var _this = this;
        var cks = document.getElementsByClassName('layui-xtree-checkbox');
        for (var i = 0; i < cks.length; i++) {
            if (cks[i].value == a) {
                if (cks[i].parentNode.parentNode.getAttribute('id') == _this._container.getAttribute('id')) return null;
                return cks[i].parentNode.parentNode.getElementsByClassName('layui-xtree-checkbox')[0];
            }
        }
        return null;
    };

    //暴露接口
    exports('treecheck', function (options) {
        var tree = new TreeCheck(options = options || {});
        tree.set(options);
        return tree;
    });
}); 