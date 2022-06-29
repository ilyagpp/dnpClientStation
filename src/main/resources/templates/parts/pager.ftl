<#macro pager url page>
    <#if page.getTotalPages() gt 1>

    <nav aria-label="Page navigation example">
        <ul class="pagination pagination-sm justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Страницы</a>
            </li>
            <#list 1..page.getTotalPages() as p>
                <#if (p-1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link"
                        <#if url?contains("?")>
                           href="${url}&page=${p-1}&size=${page.getSize()}"
                        <#else>
                             href="${url}?page=${p-1}&size=${page.getSize()}"
                        </#if>
                         tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </nav>
    </#if>
</#macro>