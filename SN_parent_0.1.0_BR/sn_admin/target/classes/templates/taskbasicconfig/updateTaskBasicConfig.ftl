<#import "/component/register.ftl" as  register/>

<#macro updateTaskBasicConfigHtml>
    <div class="modal fade" id="updateTaskBasicConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateTaskBasicConfigForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改任务基本信息配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>主键id：</label>
                            <div class="col-lg-9">
                                <input id="id3" name="id3"  class="form-control" placeholder="主键id" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务名称：</label>
                            <div class="col-lg-9">
                                <input id="taskName3" name="taskName3"  class="form-control" placeholder="任务名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务系统类型：</label>
                            <div class="col-lg-9">
                                <input id="parentId3" name="parentId3"  class="form-control" placeholder="任务系统类型" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>执行规则：</label>
                            <div class="col-lg-9">
                                <input id="runRule3" name="runRule3"  class="form-control" placeholder="执行规则" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>执行状态：</label>
                            <div class="col-lg-9">
                                <#--静态模式-start-->
                                <#assign options={"0":"无限制执行","1":"同任务串行执行","2":"同任务执行中抛弃新来任务"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="runStatus3_${key}" name="runStatus3" value="${key}" >${options[key]}
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
                                    <input type="radio" id="status3_${key}" name="status3" value="${key}" >${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务对象名称：</label>
                            <div class="col-lg-9">
                                <input id="businessObjName3" name="businessObjName3"  class="form-control" placeholder="业务对象名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>任务数：</label>
                            <div class="col-lg-9">
                                <input id="taskCount3" name="taskCount3"  class="form-control" placeholder="任务数" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>网卡名称：</label>
                            <div class="col-lg-9">
                                <input id="internetName3" name="internetName3"  class="form-control" placeholder="网卡名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>服务器IP：</label>
                            <div class="col-lg-9">
                                <input id="serverIp3" name="serverIp3"  class="form-control" placeholder="服务器IP" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务参数：</label>
                            <div class="col-lg-9">
                                <input id="businessInfo3" name="businessInfo3"  class="form-control" placeholder="业务参数" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>配置时间：</label>
                            <div class="col-lg-9">
                                <input id="configTime3" name="configTime3"  class="form-control" placeholder="配置时间" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>修改人：</label>
                            <div class="col-lg-9">
                                <input id="upPerson3" name="upPerson3"  class="form-control" placeholder="修改人" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>修改时间：</label>
                            <div class="col-lg-9">
                                <input id="upTime3" name="upTime3"  class="form-control" placeholder="修改时间" type="text" disabled="disabled"/>
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



