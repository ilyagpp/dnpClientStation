<#import "parts/common.ftl" as c>
<#import "parts/TransansactionClientInfo.ftl" as transclntinfo>
<#import "parts/modalFormTransactions.ftl" as modaltransaction>

<@c.page>

    <div class="col-8">
        <div class="card mt-3">
            <div class="card-header ">
                Поиск информации:
            </div>
            <div class="card-body">
                <h5 class="card-title">Поиск клиента</h5>
                <p class="card-text">Введите номер карты, номер телефона клиента, или его email</p>

                <form method="get" class="form-inline">
                    <input class="form-control mr-sm-2" type="search" name="search" id="search"
                           placeholder="номер/email/имя" aria-label="Search" <#if search??> value="${search}" </#if>>
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
                            <label for="bonus" class="col-sm-2 col-form-label">Бонусов</label>
                            <div class="col-sm-10">
                                <input class="form-control" type="text" placeholder="<#if client.clientCard??>${client.clientCard.bonus!}<#else>Карта отсутствует</#if>" readonly>
                            </div>
                            <#if search??>
                            <input type="hidden" name="search" value="${search}">
                            </#if>
                            <#if client??>
                                <form>
                                <label for="pin" class="col-sm-2 col-form-label">Пин-Код</label>
                            <div class="col-sm-6">
                                <#if !pin??>
                                   <input
                                        class="form-check-input" type="checkbox" name="showPin" id="defaultCheck1"
                                        value="true" <#if showPin??>checked</#if>>
                                    <button class="btn btn-primary" type="submit">Показать</button>
                                    <button class="btn btn-primary" name="recallPin" value="true">Напомнить</button>
                                <#else>
                                    <input class="form-control" type="text" maxlength="4" id="pin" name="pin"
                                           placeholder="${pin}" readonly>
                                </#if>
                            </div>
                                </form>
                            </#if>
                        </div>

                    </div>

                </form>
                <#if showPin??>
                    <form method="post" action="/client/pin/${client.id}">
                        <form class="form-inline">
                            <div class="card-body">
                                <div class="form-group row">
                                    <label for="pin" class="col-sm-2 col-form-label">Новый Пин</label>
                                    <div class="col-sm-2 row ml-1">
                                        <input class="form-control" type="text" maxlength="4" id="pin" name="pin"
                                               placeholder="****" oninput="changeHandler(this)">
                                        <input type="hidden" name="id" value="${client.id}">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">

                                    </div>
                                    <button class="btn btn-primary ml-2" type="submit">Изменить</button>
                                </div>
                            </div>
                        </form>
                    </form>
                </#if>

            </#if>
        </div>
        <#if clientError??>
            <div class="alert alert-danger mt-3" role="alert">
                ${clientError}
            </div>
        </#if>
    </div>
    <script src="static/JS/TransactionTable.js"></script>

</@c.page>