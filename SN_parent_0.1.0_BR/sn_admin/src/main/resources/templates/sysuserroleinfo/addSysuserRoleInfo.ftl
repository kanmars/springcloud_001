<#import "/component/register.ftl" as  register/>

<#macro addSysuserRoleInfoHtml>
    <div class="modal fade" id="addSysuserRoleInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysuserRoleInfoForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统用户角色绑定表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>用户编号：</label>
                            <div class="col-lg-9">
                                <input id="userNo1" name="userNo1"  class="form-control" placeholder="用户编号" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>角色编号：</label>
                            <div class="col-lg-9">
                                <input id="roleNo1" name="roleNo1"  class="form-control" placeholder="角色编号" type="text"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>


