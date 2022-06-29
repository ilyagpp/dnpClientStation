<#include "security.ftl">
<#macro clientform>
        <div class="card text-center my-3">
            <div class="card-title mt-3 border-1 text-primary">
                <h5><#if client??>Редактировать профиль клиента:
                    <#else>Новый клиент:</#if></h5>
            </div>
            <div class="card-body">
            Заполните информацию о клиенте, поля "email" и "телефон" являются ОБЯЗАТЕЛЬНЫМИ!
            </div>

        </div>

        <div class="form-group">
            <label for="surname">Фамилия</label>
            <input type="text" name="surname" maxlength="50" class="col-sm-5 form-control ${(surnameError??)?string('is-invalid', '')}" id="surname"
                   value="<#if client??>${client.surname}</#if >">
            <#if surnameError??>
                <div class="invalid-feedback">
                    ${surnameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" name="name" maxlength="50" class="col-sm-5 form-control ${(nameError??)?string('is-invalid', '')}" id="name"
                   value="<#if client??>${client.name}</#if>">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="patronymic">Отчество</label>
            <input type="text" name="patronymic" maxlength="50" class="col-sm-5 form-control" id="patronymic"
            <#if client??> value="<#if client.patronymic??>${client.patronymic}</#if>"</#if>>
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
            <input type="date" name="birthday" class="col-sm-5 form-control" id="birthday"
            <#if client??> value="${client.birthday!""}"</#if>>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Телефон</label>
            <input type="text" maxlength="10" class="col-sm-5 form-control ${(phoneNumberError??)?string('is-invalid', '')}"
                   name="phoneNumber" id="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="(123)4567890"
            <#if client??> value="${client.phoneNumber}" </#if>>
            <small id="phoneNumberHelp" class="form-text text-muted">Номер вводится без +7</small>
            <#if phoneNumberError??>
                <div class="invalid-feedback">
                    ${phoneNumberError}
                </div>
            </#if>
        </div>

        <div class="form-group">
            <label for="exampleInputEmail">Email</label>
            <input type="email" maxlength="50" class="col-sm-5 form-control ${(emailError??)?string('is-invalid', '')}"
                   id="exampleInputEmail" name="email" aria-describedby="emailHelp" placeholder="введите email"
            <#if client??>value="${client.email}"</#if>>
            <small id="emailHelp" class="form-text text-muted">Ваш email</small>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
        <#if isAdmin || isAzsAdmin>
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