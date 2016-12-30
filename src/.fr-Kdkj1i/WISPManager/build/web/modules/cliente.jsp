
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
            <table  cellpadding="10">
                <tr>
                    <td>                   
                   <h:form id="form_cliente">
                       <p>Tipo di cliente</p>
                       <table border="1">
                       <tr>
                        <td>
                            Cliente Persona Fisica
                            <h:selectOneMenu id="clientePersonaFisica"
                                             value="#{clienteController.cliente.clientePersonaFisica}">
                            <f:selectItems value="#{personaFisicaController.mapPersoneFisiche}" />
                            </h:selectOneMenu>
                            <h:commandButton value="Crea" action="#{clienteController.crea_Cliente_PersonaFisica}" />
                        </td>
                        </tr>
                        <tr>
                            <td>
                                Cliente Persona Giuridica
                                <h:selectOneMenu id="clientePersonaGiuridica"
                                         value="#{clienteController.cliente.clientePersonaGiuridica}">
                                    <f:selectItems value="#{personaGiuridicaController.mapPersoneGiuridiche}" />
                                </h:selectOneMenu>
                                <h:commandButton value="Crea" action="#{clienteController.crea_Cliente_PersonaGiuridica}" />
                            </td>
                        </tr>
                        <tr>
                           <td>
                                <p>Note Cliente</p>
                                <h:inputTextarea cols="100" rows="10"  id="notecliente" value="#{clienteController.cliente.noteCliente}"/>
                            </td>
                        </tr>
                        <tr>
                           <td>
                                <a:ajax  name="dojo.table" args="{topic: '/cliente'}"
                                        value="#{clienteController.queryCliente}" />
                        <h:commandButton value="Salva" action="#{clienteController.salva_Cliente}" />
                        <h:commandButton value="Elimina" action="#{clienteController.elimina_Cliente}" />
                        <h:commandButton value="Elenca Tutti" action="#{clienteController.elenca_tutti}" />
                           </td>
                        </tr>
                   </table>
                   </h:form>
                </td>
                <td>
                        <h:form id="servizi_cliente">
                            <p>Servizi</p>
                            <table border="1">
                                <tr>
                                    <td>Servizi a consumo</td>
                                    <td>
                                        <h:selectOneMenu id="servizi"
                                         value="#{clienteController.servizio}">
                                            <f:selectItems value="#{servizioController.mapServizi}"/>
                                        </h:selectOneMenu>
                                    </td>
                                </tr>
                            </table>
                            <h:commandButton value="Aggiungi" action="#{clienteController.aggiungi_Servizio}" />
                            <h:commandButton value="Elimina" action="#{clienteController.elimina_Servizio}" />
                        </h:form>
                        <br/>
                        <h:form id="ticket_cliente">
                            <p>Serie ticket</p>
                            <table border="1">
                                <tr>
                                    <td>Serie Ticket</td>
                                    <td>
                                        <h:selectOneMenu id="servizi" value="#{clienteController.serieTicket}">
                                            <f:selectItems value="#{clienteController.mapTicket}"/>
                                        </h:selectOneMenu>
                                    </td>
                                </tr>
                            </table>
                            <h:commandButton value="Elimina serie" action="#{clienteController.elimina_serieTicket}" />
                        </h:form>
                        <br/>
                        <h:form id="numerazioni_cliente">
                            <p>Numerazioni Telefono</p>
                            <table border="1">
                            <tr>
                                 <td>Area Internzionale</td>
                                  <td>
                                    <h:inputText id="areainternazionale" value="#{clienteController.numerazioneFonia.areaInternazionale}" />
                                  </td>
                            </tr>
                            <tr>
                                <td>Area Geografica</td>
                                <td>
                                    <h:inputText id="arealocale" value="#{clienteController.numerazioneFonia.areaLocale}" />
                                </td>
                            </tr>
                            <tr>
                                <td>Numero Telefono</td>
                                <td>
                                    <h:inputText id="numerotelefono" value="#{clienteController.numerazioneFonia.numeroTelefono}" />
                                </td>
                            </tr>
                            <tr>
                                <td>Tipo numerazione</td>
                                <td>
                                    <h:selectOneMenu id="tiponumerazione" value="#{clienteController.numerazioneFonia.tipoNumerazione}">
                                        <f:selectItem itemValue="normale"  itemLabel="normale" />
                                        <f:selectItem itemValue="verde"  itemLabel="numero verde" />
                                        <f:selectItem itemValue="commerciale"  itemLabel="commerciale" />
                                        <f:selectItem itemValue="emergenza"  itemLabel="emergenza" />
                                    </h:selectOneMenu>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h:commandButton value="Aggiungi numerazione" action="#{clienteController.aggiungi_NumerazioneFonia}" />
                                </td>
                            </tr>
                            <tr>
                                 <td>Numerazioni fonia associate al cliente</td>
                                 <td>
                                     <h:selectOneMenu id="numerazionifoniaassociate" value="#{clienteController.numerazioneFoniaSelection}">
                                         <f:selectItems value="#{clienteController.mapNumerazioni}"/>
                                     </h:selectOneMenu>
                                     <h:commandButton value="Elimina numerazione" action="#{clienteController.elimina_NumerazioneFonia}" />
                                 </td>
                             </tr>
                            </table>                                    
                        </h:form>
                    </td>
                </tr>
            </table>
    </body>
</html>
</f:view>
