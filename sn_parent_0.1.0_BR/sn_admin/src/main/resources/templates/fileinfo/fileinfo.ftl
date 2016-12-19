<#import "/component/register.ftl" as  register/>
<#import "/fileinfo/fileinfo.ftl" as  fileinfo/>
<#import "/fileinfo/addFileInfo.ftl" as  addFileInfo/>
<#import "/fileinfo/updateFileInfo.ftl" as  updateFileInfo/>
<#import "/fileinfo/detailFileInfo.ftl" as  detailFileInfo/>
<#include "/management/resource.ftl"/>

<@fileinfo.fileinfoHtml/>
<@addFileInfo.addFileInfoHtml/>
<@detailFileInfo.detailFileInfoHtml/>
<@updateFileInfo.updateFileInfoHtml/>

<#macro fileinfoHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                文件信息<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=queryFileInfoForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">文件编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="fileNo" name="fileNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">业务类型：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="businessType" name="businessType">
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
                        <div class="col-md-3">
                            <label class="queryTitle">业务编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="businessNo" name="businessNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">文件名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="fileName" name="fileName">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">文件路径：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="filePath" name="filePath">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">文件根路径：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="fileRootPath" name="fileRootPath">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">添加时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createTm" name="createTm">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">文件状态：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="fileStat" name="fileStat">
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
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">文件大小：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="fileSize" name="fileSize">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">文件描述：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="fileDesc" name="fileDesc">
                            </div>
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchFileInfo('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchFileInfo('','download','download.xls','fileNo:文件编号,businessType:业务类型,businessNo:业务编号,fileName:文件名称,filePath:文件路径,fileRootPath:文件根路径,createTm:添加时间,fileStat:文件状态,fileSize:文件大小,fileDesc:文件描述')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addFileInfo">添加</button></div>
                <table id="fileInfoTbl" class="table table-bordered">
                    <tr id="fileInfoTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>文件编号</th>
                        <th>业务类型</th>
                        <th>业务编号</th>
                        <th>文件名称</th>
                        <th>文件路径</th>
                        <th>文件根路径</th>
                        <th>添加时间</th>
                        <th>文件状态</th>
                        <th>文件大小</th>
                        <th>文件描述</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="queryFileInfo_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="queryFileInfopageSize">
                        <option selected="selected">10</option>
                        <option>20</option>
                        <option>50</option>
                    </select>
                </div>
                <div style="margin: 10px; float: right" class="pager clearfix"	id="commonPager">
                    <a href="javascript:void(0);" class="prev disable">&nbsp;&lt;<s	class="icon-prev"></s><i></i></a> 
                    <span class="cur">1</span> <a href="javascript:void(0);" class="next disable">&gt;<s class="icon-next"></s><i></i></a> 
                    <label class="txt-wrap"	for="pagenum">到<input type="text" value="1" bnum="0" class="txt" id="pNum">页</label> 
                    <a class="btn" zdx="nBtn" href="javascript:void(0)">确定</a>
                </div>
            </div>
            <!--分页 end-->

        </div>
        <!-- /.box -->
    </section>
</div>
<div>
    <input type="hidden" id="queryFileInfocurrentPage" />
    <input type="hidden" id="queryFileInfototalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/fileinfo/fileinfo.js"></script>
</#macro>
<style>
    #fileInfoTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

