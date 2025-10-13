<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Login - CollabChat</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <form method="post" action="login">
        <label>Username</label>
        <input type="text" name="username" required />
        <label>Password</label>
        <input type="password" name="password" required />
        <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <a href="register.jsp">Register</a></p>
</div>
</body>
</html>
