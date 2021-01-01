$(document).ready(function(){

    let usernameInput   = $("input[id=username]").val();
    let passwordInput   = $("[type=password]").val();
    let firstNameInput  = $("input[id=firstname]").val();
    let lastNameInput   = $("input[id=lastname]").val();
    let genderInput     = $("input[id=gender]").val();
    

    let form = $("form");
    form.submit(function(event){

        //this gets all input types
        let allFields = $(':input');

        let dayInput        = $(allFields[5]).val();
        let monthInput      = $(allFields[6]).val();
        let yearInput       = $(allFields[7]).val();

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


        //check if date of birth is correct
        if(dayInput < "1") { pass = false};

        switch(monthInput) {
            case "1": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "2": {
                //if divisible by 4 it is a leap year
                if(yearInput % 4 == 0) {
                    if(dayInput > "29") {pass = false};
                }
                else {
                    if(dayInput > "28") {pass = false};
                };
                break;
            };
            case "3": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "4": {
                if(dayInput > "30") {pass = false};
                break;
            };
            case "5": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "6": {
                if(dayInput > "30") {pass = false};
                break;
            };
            case "7": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "8": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "9": {
                if(dayInput > "30") {pass = false};
                break;
            };
            case "10": {
                if(dayInput > "31") {pass = false};
                break;
            };
            case "11": {
                if(dayInput > "30") {pass = false};
                break;
            };
            case "12": {
                if(dayInput > "31") {pass = false};
                break;
            };
            default:
                pass = false;
        }

        if(pass == false) {
            alert("Date of birth is not valid!");
            event.preventDefault();
        };

        if(pass == true) {
            alert("Registration was succesfull!");
        };
    });


    //color the field white if it is clicked
    $(":input").click(function (e) {
        if (e.target && $(e.target).is('button') == false && $(e.target).css("color") != "white") {
            $(e.target).css("background-color", "white");
        }
    });
});
