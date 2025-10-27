<%-- 
    Document   : dashboard_bibliotecari.jsp
    Created on : 23 oct 2025, 14:27:27
    Author     : edinsonioc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panell de Bibliotecari - TotEsBook</title>
    
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
                    <span style="vertical-align: middle;">TotEsBook (Bibliotecari)</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="#gestio-prestecs">Gestionar Préstecs</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#gestio-cataleg">Gestionar Catàleg</a>
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
            <h2 class="mb-4">Panell de Bibliotecari</h2>
            
            <div class="featured-card" id="gestio-prestecs">
                <h4><i class="bi bi-arrow-down-up"></i> Gestió de Préstecs</h4>
                <hr>
                <p>Aquí anirien les eines per registrar nous préstecs i devolucions.</p>
                <form class="row g-3">
                    <div class="col-md-5">
                        <input type="text" class="form-control" placeholder="ISBN del llibre">
                    </div>
                    <div class="col-md-5">
                        <input type="text" class="form-control" placeholder="ID de l'usuari">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn" style="background-color: var(--color-secundario); color: white; width: 100%;">Prestar</button>
                    </div>
                </form>
            </div>

            <div class="featured-card mt-4" id="gestio-cataleg">
                <h4><i class="bi bi-journal-plus"></i> Gestió del Catàleg</h4>
                <hr>
                <p>Eines per afegir, editar o eliminar llibres del catàleg.</p>
                <button class="btn" style="background-color: var(--color-acento); color: white;">
                    Afegir nou llibre
                </button>
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