<!DOCTYPE html>
<html>
    <head>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script>
            $(function () {
                $("#cidade").autocomplete({
                    select: function (event, ui) {
                        alert("Selecionado: " + ui.item.value);
                    },
                    source: "/AJAX-Server/cidades",
                    minLength: 2
                });
            });
        </script>
        <title>AJAX (Autocomplete)</title>
    </head>
    <div class="ui-widget">
        <label for="cidade">Nome</label>
        <input id="cidade" name="cidade" placeholder="Pelo menos 2 caracteres">
    </div>
    <br/>
    <a href="index.jsp">Voltar</a>
</body>
</html>