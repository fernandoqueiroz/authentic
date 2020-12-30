package br.com.i9core.auth.infrastructure.delivery.controller;

import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.i9core.auth.infrastructure.adapter.ClientApplicationServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;

@Controller
@SessionAttributes({"authorizationRequest"})
public class ApprovalEndpoint {

    @Autowired
    ClientApplicationServiceAdapter clientApplicationServiceAdapter;

    @RequestMapping({"/oauth/custom_confirm_access"})
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {

        String approvalContent;
        AuthorizationRequest authorizationRequest = (AuthorizationRequest)model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();
        ClientDetails clientDetails = clientApplicationServiceAdapter.loadClientByClientId(clientId);
        if (clientDetails == null) {
            approvalContent = "Aplication not foud!";
        } else {
            approvalContent = this.createTemplate(model, request, clientDetails);
        }
        if (request.getAttribute("_csrf") != null) {
            model.put("_csrf", request.getAttribute("_csrf"));
        }

        View approvalView = new View() {
            public String getContentType() {
                return "text/html";
            }

            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                response.setContentType(this.getContentType());
                response.getWriter().append(approvalContent);
            }
        };
        return new ModelAndView(approvalView, model);
    }

    protected String createTemplate(Map<String, Object> model, HttpServletRequest request, ClientDetails clientDetails) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest)model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body><h1>Permitir acesso</h1>");
        builder.append("<p>Você autoriza \"").append(HtmlUtils.htmlEscape(clientId));
        builder.append("\" acessar suas informações?</p>");
        builder.append("<form id=\"confirmationForm\" name=\"confirmationForm\" action=\"");
        String requestPath = ServletUriComponentsBuilder.fromContextPath(request).build().getPath();
        if (requestPath == null) {
            requestPath = "";
        }

        builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
        builder.append("<input name=\"user_oauth_approval\" value=\"true\" type=\"hidden\"/>");
        String csrfTemplate = null;
        CsrfToken csrfToken = (CsrfToken)((CsrfToken)(model.containsKey("_csrf") ? model.get("_csrf") : request.getAttribute("_csrf")));
        if (csrfToken != null) {
            csrfTemplate = "<input type=\"hidden\" name=\"" + HtmlUtils.htmlEscape(csrfToken.getParameterName()) + "\" value=\"" + HtmlUtils.htmlEscape(csrfToken.getToken()) + "\" />";
        }

        if (csrfTemplate != null) {
            builder.append(csrfTemplate);
        }

        String authorizeInputTemplate = "<label><input name=\"authorize\" value=\"Permitir\" type=\"submit\"/></label></form>";
        if (!model.containsKey("scopes") && request.getAttribute("scopes") == null) {
            builder.append(authorizeInputTemplate);
            builder.append("<form id=\"denialForm\" name=\"denialForm\" action=\"");
            builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
            builder.append("<input name=\"user_oauth_approval\" value=\"false\" type=\"hidden\"/>");
            if (csrfTemplate != null) {
                builder.append(csrfTemplate);
            }

            builder.append("<label><input name=\"deny\" value=\"Negar\" type=\"submit\"/></label></form>");
        } else {
            builder.append(this.createScopes(model, request, clientDetails));
            builder.append(authorizeInputTemplate);
        }

        builder.append("</body></html>");
        return builder.toString();
    }

    private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request, ClientDetails clientDetails) {
        StringBuilder builder = new StringBuilder("<ul>");

        for (String scope : clientDetails.getScope()) {
            scope = HtmlUtils.htmlEscape(scope);
            builder.append("<li><div class=\"form-group\">");
            builder.append(scope).append(": <input type=\"radio\" name=\"");
            builder.append(scope).append("\" value=\"true\"").append(">Approve</input> ");
            builder.append("<input type=\"radio\" name=\"").append(scope).append("\" value=\"false\"");
            builder.append(">Deny</input></div></li>");
        }
        builder.append("</ul>");
        return builder.toString();
    }
}


