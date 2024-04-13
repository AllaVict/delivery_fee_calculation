package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;


import java.util.List;

public interface BaseFeeService {
    BaseFeeDTO createBaseFee(BaseFeeDTO baseFeeDTO);

    BaseFeeDTO archiveBaseFee(Long baseFeeId);

    BaseFeeDTO findBaseFeeById(Long baseFeeId);

    List<BaseFeeDTO> findAllBaseFees();

    void deleteBaseFeeById(Long baseFeeId);

}
