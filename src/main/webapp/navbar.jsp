<%@ page pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container-fluid">

        <!-- Logo / Nom appli -->
        <a class="navbar-brand fw-bold" href="home.jsp">Messagerie Interne</a>

        <div class="d-flex align-items-center ms-auto">

            <!-- Bouton profil -->
            <a href="settings.jsp" class="btn btn-light me-2 d-flex align-items-center">
                <i class="bi bi-person-circle me-1"></i>
<%--                ${sessionScope.prenom}--%>
                Reda
            </a>

            <!-- Bouton déconnexion -->
            <button class="btn btn-secondary d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#logoutModal">
                <i class="bi bi-box-arrow-right me-1"></i> Déconnexion
            </button>
        </div>
    </div>
</nav>

<!-- Modal Déconnexion -->
<div class="modal fade" id="logoutModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title">Confirmation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-center">
                Voulez-vous vraiment vous déconnecter ?
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                <a href="LogoutServlet" class="btn btn-danger">Déconnexion</a>
            </div>
        </div>
    </div>
</div>
