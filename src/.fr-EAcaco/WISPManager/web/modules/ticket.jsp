
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>

<f:view>
<html>
    <head>
        <title>Form clienti</title>
        <link rel="stylesheet" href="../css/jmaki-standard.css" type="text/css"></link>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
    </head>
    <body>
        <h:form id="serie_ticket" >
            <p>Emissione Serie ticket</p>
            <table id="table">
                <tr>
                    <td>Cliente distributore</td>
                    <td>
                        <h:selectOneMenu id="cliente" value="#{ticketController.ticket.cliente}">
                            <f:selectItems value="#{clienteController.mapClienti}" />
                        </h:selectOneMenu>
                    </td>
                </tr>                
                <tr>
                    <td>Seriale</td>
                    <td><h:inputText id="seriale" value="#{ticketController.ticket.seriale}"/></td>
                </tr>
                <tr>
                    <td>Stato</td>
                    <td><h:inputText id="stato" value="#{ticketController.ticket.stato}"/></td>
                </tr>
                <tr>
                    <td>Durata in giorni</td>
                    <td><h:inputText id="durataingiorni" value="#{ticketController.ticket.durataInGiorni}"/></td>
                </tr>
                <tr>
                    <td>Utente & Password</td>
                    <td>
                        <h:inputText id="utente" value="#{ticketController.ticket.utente}"/>
                        <h:inputText id="password" value="#{ticketController.ticket.password}"/>
                    </td>
                </tr>
                <tr>
                    <td>Data di emissione</td>
                    <td>
                        <h:inputText id="dataemissione" value="#{ticketController.ticket.dataEmissione}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data di scadenza</td>
                    <td>
                        <h:inputText id="datascadenza" value="#{ticketController.ticket.dataScadenza}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data primo utilizzo</td>
                    <td>
                        <h:inputText id="dataprimoutilizzo" value="#{ticketController.ticket.dataPrimoUtilizzo}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data scadenza utilizzo</td>
                    <td>
                        <h:inputText id="datascadenzautilizzo" value="#{ticketController.ticket.dataScadenzaUtilizzo}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
            </table>

                    <h:commandButton value="Crea" action="#{servizioController.crea_servizio}" />
                    <h:commandButton value="Salva" action="#{servizioController.salva_servizio}" />
                    <h:commandButton value="Elimina" action="#{servizioController.elimina_servizio}" />
                    <h:commandButton value="Elenca Tutti" action="#{servizioController.elenca_tutti_servizio}" />

        </h:form>

        <a:ajax  name="dojo.table"
                    args="{topic: '/ticket'}"
                    value="#{ticketController.query}" />
    </body>


</html>
</f:view>
