(function () {

    new PageList(".J_pagelist", {
        url: "/ajax/admin/projectmanagerlist",
        parseData: function (res) {
            if (res.code == 200 && res.data) {
                var data = {};
                data.total = res.data.pageCount;
                data.list = res.data.list.map(function (item) {
                    var operation;
                    if (item.status == 1) {
                        operation = '<a href="/admin/projectstatistics?projectId={id}">查看上传情况</a> <a href="/admin/unpublishproject?projectId={id}">停用</a>';
                    } else {
                        operation = '<a href="/admin/modifyproject?projectId={id}">修改</a> <a href="/admin/publishproject?projectId={id}">发布</a> <a href="/admin/deleteproject?projectId={id}">删除</a>';
                    }
                    return [item.name, item.endTimeStr, item.successNumber, operation.replace(/\{(\w+)\}/g, function (m, $1) {
                        return item[$1];
                    })];
                });
                return data;
            }
            alert("接口挂了")
        }
    });
})();