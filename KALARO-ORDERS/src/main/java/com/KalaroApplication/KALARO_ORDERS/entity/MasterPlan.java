package com.KalaroApplication.KALARO_ORDERS.entity;

import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "`Master_Plan_Main`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MasterPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;

    @Column(nullable = false)
    private int orderId;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private int orderQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_master_planId",referencedColumnName = "planId")
    private List<MasterPlanSub> subPlans;
}
