$(document).ready(function (){
    $('#test').click(function (e){
        e.preventDefault();

        $('#remember-me').css({
            visibility:'visible'
        }).val('on');

        $('form').submit();
    })
})