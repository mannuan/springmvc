<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>搜索页面</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/index.js"></script>
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<div class="container-fluid">
    <div class="searchBox">
        <div class="row">
            <div class="row" id="searchdiv">
                <div class="col-md-1 col-sm-1 col-xs-1" id="logo">河长搜索</div>
                <div class="col-md-4 col-sm-4 col-xs-4">
                    <div class="input-group" id="inputBox">
                        <input id="search" type="text" class="form-control searchInput">
                        <span class="input-group-btn">
                                <button class="btn btn-default searchdiv" type="button" id="searchBtn"
                                        onclick="searchFun()">Go!</button>
                            </span>
                    </div>
                </div>
                <div class="col-md-7 col-sm-7 col-xs-7"></div>
            </div>
        </div>
    </div>
    <div id="kindBox">
        <div class="row" id="kinddiv">
            <div class="col-md-1 col-sm-1 col-xs-1"></div>
            <div class="col-md-3 col-sm-3 col-xs-3" id="kinds">
                <div class="row">
                    <div class="col-md-4 col-sm-4 col-xs-4 kind"><a href="javascript:void(0)" id="weixin"
                                                                    onclick="typeFun(this.id)">微信</a></div>
                    <div class="col-md-4 col-sm-4 col-xs-4 kind"><a href="javascript:void(0)" id="paper"
                                                                    onclick="typeFun(this.id)">论文</a></div>
                </div>
            </div>
        </div>
        <div id="showBox">
            <div class="row">
                <div class="col-md-1 col-sm-1 col-xs-1"></div>
                <div class="col-md-10 col-sm-10 col-xs-10">
                    <table class="table table-striped">
                        <tbody class="showBody">

                        <%--<tr class="weixin">--%>
                        <%--<td class="titleTd"><a href="#" class="titleA">baidu</a></td>--%>
                        <%--</tr>--%>
                        <%--<tr class="weixin">--%>
                        <%--<td class="content">这是环境修复论坛推送的第 384 篇文章。谢谢（欢迎）转发分享。 导语： 普查对象为中华人民共和国境内有污染源的单位和个体经营户。 来源：--%>
                        <%--中华人民共和国中央人民政府 各省、自治区、直辖市人民政府，国务院各部委、各直属机构： 《第二次全国污染源普查方案》已经国务院同意，现印发给你们，请认真组织实施。--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        <%--<tr class="lunwen">--%>
                        <%--<td class="titleTd"><a href="#" class="titleA">baidu</a></td>--%>
                        <%--</tr>--%>
                        <%--<tr class="lunwen">--%>
                        <%--<td class="content">Bootstrap是Twitter推出的一个用于前端开发的开源工具包。它由Twitter的设计师Mark Otto和Jacob--%>
                        <%--Thornton合作开发,是一个CSS/HTML框架。目前,Bootstrap最新版本为3.0 。...--%>
                        <%--www.bootcss.com/ - 百度快照--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        <%--<tr class="tieba">--%>
                        <%--<td class="titleTd"><a href="#" class="titleA">baidu</a></td>--%>
                        <%--</tr>--%>
                        <%--<tr class="tieba">--%>
                        <%--<td class="content">Bootstrap是abc推出的一个用于前端开发的开源工具包。它由Twitter的设计师Mark Otto和Jacob--%>
                        <%--Thornton合作开发,是一个CSS/HTML框架。目前,Bootstrap最新版本为3.0 。...--%>
                        <%--www.bootcss.com/ - 百度快照--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        </tbody>
                    </table>

                </div>
                <div class="col-md-1 col-sm-1 col-xs-1"></div>
            </div>
        </div>
    </div>

    <div class="container article" style="display:none;">
        <div class="contentArticle" style="font-size:18px">
            <div class="articleTitle">

            </div>
            <div class="articleDiv">
                     <div class="articleContent" style="font-size:18px">

                     </div>
            </div>
            <div class="returnPage">
                <a href="#" onclick="returnPage()">返回上一页</a>
            </div>
        </div>
    </div>

    <div class="container lunwenPaper" style="display:none">
        <div class="lunwenArticle" style="font-size:18px">
            <div class="lunwenTitle">

            </div>
            <div class="lunwenAuthor">

            </div>
            <div class="lunwenAbstract">

            </div>
            <div class="lunwenUrl">
                <a href="#" class="lunwenA" target="_blank">点击下载论文</a>

            </div>
            <div class="luwenPath">
                <a href="#" class="lunwenFilePath" target="_blank">点击查看详情</a>

            </div>
            <div class="lunwenUnit">

            </div>
        </div>
        <div class="returnPage">
            <a href="#" onclick="returnPage()">返回上一页</a>
        </div>
    </div>

</div>
</body>

<script type="text/javascript">
    $(function () {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                searchFun();
            }
        });
    })
</script>
</html>