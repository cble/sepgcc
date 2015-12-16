(function () {

    new PageList(".J_pagelist", {
        url: "/ajax/projectlist",
        parseData: function (res) {
            if (res.code == 200 && res.data) {
                var data = {};
                data.total = res.data.pageCount;
                data.list = res.data.list.map(function (item) {
                    return [item.name, item.endTimeStr, item];
                });
                return data;
            }
            alert("接口挂了")

        },
        lastColumn: '<a href="/upload?projectId={id}">修改</a>'
    });
})();