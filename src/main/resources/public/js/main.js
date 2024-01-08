$(document).ready(function(){
    $("#tweet").click(function(){
        const display = $("#make-post").css('display');
        if(display === 'none'){
            $("#make-post").show();
            $('body').css('background-color', 'rgba(0, 0, 0, 0.5)');
        }else{
            $("#make-post").hide();
            $('body').css('background-color', 'white');
        }
    });
});