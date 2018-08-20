package com.lms.data;

import com.lms.model.BookClassification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookClassificationDao {

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    public void addBookClassification(BookClassification bookClassification) {
        String sql = "INSERT INTO book_classification (subClassificationId, bookId) "
                + "VALUES (?, ?)";

        try {
            con = DbConnection.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bookClassification.getSubClassificationId());
            pstmt.setString(2, bookClassification.getBookId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(BookClassificationDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreparedStatement Query Error: " + ex.getMessage());
        } finally {
            DbConnection.closeConnections();
        }
    }

    public void updateBookClassification(BookClassification bookClassification) {
        String sql = "UPDATE book_classification SET "
                + "subClassificationId = ? "
                + "WHERE bookId = '" + bookClassification.getBookId() + "'";
        try {
            con = DbConnection.connect();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bookClassification.getSubClassificationId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            //Logger.getLogger(BookClassificationDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreparedStatement Query Error: " + ex.getMessage());
        } finally {
            DbConnection.closeConnections();
        }
    }

    public void deleteBookClassification(String bookId) {
        String sql = "DELETE FROM book_classification "
                + "WHERE bookId = '" + bookId + "'";
        try {
            con = DbConnection.connect();
            stmt = con.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            //Logger.getLogger(BookClassificationDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PreparedStatement Query Error: " + ex.getMessage());
        } finally {
            DbConnection.closeConnections();
        }
    }
    
    public ArrayList<BookClassification> checkSubClassificationIdExisit(String subClassificationId){
        String sql = "SELECT * FROM book_classification "
                + "WHERE subClassificationId = '" + subClassificationId + "'";
        ArrayList<BookClassification> bookClassificationList = new ArrayList<>();
        
        try{
            con = DbConnection.connect();
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                BookClassification bookClassification = new BookClassification();
                bookClassification.setBookId(rs.getString("bookId"));
                bookClassification.setSubClassificationId(rs.getString("subClassificationId"));
                bookClassificationList.add(bookClassification);
            }
            
        } catch (SQLException ex) {
            //Logger.getLogger(BookClassificationDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement Query Error: " + ex.getMessage());
        }
        
        return bookClassificationList;
    }
}
