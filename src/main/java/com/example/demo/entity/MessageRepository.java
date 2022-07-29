package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageRepository {

    public static void createMessageTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS message" +
                "(id INTEGER PRIMARY KEY ASC, sender_id INTEGER, sender_type TEXT ,receiver_id INTEGER, message TEXT, read BOOLEAN)");
        statement.close();
    }

    public static ArrayList<Message> getMessages(User friend, User loggedInUser) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM message WHERE (sender_id = "
                + friend.getId() + " AND receiver_id = "+ loggedInUser.getId()+") OR ( sender_id = "+
                loggedInUser.getId()
                + " AND receiver_id = " + friend.getId() + " )" );

        ArrayList<Message> messages = new ArrayList<>();
        while (result.next()){
            String senderType = result.getString("sender_type");
            Message message = new Message();
            if(senderType.compareTo(friend.getClass().getSimpleName().toLowerCase()) == 0){
                message.setSender(friend);
                message.setReceiver(loggedInUser);
            }else {
                message.setReceiver(friend);
                message.setSender(loggedInUser);
            }
            message.setMessage(result.getString("message"));
            message.setId(result.getInt("id"));
            messages.add(message);
        }
        statement.close();
        makeMessagesAsRead(friend,loggedInUser);
        return messages;
    }

    private static void makeMessagesAsRead(User friend, User loggedInUser) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        int result = statement.executeUpdate("UPDATE message set read = true WHERE sender_id = " + friend.getId()+
                " AND receiver_id = " + loggedInUser.getId() + " AND sender_type = '" +
                friend.getClass().getSimpleName().toLowerCase() + "'");
        System.out.println("REsult: " + result);
    }

    public static int getUnreadMessagesCount(User sender, User receiver) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        int unread = 0;
        String senderType = sender.getClass().getSimpleName().toLowerCase();
        ResultSet result = statement.executeQuery("SELECT count(id) as unread FROM message WHERE sender_id = "
                + sender.getId() + " AND receiver_id = "+ receiver.getId()+" AND sender_type is '" + senderType + "' AND read = false");
        unread = result.getInt("unread");
        statement.close();
        System.out.println(unread);
        return unread;
    }

    public static ArrayList<User> getConnectedUsers(User user) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet result = null;
        if(user.getClass() == Student.class){
            result = statement.executeQuery("SELECT DISTINCT teacher.id, teacher.name, teacher.lastname" +
                    " FROM teacher LEFT JOIN course ON course.created_by = teacher.id " +
                    "LEFT JOIN course_student ON course.id = course_student.course_id " +
                    "WHERE course_student.student_id = " + user.getId());
            while (result.next()){
                Teacher teacher = new Teacher();
                teacher.setName(result.getString("name"));
                teacher.setLastname(result.getString("lastname"));
                teacher.setId(result.getInt("id"));
                users.add(teacher);
            }
        }else {
            result = statement.executeQuery("SELECT DISTINCT student.id, student.name, student.lastname " +
                    "FROM student INNER JOIN course_student on student.id = student_id " +
                    "INNER JOIN course on course.id = course_student.course_id WHERE created_by = " + user.getId());
            while (result.next()){
                Student student = new Student();
                student.setName(result.getString("name"));
                student.setLastname(result.getString("lastname"));
                student.setId(result.getInt("id"));
                users.add(student);
            }
        }
        return users;
    }

    public static void sendMessage(User sender, User receiver, String message) throws SQLException {
        String senderType = sender.getClass().getSimpleName().toLowerCase();
        Statement statement = DbConnection.getConnection().createStatement();
        statement.executeUpdate("INSERT INTO message(sender_id, receiver_id, message, read, sender_type) VALUES " +
                "("+sender.getId()+" , " +receiver.getId() +" , '" + message +"' , " + 0 +
                " , '" + senderType + "')" );
        System.out.println("INSERT INTO message(sender_id, receiver_id, message, read, sender_type) VALUES " +
                "("+sender.getId()+" , " +receiver.getId() +" , '" + message +"' , " + false +
                " , '" + senderType + "')");
    }
}
