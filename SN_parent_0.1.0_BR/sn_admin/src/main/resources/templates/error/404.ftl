<#import "/management/head.ftl" as  head/>
<#import "/management/menu.ftl" as  menu/>
<#import "/management/footer.ftl" as  footer/>
<#import "/error/404.ftl" as  error404/>
<@head.headHtml/>
<@menu.menuHtml/>
<@error404.error404Html/>
<@footer.footerHtml/>
<#macro error404Html>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>404 Error Page</h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="#">Examples</a></li>
                <li class="active">404 error</li>
            </ol>
        </section>
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow">404</h2>
                <div class="error-content" style="height:110px;padding-top:30px;">
                    <h3>
                        <i class="fa fa-warning text-yellow"></i> Oops! Page not found.
                    </h3>
                    <p>没有找到您访问的页面，您还可以<a href="/login/login.dhtml">返回首页</a>.
                    </p>
                </div>
            </div>
        </section>
    </div>
</#macro>