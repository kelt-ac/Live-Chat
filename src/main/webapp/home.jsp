<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - Messagerie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f8f9fa;
        }

        .user-card {
            background: white;
            border-radius: 15px;
            padding: 20px;
            text-align: center;
            transition: 0.3s;
            cursor: pointer;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .user-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.15);
        }

        .user-icon {
            width: 100px;
            height: 100px;
            background-color: #e7f1ff;
            color: #0d6efd;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 45px;
            margin: 0 auto;
        }

        .user-name {
            margin-top: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <h3 class="mb-4 text-primary">Liste des utilisateurs</h3>

    <div class="row g-4">

        <!-- Exemple statique (sera remplacé par boucle JSTL plus tard) -->
        <div class="col-md-3">
            <div class="user-card">
                <i class="bi bi-person-fill user-icon"></i>
                <div class="user-name">Ahmed Ali</div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="user-card">
                <i class="bi bi-person-fill user-icon"></i>
                <div class="user-name">Sara Ben</div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="user-card">
                <i class="bi bi-person-fill user-icon"></i>
                <div class="user-name">Youssef Omar</div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
