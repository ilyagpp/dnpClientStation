<#import "parts/common.ftl" as c>

<@c.page>
    <div class="alert alert-success" role="alert">
       <#if edit??>Данные клиенты успешно обновлены!<#elseif delete??>Клиент удален!<#elseif errorPin??>${errorPin}<#elseif clientOk??>${clientOk} <#elseif mailSend??>${mailSend}<#else>Новый клиент успешно добавлен!</#if>
        <div>
            <a class="btn btn-primary" href="/main-operator" role="link">Домой</a>
        </div>
    </div>
</@c.page>