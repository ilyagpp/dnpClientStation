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
            <#if register??>
            <h5 class="card-title">Успешная регистрация!</h5>
            </#if>
            <div class="alert alert-success" role="alert">
                <#if edit??>Данные клиенты успешно обновлены!<#elseif delete??>Клиент удален!<#elseif errorPin??>${errorPin}<#elseif clientOk??>${clientOk} <#elseif mailSend??>${mailSend}<#else>Новый клиент успешно добавлен!</#if>
            </div>

            <div>
                <#if register??>
                <p>
                    На вашу электронную почту был отправлен пин-код,
                    этот код необходим для списания накопленных бонусов,
                    пожалуйста запомните его.
                    Если забыли, не страшно, мы напомним, нажмите на кнопку
                    "Напомнить PIN"
                    и мы повторно отправим его на ваш email.
                </p>
                </#if>
            </div>
            <div>
                <a class="small btn btn-primary" href="/mobile" role="link">Бонусы</a>
                <a class="small btn btn-secondary" href="/mobile/remind" role="link">Напомнить PIN</a>
            </div>
            <form method="get">

            </form>
        </div>
    </div>
</div>
</body>
</html>