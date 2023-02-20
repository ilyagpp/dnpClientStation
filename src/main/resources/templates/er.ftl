<#import "parts/common.ftl" as c>
<@c.page>



    <div class="alert alert-danger" role="alert">
        <h4>Произошла ошибка!</h4>
        <h4></h4>
    </div>
    <#if error??>
        <div class="alert alert-danger mt-3" role="alert">
            <h4>${error}</h4>
        </div>
    </#if>
    <div>
        <a class="btn btn-outline-success mr-2" href="/main-operator" role="link">На главную</a>
        <button class="btn btn-outline-primary" onclick="window.history.back()">Вернуться</button>
    </div>
    <#if clrError??>
        <div class="alert alert-danger mt-3" role="alert">
            <h4>${clrError}</h4>
        </div>
    </#if>


</@c.page>