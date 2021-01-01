$(document).ready(function(){

    let usernameInput   = $("input[id=username]").val();
    let passwordInput   = $("[type=password]").val();

    let form = $("form");
    form.submit(function(event){

        //this gets all input types
        let allFields = $(':input');

        let pass = true;

        for(let field of allFields) {

            //we exclude submit button from coloring to red
            if($(field).is('button') == false) {
                if($(field).val() == '') {
                    $(field).css("background-color", "red");
                    pass = false;
                };
            };
        };    

    

        //check if all fields are not empty
        if(pass == false){
            alert("All fields should have data!");
            event.preventDefault();
            return;
        };


        if(pass == true) {
            alert("Login was succesfull!");
        };
    });


    //color the field white if it is clicked
    $(":input").click(function (e) {
        if (e.target && $(e.target).is('button') == false && $(e.target).css("color") != "white") {
            $(e.target).css("background-color", "white");
        }
    });
});
