<#import "parts/common.ftl" as c>

<@c.page>

    <h5>Отчеты</h5>
    <div class="container-fluid mb-3">

        <div class="card">
            <div class="card-header text-center">
                Настройки
            </div>
            <div class="card-body">
                <a class="btn btn-primary" data-toggle="collapse" href="#settings" role="button"
                   aria-expanded="false" aria-controls="settings">↓</a>
                <div class="collapse multi-collapse" id="settings">
                    <div class="card card-body mt-3" style="background-color: azure">
                        <div class="container">
                            <form id="settingsForm">
                                <div class="row">
                                    <div class="col">
                                        <ul id="azs">
                                        </ul>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <label for="start" class="col-form-label">От</label>
                                        </div>
                                        <div>
                                            <input type="datetime-local" class="form-control" id="start"
                                                   name="start">
                                        </div>

                                        <div>
                                            <label for="end" class="col-form-label">До</label>
                                        </div>
                                        <div>
                                            <input type="datetime-local" class="form-control" id="end"
                                                   name="end">
                                        </div>


                                        <div>
                                            <label for="payType" class="col-form-label">Оплата</label>
                                        </div>
                                        <div>
                                            <select class="custom-select" id="payType" name="payType">
                                                <option selected value="100">Все</option>
                                                <option value="0">НАЛ</option>
                                                <option value="1">БЕЗНАЛ</option>
                                            </select>
                                        </div>

                                        <div>
                                            <label for="operation" class="col-form-label">Операция</label>
                                        </div>
                                        <div>
                                            <select class="custom-select" id="operation" name="operation">
                                                <option selected value="100">Все</option>
                                                <option value="0">НАКОПЛЕНИЕ</option>
                                                <option value="1">СПИСАНИЕ</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="card-footer text-center">
                        <button class="btn btn-outline-success" onclick="ctx.updateTable()">V</button>
                        <button class="btn btn-outline-danger">X</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div>
        <table id="repTransTable" class="table mt-3">
            <thead class="thead-light text-center">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Создано</th>
                <th scope="col">Вид ГСМ</th>
                <th scope="col">Цена</th>
                <th scope="col">Объем</th>
                <th scope="col">Итог</th>
                <th scope="col">Начислено<br>Списано</th>
                <th scope="col">Карта</th>
                <th scope="col">Тип<br>оплаты</th>
                <th scope="col">Операция</th>
                <th scope="col">АЗС</th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th colspan="6" style="text-align:center">Итого бонусов:</th>
                <th id="total"></th>
            </tr>
            </tfoot>
        </table>
    </div>
    <br>

    <script type="text/javascript" defer>
        var restTransactURL = "rest/transact/";
        var restAzsList = "rest/azs/"

        var total = 0;

        function dataTimeFormat(data) {
            let date = Date.parse(data);
            date = moment(date).format('DD.MM.YYYY HH:mm:ss')
            return date;
        }

        $(function () {
            makeEditable({
                "columns": [
                    {
                        "data": "id",
                        "visible": false
                    },
                    {
                        "data": "createDateTime",
                        "render": dataTimeFormat,
                        "orderable": false
                    },
                    {
                        "data": "fuel",
                        "render": renderFuel,
                        "defaultContent": "",
                        "orderable": true
                    },
                    {
                        "data": "price",
                        "render": renderDigits
                    },
                    {
                        "data": "volume",
                        "render": renderDigits
                    },
                    {
                        "data": "total",
                        "render": renderDigits
                    },
                    {
                        "data": "bonus",
                        "render": renderDigits
                    },
                    {
                        "data": "cardNumber"
                    },
                    {
                        "data": "nal",
                        "render": renderNal,
                        "defaultContent": "",
                        "orderable": true
                    },
                    {
                        "data": "accumulate",
                        "render": renderOperation,
                        "defaultContent": "",
                        "orderable": true
                    },
                    {
                        "data": "creator.azsName"
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            });
        });

        var ctx = {
            ajaxUrl: restTransactURL,
            updateTable: function () {
                $.ajax({
                    type: "GET",
                    url: restTransactURL + "settings",
                    data: $("#settingsForm").serialize()
                }).done(updateTableByData)
            }

        };

        function makeEditable(datatableOpts) {
            ctx.datatableApi = $("#repTransTable").DataTable(
                // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
                $.extend(true, datatableOpts,
                    {
                        "ajax": {
                            "url": ctx.ajaxUrl,
                            "dataSrc": ""
                        },
                        "paging": true,
                        "info": true,
                        "initComplete": function () {
                            total = ctx.datatableApi.column(6).data().reduce(function (a, b) {
                                return a + b;
                            }, 0)
                            $("#total").text(total.toFixed(2));
                        }
                    }
                ));
        }


        function updateTableByData(data) {
            ctx.datatableApi.clear().rows.add(data).draw();
            total = ctx.datatableApi.column(6).data().reduce(function (a, b) {
                return a + b;
            }, 0)
            $("#total").text(total.toFixed(2));
        }


        function renderNal(data, type, row) {

            return data ? "НАЛ" : "БЕЗНАЛ";
        }

        function renderOperation(data, type, row) {

            return data ? "НАКОП" : "СПИС";
        }

        function renderDigits(data, type, row) {
            return data.toFixed(2);
        }

        function renderFuel(data, type, row) {
            switch (data) {
                case "AI_92":
                    return "АИ-92";
                case "DIESEL":
                    return "ДТ";
                case "SUG":
                    return "СУГ";
                case "AI_95":
                    return "АИ-95";
            }
        }

        $.ajax({
            url: restAzsList,
            type: "GET"
        }).done(function (data) {
            $("#azs").text("");
            $.each(data, function (key, value) {
                var azsName;
                var azsId;
                $.each(value, function (k, v) {
                    if (k === "azs_id") {
                        azsId = v;
                    }
                    if (k === "azsName") {
                        azsName = v
                    }
                });
                if (typeof azsName !== "undefined" && typeof azsId !== "undefined") {
                    $("<div><row><input class='form-check-input' type='checkbox' name='azs' value='" + azsId + "'><label>" + azsName + " </label></row></div>").appendTo("#azs");
                }
            });
        });

    </script>
</@c.page>