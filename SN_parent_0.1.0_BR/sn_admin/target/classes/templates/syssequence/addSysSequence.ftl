<#import "/component/register.ftl" as  register/>

<#macro addSysSequenceHtml>
    <div class="modal fade" id="addSysSequence" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysSequenceForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统ID表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>序列Key：</label>
                            <div class="col-lg-9">
                                <input id="keyValue1" name="keyValue1"  class="form-control" placeholder="序列Key" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>序列描述：</label>
                            <div class="col-lg-9">
                                <input id="seqDesc1" name="seqDesc1"  class="form-control" placeholder="序列描述" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>前缀：</label>
                            <div class="col-lg-9">
                                <input id="top1" name="top1"  class="form-control" placeholder="前缀" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>后缀：</label>
                            <div class="col-lg-9">
                                <input id="suffix1" name="suffix1"  class="form-control" placeholder="后缀" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>当前值：</label>
                            <div class="col-lg-9">
                                <input id="currValue1" name="currValue1"  class="form-control" placeholder="当前值" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>递增数量：</label>
                            <div class="col-lg-9">
                                <input id="batchSize1" name="batchSize1"  class="form-control" placeholder="递增数量" type="text"/>
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


