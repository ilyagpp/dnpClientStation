<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
    <form method="get" action="/main" class="form-inline" >
        <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Найти по тегу">
        <button class="btn btn-primary ml-2" type="submit">Найти</button>
    </form>
    </div>
</div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Добавить сообщение
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.text}</#if>" name="text" placeholder="Введите сообщение" />
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>

            <div class="form-group">
                <input type="text" name="tag" class="form-control"
                       value="<#if message??>${message.tag}</#if>" placeholder="Тэг">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    </div>
</div>
<div >
    <table class="table mt-3">
        <thead class="thead-light">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Message</th>
            <th scope="col">Tag</th>
            <th scope="col">Author</th>
        </tr>
        </thead>
        <tbody>
        <#list messages as message>

            <div class="text-center">
                <tr>
                    <th scope="col">${message.id}</th>
                    <th scope="col">${message.text}</th>
                    <th scope="col">${message.tag}</th>
                    <th scope="col">${message.authorName}</th>
                </tr>
            </div>

        </#list>
        </tbody>
    </table>

</div>


</@c.page>