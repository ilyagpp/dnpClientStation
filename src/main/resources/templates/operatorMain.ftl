<#import "parts/common.ftl" as c>
<#import "parts/fuel.ftl" as fuel>
<#import "parts/TransansactionClientInfo.ftl" as transclntinfo>
<#import "parts/modalFormTransactions.ftl" as modaltransaction>
<#import "parts/lastTransactionsList.ftl" as lastTransactions>
<@c.page>

    <div class="container">
        <div class="row">
            <div class="col-8">
                <@transclntinfo.transactionclientinfo></@transclntinfo.transactionclientinfo>
                <@modaltransaction.formtransaction></@modaltransaction.formtransaction>
                <#if clientError??>
                    <div class="alert alert-danger mt-3" role="alert">
                        ${clientError}
                    </div>
                </#if>
                <#if error??>
                    <div class="alert alert-danger mt-3" role="alert">
                        ${error}
                    </div>
                </#if>
                <div class="mt-3">
                    <small>
                        <@lastTransactions.lastTransactionsList lastTransactionsList></@lastTransactions.lastTransactionsList>
                    </small>
                </div>
            </div>
            <div class="col-2">
                <div class="container" id="stella">
                    <div class="card text-center bg-light" style="width: 8rem;">
                        <div class="card-body">

                            <h5>Цены</h5>
                            <form method="post">
                                <#list priceList as price>
                                    <div class="text-center mt-1">
                                        <label for="price"> <@fuel.fuelnames fuel= price.fuel></@fuel.fuelnames> </label>

                                        <input class="form-control text-center" maxlength="5" name="price"
                                               aria-label="price"
                                                <#if price.price??> value="${price.price?string["00.00"]}"
                                               </#if>oninput="changeHandler(this)"
                                               style="max-width: 5rem;">
                                        <input type="hidden" class="form-control" name="fuel" value="${price.fuel}">
                                        <input type="hidden" class="form-control" name="id" value="${price.id!""}">
                                    </div>
                                </#list>
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <button type="submit" class="btn btn-outline-primary mt-3" id="stellabtn">Записать
                                </button>
                            </form>
                            <script src="static/JS/TransactionTable.js"></script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</@c.page>