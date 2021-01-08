$(document).ready(function(){

	let str = localStorage.getItem("clickedID");
	str = str.replace(/_/g,'%20');

    var baseurl = "http://localhost:8080/TicketMaster/rest/manifestation/" + str;
            
    $.ajax({
		type : 'GET',
		url : baseurl,
		dataType : "json",
		async: false,
		success : function(data) {

            var ImageHtml = `
            <div class="bg-image "
            style="
            background-image: url('`+ data.eventPoster.replace(/(\r\n|\n|\r)/gm, "") +`');
            background-repeat: no-repeat;
            background-size: cover;">
    
                <!-- First Image Mask-->
                <div class="mask" style="background-color: rgba(0, 0, 0, 0.5);">
                    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                </div>
    
                <!-- Gradient Image Mask-->
                <div class="mask" style="background: linear-gradient(0deg, rgba(0, 0, 0, 1), rgba(0, 0, 0, 0.5) 100%);">
                    <div class="container-md d-flex">
                        <div class="col">
                            <h1 class="container-sm mb-5 float-start">` + data.name+ `</h1>
                        </div>
                        <div class="col">
                            <h1>
                                <div class="text-muted float-end">` + data.dateTime + `
                                    <button type="button" class="btn btn-danger"><h3>Price: ` + data.regularPrice + `€ <br>Buy</h3></button>
                                </div>
                            </h1>
                        </div>
    
                    </div>
                </div>
            </div>
            `;

            $("#Background_image").html(ImageHtml);


            var AboutHtml = "Rave in the undergrounds of Novi Sad.";

            $("#About").html(AboutHtml);
            
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
   
});
