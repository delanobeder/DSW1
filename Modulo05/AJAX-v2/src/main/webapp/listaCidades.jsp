<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AJAX (dynamic select)</title>
<script src="js/ajaxEstado.js"></script>
</head>
<body>
	<br />
	
	<jsp:useBean id='bean' class='br.ufscar.dc.dsw.bean.ListaCidadesBean' />
	
	<jsp:setProperty name="bean" property="sigla" value="${param.estado}"/>
	
	<form name='form'>
            <table>
                <tr>
                    <td>Estado</td>
                    <td>
                        <select id = 'estado' name='estado' onchange='submit()'>
                            <option value='--'>--</option>
                            <c:forEach items='${bean.estados}' var='estado'>
                                <option value='${estado.sigla}'>${estado.sigla}</option>
                            </c:forEach>
                        </select>   
                    </td>
                </tr>
            </table>
        </form>
        
	

	<p>Quantidade: ${fn:length(bean.cidades)}</p>
	<table border="1" style="width: 400px; border: 1px solid black">

		<tr>
			<th style="width: 70%;">Nome</th>
			<th style="width: 20%; text-align: center">Estado</th>
		</tr>
		<c:forEach var="cidade" items="${bean.cidades}">
			<tr>
				<td>${cidade.nome}</td>
				<td style="text-align: center">${cidade.estado.sigla}</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<a href="index.jsp">Voltar</a>
</body>
</html>