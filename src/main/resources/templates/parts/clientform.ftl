<#include "security.ftl">
<#macro clientform>
    <div class="card text-center my-3">
        <div class="card-title mt-3 border-1 text-primary">
            <h5><#if edit??>Редактировать профиль клиента: ${client.id}
                <#else>Новый клиент:</#if></h5>
        </div>
        <div class="card-body">
            Заполните информацию о клиенте, поля "фамилия", "имя", "email" и "телефон" являются ОБЯЗАТЕЛЬНЫМИ!
        </div>
    </div>

    <form id="reg" >
        <div class="container-fluid" id="clntfrm">
    <div class="form-group">
        <#if edit??><input type="hidden" name="id" value="${client.getId()?c}"></#if>
        <label for="surname">Фамилия</label>
        <input type="text" name="surname" maxlength="50"
               class="form-control ${(surnameError??)?string('is-invalid', '')} text-center" id="surname"
               value="<#if client??>${client.surname}</#if >">
        <#if surnameError??>
            <div class="invalid-feedback">
                ${surnameError}
            </div>
        </#if>
    </div>
    <div class="form-group">

        <label for="name">Имя</label>
        <input type="text" name="name" maxlength="50"
               class="form-control ${(nameError??)?string('is-invalid', '')} text-center" id="name"
               value="<#if client??>${client.name}</#if>">
        <#if nameError??>
            <div class="invalid-feedback">
                ${nameError}
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="patronymic">Отчество</label>
        <input type="text" name="patronymic" maxlength="50" class="form-control text-center" id="patronymic"
                <#if client??> value="<#if client.patronymic??>${client.patronymic}</#if>"</#if>>
    </div>
    <div>Пол</div>
    <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="sex" id="sex"
               value="man" <#if client?? > <#if !client.getSex()?contains("unknown") && !client.sex?contains("woman")>checked</#if> </#if>>
        <label class="form-check-label" for="man">Мужской</label>
    </div>
    <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="sex" id="sex"
               value="woman" <#if client?? > <#if !client.getSex()?contains("unknown") && client.sex?contains("woman")>checked</#if></#if>>
        <label class="form-check-label" for="sex">Женский</label>
    </div>

    <div class="form-group"></div>


    <div class="form-group">
        <label for="birthday">Дата рождения</label>
        <input type="date" name="birthday" class="form-control text-center" id="birthday"
               value="<#if edit?? && client.getBirthday()??>${client.birthday}</#if>">
    </div>
    <div class="form-group">
        <label for="phoneNumber">Телефон</label>
        <input type="tel" maxlength="10" class="form-control ${(phoneNumberError??)?string('is-invalid', '')} text-center"
               name="phoneNumber" id="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="(123)4567890" oninput="changePhoneHandler(this)"
                <#if client??> value="${client.phoneNumber}" </#if>>
        <small id="phoneNumberHelp" class="form-text text-muted">Номер вводится без +7</small>
        <#if phoneNumberError??>
            <div class="invalid-feedback">
                ${phoneNumberError}
            </div>
        </#if>
        <script type="text/javascript">
            const changePhoneHandler = e =>{
                const value = e.value
                e.value = value.replace(/[^0-9]/g , '')
            }
        </script>
    </div>

    <div class="form-group">
        <label for="exampleInputEmail">Email</label>
        <input type="email" maxlength="50" class="form-control ${(emailError??)?string('is-invalid', '')} text-center"
               id="exampleInputEmail" name="email" aria-describedby="emailHelp" placeholder="введите email"
               <#if client??>value="${client.email}"</#if>>
        <small id="emailHelp" class="form-text text-muted">Ваш email</small>
        <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
    </div>
    <div class="form-check" style="display: none">
        <input class="form-check-input" type="radio" name="active" id="active" value="true" checked>
        <label class="form-check-label" for="active">
            Активировать
        </label>
    </div>
    <#if !edit??>
      <div class="form-group">
          <label for="pin">Пин Код</label>
          <input type="text" maxlength="4" class="col-sm-2 form-control ${(pinError??)?string('is-invalid', '')} text-center"
                 name="pin" id="pin" aria-describedby="pinHelp" placeholder="****" oninput="changeHandler(this)">
          <small id="pinHelp" class="form-text text-muted">Пин генерируется автоматически, или введите пин по желанию, строго 4 цифры</small>
          <#if pinError??>
              <div class="invalid-feedback">
                  ${pinError}
              </div>
          </#if>
      </div>
        <script src="static/JS/TransactionTable.js"></script>
      </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="form-group">

    <button id="two" type="submit" class="btn btn-primary"><#if edit??>Применить<#else>Добавить</#if></button>
    </div>
        </div>
    </form>
    <div class="card text-center my-3">
        <small id="smtxt">
            <div class="card-body">
                В соответствии с требованиями статьи 9 Федерального закона от 27.07.06 N 152-ФЗ "О персональных данных",
                нажимая кнопку "Добавить", вы выражаете свое согласие ООО «Дорисс-Нефтепродукт»,
                расположенному по адресу: г. Чебоксары, Дорожный проезд д. 10, (далее — Оператор)
                на обработку персональных данных, указанных в данной форме, в целях сбора и обработки
                статистической информации и проведения маркетинговых исследований.
            </div>
        </small>
    </div>
    <style>
        #smtxt{
            font-size: 0.6rem;
        }
        #clntfrm{
            text-align: center;
        }
        input{
            display: block;
            margin: 0 auto;
        }
    </style>
</#macro>