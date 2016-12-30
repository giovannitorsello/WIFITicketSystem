/*
Copyright 2007 You may not modify, use, reproduce, or distribute this software
except in compliance with the terms of the License at:
http://developer.sun.com/berkeley_license.html
*/

// define the namespaces
jmaki.namespace("jmaki.widgets.spry.validation");

/**
* Spry Validation jMaki widget
*
* Remember to put a span around the input fields you want to validation with  
* a unique id and the automatic mode will do the rest.
*
* Note: IE7 seems freezes if it references an input[type=password] and/or textarea, 
* simply wrap it with a span
* 
* @author Ahmad M. Zawawi <ahmad.zawawi@gmail.com>
* @constructor
* @see http://wiki.java.net/bin/view/Projects/jMakiValidationDataModel
* @see http://labs.adobe.com/technologies/spry/samples/
*/
jmaki.widgets.spry.validation.Widget = function(wargs) {
    
    var self = this;
    var subscribe =  ["/spry/validation", "/validation"];
    var publish = "/spry/validation";
    var containerId = wargs.uuid;
    var JMAKI_LOAD_COMPLETE = "/jmaki/runtime/loadComplete";
    
    if (wargs.publish) {
        publish = wargs.publish;
    }
    if (wargs.subscribe) {
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        }
    }
    
    if(wargs.args) {
        var args = wargs.args;
        
        //TODO implement args
    }
    
    /**
    * Returns a copy of t (simple clone)
    */
    function clone(t) {
        var obj = {};
        for (var i in t) {
            obj[i] = t[i];
        }
        return obj;
    }
    
    /**
    * TODO document processActions
    */
    function processActions(m, pid) {
        if (m) {
            var _topic = publish + "/onSelect";
            var _m = {widgetId : wargs.uuid, topic : publish, type : 'onSelect', targetId : pid};
            
            var action = m.action;
            
            if (action && action instanceof Array) {
                for (var _a=0; _a < action.length; _a++) {
                    var payload = clone(_m);
                    if (action[_a].topic) {
                        payload.topic = action[_a].topic;
                    }
                    else {
                        payload.topic = _topic;
                    }
                    if (action[_a].message) {
                        payload.message = action[_a].message;
                    }
                    jmaki.publish(payload.topic,payload);
                }
            } else
            {
                if (m.action && m.action.topic) {
                    _topic = _m.topic = m.action.topic;
                }
                if (m.action && m.action.message) {
                    _m.message = m.action.message;
                }
                jmaki.publish(_topic,_m);
            }
        }
    }
    
    /**
    * Adds an error message to an additionalErrors span
    * for spry validation widgets
    *
    * <span class="$className">[$msg|$defaultMsg]</span>
    */
    function addErrorMsg(className,msg)
    {
        var o = msgs[className];
        var defaultMsg = (o) ? o : null;
        if(defaultMsg) {
            var aMsg = (msg) ? msg : defaultMsg;
            var c = document.getElementById(containerId);
            if(c) {
                var t = document.createElement("span");
                t.className = className;
                t.appendChild(document.createTextNode(aMsg));
                c.appendChild(t);
            } else
            {
                jmaki.log("containerId element is not found. ('" + containerId +"')");
            }
        } else
        {
            jmaki.log("error class name is not found ('" + className + "')");
        }
    }
    
    //global defaults
    var DEFAULT_VALIDATE_ON = ["change"];
    
    //default class names/error messages
    var msgs = {
        //"password" messages
        "passwordRequiredMsg":          "A value is required.",
        "passwordMinCharsMsg":          "The minimum number of characters not met.",
        "passwordMaxCharsMsg":          "The maximum number of characters exceeded.",
        "passwordInvalidStrengthMsg":   "The password strength condition not met.",
        "passwordCustomMsg":            "User defined condition not met.",
        //"confirm" messages
        "confirmRequiredMsg":           "A value is required",
        "confirmInvalidMsg":            "The values don't match",
        //"select" messages
        "selectRequiredMsg":           "Please select an item",
        "selectInvalidMsg":            "Please select a valid item.",
        //"checkbox" messages
        "checkboxRequiredMsg":          "Please make a selection.",
        "checkboxMinSelectionsMsg":     "Minimum number of selections not met.",
        "checkboxMaxSelectionsMsg":     "Maximum number of selections exceeded.",
        //"radio" messages
        "radioRequiredMsg":             "Please make a selection.",
        "radioInvalidMsg":              "Please choose a valid value.",
        //"textarea" messages
        "textareaRequiredMsg":          "The value is required.",
        "textareaMinCharsMsg":          "The minimum number of characters not met.",
        "textareaMaxCharsMsg":          "The maximum number of characters exceeded.",
        //"text" messages
        "textfieldRequiredMsg":         "The value is required.",
        "textfieldInvalidFormatMsg":    "Invalid format",
        "textfieldMinCharsMsg":         "The minimum number of characters not met.",
        "textfieldMaxCharsMsg":         "The maximum number of characters exceeded."
    };
    
    /**
    * Validate an INPUT[type=password] fieldd via Spry
    */
    function validatePassword(r) {
        jmaki.log("validatePassword...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        //fallback to default error messages if not provided
        addErrorMsg("passwordRequiredMsg",r.requiredMsg);
        addErrorMsg("passwordMinCharsMsg",r.minCharsMsg);
        addErrorMsg("passwordMaxCharsMsg",r.maxCharsMsg);
        addErrorMsg("passwordInvalidStrengthMsg",r.invalidStrengthMsg);
        addErrorMsg("passwordCustomMsg",r.customMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationPassword(r.id, {
            //password rules
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            minChars:r.minChars,
            maxChars:r.maxChars,
            minUpperAlphaChars:r.minUpperAlphaChars,
            minNumbers:r.minNumbers,
            maxNumbers:r.maxNumbers,
            maxSpecialChars:r.maxSpecialChars,
            minSpecialChars:r.minSpecialChars,
            minAlphaChars:r.minAlphaChars,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate 2 INPUT field for confirmation via Spry
    * (e.g. email,confirm email fields)
    */
    function validateConfirm(r) {
        jmaki.log("validateConfirm...");
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        //fallback to default error messages if not provided
        addErrorMsg("confirmRequiredMsg",r.requiredMsg);
        addErrorMsg("confirmInvalidMsg",r.invalidMsg);
        
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationConfirm(r.id,r.confirmId, {
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate a SELECT field
    */
    function validateSelect(r) {
        jmaki.log("validateSelect...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        //fallback to default error messages if not provided
        addErrorMsg("selectRequiredMsg",r.requiredMsg);
        addErrorMsg("selectInvalidMsg",r.invalidMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationSelect(r.id, {
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            emptyValue:(r.emptyValue) ? r.emptyValue : "",
            invalidValue:(r.invalidValue) ? r.invalidValue : null,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate INPUT[type=checkbox]
    */
    function validateCheckbox(r) {
        jmaki.log("validateCheckbox...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        //fallback to default error messages if not provided
        addErrorMsg("checkboxRequiredMsg",r.requiredMsg);
        addErrorMsg("checkboxMinSelectionsMsg",r.minSelectionsMsg);
        addErrorMsg("checkboxMaxSelectionsMsg",r.maxSelectionsMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationCheckbox(r.id, {
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            minSelections:r.minSelections,
            maxSelections:r.maxSelections,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate INPUT[type=radio]
    */
    function validateRadio(r) {
        jmaki.log("validateRadio...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        
        //fallback to default error messages if not provided
        addErrorMsg("radioRequiredMsg",r.requiredMsg);
        addErrorMsg("radioInvalidMsg",r.invalidMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationRadio(r.id, {
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            emptyValue:(r.emptyValue) ? r.emptyValue : "",
            invalidValue:(r.invalidValue) ? r.invalidValue : null,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate TEXTAREA field via Spry
    */
    function validateTextarea(r) {
        jmaki.log("validateTextarea...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        
        //aCounterId = (r.counterId) ? r.counterId : counterId;
        if(r.counterType && typeof r.counterId == "undefined") {
            //user wants the widget to manage counterId placement...
            r.counterId = containerId + "_counter";
        }
        //fallback to default error messages if not provided
        addErrorMsg("textareaRequiredMsg",r.requiredMsg);
        addErrorMsg("textareaMinCharsMsg",r.minCharsMsg);
        addErrorMsg("textareaMaxCharsMsg",r.maxCharsMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationTextarea(r.id, {
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            minChars:r.minChars,
            maxChars:r.maxChars,
            counterType:r.counterType,
            counterId:r.counterId,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    /**
    * Validate INPUT[type=text] field  via Spry
    */
    function validateText(r) {
        jmaki.log("validateText...");
        
        //fallback to defaults...
        var aValidateOn = (r.validateOn) ? r.validateOn : DEFAULT_VALIDATE_ON;
        var aIsRequired = (typeof r.isRequired == "boolean") ? r.isRequired : true;
        var aUseCharacterMasking = (typeof r.useCharacterMasking == "boolean") ?
        r.useCharacterMasking : true;
        var aMethod = (typeof r.method == "string") ? r.method : "none";
        //fallback to default error messages if not provided
        addErrorMsg("textfieldRequiredMsg",r.requiredMsg);
        addErrorMsg("textfieldInvalidFormatMsg",r.invalidFormatMsg);
        addErrorMsg("textfieldMinCharsMsg",r.minCharsMsg);
        addErrorMsg("textfieldMaxCharsMsg",r.maxCharsMsg);
        
        //TODO validate rules in a generic way (throwing an typed exception for instance)...
        var p = new Spry.Widget.ValidationTextField(r.id, aMethod,{
            isRequired:aIsRequired,
            useCharacterMasking:aUseCharacterMasking,
            minChars:r.minChars,
            maxChars:r.maxChars,
            format:r.format,
            hint:r.hint,
            pattern:r.pattern,
            //When to validate? [change,blue,submit]
            validateOn:aValidateOn,
            //error messages in a seperate span
            additionalError:containerId
        });
    }
    
    
    
    /**
    * Parse the validation data model described at
    * http://wiki.java.net/bin/view/Projects/jMakiValidationDataModel
    */
    function parseDataModel(model) {
        try {
            //must be an array
            var rules = model.rules;
            var ruleMap = {
                "password": validatePassword,
                "confirm": validateConfirm,
                "select": validateSelect,
                "radio": validateRadio,
                "checkbox": validateCheckbox,
                "textarea": validateTextarea,
                "text": validateText
            };
            for(var i = 0; i < rules.length; i++) {
                var rule = rules[i];
                var ruleType = rule.type;
                var validateFunction = ruleMap[ruleType];
                if(typeof validateFunction == "function") {
                    //call rule function...
                    validateFunction(rule);
                } else
                {
                    jmaki.log("invalid rule type '" + ruleType + "'. " +
                        "Please check http://wiki.java.net/bin/view/Projects/jMakiValidationDataModel");
                }
            }
        } catch(e)
        {
            jmaki.log("Exception thrown: " + e);
            jmaki.log("Invalid data model. " +
                "Please check http://wiki.java.net/bin/view/Projects/jMakiValidationDataModel");
        }
    }
    
    
    /**
    * Automatically look inside your page's DOM and generate
    * automatic validation rules
    */
    function initFromPage() {
        jmaki.log("Auto generating rules and applying them. " +
            "Rules will be dumped to jmaki.log");
        //TODO implement wizard mode where the widget looks at the page
        //and generates automatic rules for it.... sounds cool ;-)
        //hmmm "password" could be guessed easily
        //"confirm" we need a hint there with the word "confirm" in its label or id...
        //the others we could generate basic automatic tests for it.
        
        //TODO search for LABELS, print a warning if you dont find any
        jmaki.log("Searching for SPANs with fields...");
        //var labels = Spry.$$("label");
        //jmaki.log("Found " + labels.length + " labels");
        var passwords = Spry.$$("span > input[type=password]");
        jmaki.log("Found " + passwords.length + " password fields");
        var checkboxes = Spry.$$("span > input[type=checkbox]");
        jmaki.log("Found " + checkboxes.length + " checkboxes");
        var radios = Spry.$$("span > input[type=radio]");
        jmaki.log("Found " + radios.length + " radio buttons");
        var texts = Spry.$$("span > input[type=text]");
        jmaki.log("Found " + texts.length + " text fields");
        var textareas = Spry.$$("span > textarea");
        jmaki.log("Found " + textareas.length + " text area fields");
        var selects = Spry.$$("span > select");
        jmaki.log("Found " + selects.length + " select fields");
        
        var rules = [];
        var value = {rules:rules};
        
        /**
        * Add a rule helper from DOM
        * The idea is simple add a field that has an immediate span parent around it...
        */
        function addRuleFromFields(pFields,pType) {
            var lastParentId = -1;
            for(var i = 0; i < pFields.length; i++) {
                var f = pFields[i];
                var span = f.parentNode;
                if(typeof span != 'undefined' && span.id != lastParentId) {
                    rules.push({ type:pType, id:span.id });
                    lastParentId = span.id;
                }
            }
        }

        //generate simple rules for form fields
        addRuleFromFields(passwords,'password');
        addRuleFromFields(radios,'radio');
        addRuleFromFields(checkboxes,'checkbox');
        addRuleFromFields(textareas,'textarea');
        addRuleFromFields(texts,'text');
        addRuleFromFields(selects,'select');
        
        //TODO generate "confirm" based on labels containing "confirm"
        
        //dump it to jmaki.log
        jmaki.log("Created " + rules.length + " rule(s)");
        jmaki.log('a:widget name="spry.validation" value="' + 
            jmaki.JSON.serialize(value) + '"');
        
        
        //and run it...
        parseDataModel(value);
        
    }
    
    /**
    * Initialize validation rules from value attribute
    */
    function initFromValue() {
        parseDataModel(wargs.value);
    }
    
    /**
    * Initialize validation rules from service attribute
    */
    function initFromService() {
        jmaki.doAjax({url: wargs.service, callback: function(req) {
            var response = req.responseText;
            jmaki.log("response = " + response);
            var dataModel = jmaki.JSON.deSerialize(response);
            if(dataModel) {
                parseDataModel(dataModel);
            } else
            {
                jmaki.log("Invalid data model from service");
            }
        }});
    }
    
    if (wargs.value) {
        //from value
        jmaki.subscribe(JMAKI_LOAD_COMPLETE,initFromValue);
    } else if (wargs.service)
    {
        //from service
        jmaki.subscribe(JMAKI_LOAD_COMPLETE,initFromService);
    }  else
    {
        //auto validation rule generation mode
        jmaki.subscribe(JMAKI_LOAD_COMPLETE,initFromPage);
    }
    
}