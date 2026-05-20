package com.lugus.library.model;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String banner;
    private boolean blocked;
    private String blockReason;
    private String blockUntil;
    private String createdAt;

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getBanner() { return banner; }
    public void setBanner(String banner) { this.banner = banner; }
    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
    public String getBlockReason() { return blockReason; }
    public void setBlockReason(String blockReason) { this.blockReason = blockReason; }
    public String getBlockUntil() { return blockUntil; }
    public void setBlockUntil(String blockUntil) { this.blockUntil = blockUntil; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
