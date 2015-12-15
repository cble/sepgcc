(function () {
    var fileComponents = [];
    $('.file_upload_container').each(function (container) {

        fileComponents.push(new FileComponent(container));
    });


    //提交
    $("#submit").on("click", function () {
        if (fileComponents.some(function (f) {
            return !f.isFinish();
        })) {
            //图片没传完
            return alert("请等待图片上传完");
        }

        $.ajax({
            url: "",
            dataType: "json",
            success: function () {

            },
            error: function () {

            }
        });
    });

})();