<#import "/component/register.ftl" as  register/>

<#macro addSysroleMenuInfoHtml>
    <div class="modal fade" id="addSysroleMenuInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysroleMenuInfoForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统角色菜单绑定表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>角色编号：</label>
                            <div class="col-lg-9">
                                <input id="roleNo1" name="roleNo1"  class="form-control" placeholder="角色编号" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单编号：</label>
                            <div class="col-lg-9">
                                <input id="menuNo1" name="menuNo1"  class="form-control" placeholder="菜单编号" type="text"/>
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


