<#ftl encoding="utf-8"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon"
          href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFzPTtaVLS7029u35BpMoJP-7RdfA8GH3mCD50ge12uD2XXTwi"
          type="image/x-icon">
    <meta charset="UTF-8">
    <title>Electro Boom</title>
    <script src ="js/jquery-ui-1.12.1/external/jquery/jquery.js"></script>
    <script src ="js/jquery-ui-1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="js/jquery-ui-1.12.1/jquery-ui.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src ="js/bootstrap/bootstrap.min.js"></script>
    <script src="js/adding.js"></script>
    <script src="js/deleting.js"></script>
    <script src ="js/getData.js"></script>
    <script src ="js/renderAjax.js"></script>
</head>
<#if user ??>
    <body onload="renderAjax(); renderAnswers(); return false;">
<#else>
    <body>
</#if>
<nav class="navbar navbar-expand-md navbar-light fixed-top bg-light">
    <a class="navbar-brand" href="/main" style="color: darkred; font-size: 2rem;">Electro Boom</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="/main">Catalog <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/contacts">Contacts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">About us</a>
            </li>
            <#if user ??>
            <li class="nav-item dropdown">
                <a id ="loginName" class="nav-link dropdown-toggle" role ="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">${user.name}</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/profile">Profile</a>
                    <a class="dropdown-item" href="" data-toggle="modal" data-target="#myModal2" onclick = "getUserOrders(); return false;">Your orders <span class = "badge badge-dark" id = "amountOfOrders"></span></a>
                    <a class="dropdown-item" href="/contacts" >Messages <span class = "badge badge-dark" id = "answersCount"></span></a>
                    <div class="dropdown-divider"></div>
                    <#if user.role.name == "admin">
                    <a class="dropdown-item" href="/admin" id = "adminpanel">Admin Panel</a>
                    </#if>
                    <a class="dropdown-item" href="/main" onclick="exit()">Exit</a>
                </div>
            </li>
            <li class="nav-item dropdown" style="float:left">
                <a class="nav-link dropdown-toggle" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Cart <span class = "badge badge-dark" id = "amountOfCart"></span>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <div id="cart">
                    </div>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" id="sum"></a>
                    <a class="dropdown-item" data-toggle="modal" data-target="#myModal" onclick="getData(); return false;"> Do order </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" onclick="deleteAllProduct(); return false;">Delete all products</a>
                </div>
            </li>
            <#else> <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
            </li> </#if>
        </ul>
    </div>
</nav>
<div style="height: 120px;"></div>
<div class="container" >
    <div class="row">
        <div class="col-lg-6 col-md-12 col-12">
            <div class="page-header">
                <h1>How to contacts with us?
                    <small></small>
                </h1>
            </div>
            <div class="container">
                <form>
                    <div class="form-group">
                        <label for="exampleFormControlInput1">Email address</label>
                        <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="" value = "<#if user ??>${user.email}</#if>">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlInput1">First name</label>
                        <input type="text" name = "first name" class="form-control" id="exampleFormControlInput2" placeholder="" value = "<#if user ??>${user.first_name}</#if>">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlInput1">Surname</label>
                        <input type="text" name = "surname" class="form-control" id="exampleFormControlInput3" placeholder="" value = "<#if user ??>${user.surname}</#if>">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">Write your letter for us</label>
                        <textarea name = "text" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                    <div id = "messageContact">
                    </div>
                    <#if user ??>
                    <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="sendingMessage(); return false;">Send</button>
                    <#else>
                    <button class="btn btn-lg btn-primary btn-danger" type="submit" onclick="">Please login first</button>
                    </#if>
                </form>
            </div>
            <br>

        </div>
        <#if user ??>
        <div class = "col-12 col-lg-6 col-md-12">
            <h2>
                Answers for you
            </h2>
            <div class="container" id="answers">
                <h3> Messages: ${answers?size}  </h3>
                <ul class="list-group">
                <#list answers as a>
                        <li class="list-group-item">
                            <div class="alert alert-light" role="alert"><h5>1. Your message: ${a.letter} </h5>
                                <h5 id="special">Answer: ${a.answer} </h5>
                                <button type="button" class="btn btn-danger" onclick="deleteAnswer(15); return false;">
                                    Delete
                                </button>
                            </div>
                        </li>
                </#list>
                </ul>
            </div>
        </div>
        </#if>
    </div>
</div>
<br>
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Electro Boom</p>
    </div>
</footer>
<#if user ??>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Your order</h4>
            </div>
            <div class="modal-body" id = "cart2">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="doOrder(); return false;" id ="dobutton">Do</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Your order</h4>
            </div>
            <div class="modal-body" id = "orders">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</#if>
</body>
</html>