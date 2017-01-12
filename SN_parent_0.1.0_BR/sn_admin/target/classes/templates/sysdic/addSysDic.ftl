<#import "/component/register.ftl" as  register/>

<#macro addSysDicHtml>
    <div class="modal fade" id="addSysDic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddSysDicForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增系统字典表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>一级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l1Code1" name="l1Code1"  class="form-control" placeholder="一级字典码" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>一级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l1Desc1" name="l1Desc1"  class="form-control" placeholder="一级字典描述" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>二级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l2Code1" name="l2Code1"  class="form-control" placeholder="二级字典码" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>二级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l2Desc1" name="l2Desc1"  class="form-control" placeholder="二级字典描述" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>码表参数：</label>
                            <div class="col-lg-9">
                                <input id="codeParam1" name="codeParam1"  class="form-control" placeholder="码表参数" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>实际值：</label>
                            <div class="col-lg-9">
                                <input id="codeValue1" name="codeValue1"  class="form-control" placeholder="实际值" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>排序：</label>
                            <div class="col-lg-9">
                                <input id="codeIdx1" name="codeIdx1"  class="form-control" placeholder="排序" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>存活时间(秒)：</label>
                            <div class="col-lg-9">
                                <input id="liveCount1" name="liveCount1"  class="form-control" placeholder="存活时间(秒)" type="text"/>
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


