<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation" class="active"><a href="/admin">项目管理</a></li>
        <li role="presentation"><a href="/admin/userlist">用户管理</a></li>
    </ul>
    <h2>项目列表</h2>
    <div style="text-align: right;">
        <a class="btn btn-primary" href="/admin/newproject">创建新项目</a>
    </div>
    <div class="J_pagelist">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <td>项目名</td>
                <td>截止日期</td>
                <td>用户组</td>
                <td>上传数</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script src="/js/component/pagelist.js"></script>
<script src="/js/page/project_manager.js"></script>
</@pageFrame>