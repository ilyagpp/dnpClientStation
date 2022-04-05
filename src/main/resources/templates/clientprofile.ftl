<#import "parts/common.ftl" as c>

<@c.page>
    <h5>Профиль Клиента: ${surname!} ${name!} ${patronymic}</h5>
     <#if phoneNumberError??>
         <div class="alert alert-danger" role="alert">
             ${message}
         </div>
     <#else>
         <div class="alert alert-success" role="alert">
             ${message}
         </div>
     </#if>

    <#if emailError??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    <#else>
        <div class="alert alert-success" role="alert">
            ${message}
        </div>
    </#if>


    <form method="post">
        <div class="form-group row mt-2">
            <label class="col-sm-2 col-form-label"> Password:</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row mt-2">
            <label class="col-sm-2 col-form-label"> Email:</label>
            <div class="col-sm-5">
                <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary mt-2" type="submit">Сохранить</button>
    </form>
</@c.page>