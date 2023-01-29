<#include "security.ftl">
<#macro form>
    <div class="card text-center my-3">
        <div class="card-title mt-3 border-1 text-primary">
            <h5><#if edit??>Редактировать АЗС: ${azs.azsName}
                <#else>Новая АЗС:</#if></h5>
        </div>
        <div class="card-body">
            Информация о Азс, списке терминалов, пользователей, адрес и т.д.
        </div>
    </div>

    <form id="newAzs" action="/azs/profile" method="post">
        <div class="form-group">
            <#if edit??><input type="hidden" name="azs_id" value="${azs.azs_id}"></#if>
            <label for="azsName">Наименование</label>
            <input type="text" name="azsName" maxlength="50" <#if edit??>value="${azs.azsName}"</#if>
                   class="form-control ${(azsNameError??)?string('is-invalid', '')} text-center" id="azsName">
            <#if azsNameError??>
                <div class="invalid-feedback">
                    ${azsNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="properties">Сведения:</label>
            <input type="text" name="properties" maxlength="50" <#if edit??>value="${azs.properties}"</#if>
                   class="form-control ${(propertiesError??)?string('is-invalid', '')} text-center" id="properties">
            <#if propertiesError??>
                <div class="invalid-feedback">
                    ${properties}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="bonus">Бонус</label>
            <input type="text" name="bonus" maxlength="50" class="form-control text-center" <#if edit??> value="${azs.bonus?string}"</#if> id="bonus">
            <label for="elevatedBonus">Повышенный Бонус</label>
            <input type="checkbox" value="false" name="elevatedBonus" id = "elevatedBonus" <#if azs?? && azs.elevatedBonus>checked</#if>>
        </div>
        <div class="form-group">
            <label for="azsList">Список терминалов / Рабочих мест</label>
            <div id="azsList">

                <#if azs?? && azs.users??>
                    <#list azs.users as user>
                        <div class="mt-2">
                            <b>- ${user.username} </b>
                        </div>
                    </#list>
                </#if>
            </div>
        </div>

        <div class="form-group">
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false" aria-controls="collapseExample">
                Привязка терминалов / рабочих мест
            </a>
            <div class="collapse" id="collapseExample">
                <div class="form-group mt-3">
                    <div class="form-group">
                            <#list freeUsers as user>
                                    <div class="form-row row-cols-2 mt-2">
                                        <label><input type="checkbox" name="${user.id}">${user.username}</label>
                                    </div>
                            </#list>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <#if edit??><input type="hidden" name="edit" value="true"></#if>
            <button id="newAzs" type="submit" class="btn btn-primary"><#if edit??>Редактировать<#else>Добавить</#if></button>
        </div>
    </form>
</#macro>