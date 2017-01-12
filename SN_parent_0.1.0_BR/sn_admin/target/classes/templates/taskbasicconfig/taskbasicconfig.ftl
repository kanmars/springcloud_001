<#import "/component/register.ftl" as  register/>
<#import "/taskbasicconfig/taskbasicconfig.ftl" as  taskbasicconfig/>
<#import "/taskbasicconfig/addTaskBasicConfig.ftl" as  addTaskBasicConfig/>
<#import "/taskbasicconfig/updateTaskBasicConfig.ftl" as  updateTaskBasicConfig/>
<#import "/taskbasicconfig/detailTaskBasicConfig.ftl" as  detailTaskBasicConfig/>
<#include "/management/resource.ftl"/>

<@taskbasicconfig.taskbasicconfigHtml/>
<@addTaskBasicConfig.addTaskBasicConfigHtml/>
<@detailTaskBasicConfig.detailTaskBasicConfigHtml/>
<@updateTaskBasicConfig.updateTaskBasicConfigHtml/>

<#macro taskbasicconfigHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                任务基本信息配置表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=queryTaskBasicConfigForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">主键id：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="id" name="id">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">任务名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="taskName" name="taskName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">任务系统类型：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="parentId" name="parentId">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">执行规则：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="runRule" name="runRule">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">执行状态：</label>
                            <div class="col-md-8">
                                <#--静态模式-start-->
                                <#assign options={"0":"无限制执行","1":"同任务串行执行","2":"同任务执行中抛弃新来任务"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="runStatus_${key}" name="runStatus" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">状态：</label>
                            <div class="col-md-8">
                                <#--静态模式-start-->
                                <#assign options={"1":"开启","2":"禁用"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="status_${key}" name="status" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">业务对象名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="businessObjName" name="businessObjName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">任务数：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="taskCount" name="taskCount">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">网卡名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="internetName" name="internetName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">服务器IP：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="serverIp" name="serverIp">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">业务参数：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="businessInfo" name="businessInfo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">配置时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="configTime" name="configTime">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">修改人：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="upPerson" name="upPerson">
                            </div>
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchTaskBasicConfig('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchTaskBasicConfig('','download','download.xls','id:主键id,taskName:任务名称,parentId:任务系统类型,runRule:执行规则,runStatus:执行状态,status:状态,businessObjName:业务对象名称,taskCount:任务数,internetName:网卡名称,serverIp:服务器IP,businessInfo:业务参数,configTime:配置时间,upPerson:修改人,upTime:修改时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addTaskBasicConfig">添加</button></div>
                <table id="taskBasicConfigTbl" class="table table-bordered">
                    <tr id="taskBasicConfigTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>主键id</th>
                        <th>任务名称</th>
                        <th>任务系统类型</th>
                        <th>执行规则</th>
                        <th>执行状态</th>
                        <th>状态</th>
                        <th>业务对象名称</th>
                        <th>任务数</th>
                        <th>网卡名称</th>
                        <th>服务器IP</th>
                        <th>业务参数</th>
                        <th>配置时间</th>
                        <th>修改人</th>
                        <th>修改时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="queryTaskBasicConfig_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="queryTaskBasicConfigpageSize">
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
    <input type="hidden" id="queryTaskBasicConfigcurrentPage" />
    <input type="hidden" id="queryTaskBasicConfigtotalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/taskbasicconfig/taskbasicconfig.js"></script>
</#macro>
<style>
    #taskBasicConfigTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

