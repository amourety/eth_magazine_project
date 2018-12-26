<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon"
          href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFzPTtaVLS7029u35BpMoJP-7RdfA8GH3mCD50ge12uD2XXTwi"
          type="image/x-icon">
    <meta charset="UTF-8">
    <title>Registration</title>
    <script src="http://code.jquery.com/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <script src ="/js/bootstrap/bootstrap.min.js"></script>
    <script src ="/js/registration.js"></script>
</head>
<body>
<footer class="py-5 bg-light">
    <div class="container">
        <h1 style="text-align: center"> Electro Boom </h1>
    </div>
</footer>

<div class="container">

    <form class="form-signin" id="fileForm" role="form">
        <h2 class="form-signin-heading">Registration</h2>
        <div class="form-group">
            <label>Username:  <small>This will be your login user name</small> </label>
            <input class="form-control" type="text" name="username" id = "username" minlength="8" maxlength="16" onkeyup = "Validate(this)" placeholder="" required/>
            <div id="errLast"></div>
        </div>
        <div class="form-group">
            <label> First name: </label>
            <input class="form-control" type="text" name="first_name" id = "first_name" onkeyup = "Validate(this)" required/>
            <div id="errFirst"></div>
        </div>

        <div class="form-group">
            <label> Last name: </label>
            <input class="form-control" type="text" name="surname" id = "surname" onkeyup = "Validate(this)" required/>
            <div id="errLast"></div>
        </div>
        <div class="form-group">
            <label> Email Address: </label>
            <input class="form-control" required type="text" name="email" id = "email"  onchange="email_validate(this.value);" />
            <div class="status" id="status"></div>
        </div>
        <div class="form-group">
            <label> Password: </label>
            <input required name="password" type="password" class="form-control inputpass" minlength="8" maxlength="16"  id="pass1" />
            <label> Password Confirm: </label>
            <input required name="password" type="password" class="form-control inputpass" minlength="8" maxlength="16" placeholder="Enter again to validate"  id="pass2" onkeyup="checkPass(); return false;" />
            <span id="confirmMessage" class="confirmMessage"></span>
        </div>
        <div id = "messageContact">
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="doRegist(); return false;" id = "regButton">Registration</button>
        <a href="/login">Back to sign in</a>
    </form>

</div>
<div style="height: 100px"></div>
<footer class="py-5 bg-light">
    <div class="container">
    </div>
</footer>
</body>
</html>