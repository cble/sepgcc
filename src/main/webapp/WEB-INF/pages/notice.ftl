<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>
    <hr />
    <div>
        ${project.description}
    </div>
    <div>
        <#if project.projectAttachmentList?? && project.projectAttachmentList?size gt 0>
            <h3>下载附件：</h3>
            <ul>
                <#list project.projectAttachmentList as attachment>
                    <li>
                        <a href="/download?fileId=${attachment.fileId}">${attachment.name}</a>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>
    <hr />
    <div style="text-align: center;">
        <a class="btn btn-primary btn-lg" href="/upload?projectId=${project.id}">开始上传</a>
    </div>
    <hr />
    <h4 style="text-align: right;">
    ${project.owner}
    </h4>
</div>

</@pageFrame>