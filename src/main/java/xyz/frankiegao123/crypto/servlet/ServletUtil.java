package xyz.frankiegao123.crypto.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletUtil {

    public static void saveParameter(ServletRequest request) {
        for ( Enumeration<?> e = request.getParameterNames( ) ; e.hasMoreElements( ) ; ) {
            String name = String.valueOf( e.nextElement( ) );
            request.setAttribute( name , request.getParameter( name ) );
        }
    }

    public static Map<String, String> parameter2Map(ServletRequest request) {
        Map<String, String> map = new HashMap<String, String>( );
        for ( Enumeration<?> e = request.getParameterNames( ) ; e.hasMoreElements( ) ; ) {
            String name = String.valueOf( e.nextElement( ) );
            map.put( name , request.getParameter( name ) );
        }
        return map;
    }

    public static void forward(String url, ServletContext context, ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        context.getRequestDispatcher( url ).forward( request , response );
    }
}
