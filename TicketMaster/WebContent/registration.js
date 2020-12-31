$(document).ready(function(){

    let usernameInput   = $("input[id=username]").val();
    let passwordInput   = $("[type=password]").val();
    let firstNameInput  = $("input[id=firstname]").val();
    let lastNameInput   = $("input[id=lastname]").val();
    let genderInput     = $("input[id=gender]").val();
    let dayInput        = $("input[id=day]").val();
    let monthInput      = $("input[id=month]").val();
    let yearInput       = $("input[id=year]").val();

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

        if(pass == false){
            alert("All fields should have data!");
            event.preventDefault();
        }
        else {
            alert("Registration was succesfull!");
        }

    });


    $(":input").click(function (e) {
        if (e.target && $(e.target).is('button') == false && $(e.target).css("color") != "white") {
            $(e.target).css("background-color", "white");
        }
    });
});