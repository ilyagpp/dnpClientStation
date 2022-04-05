<#import "parts/common.ftl" as c>
    <#import "parts/clientform.ftl" as cltf>
    <#import "parts/freeClients.ftl" as fcl>
<@c.page>

    <form method="post" action="/card/${card.id}">
        <#if cardError??>
            <div class="alert alert-danger" role="alert">
                ${cardError}
                <a class="btn btn-primary" href="/cards" role="link">Вернуться к выбору карты</a>
            </div>
        <#else>
        <#if card??>
            <div class="form-group row">
                <label for="cardNumber" class="col-sm-2 col-form-label">Номер карты</label>
                <div class="col-sm-10">
                    <input class="form-control" type="text" placeholder="${card.cardNumber}" readonly>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" value="${card.id}" name="cardId">
            </div>

        </#if>
        </#if>
    <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
        <div>
            <@fcl.freeclientlist>
            </@fcl.freeclientlist>
        </div>
    </#if>
    <#if client??>
        <h5>Выдать карту клиенту: ${client.surname!} ${client.name!}</h5>
        <div class="form-group row">
        <label for="phoneNumber" class="col-sm-2 col-form-label">Телефон</label>
        <div class="col-sm-10">
            <input class="form-control" type="text" placeholder="${client.phoneNumber}" readonly>
        </div>
        </div>
        <div class="form-group row">
            <label for="Email" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" placeholder="${client.email}" readonly>
            </div>
        </div>
        <#if btnActivate??>
            <input hidden name="clientId" value="${client.id}">
            <button class="btn btn-primary mt-2" type="submit">Выдать</button>
        </#if>
        <#if danger??>
            <div class="alert alert-danger" role="alert">
                ${danger}
            </div>
            <a class="btn btn-primary" href="/card/${card.id}" role="link">Назад</a>
        </#if>

    <#else>
    </form>
    <form method="get" class="form-inline mt-3">
        <input class="form-control mr-sm-2" type="search" name="search" id="search" placeholder="номер/email/имя" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
    </form>
    </#if>

</@c.page>