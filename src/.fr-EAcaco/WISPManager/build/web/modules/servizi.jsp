
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
        <h:form id="form_cliente" >
            <p>Servizi a canone Fisso</p>
            <p>Selezione: <h:outputText value="#{servizioController.servizio}"/></p>
            <table id="table">
                <tr>
                    <td>Fornitore</td>
                    <td>
                        <h:selectOneMenu id="fornitore" value="#{servizioController.servizio.fornitore}">
                            <f:selectItems value="#{fornitoreController.mapFornitori}"/>
                        </h:selectOneMenu>
                    </td>
                </tr>
                <tr>
                    <td>Descrizione</td>
                    <td><h:inputText id="descrizione" value="#{servizioController.servizio.descrizione}"/></td>
                </tr>
                <tr>
                    <td>Tipologia</td>
                    <td>
                        <h:selectOneMenu id="tipologia" value="#{servizioController.servizio.tipologia}">
                            <f:selectItem itemValue="fonia"  itemLabel="fonia" />
                            <f:selectItem itemValue="internet"  itemLabel="internet" />
                            <f:selectItem itemValue="manutenzione"  itemLabel="manutenzione" />
                            <f:selectItem itemValue="locazione"  itemLabel="locazione" />
                            <f:selectItem itemValue="canone"  itemLabel="canone" />
                            <f:selectItem itemValue="consumo" itemLabel="consumo" />
                            <f:selectItem itemValue="ticket"  itemLabel="ticket" />
                        </h:selectOneMenu>
                    </td>
                </tr>
                <tr>
                    <td>Base temporale in giorni (7/30/180/365)</td>
                    <td><h:inputText id="basetemporale" value="#{servizioController.servizio.baseTemporaleImporto}"/></td>
                </tr>
                <tr>
                    <td>Intervallo di fatturazione</td>
                    <td><h:inputText id="intervallofatturazione" value="#{servizioController.servizio.intervalloFatturazione}"/></td>
                </tr>
                <tr>
                    <td>Data inizio di validità del servizio</td>
                    <td>
                        <h:inputText id="iniziovaliditaservizio" value="#{servizioController.servizio.dataInizio}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data fine di validità del servizio</td>
                    <td>
                        <h:inputText id="finevaliditaservizio" value="#{servizioController.servizio.dataFine}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Attivazione</td>
                    <td><h:inputText id="attivazione" value="#{servizioController.servizio.attivazione}"/></td>
                </tr>
                <tr>
                    <td>Importo</td>
                    <td><h:inputText id="importo" value="#{servizioController.servizio.importo}"/></td>
                </tr>
                <tr>
                    <td>IVA applicata</td>
                    <td><h:inputText id="iva" value="#{servizioController.servizio.IVA}"/></td>
                </tr>
                <tr>
                    <td>Descrizione Unita' di consumo</td>
                    <td><h:inputText id="descrizioneunitaconsumo" value="#{servizioController.servizio.descrizioneUnitadiConsumo}"/></td>
                </tr>
                <tr>
                    <td>Unita' di consumo</td>
                    <td><h:inputText id="unitaconsumo" value="#{servizioController.servizio.unitaDiConsumo}"/></td>
                </tr>
                <tr>
                    <td>Descrizione Estesa</td>
                    <td><h:inputTextarea cols="50" rows="10" id="descrizioneestesa" value="#{servizioController.servizio.descrizioneEstesa}"/></td>
                </tr>

            </table>

                    <h:commandButton value="Crea" action="#{servizioController.crea_servizio}" />
                    <h:commandButton value="Salva" action="#{servizioController.salva_servizio}" />
                    <h:commandButton value="Elimina" action="#{servizioController.elimina_servizio}" />
                    <h:commandButton value="Elenca Tutti" action="#{servizioController.elenca_tutti_servizio}" />

        </h:form>

        <a:ajax  name="dojo.table"
                    args="{topic: '/servizio'}"
                    value="#{servizioController.query}" />
    </body>


</html>
</f:view>
