(function () {
    var fileComponents = [];
    $('.file_upload_container').each(function (i, container) {
        container = $(container);
        var itemId = container.data("id");
        fileComponents.push({
            id: itemId,
            fc: new FileComponent(container),
            required: container.attr("required") === "required"
        });
    });


    //提交
    $("#submit").on("click", function () {
        if (fileComponents.some(function (f) {
            return (f.required && !f.fc.files.length )|| !f.fc.isFinish();
        })) {
            //图片没传完
            return alert("请等待图片上传完");
        }
        var data = {items: {}, contacts: {}};
        $(".J_contact-input").each(function (i, input) {
            var id = $(input).data("id");
            data.contacts[id] = input.value;
        });
        fileComponents.forEach(function (f) {
            data.items[f.id] = f.fc.getFiles();
        });

        $.ajax({
            contentType:"application/json;charset=UTF-8",
            url: "/ajax/submitupload",
            dataType: "json",
            data: JSON.stringify(data),
            type: "post",
            success: function (res) {
                alert("提交成功")
            },
            error: function () {

            }
        });
    });

})();