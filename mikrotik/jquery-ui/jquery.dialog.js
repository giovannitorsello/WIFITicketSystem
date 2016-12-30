// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License. You may obtain a copy of
// the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// License for the specific language governing permissions and limitations under
// the License.

(function($) {

  $.fn.centerBox = function() {
    return this.each(function() {
      var s = this.style;
      s.left = (($(window).width() - $(this).width()) / 2) + "px";
      s.top = (($(window).height() - $(this).height()) / 2) + "px";
    });
  }

  $.showDialog = function(url, options) {
    options = options || {};
    options.load = options.load || function() {};
    options.cancel = options.cancel || function() {};
    options.validate = options.validate || function() { return true };
    options.submit = options.submit || function() {};
    options.callback = options.submit || function() {};
    
    var overlay = $('<div id="overlay" style="z-index:1001"></div>').css("opacity", "0");
    var dialog = $('<div id="dialog" style="z-index:1002;position:fixed;display:none;"></div>');
    
    overlay.appendTo(document.body).fadeTo(100, 0.6);
    dialog.appendTo(document.body).addClass("loading").centerBox().fadeIn(400);

    $(document).keydown(function(e) {
      if (e.keyCode == 27) dismiss(); // dismiss on escape key
    });
    function dismiss() {
      dialog.fadeOut("fast", function() {
        $("#dialog, #overlay, #overlay-frame").remove();
      });
      $(document).unbind("keydown");
    }
    overlay.click(function() { dismiss(); });

    function showError(name, message) {
      var input = dialog.find(":input[name=" + name + "]");
      input.addClass("error").next("div.error").remove();
      $('<div class="error"></div>').text(message).insertAfter(input);
    }

    $.get(url, function(html) {
      $(html).appendTo(dialog);
      dialog.removeClass("loading").addClass("loaded").centerBox().each(function() {
        options.load(dialog.children()[0]);
        $(":input:first", dialog).each(function() { this.focus() });
        $("button.cancel", dialog).click(function() { // dismiss on cancel
          dismiss();
          options.cancel();
        });
        
        $("button.conferma", dialog).click(function() { // dismiss on cancel      
          options.setdata(options.data);
          options.callback(options.data);
        });
        
        $("form", dialog).submit(function(e) { // invoke callback on submit
          e.preventDefault();
          dialog.find("div.error").remove().end().find(".error").removeClass("error");
          var data = {};
          $.each($("form :input", dialog).serializeArray(), function(i, field) {
            data[field.name] = field.value;
          });
          $("form :file", dialog).each(function() {
            data[this.name] = this.value; // file inputs need special handling
          });
          options.submit(data, function callback(errors) {
            if ($.isEmptyObject(errors)) {
              dismiss();
            } else {
              for (var name in errors) {
                showError(name, errors[name]);
              }
            }
          });
            
          return false;
        });
      });
    });
  }

})(jQuery);