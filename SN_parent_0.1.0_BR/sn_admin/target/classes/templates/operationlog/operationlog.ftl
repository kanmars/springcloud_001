<#import "/component/register.ftl" as  register/>
<#import "/operationlog/operationlog.ftl" as  operationlog/>
<#import "/operationlog/addOperationLog.ftl" as  addOperationLog/>
<#import "/operationlog/updateOperationLog.ftl" as  updateOperationLog/>
<#import "/operationlog/detailOperationLog.ftl" as  detailOperationLog/>
<#include "/management/resource.ftl"/>

<@operationlog.operationlogHtml/>
<@addOperationLog.addOperationLogHtml/>
<@detailOperationLog.detailOperationLogHtml/>
<@updateOperationLog.updateOperationLogHtml/>

<#macro operationlogHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                操作日志表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=queryOperationLogForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">操作员：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationUser" name="operationUser">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">操作时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationTime" name="operationTime">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">操作名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationName" name="operationName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">操作应用：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationApp" name="operationApp">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">操作类与方法：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationClassmethod" name="operationClassmethod">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">操作信息：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="operationInfo" name="operationInfo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">添加时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createTime" name="createTime">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchOperationLog('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchOperationLog('','download','download.xls','operationUser:操作员,operationTime:操作时间,operationName:操作名称,operationApp:操作应用,operationClassmethod:操作类与方法,createTime:添加时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <!--
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addOperationLog">添加</button></div>
                -->
                <table id="operationLogTbl" class="table table-bordered">
                    <tr id="operationLogTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>操作员</th>
                        <th>操作时间</th>
                        <th>操作名称</th>
                        <th>操作应用</th>
                        <th>操作类与方法</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="queryOperationLog_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="queryOperationLogpageSize">
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
    <input type="hidden" id="queryOperationLogcurrentPage" />
    <input type="hidden" id="queryOperationLogtotalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/operationlog/operationlog.js"></script>
</#macro>
<style>
    #operationLogTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

