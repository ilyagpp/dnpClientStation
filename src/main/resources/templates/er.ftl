<#import "parts/common.ftl" as c>
<@c.page>



            <div class="alert alert-danger" role="alert">
                <h4>Произошла ошибка!</h4>
            </div>
            <div><a class="btn btn-outline-success" href="/main-operator" role="link">На главную</a></div>
            <#if clrError??>
                <div class="alert alert-danger mt-3" role="alert">
                    <h4>${clrError}</h4>
                </div>
            </#if>


</@c.page>