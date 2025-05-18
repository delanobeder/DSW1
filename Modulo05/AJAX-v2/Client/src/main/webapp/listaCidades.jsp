<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AJAX (dynamic select)</title>
<script src="js/ajaxEstado.js" defer></script>
</head>
<body onload='recuperaEstados()' id = "listaCidades">
	<br />
	
	<form name='form'>
        <table>
            <tr>
                <td>Estado</td>
                <td>
                    <select id = 'estado' name='estado' onchange='recuperaCidades()'>    
                    </select>   
                    </td>
                </tr>
            </table>
    </form>
        
	<div id="cidades">
	</div>
	
	<br />
	<a href="index.jsp">Voltar</a>
</body>
</html>