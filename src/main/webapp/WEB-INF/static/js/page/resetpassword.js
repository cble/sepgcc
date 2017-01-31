(function () {

    var userId = PAGE_DATA.userId;

    var $tip = $(".J_tip");
    var $process = $(".J_process");

    function showErr(err) {
        if (err) {
            $tip.html(err);
            $process.addClass("hide");
            $tip.removeClass("hide");
        }
    }

    $("#submit").on("click", function (e) {
        e.preventDefault();

        $tip.addClass("hide");
        $process.removeClass("hide");

        var newPassword1 = $(".J_newpassword1").val();
        var newPassword2 = $(".J_newpassword2").val();
        
        if (!newPassword1 || !newPassword2) {
            showErr($tip, "输入错误");
            return;
        }
        if (newPassword1 != newPassword2) {
            showErr($tip, "两遍输入的密码不一致");
            return;
        }
        $.ajax({
            url: "/ajax/admin/resetpassword",
            data: {
                userId: userId,
                newPwd: newPassword1
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res && res.code == 200 && res.data) {
                    alert("操作成功");
                    location.href = "/";
                } else {
                    showErr(res.message)
                }
            },
            error: function () {
                alert("服务出错,请稍后再试");
            }
        })
    });

})();