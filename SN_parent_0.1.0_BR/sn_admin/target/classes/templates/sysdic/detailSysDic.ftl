<#import "/component/register.ftl" as  register/>

<#macro detailSysDicHtml>
    <div class="modal fade" id="detailSysDic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailSysDicForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看系统字典表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">ID：</label>
                            <div class="col-lg-9">
                                <input id="id2" name="id2"  class="form-control" placeholder="ID" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">一级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l1Code2" name="l1Code2"  class="form-control" placeholder="一级字典码" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">一级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l1Desc2" name="l1Desc2"  class="form-control" placeholder="一级字典描述" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">二级字典码：</label>
                            <div class="col-lg-9">
                                <input id="l2Code2" name="l2Code2"  class="form-control" placeholder="二级字典码" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">二级字典描述：</label>
                            <div class="col-lg-9">
                                <input id="l2Desc2" name="l2Desc2"  class="form-control" placeholder="二级字典描述" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">码表参数：</label>
                            <div class="col-lg-9">
                                <input id="codeParam2" name="codeParam2"  class="form-control" placeholder="码表参数" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">实际值：</label>
                            <div class="col-lg-9">
                                <input id="codeValue2" name="codeValue2"  class="form-control" placeholder="实际值" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">排序：</label>
                            <div class="col-lg-9">
                                <input id="codeIdx2" name="codeIdx2"  class="form-control" placeholder="排序" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">存活时间(秒)：</label>
                            <div class="col-lg-9">
                                <input id="liveCount2" name="liveCount2"  class="form-control" placeholder="存活时间(秒)" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime2" name="createTime2"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">更新时间：</label>
                            <div class="col-lg-9">
                                <input id="upTime2" name="upTime2"  class="form-control" placeholder="更新时间" type="text" disabled="disabled"/>
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



