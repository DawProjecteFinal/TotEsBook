<%-- 
    Document   : dashboard_usuari.jsp
    Created on : 23 oct 2025, 14:26:50
    Author     : edinsonioc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panell d'Usuari - TotEsBook</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="css/style.css">
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

    <header>
        <nav class="navbar navbar-expand-lg">
            <div class="container">
                <a class="navbar-brand" href="paginaInici.html">
                    <img src="img/logo.jpg" alt="Logo de TotEsBook" class="logo">
                    <span style="vertical-align: middle;">TotEsBook</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="#prestecs">Els Meus Préstecs</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="paginaInici.html#cerca">Cercar Llibres</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="paginaInici.html" style="color: var(--color-acento) !important; font-weight: bold;">Tancar Sessió</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <section class="featured-section" style="min-height: 80vh;">
        <div class="container">
            <h2 class="mb-4">Benvingut/da, [Nom Usuari]</h2>
            
            <div class="featured-card" id="prestecs">
                <h4><i class="bi bi-book-fill"></i> Els Meus Préstecs Actuals</h4>
                <hr>
                
                <div class="list-group">
                    <div class="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <strong>El nom del vent</strong>
                            <small class="d-block text-muted">Venciment: 30/10/2025</small>
                        </div>
                        <button class="btn btn-sm" style="background-color: var(--color-secundario); color: white;">Renovar</button>
                    </div>
                    <div class="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <strong>Cien años de soledad</strong>
                            <small class="d-block text-muted">Venciment: 05/11/2025</small>
                        </div>
                        <button class="btn btn-sm" style="background-color: var(--color-secundario); color: white;">Renovar</button>
                    </div>
                </div>
            </div>

        </div>
    </section>


    <footer class="mt-5">
        <div class="container">
            <p>&copy; 2025 TotEsBook. Tots els drets reservats.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>