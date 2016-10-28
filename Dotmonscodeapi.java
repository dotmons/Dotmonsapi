/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Dotmons
 */
import java.util.Calendar;
import java.sql.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

public class Dotmonscodeapi {

    static Statement st;
    static Connection cnn = getconnection();
    static Random ran;
    static int rand = 0;
    static java.util.ArrayList<String> vec = new java.util.ArrayList<>();
    static String driver = "jdbc:mysql://localhost:3306/leadsway";
    static String user = "root";
    static String password = "tiger";
    
    
    public static Connection getconnection()
    {
       Connection cn=null;
       try
        {          
       Class.forName("com.mysql.jdbc.Driver");
       cn = DriverManager.getConnection(driver, user, password);
        }
                        
        catch(Exception e)
        {
        System.out.println("Connections Error connecting to database at connect.class = "+e);
        }
        return cn;
    }
    
   public boolean closeconnection(Connection cn)
    {
       try
        {
          cn.close();
          return true;
        }
       catch(Exception e)
        {
          return false;
        }
    } 

    public static String timenow() {
        Calendar cd = Calendar.getInstance();
        int sec = (cd.get(Calendar.SECOND));
        int min = (cd.get(Calendar.MINUTE));
        int hr = (cd.get(Calendar.HOUR_OF_DAY));

        return (zero(hr)) + ":" + (zero(min)) + ":" + (zero(sec));
    }

    public static String datenow() {
        Calendar cd = Calendar.getInstance();
        int sec = (cd.get(Calendar.MONTH) + 1);
        int min = (cd.get(Calendar.DAY_OF_MONTH));
        int hr = (cd.get(Calendar.YEAR));
        return (zero(min)) + "-" + (month(Integer.parseInt(zero(sec)))) + "-" + (zero(hr));
    }

    public static String datenoformat() {
        Calendar cd = Calendar.getInstance();
        int sec = (cd.get(Calendar.MONTH) + 1);
        int min = (cd.get(Calendar.DAY_OF_MONTH));
        int hr = (cd.get(Calendar.YEAR));
        return (zero(hr)) + "-" + (zero(sec)) + "-" + (zero(min));
    }

    public static String zero(int num) {
        String number = (num < 10) ? ("0" + num) : ("" + num);
        return number;
    }

    public static String month(int num) {
        switch (num) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "DEC";
        }
    }

    /*public static boolean closeconnection(Connection cn)
    {
       try
        {
         // cn.close();
          return true;
        }
       catch(Exception e)
        {
          return false;
        }
    } 
   
     * 
     */
    public static void writeToFileByLine(String filename, String[] data) {

        BufferedWriter bufferedWriter = null;

        try {

            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(filename));

            //Start writing to the output stream
            bufferedWriter.write("Writing line one to file");
            bufferedWriter.newLine();
            bufferedWriter.write("Writing line two to file");

            for (int i = 0; i < data.length; i++) {
                bufferedWriter.write(data[i]);
                bufferedWriter.newLine();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String fileinputstream(String input)//where input represents the path of the file to be used
    {
        FileInputStream filea;
        byte bytearray[];
        String dance = null;
        int size = 0;
        File tes;
        try {

            filea = new FileInputStream(input);
            size = filea.available();
            bytearray = new byte[size];

            if (filea.read(bytearray) == size) {
                dance = (new String(bytearray, 0, size));
            }

        } catch (Exception e) {
        }
        return dance;

    }

    public static void fileoutputstream(String input, String location)//In here, input represents the path for output files.
    {
        try {
            byte value[] = input.getBytes();
            FileOutputStream fileout = new FileOutputStream(location);
            fileout.write(value);
        } catch (Exception e) {
            System.out.println("Specify path ur self " + e);

        }

    }

    public static void fileSender(InputStream inStream, OutputStream outStream) {
        try {

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static boolean isNumber(String tt) {
        String dat[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0",};

        for (int i = 0; i <= 9; i++) {
            if (dat[i].equals(tt)) {
                return true;
            }
        }
        return false;
    }

    public static Object getImage(String reg, String table) {

        Object rr = "";
        try {
            String stg = "select * from " + table + " where regid = " + reg;
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(stg);
            while (rs.next()) {
                rr = rs.getObject("Image");
            }
            st.close();
            // closeconnection(cn);
        } catch (Exception e1) {
        }
        return rr;
    }

    public static boolean isExist(String regid, String table, String id) {
        String stg = "";
        try {
            stg = "SELECT " + regid + " from " + table + " where " + regid + " = '" + id + "'";
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(stg);
            System.out.println(stg);
            while (rs.next()) {
                String rg = rs.getString(regid);
                return true;
            }
            st.close();
//         closeconnection(cn);
        } catch (Exception exception) {
java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, "{0}Sql: {1}", new Object[]{exception.toString(), stg});            
            return false;
        }
        return false;
    }

    public static boolean isExist(String regid, String table, String id, String reg) {
        String stg = "";
        try {
            stg = "SELECT " + regid + " from " + table + " where " + reg + " = '" + id + "'";
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(stg);
            System.out.println(stg);
            while (rs.next()) {
                String rg = rs.getString(regid);
                return true;
            }
            st.close();
//         closeconnection(cn);
        } catch (Exception exception) {
java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, "{0}Sql: {1}", new Object[]{exception.toString(), stg});            
            return false;
        }
        return false;
    }

    
    
    public static boolean deleteItem(String table, String pritable, String id) {
        String stg = "";
        try {
            stg = "delete from " + table + " where " + pritable + " = '" + id + "'";
            Connection cn = cnn;
            st = cn.createStatement();
            st.execute(stg);
            st.close();
//           boolean closed = closeconnection(cn);
            return true;
        } catch (Exception exception) {
            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, "{0}Sql: {1}", new Object[]{exception.toString(), stg});
            return false;
        }
    }

    public static boolean update(String table, String field, String value, String id, String prifield) {
        String stg = "";
        try {
            stg = "UPDATE " + table + " set " + field + " = '" + value + "' where " + id + " = '" + prifield + "'";
            Connection cn = cnn;
            st = cn.createStatement();
            st.executeUpdate(stg);
            st.close();
            return true;
        } catch (Exception exception) {
            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, "{0}Sql: {1}", new Object[]{exception.toString(), stg});
            return false;
        }
    }
//
//    public static void main(String[] args) {
//        Dotmonscodeapi a = new Dotmonscodeapi();
//        System.out.println(a.convertToPasswordHash("adeoye"));
//    }

    public static java.util.ArrayList selectSomeElements(java.util.ArrayList arrList, String table) {

        String stg = "";
        String sst = "";

        java.util.ArrayList externalArrayList = new java.util.ArrayList<>();

        for (int i = 1; i < arrList.size(); i++) {
            stg += (arrList.get(i - 1) + ", ");
        }

        stg += arrList.get(arrList.size() - 1);
        try {
            sst += "SELECT " + stg + " FROM " + table;
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sst);
            while (rs.next()) {       //System.out.println(rs.getString(++jj));   
                java.util.ArrayList internalArrayList = new java.util.ArrayList<>();
                for (Object strlist : arrList) {
                    internalArrayList.add(rs.getString(strlist.toString()));
                }
                externalArrayList.add(internalArrayList);
            }
            st.close();
        } catch (Exception exception) {
            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, exception.toString());
        }
        return externalArrayList;
    }

    public static int getSize(String sql, String field) {
        int jj = 0;
        java.util.ArrayList arr = new java.util.ArrayList();

        try {
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {       //System.out.println(rs.getString(++jj));   
                arr.add(rs.getString(field));
            }

            st.close();
            //closeconnection(cn);
        } catch (Exception e1) {
            System.out.println("Operation Unsuccessful");
            System.out.println("Submit = " + e1);
        }
        return arr.size();
    }

    public static java.util.ArrayList selectSql(String sql, String field) {

        int jj = 0;
        java.util.ArrayList arr = new java.util.ArrayList();

        try {
            ResultSet rs;
            st = cnn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {//System.out.println(rs.getString(++jj))
                arr.add(rs.getString(field));
            }

            st.close();
            // closeconnection(cn);
        } catch (Exception e1) {
            System.out.println("Operation Unsuccessful select sql = " + sql);
            System.out.println("Submit = " + e1);
        }
        return arr;
    }

    public static boolean isExecuteSql(String sql) {
        
        int jj = 0;
        java.util.ArrayList arr = new java.util.ArrayList();

        try {
            ResultSet rs;
            st = cnn.createStatement();
            rs = st.executeQuery(sql);
            st.close();
            return true;
            //  closeconnection(cn);
        } catch (Exception e1) {
            System.out.println("Operation Unsuccessful select sql = " + sql);
            System.out.println("Submit = " + e1);
            return false;
        }

    }
    

    public static java.util.ArrayList selectElementAsc(String sta[], String table, String field1, String field2, int obj) {

        String stg = "";
        String sst = "";
        int jj = 0;
        java.util.ArrayList arr1 = new java.util.ArrayList();
        java.util.ArrayList arr2 = new java.util.ArrayList();

        for (int i = 1; i < sta.length; i++) {
            stg += (sta[i - 1] + ", ");
        }

        stg += sta[sta.length - 1];
        try {
            sst += "SELECT " + stg + " FROM " + table + " order by " + field1 + " asc";
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sst);
            while (rs.next()) {       //System.out.println(rs.getString(++jj));   

                arr1.add(rs.getString(field1));
                arr2.add(rs.getString(field2));
            }

            st.close();
//         closeconnection(cn);
        } catch (Exception e1) {
            System.out.println(sst);
            System.out.println("Operation Unsuccessful");
            System.out.println("Submit = " + e1);
        }
        if (obj == 0) {
            return arr1;
        }
        return arr2;
    }

    public static java.util.ArrayList selectSomeElementAsc(String sta[], String table, String field1) {

        String stg = "";
        String sst = "";
        int jj = 0;
        java.util.ArrayList arr1 = new java.util.ArrayList();

        for (int i = 1; i < sta.length; i++) {
            stg += (sta[i - 1] + ", ");
        }

        stg += sta[sta.length - 1];
        try {
            sst += "SELECT " + stg + " FROM " + table + " order by " + field1 + " asc";
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sst);
            while (rs.next()) {       //System.out.println(rs.getString(++jj));   
                arr1.add(rs.getString(field1));
            }

            st.close();
//         closeconnection(cn);
        } catch (Exception e1) {
            System.out.println(sst);
            System.out.println("Operation Unsuccessful");
            System.out.println("Submit = " + e1);
        }
        return arr1;
    }

    public static java.util.ArrayList selectElementById(String sta[], String table, String idfield, String id) {

        String stg = "";
        String sst = "";
        int j = 0;
        java.util.ArrayList arr = new java.util.ArrayList();

        while (j < sta.length - 1) {
            stg += (sta[j++] + ", ");
        }
        stg += sta[sta.length - 1];
        j = 0;
        try {
            sst += "SELECT " + stg + " FROM " + table + " where " + idfield + " = '" + id + "'";//select ekno from corper where posted = 0
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sst);
            while (rs.next()) {
                while (j < sta.length) {
                    arr.add(rs.getString(++j));
                }
                /*  for(int i=1; i<=sta.length; i++)
              {arr.add(rs.getString(i)); 
              }*/
            }
            st.close();
            System.out.println(sst);
//         closeconnection(cn);
        } catch (Exception e1) {
            System.out.println("Operation Unsuccessful @ database.selectElementById");
            System.out.println("Submit = " + e1);
            System.out.println(sst);
            return arr;
        }
        return arr;
    }

    public static boolean updateAllElements(String sta[], String table, String val[], String id, String prifield) {
        String stg = "";
        String sst = "";
        int j = 0;
        java.util.ArrayList arr = new java.util.ArrayList();
        while (j < sta.length - 1) {
            stg += (sta[j] + " = '" + val[j++] + "', ");
        }
        stg += sta[sta.length - 1] + " = '" + val[sta.length - 1] + "'";
        j = 0;

        try {
            sst += "update " + table + " set " + stg + " where " + id + " = '" + prifield + "'";
            Connection cn = cnn;
            st = cn.createStatement();
            st.executeUpdate(sst);
            st.close();
//         closeconnection(cn);
            return true;
        } catch (Exception e1) {
            System.out.println(sst);
            System.out.println("database.updateallelements = " + e1);
            return false;
        }
    }

    public static boolean insertAllElement(java.util.ArrayList dblist, String table)//, String table, String idfield, String id)
    {
        String stg = "";
        String sst = "";
        int j = 0;
        while (j < dblist.size() - 1) {
            stg += ("'" + dblist.get(j++) + "', ");
        }

        stg += "'" + dblist.get(dblist.size() - 1) + "'";
        sst += "insert into " + table + " values (" + stg + ")".toUpperCase();
        sst = sst.toUpperCase();

        try {
            Connection cn = cnn;
            st = cn.createStatement();
            st.execute(sst);
            st.close();
//           boolean closed = closeconnection(cn);
            return true;
        } catch (Exception e1) {
            System.out.println("database.insertallelement" + e1);
            System.out.println("sql code = " + sst);
        }
        return false;
    }

    public static boolean insertAllSingleElement(String sta[], String table, int size)//, String table, String idfield, String id)
    {
        String stg = "";
        String sst = "";

        try {
            Connection cn = cnn;
            st = cn.createStatement();
            // System.out.println("Length = " + sta.length);
            for (int i = 0; i < size; i++) {
                sst = "insert into " + table + " values (0,'" + sta[i] + "')";
                sst = sst.toUpperCase();
                // System.out.println(sst);
                st.execute(sst);
                // st.close();
            }

//           boolean closed = closeconnection(cn);
            return true;
        } catch (Exception e1) {
            System.out.println("database.insertallelement" + e1);
            System.out.println("sql code = " + sst);
        }
        return false;
    }

    public static boolean insertZonalElement(String sta[], String table, int size)//, String table, String idfield, String id)
    {
        String stg = "";
        String sst = "";

        try {
            Connection cn = cnn;
            st = cn.createStatement();
            // System.out.println("Length = " + sta.length);
            for (int i = 0; i < size; i++) {
                sst = "insert into " + table + " values (0, '', '" + sta[i] + "', '')";
                sst = sst.toUpperCase();
                //  System.out.println(sst);
                st.execute(sst);
                // st.close();
            }

//           boolean closed = closeconnection(cn);
            return true;
        } catch (Exception e1) {
            System.out.println("database.insertallelement" + e1);
            System.out.println("sql code = " + sst);
        }
        return false;
    }

    public static boolean insertAllSingleElements(String sta[], String table, int size)//, String table, String idfield, String id)
    {
        String stg = "";
        String sst = "";

        try {
            Connection cn = cnn;
            st = cn.createStatement();
            System.out.println("Length = " + sta.length);
            for (int i = 0; i < size; i++) {
                sst = "insert into " + table + " values (0, '" + sta[i] + "', 0)";
                sst = sst.toUpperCase();
                //  System.out.println(sst);
                st.execute(sst);
                // st.close();
            }

//           boolean closed = closeconnection(cn);
            return true;
        } catch (Exception e1) {
            System.out.println("database.insertallelement" + e1);
            System.out.println("sql code = " + sst);
        }
        return false;
    }

    public static String convertToPasswordHash(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            }
            return sb.toString();
        } catch (Exception e1) {
            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, "<<<ConvertToPasswordHash: Error creating connection, error found >>>{0}", e1.toString());
        }
        return "";
    }

    public static String getRandomize() {

        rand = ran.nextInt(vec.size());
        return vec.get(rand);  //ran.nextInt(9);
    }

    public static String getRandomPassword() {
        ran = new Random();
        String rander = "";
        for (int i = 48; i <= 122; i++) {
            if ((i > 57) && (i < 65) || ((i > 90) && (i < 97))) {
                continue;
            }
            //System.out.println((char)i);
            vec.add(String.valueOf((char) i));
            //i++;
        }
        for (int i = 0; i <= 8; i++) {
            rander = rander + getRandomize();
        }
        return rander;
    }

    //Uncomment setSession, killSession, and getSessionUsername when running web page container
    
//    public static void setSession(String sessionName, String sessionValue) {
//
//        try {
//            session.put(sessionName, sessionValue);
//            System.out.println("Setting session successful for " + sessionName);
//        } catch (Exception exception) {
//        java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, exception.toString());
//        }
//    }
//
//    public static void killSession(String sessionName) {
//        try {
//            session.remove(sessionName);
//        } catch (Exception exception) {
//            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, exception.toString());
//        }
//    }
//
//    public static String getSessionUsername() {
//        try {
//            return session.get("username").toString();
//        } catch (Exception exception) {
//            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, exception.toString());
//            return "";
//        }
//    }

    public static java.util.ArrayList selectSomeElementsWithQuery(java.util.ArrayList arrList, String sql) {

        java.util.ArrayList externalArrayList = new java.util.ArrayList<>();
        try {
            Connection cn = cnn;
            ResultSet rs;
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {       //System.out.println(rs.getString(++jj));   
                java.util.ArrayList internalArrayList = new java.util.ArrayList<>();
                for (Object strlist : arrList) {
                    internalArrayList.add(rs.getString(strlist.toString()));
                }
                externalArrayList.add(internalArrayList);
            }
            st.close();
        } catch (Exception exception) {
            java.util.logging.Logger.getLogger(Dotmonscodeapi.class.getName()).log(java.util.logging.Level.SEVERE, exception.toString());
        }
        return externalArrayList;
    }

    public static int getLastDate() {
        Calendar calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDate);
        return (calendar.get(Calendar.DAY_OF_MONTH));
    }
}
