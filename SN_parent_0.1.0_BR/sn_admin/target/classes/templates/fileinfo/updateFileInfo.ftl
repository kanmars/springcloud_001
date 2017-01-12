<#import "/component/register.ftl" as  register/>

<#macro updateFileInfoHtml>
    <div class="modal fade" id="updateFileInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateUpdateFileInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">修改文件信息</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件编号：</label>
                            <div class="col-lg-9">
                                <input id="fileNo3" name="fileNo3"  class="form-control" placeholder="文件编号" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务类型：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="businessType3" name="businessType3"  >
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"010":"A类文件","020":"B类文件","030":"C类文件"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务编号：</label>
                            <div class="col-lg-9">
                                <input id="businessNo3" name="businessNo3"  class="form-control" placeholder="业务编号" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件名称：</label>
                            <div class="col-lg-9">
                                <input id="fileName3" name="fileName3"  class="form-control" placeholder="文件名称" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件路径：</label>
                            <div class="col-lg-9">
                                <input id="filePath3" name="filePath3"  class="form-control" placeholder="文件路径" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件根路径：</label>
                            <div class="col-lg-9">
                                <input id="fileRootPath3" name="fileRootPath3"  class="form-control" placeholder="文件根路径" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>添加时间：</label>
                            <div class="col-lg-9">
                                <input id="createTm3" name="createTm3"  class="form-control" placeholder="添加时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件状态：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="fileStat3" name="fileStat3"  >
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"001":"启用","002":"禁用"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件大小：</label>
                            <div class="col-lg-9">
                                <input id="fileSize3" name="fileSize3"  class="form-control" placeholder="文件大小" type="text" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件描述：</label>
                            <div class="col-lg-9">
                                <input id="fileDesc3" name="fileDesc3"  class="form-control" placeholder="文件描述" type="text" />
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



