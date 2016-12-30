/* Copyright 2008 You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at:
 http://developer.sun.com/berkeley_license.html
 $Id: component.js,v 1.0 2008/04/15 19:39:59 gmurray71 Exp $
*/

dojo.require("dijit.Tree");
dojo.require("dojo.fx");
dojo.require( "dojo.data.ItemFileWriteStore" );

jmaki.namespace("jmaki.widgets.dojo.dijit.tree");

/**
 * @constructor
*/
jmaki.widgets.dojo.dijit.tree.Widget = function(wargs) {
    
    var _widget = this;
    var publish = "/dojo/dijit/tree";
    var subscribe = ["/dojo/dijit/tree", "/tree"];
    var container;
    
    var counter = 0;
    
    this.expanded = [];
    
    function genId() {
        return wargs.uuid + "_nid_" + counter++;
    }

    var showedModelWarning = false;
    
    function showModelDeprecation() {
        if (!showedModelWarning) {
             jmaki.log("dijit tree widget uses the incorrect data format. Please see " +
                       "<a href='http://wiki.java.net/bin/view/Projects/jMakiTreeDataModel'>" +
                       "http://wiki.java.net/bin/view/Projects/jMakiTreeDataModel</a> " +
                       "for the proper format.");
             showedModelWarning = true;
        }   
    }
    
    this.findNode = function(_nid) {
        // this won't work with data store loaded over a network.
        var node = _widget.tree._itemNodeMap[_nid];
        if (!node) node = null;
        return node;
    }
    
    this.expandNode = function(e) {
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
        if (target){
             target.expand();
             // expand all parent treenodes
             while (target = target.parent) {
                 if (target.expand)target.expand();
             } 
         }     
    }
    
    this.collapseNode = function(e) {         
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var target = _widget.findNode(nid);
        if (target){
             target.collapse();
        }     
    };
    
    // modified by gmurray71 to allow for tree nodes to be added correctly 
    // programatically.
    this.addTreeItem = function(/*Object*/ item, parentInfo){
     
        //summary: callback when new item has been added to the store.

        var loadNewItem;	// should new item be displayed in tree?

        if(parentInfo){
                var parent = _widget.tree._itemNodeMap[_widget.tree.getItemParentIdentity(item, parentInfo)];

                // If new item's parent item not in tree view yet, can safely ignore.
                // Also, if a query of specified parent wouldn't return this item, then ignore.
                if(!parent ||
                        dojo.indexOf(_widget.tree.childrenAttr, parentInfo.attribute) == -1){
                        return;
                }
        }

        var childParams = {
                item: item,
                isExpandable: _widget.tree.mayHaveChildren(item)
        };

        if(parent){
            if(!parent.isExpandable){
                parent.makeExpandable();
            }
            // added a second condition to this logic to set the children
            // if the widget has not been expanded or loaded
            if(parent.state=="LOADED" || parent.isExpanded){

                var childrenMap =parent._addChildren([childParams]);
            } else {
                var childrenMap = parent._setChildren([childParams]);
            }
        } else{              
            // top level node
            var childrenMap=_widget.tree._addChildren([childParams]);		
        }
        if(childrenMap){
            dojo.mixin(_widget.tree._itemNodeMap, childrenMap);
        }
    };
    
    this.expandNodes = function(item) {
        if (_widget.expanded[item.nid] &&
               item.nid != _widget.rootNodeId) {
                   var node = _widget.findNode(item.nid);
                   _widget.tree._expandNode(node);
                   delete _widget.expanded[item.nid];
        }
        if (item.children) {
            var items = item.children;
            for (var i=0;i < items.length; i++) {          
                if (_widget.expanded[items[i].nid]) {              
                    var n = _widget.findNode(items[i].nid);
                    _widget.tree._expandNode(n);
                    delete _widget.expanded[items[i].nid];
                }
                // call back onself to expand visible children
                if (items[i].children) _widget.expandNodes(items[i]);
            }
        }
    };
    
    this.addNodes = function(e, n) {
        var ch;
        if (e.message)e = e.message;
        if (e.value) ch = e.value;
        else ch = n;
        var nid;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        // get the parent node
        var target = _widget.store._getItemByIdentity(nid);
        var parent;
        
        var targetNode = _widget.tree._itemNodeMap[nid];
 
        if (target.parent) parent = _widget.store._getItemByIdentity(target.parent);
        if (parent) {
            parentNode = _widget.findNode(parent.nid);            
        }
        if (target && ch){
          _widget.buildTree(ch, { item: target, nid : nid});
          _widget.store.save();
          targetNode.expand();
          _widget.expandNodes(target);
          
        }
    };
    
    this.removeChildren = function(e){
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        var targetNode = _widget.tree._itemNodeMap[nid];
       
        var target = _widget.store._getItemByIdentity(nid);
        
        if (target && target.children && target.children.length){       
            for (var i=target.children.length -1;  i >= 0 ; i--) {
               _widget.store.deleteItem(target.children[i]);      
            }
           delete target.children;           
            _widget.store.save();
            if (targetNode) {
                targetNode.isExpandable = false;
                targetNode._setExpando();
            }
        }         
    };
    
    this.removeNode = function(e) {
        var nid;
        if (e.message)e = e.message;
        if (e.targetId) nid = e.targetId;
        else nid = e;
        
        // assuming that the node is loaded
        var target = _widget.store._getItemByIdentity(nid);
        
        var pid = target.parent;
        _widget.store.deleteItem(target);

        var parent = _widget.store._getItemByIdentity(pid);
        // remove child reference from parent;
        for (var i=0; i < parent.children.length; i++){
            if (nid ==  parent.children[i].nid) {
                if (i != -1){
                    parent.children.splice(i, 1);
                    break;
                }
            }
        }
        _widget.store.save();
        if (pid) {
           var parentNode = _widget.findNode(pid);
           parentNode.item = parent;
           if (parentNode && parent.children && parent.children.length == 0) {
               // calling expandNode will remove the icon on the node if it is there
               // this is easier than messing around with the icon styles
               _widget.tree._expandNode(parentNode);
           }  
        }
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
    
    function clone(t) {
       var obj = {};
       for (var i in t) {
            obj[i] = t[i];
       }
       return obj;
    }

    function processActions(_t, _pid, _type, _value) {
        if (_t) {
            var _topic = publish;
            var _m = {widgetId : wargs.uuid, type : _type, targetId : _pid};
            if (typeof _value != "undefined") _m.value = _value;
            var action = _t.action;
            if (!action) _topic = _topic + "/" + _type;
            if (action && action instanceof Array) {
              for (var _a=0; _a < action.length; _a++) {
                  var payload = clone(_m);
                  if (action[_a].topic) payload.topic = action[_a].topic;
                  else payload.topic = publish;
                  if (action[_a].message) payload.message = action[_a].message;
                  jmaki.publish(payload.topic,payload);
              }
            } else {
              if (action && action.topic) {
                  _topic = _m.topic = action.topic;
              }
              if (action && action.message) _m.message = action.message;                
              jmaki.publish(_topic,_m);
            } 
        }
    }

    function _addNode(m, parent) {
        var t;
        if (m.label){
            t= m.label;
        } else if (m.title) {
            t = m.title;
            showModelDeprecation();
        }
        var nid;
        if (typeof m.id != 'undefined') nid= m.id;
        else nid = genId();
        m.nid = nid;

        var _mixins = { item : _item,
                        label : t,
                        targetId :nid,
                        nid : nid};
        if (m.action) _mixins.action = m.action;
        if (typeof parent != 'undefined') _mixins.parent = parent.nid;
        if (!m.children)m.children = []
        
        var pInfo = {
            attribute : "children"
        };
        if (typeof parent != 'undefined') {
            pInfo.parent = parent.item;
        }
        var _item = _widget.store.newItem(_mixins, pInfo);    

        return _item;
    }

    
    // now build the tree programtically
    this.buildTree = function(root, parent) {
        var _root = _addNode(root,parent);
        root.item = _root;
        if (typeof parent == 'undefined') {
          // this is the root
          _widget.rootNodeId = _root.nid;
        }
        if (typeof root.expanded == "boolean" &&
                   root.expanded == true) _widget.expanded[_root.nid] = true;

       
        for (var t=0; root.children && t < root.children.length; t++) {
            var n = root.children[t];
            var li = _addNode(n,root);
            n.item = li;
            if (typeof n.expanded == "boolean" && n.expanded == true) _widget.expanded[li.nid] = true;
           
            //  recursively call this function to add children
            if (typeof n.children != 'undefined') {
                for (var ts =0; n.children && ts < n.children.length; ts++) {
                    _widget.buildTree(n.children[ts], n);
                }
            }
            
        }
        return _root;
   };
   
   this.init = function() {
         _widget.expanded = {};

        _widget.container = document.getElementById(wargs.uuid);

        _widget.model = 			
        { 
            identifier: 'nid',
            label: 'label',
            items:  []
        };

        _widget.store = new dojo.data.ItemFileWriteStore({data: _widget.model});    

        _widget.buildTree(_widget.value.root);
        
        _widget.tree = new dijit.Tree({childrenAttr: ['children'],
                                        typeAttr: 'type',
                                        labelAttr : 'label',
                                        store : _widget.store  },
                                    _widget.container);
        // over ride this function to acction for no children in dojo 1.0.2
        _widget.tree._onNewItem = _widget.addTreeItem;
        dojo.connect( _widget.tree, "_onExpandoClick", function(message){
            if(message.node.isExpanded){
                if(message.node.item.action) jmaki.log("* action " + jmaki.inspect(message.node.item.nid));
                processActions(message.node.item, message.node.item.nid, 'onExpand', {label : message.node.item.label});

            } else {
            processActions(message.node.item, message.node.item.nid, 'onCollapse', {label : message.node.item.label});
        }
        });

      if (_widget.expanded[_widget.rootNodeId]) {        
           var rootNode = _widget.findNode(_widget.rootNodeId);         
           _widget.tree._expandNode(rootNode);
           delete _widget.expanded[_widget.rootNodeId];
           var rootItem = _widget.store._getItemByIdentity(_widget.rootNodeId)
           _widget.expandNodes(rootItem);
       }
       
        dojo.connect( _widget.tree, "_onClick", function( e){
            var domElement = e.target;
            // find widget
            var nodeWidget = dijit.getEnclosingWidget(domElement);
            // don't have onclick on leafe nodes or nodes that have actions
            if(!nodeWidget || !nodeWidget.isTreeNode || nodeWidget.isExpandable){
                return;
            }
        
            processActions(nodeWidget.item, nodeWidget.item.nid, 'onClick', {label : nodeWidget.item.label});

        });
        };
        
    this.postLoad = function() {

           if (wargs.publish) {
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
           // use the default tree found in the widget.json if none is provided
           if (!wargs.value) {
               var callback;
               var _s;
               // default to the service in the widget.json if a value has not been st
               // and if there is no service
               if (!wargs.service) {
                   _s = wargs.widgetDir + "/widget.json";
                   callback = function(req) {
                       if (req.responseText =="") {
                           container.innerHTML = "Error loading widget data. No data."
                           return;
                       }                 
                       var obj = eval("(" + req.responseText + ")");
                       _widget.value = obj.value.defaultValue;
                       _widget.init();
                   }
               } else {
               _s = wargs.service;
               callback = function(req) {
                   if (req.responseText =="") {
                       container.innerHTML = "Error loading widget data. No data."
                       return;
                   }               
                   var jTree = eval("(" + req.responseText + ")");
                   if (!jTree.root){
                       showModelDeprecation();
                       return;
                   }
                   _widget.value = jTree;
                   _widget.init();
               }        
           }
           ajax = jmaki.doAjax({url : _s,
               callback : callback,
               onerror : function() {
                   container.innerHTML = "Unable to load widget data.";
               }
           });
        } else if (wargs.value) {
            if (!wargs.value.root){
                showModelDeprecation();
                return;
            }
            if (!wargs.value.root){
                showModelDeprecation();
                return;
            }
            _widget.value = wargs.value;
            _widget.init();
        }
        // register subscribers
        _widget.subs = [];
        for (var _i=0; _i < subscribe.length; _i++) {
            doSubscribe(subscribe[_i]  + "/removeNode", _widget.removeNode);
            doSubscribe(subscribe[_i] + "/removeChildren", _widget.removeChildren);
            doSubscribe(subscribe[_i] +"/addNodes", _widget.addNodes);
            doSubscribe(subscribe[_i] + "/expandNode", _widget.expandNode);
            doSubscribe(subscribe[_i]  + "/collapseNode", _widget.collapseNode);
        }        
    };
}
