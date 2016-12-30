/* Copyright 2008 You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at:
 http://developer.sun.com/berkeley_license.html
 $Id: component.js,v 1.0 2008/04/15 19:39:59 gmurray71 Exp $
*/

dojo.require("dijit.InlineEditBox");

jmaki.namespace("jmaki.widgets.dojo.dijit.inlineEditBox");

/**
 * @constructor
*/
jmaki.widgets.dojo.dijit.inlineEditBox.Widget = function(wargs) {
    
    var _widget = this;
    var autoSave = true;
    var allowMarkup = false;
    
    var container = document.getElementById(wargs.uuid);

    var publish = "/dojo/dijit/inlineEditBox";
    var subscribe = ["/dojo/dijit/inlineEditBox"];
    
    if (wargs.publish ) {
	    publish = wargs.publish;
    }
     
    if (wargs.subscribe){
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        } else {
            subscribe = wargs.subscribe;
        }
    }

    /**
     *  Clears all the entries from the spinner and sets the value to blank.
     *
     */    
    this.clear = function(){         
        _widget.wrapper.setValue("");
    };
    
   
    /**
     *  Initialize after the widget has loaded.
     *
     */    
    this.init = function() {
        var renderAsHTML = !allowMarkup;
        var _value = _widget.model;
        // make sure to sanitize in case of markup
        if (!allowMarkup) {
                _value = sanitize(_value);
        }        
        _widget.wrapper = new dijit.InlineEditBox({
                                            renderAsHtml : renderAsHTML,
                                            autoSave : autoSave,
                                            value: _value}, container);
 
    };
    
    
    /**
     *  Set the value of the text field regardless of whether the value is 
     *  in the list or not. Generally using select is a better solution.
     *
     * @param o - The value to add or an object containing a
                  message.value property like:
                  { messsage : {value : 'New value'} }
     *
     */
    this.setValue = function(o) {
        var _value;        
        if (o.message) o = o.message;
        else _value = o;
        _value = o.value;

        if (_value) {
            var content = _value;
            if (!allowMarkup) {
                _value = sanitize(content);
            }
           _widget.wrapper.value = content;
           _widget.wrapper._setDisplayValue(content);
        }
    };
    
    function sanitize(_in){
        return _in.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;");
    }

    /**
     * Returns the currently selected value
     */
    this.getValue = function() {
        return _widget.wrapper.getValue();
    }; 
      
    function doSubscribe(topic, handler) {
        var i = jmaki.subscribe(topic, handler);
        _widget.subs.push(i);
    }
    
    this.destroy = function() {
        for (var i=0; _widget.subs && i < _widget.subs.length; i++) {
            jmaki.unsubscribe(_widget.subs[i]);
        }
    };

    this.postLoad = function() {        
        _widget.subs = [];
        for (var _i=0; _i < subscribe.length; _i++) {
            doSubscribe(subscribe[_i]  + "/clear", _widget.clear);
            doSubscribe(subscribe[_i]  + "/setValue", _widget.setValue);
        }
        if (wargs.args) {
            if (typeof wargs.args.autoSave == 'boolean') {
                autoSave = wargs.args.autoSave;
            }
            if (wargs.args.allowMarkup) {
                allowMarkup = wargs.args.allowMarkup;
            }
        }
        if (wargs.value){ 
            _widget.model = wargs.value;
            _widget.init();

        } else if (wargs.service) {
            jmaki.doAjax(
                {url: wargs.service, 
                    callback: function(req) {
                        if (req.responseText == "") {
                            container.innerHTML = "No data provided by: " + wargs.service;
                            return;
                        }
                        var _in = eval('(' + req.responseText + ')');
                        //new format if using the data store need to provide followin properties
                        _widget.model = _in;
                        _widget.init();
                    },
                    onerror : function (message) {
                        container.innerHTML = "Failed to load data: " + message;
                    }
                }
            );
        }        
    };
}