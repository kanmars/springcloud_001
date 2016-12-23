<#import "/component/register.ftl" as  register/>

<#macro addTaskBasicConfigHtml>
    <div class="modal fade" id="addTaskBasicConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddTaskBasicConfigForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增任务基本信息配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务名称：</label>
                            <div class="col-lg-9">
                                <input id="taskName1" name="taskName1"  class="form-control" placeholder="任务名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务系统类型：</label>
                            <div class="col-lg-9">
                                <input id="parentId1" name="parentId1"  class="form-control" placeholder="任务系统类型" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>执行规则：</label>
                            <div class="col-lg-9">
                                <input id="runRule1" name="runRule1"  class="form-control" placeholder="执行规则" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>执行状态：</label>
                            <div class="col-lg-9">
                                <#--静态模式-start-->
                                <#assign options={"0":"无限制执行","1":"同任务串行执行","2":"同任务执行中抛弃新来任务"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="runStatus1_${key}" name="runStatus1" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>状态：</label>
                            <div class="col-lg-9">
                                <#--静态模式-start-->
                                <#assign options={"1":"开启","2":"禁用"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="status1_${key}" name="status1" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务对象名称：</label>
                            <div class="col-lg-9">
                                <input id="businessObjName1" name="businessObjName1"  class="form-control" placeholder="业务对象名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务数：</label>
                            <div class="col-lg-9">
                                <input id="taskCount1" name="taskCount1"  class="form-control" placeholder="任务数" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>网卡名称：</label>
                            <div class="col-lg-9">
                                <input id="internetName1" name="internetName1"  class="form-control" placeholder="网卡名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>服务器IP：</label>
                            <div class="col-lg-9">
                                <input id="serverIp1" name="serverIp1"  class="form-control" placeholder="服务器IP" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务参数：</label>
                            <div class="col-lg-9">
                                <input id="businessInfo1" name="businessInfo1"  class="form-control" placeholder="业务参数" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>配置时间：</label>
                            <div class="col-lg-9">
                                <input id="configTime1" name="configTime1"  class="form-control" placeholder="配置时间" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>修改人：</label>
                            <div class="col-lg-9">
                                <input id="upPerson1" name="upPerson1"  class="form-control" placeholder="修改人" type="text"/>
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


