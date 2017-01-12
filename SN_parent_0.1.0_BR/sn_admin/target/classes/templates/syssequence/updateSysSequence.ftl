<#import "/component/register.ftl" as  register/>

<#macro updateSysSequenceHtml>
    <div class="modal fade" id="updateSysSequence" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysSequenceForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统ID表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>id：</label>
                            <div class="col-lg-9">
                                <input id="id3" name="id3"  class="form-control" placeholder="id" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>序列Key：</label>
                            <div class="col-lg-9">
                                <input id="keyValue3" name="keyValue3"  class="form-control" placeholder="序列Key" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>序列描述：</label>
                            <div class="col-lg-9">
                                <input id="seqDesc3" name="seqDesc3"  class="form-control" placeholder="序列描述" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>前缀：</label>
                            <div class="col-lg-9">
                                <input id="top3" name="top3"  class="form-control" placeholder="前缀" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>后缀：</label>
                            <div class="col-lg-9">
                                <input id="suffix3" name="suffix3"  class="form-control" placeholder="后缀" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>当前值：</label>
                            <div class="col-lg-9">
                                <input id="currValue3" name="currValue3"  class="form-control" placeholder="当前值" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>递增数量：</label>
                            <div class="col-lg-9">
                                <input id="batchSize3" name="batchSize3"  class="form-control" placeholder="递增数量" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime3" name="createTime3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
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



