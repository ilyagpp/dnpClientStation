<#import "fuel.ftl" as fuel>
<#macro editformtransaction>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Накопление бонусов</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/transaction/accumulate" method="post">
                        <div class="form-group">
                            <label for="clientCard" class="col-form-label">Номер карты</label>
                            <#if editTransaction??>
                                <input class="form-control" type="text" name="clientCard" value="${editTransaction.getCardNumber()}" placeholder="${editTransaction.getCardNumber()} "
                                       readonly>
                            </#if>
                        </div>
                        <div id="nal" class="form-group">
                            <label for="nal">Тип оплаты</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="nal" id="nal" value="true">
                                <label class="form-check-label" for="nal">
                                    Наличными
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="nal" id="nal" value="false" checked>
                                <label class="form-check-label" for="nal">
                                    Безналичными
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fuel">Вид ГСМ</label>
                            <select id="fuel" class="form-control" name="fuel">

                                <#list priceList as price>
                                    <#if price.price gt 0>
                                        <option >
                                            <@fuel.fuelnames fuel= price.fuel></@fuel.fuelnames>
                                            -> ${price.price}
                                        </option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <div id="vol" class="form-group">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="exampleRadios" id="total" value="t" checked>
                                <label class="form-check-label" for="total">
                                    Деньги
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="exampleRadios" id="volume" value="v">
                                <label class="form-check-label" for="volume">
                                    Литры
                                </label>
                            </div>
                            <input type="text" class="form-control" id="input" name="input" oninput="changeHandler(this)" >
                        </div>
                        <div class="form-group">
                            <h5 style="color: red">"Заполнять очень внимательно!"</h5>
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="id" value="${editTransaction.getId()?c}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                            <button type="submit" class="btn btn-primary">Применить</button>
                        </div>
                    </form>
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
                    <form action="/transaction/use" method="post">
                        <div class="form-group">
                            <label for="clientCard" class="col-form-label">Номер карты</label>
                            <#if editTransaction??>
                                <input class="form-control" type="text" name="clientCard" value="${editTransaction.getCardNumber()}" placeholder="${editTransaction.getCardNumber()} "
                                       readonly>
                            <#else ><input type="text" class="form-control" name="clientCard" oninput="changeHandler(this)" >
                            </#if>
                        </div>
                        <div class="form-group">
                            <label for="cardBonus" class="col-form-label">Бонусов для списания</label>

                                <input class="form-control" type="text" name="cardBonus" placeholder="<#if bonus??> ${bonus}<#else>0</#if>"
                                       readonly>
                        </div>
                        <div id="nal" class="form-group">
                            <label for="nal">Тип оплаты</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="nal" id="nal" value="true">
                                <label class="form-check-label" for="nal">
                                    Наличными
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="nal" id="nal" value="false" checked>
                                <label class="form-check-label" for="nal">
                                    Безналичными
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fuel">Вид ГСМ</label>
                            <select id="fuel" class="form-control" name="fuel">

                                <#list priceList as price>
                                    <#if price.price gt 0>
                                        <option >
                                            <@fuel.fuelnames fuel= price.fuel></@fuel.fuelnames> -> ${price.price}
                                        </option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <div id="vol" class="form-group">
                            <label for="bonus" class="col-form-label">Списать</label>
                            <input type="text" class="form-control" name="bonus" oninput="changeHandler(this)" >
                        </div>
                        <div class="form-group">
                            <h5 style="color: red">"Заполнять очень внимательно!"</h5>
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="id" value="${editTransaction.getId()?c}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                            <button type="submit" class="btn btn-primary">Применить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="static/JS/TransactionTable.js"></script>


</#macro>