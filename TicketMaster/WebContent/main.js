$(document).ready(function(){

    var baseurl = "http://localhost:8080/TicketMasterTest/rest/manifestation/manifestations";



    $.ajax({
		type : 'GET',
		url : baseurl,
		dataType : "json", // data type of response
		success : function(data) {
            var html = '<div class="card-group my-3">';

            for( var i=0; i <data.length; i++) {
                html +=
                    `
                    <div class="card" style="width: 18rem;">
                        <a href="event.html" style="text-decoration: none; color:Black">
                            <img class="card-img-top" src="https://www.thenittygrittyguide.co/wp-content/uploads/2019/05/hanny-naibaho-388579-unsplash-810x540.jpg" alt="Card image cap">
                            <div class="card-body text-light">
                            <h5 class="card-title">` + data[i].name + `<div class="text-muted float-end">` + data[i].dateTime + `</div></h5>
                            
                            <p class="card-text">TREBA UNETI OPIS MANIFESTACIJE</p>
                            <button type="button" class="btn btn-danger">Price: ` + data[i].regularPrice + `€</button>
                            </div>
                        </a>
                    </div>`;

                if( (i+1) % 3 == 0){

                    html += '</div><div class="card-group my-3">';
                }
            }

            html += "</div>";
            $("#cards_container").html(html);
            
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});



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