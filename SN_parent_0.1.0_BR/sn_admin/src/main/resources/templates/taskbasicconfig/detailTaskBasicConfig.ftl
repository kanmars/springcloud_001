<#import "/component/register.ftl" as  register/>

<#macro detailTaskBasicConfigHtml>
    <div class="modal fade" id="detailTaskBasicConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailTaskBasicConfigForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看任务基本信息配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">主键id：</label>
                            <div class="col-lg-9">
                                <input id="id2" name="id2"  class="form-control" placeholder="主键id" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">任务名称：</label>
                            <div class="col-lg-9">
                                <input id="taskName2" name="taskName2"  class="form-control" placeholder="任务名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">任务系统类型：</label>
                            <div class="col-lg-9">
                                <input id="parentId2" name="parentId2"  class="form-control" placeholder="任务系统类型" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">执行规则：</label>
                            <div class="col-lg-9">
                                <input id="runRule2" name="runRule2"  class="form-control" placeholder="执行规则" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">执行状态：</label>
                            <div class="col-lg-9">
                                <input id="runStatus2" name="runStatus2"  class="form-control" placeholder="执行状态" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">状态：</label>
                            <div class="col-lg-9">
                                <input id="status2" name="status2"  class="form-control" placeholder="状态" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">业务对象名称：</label>
                            <div class="col-lg-9">
                                <input id="businessObjName2" name="businessObjName2"  class="form-control" placeholder="业务对象名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">任务数：</label>
                            <div class="col-lg-9">
                                <input id="taskCount2" name="taskCount2"  class="form-control" placeholder="任务数" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">网卡名称：</label>
                            <div class="col-lg-9">
                                <input id="internetName2" name="internetName2"  class="form-control" placeholder="网卡名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">服务器IP：</label>
                            <div class="col-lg-9">
                                <input id="serverIp2" name="serverIp2"  class="form-control" placeholder="服务器IP" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">业务参数：</label>
                            <div class="col-lg-9">
                                <input id="businessInfo2" name="businessInfo2"  class="form-control" placeholder="业务参数" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">配置时间：</label>
                            <div class="col-lg-9">
                                <input id="configTime2" name="configTime2"  class="form-control" placeholder="配置时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">修改人：</label>
                            <div class="col-lg-9">
                                <input id="upPerson2" name="upPerson2"  class="form-control" placeholder="修改人" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">修改时间：</label>
                            <div class="col-lg-9">
                                <input id="upTime2" name="upTime2"  class="form-control" placeholder="修改时间" type="text" disabled="disabled"/>
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



