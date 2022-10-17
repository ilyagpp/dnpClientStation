<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if  known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        id = user.getId()
        isAdmin = user.isAdmin()
        isAzsAdmin = user.isAzsAdmin()
    >
<#else>
    <#assign
        name = "Гость"
        isAdmin = false
        isAzsAdmin = false
        id = -1
    >
</#if>