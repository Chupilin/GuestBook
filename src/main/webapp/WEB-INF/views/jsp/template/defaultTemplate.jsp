<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Title not static. Javascript changes title -->
    <title><spring:message code="guest.book"/></title>
    <link rel="icon" href="<c:url value="/resources/ico/ic_book_black_white_18dp.png"/>" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datetimepicker.min.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/guestbook.css"/>" />

    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.12.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/moment-with-locales.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-datetimepicker.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/guestbook.js"/>"></script>
</head>

<body onload='changeTitle("newTitle")'>
<div id="page-wrap">
    <div id="page">
        <div id="body">
            <div id="header">
                <tiles:insertAttribute name="header"/>
            </div>
            <div id="content">
                <div id="content-block">
                    <tiles:insertAttribute name="content"/>
                </div>
            </div>
        </div>
        <div id="footer">
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>
</div>

</body>
</html>