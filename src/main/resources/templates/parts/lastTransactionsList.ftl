<#import "fuel.ftl" as fuel>
<#macro lastTransactionsList lastTransactionsList>
    <div class="card" xmlns="http://www.w3.org/1999/html">
        <div class="card-header">
           <h5>Последние транзакции:</h5>
        </div>
        <div class="card-body">
            <#if lastTransactionsList??>

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
                    </tr>
                    </thead>
                    <tbody>

                    <#list lastTransactionsList as transaction>
                        <tr class="text-center">
                            <th scope="col">
                                ${transaction.createDateTime.toLocalTime().hour?string["00"]}:
                                ${transaction.createDateTime.toLocalTime().minute?string["00"]}:
                                ${transaction.createDateTime.toLocalTime().second?string["00"]} &nbsp
                                ${transaction.createDateTime.toLocalDate()?string?date['yyyy-MM-dd']}
                            </th>
                            <th scope="col">
                                <@fuel.fuelnames fuel= transaction.fuel></@fuel.fuelnames>
                            </th>
                            <th scope="col"> ${transaction.price?string["0.00"]}</th>
                            <th scope="col">${transaction.volume?string["0.00"]}</th>
                            <th scope="col">${transaction.total?string["0.00"]}</th>
                            <th scope="col">${transaction.bonus?string["0.00"]}</th>
                            <th scope="col">${transaction.cardNumber}</th>
                            <th scope="col"><#if transaction.isNal()>НАЛ<#else>БЕЗНАЛ</#if></th>
                        </tr>
                    </#list>
                    </tbody>

                </table>

            <#else>
                <h5>Транзакции отсутствуют</h5>
            </#if>
        </div>
    </div>
</#macro>