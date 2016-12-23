<#macro wrapper>
<script type="text/javascript">
    //切换标签
    function switchTab(contentId,url){
        $(".tab_content").slideUp();//none
        $("#"+contentId+"").slideDown();//show
    }
</script>
<div style="min-height: 916px;" class="content-wrapper">
    <div class="col-md-12" style="float:none;">
        <div id="tabs" >
            <!-- Nav tabs -->
            <ul  id="tabul" class="nav nav-tabs" role="tablist">
                <li  value="tab_content_home" onclick="switchTab('tab_content_home','/welcome.html')"  class="active" role="presentation" id="tab_home"><a href="javascript:void(0)" aria-controls="tab_home" role="tab" data-toggle="tab">debug专用页面</a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content" id="mainPan">
                <#nested/>
            </div>
        </div>
    </div>
</div>
<style>
    #tabul li, #tabul li .close-tab{padding-top:10px;}
</style>
</#macro>