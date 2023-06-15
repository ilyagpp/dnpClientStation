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
    </style>
</head>
<body>
<div class="container-fluid d-flex h-100 justify-content-center align-items-center p-0">
    <div class="card text-center" style="width: 24rem; background-color: azure; border: azure">
        <div class="card-body">
            <h5 class="card-title">Напомнить Пин-Код</h5>
            <p class="card-text">Введите номер телефона без +7</p>
            <form method="get" action="/mobile/remind">
                <input class="form-control text-center" type="tel" name="phoneNumber" id="phoneNumber"
                       placeholder="(123)4567890" aria-label="phoneNumber" oninput="changeHandler(this)">
                <div class="mt-3">
                    <button id="remind" class="btn btn-success my-2 my-sm-0" type="submit">Отправить</button>
                </div>

            </form>
            <script type="text/javascript">
                $(document).ready(function() {

                    $('#remind').click(function () {
                        $(this).attr('disabled', true);
                        document.forms[0].submit();
                    });
                });
            </script>
            <script src="static/JS/TransactionTable.js"></script>
            <br>
            <#if phoneNumberError??>
                <h5 style="color: darkred"> ${phoneNumberError} </h5>
            </#if>
        </div>
    </div>
</div>
</body>
</html>

