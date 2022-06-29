<#macro sizer url page>

    <div class="form-inline">
        <label class="mr-2" for="size">показать по</label>


        <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" name="size">
            ${page.getSize()}
        </button>
        <div class="dropdown-menu">
        <#list [5, 10, 15, 25, 50, 100] as s>
            <a class="dropdown-item" href="
            <#if url?contains("?")>
            ${url}&page=${page.getNumber()}&size=${s}
            <#else >
            ${url}?page=${page.getNumber()}&size=${s}
            </#if>
            ">${s}</a>
        </#list>
        </div>








</#macro>