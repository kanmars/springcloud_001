<#import "/component/register.ftl" as  register/>

<#macro detailSysConfigHtml>
    <div class="modal fade" id="detailSysConfig" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailSysConfigForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看系统配置表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">主键id：</label>
                            <div class="col-lg-9">
                                <input id="id2" name="id2"  class="form-control" placeholder="主键id" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">版本号：</label>
                            <div class="col-lg-9">
                                <input id="version2" name="version2"  class="form-control" placeholder="版本号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">系统名称：</label>
                            <div class="col-lg-9">
                                <input id="systemName2" name="systemName2"  class="form-control" placeholder="系统名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">图片路径：</label>
                            <div class="col-lg-9">
                                <input id="imgUrl2" name="imgUrl2"  class="form-control" placeholder="图片路径" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">状态：</label>
                            <div class="col-lg-9">
                                <input id="status2" name="status2"  class="form-control" placeholder="状态" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime2" name="createTime2"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">创建人名称：</label>
                            <div class="col-lg-9">
                                <input id="createName2" name="createName2"  class="form-control" placeholder="创建人名称" type="text" disabled="disabled"/>
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



