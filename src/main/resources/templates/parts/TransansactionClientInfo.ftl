<#macro transactionclientinfo>

    <div class="card mt-3">
        <div class="card-header ">
            Поиск информации:
        </div>
        <div class="card-body">
            <h5 class="card-title">Поиск клиента</h5>
            <p class="card-text">Введите номер карты, номер телефона клиента, или его email</p>

            <form method="get" class="form-inline">
                <input class="form-control mr-sm-2" type="search" name="search" id="search"
                       placeholder="номер/email" aria-label="Search">
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

</#macro>