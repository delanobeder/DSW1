<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AJAX (dynamic select)</title>
        <script src="js/ajaxEstado.js"></script>
    </head>
    <body>
        <br/>
        <jsp:useBean id='bean' class='br.ufscar.dc.dsw.bean.BuscaPorEstadoBean'/>

        <form name='form'>
            <table>
                <tr>
                    <td>Estado</td>
                    <td>
                        <select id = 'estado' name='estado' onchange='estadoSelecionado(this.value)'>
                            <option value='--'>--</option>
                            <c:forEach items='${bean.estados}' var='estado'>
                                <option value='${estado.sigla}'>${estado.sigla}</option>
                            </c:forEach>
                        </select>   
                    </td>
                </tr>
                <tr id='cidades'>    
                    <td>
                        Cidade
                    </td>
                    <td>
                        <select id='cidade' name='cidade' onchange='apresenta()'>
                        </select>
                    </td>   
                </tr>
            </table>
        </form>
        <br/>
        <a href="index.jsp">Voltar</a>
    </body>
</html>