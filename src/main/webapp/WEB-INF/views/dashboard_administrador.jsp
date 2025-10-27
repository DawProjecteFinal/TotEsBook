<%-- 
    Document   : dashboard_administrador
    Created on : 23 oct 2025, 14:34:02
    Author     : edinsonioc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panell d'Administració - TotEsBook</title>
    
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
                    <span style="vertical-align: middle;">TotEsBook (Admin)</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="#gestio-usuaris">Gestionar Usuaris</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="dashboard_bibliotecario.html">Panell Bibliotecari</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#estadistiques">Estadístiques</a>
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
            <h2 class="mb-4">Panell d'Administració</h2>
            
            <div class="featured-card" id="gestio-usuaris">
                <h4><i class="bi bi-people-fill"></i> Gestió d'Usuaris</h4>
                <hr>
                <p>Aquí aniria la taula per gestionar els usuaris del sistema (bibliotecaris i usuaris finals).</p>
                <button class="btn" style="background-color: var(--color-acento); color: white;">
                    <i class="bi bi-person-plus-fill"></i> Crear nou usuari
                </button>
                
                <table class="table table-striped mt-3">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Accions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Anna Puig</td>
                            <td>anna@exemple.com</td>
                            <td>Bibliotecari</td>
                            <td><a href="#">Editar</a> | <a href="#" class="text-danger">Eliminar</a></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Marc Soler</td>
                            <td>marc@exemple.com</td>
                            <td>Usuari</td>
                            <td><a href="#">Editar</a> | <a href="#" class="text-danger">Eliminar</a></td>
                        </tr>
                    </tbody>
                </table>

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