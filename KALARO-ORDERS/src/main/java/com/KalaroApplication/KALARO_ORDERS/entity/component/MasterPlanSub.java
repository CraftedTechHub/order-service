package com.KalaroApplication.KALARO_ORDERS.entity.component;

import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="`Master_Plan_Sub`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MasterPlanSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String center;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "master_plan_id", nullable = false)
    private MasterPlan masterPlan;
}
