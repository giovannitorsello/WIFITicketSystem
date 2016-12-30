/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



djd43.provide("djd43.widget.TreeExpandToNodeOnSelect");
djd43.require("djd43.widget.HtmlWidget");
djd43.widget.defineWidget("djd43.widget.TreeExpandToNodeOnSelect", djd43.widget.HtmlWidget, {selector:"", controller:"", withSelected:false, initialize:function () {
	this.selector = djd43.widget.byId(this.selector);
	this.controller = djd43.widget.byId(this.controller);
	djd43.event.topic.subscribe(this.selector.eventNames.select, this, "onSelect");
}, onSelectEvent:function (message) {
	this.controller.expandToNode(message.node, this.withSelected);
}});

