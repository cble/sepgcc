<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation" class="active"><a href="#">项目列表</a></li>
        <li role="presentation"><a href="#">我的上传</a></li>
    </ul>

    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <td>项目名</td>
            <td>截止日期</td>
            <td>操作</td>
        </tr>
        </thead>
        <#if projectList?exists && projectList?size gt 0>
        <tbody>
        <#list projectList as project>
        <tr>
            <td>${project.name}</td>
            <td>${project.endTime?datetime}</td>
            <td><a href="notice?projectId=${project.id}">上传</a></td>
        </tr>
        </#list>
        </tbody>
        </#if>
    </table>

</div>

</@pageFrame>