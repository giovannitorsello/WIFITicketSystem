
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
            <table id="table">
                <tr>
                    <td>Descrizione</td>
                    <td><h:inputText id="descrizione" value="#{servizioCanoneController.servizioCanone.descrizione}"/></td>
                </tr>
                <tr>
                    <td>Base temporale in giorni (7/30/180/365)</td>
                    <td><h:inputText id="basetemporale" value="#{servizioCanoneController.servizioCanone.baseTemporaleImporto}"/></td>
                </tr>
                <tr>
                    <td>Intervallo di fatturazione</td>
                    <td><h:inputText id="intervallofatturazione" value="#{servizioCanoneController.servizioCanone.intervalloFatturazione}"/></td>
                </tr>
                <tr>
                    <td>Data inizio di validità del servizio</td>
                    <td>
                        <h:inputText id="iniziovaliditaservizio" value="#{servizioCanoneController.servizioCanone.dataInizio}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Data fine di validità del servizio</td>
                    <td>
                        <h:inputText id="finevaliditaservizio" value="#{servizioCanoneController.servizioCanone.dataFine}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                </tr>
                <tr>
                    <td>Importo</td>
                    <td><h:inputText id="importo" value="#{servizioCanoneController.servizioCanone.importo}"/></td>
                </tr>
                <tr>
                    <td>IVA applicata</td>
                    <td><h:inputText id="iva" value="#{servizioCanoneController.servizioCanone.IVA}"/></td>
                </tr>

            </table>

                    <h:commandButton value="Crea" action="#{servizioCanoneController.crea_servizioCanone}" />
                    <h:commandButton value="Salva" action="#{servizioCanoneController.salva_servizioCanone}" />
                    <h:commandButton value="Elimina" action="#{servizioCanoneController.elimina_servizioCanone}" />
                    <h:commandButton value="Elenca Tutti" action="#{servizioCanoneController.elenca_tutti_servizioCanone}" />
                        
        </h:form>

        <a:ajax  name="dojo.table"
                    args="{topic: '/servizioCanone'}"
                    value="#{servizioCanoneController.query}" />
    </body>



</html>
</f:view>
