<#import "/component/register.ftl" as  register/>

<#macro addSysmenuInfoHtml>
    <div class="modal fade" id="addSysmenuInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysmenuInfoForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统菜单表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单编号：</label>
                            <div class="col-lg-9">
                                <input id="menuNo1" name="menuNo1"  class="form-control" placeholder="菜单编号" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单名称：</label>
                            <div class="col-lg-9">
                                <input id="menuName1" name="menuName1"  class="form-control" placeholder="菜单名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单类型：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="menuType1" name="menuType1">
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
                                <input id="parentMenuCode1" name="parentMenuCode1"  class="form-control" placeholder="父菜单编码" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单url：</label>
                            <div class="col-lg-9">
                                <input id="menuUrl1" name="menuUrl1"  class="form-control" placeholder="菜单url" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>菜单排序：</label>
                            <div class="col-lg-9">
                                <input id="menuIdx1" name="menuIdx1"  class="form-control" placeholder="菜单排序" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>应用名称：</label>
                            <div class="col-lg-9">
                                <#--静态模式-start-->
                                <#assign options={"010":"系统A","020":"系统B","030":"系统C"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="applicationCode1_${key}" name="applicationCode1" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime1" name="createTime1"  class="form-control" placeholder="添加时间" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>更新时间：</label>
                            <div class="col-lg-9">
                                <input id="upTime1" name="upTime1"  class="form-control" placeholder="更新时间" type="text"/>
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


