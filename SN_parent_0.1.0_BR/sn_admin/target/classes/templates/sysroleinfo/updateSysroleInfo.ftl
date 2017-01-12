<#import "/component/register.ftl" as  register/>

<#macro updateSysroleInfoHtml>
    <div class="modal fade" id="updateSysroleInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysroleInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统角色表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>角色编号：</label>
                            <div class="col-lg-9">
                                <input id="roleNo3" name="roleNo3"  class="form-control" placeholder="角色编号" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>角色名称：</label>
                            <div class="col-lg-9">
                                <input id="roleName3" name="roleName3"  class="form-control" placeholder="角色名称" type="text" />
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
                            <label class="col-lg-3 title"><i class="redFont">*</i>创建人编号：</label>
                            <div class="col-lg-9">
                                <input id="createOprNo3" name="createOprNo3"  class="form-control" placeholder="创建人编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>创建人名称：</label>
                            <div class="col-lg-9">
                                <input id="createOprName3" name="createOprName3"  class="form-control" placeholder="创建人名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>角色附属信息：</label>
                            <div class="col-lg-9">
                                <input id="roleOthMsg3" name="roleOthMsg3"  class="form-control" placeholder="角色附属信息" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" />
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



