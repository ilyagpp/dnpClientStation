<#import "parts/common.ftl" as c>
<@c.page>
    <#if message??>
        <div class="alert alert-success" role="alert">
            Успешно!
        </div>
        <div>
            <div>
                <h5>${message}</h5>
            </div>
        </div>
    </#if>
</@c.page>