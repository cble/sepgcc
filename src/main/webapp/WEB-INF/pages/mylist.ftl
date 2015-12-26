<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <ul class="nav nav-pills" style="padding: 15px 0;">
        <li role="presentation"><a href="index">项目列表</a></li>
        <li role="presentation" class="active"><a href="mylist">我的上传</a></li>
    </ul>
    <div class="J_pagelist">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <td>项目名</td>
                <td>截止日期</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script src="/js/component/pagelist.js"></script>
<script src="/js/page/myprojectlist.js"></script>
</@pageFrame>