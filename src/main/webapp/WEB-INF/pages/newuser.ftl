<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation"><a href="/admin">项目管理</a></li>
        <li role="presentation" class="active"><a href="/admin/userlist">用户管理</a></li>
    </ul>
    <h2>创建新用户</h2>
    <div>
        <form class="form form-horizontal">
            <div class="alert alert-success J_process hide" role="alert">操作进行中...</div>
            <div class="alert alert-danger J_tip hide" role="alert"></div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-3">
                    <input class="form-control J_username" type="text" name="username" required="" placeholder="如：FDDX" />
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">单位名</label>
                <div class="col-sm-3">
                    <input class="form-control J_nickname" type="text" name="nickname" required="" placeholder="如：复旦大学" />
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">用户组</label>
                <div class="col-sm-10">
                    <label class="radio-inline" for="usergroup-1">
                        <input class="J_usergroup" id="usergroup-1" type="radio" name="usergroup" value="1"> 学校用户
                    </label>
                    <label class="radio-inline" for="usergroup-2">
                        <input class="J_usergroup" id="usergroup-2" type="radio" name="usergroup" value="2"> 剧院用户
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">密码</label>
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

<script type="text/javascript" src="/js/page/newuser.js"></script>

</@pageFrame>