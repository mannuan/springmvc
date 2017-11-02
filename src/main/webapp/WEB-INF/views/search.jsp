<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>搜索页面</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/js/index.js"></script>
    <style>
        .row2{
            margin-left: 5px;
        }
        .box1,.box2{
            width:100%;
            height: 300px;
            cursor: pointer;
            background-color: #c0f897;
            margin-top: 10px;
        }
        .col-md-4,.col-md-6{
            padding-right:0px;
            padding-left: 0px;
        }
    </style>
</head>
<body>
    <div id="up">
        <div style="padding:20px 30px 10px">
            <form class="bs-example bs-example-form" role="form">
                <div class="row">
                    <div class="col-md-4" style="font-family: Arial;font-size: 32px">地主搜索</div>
                    <div class="col-md-4">
                        <div class="input-group">
                            <input id="search" type="text" class="form-control" style="height:60px;">
                            <span class="input-group-btn" >
                                <button class="btn btn-default" type="button" id="searchBtn"  onclick=searchFun() style="height:60px;width:80px;font-size: 32px">Go!</button>
                            </span>
                        </div>
                    </div>
                    <div class="col-md-4"></div>
                </div>
            </form>
        </div>
    </div>

    <div id="down">
        <div class="row">
            <div id="left" class="col-md-6">
                <div class="row1">
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                    <div class="box1">

                    </div>
                </div>
            </div>
            <div id="right" class="col-md-6">
                <div class="row2">
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                    <div class="box2">

                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>