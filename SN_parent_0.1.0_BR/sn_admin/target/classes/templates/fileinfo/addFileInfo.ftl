<#import "/component/register.ftl" as  register/>

<#macro addFileInfoHtml>
    <div class="modal fade" id="addFileInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateAddFileInfoForm" method="post" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">新增文件信息</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>业务类型：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="businessType1" name="businessType1">
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
                                <input id="businessNo1" name="businessNo1"  class="form-control" placeholder="业务编号" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件名称：</label>
                            <div class="col-lg-9">
                                <input id="fileName1" name="fileName1"  class="form-control" placeholder="文件名称" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件路径：</label>
                            <div class="col-lg-9">
                                <input id="filePath1" name="filePath1"  class="form-control" placeholder="文件路径" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件根路径：</label>
                            <div class="col-lg-9">
                                <input id="fileRootPath1" name="fileRootPath1"  class="form-control" placeholder="文件根路径" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件状态：</label>
                            <div class="col-lg-9">
                                <select class="form-control select2" id="fileStat1" name="fileStat1">
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
                                <input id="fileSize1" name="fileSize1"  class="form-control" placeholder="文件大小" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title"><i class="redFont">*</i>文件描述：</label>
                            <div class="col-lg-9">
                                <input id="fileDesc1" name="fileDesc1"  class="form-control" placeholder="文件描述" type="text"/>
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


