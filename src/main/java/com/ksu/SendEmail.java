package com.ksu;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;

public class SendEmail {

    private static String mail;
    private  static String first;
    private  static String last;
    private  static String messtext;



    public void Send(String Mail, String First, String Last, String MessText,String Dom) {

        first = First;
        last = Last;
        messtext = MessText;
        mail = Mail + Dom;
        final String username = "hovopro99@gmail.com";
        final String password = "d6w299dr7h";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hovopro99@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(SendEmail.mail)
            );
            message.setSubject("Welcome to the club buddy");
            message.setText("Samurai, "+first+" "+last+" "+messtext+"");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

class DBTest {
    //Для примера данные конфигурации вынесены здесь. В рабочих проектах данные конфигурации выносятся в отдельный файл.
    private final String url = "jdbc:mysql://localhost:3306/Meil";
    private final String serverName= "localhost";
    private final String portNumber = "3306";
    private final String databaseName= "Meil";
    private final String userName = "root";
    private final String password = "D6w299dr7h_";
    //Пример заполнения конфигурации подключения. Можно заполнять страндартными методами Java JDBC
    private String connectionUrl = url + serverName + ":" + portNumber + ";databaseName=" + databaseName;

    //Пример работы Select
    public void select() {
        Connection con = null;
        try {
            //Указываем название драйвера, если требуется
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Выполняем подключение
            con = DriverManager.getConnection(connectionUrl,userName,password);

            //Выполняем наш SQL запрос
            String sqlSelect = "SELECT * FROM [JavaTest].[dbo].[Test]";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);

            //Обрабатываем полученные данные от БД
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Пример работы Insert
    public void insert() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //Указываем название драйвера, если требуется
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Выполняем подключение
            conn = DriverManager.getConnection(connectionUrl,userName,password);
            stmt = conn.createStatement();

            String sql = "INSERT INTO [Meil].[dbo].[Test](name) VALUES ('first' , 'second')";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, "java");
            stat.executeUpdate();

            conn.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}