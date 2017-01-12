<#import "/component/register.ftl" as  register/>
<#import "/sysrolemenuinfo/sysrolemenuinfo.ftl" as  sysrolemenuinfo/>
<#import "/sysrolemenuinfo/addSysroleMenuInfo.ftl" as  addSysroleMenuInfo/>
<#import "/sysrolemenuinfo/updateSysroleMenuInfo.ftl" as  updateSysroleMenuInfo/>
<#import "/sysrolemenuinfo/detailSysroleMenuInfo.ftl" as  detailSysroleMenuInfo/>
<#include "/management/resource.ftl"/>

<@sysrolemenuinfo.sysrolemenuinfoHtml/>
<@addSysroleMenuInfo.addSysroleMenuInfoHtml/>
<@detailSysroleMenuInfo.detailSysroleMenuInfoHtml/>
<@updateSysroleMenuInfo.updateSysroleMenuInfoHtml/>

<#macro sysrolemenuinfoHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统角色菜单绑定表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysroleMenuInfoForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">角色编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="roleNo" name="roleNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">菜单编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="menuNo" name="menuNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">添加时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="createTime" name="createTime">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">更新时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="upTime" name="upTime">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysroleMenuInfo('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysroleMenuInfo('','download','download.xls','roleNo:角色编号,menuNo:菜单编号,createTime:添加时间,upTime:更新时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysroleMenuInfo">添加</button></div>
                <table id="sysroleMenuInfoTbl" class="table table-bordered">
                    <tr id="sysroleMenuInfoTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>角色编号</th>
                        <th>菜单编号</th>
                        <th>添加时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysroleMenuInfo_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysroleMenuInfopageSize">
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
    <input type="hidden" id="querySysroleMenuInfocurrentPage" />
    <input type="hidden" id="querySysroleMenuInfototalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysrolemenuinfo/sysrolemenuinfo.js"></script>
</#macro>
<style>
    #sysroleMenuInfoTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

