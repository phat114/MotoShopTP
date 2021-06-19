/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.motorshop.repository;

import com.example.motorshop.entity.BillDetail;
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
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
    
    @Query(value =  " select bd.* " +
                    " from bill_detail bd, motor m " +
                    " where bd.motor_id = m.motor_id " +
                    " and m.name like %:motorName% ",
                    nativeQuery = true)
    public List<Object[]> findByMotorName(@Param("motorName") String motorName);
    
    @Query(value =  " select bd.* " +
                    " from bill_detail bd, accessory a " +
                    " where bd.accessory_id = a.accessory_id " +
                    " and a.name like %:accessoryName% ",
                    nativeQuery = true)
    public List<Object[]> findByAccessoryName(@Param("accessoryName") String accessoryName);
    
    @Query(value =  " select bd.id, bd.bill_id, m.name as product, bd.amount, bd.price " +
                    " from bill_detail bd, motor m " +
                    " where bd.motor_id = m.motor_id and bd.motor_id is not null " +
                    " union " +
                    " select bd.id, bd.bill_id, a.name as product , bd.amount, bd.price " +
                    " from bill_detail bd, accessory a " +
                    " where bd.accessory_id = a.accessory_id and bd.accessory_id is not null " +                    
                    " order by bd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearAllCombined();
    
    @Query(value =  " select bd.id, bd.bill_id, m.name as motor, a.name as accessory, bd.amount, bd.price " +
                    " from bill_detail bd " +
                    " left join motor m " +
                    " on m.motor_id = bd.motor_id " +
                    " left join accessory a " +
                    " on bd.accessory_id = a.accessory_id" +
                    " order by bd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearAllUnCombined();
                    
    @Query(value =  " select bd.id, bd.bill_id, m.name as product, bd.amount, bd.price " +
                    " from bill_detail bd, motor m " +
                    " where bd.motor_id = m.motor_id and bd.motor_id is not null " +
                    " and bd.bill_id = :billId " +
                    " union " +
                    " select bd.id, bd.bill_id, a.name as product , bd.amount, bd.price " +
                    " from bill_detail bd, accessory a " +
                    " where bd.accessory_id = a.accessory_id and bd.accessory_id is not null " +
                    " and bd.bill_id = :billId " +
                    " order by bd.id ",
                    nativeQuery = true)
    public List<Object[]> findClearByBillId(@Param("billId") Integer billId);
    
    @Query(value =  " select * " +
                    " from ( " +
                    " select bd.id, bd.bill_id, m.name as product, bd.amount, bd.price " +
                    " from bill_detail bd, motor m " +
                    " where bd.motor_id = m.motor_id and bd.motor_id is not null " +
                    " union " +
                    " select bd.id, bd.bill_id, a.name as product , bd.amount, bd.price " +
                    " from bill_detail bd, accessory a " +
                    " where bd.accessory_id = a.accessory_id and bd.accessory_id is not null ) " +
                    " where product like %:productName% ",
                    nativeQuery = true)
    public List<Object[]> findClearByProductName(@Param("productName") String productName);
}
