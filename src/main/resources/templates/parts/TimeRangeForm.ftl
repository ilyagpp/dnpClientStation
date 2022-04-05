<#macro TimeRangeForm>
    <form method="get" class="form-inline mt-3">
    <span class="form-inline">
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
    <button class="btn btn-primary" type="submit">Показать</button>
    </span>
        <a class="btn btn-primary ml-3" href="/transactions" role="link">Сброс</a>
    </form>
</#macro>

