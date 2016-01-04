<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <form class="form-signin" method="post" action="login">
        <h2 class="form-signin-heading">请先登录</h2>
        <div class="form-group">
            <input type="text" name="username" class="form-control" placeholder="用户名" required="" autofocus="" />
        </div>
        <div class="form-group">
            <input type="password" name="password" class="form-control" placeholder="密码" required="" />
        </div>
        <div class="form-group">
            <input type="text" name="captcha" class="form-control" placeholder="验证码" required="" style="width: 190px" />
            <img onclick="javascript:this.src='/captcha?tm='+Math.random()" src="/captcha" style="width: 100px; height: 44px;" />
        </div>
    <#--<div class="checkbox">-->
    <#--<label>-->
    <#--<input type="checkbox" value="remember-me"> Remember me-->
    <#--</label>-->
    <#--</div>-->
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div>

</@pageFrame>