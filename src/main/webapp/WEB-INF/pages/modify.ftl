<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <h1>${project.name}</h1>

    <form class="form" method="post" action="">

        <h3>联系人信息</h3>
        <#list upload.contactValueList as uploadContact>
            <div class="form-group">
                <label for="contact_${uploadContact.id}">${uploadContact.name}<#if uploadContact.required>
                    （<span style="color: red">*</span>）</#if></label>

                <input type="text"
                       class="form-control J_contact-input"
                       id="contact_${uploadContact.id}"
                       <#if uploadContact.contactValue??>value="${uploadContact.contactValue}"</#if>
                       data-id="${uploadContact.id}"
                       <#if uploadContact.required>required=""</#if> />
            </div>
        </#list>

        <h3>项目资料</h3>
        <#list upload.itemValueList as uploadItem>
            <div class="form-group">
                <label for="item_${uploadItem.id}">${uploadItem.name}<#if uploadItem.required>（<span
                        style="color: red">*</span>）</#if></label>

                <p>${uploadItem.description}</p>
                <#if uploadItem.descriptionImage??>
                    <p><img src="${uploadItem.descriptionImage}" alt="示例图片"/></p>
                </#if>
                <div class="file_upload_container" data-id="${uploadItem.id}">
                </div>
                <#list uploadItem.fileMetaList as fileMeta>
                    <div data-fileId="${fileMeta.fileId}">${fileMeta.fileName} ${fileMeta.fileType}</div>
                </#list>
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