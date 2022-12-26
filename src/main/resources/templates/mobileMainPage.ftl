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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
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
    </style>
</head>
<body>
<div class="container-fluid d-flex h-100 justify-content-center align-items-center p-0">
    <div class="card text-center" style="width: 24rem; background-color: azure; border: azure">
        <div class="card-body">
            <h5 class="card-title">Проверить начисления</h5>
            <p class="card-text">Введите номер телефона</p>
            <form method="get">
                <input class="form-control text-center" type="search" name="phoneNumber" id="phoneNumber"
                       placeholder="(123)4567890" aria-label="phoneNumber" oninput="changeHandler(this)">
                <div class="mt-3">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                </div>
            </form>

        <script type="text/javascript">
            const changeHandler = e =>{
                const value = e.value
                e.value = value.replace(/[^0-9]/g , '')
            }
        </script>
            <br>
            <#if clientError??>
                <h5 style="color: darkred"> ${clientError} </h5>
            </#if>
            <#if bonus??>
                <div>
                    <h4>Бонусов на счету: ${bonus}</h4>
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
</body>
</html>


