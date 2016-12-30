
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>

<f:view>
<html>
    <head>
        <title>Persone Giuridiche (Ditte,Associazioni,Società)</title>
        <link rel="stylesheet" href="../css/jmaki-standard.css" type="text/css"></link>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
    </head>
    <body>       
        <h:form id="form_personagiuridica" >
            <p>Intestazione</p>
            <table id="table">
                <tr>
                    <td>Denominazione</td>
                    <td><h:inputText id="denominazione" value="#{personaGiuridicaController.personaGiuridica.denominazione}"/></td>

                    
                    <td>Legale rappresentante</td>
                    <td>
                        <h:selectOneMenu id="legaleRappresentante"                                         
                                         value="#{personaGiuridicaController.personaGiuridica.legaleRappresentante}">

                            <f:selectItems value="#{personaFisicaController.mapPersoneFisiche}" />                                                        
                         </h:selectOneMenu>
                    </td>

                    <td>Partita IVA</td>
                    <td><h:inputText id="partitaIVA" value="#{personaGiuridicaController.personaGiuridica.partitaIva}" /></td>

                </tr>
            </table>
            <p>Sede Legale</p>
            <table>
               <tr><td>Via</td><td>CAP</td><td>Comune</td><td>Provincia</td></tr>
               <tr>
                   <td><h:inputText id="sedeLegaleVia" value="#{personaGiuridicaController.personaGiuridica.sedeLegaleVia}" /></td>
                   <td><h:inputText id="sedeLegaleCAP" value="#{personaGiuridicaController.personaGiuridica.sedeLegaleCap}" /></td>
                   <td><h:inputText id="sedeLegaleComune" value="#{personaGiuridicaController.personaGiuridica.sedeLegaleComune}" /></td>
                   <td><h:inputText id="sedeLegaleProvincia" value="#{personaGiuridicaController.personaGiuridica.sedeLegaleProvincia}" /></td>
                </tr>
            </table>

            <p>Recapiti telefonici - email</p>
            <table>
               <tr><td>Telefono</td><td>Cellulare</td><td>Email</td></tr>
               <tr>
                    <td><h:inputText id="telefono" value="#{personaGiuridicaController.personaGiuridica.telefono}" /></td>
                    <td><h:inputText id="cellulare" value="#{personaGiuridicaController.personaGiuridica.cellulare}" /></td>
                    <td><h:inputText id="email" value="#{personaGiuridicaController.personaGiuridica.email}" /></td>
                    <td><h:inputText id="sito" value="#{personaGiuridicaController.personaGiuridica.email}" /></td>
                </tr>
            </table>
            
            <h:commandButton value="Crea" action="#{personaGiuridicaController.crea_personaGiuridica}" />
            <h:commandButton value="Salva" action="#{personaGiuridicaController.salva_personaGiuridica}" />
            <h:commandButton value="Elimina" action="#{personaGiuridicaController.elimina_personaGiuridica}" />            
        </h:form>

        <a:ajax  name="dojo.table"
         args="{topic: '/personaGiuridica'}"
         value="#{personaGiuridicaController.query}" />

    </body>
</html>
</f:view>
