<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <form class="form-signin" method="post" action="login">
        <h2 class="form-signin-heading">请先登录</h2>
        <input type="text" name="username" class="form-control" placeholder="用户名" required="" autofocus="" />
        <input type="password" name="password" class="form-control" placeholder="密码" required="" />
    <#--<div class="checkbox">-->
    <#--<label>-->
    <#--<input type="checkbox" value="remember-me"> Remember me-->
    <#--</label>-->
    <#--</div>-->
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div>

</@pageFrame>