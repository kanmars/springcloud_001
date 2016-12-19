<#import "/component/register.ftl" as  register/>

<#macro updateSysuserInfoHtml>
    <div class="modal fade" id="updateSysuserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysuserInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统用户表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户编号：</label>
                            <div class="col-lg-9">
                                <input id="userNo3" name="userNo3"  class="form-control" placeholder="用户编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户姓名：</label>
                            <div class="col-lg-9">
                                <input id="userName3" name="userName3"  class="form-control" placeholder="用户姓名" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户昵称：</label>
                            <div class="col-lg-9">
                                <input id="userNickname3" name="userNickname3"  class="form-control" placeholder="用户昵称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户登录帐号：</label>
                            <div class="col-lg-9">
                                <input id="loginName3" name="loginName3"  class="form-control" placeholder="用户登录帐号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户密码：</label>
                            <div class="col-lg-9">
                                <input id="password3" name="password3"  class="form-control" placeholder="用户密码" type="password" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户状态：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="userStatus3" name="userStatus3"  >
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"010":"正常","020":"禁用","030":"失效"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>应用名称：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="applicationCode3" name="applicationCode3"  >
                                    <option selected="selected" value="">全部</option>
                                    <#--动态模式-start-->
                                    <#assign options=dicList['application_code,application_code']/>
                                    <#list options as item >
                                        <option value="${item.codeParam}">${item.codeValue}</option>
                                    </#list>
                                    <#--动态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>最后登录ip：</label>
                            <div class="col-lg-9">
                                <input id="lastLoginIp3" name="lastLoginIp3"  class="form-control" placeholder="最后登录ip" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>最后登录时间：</label>
                            <div class="col-lg-9">
                                <input id="lastLoginTm3" name="lastLoginTm3"  class="form-control" placeholder="最后登录时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="submit" class="btn btn-primary">提交更改</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>



