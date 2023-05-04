<#import "modalEditTransaction.ftl" as modal>
<#macro  editformtransaction editTransaction>

    <div class="card mt-3">
        <div class="card-header ">
            Форма редактирования транзакции:
        </div>
        <div class="card-body">
            <h5 class="card-title">Редактируем:</h5>
        </div>
        <#if editTransaction??>
            <form class="form-inline">
                <div class="card-body">
                    <h5 class="card-title">Транзакция:${editTransaction.id?c}</h5>
                    <div class="form-group row">
                        <label for="clientCard" class="col-sm-2 col-form-label">Карта клиента</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text"
                                   placeholder="${editTransaction.getCardNumber()}" readonly>
                        </div>
                        <label for="fuel" class="col-sm-2 col-form-label">Топливо</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text" placeholder="${editTransaction.getFuel()} -> ${editTransaction.getPrice()}" readonly>
                        </div>
                        <label for="vol" class="col-sm-2 col-form-label">Объем</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text" placeholder="${editTransaction.getVolume()} " readonly>
                        </div>
                        <label for="total" class="col-sm-2 col-form-label">Итог</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text" placeholder="${editTransaction.getTotal()} " readonly>
                        </div>
                        <label for="bonus" class="col-sm-2 col-form-label">Бонусов</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text" placeholder="${editTransaction.getBonus()} " readonly>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                    </div>

                    <div class="mt-3">
                        <#if editTransaction.getBonus() gt 0>
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#exampleModal"
                                    data-whatever="@mdo">
                                Накопить бонусы
                            </button>
                        <#else>
                            <button type="button" class="btn btn-primary ml-2" data-toggle="modal"
                                    data-target="#useBonusModal"
                                    data-whatever="@mdo">
                                Списать бонусы
                            </button>
                        </#if>
                    </div>
                </div>
            </form>
        </#if>
    </div>
    <@modal.editformtransaction></@modal.editformtransaction>
</#macro>