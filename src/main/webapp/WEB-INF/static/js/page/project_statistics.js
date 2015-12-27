(function () {

    new PageList(".J_pagelist", {
        url: "/ajax/admin/projectstatistics",
        parseData: function (res) {
            if (res.code == 200 && res.data) {
                var data = {};
                data.total = res.data.pageCount;
                data.list = res.data.list.map(function (item) {
                    return res.data.columns.map(function (key) {
                        return item[key];
                    });
//                    var columns = [];
//                    res.data.columns.forEach(function(key) {
//                        columns.push(item[key]);
//                    });
//                    return columns;
                });
                return data;
            }
            alert("接口挂了")
        },
        queryData: {
            projectId: pageData.projectId
        }
    });
})();