<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    Integer userId = (Integer) session.getAttribute("userId");
    Integer groupId = null;
    try { groupId = Integer.parseInt(request.getParameter("groupId")); } catch (Exception ignored) {}
    if (username == null || userId == null || groupId == null) {
        response.sendRedirect("home.jsp");
        return;
    }
    String encUser = java.net.URLEncoder.encode(username, "UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Chat - CollabChat</title>
    <link rel="stylesheet" href="styles.css" />
    <script>
        // Safely emit server-side values into JS using URL-encoding to avoid injection
        const CURRENT_USER = { id: <%= userId %>, name: decodeURIComponent("<%= encUser %>") };
        const CURRENT_GROUP = <%= groupId %>;
    </script>
</head>
<body>
<div class="topbar">
    <div class="brand">CollabChat</div>
    <div class="user">Group: <strong id="groupName">#<%= groupId %></strong> <a href="home.jsp">Back</a></div>
</div>

<div class="chat-container">
    <div id="messages" class="messages"></div>
    <form id="msgForm" class="msg-form">
        <input type="text" id="msgInput" placeholder="Type a message..." autocomplete="off" required />
        <button type="submit">Send</button>
    </form>
</div>

    <script src="chat.js"></script>
</body>
</html>
