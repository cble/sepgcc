<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">
    <link href="/css/themes/default/css/umeditor.css" rel="stylesheet"/>


    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation" class="active"><a href="/admin">项目管理</a></li>
        <#--<li role="presentation"><a href="/admin/userlist">用户管理</a></li>-->
    </ul>
    <form class="form form-horizontal">
        <h2>填写通知</h2>
        <script id="container" name="content" type="text/plain" style="width:1000px;height:440px;"></script>

        <h2>添加附件</h2>
        <div>
            <div class="J_attachment">
                <div class="file_upload_container">
                </div>
            </div>
        </div>
        <hr />
        <h2>联系人资料</h2>
        <div>
            <div class="J_contact">
                <#list project.projectContactList as projectContact>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">字段名</label>
                    <div class="col-sm-10">
                        <input class="form-control " type="text" name="name" value="${projectContact.name}" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">是否必填</label>
                    <div class="col-sm-10">
                        <label class="radio-inline">
                            <input type="radio" name="required1" value="1" <#if projectContact.required>checked</#if>> 是
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="required1" value="0" <#if !projectContact.required>checked</#if>> 否
                        </label>
                    </div>
                </div>
                </#list>
            </div>
        </div>
        <a class="btn btn-success btn-sm J_add" data-for=".J_contact" href="javascript:void(0);">添加一个字段</a>
        <hr />
        <h2>项目资料</h2>
        <div>
            <div class="J_meta">
                <#list project.projectItemList as projectItem>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">字段名</label>
                    <div class="col-sm-10">
                        <input class="form-control " type="text" name="name" value="${projectItem.name}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">字段描述</label>
                    <div class="col-sm-10">
                        <input class="form-control " type="text" name="description" value="${projectItem.description}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">是否必填</label>
                    <div class="col-sm-10">
                        <label class="radio-inline">
                            <input type="radio" name="required2" value="1" <#if projectItem.required>checked</#if>> 是
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="required2" value="0" <#if !projectItem.required>checked</#if>> 否
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">样例图片</label>
                    <div class="file_upload_container" data-id="${projectItem.id}">
                    </div>
                </div>
                </#list>

            </div>
        </div>
        <a class="btn btn-success btn-sm J_add" data-for=".J_meta" href="javascript:void(0);">添加一个字段</a>
        <hr />
        <h2>项目信息</h2>
        <div>
            <div class="J_info">
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">项目名</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="name" value="${project.name}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">主办方名称</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="owner" value="${project.owner}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">有效期</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" placeholder="例2015-01-01 00:00:00"
                               style="width:200px;display: inline-block" name="beginTime" value="${project.beginTimeStr}"/>
                        ~
                        <input class="form-control" type="text" style="width:200px;display: inline-block"
                               name="endTime" value="${project.endTimeStr}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">用户组</label>
                    <div class="col-sm-10">
                        <label class="radio-inline" for="usergroup-1">
                            <input id="usergroup-1" type="radio" name="userGroup" value="1" <#if project.userGroup == 1>checked</#if>> 学校用户
                        </label>
                        <label class="radio-inline" for="usergroup-2">
                            <input id="usergroup-2" type="radio" name="userGroup" value="2" <#if project.userGroup == 2>checked</#if>> 剧院用户
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <hr />
    <div style="text-align: center">
        <a class="btn btn-primary btn-lg" id="submit">提交</a>
    </div>
</div>

<script>
    window.PAGE_DATA = {
        <#if project.projectAttachmentList?? && project.projectAttachmentList?size gt 0>
        attachments: [
            <#list project.projectAttachmentList as attachment>
            {
                fileId: "${attachment.fileId}",
                fileName: "${attachment.name}"
            },
            </#list>
        ],
        </#if>
        itemImages: {
            <#list project.projectItemList as projectItem>
            <#if projectItem.fileId??>
            "${projectItem.id}": [{
                fileId: "${projectItem.fileId}",
                fileName: "${projectItem.fileName}"
            }],
            </#if>
            </#list>
        },
        projectId: ${project.id},
        projectDescription: '${project.description}'
    }
</script>
<script type="text/javascript" src="/js/component/umeditor.config.js"></script>
<script type="text/javascript" src="/js/component/umeditor.min.js"></script>
<script src="/js/component/jquery.ui.widget.js"></script>
<script src="/js/component/jquery.fileupload.js"></script>
<script src="/js/component/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="/js/component/fileupload.js"></script>
<script type="text/javascript" src="/js/page/newproject.js"></script>

</@pageFrame>