<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation"><a href="/admin">项目管理</a></li>
        <li role="presentation" class="active"><a href="/admin/userlist">用户管理</a></li>
    </ul>
    <h2>用户列表</h2>
    <div>
        <a class="btn btn-primary" href="/admin/importuser">导入新用户</a>
    </div>
    <div class="J_pagelist">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <td>用户名</td>
                <td>单位名</td>
                <td>状态</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script src="/js/component/pagelist.js"></script>
<script src="/js/page/user_manager.js"></script>
</@pageFrame>