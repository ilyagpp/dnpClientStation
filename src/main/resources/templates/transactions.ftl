<#import "parts/common.ftl" as c>
<#import "parts/modalFormTransactions.ftl" as f>
<#import "parts/TimeRangeForm.ftl" as t>
<#import "parts/fuel.ftl" as fuel>
<#import "parts/pager.ftl" as pager>
<#import  "parts/sizer.ftl" as sizer>

<@c.page>
    <div class="mt-3">
    <@sizer.sizer url page/>
    </div>
    <div class="card mt-3">
        <div class="card-header ">
            Поиск информации:
        </div>
        <div class="card-body">
            <h5 class="card-title">Поиск клиента</h5>
            <p class="card-text">Введите номер карты, номер телефона клиента, или его email</p>

            <form method="get" class="form-inline ">
                <input class="form-control mr-sm-2" type="search" name="search" id="search"
                       placeholder="номер/email/имя" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
            </form>
        </div>
        <#if client??>
            <form class="form-inline">
                <div class="card-body">
                <h5 class="card-title">Клиент найден:</h5>
                <div class="form-group row">
                    <label for="clientName" class="col-sm-2 col-form-label">ФИО</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text"
                               placeholder="${client.surname!} ${client.name!} ${client.patronymic}" readonly>
                    </div>
                    <label for="phoneNumber" class="col-sm-2 col-form-label">Телефон</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" placeholder="${client.phoneNumber}" readonly>
                    </div>
                    <label for="email" class="col-sm-2 col-form-label">Email</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" placeholder="${client.email} " readonly>
                    </div>
                    <label for="cardNumber" class="col-sm-2 col-form-label">Карта</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" placeholder="<#if client.clientCard??>${client.clientCard.cardNumber!}<#else>Карта не выдана</#if> "
                               readonly>
                    </div>
                    <input type="hidden" name="clientId" value="${client.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </div>
                    <#if client.clientCard?? && client.isActive()>
                        <div class="mt-3">
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#exampleModal"
                                    data-whatever="@mdo">
                                Накопить бонусы
                            </button>
                            <button type="button" class="btn btn-primary ml-2" data-toggle="modal"
                                    data-target="#useBonusModal"
                                    data-whatever="@mdo">Списать бонусы
                            </button>
                        </div>
                    <#elseif !client.clientCard??>
                        <div class="alert alert-danger mt-3" role="alert">
                            Операции для клиентов без карты невозможны!
                        </div>
                        <#elseif !client.isActive()>
                        <div class="alert alert-danger mt-3" role="alert">
                            Клиент деактивирован! Операция не возможна!
                        </div>
                    </#if>
                </div>

            </form>
        </#if>
    </div>


    <#if clientError??>
        <div class="alert alert-danger mt-3" role="alert">
            ${clientError}
        </div>
    </#if>

    <@f.formtransaction>
    </@f.formtransaction>


    <@t.TimeRangeForm>
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
                <th scope="col">АЗС</th>
            </tr>
            </thead>
            <tbody>
            <#list page.content as transaction>

                <div class="text-center">
                    <tr class="text-center" size="10">
                        <th scope="col">
                            ${transaction.createDateTime.toLocalTime().hour}:
                            ${transaction.createDateTime.toLocalTime().minute}:
                            ${transaction.createDateTime.toLocalTime().second}  -
                            ${transaction.createDateTime.toLocalDate()}</th>
                        <th scope="col">
                        <@fuel.fuelnames fuel= transaction.fuel></@fuel.fuelnames>
                        </th>
                        <th scope="col"> ${transaction.price?string["0.00"]}</th>
                        <th scope="col">${transaction.volume?string["0.00"]}</th>
                        <th scope="col">${transaction.total?string["0.00"]}</th>
                        <th scope="col">${transaction.bonus?string["0.00"]}</th>
                        <th scope="col">${transaction.clientCard}</th>
                        <th scope="col">${transaction.creator.username}</th>
                    </tr>
                </div>
            </#list>
            </tbody>
        </table>
    </div>
    <@pager.pager url page />
</@c.page>