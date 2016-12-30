// define the namespaces
jmaki.namespace("jmaki.widgets.jquery.accordion");

/**
* jQuery UI Accordion jMaki Wrapper Widget
*
* @author Ahmad M. Zawawi <ahmad.zawawi@gmail.com>
* @constructor
* @see http://docs.jquery.com/UI/Accordion
*/
jmaki.widgets.jquery.accordion.Widget = function(wargs) {

    var _widget = this;
    var uuid = wargs.uuid;
    var publish = "/jquery/accordion";
    var subscribe = ["/jquery/accordion", "/accordion"];    

    var wrapper =  null;
    this.rows = [];
    var selectedIndex = 0;
    
    if (wargs.args){
        if (  wargs.args.selectedIndex != 'undefined') {
            selectedIndex = Number(wargs.args.selectedIndex);
        }
    }
    if (wargs.publish) {
        publish = wargs.publish;
    }
    
    
    /**
    * Initialize accordion
    */
    var initAccordion = function() {
        wrapper = document.getElementById(uuid);
    	
        for ( var i = 0; i < _widget.rows.length; ++i) {

			var _row = _widget.rows[i];

			var group = document.createElement("div");
			group.className = "ui-accordion-group";
			var header = document.createElement("h3");
			header.className = "ui-accordion-header";
			group.appendChild(header);
			var hlink = document.createElement("a");
			hlink.href = "#";
			hlink.innerHTML = _row.label;
			header.appendChild(hlink);
			var content = document.createElement("div");
			content.className = "ui-accordion-content";
			if (typeof _row.include == 'undefined') {
				content.innerHTML = _row.content;
				group.appendChild(content);
			} else {
				// TODO lazy load is not working...
				var div = document.createElement("div");
				var dcontainer = new jmaki.DContainer( {
					target :div,
					useIframe :iframe,
					// overflow: of,
					topic :publish,
					url :_row.url
				});
				content.appendChild(div);
			}
			wrapper.appendChild(group)
		}
        wrapper = $('#' + uuid).accordion({
			header: ".ui-accordion-header",
			clearStyle: true
		});
    }
    
    /**
	 * Should be called once jMaki is loaded
	 */
    this.postLoad = function() {

        if(wargs.value) {
            _widget.rows = wargs.value.rows;
            initAccordion();
        } else if(wargs.service)
        {
            jmaki.doAjax({url: wargs.service, callback: function(req) {
                var data = eval('(' + req.responseText + ')');
                _widget.rows = data.rows;
                initAccordion();
            }});
        } else
        {
            _widget.rows = [
                {label: 'Books', content: 'Book content'},
                {label: 'Magazines', content: 'Magazines here'},
                {label: 'Newspaper', content: 'Newspaper content'}
            ];
            initAccordion();
        }
    }
    
};
