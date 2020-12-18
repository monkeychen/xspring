package org.xspring.tutorial.sb.hello.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Title: DemoUser</p>
 * <p>Description:</p>
 * <p>Copyright: FJ.CMCC Co., Ltd. (c) 2020</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2020/3/20 6:06 下午</p>
 */
@Entity
@Table(name = "dwh_asset_app_summary")
public class DemoAppAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "department")
    private String department;

    @Column(name = "admin")
    private String admin;

    @Column(name = "creator")
    private String creator;

    @Column(name = "phone")
    private String phone;

    @Column(name = "vendor_admin")
    private String vendorAdmin;

    @Column(name = "vendor_phone")
    private String vendorPhone;

    @Column(name = "vendor_mail")
    private String vendorMail;

    @Column(name = "app_intro")
    private String appIntro;

    @Column(name = "descr")
    private String descr;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVendorAdmin() {
        return vendorAdmin;
    }

    public void setVendorAdmin(String vendorAdmin) {
        this.vendorAdmin = vendorAdmin;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getVendorMail() {
        return vendorMail;
    }

    public void setVendorMail(String vendorMail) {
        this.vendorMail = vendorMail;
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
