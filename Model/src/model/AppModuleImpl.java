package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 27 11:49:52 MDT 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleImpl extends ApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleImpl() {
        
    }
    
    public String getMetodoCarga(Integer serie, Long identificador) {
        String resp = "";
        //Creamos el Statement para obtener la conexi�n
        Statement st;
        st = getDBTransaction().createStatement(0);
        OracleCallableStatement stmt = null;

        try {
          Connection conn = st.getConnection();
          stmt = 
              (OracleCallableStatement)conn.prepareCall("{ = call RE_CAJAS_LOGIN_WRAPPER.CALL_GET_ESTATUS_CORTE() }");
          stmt.execute();
          resp = stmt.getString(1);

        } 
        catch (SQLException e) {
          e.printStackTrace();
        } 
        finally {
          if (st != null && stmt != null) {
            try {
              st.close();
              stmt.close();
            } catch (Exception e) {
             e.printStackTrace();
            }
          }
        }
        return resp;
        }
}
