<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Register - CollabChat</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="container">
    <h1>Register</h1>
    <form method="post" action="register">
        <label>Username</label>
        <input type="text" name="username" required />
        <label>Password</label>
        <input type="password" name="password" required />
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login</a></p>
    <div class="note">Passwords are stored using BCrypt hashing.</div>
</div>
</body>
</html>
