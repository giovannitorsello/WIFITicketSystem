<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a" uri="http://java.sun.com/jmaki-jsf" %>

<f:view>
<html>
    <head>
        <title>Persone fisiche</title>
        <link rel="stylesheet" href="../css/jmaki-standard.css" type="text/css"></link>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
    </head>
    <body>

       
        <h:form id="form_personafisica">
            <p>Dati Anagrafici</p>
            <table id="table">
                <tr>
                    <td>Nome</td>
                    <td>
                        <h:inputText id="nome" value="#{personaFisicaController.personaFisica.nome}"/>
                    </td>

                    
                    <td>Cognome</td>
                    <td><h:inputText id="cognome" value="#{personaFisicaController.personaFisica.cognome}" />
                        <h:commandLink value="?" actionListener="#{personaFisicaController.findByCognome}" /></td>

                </tr>
                <tr>
                    <td>Data di nascita</td>
                    <td>
                        <h:inputText  id="dataNascita" value="#{personaFisicaController.personaFisica.dataNascita}">
                            <f:convertDateTime type="date" pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </td>
                    <td>Comune di nascita</td>
                    <td><h:inputText id="comuneNascita" value="#{personaFisicaController.personaFisica.comuneNascita}" /></td>
                </tr>

                <tr>
                    <td>Codice fiscale</td>
                    <td><h:inputText id="codiceFiscale" value="#{personaFisicaController.personaFisica.codiceFiscale}" /></td>
                </tr>
            </table>
            <p>Residenza</p>
            <table>
               <tr><td>Via</td><td>CAP</td><td>Comune</td><td>Provincia</td></tr>
               <tr>
                    <td><h:inputText id="residenzaVia" value="#{personaFisicaController.personaFisica.residenzaVia}" /></td>
                    <td><h:inputText id="residenzaCAP" value="#{personaFisicaController.personaFisica.residenzaCAP}" /></td>
                    <td><h:inputText id="residenzaComune" value="#{personaFisicaController.personaFisica.residenzaComune}" /></td>
                    <td><h:inputText id="residenzaProvincia" value="#{personaFisicaController.personaFisica.residenzaProvincia}" /></td>
                </tr>
            </table>

            <p>Domicilio</p>
            <table>               
               <tr><td>Via</td><td>CAP</td><td>Comune</td><td>Provincia</td></tr>
               <tr>
                    <td><h:inputText id="domicilioVia" value="#{personaFisicaController.personaFisica.domicilioVia}" /></td>
                    <td><h:inputText id="domicilioCAP" value="#{personaFisicaController.personaFisica.domicilioCAP}" /></td>
                    <td><h:inputText id="domicilioComune" value="#{personaFisicaController.personaFisica.domicilioComune}" /></td>
                    <td><h:inputText id="domicilioProvincia" value="#{personaFisicaController.personaFisica.domicilioProvincia}" /></td>
                </tr>
            </table>
            <p>Recapiti telefonici - email</p>
            <table>
               <tr><td>Telefono</td><td>Cellulare</td><td>Email</td></tr>
               <tr>
                    <td><h:inputText id="telefono" value="#{personaFisicaController.personaFisica.telefono}" /></td>
                    <td><h:inputText id="cellulare" value="#{personaFisicaController.personaFisica.cellulare}" /></td>
                    <td><h:inputText id="email" value="#{personaFisicaController.personaFisica.email}" /></td>
                </tr>
            </table>
            <p>Documenti</p>
            <table>
               <tr><td>Foto Tessera</td><td>Documento di Identità</td><td>Firma legibile</td><td>Firma digitale</td></tr>
               <tr>
                    <td><!--h:inputText id="fotoTesseraPersonale" value="{personaFisica.fotoTesseraPersonale}" /--></td>
                    <td><!--h:inputText id="copiaDocumentoIdentita" value="{personaFisica.copiaDocumentoIdentita}" /--></td>
                    <td><!--h:inputText id="firmaLeggibileDepositata" value="{personaFisica.firmaLeggibileDepositata}" /--></td>
                    <td><!--h:inputText id="firmaDigitale" value="{personaFisica.firmaDigitale}" /--></td>
                </tr>
            </table>
            
            <h:commandButton value="Crea" action="#{personaFisicaController.crea_personaFisica}" />
            <h:commandButton value="Salva" action="#{personaFisicaController.salva_personaFisica}" />
            <h:commandButton value="Elimina" action="#{personaFisicaController.elimina_personaFisica}" />         
        </h:form>

               <a:ajax  name="dojo.table"
                        args="{topic: '/personaFisica'}"
                        value="#{personaFisicaController.query}" />


    </body>

</html>
</f:view>
