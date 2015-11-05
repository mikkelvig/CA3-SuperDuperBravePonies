/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author steffen
 */
@Entity
public class Currency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String code;
    private String description;
    private double rate;
    private String dag;

    public Currency() {
    }

    public Currency(String code, String description, double rate, String dag) {
        this.code = code;
        this.description = description;
        this.rate = rate;
        this.dag = dag;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return  code +  description + rate  + dag ;
    }
    


}
