<#import "parts/common.ftl" as c>
<#import "parts/FormEditTransacton.ftl" as editTransaction>
<#import "parts/TimeRangeForm.ftl" as t>
<#import "parts/fuel.ftl" as fuel>
<#import "parts/pager.ftl" as pager>
<#import  "parts/sizer.ftl" as sizer>
<#include "parts/security.ftl">

<@c.page>
    <div class="mt-3">
        <@sizer.sizer url page size/>
    </div>
    <#if transactionError??>
        <div class="alert alert-danger mt-3" role="alert">
            ${transactionError}
        </div>
    </#if>

    <#if clientError??>
        <div class="alert alert-danger mt-3" role="alert">
            ${clientError}
        </div>
    </#if>


    <@t.TimeRangeForm payType operationType>
    </@t.TimeRangeForm>

    <div>
        <table class="table mt-3">
            <thead class="thead-light text-center">
            <tr>
                <th scope="col">Создано</th>
                <th scope="col">Вид ГСМ</th>
                <th scope="col">Цена</th>
                <th scope="col">Объем</th>
                <th scope="col">Итог</th>
                <th scope="col">Начислено/Списано</th>
                <th scope="col">Карта</th>
                <th scope="col">Тип оплаты</th>
                <th scope="col">Операция</th>
                <th scope="col">АЗС</th>
                <th scope="col"></th>
                <#if isAdmin><th scope="col"></th></#if>
            </tr>
            </thead>
            <tbody>
            <#list page.content as transaction>
                    <tr class="text-center">
                        <th scope="col">
                            ${transaction.createDateTime.toLocalTime().hour?string["00"]}:
                            ${transaction.createDateTime.toLocalTime().minute?string["00"]}:
                            ${transaction.createDateTime.toLocalTime().second?string["00"]} &nbsp
                            ${transaction.createDateTime.toLocalDate()?string?date['yyyy-MM-dd']}</th>
                        <th scope="col">
                            <@fuel.fuelnames fuel= transaction.fuel></@fuel.fuelnames>
                        </th>
                        <th scope="col"> ${transaction.price?string["0.00"]}</th>
                        <th scope="col">${transaction.volume?string["0.00"]}</th>
                        <th scope="col">${transaction.total?string["0.00"]}</th>
                        <th scope="col">${transaction.bonus?string["0.00"]}</th>
                        <th scope="col">${transaction.cardNumber}</th>
                        <th scope="col"><#if transaction.isNal()>НАЛ<#else>БЕЗНАЛ</#if></th>
                        <th scope="col"><#if transaction.isAccumulate()>Накоп.<#else>Списание</#if></th>
                        <th scope="col"><#if transaction.creator.getAzsName()??>${transaction.creator.getAzsName()!""}<#else>${transaction.creator.username!""}</#if> </th>
                        <#if transaction.creator.id == id || isAdmin>
                            <th scope="col">
                                <a type="button" class="btn btn-outline-success btn-sm" href="transactions/edit/${transaction.id}">
                                    <i class="fa-solid fa-pen-to-square fa-lg"></i>
                                </a>
                            </th>
                        </#if>
                        <#if isAdmin><th scope="col">
                                <#assign id = transaction.getId()>

                                <form action="transactions/delete/${id}" method="post">
                                    <button type="submit" class="btn btn-outline-danger btn-sm" href="transactions/delete/${transaction.id}">
                                        <i class="fa-solid fa-trash-can fa-lg"></i>
                                    </button>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                </form>

                            </th>
                        </#if>
                    </tr>

            </#list>
            </tbody>
        </table>
    </div>
    <@pager.pager url page />
</@c.page>