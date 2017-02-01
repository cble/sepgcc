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

        var userName = $(".J_username").val();
        var nickName = $(".J_nickname").val();
        var password = $(".J_password").val();
        var userGroup = $(".J_usergroup:checked").val();

        if (!userName || !nickName || !password || !userGroup) {
            showErr("输入错误");
            return;
        }
        var reg = /^[A-Za-z0-9]{8,20}$/;
        if (!reg.test(password)) {
            showErr("密码不符合规则");
            return;
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            url: "/ajax/admin/newuser",
            data: JSON.stringify({
                username: userName,
                nickname: nickName,
                userGroup: userGroup,
                password: password
            }),
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
