<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>

<div class="col-sm-offset-2 col-sm-8">
    <h1 id="newTitle"><spring:message code="all.messages"/></h1>
</div>
<div class="col-sm-offset-2 col-sm-8">
    <b>
        <c:if test="${isSend}">
            <p class="result-operation text-success bg-success"><spring:message code="is.send"/></p>
        </c:if>
        <c:if test="${isUpdate}">
            <p class="result-operation text-warning bg-warning"><spring:message code="is.update"/></p>
        </c:if>
        <c:if test="${isDelete}">
            <p class="result-operation text-danger bg-danger"><spring:message code="is.delete"/></p>
        </c:if>
    </b>
</div>
<div class="col-sm-offset-2 col-sm-8" align="right">
    <a href="${pageContext.request.contextPath}/message"><spring:message code="new.message"/></a>
</div>
</div>


<c:forEach items="${messageList}" var="message">

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-2">${message.author}</div>
        <div class="col-sm-6">${message.dateCreate}</div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-6">${message.text}</div>
        <div class="col-sm-2" align="right">
            <form:form method="get" action="${pageContext.request.contextPath}/message/${message.id}">
                <button type="submit" id="btn-edit" class="btn btn-default">
                    <spring:message code="edit"/>
                </button>
            </form:form>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8 lastRow">
            <a href="" onclick="topPage()"><spring:message code="top.page"/></a>
        </div>
    </div>

</c:forEach>
