package org.jasig.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class ProvideLoginTicketAction extends AbstractAction {

    private static final String PREFIX = "LT";

    private final Log logger = LogFactory.getLog(getClass());

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;

    public void setTicketIdGenerator(final UniqueTicketIdGenerator generator) {
        this.ticketIdGenerator = generator;
    }

    @Override
    protected Event doExecute(RequestContext context) throws Exception {

        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

//        final String ticketGrantingTicket = WebUtils.getTicketGrantingTicketId(context);
//        if (ticketGrantingTicket != null) {
//            return result("newapp");
//        }

        if (request.getParameter("get-lt") != null && request.getParameter("get-lt").equalsIgnoreCase("true")) {
            final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);
            this.logger.info("--------------Generated login ticket :" + loginTicket);
            WebUtils.putLoginTicket(context, loginTicket);
            return result("loginTicketRequested");
        }

        return result("continue");
    }
}