<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<#import "parts/pager.ftl" as pager>
<#import  "parts/sizer.ftl" as sizer>
<@c.page>
    <div class="mt-3">
        <@sizer.sizer url page size/>
    </div>
    <div>
        <div class="form-group">
            <h5>Клиенты:</h5>
        </div>

        <div>
            <table class="table mt-3">
                <thead class="thead-light">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">ФИО</th>
                    <th scope="col">Дата рождения</th>
                    <th scope="col">Телефон</th>
                    <th scope="col">Email</th>
                    <th scope="col">Номер карты</th>
                    <th scope="col">Бонусов на карте</th>
                    <th scope="col">Статус</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <#list page.content as client>

                    <div class="text-center">
                        <tr>
                            <th scope="col">${client.id}</th>
                            <th scope="col">
                                ${client.surname}
                                ${client.name}
                                <#if client.patronymic??>${client.patronymic!}</#if>
                            </th>
                            <th scope="col">
                                ${client.birthday!""}</th>
                            <th scope="col"><#if client.phoneNumber??>${client.phoneNumber}</#if></th>
                            <th scope="col"><#if client.email??> ${client.email}</#if></th>
                            <th scope="col"><#if client.clientCard??>${client.clientCard.cardNumber}</#if></th>
                            <th scope="col"><#if client.clientCard??>${client.clientCard.bonus}</#if></th>
                            <th scope="col">
                                <input type="checkbox"  value <#if client.isActive()>checked</#if>/>
                                 </th>
                            <th scope="col">
                                <a type="button" class="btn btn-outline-success btn-sm" href="client/edit/${client.id}">
                                    <i class="fa-solid fa-pen-to-square fa-lg"></i>
                                </a>
                            </th>
                            <#if isAdmin>
                            <th scope="col">
                                <#assign id = client.getId()>
                                <form action="client/delete/${id}" method="post">
                                    <button type="submit" class="btn btn-outline-danger btn-sm" > <i class="fa-solid fa-trash-can fa-lg"></i> </button>
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
     <@pager.pager url page />
</@c.page>