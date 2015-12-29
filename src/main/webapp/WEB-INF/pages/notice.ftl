<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>
    <hr />
    <div>
        ${project.description}
    </div>
    <div>
        <#if project.projectAttachment?? && project.projectAttachment?size gt 0>
            <span>下载附件：</span>
            <#list project.projectAttachment as attachment>
                <a href="/download?fileId=${attachment.fileId}">${attachment.name}</a>
            </#list>
        </#if>
    </div>
    <div style="text-align: center;">
        <a class="btn btn-primary" href="/upload?projectId=${project.id}">开始上传</a>
    </div>
    <hr />
    <div>
        ${project.owner}
    </div>
</div>

</@pageFrame>