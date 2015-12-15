(function () {

    new PageList(".J_pagelist", {
        url: "",
        parseData: function (res) {
            var data = {};
            data.total = res.total;
            data.list = res.list.map(function (item) {
                return [item.name, item.endTime, item];
            });
            return res;
        },
        lastColumn: '<a href="notice?projectId={id}">修改</a><a href="notice?projectId={id}">停用</a><a href="notice?projectId={id}">重置密码</a>'
    })
})();