
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="jsfExt" uri="http://java.sun.com/jsf/extensions/dynafaces"%>

<%@taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>

<f:view>
<html>
    <head>
        <title>Form clienti</title>
        <link rel="stylesheet" href="../css/jmaki-standard.css" type="text/css"></link>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
    </head>
    <body>
<jsfExt:scripts/>
       
        <h:form id="form_cliente" >
            <p>Dati Anagrafici</p>
            <table id="table">
                <tr>
                    <td>Connessine a canone fisso</td>
                    <td>
                        <h:inputText id="servzioConnettivitaCanone" value="#{servizioConnettivita.servizioCanone}"/></td>

                    
                    <td>Connessine a consumo</td>
                    <td><h:inputText id="servzioConnettivitaConsumo" value="#{servizioConnettivita.servizioConsumo}"/></td>
                </tr>
                <tr>
                    <td>Fonia a canone fisso</td>
                    <td>
                        <h:inputText id="servzioFoniaCanone" value="#{servizioFonia.servizioCanone}"/></td>


                    <td>Fonia a consumo</td>
                    <td><h:inputText id="servzioFoniaConsumo" value="#{servizioFonia.servizioConsumo}"/></td>
                </tr>
            </table>

            <h:commandButton value="Inserisci" action="#{personaFisicaController.inserisci_personaFisica}" />
                        
        </h:form>
    </body>



</html>
</f:view>
