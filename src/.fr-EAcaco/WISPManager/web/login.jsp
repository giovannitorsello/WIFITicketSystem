<%-- 
    Document   : login
    Created on : 3-ago-2009, 14.25.45
    Author     : torsello
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Login page</title>
        </head>
        <body>
            <h:form id="form_login">
            <table align="center">
                <tr>
                    <td>Utente</td>
                    <td><h:inputText id="user" value="#{LoginController.user}"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><h:inputText id="password" value="#{LoginController.password}"/></td>
                </tr>
            </table>
                    <center><h:commandButton id="login" value="login" action="#{LoginController.login}"/></center>
            </h:form>
        </body>
    </html>
</f:view>
