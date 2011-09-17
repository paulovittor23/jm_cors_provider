package cors.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import cors.CORSHelper;
import cors.exception.*;

public class CORSFilter implements Filter {

  public CORSHelper corsHelper = new CORSHelper();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
  throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    try {
      //trata requisições preflighted
      if (corsHelper.isPreflightRequest(request)) {
        corsHelper.handlePreflightRequest(request, response);
        filterChain.doFilter(request, response);
        return;
      }
      //trata requisições originais (subsequentes às requisições preflighted)
      if (corsHelper.isOriginalRequest(request)) {
        corsHelper.handleOriginalRequest(request, response);
        filterChain.doFilter(request, response);
        return;
      }
      // trata requisições do mesmo domínio
      filterChain.doFilter(request, response);
      
    } catch (CORSInvalidRequestException e) {
      printMessage(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (CORSOriginDeniedException e) {
      printMessage(response, HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    } catch (CORSUnsupportedHttpMethodException e) {
      printMessage(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, e.getMessage());
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void destroy() {}

  private void printMessage(final HttpServletResponse response, final int sc, final String msg) 
  throws IOException, ServletException {
    response.setStatus(sc);
    response.resetBuffer();
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    out.println("Filtro CORS: " + msg);
  }

} 