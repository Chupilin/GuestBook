<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="isNewMessage" value="${message.id==null ? 'true' : 'false'}"/>


<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <h1 id="newTitle"><spring:message code="${isNewMessage ? 'new.message' : 'update.message'}"/></h1>
    </div>
    <div class="col-sm-offset-2 col-sm-6" align="right">
        <a href="${pageContext.request.contextPath}/messages"><spring:message code="all.messages"/></a>
    </div>
    <div class="col-sm-12"></div>
</div>


<spring:url var="urlCreateMessage" value="/message" />
<spring:url var="urlUpdateMessage" value="/message/${message.id}" />
<form:form action="${isNewMessage ? urlCreateMessage : urlUpdateMessage}"
           method="${isNewMessage ? 'post' : 'put'}"
           commandName="message" modelAttribute="message"
           cssClass="form-horizontal">

    <c:if test="${!isNewMessage}">

        <spring:bind path="id">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="hiddenId"><spring:message code="id"/></label>
                <div class="col-sm-2">
                    <form:input path="id" class="form-control" id="hiddenId" readonly="true"/>
                </div>
                <div class="col-sm-8"></div>
            </div>
        </spring:bind>

        <spring:bind path="dateCreate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label" for="selectDate"><spring:message code="date"/></label>
                <div class="col-sm-2">
                    <div class="input-group date" id="datetimepicker">
                        <spring:message var="i18nPlaceholderDate" code="placeholder.date"/>
                        <form:input path="dateCreate" class="form-control" id="selectDate"
                                placeholder="${i18nPlaceholderDate}"/>
                            <span class="input-group-addon">
                                <span class="glyphicon-calendar glyphicon"></span>
                            </span>
                        </div>
                </div>
                <div class="col-sm-8">
                    <form:errors path="dateCreate" cssClass="control-label" delimiter=" "/>
                </div>
            </div>
        </spring:bind>

    </c:if>

    <spring:bind path="text">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label class="col-sm-2 control-label" for="inputText"><spring:message code="text"/></label>
            <div class="col-sm-6">
                <spring:message var="i18nPlaceholderText" code="placeholder.text"/>
                <form:textarea class="form-control" path="text" id="inputText" rows="3"
                               placeholder="${i18nPlaceholderText}"/>
            </div>
            <div class="col-sm-4">
                <form:errors path="text" cssClass="control-label" delimiter=" "/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="author">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label class="col-sm-2 control-label" for="inputAuthor"><spring:message code="author"/></label>
            <div class="col-sm-6">
                <div class="input-group">
                    <spring:message var="i18nPlaceholderAuthor" code="placeholder.author"/>
                    <form:input class="form-control" path="author" id="inputAuthor"
                                placeholder="${i18nPlaceholderAuthor}"/>
                <span class="input-group-btn">
                    <c:if test="${!isNewMessage}">
                        <button type="submit" id="btn-save" class="btn btn-warning">
                            <spring:message code="save"/>
                        </button>
                    </c:if>
                    <c:if test="${isNewMessage}">
                        <button type="submit" id="btn-send" class="btn btn-success">
                            <spring:message code="send"/>
                        </button>
                    </c:if>
                </span>
                </div>
            </div>
            <div class="col-sm-4">
                <form:errors path="author" cssClass="control-label" delimiter=" "/>
            </div>
        </div>
    </spring:bind>

</form:form>

<c:if test="${!isNewMessage}">
    <form:form action="${pageContext.request.contextPath}/message/${message.id}"
               method="delete" modelAttribute="message" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" id="btn-delete" class="btn btn-danger">
                    <spring:message code="delete"/>
                </button>
            </div>
        </div>
    </form:form>
</c:if>

<script type="text/javascript">
    $(function () {
        $("#datetimepicker").datetimepicker({
            format: "YYYY-MM-DD",
            minDate:"1970-01-01",
            maxDate:"2049-12-31"
        });
    });
</script>