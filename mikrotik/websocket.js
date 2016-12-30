var webSocket;
var messages = document.getElementById("messages");
           
function request_access()
{
    $( "#form_medium_phase" ).submit(function( event ) {
        var phone=$("#phone").val();        
        var email=$("#email").val();        
        var customer=$("#customer").val();        
        var command="<make_phone_entry><"+phone+">"+"<"+email+">"+"<"+customer+">";
        webSocket.send(command);
        event.preventDefault();
    });
    
}



function openSocket(){
    // Ensures only one connection is open at a time
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
        writeResponse("WebSocket is already opened.");
        return;
    }
    // Create a new instance of the websocket
    webSocket = new WebSocket("ws://localhost:8080/WIFITicketSystem/echo");
    
    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function(event){
        // For reasons I can't determine, onopen gets called twice
        // and the first time event.data is undefined.
        // Leave a comment if you know the answer.
        if(event.data === undefined) return;        
    };
    
    webSocket.onmessage = function(event){
        parse_message(event.data);
    };
    
    webSocket.onclose = function(event){
        webSocket.close();
    };
}

function get_token_code()
{
    webSocket.send("<get_token_code>");
}

function parse_message(command){
    
    var obj=JSON.parse(command);
    
    //Begin and insert the base information name, phone and email    
    if(obj.command==="ticket_made")
    {
        $("#first_phase").hide();        
        $("#medium_phase").show();
        $("#pin_phase").hide();
        $("#final_phase").hide();        
    }
    
    //waiting for phone call and servlet answer to check the pin
    if(obj.command==="confirm_phone")
    {
        $("#first_phase").hide();
        $("#medium_phase").hide();
        $("#pin_phase").show();
        $("#final_phase").hide();        
        $("#pin_code").append(obj.password_ticket);
    }
    
    //pin confirmed and ticket activation
    if(obj.command==="activate_ticket")
    {
        $("#first_phase").hide();
        $("#medium_phase").hide();
        $("#pin_phase").hide();        
        $("#final_phase").show();
        $("#username_ticket").text("Username: "+obj.username_ticket);
        $("#password_ticket").text("Password: "+obj.password_ticket);
        $("#webpage").attr("href","http://"+obj.webpage);        
        $("#username").val(obj.username_ticket);
        $("#password").val(obj.password_ticket);        
    }
}

