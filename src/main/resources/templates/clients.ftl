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
            Поиск по имени, фамилии, номеру
            <form method="get" class="form-inline">
                <input class="form-control mr-sm-2" type="search" name="search" aria-label="Search"
                <#if search??>value="${search}" <#else> placeholder="имя, фамилия, номер" </#if>>
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                <a class="btn btn-outline-primary ml-2 my-sm-0" href="${referer!""}" role="link">Сброс</a>
            </form>
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
                                <#if client.birthday??>${client.birthday.toString()?date['yyyy-MM-dd']}</#if></th>
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