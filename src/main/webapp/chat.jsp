<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Conversation</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f1f3f6;
            margin: 0;
        }

        .chat-header {
            background: white;
            padding: 10px 15px;
            border-bottom: 1px solid #ddd;
            display: flex;
            align-items: center;
        }

        .chat-header h6 {
            margin: 0;
            font-weight: bold;
        }

        .back-btn {
            font-size: 20px;
            margin-right: 15px;
            color: #0d6efd;
            cursor: pointer;
        }

        /* Zone messages */
        .chat-container {
            padding: 20px;
            overflow-y: auto;
            height: calc(100vh - 140px); /* adapte selon hauteur navbar + header */
        }

        .message {
            max-width: 60%;
            padding: 10px 15px;
            border-radius: 15px;
            margin-bottom: 10px;
            position: relative;
            font-size: 14px;
        }

        .sent {
            background-color: #0d6efd;
            color: white;
            margin-left: auto;
            border-bottom-right-radius: 5px;
        }

        .received {
            background-color: #e9ecef;
            color: black;
            margin-right: auto;
            border-bottom-left-radius: 5px;
        }

        .message-time {
            font-size: 11px;
            opacity: 0.7;
            margin-top: 5px;
            text-align: right;
        }

        /* Zone input */
        .chat-input {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            background: white;
            padding: 10px 15px;
            border-top: 1px solid #ddd;
        }

        .send-btn {
            background-color: #0d6efd;
            color: white;
            border-radius: 50%;
            width: 45px;
            height: 45px;
            border: none;
        }

        .send-btn:hover {
            background-color: #0b5ed7;
        }
    </style>
</head>
<body>

<!-- Header conversation -->
<div class="chat-header">
    <a href="home.jsp" class="back-btn">
        <i class="bi bi-arrow-left"></i>
    </a>
    <h6>Youssef Omar</h6>
</div>

<!-- Messages -->
<div class="chat-container">

    <!-- Message reçu -->
    <div class="message received">
        Bonjour, comment ça va ?
        <div class="message-time">10:15</div>
    </div>

    <!-- Message envoyé -->
    <div class="message sent">
        Ça va très bien merci 😊
        <div class="message-time">10:16</div>
    </div>

    <!-- Message reçu -->
    <div class="message received">
        On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet
        ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet
        ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet
        ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet ?On travaille sur le projet
        ?On travaille sur le projet ?On travaille sur le projet ?
        <div class="message-time">10:17</div>
    </div>
    <!-- Message reçu -->
    <div class="message received">
        On travaille sur le projet ?
        <div class="message-time">10:17</div>
    </div>  <!-- Message reçu -->
    <div class="message received">
        On travaille sur le projet ?
        <div class="message-time">10:17</div>
    </div>  <!-- Message reçu -->
    <div class="message received">
        On travaille sur le projet ?
        <div class="message-time">10:17</div>
    </div>  <!-- Message reçu -->
    <div class="message received">
        On travaille sur le projet ?
        <div class="message-time">10:17</div>
    </div>
    <div class="message sent">
        Ça va très bien merci
        <div class="message-time">10:16</div>
    </div>
    <div class="message sent">
        Ça va très bien merci
        <div class="message-time">10:16</div>
    </div>
</div>

<!-- Input -->
<div class="chat-input">
    <form class="d-flex">
        <input type="text" class="form-control me-2" placeholder="Écrire un message..." required>
        <button type="submit" class="send-btn">
            <i class="bi bi-send-fill"></i>
        </button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function scrollToBottom() {
        const chatContainer = document.querySelector(".chat-container");
        chatContainer.scrollTop = chatContainer.scrollHeight;
    }

    window.onload = scrollToBottom;
</script>
</body>
</html>
