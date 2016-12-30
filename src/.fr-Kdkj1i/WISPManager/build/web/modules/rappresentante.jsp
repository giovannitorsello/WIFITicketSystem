
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
        <h:form id="form_rappresentante" >
            <p>Tipo di rapprsentante</p>
            <table>
                <tr>
                    <td>Rappresentante Persona Fisica</td>
                    <td>
                        <h:selectOneMenu id="rappresentantePersonaFisica"
                                         value="#{rappresentanteController.rappresentante.rappresentantePersonaFisica}">
                            <f:selectItems value="#{personaFisicaController.mapPersoneFisiche}" />
                        </h:selectOneMenu>
                    </td>
                    <td>
                        <h:commandButton value="Crea" action="#{rappresentanteController.crea_Rappresentante_PersonaFisica}" />
                    </td>
                </tr>
                <tr>
                    <td>Rappresentante Persona Giuridica</td>
                    <td>
                        <h:selectOneMenu id="rappresentantePersonaGiuridica"
                                         value="#{rappresentanteController.rappresentante.rappresentantePersonaGiuridica}">
                            <f:selectItems value="#{personaGiuridicaController.mapPersoneGiuridiche}" />
                        </h:selectOneMenu>
                    </td>
                    <td>
                        <h:commandButton value="Crea" action="#{rappresentanteController.crea_Rappresentante_PersonaGiuridica}" />
                    </td>
                </tr>
            </table>

                    <p>Note rappresentante</p>
                    <h:inputTextarea cols="100" rows="10"  id="noterappresentante" value="#{rappresentanteController.rappresentante.noteRappresentante}"/>
                    <p>Provvigione percentule clienti</p>
                    <h:inputText id="provvigionepercentulaeclienti" value="#{rappresentanteController.rappresentante.provvigionePercentuleClienti}"/>
                    <p>Premio mensile</p>
                    <h:inputText id="premiomensile" value="#{rappresentanteController.rappresentante.premiomensile}"/>                    
                    <p>Premio annuale</p>
                    <h:inputText id="premioannuale" value="#{rappresentanteController.rappresentante.premioannuale}"/>

                    <a:ajax  name="dojo.table"
                        args="{topic: '/rappresentante'}"
                        value="#{rappresentanteController.queryRappresentante}" />

            <h:commandButton value="Salva" action="#{rappresentanteController.salva_Rappresentante}" />
            <h:commandButton value="Elimina" action="#{rappresentanteController.elimina_Rappresentante}" />
            <h:commandButton value="Elenca Tutti" action="#{rappresentanteController.elenca_tutti}" />




        </h:form>
        <h:form id="clienti_rappresentante">
             <p>Clienti</p>
             <table>
                <tr>                    
                    <td>
                        <h:selectOneMenu id="clienti"
                                         value="#{rappresentanteController.cliente}"
                                         >

                            <f:selectItems value="#{clienteController.mapClienti}"/>
                        </h:selectOneMenu>
                    </td>
                    <h:commandButton value="Aggiungi" action="#{rappresentanteController.aggiungi_Cliente}" />
                    <h:commandButton value="Elimina" action="#{rappresentanteController.elimina_Cliente}" />                    

                </tr>                
            </table>
        </h:form>

    </body>
</html>
</f:view>
