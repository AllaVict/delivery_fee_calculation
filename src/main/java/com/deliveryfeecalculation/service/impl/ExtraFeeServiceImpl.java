package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import com.deliveryfeecalculation.service.ExtraFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public ExtraFeeDTO createExtraFee(ExtraFeeDTO extraFeeDTO) {
        if (extraFeeDTO == null)
            throw new IllegalArgumentException("extraFeeDTO cannot be null");

        extraFeeDTO.setCreatedDate(LocalDateTime.now());
        final ExtraFee extraFeeToSave = extraFeeDTOExtraFeeTypeConverter.convert(extraFeeDTO);

        final ExtraFee savedExtraFee = extraFeeRepository.saveAndFlush(extraFeeToSave);

        LOGGER.debug("In createExtraFee received POST extraFee save successfully with id {} ", savedExtraFee);

        return extraFeeExtraFeeDTOTypeConverter.convert(savedExtraFee);
    }

    @Override
    public ExtraFeeDTO findExtraFeeById(Long extraFeeId) {
        final ExtraFee foundExtraFee = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        final ExtraFeeDTO foundExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(foundExtraFee);

        LOGGER.debug("In findExtraFeeById find extraFee successfully with id {} ", foundExtraFeeDto.getId());
         return foundExtraFeeDto;
    }

    @Override
    public List<ExtraFeeDTO> findAllExtraFees() {

        final List<ExtraFee> extraFeeList = extraFeeRepository.findAll();

        final List<ExtraFeeDTO> extraFeeDTOList = extraFeeExtraFeeDTOTypeConverter.convert(extraFeeList);

        LOGGER.debug("In getAllExtraFees find all ExtraFees successfully {} ", extraFeeDTOList);

        return extraFeeDTOList;

    }

    @Override
    public ExtraFeeDTO archiveExtraFee(final Long extraFeeId) {

        final ExtraFee extraFeeToArchive = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        extraFeeToArchive.setStatus(Status.ARCHIVE);
        final ExtraFee archivedExtraFee = extraFeeRepository.saveAndFlush(extraFeeToArchive);
        final ExtraFeeDTO archivedExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(archivedExtraFee);

        LOGGER.debug("In archiveExtraFee extraFee archive successfully with id {} ", archivedExtraFeeDto.getId());

        return  archivedExtraFeeDto;
    }

    @Override
    public void deleteExtraFeeById(Long extraFeeId) {
        Optional<ExtraFee> extraFeeToDelete = extraFeeRepository.findById(extraFeeId);
        if(extraFeeToDelete.isEmpty())
            extraFeeToDelete
                    .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        extraFeeRepository.delete(extraFeeToDelete.orElseThrow());

        LOGGER.debug("In deleteComment Deleting success Comment with id {}", extraFeeId);
    }

}
