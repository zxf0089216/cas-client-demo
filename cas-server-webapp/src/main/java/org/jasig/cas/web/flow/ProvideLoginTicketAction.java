package org.jasig.cas.web.flow;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;  
  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.jasig.cas.CentralAuthenticationService;  
import org.jasig.cas.authentication.principal.Service;  
import org.jasig.cas.ticket.TicketException;  
import org.jasig.cas.util.UniqueTicketIdGenerator;  
import org.jasig.cas.web.support.WebUtils;  
import org.springframework.webflow.action.AbstractAction;  
import org.springframework.webflow.execution.Event;  
import org.springframework.webflow.execution.RequestContext;  

public class ProvideLoginTicketAction extends AbstractAction{  
      
    /** 3.5.1 - Login tickets SHOULD begin with characters "LT-" */  
    private static final String PREFIX = "LT";  
  
    /** Logger instance */  
    private final Log logger = LogFactory.getLog(getClass());  
  
  
    @NotNull  
    private UniqueTicketIdGenerator ticketIdGenerator;  
  
    public final String generate(final RequestContext context) {  
        final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);  
        this.logger.debug("Generated login ticket " + loginTicket);  
        WebUtils.putLoginTicket(context, loginTicket);  
        return "generated";  
    }  
  
    public void setTicketIdGenerator(final UniqueTicketIdGenerator generator) {  
        this.ticketIdGenerator = generator;  
    }  
    @Override  
    protected Event doExecute(RequestContext context) throws Exception {  
          
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);  
          
        if (request.getParameter("get-lt") != null && request.getParameter("get-lt").equalsIgnoreCase("true")) {  
              
            final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);  
            this.logger.debug("Generated login ticket " + loginTicket);  
            WebUtils.putLoginTicket(context, loginTicket);  
              
            return result("loginTicketRequested");  
        }  
        return result("continue");  
    }  
      
}  