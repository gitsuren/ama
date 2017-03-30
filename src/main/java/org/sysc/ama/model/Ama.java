package org.sysc.ama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Ama {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User subject;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> allowedUsers;

    private boolean isPublic;
    private String title;
    private Date created;
    private Date updated;

    public Ama () {}

    public Ama (String title, User subject, boolean isPublic) {
        this.title = title;
        this.subject = subject;
        this.isPublic = isPublic;
        this.created = new Date();
        this.updated = new Date();
    }

    public Ama (String title, User subject, boolean isPublic, Set<User> allowedUsers) {
        this(title, subject, isPublic);
        this.allowedUsers = allowedUsers;
    }

    public Long getId(){
        return this.id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated () {
        return this.created;
    }

    public void setUpdated (Date updated) {
        this.updated = updated;
    }

    public Date getUpdated () {
        return this.updated;
    }

    public Set<User> getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(Set<User> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public boolean userIsAllowedToView(User user){
        if (user.getId() == this.subject.getId())
            return true;

        for (User u : this.allowedUsers){
            if (u.getId() == user.getId()){
                return true;
            }
        }
        return false;
    }
}
