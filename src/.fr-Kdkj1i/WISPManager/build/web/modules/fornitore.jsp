
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
        <h:form id="form_fornitore" >
            <p>Tipo di fornitore</p>
            <table>
                <tr>
                    <td>Cliente Persona Fisica</td>
                    <td>
                        <h:selectOneMenu id="fornitorePersonaFisica"
                                         value="#{fornitoreController.fornitore.fornitorePersonaFisica}">
                            <f:selectItems value="#{personaFisicaController.mapPersoneFisiche}" />
                        </h:selectOneMenu>
                    </td>
                    <td>
                        <h:commandButton value="Crea" action="#{fornitoreController.crea_Fornitore_PersonaFisica}" />
                    </td>
                </tr>
                <tr>
                    <td>Cliente Persona Giuridica</td>
                    <td>
                        <h:selectOneMenu id="fornitorePersonaGiuridica"
                                         value="#{fornitoreController.fornitore.fornitorePersonaGiuridica}">
                            <f:selectItems value="#{personaGiuridicaController.mapPersoneGiuridiche}" />
                        </h:selectOneMenu>
                    </td>
                    <td>
                        <h:commandButton value="Crea" action="#{fornitoreController.crea_Fornitore_PersonaGiuridica}" />
                    </td>
                </tr>
            </table>

                    <p>Note Fornitore</p>
                    <h:inputTextarea cols="100" rows="10"  id="notefornitore" value="#{fornitoreController.fornitore.noteFornitore}"/>
                    <a:ajax  name="dojo.table"
                        args="{topic: '/fornitore'}"
                        value="#{fornitoreController.queryFornitore}" />

            <h:commandButton value="Salva" action="#{fornitoreController.salva_Fornitore}" />
            <h:commandButton value="Elimina" action="#{fornitoreController.elimina_Fornitore}" />
            <h:commandButton value="Elenca Tutti" action="#{fornitoreController.elenca_tutti}" />



        </h:form>
    </body>
</html>
</f:view>
