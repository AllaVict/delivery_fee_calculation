package com.deliveryfeecalculation.service;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;

import java.util.List;

public interface ExtraFeeService {

    /**
     * Creates a new ExtraFee record based on the provided ExtraFeeDTO.
     *
     * @param extraFeeDTO the data transfer object containing the details of the extra fee to be created
     * @return an ExtraFeeDTO representing the created extra fee
     */
    ExtraFeeDTO createExtraFee(ExtraFeeDTO extraFeeDTO);

    /**
     * Archives an existing extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to be archived
     * @return an ExtraFeeDTO representing the archived extra fee
     */
    ExtraFeeDTO archiveExtraFee(Long extraFeeId);

    /**
     * Retrieves an extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to retrieve
     * @return an ExtraFeeDTO representing the found extra fee
     */
    ExtraFeeDTO findExtraFeeById(Long extraFeeId);

    /**
     * Retrieves all extra fees.
     *
     * @return a list of ExtraFeeDTOs representing all stored extra fees
     */
    List<ExtraFeeDTO> findAllExtraFees();

    /**
     * Deletes an extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to be deleted
     */
    void deleteExtraFeeById(Long extraFeeId);

}
