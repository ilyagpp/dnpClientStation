<#include "security.ftl">
<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row mt-2">
            <label class="col-sm-2 col-form-label"> Имя пользователя:</label>
            <div class="col-sm-5">
            <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                   class="form-control ${(usernameError??)?string('is-invalid', '')}"
                    placeholder="Имя пользователя"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row mt-2">
            <label class="col-sm-2 col-form-label"> Пароль:</label>
            <div class="col-sm-5">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   placeholder="Пароль"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if !isRegisterForm>
            <div class="form-group row">
                <div class="col-sm-2">Запомнить меня:</div>
                <div class="col-sm-2">
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" id="remember-me" name="remember-me">
                    </div>
                </div>
            </div>
        </#if>
        <#if isRegisterForm>
            <div class="form-group row mt-2">
                <label class="col-sm-2 col-form-label"> Повторите пароль:</label>
                <div class="col-sm-5">
                    <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Повторите пароль"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row mt-2">
                <label class="col-sm-2 col-form-label"> Электронная почта (Email):</label>
                <div class="col-sm-5">
                    <input type="email" name="email" value="<#if user??>${user.email!""}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <span class="form-inline">
        <#if !isRegisterForm ><a class="btn btn-danger disabled mr-2" href="/registration" role="link">Регистрация</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Создать<#else>Войти</#if></button>
        </span>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">Выйти</button>
    </form>
</#macro>