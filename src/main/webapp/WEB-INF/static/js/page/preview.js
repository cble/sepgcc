(function () {

    var userGroup = PAGE_DATA.userGroup;

    new PageList(".J_pagelist", {
        url: "/ajax/admin/projectlist/"+ userGroup,
        parseData: function (res) {
            if (res.code == 200 && res.data) {
                var data = {};
                data.total = res.data.pageCount;
                data.list = res.data.list.map(function (item) {
                    var operation = "";
                    if (item.available) {
                        if (item.uploadId > 0) {
                            operation = '<a href="/modify?uploadId={uploadId}">修改上传</a>';
                        } else {
                            operation = '<a href="/notice?projectId={id}">上传资料</a>';
                        }
                    }
                    return [item.name, item.endTimeStr, operation.replace(/\{(\w+)\}/g, function (m, $1) {
                        return item[$1];
                    })];
                });
                return data;
            }
            alert("接口挂了")

        }
    });

})();