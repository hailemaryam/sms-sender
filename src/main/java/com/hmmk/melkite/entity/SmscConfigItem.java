package com.hmmk.melkite.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "SmscConfigItem")
@Cacheable
public class SmscConfigItem extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;
    public String serviceId;
    public String productId;
    public String username;
    public String password;
    public String ip;
    public String port;
    public String shortCode; // will be used as smsc-id
    public String okReplyTemplate;
    public String language;
    @CreationTimestamp
    public Date dateCreated;
    @UpdateTimestamp
    public Date dateUpdated;

}
