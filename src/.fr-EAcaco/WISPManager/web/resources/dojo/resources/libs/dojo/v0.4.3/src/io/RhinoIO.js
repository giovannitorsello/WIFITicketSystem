/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



djd43.provide("djd43.io.RhinoIO");
djd43.require("djd43.io.common");
djd43.require("djd43.lang.func");
djd43.require("djd43.lang.array");
djd43.require("djd43.string.extras");
djd43.io.RhinoHTTPTransport = new function () {
	this.canHandle = function (req) {
		if (djd43.lang.find(["text/plain", "text/html", "text/xml", "text/javascript", "text/json", "application/json"], (req.mimetype.toLowerCase() || "")) < 0) {
			return false;
		}
		if (req.url.substr(0, 7) != "http://") {
			return false;
		}
		return true;
	};
	function doLoad(req, conn) {
		var ret;
		if (req.method.toLowerCase() == "head") {
		} else {
			var stream = conn.getContent();
			var reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream));
			var text = "";
			var line = null;
			while ((line = reader.readLine()) != null) {
				text += line;
			}
			if (req.mimetype == "text/javascript") {
				try {
					ret = dj_eval(text);
				}
				catch (e) {
					djd43.debug(e);
					djd43.debug(text);
					ret = null;
				}
			} else {
				if (req.mimetype == "text/json" || req.mimetype == "application/json") {
					try {
						ret = dj_eval("(" + text + ")");
					}
					catch (e) {
						djd43.debug(e);
						djd43.debug(text);
						ret = false;
					}
				} else {
					ret = text;
				}
			}
		}
		req.load("load", ret, req);
	}
	function connect(req) {
		var content = req.content || {};
		var query;
		if (req.sendTransport) {
			content["djd43.transport"] = "rhinohttp";
		}
		if (req.postContent) {
			query = req.postContent;
		} else {
			query = djd43.io.argsFromMap(content, req.encoding);
		}
		var url_text = req.url;
		if (req.method.toLowerCase() == "get" && query != "") {
			url_text = url_text + "?" + query;
		}
		var url = new java.net.URL(url_text);
		var conn = url.openConnection();
		conn.setRequestMethod(req.method.toUpperCase());
		if (req.headers) {
			for (var header in req.headers) {
				if (header.toLowerCase() == "content-type" && !req.contentType) {
					req.contentType = req.headers[header];
				} else {
					conn.setRequestProperty(header, req.headers[header]);
				}
			}
		}
		if (req.contentType) {
			conn.setRequestProperty("Content-Type", req.contentType);
		}
		if (req.method.toLowerCase() == "post") {
			conn.setDoOutput(true);
			var output_stream = conn.getOutputStream();
			var byte_array = (new java.lang.String(query)).getBytes();
			output_stream.write(byte_array, 0, byte_array.length);
		}
		conn.connect();
		doLoad(req, conn);
	}
	this.bind = function (req) {
		var async = req["sync"] ? false : true;
		if (async) {
			setTimeout(djd43.lang.hitch(this, function () {
				connect(req);
			}), 1);
		} else {
			connect(req);
		}
	};
	djd43.io.transports.addTransport("RhinoHTTPTransport");
};

