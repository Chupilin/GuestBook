<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" trimDirectiveWhitespaces="true"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="form-group">
  <div class="col-sm-offset-2 col-sm-10">
    <h1 id="newTitle"><spring:message code="error.page.500"/></h1>
  </div>
  <div align="center">
    <img src="<c:url value="/resources/images/500.png"/>"/>
  </div>
</div>