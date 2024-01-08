$(document).ready(function(){
    $("#tweet").click(function(){
        
        const display = $("#make-post").css('display');
        if(display === 'none'){
            console.log(2)

            $(".overlay").show();
            $("#make-post").show();
        }else{
            $(".overlay").hide();
            $("#make-post").hide();
        }
    });
});