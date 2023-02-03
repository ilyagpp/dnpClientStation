<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div>
        <div class="form-group">
            <h5>Список АЗС:</h5>
        </div>
        <div>
            <table class="table mt-3">
                <thead class="thead-light">
                <tr>
                    <th scope="col">AZS_ID</th>
                    <th scope="col">Наименование</th>
                    <th scope="col">Информация</th>
                    <th scope="col">Начисление бонусов</th>
                    <th scope="col">Повышенные бонусы</th>
                    <th scope="col">список пользователей</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <#list azslist as azs>

                    <div class="text-center">
                        <tr>
                            <th scope="col">${azs.azs_id}</th>
                            <th scope="col">${azs.azsName}</th>
                            <th scope="col">${azs.properties!""}</th>
                            <th scope="col">${azs.bonus!""}</th>
                            <th scope="col">
                                <input type="checkbox"  value <#if azs.isElevatedBonus()>checked</#if>/>
                            </th>

                            <th scope="col">
                                <#list azs.users as user>
                                ${user.username!"-"}<br></#list></th>

                            <th scope="col">
                                <a type="button" class="btn btn-outline-success btn-sm " href="azs/${azs.azs_id}">
                                    <i class="fa-solid fa-pen-to-square fa-lg"></i>
                                </a>
                            </th>
                            <#if isAdmin>
                                <th scope="col">
                                    <#assign id = azs.azs_id>
                                    <form action="azs/${id}/del" method="post">
                                        <button type="submit" class="btn btn-outline-danger btn-sm "><i class="fa-solid fa-trash-can fa-lg"></i></button>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </th>
                            </#if>
                        </tr>
                    </div>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>