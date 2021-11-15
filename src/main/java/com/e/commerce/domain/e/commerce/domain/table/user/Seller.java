package com.e.commerce.domain.e.commerce.domain.table.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User{


    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "company_contact")
    private Long companyContact;

    @Column(name = "company_name")
    private String companyName;

    private String newPassword;

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
