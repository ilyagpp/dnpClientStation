<#macro formtransaction>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Новая транзакция</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"></span>
                </button>
            </div>
            <div class="modal-body">
                <form action="/transactions" method="post">
                    <div class="form-group">
                        <label for="clientCard" class="col-form-label">Номер карты</label>
                        <#if client??>
                        <input class="form-control" type="text" name="clientCard" value="${client.clientCard.cardNumber}" placeholder="<#if client.clientCard??>${client.clientCard.cardNumber!}<#else>Карта не выдана</#if> "
                               readonly>
                            <#else ><input type="text" class="form-control" name="clientCard" oninput="changeHandler(this)" >
                        </#if>
                    </div>

                    <div class="form-group">
                        <label for="fuel" >Вид ГСМ</label>
                        <select id="fuel" class="form-control" name="fuel">
                            <option selected>Аи-92</option>
                            <option>Аи-95</option>
                            <option>Дт</option>
                            <option>СУГ</option>
                        </select>
                    </div>
                    <div id="vol" class="form-group">
                        <label for="volume" class="col-form-label">Объем</label>
                        <input type="text" class="form-control" id="volume" name="volume" oninput="changeHandler(this)" >
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-form-label">Цена за литр</label>
                        <input class="form-control" id="price" name="price" oninput="changeHandler(this)">
                    </div>
                    <div class="form-group">
                        <h5 style="color: red">"Заполнять очень внимательно!"</h5>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                        <button type="submit" class="btn btn-primary">Применить</button>
                    </div>
                </form>
                <script type="text/javascript">
                    const changeHandler = e =>{
                        const value = e.value
                        e.value = value.replace(/[^0-9.]/g , '')
                    }
                </script>
            </div>

        </div>
    </div>
</div>
    <div class="modal fade" id="useBonusModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Списать бонусы</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/transactions" method="post">
                    </form>
                </div>
            </div>
        </div>
    </div>
</#macro>