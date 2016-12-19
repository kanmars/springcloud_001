<#import "/component/register.ftl" as  register/>

<#macro updateSysmenuInfoHtml>
    <div class="modal fade" id="updateSysmenuInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysmenuInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统菜单表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单编号：</label>
                            <div class="col-lg-9">
                                <input id="menuNo3" name="menuNo3"  class="form-control" placeholder="菜单编号" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单名称：</label>
                            <div class="col-lg-9">
                                <input id="menuName3" name="menuName3"  class="form-control" placeholder="菜单名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单类型：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="menuType3" name="menuType3"  >
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"010":"空菜单","020":"带资源的菜单","030":"资源页面"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>父菜单编码：</label>
                            <div class="col-lg-9">
                                <input id="parentMenuCode3" name="parentMenuCode3"  class="form-control" placeholder="父菜单编码" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单url：</label>
                            <div class="col-lg-9">
                                <input id="menuUrl3" name="menuUrl3"  class="form-control" placeholder="菜单url" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单排序：</label>
                            <div class="col-lg-9">
                                <input id="menuIdx3" name="menuIdx3"  class="form-control" placeholder="菜单排序" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>应用名称：</label>
                            <div class="col-lg-9">
                                <#--静态模式-start-->
                                <#assign options={"010":"系统A","020":"系统B","030":"系统C"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="applicationCode3_${key}" name="applicationCode3" value="${key}" >${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>更新时间：</label>
                            <div class="col-lg-9">
                                <input id="upTime3" name="upTime3"  class="form-control" placeholder="更新时间" type="text" disabled="disabled"/>
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



