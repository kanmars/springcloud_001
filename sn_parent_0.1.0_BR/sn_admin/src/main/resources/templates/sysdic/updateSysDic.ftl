<#import "/component/register.ftl" as  register/>

<#macro updateSysDicHtml>
    <div class="modal fade" id="updateSysDic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateSysDicForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改系统字典表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>ID：</label>
                            <div class="col-lg-9">
                                <input id="id3" name="id3"  class="form-control" placeholder="ID" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>一级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l1Code3" name="l1Code3"  class="form-control" placeholder="一级字典码" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>一级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l1Desc3" name="l1Desc3"  class="form-control" placeholder="一级字典描述" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>二级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l2Code3" name="l2Code3"  class="form-control" placeholder="二级字典码" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>二级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l2Desc3" name="l2Desc3"  class="form-control" placeholder="二级字典描述" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>码表参数：</label>
                            <div class="col-lg-9">
                                <input id="codeParam3" name="codeParam3"  class="form-control" placeholder="码表参数" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>实际值：</label>
                            <div class="col-lg-9">
                                <input id="codeValue3" name="codeValue3"  class="form-control" placeholder="实际值" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>排序：</label>
                            <div class="col-lg-9">
                                <input id="codeIdx3" name="codeIdx3"  class="form-control" placeholder="排序" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>存活时间(秒)：</label>
                            <div class="col-lg-9">
                                <input id="liveCount3" name="liveCount3"  class="form-control" placeholder="存活时间(秒)" type="text" />
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
                                <input id="upTime3" name="upTime3"  class="form-control" placeholder="更新时间" type="text" disabled="disabled" />
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



