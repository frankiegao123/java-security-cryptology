package xyz.frankiegao123.crypto.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.frankiegao123.crypto.bean.CryptoData;
import xyz.frankiegao123.crypto.bean.UserData;
import xyz.frankiegao123.crypto.service.base.Cryptology;
import xyz.frankiegao123.crypto.util.ProviderUtil;
import xyz.frankiegao123.crypto.util.Tools;

/**
 * Servlet implementation class MessageDigestServlet
 */
public class CryptologyServlet extends HttpServlet {

    private final static String CRYPTOLOGY_DIR   = "/cryptology/";

    private static final long   serialVersionUID = 1L;

    public CryptologyServlet() {
        super( );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost( request , response );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Map<String, String> map = ServletUtil.parameter2Map( request );

        ServiceLocator locator = ServiceLocator.getCryptology( request.getParameter( "service" ) );
        if ( locator == null ) {
            request.setAttribute( "info" , "数据加载错误 " + request.getParameter( "service" ) );
            ServletUtil.forward( "/PageError.jsp" , getServletContext( ) , request , response );
            return;
        }

        UserData ud = UserData.newInstance( map );
        if ( ud.isEmptyData( ) ) {
            request.setAttribute( "info" , "数据不能为空" );
            ServletUtil.forward( CRYPTOLOGY_DIR + locator.getPageName( ) , getServletContext( ) , request , response );
            return;
        }

        if ( Tools.isEmpty( ud.getAlgorithm( ) ) ) {
            request.setAttribute( "info" , "算法不能为空" );
            ServletUtil.forward( CRYPTOLOGY_DIR + locator.getPageName( ) , getServletContext( ) , request , response );
            return;
        }

        ProviderUtil.usingBouncyCastle( ud.isUsingBouncyCastle( ) );

        Cryptology cryptology = locator.getCryptology( ud );
        CryptoData result = null;
        try {
            result = cryptology.encrypt( );
            request.setAttribute( "result" , result );
            request.setAttribute( "info" , "成功" );
        }
        catch ( Exception e ) {
            request.setAttribute( "info" , e.getMessage( ) );
        }

        ServletUtil.forward( CRYPTOLOGY_DIR + locator.getPageName( ) , getServletContext( ) , request , response );
    }
}
