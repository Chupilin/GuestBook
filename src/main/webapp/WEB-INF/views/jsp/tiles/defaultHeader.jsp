<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div align="right">
    <a href='?lang=ru'>ru</a> | <a href='?lang=en'>en</a>
</div>

<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <a class="home" href="${pageContext.request.contextPath}/messages">
            <p class="h1"><b><spring:message code="guest.book"/></b>
                <img src="<c:url value="/resources/ico/ic_book_black_white_18dp.png"/>">
            </p>
        </a>
    </div>
</div>

