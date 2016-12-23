<#import "/component/register.ftl" as  register/>

<#macro detailFileInfoHtml>
    <div class="modal fade" id="detailFileInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailFileInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看文件信息</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">文件编号：</label>
                            <div class="col-lg-9">
                                <input id="fileNo2" name="fileNo2"  class="form-control" placeholder="文件编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">业务类型：</label>
                            <div class="col-lg-9">
                                <input id="businessType2" name="businessType2"  class="form-control" placeholder="业务类型" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">业务编号：</label>
                            <div class="col-lg-9">
                                <input id="businessNo2" name="businessNo2"  class="form-control" placeholder="业务编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件名称：</label>
                            <div class="col-lg-9">
                                <input id="fileName2" name="fileName2"  class="form-control" placeholder="文件名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件路径：</label>
                            <div class="col-lg-9">
                                <input id="filePath2" name="filePath2"  class="form-control" placeholder="文件路径" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件根路径：</label>
                            <div class="col-lg-9">
                                <input id="fileRootPath2" name="fileRootPath2"  class="form-control" placeholder="文件根路径" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTm2" name="createTm2"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件状态：</label>
                            <div class="col-lg-9">
                                <input id="fileStat2" name="fileStat2"  class="form-control" placeholder="文件状态" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件大小：</label>
                            <div class="col-lg-9">
                                <input id="fileSize2" name="fileSize2"  class="form-control" placeholder="文件大小" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">文件描述：</label>
                            <div class="col-lg-9">
                                <input id="fileDesc2" name="fileDesc2"  class="form-control" placeholder="文件描述" type="text" disabled="disabled"/>
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



