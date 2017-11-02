function searchthing() {
    var searchContent = $(searchcontent).val();
    $.ajax({
        type: "POST",
        url: "dosearch",
        data:{
            searchContent:searchContent
        },
        dataType:"json",
        success:function (data) {
            // alert(data.result);
            $("#answerdetails").empty();
            $("#answerdetails").append("<p>结果是："+data.result+"<p>");
        }
    });
}