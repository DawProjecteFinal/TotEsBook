<%-- 
    Author     :  Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Gestionar Préstec</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container mt-5">

            <h5 class="mb-4">Gestionar Préstec</h5>

            <c:if test="${not empty missatge}">
                <div class="alert alert-success">
                    ${missatge}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>

            <div class="card shadow-sm">
                <div class="card-body">

                    <!-- DADES DEL PRÉSTEC -->
                    <div class="mb-3">
                        <label class="form-label">ISBN</label>
                        <input type="text" class="form-control" value="${prestec.llibre.isbn}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Títol</label>
                        <input type="text" class="form-control" value="${prestec.llibre.titol}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Usuari</label>
                        <input type="text" class="form-control"
                               value="${prestec.usuari.nom} ${prestec.usuari.cognoms}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Data préstec</label>
                        <input type="text" class="form-control" value="${prestec.dataPrestecFormatted}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Data venciment</label>
                        <input type="text" class="form-control" value="${prestec.dataVencimentCalculada}" readonly>
                    </div>



                    <!-- ACCIONS -->
                    <h6 class="mb-3">Accions</h6>

                    <div class="d-flex flex-wrap gap-2">

                        <!-- TORNAR -->
                        <form action="${pageContext.request.contextPath}/gestionarPrestec/retornar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-box-arrow-in-down-left"></i> Tornar
                            </button>
                        </form>

                        <!-- RENOVAR +30 DIES -->
                        <form action="${pageContext.request.contextPath}/gestionarPrestec/renovar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">
                            <button type="submit" class="btn btn-warning">
                                <i class="bi bi-arrow-repeat"></i> Renovar +30 dies
                            </button>
                        </form>

                        <!-- SANCIONAR (DUES OPCIONS) -->
                        <form action="${pageContext.request.contextPath}/gestionarPrestec/sancionar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">

                            <button type="submit" name="tipus" value="RETARD"
                                    class="btn btn-outline-danger">
                                Sanció per retard
                            </button>

                            <button type="submit" name="tipus" value="MAL_ESTAT"
                                    class="btn btn-danger">
                                Sanció per mal estat
                            </button>
                        </form>

                    </div>

                    <hr>

                    <a href="${pageContext.request.contextPath}/dashboard_bibliotecari"
                       class="btn btn-secondary">
                        &laquo; Tornar al llistat de préstecs
                    </a>

                </div>
            </div>

        </div>

    </body>
</html>

