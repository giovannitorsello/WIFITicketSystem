<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>

<f:view>
<html>
    <head>
        <link rel="stylesheet" href="./css/jmaki-standard.css" type="text/css"/>
        <link rel="stylesheet" href="./resource/css/themes/kame/theme.css" type="text/css"/>

        <script type="text/javascript" src="./jmaki.js" />
        <script type="text/javascript" src="./glue.js" />

        <title>Page Title</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <div id="outerBorder" style="height:2000px">
            <div id="header" style="height:100px">
                <div id="banner">Wisp manager</div>                
                <!--div id="subheader">
                    <div>
                        <a href="mailto:develop@wifinetcom.net">Feedback</a> |
                    </div>
                    
                </div-->
            </div> <!-- header -->

            <div id="main" style="height:1900px">

                <!--div id="leftSidebar" style="position:absolute;top:100px;width:100px;left:0px">
                    <!--a:ajax name="dojo.tree" id="navigazione"
                    value="{
                    root : {
                    label : 'Principale',
                    expanded : true,
                        children :
                        [
                            { 
                            label : 'Anagrafiche',  action : {},
                            expanded : true,
                                children :
                                [
                                    {label : 'Persone fisiche', action : {topic : '/page/select', message : 'personefisiche' }},
                                    {label : 'Persone giuridiche', action : {topic : '/page/select', message : 'personegiuridiche' }},
                                    {label : 'Clienti', action : {topic : '/page/select', message : 'clienti' }},
                                    {label : 'Fornitori', action : {topic : '/page/select', message : 'fornitori' }},
                                    {label : 'Rappresentanti', action : {topic : '/page/select', message : 'rappresentanti' }}
                                ]
                            },
                            { label : 'Contabilità',action : {}},
                            { label : 'Rete',action : {}},
                        ]
                    }
                    }"/>

                </div-->
                <div id="content" style="position:absolute;top:100px;width:100px;height:1900px">
                    <a:ajax name="yahoo.tabbedview"
                    value="{items:[
                        {id : 'tabPersonaFisica', label : 'Persona Fisica', include : './faces/modules/personafisica.jsp', iframe : true },
                        {id : 'tabPersonaGiuridica', label : 'Persona Giuridica', include : './faces/modules/personagiuridica.jsp', iframe : true },
                        {id : 'tabCliente', label : 'Cliente', include : './faces/modules/cliente.jsp', iframe : true },
                        {id : 'tabFornitore', label : 'Fornitore', include : './faces/modules/fornitore.jsp', iframe : true },
                        {id : 'tabRappresentante', label : 'Rappresentante', include : './faces/modules/rappresentante.jsp', iframe : true },
                        {id : 'tabServizi', label : 'Servizi', include : './faces/modules/servizi.jsp', iframe : true },
                        {id : 'tabSerieTicket', label : 'Serie Ticket', include : './faces/modules/serie_ticket.jsp', iframe : true },
                        {id : 'tabTicket', label : 'Ticket', include : './faces/modules/ticket.jsp', iframe : true }
                        ]
                    }" />
                </div> <!-- content -->       
            </div> <!-- main -->
        </div> <!-- outerborder -->
    </body>
</html>
</f:view>