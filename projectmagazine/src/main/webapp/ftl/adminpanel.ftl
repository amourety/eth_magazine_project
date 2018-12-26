<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
    <script src ="js/jquery-ui-1.12.1/external/jquery/jquery.js"></script>
    <script src ="js/jquery-ui-1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="js/jquery-ui-1.12.1/jquery-ui.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src ="js/admincommand.js"></script>
    <script src ="../js/bootstrap/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/style.css">
</head>
<body onload = "getMessages()">
<footer class="py-5 bg-light">
    <div class="container">
        <h1 style="text-align: center"> ADMIN PANEL </h1>
        <br>
        <div>
            <p style="text-align: center"><a href="/main">Electro Boom</a></p>
        </div>
    </div>
</footer>

<div style="height: 100px"></div>

<div class="container">
    <div class="row">
        <div class="col-lg-3 col-md-6 col-12">
            <h2>Products</h2>
            <button onclick = "getCatalog()" class="btn btn-info btn-lg" type="button" data-toggle="modal" data-target="#myModal1">List of products</button>
            <div style="height: 20px"></div>
            <div id="myModal1" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <h4 class="modal-title" style="text-align: center">List of products</h4>
                        <div class="modal-body"><div id = "listOfProducts"></div></div>
                    </div>
                </div>
            </div>
            <button class="btn btn-info btn-lg" type="button" data-toggle="modal" data-target="#myModal2">
                Add product to list</button>
            <div style="height: 20px"></div>
            <div id="myModal2" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <h4 class="modal-title" style="text-align: center">
                            Add product</h4>
                        <div class="modal-body">
                            <form>
                            <div class="form-group row">
                                <label for="addProductName" class="col-sm-5 col-form-label col-form-label-sm">Product's name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control form-control-sm" id="addProductName"
                                           placeholder="">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="addProductPrice" class="col-sm-5 col-form-label col-form-label-sm">Product's price</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control form-control-sm" id="addProductPrice"
                                           placeholder="">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="addProductImg" class="col-sm-5 col-form-label col-form-label-sm">Link img</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control form-control-sm" id="addProductImg"
                                           placeholder="images/...">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block" onclick="addProductIntoList()">Add product</button>
                        </form></div>
                        <div class="modal-footer"><button class="btn btn-default" type="button" data-dismiss="modal">Close</button></div>
                    </div>
                </div>
            </div>
            <br>
        </div>
        <div class="col-lg-3 col-md-6 col-12">
            <h2>Messages <span class = "badge badge-dark" id = "messagesCount"></span></h2>
            <button class="btn btn-info btn-lg" type="button" data-toggle="modal" data-target="#myModal3">Messages</button>
            <div style="height: 20px"></div>
            <div id="myModal3" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <h4 class="modal-title" style="text-align: center">Messages</h4>
                        <div class="modal-body"><div id = "answersList"></div></div>
                        <div class="modal-footer"><button class="btn btn-default" type="button" data-dismiss="modal">Close</button></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 col-12">
            <h2>Users</h2>
            <button onclick = "getUsers()" class="btn btn-info btn-lg" type="button" data-toggle="modal" data-target="#myModal4">Users</button>
            <div style="height: 20px"></div>
            <div id="myModal4" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <h4 class="modal-title" style="text-align: center">Users</h4>
                        <div class="modal-body"><div id = "listUsers"></div></div>
                        <div class="modal-footer"><button class="btn btn-default" type="button" data-dismiss="modal">Close</button></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="height: 100px"></div>
<footer class="py-5 bg-light">
    <div class="container">
    </div>
</footer>


</body>
</html>
