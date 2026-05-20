package com.lugus.library.model;

import com.lugus.library.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User authenticate(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND blocked = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashPassword(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String email, String password, String username) {
        String sql = "INSERT INTO users (email, password, username, role) VALUES (?, ?, ?, 'user')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashPassword(password));
            ps.setString(3, username != null && !username.isEmpty() ? username : email.split("@")[0]);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(int userId, String username, String avatar, String banner) {
        String sql = "UPDATE users SET username = COALESCE(?, username), avatar = COALESCE(?, avatar), banner = COALESCE(?, banner) WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, avatar);
            ps.setString(3, banner);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean blockUser(int userId, String reason, String until) {
        String sql = "UPDATE users SET blocked = 1, block_reason = ?, block_until = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reason);
            ps.setString(2, until);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unblockUser(int userId) {
        String sql = "UPDATE users SET blocked = 0, block_reason = NULL, block_until = NULL WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ? AND role != 'admin'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Document> getDocumentsByUser(int userId) {
        List<Document> docs = new ArrayList<>();
        String sql = "SELECT d.*, u.username AS uploaderName, u.email AS uploaderEmail FROM documents d JOIN users u ON d.uploaded_by = u.id WHERE d.uploaded_by = ? ORDER BY d.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                docs.add(mapDocumentWithUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docs;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role"));
        user.setAvatar(rs.getString("avatar"));
        user.setBanner(rs.getString("banner"));
        user.setBlocked(rs.getInt("blocked") == 1);
        user.setBlockReason(rs.getString("block_reason"));
        user.setBlockUntil(rs.getString("block_until"));
        user.setCreatedAt(rs.getString("created_at"));
        return user;
    }

    private Document mapDocumentWithUser(ResultSet rs) throws SQLException {
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
