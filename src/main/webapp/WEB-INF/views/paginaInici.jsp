<%-- 
    Document   : paginaInici
    Created on : 28 sept 2025, 17:43:00
    Author     : jmiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TotEsBook</title>
        <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> -->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .bg-totlight {
                --bs-bg-opacity: 1;
                background-color: #eefcfe;
            }


            .bg-tot {
                background-color: #92dbe6;
            }


            .btn-tot {
                background-color: #eefcfe;
                border: 1px solid #35818b;
                color: #35818b;
                border-radius: 6px;
                transition: background-color 0.2s ease;
            }


            .btn-tot:hover,
            .btn-tot:focus,
            .btn-tot:active {
                background-color: #92dbe6;
                border: none;
                color: white;
                box-shadow: none;
            }


            .text-tot-bold {
                font-weight: bold;
                color: #324b4f;
            }


            .text-tot-light {
                color: #324b4f;
            }

            .img-fixed {
                width: 189px;
                height: 200px;
                object-fit: cover;
                object-position: center;
            }
        </style>
    </head>
    <body>

        <!-- Encapçalat -->
        <header class="bg-tot py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">TotEsBook</h1>
                    <p class="lead fw-normal text-white-50 mb-0">La biblioteca de tothom</p>
                    <img src="assets/logo-gran.jpeg" alt="Logo">
                </div>
            </div>
        </header>
                
        
        <!-- Menu -->
        <nav class="navbar navbar-expand-lg navbar-light bg-totlight">
            <div class="container px-4 px-lg-5">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>


                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">Inici</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">Biblioteques</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li>
                                    <hr class="dropdown-divider" />
                                </li>
                                <li><a class="dropdown-item" href="#!">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="#!">Ficció</a></li>
                                <li><a class="dropdown-item" href="#!">Juvenil</a></li>
                                <li><a class="dropdown-item" href="#!">Novel·la</a></li>
                                <li><a class="dropdown-item" href="#!">True crime</a></li>
                            </ul>
                        </li>
                    </ul>

                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search">
                            <input class="form-control form-control-sm me-2" type="search"
                                   placeholder="Cerca per títol, autor..." aria-label="Search">
                            <button class="btn btn-tot btn-sm" type="submit">
                                <i class="bi bi-search-heart"></i>
                            </button>
                        </form>

                        <a href="#" class="btn btn-tot btn-sm my-2 my-lg-0">Inicia sessió</a>
                    </div>
                </div>
            </div>
        </nav>
        

        <section class="py-5">

            <div class="container px-4 px-lg-5 mt-5">
                <h1 class="justify-content-center">En construcció!</h1>
                
                <form action="${pageContext.request.contextPath}/mostrarLlibres" method="get">
                    <button type="submit">Veure el llistat de llibres</button>
                </form>

                <form action="${pageContext.request.contextPath}/mostrarUsuaris" method="get">
                    <button type="submit">Veure el llistat de usuaris</button>
                </form>
                    <br>
                    <br>
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                    <% for (int i = 0; i < 16; i++) {%>
                    <div class="col mb-5">
                        <div class="card h-100">
                            <img class="card-img-top" src="https://dummyimage.com/189x290/cccccc/fff.jpg&text=Portada" alt="..." />
                            <div class="card-body p-4">
                                <div class="text-center">
                                    <h5 class="text-tot-bold">Títol <%= i + 1%></h5>
                                    <p class="text-tot-light">Autor</p>
                                </div>
                            </div>
                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                <div class="text-center">
                                    <a class="btn btn-tot mt-auto" href="#">Més informació</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <% }%>
                </div>
            </div>
        </section>


        <!-- peu de pàgina -->
        <footer class="bg-tot text-center text-lg-start border-top mt-5 py-3">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-4 mb-3 mb-md-0">
                        <h6 class="fw-bold">TotEsBook</h6>
                        <p class="mb-0 small">Projecte de gestió de biblioteques · DAW M12</p>
                    </div>

                    <div class="col-md-4 mb-3 mb-md-0">
                        <ul class="list-unstyled mb-0">
                            <li><a href="#" class="text-decoration-none text-secondary">Contacte</a></li>
                            <li><a href="#" class="text-decoration-none text-secondary">Informació legal</a></li>
                            <li><a href="#" class="text-decoration-none text-secondary">Política de privacitat</a></li>
                        </ul>
                    </div>

                    <div class="col-md-4">
                        <div class="d-flex justify-content-center">
                            <a href="#"><i class="bi bi-twitter mx-2 text-secondary"></i></a>
                            <a href="#"><i class="bi bi-facebook mx-2 text-secondary"></i></a>
                            <a href="#"><i class="bi bi-instagram mx-2 text-secondary"></i></a>
                        </div>
                        <p class="fst-italic small mt-2 mb-0">“Llegir és viure mil vides.”</p>
                    </div>
                </div>

                <hr class="my-3">
                <p class="text-center small text-muted mb-0">© 2025 TotEsBook. Tots els drets reservats.</p>
            </div>
        </footer>
        
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>

