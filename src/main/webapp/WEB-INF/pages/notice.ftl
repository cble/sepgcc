<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>
    <hr />
    <div>
        ${project.description}
    </div>
    <div>
        <a href="/upload?projectId=${project.id}">开始上传</a>
    </div>
    <hr />
    <div>
        ${project.owner}
    </div>
</div>

</@pageFrame>