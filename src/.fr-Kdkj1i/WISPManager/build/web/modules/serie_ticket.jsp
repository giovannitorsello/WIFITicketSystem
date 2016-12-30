
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
                        <h:selectOneMenu id="cliente" value="#{serieTicketController.serieTicket.cliente}">
                            <f:selectItems value="#{clienteController.mapClienti}" />
                        </h:selectOneMenu>
                    </td>
                </tr>
                <tr>
                    <td>Servizio associato</td>
                    <td>
                        <h:selectOneMenu id="servizio" value="#{serieTicketController.serieTicket.servizio}">
                            <f:selectItems value="#{servizioController.mapServizi}" />
                        </h:selectOneMenu>
                    </td>
                </tr>
                <tr>
                    <td>Durata in giorni</td>
                    <td><h:inputText id="durataingiorni" value="#{serieTicketController.serieTicket.durataInGiorni}"/></td>
                </tr>
                <tr>
                    <td>Numero tickets</td>
                    <td><h:inputText id="numerotickets" value="#{serieTicketController.serieTicket.numeroTickets}"/></td>
                </tr>
                <tr>
                    <td>Data di emissione</td>
                    <td>
                        <h:inputText id="dataemissione" value="#{serieTicketController.serieTicket.dataEmissione}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data di scadenza</td>
                    <td>
                        <h:inputText id="datascadenza" value="#{serieTicketController.serieTicket.dataScadenza}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
            </table>

                    <h:commandButton value="Crea" action="#{serieTicketController.crea_serieTicket}" />
                    <h:commandButton value="Salva" action="#{serieTicketController.salva_serieTicket}" />
                    <h:commandButton value="Apri serie" action="#{serieTicketController.apri_serieTicket}" />
                    <h:commandButton value="Elimina" action="#{serieTicketController.elimina_serieTicket}" />
                    <h:commandButton value="Elenca Tutti" action="#{serieTicketController.elenca_tutti_serieTicket}" />
                    <h:commandButton value="Stampa la serie" action="#{serieTicketController.StampaSerie}" />

        </h:form>

        <a:ajax  name="dojo.table"
                    args="{topic: '/serieTicket'}"
                    value="#{serieTicketController.query}" />
    </body>


</html>
</f:view>
