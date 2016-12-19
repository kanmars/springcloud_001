<#import "/component/register.ftl" as  register/>

<#macro addSysConfigHtml>
    <div class="modal fade" id="addSysConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysConfigForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>版本号：</label>
                            <div class="col-lg-9">
                                <input id="version1" name="version1"  class="form-control" placeholder="版本号" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>系统名称：</label>
                            <div class="col-lg-9">
                                <input id="systemName1" name="systemName1"  class="form-control" placeholder="系统名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>图片路径：</label>
                            <div class="col-lg-9">
                                <input id="imgUrl1" name="imgUrl1"  class="form-control" placeholder="图片路径" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>状态：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="status1" name="status1">
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"001":"启用","002":"禁用"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>创建人名称：</label>
                            <div class="col-lg-9">
                                <input id="createName1" name="createName1"  class="form-control" placeholder="创建人名称" type="text"/>
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


