<#import "/management/head.ftl" as  head/>
<#import "/management/menu.ftl" as  menu/>
<#import "/management/footer.ftl" as  footer/>
<#import "/error/500.ftl" as  error500/>
<@head.headHtml/>
<@menu.menuHtml/>
<@error500.error500Html/>
<@footer.footerHtml/>
<#macro error500Html>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>500 Error Page</h1>
        </section>
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-red">500</h2>
                <div class="error-content" style="height:110px;padding-top:30px;">
                    <h3>
                        <i class="fa fa-warning text-red"></i> Oops! Something went wrong.
                    </h3>
                    <p>没有找到您访问的页面，您还可以<a href="/login/login.dhtml">返回首页</a>.
                    </p>
                </div>
            </div>
        </section>
    </div>
</#macro>