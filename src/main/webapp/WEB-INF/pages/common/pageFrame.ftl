<#macro pageFrame loginPage=false>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>上海教育报刊总社资料收集平台</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/base.css" />
    <script src="/js/jquery.1.9.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/index">上海教育报刊总社资料收集平台</a>
        </div>

        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
            <#if user?? && user.id == 1> <!-- TODO usertype -->
            <ul class="nav navbar-nav">
                <li><a href="/index">普通用户入口</a></li>
                <li><a href="/admin">管理员入口</a></li>
            </ul>
            </#if>

            <#if !loginPage && user??>
            <ul class="nav navbar-nav navbar-right">
                <li><a style="color: white;">您好，${user.nickname}</a></li>
                <li><a href="/logout">退出登录</a></li>
            </ul>
            </#if>
        </div>
    </div>
</nav>

<#nested>

</body>
</html>

</#macro>