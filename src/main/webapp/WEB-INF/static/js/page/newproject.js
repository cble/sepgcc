(function () {
    var ue = UM.getEditor('container', {
        autoHeight: false
    });

    //添加
    $('.J_add').each(function (i, btn) {
        btn = $(btn);
        var tpl = $(btn.data("for"));
        btn.on('click', function () {
            var radioName = Math.random();
            var row = tpl.clone();
            row.find("input[type=radio]").each(function () {
                $(this).attr("name", radioName);
            });
            tpl.parent().append(row);
        });
    });

    ue.ready(function () {

        $('#submit').on("click", function (e) {
            e.preventDefault();

            var data = {projectItemList: [], projectContactList: []};

            //项目信息
            $(".J_info").find("input").each(function (i, item) {
                data[$(item).attr("name")] = item.value;
            });

            data.description = ue.getContent();

            $(".J_meta").each(function (i, meta) {
                var item = {};
                $(meta).find("input[type=text]").each(function (j, input) {
                    item[$(input).attr("name")] = input.value;
                });
                item.required = $(meta).find('input:checked').val() == '1';
                data.projectItemList.push(item);
            });

            $(".J_contact").each(function (i, meta) {
                var item = {};
                $(meta).find("input[type=text]").each(function (j, input) {
                    item[$(input).attr("name")] = input.value;
                });
                item.required = $(meta).find('input:checked').val() == '1';
                data.projectContactList.push(item);
            });

            $.ajax({
                url: "/ajax/admin/createnewproject",
                data: data,
                dataType: "json",
                type: "post",
                success: function (res) {
                    if (res && res.code == 200) {
                        alert("提交成功");
                        location.href = res.data;
                    } else {
                        alert("服务出错,请稍后再试");
                    }
                },
                error: function () {
                    alert("服务出错,请稍后再试");
                }
            })

        });

    });


})();
