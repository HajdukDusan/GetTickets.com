
function encodeImageFileUrl(){
    fileSelect = document.getElementById("fileUp").files;
    if(fileSelect.length >0){
        var fileSelect = fileSelect[0];
        var fileReader = new FileReader();



        fileReader.onload = function(FileLoadEvent){
            var srcData = FileLoadEvent.target.result;
            document.getElementById("imageFile").src = srcData;
            //document.getElementById("Base64Data").innerHTML = srcData;
        
        }
        fileReader.readAsDataURL(fileSelect);
    }
}

window.onload = function(){
    var fileUp = document.getElementById("fileUp");
    if(fileUp){
        fileUp.addEventListener("change",function(){
            encodeImageFileUrl();
    })
    }
}

$(document).ready(function(){
    let form = $("form");
    form.submit(function(event){

        //this gets all input types
        let allFields = $(':input');

        let name            = $(allFields[0]).val();
        //let desc            = $(allFields[1]).val();
        //let type            = $(allFields[2]).val();
        let numOfSeats      = $(allFields[3]).val();
        let hour            = $(allFields[4]).val();
        let minute          = $(allFields[5]).val();
        let day             = $(allFields[6]).val();
        let month           = $(allFields[7]).val();
        let year            = $(allFields[8]).val();
        let price           = $(allFields[9]).val();
        let image           = $(allFields[10]).val();



        //check if all fields are not empty
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
            return;
        };



        //check if seats are a num
        if(isNaN(numOfSeats)) {
            alert("Number of seats is not a number!");
            event.preventDefault();
            return;
        }

        //check if time and Date is a num
        if(isNaN(hour) || isNaN(minute) || isNaN(day) || isNaN(month) || isNaN(year)) {
            alert("Time and Date is not a number!");
            event.preventDefault();
            return;
        }

        //check if time is correct
        if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            alert("Time is invalid!");
            event.preventDefault();
            return;
        }

        //check if date of the event is correct
        if(day < "1") { pass = false};

        switch(month) {
            case "1": {
                if(day > "31") {pass = false};
                break;
            };
            case "2": {
                //if divisible by 4 it is a leap year
                if(year % 4 == 0) {
                    if(day > "29") {pass = false};
                }
                else {
                    if(day > "28") {pass = false};
                };
                break;
            };
            case "3": {
                if(day > "31") {pass = false};
                break;
            };
            case "4": {
                if(day > "30") {pass = false};
                break;
            };
            case "5": {
                if(day > "31") {pass = false};
                break;
            };
            case "6": {
                if(day > "30") {pass = false};
                break;
            };
            case "7": {
                if(day > "31") {pass = false};
                break;
            };
            case "8": {
                if(day > "31") {pass = false};
                break;
            };
            case "9": {
                if(day > "30") {pass = false};
                break;
            };
            case "10": {
                if(day > "31") {pass = false};
                break;
            };
            case "11": {
                if(day > "30") {pass = false};
                break;
            };
            case "12": {
                if(day > "31") {pass = false};
                break;
            };
            default:
                pass = false;
        }

        if(pass == false) {
            alert("Date of the event is not valid!");
            event.preventDefault();
        };


        //check if price is a num
        if(isNaN(price)) {
            alert("Price is not a number!");
            event.preventDefault();
            return;
        }

        //check if poster is uploaded
        if(srcData.length < 20) {
            alert("Upload an image!");
            event.preventDefault();
            return;
        }

        //if everything is ok
        if(pass == true) {
            alert("Event was created!");
        };
    });
});
