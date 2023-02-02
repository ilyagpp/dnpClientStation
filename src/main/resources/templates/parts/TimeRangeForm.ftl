<#include "security.ftl">
<#macro TimeRangeForm payType operationType>
    <form method="get" class="form-inline mt-3">
    <span class="form-inline">
    <#if isAdmin>

        <div class="form-check">
                <label class="mr-1" for="defaultCheck1">Показать все</label>
            <input class="form-check-input"
                   type="checkbox" name="showAll"
                   id="defaultCheck1" value="true"
                   <#if showAll??>checked</#if>>
            </div>
    </#if>
        <div>
            <label for="start" class="col-form-label">От</label>
        </div>
    <div class="col">
        <input type="datetime-local" class="form-control" id="start" name="start"
               value="<#if start??>${start}</#if>">
    </div>
    <div>
        <label for="end" class="col-form-label">До</label>
    </div>
    <div class="col">
        <input type="datetime-local" class="form-control" id="end" name="end"
               value="<#if end??>${end}</#if>">
    </div>
    <div class="col">
        <div class="input-group">
          <div class="input-group-prepend">
            <label class="input-group-text" for="payType">Тип Оплаты</label>
          </div>
          <select class="custom-select" id="payType" name="payType">
            <option <#if payType?? && payType ==100> selected</#if> value="100">Все</option>
            <option <#if payType?? && payType ==0> selected</#if> value="0">НАЛ</option>
            <option <#if payType?? && payType ==1> selected</#if> value="1">БЕЗНАЛ</option>
          </select>
        </div>
    </div>
        <div class="col">
        <div class="input-group">
          <div class="input-group-prepend">
            <label class="input-group-text" for="operationType">Операция</label>
          </div>
          <select class="custom-select" id="operationType" name="operationType">
            <option <#if operationType?? && operationType ==100> selected</#if> value="100">Все</option>
            <option <#if operationType?? && operationType ==0> selected</#if> value="0">НАКОП</option>
            <option <#if operationType?? && operationType ==1> selected</#if> value="1">СПИС</option>
          </select>
        </div>
    </div>
        <input type="hidden" name="size" value="${size}">
    <button class="btn btn-primary" type="submit">Показать</button>

        <a class="btn btn-primary ml-3" href="/transactions" role="link">Сброс</a>
    </span>
    </form>

</#macro>

