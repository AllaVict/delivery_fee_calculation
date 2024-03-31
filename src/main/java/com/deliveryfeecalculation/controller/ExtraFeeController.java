package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.deliveryfeecalculation.constants.Constants.Endpoints.EXTRA_FEE_URL;

/**
 * Controller for handling extra fee  requests.
 * This controller provides endpoints to create, archive, find by id, delete a ExtraFee and find all extra fees.
 *
 * @author Alla Borodina
 */
@RestController
@RequestMapping(EXTRA_FEE_URL)
public class ExtraFeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFeeController.class);

    private final ExtraFeeRepository extraFeeRepository;

    private final TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter;

    private final TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter;

    public ExtraFeeController(final ExtraFeeRepository extraFeeRepository,
                              final TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter,
                              final TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter) {
        this.extraFeeRepository = extraFeeRepository;
        this.extraFeeDTOExtraFeeTypeConverter = extraFeeDTOExtraFeeTypeConverter;
        this.extraFeeExtraFeeDTOTypeConverter = extraFeeExtraFeeDTOTypeConverter;
    }

    /**
     * Creates a new ExtraFee entity based on the provided ExtraFeeDTO.
     *
     * @param extraFeeDto The DTO containing ExtraFee data.
     * @return A {@link ResponseEntity} containing the created ExtraFee entity or a bad request error.
     */
    @PostMapping()
    public ResponseEntity<?> createExtraFee(@RequestBody final ExtraFeeDTO extraFeeDto) {

        if (extraFeeDto == null || extraFeeDto.getName() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all fields");

        extraFeeDto.setCreatedDate(LocalDateTime.now());
        final ExtraFee extraFeeToSave = extraFeeDTOExtraFeeTypeConverter.convert(extraFeeDto);

        final ExtraFee savedExtraFee = extraFeeRepository.save(extraFeeToSave);

        LOGGER.debug("In createExtraFee received POST extraFee save successfully with id {} ", savedExtraFee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExtraFee);
    }

    /**
     * Archives an existing ExtraFee entity by its ID.
     *
     * @param extraFeeId The ID of the ExtraFee to archive.
     * @return A {@link ResponseEntity} containing the archived ExtraFee entity or a bad request error if the ID is null.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> archiveExtraFee(@PathVariable("id") final Long extraFeeId) {
        if (extraFeeId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert ExtraFee id");

        final ExtraFee extraFeeToUpdate = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        extraFeeToUpdate.setStatus(Status.ARCHIVE);
        final ExtraFee archivedExtraFee = extraFeeRepository.save(extraFeeToUpdate);

        final ExtraFeeDTO archivedExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(archivedExtraFee);

        LOGGER.debug("In archiveExtraFee received PUT extraFee archive successfully with id {} ", archivedExtraFeeDto.getId());

        return ResponseEntity.status(HttpStatus.OK).body(archivedExtraFeeDto);
    }

    /**
     * Retrieves an ExtraFee entity by its ID.
     *
     * @param extraFeeId The ID of the ExtraFee to find.
     * @return A {@link ResponseEntity} containing the found ExtraFee entity or a not found error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findExtraFeeById(@PathVariable("id") final Long extraFeeId) {

        final ExtraFee foundExtraFee = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " + extraFeeId));

        final ExtraFeeDTO foundExtraFeeDto = extraFeeExtraFeeDTOTypeConverter.convert(foundExtraFee);

        LOGGER.debug("In findExtraFeeById received GET extraFee find successfully with id {} ", foundExtraFeeDto.getId());

        return ResponseEntity.status(HttpStatus.OK).body(foundExtraFeeDto);
    }

    /**
     * Retrieves all ExtraFee entities.
     *
     * @return A {@link ResponseEntity} containing a list of all ExtraFee entities or a not found error if the list is empty.
     */
    @GetMapping()
    public ResponseEntity<?> getAllExtraFees() {

        final List<ExtraFee> extraFeeList = extraFeeRepository.findAll();
        if (extraFeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty list");
        }
        final List<ExtraFeeDTO> extraFeeDTOList = extraFeeExtraFeeDTOTypeConverter.convert(extraFeeList);

        LOGGER.debug("In getAllExtraFees received GET get all ExtraFees successfully {} ", extraFeeDTOList);

        return ResponseEntity.status(HttpStatus.OK).body(extraFeeDTOList);
    }

    /**
     * Deletes an ExtraFee entity by its ID.
     *
     * @param extraFeeId The ID of the ExtraFee to delete.
     * @return A {@link ResponseEntity} confirming the deletion or a bad request error if the ID is null.
     */
    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteExtraFee(@PathVariable("Id") Long extraFeeId) {

        extraFeeRepository.deleteById(extraFeeId);

        LOGGER.debug("In deleteExtraFee received DELETE ExtraFee delete successfully with id {} ", extraFeeId);

        return ResponseEntity.status(HttpStatus.OK).body("The ExtraFee has deleted successfully");
    }

}
