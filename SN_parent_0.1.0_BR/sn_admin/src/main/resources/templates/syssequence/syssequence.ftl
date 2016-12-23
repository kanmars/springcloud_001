<#import "/component/register.ftl" as  register/>
<#import "/syssequence/syssequence.ftl" as  syssequence/>
<#import "/syssequence/addSysSequence.ftl" as  addSysSequence/>
<#import "/syssequence/updateSysSequence.ftl" as  updateSysSequence/>
<#import "/syssequence/detailSysSequence.ftl" as  detailSysSequence/>
<#include "/management/resource.ftl"/>

<@syssequence.syssequenceHtml/>
<@addSysSequence.addSysSequenceHtml/>
<@detailSysSequence.detailSysSequenceHtml/>
<@updateSysSequence.updateSysSequenceHtml/>

<#macro syssequenceHtml>
<div style="" class="">
    <section class="content-header">
        <h1>
                系统ID表<small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="box">
            <div class="box-header">
                <form id=querySysSequenceForm class="form-horizontal form-inline">
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">id：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="id" name="id">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">序列Key：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="keyValue" name="keyValue">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">序列描述：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="seqDesc" name="seqDesc">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">前缀：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="top" name="top">
                            </div>
                        </div>
                    </div>
                    <div class="row dis-top">
                        <div class="col-md-3">
                            <label class="queryTitle">后缀：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="suffix" name="suffix">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">当前值：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="currValue" name="currValue">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="queryTitle">递增数量：</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="" id="batchSize" name="batchSize">
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
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysSequence('')">搜索</button>
                                <button type="button" class="btn btn-info searchBtn" onclick="searchSysSequence('','download','download.xls','keyValue:序列Key,seqDesc:序列描述,top:前缀,suffix:后缀,currValue:当前值,batchSize:递增数量,createTime:添加时间,upTime:更新时间')">下载</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="box-body">
                <div style="width:50px;margin:10px 0px;"><button class="btn btn-block btn-success" data-toggle="modal" data-target="#addSysSequence">添加</button></div>
                <table id="sysSequenceTbl" class="table table-bordered">
                    <tr id="sysSequenceTR_FIRST">
                        <th></th>
                        <th>序号</th>
                        <th>序列Key</th>
                        <th>序列描述</th>
                        <th>前缀</th>
                        <th>后缀</th>
                        <th>当前值</th>
                        <th>递增数量</th>
                        <th>添加时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                </table>
                <div id="page1"></div>
            </div>
            <!-- /.box-body -->

            <!--分页 start-->
            <div id="querySysSequence_fy" style="width: 100%;" class="gmPagination gmPagination-mini">
                <div style="float:left;margin: 10px;">
                    <select id="querySysSequencepageSize">
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
    <input type="hidden" id="querySysSequencecurrentPage" />
    <input type="hidden" id="querySysSequencetotalPage" />
</div>
<script type="text/javascript" src="${props('resourceUrl')}/js/syssequence/syssequence.js"></script>
</#macro>
<style>
    #sysSequenceTbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}
    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}
    .redFont{color:red;display:inline-block;width:15px;text-align:center;}
    .modal-dialog{margin:165px auto;}
</style>

