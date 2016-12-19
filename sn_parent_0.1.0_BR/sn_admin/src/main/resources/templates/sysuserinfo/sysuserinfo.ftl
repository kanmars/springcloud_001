<#import "/component/register.ftl" as  register/>
<#import "/sysuserinfo/sysuserinfo.ftl" as  sysuserinfo/>
<#import "/sysuserinfo/addSysuserInfo.ftl" as  addSysuserInfo/>
<#import "/sysuserinfo/updateSysuserInfo.ftl" as  updateSysuserInfo/>
<#import "/sysuserinfo/detailSysuserInfo.ftl" as  detailSysuserInfo/>
<#include "/management/resource.ftl"/>

<@sysuserinfo.sysuserinfoHtml/>
<@addSysuserInfo.addSysuserInfoHtml/>
<@detailSysuserInfo.detailSysuserInfoHtml/>
<@updateSysuserInfo.updateSysuserInfoHtml/>

<#macro sysuserinfoHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统用户表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysuserInfoForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">用户编号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="userNo" name="userNo">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">用户姓名：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="userName" name="userName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">用户昵称：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="userNickname" name="userNickname">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">用户登录帐号：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="loginName" name="loginName">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">用户状态：</label>
                            <div class="col-md-8">
                                <select class="form-control select2" id="userStatus" name="userStatus">
                                    <option selected="selected" value="">全部</option>
                                    <#--静态模式-start-->
                                    <#assign options={"010":"正常","020":"禁用","030":"失效"}/>
                                    <#list options?keys as key >
                                        <option value="${key}">${options[key]}</option>
                                    </#list>
                                    <#--静态模式- end -->
                                </select>
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
                            <label class="queryTitle">最后登录时间：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="lastLoginTm" name="lastLoginTm">
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
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysuserInfo('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysuserInfo('','download','download.xls','userNo:用户编号,userName:用户姓名,userNickname:用户昵称,loginName:用户登录帐号,userStatus:用户状态,applicationCode:应用名称,lastLoginIp:最后登录ip,lastLoginTm:最后登录时间,createTime:添加时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysuserInfo">添加</button></div>
                <table id="sysuserInfoTbl" class="table table-bordered">
                    <tr id="sysuserInfoTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>用户编号</th>
                        <th>用户姓名</th>
                        <th>用户昵称</th>
                        <th>用户登录帐号</th>
                        <th>用户状态</th>
                        <th>应用名称</th>
                        <th>最后登录ip</th>
                        <th>最后登录时间</th>
                        <th>添加时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysuserInfo_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysuserInfopageSize">
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
    <input type="hidden" id="querySysuserInfocurrentPage" />
    <input type="hidden" id="querySysuserInfototalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysuserinfo/sysuserinfo.js"></script>
</#macro>
<style>
    #sysuserInfoTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

