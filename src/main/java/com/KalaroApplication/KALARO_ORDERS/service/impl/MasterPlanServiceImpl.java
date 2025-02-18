package com.KalaroApplication.KALARO_ORDERS.service.impl;

import com.KalaroApplication.KALARO_ORDERS.dto.MasterPlanDto;
import com.KalaroApplication.KALARO_ORDERS.dto.component.MasterPlanSubDto;
import com.KalaroApplication.KALARO_ORDERS.entity.MasterPlan;
import com.KalaroApplication.KALARO_ORDERS.entity.component.MasterPlanSub;
import com.KalaroApplication.KALARO_ORDERS.repository.MasterPlanRepository;
import com.KalaroApplication.KALARO_ORDERS.repository.MasterPlanSubRepository;
import com.KalaroApplication.KALARO_ORDERS.service.MasterPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MasterPlanServiceImpl implements MasterPlanService {

    @Autowired
    private MasterPlanRepository masterPlanRepository;

    @Autowired
    private MasterPlanSubRepository masterPlanSubRepository;

    @Override
    public int saveOrderDetails(MasterPlanDto masterPlanDto) {
        try{
            MasterPlan masterPlan = new MasterPlan();

            masterPlan.setPlanId(masterPlanDto.getPlanId());
            masterPlan.setOrderId(masterPlanDto.getOrderId());
            masterPlan.setColor(masterPlanDto.getColor());
            masterPlan.setSize(masterPlanDto.getSize());
            masterPlan.setOrderQuantity(masterPlanDto.getOrderQuantity());
            masterPlan.setOrderCategory(masterPlanDto.getOrderCategory());

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
            log.info("Order details saved successfully");
            return 1;
        } catch (Exception e) {
            log.error("Error occurred while saving order details of Master Plan: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public List<MasterPlanDto> getOrderDetails(int orderId) {
        try{
            List<MasterPlan> masterPlanList = masterPlanRepository.findAllByOrderId(orderId);
            List<MasterPlanDto> masterPlanDtoList = new ArrayList<>();

            for(MasterPlan masterPlan:masterPlanList){
                MasterPlanDto masterPlanDto = new MasterPlanDto();
                masterPlanDto.setPlanId(masterPlan.getPlanId());
                masterPlanDto.setOrderId(masterPlan.getOrderId());
                masterPlanDto.setColor(masterPlan.getColor());
                masterPlanDto.setSize(masterPlan.getSize());
                masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());
                masterPlanDto.setOrderCategory(masterPlan.getOrderCategory());

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
            log.info("Order details of Master Plan fetched successfully");
            return masterPlanDtoList;
        } catch (Exception e) {
            log.info("Error occurred while fetching order details from Master Plan: {}", e.getMessage());
            throw new RuntimeException("Error occurred while fetching order details from Master Plan");
        }
    }

    @Override
    public int updateOrderDetails(MasterPlanDto masterPlanDto) {
        try{
            MasterPlan existingMasterPlan = masterPlanRepository.findById(masterPlanDto.getPlanId())
                    .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + masterPlanDto.getPlanId()));


            // Update MasterPlan fields
            existingMasterPlan.setPlanId(masterPlanDto.getPlanId());
            existingMasterPlan.setOrderId(masterPlanDto.getOrderId());
            existingMasterPlan.setColor(masterPlanDto.getColor());
            existingMasterPlan.setSize(masterPlanDto.getSize());
            existingMasterPlan.setOrderQuantity(masterPlanDto.getOrderQuantity());
            existingMasterPlan.setOrderCategory(masterPlanDto.getOrderCategory());

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
            log.info("Order details updated successfully, including new sub-plans.");
            return 1;
        } catch (Exception e) {
            log.error("Error occurred while updating order details of Master Plan: {}", e.getMessage());
            return 0;
        }

    }

    @Override
    public int deleteOrderDetails(int planId) {
        try{
            MasterPlan masterPlan = masterPlanRepository.findById(planId)
                    .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + planId));
            masterPlanRepository.delete(masterPlan);
            return 1;
        } catch (Exception e) {
            log.error("Error occurred while deleting order details of Master Plan: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public List<MasterPlanDto> getMasterPlanSubByCenter(String centerName) {
        List<MasterPlanSub> masterPlanSubList =  masterPlanSubRepository.findAllByCenter(centerName);
        List<MasterPlanDto> masterPlanDtoList = new ArrayList<>();
        for(MasterPlanSub masterPlanSub:masterPlanSubList){
            if(masterPlanSub.getCenter()==null){
                throw new RuntimeException("No data found for center: " + centerName);
            }else{
                MasterPlan masterPlan = masterPlanRepository.findById(masterPlanSub.getMasterPlan().getPlanId())
                        .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + masterPlanSub.getMasterPlan().getPlanId()));

                MasterPlanDto masterPlanDto = new MasterPlanDto();
                masterPlanDto.setPlanId(masterPlan.getPlanId());
                masterPlanDto.setOrderId(masterPlan.getOrderId());
                masterPlanDto.setColor(masterPlan.getColor());
                masterPlanDto.setSize(masterPlan.getSize());
                masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());
                masterPlanDto.setOrderCategory(masterPlan.getOrderCategory());

                MasterPlanSubDto masterPlanSubDto = new MasterPlanSubDto();
                masterPlanSubDto.setId(masterPlanSub.getId());
                masterPlanSubDto.setCenter(masterPlanSub.getCenter());
                masterPlanSubDto.setDate(masterPlanSub.getDate());
                masterPlanSubDto.setQty(masterPlanSub.getQty());

                masterPlanDto.setSubPlans(List.of(masterPlanSubDto));

                masterPlanDtoList.add(masterPlanDto);
            }
        }
        log.info("Master Plan Sub fetched successfully");
        return masterPlanDtoList;
    }

    @Override
    public MasterPlanDto getMasterPlanByPlanId(int planId) {
        MasterPlan masterPlan = masterPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + planId));
        MasterPlanDto masterPlanDto = new MasterPlanDto();
        masterPlanDto.setPlanId(masterPlan.getPlanId());
        masterPlanDto.setOrderId(masterPlan.getOrderId());
        masterPlanDto.setColor(masterPlan.getColor());
        masterPlanDto.setSize(masterPlan.getSize());
        masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());
        masterPlanDto.setOrderCategory(masterPlan.getOrderCategory());

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
        return masterPlanDto;

    }

    @Override
    public MasterPlanDto getMasterPlan(int planId) {
        MasterPlan masterPlan = masterPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("MasterPlan not found for ID: " + planId));

        MasterPlanDto masterPlanDto = new MasterPlanDto();
        masterPlanDto.setPlanId(masterPlan.getPlanId());
        masterPlanDto.setOrderId(masterPlan.getOrderId());
        masterPlanDto.setColor(masterPlan.getColor());
        masterPlanDto.setSize(masterPlan.getSize());
        masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());
        masterPlanDto.setOrderCategory(masterPlan.getOrderCategory());

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

        log.info("Master Plan fetched successfully");
        return masterPlanDto;
    }

    @Override
    public MasterPlanDto getMasterPlanDetailsForCenter(int planId, String centerName) {
        MasterPlan masterPlan = masterPlanRepository.findById(planId)
                .stream()
                .filter(mp -> mp.getSubPlans().stream().anyMatch(subPlan -> subPlan.getCenter().equals(centerName)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("MasterPlan not found for Order ID: " + planId + " and Center: " + centerName));
        MasterPlanDto masterPlanDto = new MasterPlanDto();
        masterPlanDto.setPlanId(masterPlan.getPlanId());
        masterPlanDto.setOrderId(masterPlan.getOrderId());
        masterPlanDto.setColor(masterPlan.getColor());
        masterPlanDto.setSize(masterPlan.getSize());
        masterPlanDto.setOrderQuantity(masterPlan.getOrderQuantity());
        masterPlanDto.setOrderCategory(masterPlan.getOrderCategory());

        List<MasterPlanSubDto> masterPlanSubDtoList = new ArrayList<>();
        for(MasterPlanSub masterPlanSub:masterPlan.getSubPlans()){
            if(masterPlanSub.getCenter().equals(centerName)){
                MasterPlanSubDto masterPlanSubDto = new MasterPlanSubDto();
                masterPlanSubDto.setId(masterPlanSub.getId());
                masterPlanSubDto.setCenter(masterPlanSub.getCenter());
                masterPlanSubDto.setDate(masterPlanSub.getDate());
                masterPlanSubDto.setQty(masterPlanSub.getQty());
                masterPlanSubDtoList.add(masterPlanSubDto);
            }
        }
        masterPlanDto.setSubPlans(masterPlanSubDtoList);
        return masterPlanDto;
    }
}
