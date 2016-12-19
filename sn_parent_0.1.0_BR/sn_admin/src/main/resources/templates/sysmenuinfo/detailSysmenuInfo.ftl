<#import "/component/register.ftl" as  register/>

<#macro detailSysmenuInfoHtml>
    <div class="modal fade" id="detailSysmenuInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailSysmenuInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看系统菜单表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">菜单编号：</label>
                            <div class="col-lg-9">
                                <input id="menuNo2" name="menuNo2"  class="form-control" placeholder="菜单编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">菜单名称：</label>
                            <div class="col-lg-9">
                                <input id="menuName2" name="menuName2"  class="form-control" placeholder="菜单名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">菜单类型：</label>
                            <div class="col-lg-9">
                                <input id="menuType2" name="menuType2"  class="form-control" placeholder="菜单类型" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">父菜单编码：</label>
                            <div class="col-lg-9">
                                <input id="parentMenuCode2" name="parentMenuCode2"  class="form-control" placeholder="父菜单编码" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">菜单url：</label>
                            <div class="col-lg-9">
                                <input id="menuUrl2" name="menuUrl2"  class="form-control" placeholder="菜单url" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">菜单排序：</label>
                            <div class="col-lg-9">
                                <input id="menuIdx2" name="menuIdx2"  class="form-control" placeholder="菜单排序" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">应用名称：</label>
                            <div class="col-lg-9">
                                <input id="applicationCode2" name="applicationCode2"  class="form-control" placeholder="应用名称" type="text" disabled="disabled"/>
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



