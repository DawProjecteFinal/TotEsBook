<%-- 
    Document   : header
    Created on : 27 oct 2025, 13:02:07
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    Aquest fitxer gestiona: la comprovació de canvi avis per a que amb la sessió oberta
    la homepage no mostri l'inici de sessió; funcionalitats de control d'accés; el logout segur.
    Comprova la sessió ('usuariLoguejat') i mostra enllaços segons el rol.
--%>
<header>
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="paginaInici.jsp">
                <img src="img/logo.jpg" alt="Logo de TotEsBook" class="logo">
                <span style="vertical-align: middle;">TotEsBook</span>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="paginaInici.jsp#cerca">Cercar</a>
                    </li>
                    
                    <%-- Comprovació si la sessió NO està oberta --%>
                    <c:if test="${empty sessionScope.usuariLoguejat}">
                        <li class="nav-item">
                            <a class="nav-link" href="login.jsp">Iniciar Sessió</a>
                        </li>
                    </c:if>

                    <%-- Si la sessió SÍ està oberta, mostrem segons el rol --%>
                    <c:if test="${not empty sessionScope.usuariLoguejat}">
                        
                        <%-- El JSP EL pot comparar enums amb strings directament --%>
                        <c:if test="${sessionScope.usuariLoguejat.rol == 'USUARI'}">
                            <li class="nav-item">
                                <a class="nav-link fw-bold" href="dashboard_usuari.jsp">El Meu Panell</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.usuariLoguejat.rol == 'BIBLIOTECARI'}">
                            <li class="nav-item">
                                <a class="nav-link fw-bold" href="dashboard_bibliotecari.jsp">Panell Bibliotecari</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.usuariLoguejat.rol == 'ADMIN'}">
                            <li class="nav-item">
                                <a class="nav-link fw-bold" href="dashboard_admin.jsp">Panell Admin</a>
                            </li>
                        </c:if>
                        
                        <%-- Enllaç de Logout Segur --%>
                        <li class="nav-item">
                            <a class="nav-link" href="logout" style="color: var(--color-acento) !important; font-weight: bold;">Tancar Sessió</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</header>