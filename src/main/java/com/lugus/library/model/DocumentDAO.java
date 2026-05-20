package com.lugus.library.model;

import com.lugus.library.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public List<Document> getAllDocuments() {
        List<Document> docs = new ArrayList<>();
        String sql = "SELECT d.*, u.username AS uploaderName, u.email AS uploaderEmail FROM documents d JOIN users u ON d.uploaded_by = u.id ORDER BY d.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                docs.add(mapDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docs;
    }

    public Document getDocumentById(int id) {
        String sql = "SELECT d.*, u.username AS uploaderName, u.email AS uploaderEmail FROM documents d JOIN users u ON d.uploaded_by = u.id WHERE d.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapDocument(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addDocument(Document doc) {
        String sql = "INSERT INTO documents (title, author, info, filename, uploaded_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doc.getTitle());
            ps.setString(2, doc.getAuthor());
            ps.setString(3, doc.getInfo());
            ps.setString(4, doc.getFilename());
            ps.setInt(5, doc.getUploadedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDocument(int id) {
        String sql = "DELETE FROM documents WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Document> searchDocuments(String query) {
        List<Document> docs = new ArrayList<>();
        String sql = "SELECT d.*, u.username AS uploaderName, u.email AS uploaderEmail FROM documents d JOIN users u ON d.uploaded_by = u.id WHERE d.title LIKE ? OR d.author LIKE ? ORDER BY d.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                docs.add(mapDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docs;
    }

    private Document mapDocument(ResultSet rs) throws SQLException {
        Document doc = new Document();
        doc.setId(rs.getInt("id"));
        doc.setTitle(rs.getString("title"));
        doc.setAuthor(rs.getString("author"));
        doc.setInfo(rs.getString("info"));
        doc.setFilename(rs.getString("filename"));
        doc.setUploadedBy(rs.getInt("uploaded_by"));
        doc.setCreatedAt(rs.getString("created_at"));
        doc.setUploaderName(rs.getString("uploaderName"));
        doc.setUploaderEmail(rs.getString("uploaderEmail"));
        return doc;
    }
}
