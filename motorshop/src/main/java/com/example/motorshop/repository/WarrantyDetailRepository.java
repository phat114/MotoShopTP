/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.motorshop.repository;

import com.example.motorshop.entity.WarrantyDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hungh
 */
@Repository
public interface WarrantyDetailRepository extends JpaRepository<WarrantyDetail, Integer> {
    
    @Query(value =  " select wd.* " +
                    " from warranty_detail wd, motor m " +
                    " where wd.motor_id = m.motor_id " +
                    " and m.name like %:motorName% ",
                    nativeQuery = true)
    public List<Object[]> findByMotorName(@Param("motorName") String motorName);
    
    @Query(value =  " select wd.* " +
                    " from warranty_detail wd, accessory a " +
                    " where wd.accessory_id = a.accessory_id " +
                    " and a.name like %:accessoryName% ",
                    nativeQuery = true)
    public List<Object[]> findByAccessoryName(@Param("accessoryName") String accessoryName);
    
    @Query(value =  " select wd.id, wd.warranty_id, m.name as product, wd.content, wd.price " +
                    " from warranty_detail wd, motor m " +
                    " where wd.motor_id = m.motor_id and wd.motor_id is not null " +
                    " union " +
                    " select wd.id, wd.warranty_id, a.name as product , wd.content, wd.price " +
                    " from warranty_detail wd, accessory a " +
                    " where wd.accessory_id = a.accessory_id and wd.accessory_id is not null " +
                    " order by wd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearAllCombined();
    
    @Query(value =  " select wd.id, wd.warranty_id, m.name as motor, a.name as accessory, wd.content, wd.price " +
                    " from warranty_detail wd " +
                    " left join motor m " +
                    " on m.motor_id = wd.motor_id " +
                    " left join accessory a " +
                    " on wd.accessory_id = a.accessory_id " +
                    " order by wd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearAllUnCombined();
    
    @Query(value =  " select wd.id, wd.warranty_id, m.name as product, wd.content, wd.price " +
                    " from warranty_detail wd, motor m " +
                    " where wd.motor_id = m.motor_id and wd.motor_id is not null " +
                    " and wd.warranty_id = :warrantyId " +
                    " union " +
                    " select wd.id, wd.warranty_id, a.name as product , wd.content, wd.price " +
                    " from warranty_detail wd, accessory a " +
                    " where wd.accessory_id = a.accessory_id and wd.accessory_id is not null " +
                    " and wd.warranty_id = :warrantyId " +
                    " order by wd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearByWarrantyId(@Param("warrantyId") Integer warrantyId);
    
    @Query(value =  " select * " +
                    " from ( " +
                    " select wd.id, wd.warranty_id, m.name as product, wd.content, wd.price " +
                    " from warranty_detail wd, motor m " +
                    " where wd.motor_id = m.motor_id and wd.motor_id is not null " +
                    " union " +
                    " select wd.id, wd.warranty_id, a.name as product , wd.content, wd.price " +
                    " from warranty_detail wd, accessory a " +
                    " where wd.accessory_id = a.accessory_id and wd.accessory_id is not null ) " +
                    " where product like %:productName% ",
                    nativeQuery = true)
    public List<Object[]> findClearByProductName(@Param("productName") String productName);
}
