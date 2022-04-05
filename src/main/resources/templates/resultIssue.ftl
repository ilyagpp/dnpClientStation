<#import "parts/common.ftl" as c>

<@c.page>


    <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
            <a class="btn btn-primary" href="/cards" role="link">Вернуться к выбору карты</a>
        </div>
    <#else>
        <div class="alert alert-success" role="alert">
            Успешно!
        </div>
        <div>
            <div>
                <h5>Карта ${card.cardNumber} выдана клиенту: ${client.surname!} ${client.name!} ${client.phoneNumber}</h5>
            </div>
        </div>
    </#if>
</@c.page>