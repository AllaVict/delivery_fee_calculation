package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.FeeCreationException;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import com.deliveryfeecalculation.service.ExtraFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing extra fees.
 * Provides operations to create, archive, find, and delete extra fees, as well as to retrieve all existing extra fees.
 * Each operation is designed to manipulate the ExtraFee records in the database through a defined API,
 * ensuring data integrity and consistent access patterns.
 *
 * @author Alla Borodina
 */
@Service
public class ExtraFeeServiceImpl implements ExtraFeeService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExtraFeeServiceImpl.class);

    private final ExtraFeeRepository extraFeeRepository;

    private final TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter;

    private final TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter;

    public ExtraFeeServiceImpl(final ExtraFeeRepository extraFeeRepository,
                               final TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter,
                               final TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter) {
        this.extraFeeRepository = extraFeeRepository;
        this.extraFeeDTOExtraFeeTypeConverter = extraFeeDTOExtraFeeTypeConverter;
        this.extraFeeExtraFeeDTOTypeConverter = extraFeeExtraFeeDTOTypeConverter;
    }

    /**
     * Creates a new ExtraFee record based on the provided ExtraFeeDTO.
     *
     * @param extraFeeDTO the data transfer object containing the details of the extra fee to be created
     * @return an ExtraFeeDTO representing the created extra fee
     * @throws IllegalArgumentException if the extraFeeDTO is null
     */
    @Override
    public ExtraFeeDTO createExtraFee(ExtraFeeDTO extraFeeDTO) {
        if (extraFeeDTO == null)
            throw new FeeCreationException("extraFeeDTO cannot be null");

        extraFeeDTO.setCreatedDate(LocalDateTime.now());
        extraFeeDTO.setStatus(Status.CURRENT);
        final ExtraFee extraFeeToSave = extraFeeDTOExtraFeeTypeConverter.convert(extraFeeDTO);

        final ExtraFee savedExtraFee = extraFeeRepository.saveAndFlush(extraFeeToSave);

        LOGGER.debug("In createExtraFee received POST extraFee save successfully with id {} ", savedExtraFee);

        return extraFeeExtraFeeDTOTypeConverter.convert(savedExtraFee);
    }

    /**
     * Archives an existing extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to be archived
     * @return an ExtraFeeDTO representing the archived extra fee
     * @throws ResourceNotFoundException if no extra fee is found for the given ID
     */
    @Override
    public ExtraFeeDTO archiveExtraFee(final Long extraFeeId) {

        final ExtraFee extraFeeToArchive = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        extraFeeToArchive.setStatus(Status.ARCHIVE);
        final ExtraFee archivedExtraFee = extraFeeRepository.saveAndFlush(extraFeeToArchive);
        final ExtraFeeDTO archivedExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(archivedExtraFee);

        LOGGER.debug("In archiveExtraFee extraFee archive successfully with id {} ", archivedExtraFeeDto.getId());

        return archivedExtraFeeDto;
    }

    /**
     * Retrieves an extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to retrieve
     * @return an ExtraFeeDTO representing the found extra fee
     * @throws ResourceNotFoundException if no extra fee is found for the given ID
     */
    @Override
    public ExtraFeeDTO findExtraFeeById(Long extraFeeId) {
        final ExtraFee foundExtraFee = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        final ExtraFeeDTO foundExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(foundExtraFee);

        LOGGER.debug("In findExtraFeeById find extraFee successfully with id {} ", foundExtraFeeDto.getId());
        return foundExtraFeeDto;
    }

    /**
     * Retrieves all extra fees.
     *
     * @return a list of ExtraFeeDTOs representing all stored extra fees
     */
    @Override
    public List<ExtraFeeDTO> findAllExtraFees() {

        final List<ExtraFee> extraFeeList = extraFeeRepository.findAll();

        final List<ExtraFeeDTO> extraFeeDTOList = extraFeeExtraFeeDTOTypeConverter.convert(extraFeeList);

        LOGGER.debug("In getAllExtraFees find all ExtraFees successfully {} ", extraFeeDTOList);

        return extraFeeDTOList;

    }

    /**
     * Deletes an extra fee by its ID.
     *
     * @param extraFeeId the ID of the extra fee to be deleted
     * @throws ResourceNotFoundException if no extra fee is found for the given ID
     */
    @Override
    public void deleteExtraFeeById(Long extraFeeId) {
        Optional<ExtraFee> extraFeeToDelete = extraFeeRepository.findById(extraFeeId);
        if (extraFeeToDelete.isEmpty())
            extraFeeToDelete
                    .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        extraFeeRepository.delete(extraFeeToDelete.orElseThrow());

        LOGGER.debug("In deleteComment Deleting success ExtraFee with id {}", extraFeeId);
    }

}
