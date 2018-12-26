<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon"
          href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFzPTtaVLS7029u35BpMoJP-7RdfA8GH3mCD50ge12uD2XXTwi"
          type="image/x-icon">
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="http://code.jquery.com/jquery.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src ="../js/bootstrap/bootstrap.min.js"></script>
</head>
<body>
<footer class="py-5 bg-light">
    <div class="container">
        <h1 style="text-align: center"> Electro Boom </h1>
    </div>
</footer>

<div class="container">

    <form class="form-signin" role="form" method="post" action="login">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input name="name" type="text" class="form-control" placeholder="Login" required="" autofocus="">
        <br>
        <input name="password" type="password" class="form-control" placeholder="Password" required="">
        <label class="checkbox">
            <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <a href="/registration">Registration</a>
    </form>

</div>
<div style="height: 100px"></div>
<footer class="py-5 bg-light">
    <div class="container">
    </div>
</footer>

</body>
</html>