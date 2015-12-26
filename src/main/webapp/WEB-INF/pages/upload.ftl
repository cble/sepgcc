<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>

    <form class="form" method="post" action="">

        <h3>联系人信息</h3>
        <#list project.projectContactList as projectContact>
            <div class="form-group">
                <label for="contact_${projectContact.id}">${projectContact.name}<#if projectContact.required>
                    （<span style="color: red">*</span>）</#if></label>

                <input type="text"
                       class="form-control J_contact-input"
                       id="contact_${projectContact.id}"
                       data-id="${projectContact.id}"
                       <#if projectContact.required>required=""</#if> />
            </div>
        </#list>

        <h3>项目资料</h3>
        <#list project.projectItemList as projectItem>
            <div class="form-group">
                <label for="item_${projectItem.id}">${projectItem.name}<#if projectItem.required>（<span
                        style="color: red">*</span>）</#if></label>

                <p>${projectItem.description}</p>
                <#if projectItem.descriptionImage??>
                    <p><img src="${projectItem.descriptionImage}" alt="示例图片"/></p>
                </#if>
                <div class="file_upload_container" data-id="${projectItem.id}">
                </div>
            </div>
        </#list>

    </form>
    <hr/>
    <div>
    ${project.owner}
    </div>

    <div><a class="btn btn-primary btn-lg" style="float: right" id="submit" href="javascript:void(0);">提交</a></div>
</div>

<script src="/js/component/jquery.ui.widget.js"></script>
<script src="/js/component/jquery.fileupload.js"></script>
<script src="/js/component/jquery.iframe-transport.js"></script>
<script src="/js/component/fileupload.js"></script>
<script src="/js/page/upload.js"></script>

</@pageFrame>