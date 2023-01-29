<#import "parts/common.ftl" as c>

<@c.page>
User editor

    <form action="/user" method="post">

        <div>
            <label>
                Имя(username)
            </label>
            <input type="text" name="username" value="${user.username}">
        </div>
        <div>
            <label>
                Точка обслуживания
            </label>
            <input type="text" name="azsName" value="${user.azsName!}">
        </div>
        <#list roles as role>
            <div>
               <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
            </div>
        </#list>

        <#if user.azs??>
        <div>
                <label for="azsName">
                    Привязана к
                </label>

                    <input id="azsName" type="text" readonly value="${user.azs.azsName!}">
                    <input type="hidden" name="azsId" value="${user.azs.azs_id}">
                    <input type="checkbox" name="delAzs">
        </div>
        </#if>

        <#if error??>
            <div class="alert alert-danger mt-3" role="alert">
                <h4>${error}</h4>
            </div>
            <div>
                <#if referer??><a class="btn btn-outline-danger" href="${referer}" role="link">Назад</a></#if>
            </div>
        </#if>

        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>

</@c.page>