<%-- 
    Author     :  Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Afegir Llibre</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <h5>Afegir Llibre a una Biblioteca</h5>


            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success mt-3">${success}</div>
            </c:if>


            <form action="${pageContext.request.contextPath}/gestio/llibres/afegir" method="post" class="mt-3">

                <div class="mb-3">
                    <label class="form-label">ISBN</label>
                    <input type="text" name="isbn" class="form-control"
                           placeholder="Introdueix l'ISBN" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Biblioteca</label>
                    <select name="idBiblioteca" class="form-select" required>
                        <option value="">-- Selecciona una biblioteca --</option>
                        <c:forEach var="b" items="${biblioteques}">
                            <option value="${b.idBiblioteca}">${b.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Exemplars</label>
                    <input type="number" name="exemplars" class="form-control"
                           min="1" value="1" required>
                </div>

                <button type="submit" class="btn btn-tot">Afegir Llibre</button>
                <a href="${pageContext.request.contextPath}/dashboard_administrador"
                   class="btn btn-secondary">Cancel·lar</a>
            </form>


            <c:if test="${not empty llibreAfegit}">
                <hr class="my-4">

                <h5>Llibre afegit</h5>

                <div class="card mt-3" style="max-width: 450px;">
                    <div class="row g-0">
                        <div class="col-4">
                            <img src="${llibreAfegit.imatgeUrl}" 
                                 class="img-fluid rounded-start"
                                 alt="Portada de ${llibreAfegit.titol}">
                        </div>
                        <div class="col-8">
                            <div class="card-body">
                                <h5 class="card-title mb-1">
                                    <c:out value="${llibreAfegit.titol}"/>
                                </h5>
                                <p class="card-text text-muted mb-2">
                                    <c:out value="${llibreAfegit.autor}"/>
                                </p>
                                <p class="card-text mb-0">
                                    <small class="text-muted">
                                        ISBN: <c:out value="${llibreAfegit.isbn}"/>
                                    </small>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/dashboard_administrador"
                       class="btn btn-tot">
                        Tornar al meu panell de gestió
                    </a>
                </div>
            </c:if>

        </div>

    </body>
</html>

