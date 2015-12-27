<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation" class="active"><a href="/admin">项目管理</a></li>
        <li role="presentation"><a href="/admin/userlist">用户管理</a></li>
    </ul>
    <h2>${project.name}</h2>
    <div>
        <a class="btn btn-primary" href="/admin/downloadstatistics?projectId=${project.id}">下载报表</a>
        <a class="btn btn-primary" href="/admin/downloadproject?projectId=${project.id}">打包下载文件</a>
    </div>
    <div class="J_pagelist">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <td>单位名</td>
                <#list project.projectContactList as projectContact>
                    <td>${projectContact.name}</td>
                </#list>
                <#list project.projectItemList as projectItem>
                    <td>${projectItem.name}</td>
                </#list>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script>
    var pageData = {
        projectId: ${project.id}
    };
</script>
<script src="/js/component/pagelist.js"></script>
<script src="/js/page/project_statistics.js"></script>
</@pageFrame>