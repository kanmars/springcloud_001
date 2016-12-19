<#import "/component/register.ftl" as  register/>

<#macro detailDemoInfoHtml>
    <div class="modal fade" id="detailDemoInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="validateDetailDemoInfoForm" method="post" class="form-horizontal">
                    <input type="hidden" id="id_key" name="id_key" value="" />
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">查看DEMO表信息演示</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-lg-3 title">表ID：</label>
                            <div class="col-lg-9">
                                <input id="demoId2" name="demoId2"  class="form-control" placeholder="表ID" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">演示名称：</label>
                            <div class="col-lg-9">
                                <input id="demoNm2" name="demoNm2"  class="form-control" placeholder="演示名称" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">演示金额：</label>
                            <div class="col-lg-9">
                                <input id="demoMoney2" name="demoMoney2"  class="form-control" placeholder="演示金额" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">创建日期：</label>
                            <div class="col-lg-9">
                                <input id="createDate2" name="createDate2"  class="form-control" placeholder="创建日期" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">创建时间：</label>
                            <div class="col-lg-9">
                                <input id="createTime2" name="createTime2"  class="form-control" placeholder="创建时间" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">静态选择框：</label>
                            <div class="col-lg-9">
                                <input id="selectStatic2" name="selectStatic2"  class="form-control" placeholder="静态选择框" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">动态选择框：</label>
                            <div class="col-lg-9">
                                <input id="selectDynamic2" name="selectDynamic2"  class="form-control" placeholder="动态选择框" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">静态单选按钮：</label>
                            <div class="col-lg-9">
                                <input id="radioStatic2" name="radioStatic2"  class="form-control" placeholder="静态单选按钮" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 title">动态单选按钮：</label>
                            <div class="col-lg-9">
                                <input id="radioDynamic2" name="radioDynamic2"  class="form-control" placeholder="动态单选按钮" type="text" disabled="disabled"/>
                            </div>
                        </div>
                        <#--静态模式-start-->
                        <#assign options=["正常","禁用","失效"]/>
                        <@register.checkbox form="validateDetailDemoInfoForm" name="checkboxStatic2" label="静态复选框" options=options value=""
                        class1="form-group" class2="col-lg-3 title" class3="col-lg-9" class4="checkbox-inline" class5="inline1" readOnly="true"/>
                        <#--静态模式- end -->
                        <#--动态模式-start-->
                        <#assign options=dicCheckbox['application_code,application_code']/>
                        <@register.checkbox form="validateDetailDemoInfoForm" name="checkboxDynamic2" label="动态复选框" options=options value=""
                        class1="form-group" class2="col-lg-3 title" class3="col-lg-9" class4="checkbox-inline" class5="inline1" readOnly="true"/>
                        <#--动态模式- end -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#macro>



