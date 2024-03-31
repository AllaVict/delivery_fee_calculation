package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;

import java.util.List;

public interface ExtraFeeService {

    ExtraFeeDTO createExtraFee(ExtraFeeDTO extraFeeDTO);

    ExtraFeeDTO archiveExtraFee(Long extraFeeId);

    ExtraFeeDTO findExtraFeeById(Long extraFeeId);

    List<ExtraFeeDTO> findAllExtraFees();

    void deleteExtraFeeById(Long extraFeeId);
}
