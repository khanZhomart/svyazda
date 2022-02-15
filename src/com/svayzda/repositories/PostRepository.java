package com.svayzda.repositories;

import com.svayzda.data.IDB;
import com.svayzda.entities.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class PostRepository {

    private final IDB db;

    public PostRepository(IDB db) {
        this.db = db;
    }

//    @Override
//    public boolean createBooking(Post post) {
//        Connection con = null;
//        try {
//            con = db.getConnection();
//            PreparedStatement st = con.prepareStatement("INSERT INTO bookings(guestId, roomId, dateIn, dateOut, extraDescription) VALUES (?,?,?,?,?)");
//
//
//            st.setInt(1, post.getGuestId());
//            st.setInt(2, post.getRoomId());
//            st.setDate(3, post.getDateIn());
//            st.setDate(4, post.getDateOut());
//            st.setString(5, post.getExtraDescription());
//            st.execute();
//            return true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Post getBooking(int id) {
//        Connection con = null;
//        try {
//            con = db.getConnection();
//            PreparedStatement st = con.prepareStatement("SELECT bookingId, guestId, roomId, dateIn, dateOut, extraDescription FROM bookings WHERE bookingId=?");
//
//            st.setInt(1, id);
//
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                Post post = new Post(
//                        rs.getInt("bookingId"),
//                        rs.getInt("guestId"),
//                        rs.getInt("roomId"),
//                        rs.getDate("dateIn"),
//                        rs.getDate("dateOut"),
//                        rs.getString("extraDescription")
//                );
//
//                return post;
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<Post> getAllBookings() {
//        Connection con = null;
//        try {
//            con = db.getConnection();
//            Statement st = con.createStatement();
//
//            ResultSet rs = st.executeQuery("SELECT bookingId, guestId, roomId, dateIn, dateOut, extraDescription FROM bookings");
//            List<Post> posts = new LinkedList<>();
//            while (rs.next()) {
//                Post post = new Post(
//                        rs.getInt("bookingId"),
//                        rs.getInt("guestId"),
//                        rs.getInt("roomId"),
//                        rs.getDate("dateIn"),
//                        rs.getDate("dateOut"),
//                        rs.getString("extraDescription")
//                );
//
//                posts.add(post);
//            }
//
//            return posts;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean cancelBooking(int id) {
//        Connection con = null;
//        try {
//            con = db.getConnection();
//            PreparedStatement pst = con.prepareStatement("DELETE FROM bookings WHERE bookingId=?");
//
//            pst.setInt(1, id);
//            pst.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            System.out.println("Invalid ID" + e);
//        }
//        return false;
//    }

    public boolean createPost(Post post) {
        Connection con = null;
        try {
            con = db.getConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO posts(post_id, created_at, disabled_comments, text, title, visibility, author) VALUES (?,?,?,?,?,?,?)");

            // generating new unique postId
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT max(post_id) FROM posts");
            resultSet.next();
            int postId = resultSet.getInt("max") + 1;

            // getting authorId from username
            PreparedStatement pst2 = con.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            pst2.setString(1, post.getAuthorUsername());
            ResultSet resultSet2 = pst2.executeQuery();
            resultSet2.next();
            int authorId = resultSet2.getInt("user_id");

            pst.setInt(1, postId);
            pst.setDate(2, new Date(new java.util.Date().getTime()));
            pst.setBoolean(3, post.isDisabledComments());
            pst.setString(4, post.getText());
            pst.setString(5, post.getTitle());
            pst.setInt(6, post.getVisibility());
            pst.setInt(7, authorId);
            pst.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public Collection<Post> getAllPosts() {
        Connection con = null;
        try {
            con = db.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM posts");
            Collection<Post> posts = new ArrayList<>();
            while (rs.next()) {

                // getting username from authorId
                PreparedStatement pst2 = con.prepareStatement("SELECT username FROM users WHERE user_id = ?");
                pst2.setInt(1, rs.getInt("author"));
                ResultSet resultSet2 = pst2.executeQuery();
                resultSet2.next();
                String authorUsername = resultSet2.getString("username");

                Post post = new Post(
                        rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getInt("visibility"),
                        rs.getBoolean("disabled_comments"),
                        authorUsername
                );

                posts.add(post);
            }
            return posts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
