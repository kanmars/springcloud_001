<#import "/component/register.ftl" as  register/>
<#import "/sysmenuinfo/sysmenuinfo.ftl" as  sysmenuinfo/>
<#import "/sysmenuinfo/addSysmenuInfo.ftl" as  addSysmenuInfo/>
<#import "/sysmenuinfo/updateSysmenuInfo.ftl" as  updateSysmenuInfo/>
<#import "/sysmenuinfo/detailSysmenuInfo.ftl" as  detailSysmenuInfo/>
<#include "/management/resource.ftl"/>

<@sysmenuinfo.sysmenuinfoHtml/>
<@addSysmenuInfo.addSysmenuInfoHtml/>
<@detailSysmenuInfo.detailSysmenuInfoHtml/>
<@updateSysmenuInfo.updateSysmenuInfoHtml/>

<#macro sysmenuinfoHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统菜单表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysmenuInfoForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">菜单编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="menuNo" name="menuNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">菜单名称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="menuName" name="menuName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">菜单类型：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="menuType" name="menuType">
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"010":"空菜单","020":"带资源的菜单","030":"资源页面"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">父菜单编码：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="parentMenuCode" name="parentMenuCode">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">菜单url：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="menuUrl" name="menuUrl">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">菜单排序：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="menuIdx" name="menuIdx">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">应用名称：</label>
                            <div class="col-md-8">
                                <#--静态模式-start-->
                                <#assign options={"010":"系统A","020":"系统B","030":"系统C"}/>
                                <#list options?keys as key >
                                <label class="radio-inline">
                                    <input type="radio" id="applicationCode_${key}" name="applicationCode" value="${key}">${options[key]}
                                </label>
                                </#list>
                                <#--静态模式- end -->
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">添加时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createTime" name="createTime">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">更新时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="upTime" name="upTime">
                            </div>
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysmenuInfo('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysmenuInfo('','download','download.xls','menuNo:菜单编号,menuName:菜单名称,menuType:菜单类型,parentMenuCode:父菜单编码,menuUrl:菜单url,menuIdx:菜单排序,applicationCode:应用名称,createTime:添加时间,upTime:更新时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysmenuInfo">添加</button></div>
                <table id="sysmenuInfoTbl" class="table table-bordered">
                    <tr id="sysmenuInfoTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>菜单编号</th>
                        <th>菜单名称</th>
                        <th>菜单类型</th>
                        <th>父菜单编码</th>
                        <th>菜单url</th>
                        <th>菜单排序</th>
                        <th>应用名称</th>
                        <th>添加时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysmenuInfo_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysmenuInfopageSize">
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
    <input type="hidden" id="querySysmenuInfocurrentPage" />
    <input type="hidden" id="querySysmenuInfototalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysmenuinfo/sysmenuinfo.js"></script>
</#macro>
<style>
    #sysmenuInfoTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

