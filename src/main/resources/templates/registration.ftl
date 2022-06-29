<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <div class="mb-1">
        <h4> Регистрация </h4>
    </div>
    ${message!}
<@l.login "/registration" true/>




</@c.page>