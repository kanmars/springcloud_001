<#import "/component/register.ftl" as  register/>
<#import "/sysdic/sysdic.ftl" as  sysdic/>
<#import "/sysdic/addSysDic.ftl" as  addSysDic/>
<#import "/sysdic/updateSysDic.ftl" as  updateSysDic/>
<#import "/sysdic/detailSysDic.ftl" as  detailSysDic/>
<#include "/management/resource.ftl"/>

<@sysdic.sysdicHtml/>
<@addSysDic.addSysDicHtml/>
<@detailSysDic.detailSysDicHtml/>
<@updateSysDic.updateSysDicHtml/>

<#macro sysdicHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统字典表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysDicForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">ID：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="id" name="id">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">一级字典码：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="l1Code" name="l1Code">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">一级字典描述：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="l1Desc" name="l1Desc">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">二级字典码：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="l2Code" name="l2Code">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">二级字典描述：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="l2Desc" name="l2Desc">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">码表参数：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="codeParam" name="codeParam">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">实际值：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="codeValue" name="codeValue">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">排序：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="codeIdx" name="codeIdx">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">存活时间(秒)：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="liveCount" name="liveCount">
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
                        <div class="col-md-3">
                            <div class="searchDiv">
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysDic('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysDic('','download','download.xls','l1Code:一级字典码,l1Desc:一级字典描述,l2Code:二级字典码,l2Desc:二级字典描述,codeParam:码表参数,codeValue:实际值,codeIdx:排序,liveCount:存活时间(秒),createTime:添加时间,upTime:更新时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysDic">添加</button></div>
                <table id="sysDicTbl" class="table table-bordered">
                    <tr id="sysDicTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>一级字典码</th>
                        <th>一级字典描述</th>
                        <th>二级字典码</th>
                        <th>二级字典描述</th>
                        <th>码表参数</th>
                        <th>实际值</th>
                        <th>排序</th>
                        <th>存活时间(秒)</th>
                        <th>添加时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysDic_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysDicpageSize">
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
    <input type="hidden" id="querySysDiccurrentPage" />
    <input type="hidden" id="querySysDictotalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/sysdic/sysdic.js"></script>
</#macro>
<style>
    #sysDicTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

