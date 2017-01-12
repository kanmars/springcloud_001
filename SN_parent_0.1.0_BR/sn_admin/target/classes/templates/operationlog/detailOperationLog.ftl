<#import "/component/register.ftl" as  register/>

<#macro detailOperationLogHtml>
    <div class="modal fade" id="detailOperationLog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailOperationLogForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看操作日志表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">操作ID：</label>
                            <div class="col-lg-9">
                                <input id="operationId2" name="operationId2"  class="form-control" placeholder="操作ID" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作员：</label>
                            <div class="col-lg-9">
                                <input id="operationUser2" name="operationUser2"  class="form-control" placeholder="操作员" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作时间：</label>
                            <div class="col-lg-9">
                                <input id="operationTime2" name="operationTime2"  class="form-control" placeholder="操作时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作名称：</label>
                            <div class="col-lg-9">
                                <input id="operationName2" name="operationName2"  class="form-control" placeholder="操作名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作描述：</label>
                            <div class="col-lg-9">
                                <input id="operationDesc2" name="operationDesc2"  class="form-control" placeholder="操作描述" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作应用：</label>
                            <div class="col-lg-9">
                                <input id="operationApp2" name="operationApp2"  class="form-control" placeholder="操作应用" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作类与方法：</label>
                            <div class="col-lg-9">
                                <input id="operationClassmethod2" name="operationClassmethod2"  class="form-control" placeholder="操作类与方法" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">操作信息：</label>
                            <div class="col-lg-9">
                                <textarea id="operationInfo2" name="operationInfo2"  class="form-control" placeholder="操作信息" type="text" disabled="disabled"></textarea>
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



