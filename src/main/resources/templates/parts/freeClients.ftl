<#macro freeclientlist>
    <div>
        <table class="table mt-3">
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">ФИО</th>
                <th scope="col">Email</th>
                <th scope="col">Номер телефона</th>
                <th scope="col"></th>
            </tr>

            </thead>
            <tbody>

                <#list clients as client>

                    <div class="text-center">
                        <tr>

                            <th scope="col">${client.id}</th>
                            <th scope="col">${client.surname} ${client.name}
                            </th>
                            <th scope="col">${client.email}</th>
                            <th scope="col">${client.phoneNumber}</th>
                            <th scope="col">
                                <form method="post">
                                <div>
                                    <input type="hidden" name="clientId" value="${client.id}">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    <button class="btn btn-primary mt-2" type="submit">Выдать</button>
                                </div>
                                </form>
                            </th>

                        </tr>
                    </div>
                </#list>

            </tbody>
        </table>
    </div>
</#macro>