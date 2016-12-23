<#import "/component/register.ftl" as  register/>
<#import "/sysconfig/sysconfig.ftl" as  sysconfig/>
<#import "/sysconfig/addSysConfig.ftl" as  addSysConfig/>
<#import "/sysconfig/updateSysConfig.ftl" as  updateSysConfig/>
<#import "/sysconfig/detailSysConfig.ftl" as  detailSysConfig/>
<#include "/management/resource.ftl"/>

<@sysconfig.sysconfigHtml/>
<@addSysConfig.addSysConfigHtml/>
<@detailSysConfig.detailSysConfigHtml/>
<@updateSysConfig.updateSysConfigHtml/>

<#macro sysconfigHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统配置表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysConfigForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">主键id：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="id" name="id">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">版本号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="version" name="version">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">系统名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="systemName" name="systemName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">图片路径：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="imgUrl" name="imgUrl">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">状态：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="status" name="status">
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
                        <div class="col-md-3">
                            <label class="queryTitle">添加时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createTime" name="createTime">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">创建人名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createName" name="createName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysConfig('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysConfig('','download','download.xls','id:主键id,version:版本号,systemName:系统名称,imgUrl:图片路径,status:状态,createTime:添加时间,createName:创建人名称')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysConfig">添加</button></div>
                <table id="sysConfigTbl" class="table table-bordered">
                    <tr id="sysConfigTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>主键id</th>
                        <th>版本号</th>
                        <th>系统名称</th>
                        <th>图片路径</th>
                        <th>状态</th>
                        <th>添加时间</th>
                        <th>创建人名称</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysConfig_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysConfigpageSize">
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
    <input type="hidden" id="querySysConfigcurrentPage" />
    <input type="hidden" id="querySysConfigtotalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysconfig/sysconfig.js"></script>
</#macro>
<style>
    #sysConfigTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

