<#import "/component/register.ftl" as  register/>

<#macro updateSysConfigHtml>
    <div class="modal fade" id="updateSysConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysConfigForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>主键id：</label>
                            <div class="col-lg-9">
                                <input id="id3" name="id3"  class="form-control" placeholder="主键id" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>版本号：</label>
                            <div class="col-lg-9">
                                <input id="version3" name="version3"  class="form-control" placeholder="版本号" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>系统名称：</label>
                            <div class="col-lg-9">
                                <input id="systemName3" name="systemName3"  class="form-control" placeholder="系统名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>图片路径：</label>
                            <div class="col-lg-9">
                                <input id="imgUrl3" name="imgUrl3"  class="form-control" placeholder="图片路径" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>状态：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="status3" name="status3"  >
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
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>创建人名称：</label>
                            <div class="col-lg-9">
                                <input id="createName3" name="createName3"  class="form-control" placeholder="创建人名称" type="text" disabled="disabled"/>
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



