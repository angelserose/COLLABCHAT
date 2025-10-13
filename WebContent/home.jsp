<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
    String username = (String) session.getAttribute("username");
    Integer userId = (Integer) session.getAttribute("userId");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Home - CollabChat</title>
    <link rel="stylesheet" href="styles.css" />
    <script src="app.js"></script>
</head>
<body>
<div class="topbar">
    <div class="brand">CollabChat</div>
    <div class="user">Logged in as <strong><%= username %></strong> <a href="logout">Logout</a></div>
</div>
<div class="container">
    <h2>Your Groups</h2>
    <div id="groups"></div>

    <h3>Create Group</h3>
    <form id="createGroupForm">
        <input type="text" id="groupName" placeholder="Group name" required />
        <button type="submit">Create</button>
    </form>

    <h3>Join Group</h3>
    <form id="joinGroupForm">
        <input type="number" id="joinGroupId" placeholder="Group ID" required />
        <button type="submit">Join</button>
    </form>

    <div class="extras">
        <button id="openWhiteboard">Shared Whiteboard (placeholder)</button>
        <button id="openMiniGame">Mini-Game (placeholder)</button>
    </div>
</div>

<!-- Modals placeholders -->
<div id="whiteboardModal" class="modal" style="display:none;">
    <div class="modal-content">
        <h3>Shared Whiteboard (placeholder)</h3>
        <p>Future feature: collaborative canvas here.</p>
        <button onclick="closeWhiteboard()">Close</button>
    </div>
</div>

<div id="miniGameModal" class="modal" style="display:none;">
    <div class="modal-content">
        <h3>Mini-Game (placeholder)</h3>
        <p>Future feature: small multiplayer game.</p>
        <button onclick="closeMiniGame()">Close</button>
    </div>
</div>

</body>
</html>
