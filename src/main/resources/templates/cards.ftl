<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <h5>Список карт зарегистрированных в системе</h5>
    <h5 style="color: firebrick">${nameList} </h5>
    <div class="form-row">
        <div class="form-inline" >
            <#if isAdmin>
            <form method="post" class="form-inline" >
                <input type="text" name="cardNumber" class="form-control" placeholder="1100000000000">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button class="btn btn-primary mr-2" type="submit">Добавить карту</button>
            </form>
            </#if>
            <form method="get" class="form-inline">
                <input type="number" name="size" class="form-control" placeholder="10">
                <input type="hidden" name="free" value="'1'">
                <button class="btn btn-primary" type="submit">Свободные карты</button>
            </form>
            <form method="post" action="/cards/auto" class="form-inline ml-lg-2">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button class="btn btn-primary mr-2" type="submit">Новая карта</button>
            </form>
        </div>
        <div>
            Поиск карты:
            <form method="get" class="form-inline">
                <input class="form-control mr-sm-2" type="search" name="search" aria-label="Search"
                       <#if search??>value="${search}" <#else> placeholder="******" </#if>>
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                <a class="btn btn-outline-primary ml-2 my-sm-0" href="/cards" role="link">Сброс</a>
            </form>
        </div>
    </div>


    <div>
        <table class="table mt-3">
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Номер Карты</th>
                <th scope="col">На карте Бонусов</th>
                <th scope="col">Выдана клиенту:</th>
                <th scope="col">Выдать клиенту</th>
            </tr>

            </thead>
            <tbody>
            <#list cardsList as card>

                <div class="text-center">
                    <tr>
                        <form method="get" action="/card/${card.id}">
                        <th scope="col">${card.id}</th>
                        <th scope="col">${card.cardNumber}</th>
                        <th scope="col">${card.bonus}</th>
                        <th scope="col"><#if card.client??>${card.client.name} ${card.client.surname}<#else> - </#if></th>
                        <th scope="col">
                            <#if !card.client??>
                                <div>
                                    <a href="/card/${card.id}" class="btn btn-light">Выдать</a>
                                </div>
                            <#else>
                                <div>
                                    <form method="get" action="/card/fr/${card.id}">
                                        <a href="/card/fr/${card.id}" class="btn btn-danger">Изъять</a>
                                    </form>
                                </div>
                            </#if>
                        </th>
                        </form>
                    </tr>
                </div>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>