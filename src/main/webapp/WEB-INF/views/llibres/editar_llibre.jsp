<%-- 
    Author     :  Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Editar Llibre</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <h5>Assignar Biblioteca i Exemplars</h5>

            <form action="${pageContext.request.contextPath}/gestio/llibres/${llibre.isbn}/editar" method="post">

                
                <div class="mb-3">
                    <label class="form-label">ISBN</label>
                    <input type="text" class="form-control" value="${llibre.isbn}" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Títol</label>
                    <input type="text" class="form-control" value="${llibre.titol}" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Autor</label>
                    <input type="text" class="form-control" value="${llibre.autor}" readonly>
                </div>

                
                <div class="mb-3">
                    <label class="form-label">Biblioteca assignada</label>
                    <select name="idBiblioteca" class="form-select" required>
                        <option value="">-- Selecciona una biblioteca --</option>
                        <c:forEach var="b" items="${biblioteques}">
                            <option value="${b.idBiblioteca}"
                                <c:if test="${relacio != null && relacio.biblioteca.idBiblioteca == b.idBiblioteca}">
                                    selected
                                </c:if>>
                                ${b.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Exemplars</label>
                    <input type="number" name="exemplars" class="form-control"
                           min="0" required
                           value="<c:out value='${relacio != null ? relacio.exemplars : 0}'/>">
                </div>

                <button type="submit" class="btn btn-tot">Guardar canvis</button>
                <a href="${pageContext.request.contextPath}/dashboard_administrador" class="btn btn-secondary">Cancel·lar</a>
            </form>
        </div>

    </body>
</html>



