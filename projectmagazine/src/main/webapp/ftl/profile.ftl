<#ftl encoding="utf-8"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon"
          href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFzPTtaVLS7029u35BpMoJP-7RdfA8GH3mCD50ge12uD2XXTwi"
          type="image/x-icon">
    <meta charset="UTF-8">
    <title>Electro Boom</title>
    <script src ="/js/jquery-ui-1.12.1/external/jquery/jquery.js"></script>
    <script src ="/js/jquery-ui-1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/jquery-ui-1.12.1/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src ="/js/bootstrap/bootstrap.min.js"></script>
    <script src="/js/adding.js"></script>
    <script src="/js/deleting.js"></script>
    <script src ="/js/getData.js"></script>
    <script src ="/js/renderAjax.js"></script>
</head>
<#if user ??>
    <body onload="renderAjax(); return false;">
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
            <li class="nav-item">
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
<#if user ??>
<div class="container emp-profile" onload="getImg()">
        <div class="row">
            <div class="col-md-6 col-lg-3 col-12">
                <div class="profile-img" style="width: 250px;
  height: 300px;
  border-style: solid;">
                    <img style = "width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: 0 0;" src="http://localhost:8080/img" alt=""/>
                </div>
                <form action="/profile" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" />
                    <input type="submit"/>
                </form>
            </div>
            <div class="col-md-6 col-lg-4 col-12">
                <div class="row">
                    <div class="col-md-12">
                        <div class="profile-head">
                            <h2>
                                ${user.first_name} ${user.surname}
                            </h2>
                            <h4>
                                Customer
                            </h4>
                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                                       aria-controls="home" aria-selected="true">About</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                                       aria-controls="profile" aria-selected="false">Timeline</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="col-md-6">
                            <div class="tab-content profile-tab" id="myTabContent">
                                <div class="tab-pane fade show active" id="home" role="tabpanel"
                                     aria-labelledby="home-tab">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>Username</label>
                                        </div>
                                        <div class="col-md-6">
                                            <p>${user.name}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>Name</label>
                                        </div>
                                        <div class="col-md-6">
                                            <p>${user.first_name}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>Surname</label>
                                        </div>
                                        <div class="col-md-6">
                                            <p>${user.surname}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>Email</label>
                                        </div>
                                        <div class="col-md-6">
                                            <p>${user.email}</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>Orders</label>
                                        </div>
                                        <div class="col-md-6">
                                            <p>//todo </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
        </div>
</div>
<#else>
    <h3 style="text-align: center">Sorry, you cant see your profile, you need to log in, click</h3>
    <button class="btn btn-lg btn-primary btn-block" href="/login">Sign in</button>
</#if>
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