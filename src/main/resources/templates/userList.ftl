<#import "parts/common.ftl" as c>

<@c.page>
    List of users
    <form method="get" class="form-inline">
        <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
    </form>

    <table class="table mt-3">
        <thead class="thead-light">
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/aUser/${user.id}">edit</a> </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>