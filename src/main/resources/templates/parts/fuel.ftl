<#macro fuelnames fuel>
    <#if fuel == "DIESEL">ДТ
    <#elseif fuel == "AI_92">АИ-92
    <#elseif fuel == "AI_95">АИ-95
    <#elseif fuel == "SUG">СУГ</#if>
</#macro>