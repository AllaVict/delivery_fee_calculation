package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.exception.FeeCreationException;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.BaseFeeRepository;
import com.deliveryfeecalculation.service.BaseFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

 /**
     * Service for managing base fees.
     * Provides operations to create, archive, find, and delete base fees, as well as to retrieve all existing base fees.
     * Each operation is designed to manipulate the BaseFee records in the database through a defined API,
     * ensuring data integrity and consistent access patterns.
     *
     * @author Alla Borodina
     */
@Service
public class BaseFeeServiceImpl implements BaseFeeService {

        public static final Logger LOGGER = LoggerFactory.getLogger(BaseFeeServiceImpl.class);

        private final BaseFeeRepository baseFeeRepository;

        private final TypeConverter<BaseFeeDTO, BaseFee> baseFeeDTOBaseFeeTypeConverter;

        private final TypeConverter<BaseFee, BaseFeeDTO> baseFeeBaseFeeDTOTypeConverter;

        public BaseFeeServiceImpl(final BaseFeeRepository baseFeeRepository,
                                  final TypeConverter<BaseFeeDTO, BaseFee> baseFeeDTOBaseFeeTypeConverter,
                                  final TypeConverter<BaseFee, BaseFeeDTO> baseFeeBaseFeeDTOTypeConverter) {
            this.baseFeeRepository = baseFeeRepository;
            this.baseFeeDTOBaseFeeTypeConverter = baseFeeDTOBaseFeeTypeConverter;
            this.baseFeeBaseFeeDTOTypeConverter = baseFeeBaseFeeDTOTypeConverter;
        }

        /**
         * Creates a new BaseFee record based on the provided BaseFeeDTO.
         *
         * @param baseFeeDTO the data transfer object containing the details of the base fee to be created
         * @return an BaseFeeDTO representing the created base fee
         * @throws FeeCreationException if the baseFeeDTO is null
         */
        @Override
        public BaseFeeDTO createBaseFee(BaseFeeDTO baseFeeDTO) {
            if (baseFeeDTO == null)
                throw new FeeCreationException("baseFeeDTO cannot be null");

            baseFeeDTO.setCreatedDate(LocalDateTime.now());
            baseFeeDTO.setStatus(Status.CURRENT);
            final BaseFee baseFeeToSave = baseFeeDTOBaseFeeTypeConverter.convert(baseFeeDTO);

            final BaseFee savedBaseFee = baseFeeRepository.saveAndFlush(baseFeeToSave);

            LOGGER.debug("In createBaseFee received POST baseFee save successfully with id {} ", savedBaseFee);

            return baseFeeBaseFeeDTOTypeConverter.convert(savedBaseFee);
        }

        /**
         * Archives an existing base fee by its ID.
         *
         * @param baseFeeId the ID of the base fee to be archived
         * @return an BaseFeeDTO representing the archived base fee
         * @throws ResourceNotFoundException if no base fee is found for the given ID
         */
        @Override
        public BaseFeeDTO archiveBaseFee(final Long baseFeeId) {

            final BaseFee baseFeeToArchive = baseFeeRepository.findById(baseFeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("BaseFee not found for id: " + baseFeeId));

            baseFeeToArchive.setStatus(Status.ARCHIVE);
            final BaseFee archivedBaseFee = baseFeeRepository.saveAndFlush(baseFeeToArchive);
            final BaseFeeDTO archivedBaseFeeDto = baseFeeBaseFeeDTOTypeConverter.convert(archivedBaseFee);

            LOGGER.debug("In archiveBaseFee BaseFee archive successfully with id {} ", archivedBaseFeeDto.getId());

            return archivedBaseFeeDto;
        }

        /**
         * Retrieves an base fee by its ID.
         *
         * @param baseFeeId the ID of the base fee to retrieve
         * @return an BaseFeeDTO representing the found base fee
         * @throws ResourceNotFoundException if no base fee is found for the given ID
         */
        @Override
        public BaseFeeDTO findBaseFeeById(Long baseFeeId) {
            final BaseFee foundBaseFee = baseFeeRepository.findById(baseFeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("BaseFee not found for id: " + baseFeeId));

            final BaseFeeDTO foundBaseFeeDto = baseFeeBaseFeeDTOTypeConverter.convert(foundBaseFee);

            LOGGER.debug("In findBaseFeeById find BaseFee successfully with id {} ", foundBaseFeeDto.getId());
            return foundBaseFeeDto;
        }

        /**
         * Retrieves all base fees.
         *
         * @return a list of BaseFeeDTOs representing all stored base fees
         */
        @Override
        public List<BaseFeeDTO> findAllBaseFees() {

            final List<BaseFee> baseFeeList = baseFeeRepository.findAll();

            final List<BaseFeeDTO> baseFeeDTOList = baseFeeBaseFeeDTOTypeConverter.convert(baseFeeList);

            LOGGER.debug("In findAllBaseFees find all BaseFees successfully {} ", baseFeeDTOList);

            return baseFeeDTOList;

        }

        /**
         * Deletes an base fee by its ID.
         *
         * @param baseFeeId the ID of the base fee to be deleted
         * @throws ResourceNotFoundException if no base fee is found for the given ID
         */
        @Override
        public void deleteBaseFeeById(Long baseFeeId) {
            Optional<BaseFee> baseFeeToDelete = baseFeeRepository.findById(baseFeeId);
            if (baseFeeToDelete.isEmpty())
                baseFeeToDelete
                        .orElseThrow(() -> new ResourceNotFoundException("BaseFee not found for id: " + baseFeeId));

            baseFeeRepository.delete(baseFeeToDelete.orElseThrow());

            LOGGER.debug("In deleteBaseFeeById Deleting success baseFee with id {}", baseFeeId);
        }

    }
