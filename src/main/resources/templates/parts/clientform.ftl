<#include "security.ftl">
<#macro clientform>


        <div class="form-group">
            <label for="surname">Фамилия</label>
            <input type="text" name="surname" class="form-control ${(surnameError??)?string('is-invalid', '')}" id="surname"
                   value="<#if client??>${client.surname}</#if >">
            <#if surnameError??>
                <div class="invalid-feedback">
                    ${surnameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" name="name" class="form-control ${(nameError??)?string('is-invalid', '')}" id="name"
                   value="<#if client??>${client.name}</#if>">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="patronymic">Отчество</label>
            <input type="text" name="patronymic" class="form-control" id="patronymic">
        </div>
        <div>Пол</div>
        <div class="form-check form-check-inline" >
            <input class="form-check-input" type="radio" name="sex" id="sex" value="man">
            <label class="form-check-label"  for="man">Мужской</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="sex" id="sex" value="woman">
            <label class="form-check-label" for="sex">Женский</label>
        </div>

        <div class="form-group"></div>


        <div class="form-group">
            <label for="birthday">Дата рождения</label>
            <input type="date" name="birthday" class="form-control" id="birthday">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Телефон</label>
            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="(123)4567890">
        </div>

        <div class="form-group">
            <label for="exampleInputEmail">Email</label>
            <input type="email" class="form-control" id="exampleInputEmail" name="email" aria-describedby="emailHelp" placeholder="введите email">
            <small id="emailHelp" class="form-text text-muted">Ваш email</small>
        </div>
        <#if isAdmin>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="active" id="active" value="true" checked>
            <label class="form-check-label" for="active">
                Активировать
            </label>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary">Создать</button>


</#macro>