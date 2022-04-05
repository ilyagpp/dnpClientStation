<#import "parts/common.ftl" as c>
<#import "parts/formtransaction.ftl" as f>
<#import "parts/TimeRangeForm.ftl" as t>


<@c.page>


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
                    <#if client.clientCard??>
                    <div class="mt-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal"
                                data-whatever="@mdo">
                            Накопить бонусы
                        </button>
                        <button type="button" class="btn btn-primary ml-2" data-toggle="modal"
                                data-target="#useBonusModal"
                                data-whatever="@mdo">Списать бонусы
                        </button>
                    </div>
                        <#else>
                            <div class="alert alert-danger mt-3" role="alert">
                                Операции для клиентов без карты невозможны!
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
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Создано</th>
                <th scope="col">Вид ГСМ</th>
                <th scope="col">Цена</th>
                <th scope="col">Объем</th>
                <th scope="col">Итог</th>
                <th scope="col">Начислено/Списано<br>Бонусов</th>
                <th scope="col">Карта</th>
                <th scope="col">АЗС</th>
            </tr>
            </thead>
            <tbody>
            <#list transactions as transaction>

                <div class="text-center">
                    <tr>
                        <th scope="col">${transaction.id}</th>
                        <th scope="col">
                            ${transaction.createDateTime.toLocalTime().hour}:
                            ${transaction.createDateTime.toLocalTime().minute}:
                            ${transaction.createDateTime.toLocalTime().second}
                            ${transaction.createDateTime.toLocalDate()}</th>
                        <th scope="col">${transaction.fuel}</th>
                        <th scope="col">${transaction.price}</th>
                        <th scope="col">${transaction.volume}</th>
                        <th scope="col">${transaction.total}</th>
                        <th scope="col">${transaction.bonus}</th>
                        <th scope="col">${transaction.clientCard}</th>
                        <th scope="col">${transaction.creator.username}</th>
                    </tr>
                </div>
            </#list>
            </tbody>
        </table>
    </div>


</@c.page>