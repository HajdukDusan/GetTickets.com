$(document).ready(function(){

    //Typing In Search Event
    $('input').on('keyup', function() {

        let text = $(this).val().toLowerCase();

        // hide all cards
        $('.card').hide();

        // search card-title and card-text for text and show the cards
        $('.card .card-title:contains("'+text+'"), .card-text:contains("'+text+'")').closest('.card').show();
   });

   
});


//We search by case-insensitive
$.expr[":"].contains = $.expr.createPseudo(function(arg) {
    return function( elem ) {
     return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
});