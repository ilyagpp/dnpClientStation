<#include "security.ftl">
<#import  "login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">
        <img src="/static/logo1.jpg" width="70" height="70" class="rounded-circle" alt="logo">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href= "
                <#if isAdmin || isAzsAdmin>/main-operator<#else>/</#if>
                ">Главная</a>
            </li>

            <#if isAdmin || isAzsAdmin>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" aria-current="page"  href="#" id="CardWorkingSpace" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Клиенты
                    </a>
                    <div class="dropdown-menu" aria-labelledby="CardWorkingSpace">
                        <a class="dropdown-item" href="/clients">Клиенты</a>
                        <a class="dropdown-item" href="/client/new">Создать</a>
                        <a class="dropdown-item" href="/clients/pin">Пин-Коды</a>
                    </div>
                </li>
            </#if>
            <#if isAdmin || isAzsAdmin>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/transactions">Транзакции</a>
                </li>
            </#if>
            <#if isAdmin  || isAzsAdmin>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" aria-current="page"  href="#" id="CardWorkingSpace" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Карты
                    </a>
                    <div class="dropdown-menu" aria-labelledby="CardWorkingSpace">
                        <a class="dropdown-item" href="/cards">Список карт</a>
                    </div>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/user">Список пользователей</a>
                </li>
            </#if>
            <#if isAdmin  || isAzsAdmin>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/user/profile">Профиль</a>
                </li>
            </#if>
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="/main">Обратная связь</a>
            </li>
        </ul>

        <span class="form-inline my-2 my-lg-0">
            <div class="navbar-text mr-sm-2">${name}</div>
            <#if user??> <@l.logout /> <#else>
                <a class="btn btn-success" href="/main" role="link">Авторизация</a></#if>
        </span>

    </div>
</nav>