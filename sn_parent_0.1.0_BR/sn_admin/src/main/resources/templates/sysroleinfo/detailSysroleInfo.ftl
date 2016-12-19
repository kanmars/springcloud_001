<#import "/component/register.ftl" as  register/>

<#macro detailSysroleInfoHtml>
    <div class="modal fade" id="detailSysroleInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailSysroleInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看系统角色表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">角色编号：</label>
                            <div class="col-lg-9">
                                <input id="roleNo2" name="roleNo2"  class="form-control" placeholder="角色编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">角色名称：</label>
                            <div class="col-lg-9">
                                <input id="roleName2" name="roleName2"  class="form-control" placeholder="角色名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">应用名称：</label>
                            <div class="col-lg-9">
                                <input id="applicationCode2" name="applicationCode2"  class="form-control" placeholder="应用名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">创建人编号：</label>
                            <div class="col-lg-9">
                                <input id="createOprNo2" name="createOprNo2"  class="form-control" placeholder="创建人编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">创建人名称：</label>
                            <div class="col-lg-9">
                                <input id="createOprName2" name="createOprName2"  class="form-control" placeholder="创建人名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">角色附属信息：</label>
                            <div class="col-lg-9">
                                <input id="roleOthMsg2" name="roleOthMsg2"  class="form-control" placeholder="角色附属信息" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime2" name="createTime2"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>



