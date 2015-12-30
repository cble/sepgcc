function publishproject(id) {
    if (confirm("发布后不能修改，确定要发布？")) {
        $.ajax({
            url: "/ajax/admin/publishproject",
            data: {
                projectId: id
            },
            type: "get",
            success: function (res) {
                if (res && res.code == 200 && res.data) {
                    alert("操作成功");
                } else {
                    alert("服务出错,请稍后再试");
                }
                window.location.reload();
            },
            error: function () {
                alert("服务出错,请稍后再试");
            }
        });
    }
}

function unpublishproject(id) {
    if (confirm("停用后用户将无法上传和修改资料，确定要停用？")) {
        $.ajax({
            url: "/ajax/admin/unpublishproject",
            data: {
                projectId: id
            },
            type: "get",
            success: function (res) {
                if (res && res.code == 200 && res.data) {
                    alert("操作成功");
                } else {
                    alert("服务出错,请稍后再试");
                }
                window.location.reload();
            },
            error: function () {
                alert("服务出错,请稍后再试");
            }
        });
    }
}

function deleteproject(id) {
    if (confirm("确定要删除？")) {
        $.ajax({
            url: "/ajax/admin/deleteproject",
            data: {
                projectId: id
            },
            type: "get",
            success: function (res) {
                if (res && res.code == 200 && res.data) {
                    alert("操作成功");
                } else {
                    alert("服务出错,请稍后再试");
                }
                window.location.reload();
            },
            error: function () {
                alert("服务出错,请稍后再试");
            }
        });
    }
}

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
                        operation = '<a href="/admin/projectstatistics?projectId={id}">查看上传情况</a> <a href="javascript:unpublishproject({id})">停用</a>';
                    } else if (item.status == 0) {
                        operation = '<a href="/admin/modifyproject?projectId={id}">修改</a> <a href="javascript:publishproject({id})">发布</a> <a href="javascript:deleteproject({id})">删除</a>';
                    } else if (item.status == 2) {
                        operation = '<a href="/admin/projectstatistics?projectId={id}">查看上传情况</a>';
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