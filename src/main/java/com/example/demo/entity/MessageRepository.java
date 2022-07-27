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
                "(id INTEGER PRIMARY KEY, sender_id INTEGER, sender_type TEXT ,receiver_id INTEGER, message TEXT, read BOOLEAN)");
        statement.close();
    }

    public static int getUnreadMessagesCount(User sender, User receiver) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        int unread = 0;
        if(sender.getClass() == Teacher.class){
            ResultSet result = statement.executeQuery("SELECT count(id) as unread FROM message WHERE sender_id = "
                    + sender.getId() + " AND receiver_id = "+ receiver.getId()+" AND sender_type is 'teacher' AND read is false");
            unread = result.getInt("unread");
        }else {
            //teacher
        }

        statement.close();
        return unread;
    }

    public static ArrayList<User> getConnectedUsers(User user) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = DbConnection.getConnection().createStatement();
        if(user.getClass() == Student.class){
            ResultSet result = null;

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
        }
        return users;
    }
}
