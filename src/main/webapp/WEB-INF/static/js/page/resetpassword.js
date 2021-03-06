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

        var password = $(".J_password").val();

        if (!password) {
            showErr("输入错误");
            return;
        }
        var reg = /^[A-Za-z0-9]{8,20}$/;
        if (!reg.test(password)) {
            showErr("密码不符合规则");
            return;
        }
        $.ajax({
            url: "/ajax/admin/resetpassword",
            data: {
                userId: userId,
                newPwd: password
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res && res.code == 200 && res.data) {
                    alert("操作成功");
                    location.href = "/admin/userlist";
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
