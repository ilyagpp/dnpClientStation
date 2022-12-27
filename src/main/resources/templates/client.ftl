<#import "parts/common.ftl" as c>
<#import "parts/clientform.ftl" as clfm>
<@c.page>
    <div>
    <form method="post">
    <@clfm.clientform>
    </@clfm.clientform>
    </form>
    <script type="text/javascript">
        $(document).ready(function() {

            $('#two').click(function () {
                $(this).attr('disabled', true); // Либо добавить атрибут disabled
                document.forms[1].submit();
            });
        });
    </script>
    </div>
</@c.page>
