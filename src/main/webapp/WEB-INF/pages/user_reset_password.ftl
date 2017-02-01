<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation"><a href="/admin">项目管理</a></li>
        <li role="presentation" class="active"><a href="/admin/userlist">用户管理</a></li>
    </ul>
    <h2>重置密码</h2>
    <div>
        <form class="form form-horizontal">
            <div class="alert alert-success J_process hide" role="alert">操作进行中...</div>
            <div class="alert alert-danger J_tip hide" role="alert"></div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">新密码</label>
                <div class="col-sm-3">
                    <input class="form-control J_password" type="password" name="password" required="" placeholder="请输入8位以上的字母和数字" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-5" style="text-align: center">
                    <a class="btn btn-primary" id="submit">提交</a>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    window.PAGE_DATA = {
        userId: '${userId}'
    }
</script>
<script type="text/javascript" src="/js/page/resetpassword.js"></script>

</@pageFrame>