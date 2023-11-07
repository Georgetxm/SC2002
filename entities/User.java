package entities;

import java.util.EnumSet;

import types.Faculty;
import types.Perms;

public class User {
    private String userId;
    private String password;
    private Faculty faculty;
    private EnumSet<Perms> permission;

    public User() {
        this.userId = "";
        this.password = "";
        this.faculty = Faculty.WHOLE_NTU;
        this.permission = EnumSet.noneOf(Perms.class);
    }

    public User(String userId, String password, Faculty faculty, EnumSet<Perms> permission) {
        this.userId = userId;
        this.password = password;
        this.faculty = faculty;
        this.permission = permission;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return this.password;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public EnumSet<Perms> getPerms() {
        return this.permission;
    }

    public boolean addPerms(EnumSet<Perms> newPerms) {
        try {
            this.permission.addAll(newPerms);
        } catch (Exception e) {
            System.out.println("Error adding permissions: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean replacePerms(EnumSet<Perms> newPerms) {
        try {
            this.permission = newPerms;
        } catch (Exception e) {
            System.out.println("Error replacing permission attribute: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean removePerms(EnumSet<Perms> newPerms) {
        try {
            this.permission.removeAll(newPerms);
        } catch (Exception e) {
            System.out.println("Error removing permissions: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
