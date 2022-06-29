<#import "parts/common.ftl" as c>

<@c.page>
    <form>
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
                </tr>
                </thead>
                <tbody>
                <#list clients as client>

                    <div class="text-center">
                        <tr>
                            <th scope="col">${client.id}</th>
                            <th scope="col">
                                ${client.surname}
                                ${client.name}
                                <#if client.patronymic??>${client.patronymic!}</#if>
                            </th>
                            <th scope="col">
                                ${client.birthday}</th>
                            <th scope="col"><#if client.phoneNumber??>${client.phoneNumber}</#if></th>
                            <th scope="col"><#if client.email??> ${client.email}</#if></th>
                            <th scope="col"><#if client.clientCard??>${client.clientCard.cardNumber}</#if></th>
                            <th scope="col"><#if client.clientCard??>${client.clientCard.bonus}</#if></th>
                            <th scope="col">
                                <input type="checkbox"  value <#if client.isActive()>checked</#if>/>
                                 </th>
                        </tr>
                    </div>
                </#list>
                </tbody>
            </table>
        </div>
    </form>
</@c.page>