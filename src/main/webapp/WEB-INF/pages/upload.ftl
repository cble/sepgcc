<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>
    <form class="form" method="post" action="">
        <div>
            <hr />
            <h2>联系人信息</h2>
            <#list project.projectContactList as projectContact>
                <label for="contact_${projectContact.id}">${projectContact.name}<#if projectContact.required>（*）</#if></label>
                <input type="text" name="username"
                       class="form-control"
                       id="contact_${projectContact.id}"
                       <#if projectContact.required>required=""</#if> />
            </#list>
        </div>
        <div>
            <hr />
            <h2>项目资料</h2>
            <#list project.projectItemList as projectItem>
                <label for="item_${projectItem.id}">${projectItem.name}<#if projectItem.required>（*）</#if></label>
                <p>${projectItem.description}</p>
                <#if projectItem.descriptionImage??>
                <p><img src="${projectItem.descriptionImage}" alt="示例图片" /></p>
                </#if>
                <input type="text" name="username"
                       class="form-control"
                       id="item_${projectItem.id}"
                       <#if projectItem.required>required=""</#if> />
            </#list>
        </div>
    </form>
    <hr />
    <div>
        ${project.owner}
    </div>
</div>

</@pageFrame>