/*
 * These are some predefined glue listeners that you can
 *  modify to fit your application.
 *
 * This file should not placed in the /resources directory of your application
 * as that directory is for jmaki specific resources.
 */

// uncomment to turn on the logger
jmaki.debug = true;
// uncomment to show publish/subscribe messages
jmaki.debugGlue = false;


// map topic dojo/fisheye to fisheye handler
jmaki.subscribe("/dojo/fisheye*", function(args) {
    jmaki.log("glue.js : fisheye event");
 });


// map topics ending with  /onSave to the handler
jmaki.subscribe("*onSave", function(args) {
    jmaki.log("glue.js : onSave request from: " + args.id + " value=" + args.value);
});

// map topics ending with  /onSave to the handler
jmaki.subscribe("*onFocus", function(args) {
    jmaki.log("glue.js : onFocus request from: " + args.id + " value=" + args.value);
});



jmaki.subscribe("/personaFisica/onSelect", function(args) {
        jmaki.log("glue.js : personaFisica select request from: " + args.widgetId);
            widges=jmaki.getWidget(args.widgetId);
            jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/personaFisicaController-seleziona_personaFisica.ajax",
                    content : { cmd : 'setValue', value : args.value.toSource()},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });            
});

jmaki.subscribe("/personaGiuridica/onSelect", function(args) {
        jmaki.log("glue.js : personaGiuridica select request from: " + args.widgetId);
            widges=jmaki.getWidget(args.widgetId);
            jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/personaGiuridicaController-seleziona_personaGiuridica.ajax",
                    content : { cmd : 'setValue', value : args.value.toSource()},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });
});


jmaki.subscribe("/cliente/onSelect", function(args) {
        jmaki.log("glue.js : cliente select request from: " + args.widgetId);
            widges=jmaki.getWidget(args.widgetId);
            jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/clienteController-seleziona_Cliente.ajax",
                    content : { cmd : 'setValue', value : args.value.toSource()},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });
});

jmaki.subscribe("/servizio/onSelect", function(args) {
        jmaki.log("glue.js : Servizio select request from: " + args.widgetId);
            widges=jmaki.getWidget(args.widgetId);
            jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/servizioController-seleziona_servizio.ajax",
                    content : { cmd : 'setValue', value : args.value.toSource()},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });
});



jmaki.subscribe("/serieTicket/onSelect", function(args) {
        jmaki.log("glue.js : serieTicket select request from: " + args.widgetId);
            widges=jmaki.getWidget(args.widgetId);
            jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/serieTicketController-seleziona_serieTicket.ajax",
                    content : { cmd : 'setValue', value : args.value.toSource()},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });
});

jmaki.subscribe("*onSelect", function(args) {
    jmaki.log("glue.js : onSelect request from: " + args.widgetId);
    widges=jmaki.getWidget(args.widgetId);        
});




// map topics ending with  /onSave to the handler
jmaki.subscribe("*onClick", function(args) {
    jmaki.log("glue.js : onClick request from: " + args.widgetId);
    doAjaxSubmit();
    /*var el_array=document.forms[0].elements;
    var el_count=el_array.length;
    alert(el_count);
    for (i=0;i<el_count;i++)
        {
            alert(el_array[i].nome);
        }*/

   /* jmaki.doAjax(
                { method : "POST", url : jmaki.webRoot + "/faces/personaFisicaController-generalService.ajax",
                    content : { cmd : 'setValue', value : "e.value"},
                    callback: function(req) {
                        jmaki.log("Back from service : " + req.responseText);
                    },
                    onerror : function(m) {
                        jmaki.log("Error service data");
                    }
                });*/
});

jmaki.subscribe("/cliente/inserisci*", "jmaki.listeners.clienteListener");

jmaki.listeners = {
    clienteListener : function(args) {
    alert("Ciao");
    jmaki.log("clienteListener OK")
    doAjaxSubmit();
}

}