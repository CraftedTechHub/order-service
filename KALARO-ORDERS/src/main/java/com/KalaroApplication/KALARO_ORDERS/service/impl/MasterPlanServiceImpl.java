package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import com.KalaroApplication.KALARO_ORDERS.repository.MasterPlanRepository;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class MasterPlanServiceImpl implements MasterPlanService {

    @Autowired
    private MasterPlanRepository masterPlanRepository;

    @Override
    public String saveOrderDetails(MasterPlanDto masterPlanDto) {

        MasterPlan masterPlan = new MasterPlan();

        masterPlan.setPlanId(masterPlanDto.getPlanId());
        masterPlan.setOrderId(masterPlanDto.getOrderId());
        masterPlan.setColor(masterPlanDto.getColor());
        masterPlan.setSize(masterPlanDto.getSize());
        masterPlan.setOrderQuantity(masterPlanDto.getOrderQuantity());

        List<MasterPlanSub> masterPlanSubList = new ArrayList<>();

        for (MasterPlanSubDto masterPlanSubDto : masterPlanDto.getSubPlans()) {
            MasterPlanSub subPlan = new MasterPlanSub();
            subPlan.setCenter(masterPlanSubDto.getCenter());
            subPlan.setDate(masterPlanSubDto.getDate());
            subPlan.setQty(masterPlanSubDto.getQty());
            subPlan.setMasterPlan(masterPlan);
            masterPlanSubList.add(subPlan);
        }
        masterPlan.setSubPlans(masterPlanSubList);
        masterPlanRepository.save(masterPlan);
        return "Data saved successfully";
    }

    @Override
    public List<MasterPlanDto> getOrderDetails(int orderId) {
        List<MasterPlan> masterPlanList = masterPlanRepository.findAllByOrderId(orderId);

        List<MasterPlanDto> masterPlanDtoList = new ArrayList<>();

        for(MasterPlan masterPlan:masterPlanList){
            MasterPlanDto masterPlanDto = new MasterPlanDto();
            masterPlanDto.setPlanId(masterPlan.getPlanId());
            masterPlanDto.setOrderId(masterPlan.getOrderId());
            masterPlanDto.setColor(masterPlan.getColor());
            masterPlanDto.setSize(masterPlan.getSize());
            masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());

            List<MasterPlanSubDto> masterPlanSubDtoList = new ArrayList<>();
            for(MasterPlanSub masterPlanSub:masterPlan.getSubPlans()){
                MasterPlanSubDto masterPlanSubDto = new MasterPlanSubDto();
                masterPlanSubDto.setId(masterPlanSub.getId());
                masterPlanSubDto.setCenter(masterPlanSub.getCenter());
                masterPlanSubDto.setDate(masterPlanSub.getDate());
                masterPlanSubDto.setQty(masterPlanSub.getQty());
                masterPlanSubDtoList.add(masterPlanSubDto);
            }
            masterPlanDto.setSubPlans(masterPlanSubDtoList);
            masterPlanDtoList.add(masterPlanDto);
        }
        return masterPlanDtoList;
    }

    @Override
    public String updateOrderDetails(MasterPlanDto masterPlanDto) {
        MasterPlan existingMasterPlan = masterPlanRepository.findById(masterPlanDto.getPlanId())
                .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + masterPlanDto.getPlanId()));

        // Update MasterPlan fields
        existingMasterPlan.setPlanId(masterPlanDto.getPlanId());
        existingMasterPlan.setOrderId(masterPlanDto.getOrderId());
        existingMasterPlan.setColor(masterPlanDto.getColor());
        existingMasterPlan.setSize(masterPlanDto.getSize());
        existingMasterPlan.setOrderQuantity(masterPlanDto.getOrderQuantity());

        // Create a map for existing sub-plans by ID for easy lookup
        Map<Integer, MasterPlanSub> existingSubPlans = existingMasterPlan.getSubPlans()
                .stream()
                .collect(Collectors.toMap(MasterPlanSub::getId, subPlan -> subPlan));

        // Update or add sub-plans
        for (MasterPlanSubDto subPlanDto : masterPlanDto.getSubPlans()) {
            MasterPlanSub subPlan;
            if (subPlanDto.getId() != 0 && existingSubPlans.containsKey(subPlanDto.getId())) {
                // Update existing sub-plan
                subPlan = existingSubPlans.get(subPlanDto.getId());
                subPlan.setCenter(subPlanDto.getCenter());
                subPlan.setDate(subPlanDto.getDate());
                subPlan.setQty(subPlanDto.getQty());
            } else {
                // Add new sub-plan
                subPlan = new MasterPlanSub();
                subPlan.setCenter(subPlanDto.getCenter());
                subPlan.setDate(subPlanDto.getDate());
                subPlan.setQty(subPlanDto.getQty());
                subPlan.setMasterPlan(existingMasterPlan);
                existingMasterPlan.getSubPlans().add(subPlan);
            }
        }

        // Remove sub-plans not present in the new list (optional, based on your requirement)
        Set<Integer> newSubPlanIds = masterPlanDto.getSubPlans()
                .stream()
                .map(MasterPlanSubDto::getId)
                .collect(Collectors.toSet());
        existingMasterPlan.getSubPlans().removeIf(subPlan -> !newSubPlanIds.contains(subPlan.getId()));

        // Save updated MasterPlan
        masterPlanRepository.save(existingMasterPlan);

        return "Order details updated successfully, including new sub-plans.";

    }

    @Override
    public String deleteOrderDetails(int planId) {
        MasterPlan masterPlan = masterPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + planId));
        masterPlanRepository.delete(masterPlan);
        return "Order details deleted successfully";
    }

}
