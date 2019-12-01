// stringBuffer对象
function StringBuffer() {
    this.__strings__ = [];
};
StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this;
};
StringBuffer.prototype.toString = function () {
    return this.__strings__.join('');
};
StringBuffer.prototype.clear = function () {
    this.__strings__ = [];
}
StringBuffer.prototype.size = function () {
    return this.__strings__.length;
};

// map对象
function HashMap() {
    this.mapArr = new Array();

    var struct = function (key, value) {
        this.key = key;
        this.value = value;
    };

    this.put = function (key, value) {
        for (var i = 0, len = this.mapArr.length; i < len; i++) {
            var item = this.mapArr[i];
            if (item.key === key) {
                item.value = value;
                return;
            }
        }
        this.mapArr[this.mapArr.length] = new struct(key, value);
    };

    this.get = function (key) {
        for (var i = 0, len = this.mapArr.length; i < len; i++) {
            var item = this.mapArr[i];
            if (item.key === key) {
                return item.value;
            }
        }
        return null;
    };

    this.remove = function (key) {
        for (var i = 0, len = this.mapArr.length; i < len; i++) {
            // 删除并返回数组的最后一个元素
            var item = this.mapArr.pop();
            if (item.key === key) {
                break;
            }
            // 向数组的开头添加一个或更多元素，并返回新的长度。
            this.mapArr.unshift(item);
        }
    };
    this.size = function () {
        return this.mapArr.length;
    };

    this.isEmpty = function () {
        return this.mapArr.length <= 0;
    };

    this.clear = function () {
        return this.mapArr = new Array();
    };

}


function removeItemForArr(arr, item) {
    var newArr = new Array();
    for (var i = 0, len = arr.length; i < len; i++) {
        if (arr[i] == item) {
            continue;
        }
        newArr.push(arr[i]);
    }
    return newArr;
}

function strToCharArr(str) {
    var arr = new Array();
    for(var i = 0, len = str.length; i < len; i++){
        arr.push(str.charAt(i));
    }
    return arr;
}

function conditionGroupPosition(ruleExpression){
    var positionArr = new Array();
    var expressionArr = strToCharArr(ruleExpression);
    var tmpPosition = 0;
    for (var i = 0, len = expressionArr.length; i < len; i++) {
        if ('(' == expressionArr[i]) {
            tmpPosition = i;
            while (true){
                tmpPosition++;
                if (tmpPosition >= len) {
                    break;
                }
                if (')' != expressionArr[tmpPosition]) {
                    continue;
                }
                positionArr.push(i + ':' + tmpPosition);
                break;
            }
        }
    }
    return positionArr;
}

function positionIsInGroupArr(position, positionGroupArr) {
    var item = null, tempArr = null;
    for (var i = 0, len = positionGroupArr.length; i < len; i++) {
        item = positionGroupArr[i];
        tempArr = item.split(':');
        if (parseInt(tempArr[0]) < parseInt(position) && parseInt(tempArr[1]) > parseInt(position)) {
            return true;
        }
    }
    return false;
}

function getPositionArrInGroupArr(position, positionGroupArr) {
    var item = null, tempArr = null;
    for (var i = 0, len = positionGroupArr.length; i < len; i++) {
        item = positionGroupArr[i];
        tempArr = item.split(':');
        if (parseInt(tempArr[0]) < parseInt(position) && parseInt(tempArr[1]) > parseInt(position)) {
            return item;
        }
    }
    return null;
}
