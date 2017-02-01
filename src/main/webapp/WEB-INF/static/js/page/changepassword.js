(function () {

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

        var oldPassword = $(".J_oldpassword").val();
        var newPassword1 = $(".J_newpassword1").val();
        var newPassword2 = $(".J_newpassword2").val();

        if (!oldPassword || !newPassword1 || !newPassword2) {
            showErr("输入错误");
            return;
        }
        if (newPassword1 != newPassword2) {
            showErr("两遍输入的密码不一致");
            return;
        }
        var reg = /^[A-Za-z0-9]{8,20}$/;
        if (!reg.test(newPassword1)) {
            showErr("密码不符合规则");
            return;
        }
        $.ajax({
            url: "/ajax/changepassword",
            data: {
                oldPwd: oldPassword,
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
