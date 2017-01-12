<#import "/component/register.ftl" as  register/>
<#import "/sysroleinfo/sysroleinfo.ftl" as  sysroleinfo/>
<#import "/sysroleinfo/addSysroleInfo.ftl" as  addSysroleInfo/>
<#import "/sysroleinfo/updateSysroleInfo.ftl" as  updateSysroleInfo/>
<#import "/sysroleinfo/detailSysroleInfo.ftl" as  detailSysroleInfo/>
<#include "/management/resource.ftl"/>

<@sysroleinfo.sysroleinfoHtml/>
<@addSysroleInfo.addSysroleInfoHtml/>
<@detailSysroleInfo.detailSysroleInfoHtml/>
<@updateSysroleInfo.updateSysroleInfoHtml/>

<#macro sysroleinfoHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统角色表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysroleInfoForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">角色编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="roleNo" name="roleNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">角色名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="roleName" name="roleName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">应用名称：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="applicationCode" name="applicationCode">
                                    <option selected="selected" value="">全部</option>
                                    <#--动态模式-start-->
                                    <#assign options=dicList['application_code,application_code']/>
                                    <#list options as item >
                                        <option value="${item.codeParam}">${item.codeValue}</option>
                                    </#list>
                                    <#--动态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">创建人编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createOprNo" name="createOprNo">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">创建人名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createOprName" name="createOprName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">角色附属信息：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="roleOthMsg" name="roleOthMsg">
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
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysroleInfo('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysroleInfo('','download','download.xls','roleNo:角色编号,roleName:角色名称,applicationCode:应用名称,createOprNo:创建人编号,createOprName:创建人名称,roleOthMsg:角色附属信息,createTime:添加时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysroleInfo">添加</button></div>
                <table id="sysroleInfoTbl" class="table table-bordered">
                    <tr id="sysroleInfoTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>角色编号</th>
                        <th>角色名称</th>
                        <th>应用名称</th>
                        <th>创建人编号</th>
                        <th>创建人名称</th>
                        <th>角色附属信息</th>
                        <th>添加时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysroleInfo_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysroleInfopageSize">
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
    <input type="hidden" id="querySysroleInfocurrentPage" />
    <input type="hidden" id="querySysroleInfototalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysroleinfo/sysroleinfo.js"></script>
</#macro>
<style>
    #sysroleInfoTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

