<#import "/component/register.ftl" as  register/>

<#macro updateOperationLogHtml>
    <div class="modal fade" id="updateOperationLog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateOperationLogForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改操作日志表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作ID：</label>
                            <div class="col-lg-9">
                                <input id="operationId3" name="operationId3"  class="form-control" placeholder="操作ID" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作员：</label>
                            <div class="col-lg-9">
                                <input id="operationUser3" name="operationUser3"  class="form-control" placeholder="操作员" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作时间：</label>
                            <div class="col-lg-9">
                                <input id="operationTime3" name="operationTime3"  class="form-control" placeholder="操作时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作名称：</label>
                            <div class="col-lg-9">
                                <input id="operationName3" name="operationName3"  class="form-control" placeholder="操作名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作描述：</label>
                            <div class="col-lg-9">
                                <input id="operationDesc3" name="operationDesc3"  class="form-control" placeholder="操作描述" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作应用：</label>
                            <div class="col-lg-9">
                                <input id="operationApp3" name="operationApp3"  class="form-control" placeholder="操作应用" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作类与方法：</label>
                            <div class="col-lg-9">
                                <input id="operationClassmethod3" name="operationClassmethod3"  class="form-control" placeholder="操作类与方法" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>操作信息：</label>
                            <div class="col-lg-9">
                                <input id="operationInfo3" name="operationInfo3"  class="form-control" placeholder="操作信息" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
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



