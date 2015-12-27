(function () {

    new PageList(".J_pagelist", {
        url: "/ajax/admin/usermanagerlist",
        parseData: function (res) {
            if (res.code == 200 && res.data) {
                var data = {};
                data.total = res.data.pageCount;
                data.list = res.data.list.map(function (item) {
                    return [item.username, item.nickname, item.groupStr, item];
                });
                return data;
            }
            alert("接口挂了")
        },
        lastColumn: '<a href="notice?projectId={id}">修改</a> <a href="notice?projectId={id}">发布</a> <a href="notice?projectId={id}">删除</a>'
    });
})();