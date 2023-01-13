package ceng.ceng351.cengvacdb;

import java.sql.*;
import java.util.Vector;

import ceng.ceng351.cengvacdb.QueryResult.UserIDuserNameAddressResult;

public class CENGVACDB implements ICENGVACDB{

    private static String user = "e2380624"; // TODO: Your userName
    private static String password = "DGMe@%*t39-U"; //  TODO: Your password
    private static String host = "144.122.71.121"; // host name
    private static String database = "db2380624"; // TODO: Your database name
    private static int port = 8080; // port

    Connection connection = null;



    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        // TODO Auto-generated method stub
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection =  DriverManager.getConnection(url, user, password);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {
        
        String User = "CREATE TABLE User(" +
        "userID int NOT NULL," +
        "userName varchar(30)," +
        "age int," +
        "address varchar(150)," +
        "password varchar(30)," +
        "status varchar(15)," +
        "PRIMARY KEY(userID))";

        String Vaccine = "CREATE TABLE Vaccine(" +
        "code int NOT NULL," +
        "vaccinename varchar(30)," +
        "type varchar(30)," +
        "PRIMARY KEY(code))";
        
        String Vaccination = "CREATE TABLE Vaccination(" +
        "code int," +
        "userID int," +
        "dose int," + 
        "vacdate date," + 
        "FOREIGN KEY (code) REFERENCES Vaccine(code) ON DELETE CASCADE," + 
        "FOREIGN KEY (userID) REFERENCES User(userID)," +
        "PRIMARY KEY(code,userID,dose))";

        String AllergicSideEffect = "CREATE TABLE AllergicSideEffect(" +
        "effectcode int NOT NULL," +
        "effectname varchar(50)," + 
        "PRIMARY KEY(effectcode))";

        String Seen = "CREATE TABLE Seen(" +
        "effectcode int," +
        "code int," +
        "userID int," +
        "date date," +
        "degree varchar(30)," +
        "FOREIGN KEY (effectcode) REFERENCES AllergicSideEffect(effectcode) ON DELETE CASCADE," + 
        "FOREIGN KEY (code) REFERENCES Vaccination(code) ON DELETE CASCADE," +
        "FOREIGN KEY (userID) REFERENCES User(userID)," +
        "PRIMARY KEY (effectcode,code,userID))";

        int return_var = 0;
        try{
            
            Statement st = connection.createStatement();
            st.executeUpdate(User);
            return_var++;
            st.executeUpdate(Vaccine);
            return_var++;
            st.executeUpdate(Vaccination);
            return_var++;
            st.executeUpdate(AllergicSideEffect);
            return_var++;
            st.executeUpdate(Seen);
            return_var++;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        

        return return_var;
    }

    @Override
    public int dropTables() {
        // TODO Auto-generated method stub
        
        String User = "DROP TABLE User" + ";";
        String Vaccine = "DROP TABLE Vaccine;";
        String Vaccination = "DROP TABLE Vaccination;";
        String AllergicSideEffect = "DROP TABLE AllergicSideEffect;";
        String Seen = "DROP TABLE Seen" + ";";

        int return_var = 0;

        try{
            Statement st = connection.createStatement();
            st.executeUpdate(Seen);
            return_var++;
            st.executeUpdate(AllergicSideEffect);
            return_var++;
            st.executeUpdate(Vaccination);
            return_var++;
            st.executeUpdate(Vaccine);
            return_var++;
            st.executeUpdate(User);
            return_var++;
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return return_var;
    

    }

    @Override
    public int insertUser(User[] users) {
        // TODO Auto-generated method stub
        
        int return_var = 0;
        try{
            Statement st = connection.createStatement();
            int i;
            for(i=0;i<users.length;i++) {
                String User = "INSERT INTO User " +
                "VALUES (" + users[i].getUserID() + "," + "'" + users[i].getUserName() + "'" + "," + 
                users[i].getAge() + "," + "'" + users[i].getAddress() + "'" + "," + "'" + users[i].getPassword() + "'" + "," + "'" + users[i].getStatus() + "'" + ")";
                return_var++;
                st.executeUpdate(User);
        }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }


        return return_var;
    }

    @Override
    public int insertAllergicSideEffect(AllergicSideEffect[] sideEffects) {
        // TODO Auto-generated method stub
        int return_var = 0;
        try{
            Statement st = connection.createStatement();
            int i;
            for(i=0;i<sideEffects.length;i++) {
                String AllergicSideEffect = "INSERT INTO AllergicSideEffect " +
                "VALUES (" + sideEffects[i].getEffectCode() + "," + "'" + sideEffects[i].getEffectName() + "'" + ")";
                return_var++;
                st.executeUpdate(AllergicSideEffect);
        }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return return_var;
    }

    @Override
    public int insertVaccine(Vaccine[] vaccines) {
        // TODO Auto-generated method stub

        int return_var = 0;
        try{
            Statement st = connection.createStatement();
            int i;
            for(i=0;i<vaccines.length;i++) {
                String Vaccine = "INSERT INTO Vaccine " +
                "VALUES (" + vaccines[i].getCode() + "," + "'" + vaccines[i].getVaccineName() + "'" + "," + "'" + vaccines[i].getType() + "'" + ")";
                return_var++;
                st.executeUpdate(Vaccine);
        }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return return_var;
    }

    @Override
    public int insertVaccination(Vaccination[] vaccinations) {
        // TODO Auto-generated method stub
        int return_var = 0;
        try{
            Statement st = connection.createStatement();
            int i;
            for(i=0;i<vaccinations.length;i++) {
                String Vaccination = "INSERT INTO Vaccination " +
                "VALUES (" + vaccinations[i].getCode() + "," + vaccinations[i].getUserID() + "," + vaccinations[i].getDose() + "," + "'" + vaccinations[i].getVacdate() + "'" + ")";
                return_var++;
                st.executeUpdate(Vaccination);
        }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return return_var;
    }

    @Override
    public int insertSeen(Seen[] seens) {
        // TODO Auto-generated method stub
        int return_var = 0;
        try{
            Statement st = connection.createStatement();
            int i;
            for(i=0;i<seens.length;i++) {
                String Seen = "INSERT INTO Seen " +
                "VALUES (" + seens[i].getEffectcode() + "," + seens[i].getCode() + "," + seens[i].getUserID() + "," + "'" + seens[i].getDate() + "'" + "," + "'" + seens[i].getDegree() + "'" + ")";
                return_var++;
                st.executeUpdate(Seen);
        }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return return_var;
    }

    @Override
    public Vaccine[] getVaccinesNotAppliedAnyUser() {
        
        Vaccine arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT V.code,V.vaccinename,V.type " + 
            "FROM Vaccine V " +
            "WHERE V.code NOT IN (" +
            "SELECT V1.code " +
            "FROM Vaccine V1,Vaccination V2 " + 
            "WHERE V1.code = V2.code ) " + 
            "ORDER BY code ASC " +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new Vaccine [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                Vaccine v = new Vaccine(rss.getInt("code"), rss.getString("vaccinename"), rss.getString("type"));
                arr[seconde_index] = v;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        return arr;
    }

    @Override
    public UserIDuserNameAddressResult[] getVaccinatedUsersforTwoDosesByDate(String vacdate) {
        // TODO Auto-generated method stub
        UserIDuserNameAddressResult arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT U.userID,U.userName,U.address " + 
            "FROM User U " +
            "WHERE U.userID IN (" +
            "SELECT V.userID " +
            "FROM Vaccination V " + 
            "WHERE V.vacdate > " + "'" +  vacdate + "'" +
            "GROUP BY V.userID " +
            "HAVING Count(*) = 2 ) " +
            "ORDER BY userID ASC" +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new UserIDuserNameAddressResult [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                UserIDuserNameAddressResult u = new UserIDuserNameAddressResult(rss.getString("userID"), rss.getString("userName"), rss.getString("address"));
                arr[seconde_index] = u;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public Vaccine[] getTwoRecentVaccinesDoNotContainVac() {
        // TODO Auto-generated method stub

        Vaccine arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT V.code,V.vaccinename,V.type " + 
            "FROM Vaccine V, Vaccination V1 " +
            "WHERE V.code = V1.code AND V.vaccinename NOT LIKE '%vac%' AND V1.vacdate = ( " +
                "SELECT MAX(V2.vacdate) " +
                "FROM Vaccination V2 " +
                "WHERE V2.code = V1.code " + 
                "GROUP BY V2.code " +
            ") " + 	
            "GROUP BY V.code,V1.vacdate " + 
            "ORDER BY V1.vacdate DESC LIMIT 0,2;";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new Vaccine [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                Vaccine v = new Vaccine(rss.getInt("code"), rss.getString("vaccinename"), rss.getString("type"));
                arr[seconde_index] = v;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        return arr;
    }

    @Override
    public UserIDuserNameAddressResult[] getUsersAtHasLeastTwoDoseAtMostOneSideEffect() {
        // TODO Auto-generated method stub

        UserIDuserNameAddressResult arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT U.userID,U.userName,U.address " + 
            "FROM User U " +
            "WHERE U.userID IN (" +
            "SELECT V.userID " +
            "FROM Vaccination V, Vaccination V1 " + 
            "WHERE V.userID = V1.userID AND V.code = V1.code AND V.dose <> V1.dose ) " + 
            "AND U.userID NOT IN ( " +
            "SELECT U.userID " +
            "FROM Seen S, User U " +
            "WHERE S.userID = U.userID " +
            "GROUP BY U.userID " +
            "HAVING Count(*) > 1 ) " +
            "ORDER BY userID ASC " +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new UserIDuserNameAddressResult [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                UserIDuserNameAddressResult u = new UserIDuserNameAddressResult(rss.getString("userID"), rss.getString("userName"), rss.getString("address"));
                arr[seconde_index] = u;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }




        return arr;
    }

    @Override
    public UserIDuserNameAddressResult[] getVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect(String effectname) {
        // TODO Auto-generated method stub

        UserIDuserNameAddressResult arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT U.userID,U.userName,U.address " + 
            "FROM User U " +
            "WHERE NOT EXISTS ( " +
            "SELECT S.code " +
            "FROM Seen S, AllergicSideEffect A " + 
            "WHERE A.effectname = " + "'" + effectname + "'" + 
            " AND A.effectcode = S.effectcode AND " +
            "S.code NOT IN ( " +
            "SELECT S1.code " +
            "FROM Seen S1 " +
            "WHERE S1.userID = U.userID " +
            ")" + ")" +
            "ORDER BY userID ASC " +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new UserIDuserNameAddressResult [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                UserIDuserNameAddressResult u = new UserIDuserNameAddressResult(rss.getString("userID"), rss.getString("userName"), rss.getString("address"));
                arr[seconde_index] = u;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }




        return arr;
    }

    @Override
    public UserIDuserNameAddressResult[] getUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval(String startdate,
            String enddate) {
        // TODO Auto-generated method stub
        UserIDuserNameAddressResult arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT U.userID,U.userName,U.address " + 
            "FROM User U " +
            "WHERE U.userID IN ( " +
            "SELECT V.userID " +
            "FROM Vaccination V,Vaccination V1 " + 
            "WHERE V.userID = V1.userID AND V.code <> V1.code AND V.vacdate >= " + "'" + startdate + "'" + " AND V.vacdate <= " + "'" + enddate + "'" + " AND V1.vacdate >= '" + startdate + "'" +  "AND V1.vacdate <= '" + enddate + "'" + 
            ")" + 
            "ORDER BY userID ASC " +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new UserIDuserNameAddressResult [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                UserIDuserNameAddressResult u = new UserIDuserNameAddressResult(rss.getString("userID"), rss.getString("userName"), rss.getString("address"));
                arr[seconde_index] = u;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }




        return arr;
    }

    @Override
    public AllergicSideEffect[] getSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays() {
        // TODO Auto-generated method stub

        AllergicSideEffect arr[] = null;
        int index = 0;
        int seconde_index = 0;
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT A.effectcode, A.effectname " + 
            "FROM AllergicSideEffect A, Seen S " +
            "WHERE A.effectcode = S.effectcode AND S.userID IN ( " +
            "SELECT V.userID " +
            "FROM Vaccination V,Vaccination V1 " + 
            "WHERE V.userID = V1.userID AND (V1.dose - V.dose) = 1 AND DATEDIFF(V1.vacdate,V.vacdate) < 20" +   
            ")" + 
            "ORDER BY effectcode ASC " +
            ";";
            ResultSet rs = st.executeQuery(my_query);

            while(rs.next()){
                index++;
            }
            arr = new AllergicSideEffect [index];
            ResultSet rss = st.executeQuery(my_query);
            while (rss.next()) {
                AllergicSideEffect u = new AllergicSideEffect(rss.getInt("effectcode"), rss.getString("effectname"));
                arr[seconde_index] = u;
                seconde_index++;
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return arr;
    }

    @Override
    public double averageNumberofDosesofVaccinatedUserOverSixtyFiveYearsOld() {
        // TODO Auto-generated method stub
        
        double return_val = 0;
        
        
        try{

            Statement st = connection.createStatement();
            String my_query = "SELECT AVG(TEMP.number) AS Average " + 
            "FROM ( " +
            "SELECT COUNT(*) AS number " +
            "FROM Vaccination V, User U " +
            "WHERE U.age > 65 AND U.userID = V.userID " + 
            "GROUP BY V.userID " +
            ") AS TEMP " +
            ";";
            ResultSet rs = st.executeQuery(my_query);
            rs.next();
            return_val = rs.getDouble("Average");
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }



        
        return return_val;
    }

    @Override
    public int updateStatusToEligible(String givendate) {
        // TODO Auto-generated method stub


        
        
        int varr = 0;
        

        try{
            Statement st = connection.createStatement();

            String my_query = "UPDATE User " +
            "SET status = 'eligible' " +
            "WHERE status <> 'Eligible' AND userID IN ( " +
                "SELECT TEMP.userID " +
                "FROM ( " +
                "SELECT V.userID, MAX(V.vacdate) AS maxdate " +
                "FROM Vaccination V " +
                "GROUP BY V.userID) AS TEMP " +
                "WHERE 120 < DATEDIFF( '" + givendate + "'" + ", maxdate) " + 
                ")";




            st.executeUpdate(my_query);
            varr = st.getUpdateCount();
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return varr;
    }

    @Override
    public Vaccine deleteVaccine(String vaccineName) {
        // TODO Auto-generated method stub


        Vaccine vac = null;
        try {
            Statement st = connection.createStatement();
            String my_query = "SELECT * " +
                    "FROM Vaccine V " +
                    "WHERE V.vaccinename = '" + vaccineName + "'" + ";";
            ResultSet rs = st.executeQuery(my_query);
            rs.next();
            vac = new Vaccine(rs.getInt(1), rs.getString(2), rs.getString(3));

            String my_query1 = "DELETE FROM Vaccine " +
                    "WHERE vaccinename = '" + vaccineName + "'" + ";";

            st.executeUpdate(my_query1);
        } catch (Exception E) {
            E.printStackTrace();
        }
        return vac;
    }
    

}