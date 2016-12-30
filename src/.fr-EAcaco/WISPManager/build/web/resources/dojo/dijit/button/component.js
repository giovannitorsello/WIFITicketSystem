/* Copyright 2008 You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at:
 http://developer.sun.com/berkeley_license.html
 $Id: component.js,v 1.0 2008/04/15 19:39:59 gmurray71 Exp $
*/
dojo.require("dojo.parser");
dojo.require("dijit.form.Button");
dojo.require("dijit.Menu");


jmaki.namespace("jmaki.widgets.dojo.dijit.button");

/**
* Dojo Dijit Button Widget
* @author Sloan C. Bowman <sloanb@sginnovations.com>
* @see http://developer.yahoo.com/yui/button/
8 @version 1.0.2
*/
jmaki.widgets.dojo.dijit.button.Widget = function(wargs) {
    
    var _widget = this;
    var uuid = wargs.uuid;
    var container = document.getElementById(wargs.uuid);
    var publish = "/dojo/dijit/button";
    var subscribe = [];
    var cfg;    // The default configuration
    var value;
    
    if (wargs.publish ) {
	    publish = wargs.publish;
    }
    
    
    // Check to see if the user overides the subscriptions
    if (wargs.subscribe){
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        } else {
            subscribe = wargs.subscribe;
        }
    }
    
    
    var counter = 0;
    // Generate the unique key
    function genKey(){
        return wargs.uuid + "_item_" + counter++;
    };
    
    /**
     * The default event handler for buttons
     * @param e Object representing the caller and action to be performed
     */
    this.onClick = function(e) {
        var _targetId;
        var _value = wargs.value;
        if (_value.id) _targetId = _value.id;
        var _action;
        if (_value.action){
            _action =_value.action;
        }
        jmaki.processActions({ topic : publish, 
                             widgetId : wargs.uuid,
                             action : _action,
                             value : _value,
                             targetId : _targetId,
                             type : "onClick"
                           }
        
        );
    };
    
    /**
     * The Default event for Menu Item onClick events
     * @param menuItem The menu item object configuration
     * @param payload The payload message being sent from the menu item
     */
    this.onMenuItemClick = function(e) {
        var _targetId;
        var _value = wargs.value;
        if (_value.id) _targetId = _value.id;
        var _action;
        if (e.action){
            _action = e.action;
        }
        jmaki.processActions({ topic : publish, 
                             widgetId : wargs.uuid,
                             action : _action,
                             value : _value,
                             targetId : _targetId,
                             type : "onClick"
                           }
        
        );
    }
    
    /**
     * Initialize the dojo dijit wrappers. Three different types are allowed.
     * The normal button, the comboButton and the dropDownbutton. These types
     * are set by passing the type as an argument to the widget wargs.args.type
     */
    this.init = function() {
        switch(cfg.type){
            case "button":{
                jmaki.log("type is button");
                _widget.wrapper = new dijit.form.Button(cfg, container);
                break;
            }
            case "dropDownButton": {
                // Convert the jMaki menu Model to a dijit Menu
                var menu = convertToDojoMenu(cfg.menu);
                
                var params = {
                    label : cfg.label,
                    name : uuid + "_ddBtn",
                    id : uuid + "ddBtn" + (new Date().getTime()) + counter,
                    dropDown : menu
                };
                
                _widget.wrapper = 
                    new dijit.form.DropDownButton(params, container);

                break;
            }
            case "comboButton": {
                var menu = convertToDojoMenu(cfg.menu);
                
                var params = {
                    label : cfg.label,
                    name : uuid +"_cBtn",
                    dropDown : menu,
                    onClick: _widget.onClick
                }
                
                _widget.wrapper = 
                    new dijit.form.ComboButton(params,container);
                    
                break;
            }
            default: {
                jmaki.log("Unknown button type selected");
            }
        
        }
    };

    
    var menuCount = 0;
    var menuItemCount = 0;
    /**
     * Convert the jMaki menu model to the Dojo Dijit Menu
     * @param jMakiMenu The jMaki menu model
     * @see http://wiki.java.net/bin/view/Projects/jMakiMenuDataModel
     *
     */
    function convertToDojoMenu(jMakiMenu) {
        var baseMenu;
        var currentMenu;
        
        var bmCfg = {
            id: uuid + "_bm" + (new Date().getTime()) + (++menuCount),
            label : 'Menu 1'
        };
        
        // Create the base menu for the button
        baseMenu = new dijit.Menu(bmCfg);
        baseMenu.domNode.style.display = "none";
        
        
        if(typeof jMakiMenu != 'undefined'){
            for(var i=0; i< jMakiMenu.length; i++){
                var item = jMakiMenu[i];
                
                // Check to see if this is a submenu or a simple menu item
                if(typeof item.menu == 'undefined') {
                    var dMenuItem = processMenuItem(item, false);
                    baseMenu.addChild(dMenuItem);
                } else {
                    // Process the submenu
                    var smCfg = {
                        label : item.label,
                        id : uuid + (new Date().getTime()) + (++menuCount),
                        showLabel : true
                    };
                    
                    var subMenu = processMenuItem(item, true);
                    smCfg.popup = subMenu;
                    var subMenuPopup = new dijit.PopupMenuItem(smCfg);
                    baseMenu.addChild(subMenuPopup);

                }
            }

            // Added to make sure theme was being used on the menu items
            addThemeClass(baseMenu, "tundra");
            
        }
        
        return(baseMenu);
    };
    
    /**
     * Convert jMaki MenuItem to Dojo Dijit MenuItem
     * @param jMakiMenuItem An Object from the jMaki Menu Model 
     * @param isSubMenu Boolean expression specifiying if this is a submenu
     * Example: {label : '', action : {}, message : {}}
     */
    function processMenuItem(jMakiMenuItem, isSubMenu) {
        var menuItem;
        if(isSubMenu == true){
            if(typeof jMakiMenuItem != 'undefined'){
                var menu = jMakiMenuItem;
                
                var mnCfg = {
                    id : uuid + (new Date().getTime()) + (++menuCount),
                    showLabel : false
                };
                
                var dMenu = new dijit.Menu(mnCfg);
                
                for(var i=0; i < jMakiMenuItem.menu.length; i++){
                    var item = jMakiMenuItem.menu[i];
                    var smiCfg = {
                        label : jMakiMenuItem.menu[i].label,
                        id : uuid + "_smi" + (new Date().getTime()) + (++menuItemCount),
                        showLabel : true
                    };
                    
                    var action = jMakiMenuItem.menu[i].action;
                    if(action){
                        smiCfg.onClick = function(){
                            _widget.onMenuItemClick(item);
                        };
                    }
                    
                    var sMenuItem = new dijit.MenuItem(smiCfg);
                    addThemeClass(sMenuItem, "tundra");
                    
                    // Add the Menu Item to the submenu
                    dMenu.addChild(sMenuItem);
                }
                
                addThemeClass(dMenu, "tundra");
                menuItem = dMenu;
            } // End if(typeof jMakiMenuItem != 'undefined')
        } else {
        
            if(typeof jMakiMenuItem != 'undefined'){
                var miCfg = {
                    label : "Menu Item",
                    iconClass : '',
                    showLabel : true
                }

                if(typeof jMakiMenuItem.label != 'undefined'){
                    miCfg.label = jMakiMenuItem.label;
                }

                if(typeof jMakiMenuItem.iconClass != 'undefined'){
                    miCfg.iconClass = jMakiMenuItem.iconClass;
                }

                // Configure the default action for this Menu Item
                var action = jMakiMenuItem.action;
                if(action){
                    miCfg.onClick = function(){
                        _widget.onMenuItemClick(jMakiMenuItem);
                    }
                }

                menuItem = new dijit.MenuItem(miCfg);
                addThemeClass(menuItem, "tundra");
            }
        }
        
        // Return the menuItem
        return(menuItem);
    };


    /**
     * Adds the themes classes to the element
     * @param e The element (DOM) you want to add the theme class to
     * @param themeName The name of the theme you want to add to the element
     */
    function addThemeClass(element,themeName){
        
        if(typeof themeName != 'undefined'){
            // Check to see if this is a dojo element or a straight dom element
            var themeClass;
            if(typeof element.domNode != 'undefinfed'){
                themeClass = element.domNode.className + " " + themeName;
                element.domNode.className = themeClass;
            } else {
                themeClass = element.className + " " + themeName; 
                element.className = themeClass;
            }

        }

    }
    
    function doSubscribe(topic, handler) {
        var i = jmaki.subscribe(topic, handler);
        _widget.subs.push(i);
    };
    
    this.destroy = function() {
        for (var i=0; _widget.subs && i < _widget.subs.length; i++) {
            jmaki.unsubscribe(_widget.subs[i]);
        }
        
        cfg = {};
    };

    /**
     * Called after widget is loaded. Put any initialization needed for the 
     * wrapper here. 
     */
    this.postLoad = function() {

        // Define the default configuration for the widget
        cfg = {
            label: 'Click Me',
            showLabel : true,
            type : 'button',
            iconClass : '',
            onClick : _widget.onClick
        };
        
        // Parse the args
        if(wargs.args) {
            
            // Check for the label
            if(typeof wargs.args.label != 'undefined') {
                cfg.label = wargs.args.label;
            }
        
            // Check if the user wants to display the label
            if(typeof wargs.args.showLabel != 'undefined') {
                cfg.showLabel = wargs.args.showLabel;
            }
            
            // Check to see what type of button
            if(wargs.args.type) {
                cfg.type = wargs.args.type;
            }
        }
        // Check for the menu model
        if(typeof wargs.value != 'undefined'){
            // Check to see if they specified the menu model
            value = wargs.value;
            if(typeof value.menu != undefined){
                cfg.menu = value.menu;
                cfg.label = value.label;
            }
        }        
        // Initialize the widget
        _widget.init();
        _widget.subs = [];
        for (var _i=0; _i < subscribe.length; _i++) {
            doSubscribe(subscribe[_i]  + "/setLabel", _widget.setLabel);
        }
        
  	        
    };
        
    /**
     * Sets the label of the button
     * @param text The text you want the label of the button to display
     */
    this.setLabel = function(label){
        if(typeof label != 'undefined'){
            _widget.wrapper.setText(label);
        }
    }
};