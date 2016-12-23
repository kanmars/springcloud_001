<#import "/management/head.ftl" as  head/>
<#import "/management/menu.ftl" as  menu/>
<#import "/management/content.ftl" as  content/>
<#import "/management/footer.ftl" as  footer/>
<#import "/debug/debugcontent.ftl" as debufcontent/>
<@head.headHtml/>
<@menu.menuHtml/>
<@debufcontent.wrapper>
    <#--
        修改此处引用的页面，可以用于系统调试
    -->
    <#if debugPath?exists>
        <#include debugPath/>
    <#else>
        请在DebugController.java中设置页面，或者在参数中传递debugPage=/sysdic/sysdic.ftl<br>
    例如:http://localhost:20901/debug/debug.dhtml?debugPage=/sysdic/sysdic.ftl
    </#if>


</@debufcontent.wrapper>

<@footer.footerHtml/>