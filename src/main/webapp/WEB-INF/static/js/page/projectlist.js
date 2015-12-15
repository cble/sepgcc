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
        lastColumn: '<a href="notice?projectId={id}">上传资料</a>'
    })
})();