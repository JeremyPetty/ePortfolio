package cs499application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sqlConnection {

    private static Connection con;
    private static String status;
    private static users curentUser;

    public static void ConnectDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //We create a connection providin db name, username-> root Password -> null  
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/resourcesdb", "root", "");
            status = "Connected...";
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            status = "Not Connected";
        }

    }

    public static void CreateResourceTables() {
        String tbleOne = "resource";

        //Check if table resource exists
        if (!checkTable(tbleOne)) {

            try {
                String create = "CREATE TABLE resource ("
                        + " resourceName varchar(45) NOT NULL,"
                        + " resourceId varchar(30) NOT NULL,"
                        + " source varchar(50) NOT NULL,"
                        + " briefDescription text NOT NULL,"
                        + " providerEmail varchar(25) NOT NULL,"
                        + " price varchar(20) NOT NULL,"
                        + "resourceType varchar(30) NOT NULL,"
                        + "CreationDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + " PRIMARY KEY (resourceId)"
                        + ") ";

                Statement st = con.createStatement();
                st.execute(create);

            } catch (SQLException ex) {
                Logger.getLogger(sqlConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void CreateUserTables() {
        String tble = "users";

        //Check if table resource exists
        if (!checkTable(tble)) {

            try {
                String create = "CREATE TABLE user ("
                        + " UserName varchar(45) NOT NULL,"
                        + " password varchar(30) NOT NULL,"
                        + " UserEmail varchar(50) NOT NULL,"
                        + " CreationDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                        + " PRIMARY KEY (UserEmail)"
                        + ") ";

                Statement st = con.createStatement();
                st.execute(create);

            } catch (SQLException ex) {

                System.out.println("" + ex);
            }
        }
    }

    public static boolean checkTable(String tableName) {

        try {
            DatabaseMetaData dbm = con.getMetaData();
            // check if table exista
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (tables.next()) {

                System.out.println("Exists");
                return true;

            } else {

                System.out.println("Does Not Exists");
                return false;
            }
        } catch (SQLException ex) {

            System.out.println(" " + ex);
        }

        return false;
    }

    public static boolean registerClient(users user) {

        boolean result = false;
        String uname = user.getUsernme();
        String userEmail = user.getUserEmail();
        String userPass = user.getUsrepass();

        if (checkUser(userEmail)) {

            System.out.println("User Exists Bro!!");
            return false;
        }

        //Using INSERT statement to insert data into our database
        String query = "INSERT INTO users (UserName,password,userEmail) VALUES('" + uname + "','" + userPass + "','" + userEmail + "')";

        try {

            Statement st = con.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                return true;
            }
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return result;

    }

    /*We check if user exists first, so that we do not duplicate user details like emails*/
    public static boolean checkUser(String userEmail) {

        String query = "SELECT * FROM users WHERE userEmail = '" + userEmail + "' ";

        try {

            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);

            return rst.next();

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return false;
    }

    public static boolean logIn(String userEmail, String pass) {

        String query = "SELECT * FROM users WHERE userEmail = '" + userEmail + "' AND password =  '" + pass + "'";

        try {

            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);

            curentUser = new users();
            if (rst.next()) {

                curentUser.setUserEmail(rst.getString("userEmail"));
                curentUser.setUsernme("UserName");
                curentUser.setUsrepass(rst.getString("password"));
                curentUser.setIsRegistered(true);
                return true;
            } else {

                curentUser.setIsRegistered(false);
                return false;
            }

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return false;
    }

    public static ArrayList<resource> getResources() {

        String query = "SELECT * FROM  resource ";

        ArrayList<resource> resources = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                resource rsc = new resource();
                rsc.setPrice(rst.getString("price"));
                rsc.setResoucetype(rst.getString("resourceType"));
                rsc.setResourceName(rst.getString("resourceName"));
                rsc.setResource_id(rst.getString("resourceId"));
                rsc.setSource(rst.getString("source"));
                rsc.setProvider(rst.getString("providerEmail"));
                rsc.setResourceDescription(rst.getString("briefDescription"));
                rsc.setCreationDate(rst.getString("CreationDate"));

                resources.add(rsc);

            }

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return resources;
    }

    public static ArrayList<resource> getResources(String hint) {

        String query = "SELECT * FROM  resource WHERE resourceName LIKE '%" + hint + "%' OR source LIKE '%" + hint + "%' OR briefDescription LIKE '%" + hint + "%' "
                + " OR providerEmail LIKE '%" + hint + "%' OR  price LIKE '%" + hint + "%'  OR resourceId LIKE '%" + hint + "%' "
                + " OR resourceType LIKE '%" + hint + "%'";

        ArrayList<resource> resources = new ArrayList<>();
        try {

            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                resource rsc = new resource();

                rsc.setPrice(rst.getString("price"));
                rsc.setResoucetype(rst.getString("resourceType"));
                rsc.setResourceName(rst.getString("resourceName"));
                rsc.setResource_id(rst.getString("resourceId"));
                rsc.setSource(rst.getString("source"));
                rsc.setProvider(rst.getString("providerEmail"));
                rsc.setResourceDescription(rst.getString("briefDescription"));

                resources.add(rsc);

            }

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return resources;
    }

    public static boolean addResource(resource rsc) {

        String query = "INSERT INTO resource (resourceName,resourceId,source,briefDescription,providerEmail,price,resourceType)"
                + " VALUES('" + rsc.getResourceName() + "','" + rsc.getResource_id() + "','" + rsc.getSource() + "','" + rsc.getResourceDescription() + "',"
                + "'" + rsc.getProvider() + "','" + rsc.getPrice() + "','" + rsc.getResoucetype() + "')";

        try {

            Statement st = con.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                return true;
            }

        } catch (SQLException exc) {

            System.out.println(" " + exc);

        }

        return false;
    }

    /*tO REMOVE A RESOURCE, THE USER MUST BE THE ORNER OF HE RESOURCE*/
    public static boolean removeResource(String resourceId) {

        String query = "DELETE FROM resource WHERE resourceId = '" + resourceId + "' AND providerEmail = '" + curentUser.getUserEmail() + "' ";

        try {

            Statement st = con.createStatement();
            int val = st.executeUpdate(query);
            System.out.println(" Value " + val);

            if (val >= 1) {

                return true;
            }

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return false;
    }

    //This function will update current users books only
    public static boolean updateResource(String Column, String resourceId, String newValue) {

        String query = "UPDATE resource SET " + Column + " = '" + newValue + "' WHERE resourceId = "
                + "'" + resourceId + "' AND providerEmail = '" + curentUser.getUserEmail() + "'";

        try {

            Statement st = con.createStatement();
            int val = st.executeUpdate(query);
            if (val >= 1) {

                return true;
            }
        } catch (SQLException exc) {
            System.out.println(" " + exc);
        }

        return false;
    }

    public static String getStatus() {
        return status;
    }

    public static users getCurentUser() {

        return curentUser;
    }

}
