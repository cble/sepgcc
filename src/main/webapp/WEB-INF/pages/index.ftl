<#include "common/header.ftl" />

<div class="container">

    <form class="form-signin">
        <h2 class="form-signin-heading">请先登录</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="用户名" required="" autofocus="">
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="密码" required="">
    <#--<div class="checkbox">-->
    <#--<label>-->
    <#--<input type="checkbox" value="remember-me"> Remember me-->
    <#--</label>-->
    <#--</div>-->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</div>

<#include "common/footer.ftl" />