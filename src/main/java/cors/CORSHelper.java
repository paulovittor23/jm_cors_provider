package cors;

import javax.servlet.http.*;

import cors.exception.*;

public class CORSHelper {
  
  private static final String ALLOW_METHODS = "GET, POST, PUT, DELETE";
  private static final String ALLOW_CREDENTIALS = "false";
  private static final String ALLOW_HEADERS = "X-Requested-With, X-Prototype-Version, XDomainRequest";
  private static final String MAX_AGE = "5"; 

  public boolean isPreflightRequest(HttpServletRequest request){
    return (request.getHeader("Origin") != null &&
        request.getHeader("Access-Control-Request-Method") != null &&
        request.getMethod() != null &&
          request.getMethod().equals("OPTIONS"));
  }
  
  public void handlePreflightRequest(HttpServletRequest request, HttpServletResponse response) 
  throws CORSInvalidRequestException, CORSOriginDeniedException, CORSUnsupportedHttpMethodException  {
    String originHeader = request.getHeader("Origin");
    String requestMethodHeader = request.getHeader("Access-Control-Request-Method");
    
    if(! ALLOW_METHODS.contains(requestMethodHeader))
      throw new CORSUnsupportedHttpMethodException();
    
    this.addControlHeaders(response, originHeader);
  }
  
  public boolean isOriginalRequest(HttpServletRequest request){
    return (request.getHeader("Origin") != null && 
          request.getMethod() != null &&
        !request.getMethod().equals("OPTIONS"));
  }
  
  public void handleOriginalRequest(HttpServletRequest request, HttpServletResponse response) 
  throws CORSInvalidRequestException, CORSOriginDeniedException, CORSUnsupportedHttpMethodException  {
    String originHeader = request.getHeader("Origin");
    String httpMethod = request.getMethod();
    
    if(! ALLOW_METHODS.contains(httpMethod))
      throw new CORSUnsupportedHttpMethodException();
    
    this.addControlHeaders(response, originHeader);
  }

  private void addControlHeaders(HttpServletResponse response, String origin) {
    response.addHeader("Access-Control-Allow-Origin", origin);
    response.addHeader("Access-Control-Allow-Credentials", ALLOW_CREDENTIALS);
    response.addHeader("Access-Control-Allow-Methods", ALLOW_METHODS);
    response.addHeader("Access-Control-Allow-Headers", ALLOW_HEADERS);
    response.addHeader("Access-Control-Max-Age", MAX_AGE);
  }
  
}
