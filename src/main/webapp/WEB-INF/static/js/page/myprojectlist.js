(function () {

    new PageList(".J_pagelist", {
        url: "/ajax/projectlist",
        parseData: function (res) {
            var data = {};
            data.total = res.total;
            data.list = res.list.map(function (item) {
                return [item.name, item.endTime, item];
            });
            return res;
        },
        lastColumn: '<a href="/upload?projectId={id}">修改</a>'
    });
})();