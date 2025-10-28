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
    Comprova la sessió ('sessioUsuari') i mostra enllaços segons el rol. 
--%>
<header>
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç del logo/inici --%>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/paginaInici.jsp">
                <%-- CORRECCIÓ: Fem servir contextPath per a la imatge del logo --%>
                <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="Logo de TotEsBook" class="logo">
                <span style="vertical-align: middle;">TotEsBook</span>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç (tot i que sigui una àncora, és bona pràctica) --%>
                        <a class="nav-link" href="${pageContext.request.contextPath}/paginaInici.jsp#cerca">Cercar</a>
                    </li>
                    <li class="nav-item">
                        <%-- CORRECCIÓ: Enllaç al Servlet del catàleg --%>
                        <a class="nav-link" href="${pageContext.request.contextPath}/cataleg">Catàleg</a>
                    </li>
                    
                    <%-- 
                       CORRECCIÓ CRÍTICA: 
                       Canviem 'usuariLoguejat' per 'sessioUsuari' 
                       perquè coincideixi amb el nom usat al LoginServlet 
                    --%>
                    <c:if test="${empty sessionScope.sessioUsuari}">
                        <li class="nav-item">
                             <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç --%>
                            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Iniciar Sessió</a>
                        </li>
                    </c:if>

                    <c:if test="${not empty sessionScope.sessioUsuari}">
                        
                        <%-- 
                           CORRECCIÓ CRÍTICA: 
                           Accedim al rol mitjançant el getter de SessioUsuari -> .rol 
                        --%>
                        <c:if test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                            <li class="nav-item">
                                <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç --%>
                                <a class="nav-link fw-bold" href="${pageContext.request.contextPath}/dashboard_usuari.jsp">El Meu Panell</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                            <li class="nav-item">
                                <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç --%>
                                <a class="nav-link fw-bold" href="${pageContext.request.contextPath}/dashboard_bibliotecari.jsp">Panell Bibliotecari</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                            <li class="nav-item">
                                <%-- CORRECCIÓ: Fem servir contextPath per a l'enllaç --%>
                                <a class="nav-link fw-bold" href="${pageContext.request.contextPath}/dashboard_admin.jsp">Panell Admin</a>
                            </li>
                        </c:if>
                        
                        <li class="nav-item">
                            <%-- CORRECCIÓ: Enllaç al Servlet de Logout --%>
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout" style="color: var(--color-acento) !important; font-weight: bold;">Tancar Sessió</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</header>