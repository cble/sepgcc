<#include "common/pageFrame.ftl" />
<@pageFrame>

<div class="container">

    <div>
        <form class="form form-horizontal">
            <div class="alert alert-success J_process hide" role="alert">操作进行中...</div>
            <div class="alert alert-danger J_tip hide" role="alert"></div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">原密码</label>
                <div class="col-sm-3">
                    <input class="form-control J_oldpassword" type="password" name="oldpassword" required="" />
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">新密码</label>
                <div class="col-sm-3">
                    <input class="form-control J_newpassword1" type="password" name="newpassword1" required="" placeholder="请输入8位以上的字母和数字" />
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-2 control-label">再次输入新密码</label>
                <div class="col-sm-3">
                    <input class="form-control J_newpassword2" type="password" name="newpassword2" required="" placeholder="请输入8位以上的字母和数字" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-5" style="text-align: center">
                <a class="btn btn-primary" id="submit">提交</a>
                </div>
            </div>

        </form>
    </div>
</div>

<script type="text/javascript" src="/js/page/changepassword.js"></script>

</@pageFrame>