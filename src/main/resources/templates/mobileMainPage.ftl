<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="UTF-8">
    <title>DNPClientStation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shorcut icon " href="/static/favicon.ico"/>
    <!-- Bootstrap CSS -->

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <style>
        html, body {
            height: 100%;
        }
        body, card {
            background-color: azure;
        }
        table {
            font-size: 0.50rem;
        }
    </style>
</head>
<body>
<div class="container-fluid d-flex h-100 justify-content-center align-items-center p-0">
    <div class="card text-center" style="width: 24rem; background-color: azure; border: azure">
        <div class="card-body">
            <h5 class="card-title">Проверить начисления</h5>
            <p class="card-text">Введите номер телефона</p>
            <form method="get">
                <input class="form-control text-center" type="tel" name="phoneNumber" id="phoneNumber"
                       placeholder="(123)4567890" aria-label="phoneNumber" <#if phoneNumber??>value="${phoneNumber}"</#if> oninput="changeHandler(this)">
                <div class="mt-3">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                </div>
            </form>

        <script type="text/javascript">

            function showTransact(phoneNumber) {
                $("#showTransactions").css("display", "none");

                $("#trTable").css(
                    {
                        display: "block",
                        font: "normal"
                    }
                ).DataTable({
                    ajax: {
                        method: "GET",
                        url: "/mobile/transact/" + phoneNumber,
                        dataSrc: ''
                    },
                    columns: [
                        {
                            data: 'createDateTime',
                            render: dataTimeFormat
                        },
                        {
                            data: 'fuel',
                            render: renderFuel
                        },
                        {
                            data: 'volume',
                            render: renderDigits
                        },
                        {
                            data: 'total',
                            render: renderDigits
                        },
                        {
                            data: 'bonus',
                            render: renderDigits
                        },
                        {
                            data: 'accumulate',
                            render: renderOperation
                        },
                        {
                            data: 'creator.azs',
                            render: renderAzsName
                        },

                    ],
                    paging: false,
                    ordering: false,
                    info: false,
                    searching: false,
                    scrollY: 400
                });
            }

            function dataTimeFormat(data) {
                let date = Date.parse(data);
                date = moment(date).format('DD.MM.YYYY HH:mm:ss')
                return date;
            }
        </script>

            <br>
            <#if clientError??>
                <h5 style="color: darkred"> ${clientError} </h5>
            </#if>
            <#if bonus??>
                <div>
                    <h4>Бонусов на счету: ${bonus?string["0.00"]}</h4>
                </div>
                <div id="hist">
                    История транзакций:
                </div>

                <div>
                    <button class="btn btn-outline-success" id="showTransactions" onclick="showTransact(${phoneNumber})">показать</button>
                    <table class="table-hover font-weight-bold" style="display:none" id="trTable">
                        <thead>
                        <tr>
                            <th>Когда</th>
                            <th>ГСМ</th>
                            <th>Сколько</th>
                            <th>Сумма</th>
                            <th>Бонус</th>
                            <th>Что</th>
                            <th>АЗС</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <#else>
                    <br>
                    <p class="card-text">Нет номера в системе? регистрация занимает меньше минуты!</p>
                    <div class="mt-3">
                        <a class="btn btn-outline-danger my-2 my-sm-0" href="mobile/newclient" role="link">Регистрация</a>
                        <a class="btn btn-outline-success my-2 my-sm-0" href="mobile/remind" role="link">Напомнить PIN</a>
                    </div>
            </#if>
        </div>
    </div>
</div>
<script src="static/JS/moment.js"></script>
<script src="static/JS/TransactionTable.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/v/bs4-4.6.0/dt-1.13.4/af-2.5.3/b-2.3.6/datatables.min.js"></script>
<script src="https://cdn.datatables.net/plug-ins/1.13.4/api/sum().js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.min.js"></script>

</body>
</html>


