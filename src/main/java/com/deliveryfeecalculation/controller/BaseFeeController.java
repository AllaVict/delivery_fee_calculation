package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.service.BaseFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deliveryfeecalculation.constants.Constants.Endpoints.BASE_FEE_URL;

/**
 * Controller for handling base fee  requests.
 * This controller provides endpoints to create, archive, find by id, delete a BaseFee and find all base fees.
 *
 * @author Alla Borodina
 */
@RestController
@RequestMapping(BASE_FEE_URL)
public class BaseFeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFeeController.class);

    private final BaseFeeService baseFeeService;

    public BaseFeeController(final BaseFeeService baseFeeService) {
        this.baseFeeService = baseFeeService;
    }


    /**
     * Creates a new BaseFee entity based on the provided BaseFeeDTO.
     *
     * @param baseFeeDTO The DTO containing BaseFee data.
     * @return A {@link ResponseEntity} containing the created BaseFee entity or a bad request error.
     */
    @PostMapping()
    public ResponseEntity<?> createBaseFee(@RequestBody final BaseFeeDTO baseFeeDTO) {

        if (baseFeeDTO == null || baseFeeDTO.getCity() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all fields");

        final BaseFeeDTO savedBaseFee = baseFeeService.createBaseFee(baseFeeDTO);

        LOGGER.debug("In createBaseFee received POST BaseFee save successfully with id {} ", savedBaseFee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBaseFee);
    }

    /**
     * Archives an existing BaseFee entity by its ID.
     *
     * @param baseFeeId The ID of the BaseFee to archive.
     * @return A {@link ResponseEntity} containing the archived BaseFee entity or a bad request error if the ID is null.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> archiveBaseFee(@PathVariable("id") final Long baseFeeId) {
        if (baseFeeId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert BaseFee id");

        final BaseFeeDTO archivedBaseFeeDto = baseFeeService.archiveBaseFee(baseFeeId);

        LOGGER.debug("In archiveBaseFee received PUT BaseFee archive successfully with id {} ", baseFeeId);

        return ResponseEntity.status(HttpStatus.OK).body(archivedBaseFeeDto);
    }

    /**
     * Retrieves an BaseFee entity by its ID.
     *
     * @param baseFeeId The ID of the BaseFee to find.
     * @return A {@link ResponseEntity} containing the found BaseFee entity or a not found error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findBaseFeeById(@PathVariable("id") final Long baseFeeId) {

        final BaseFeeDTO foundBaseFeeDto = baseFeeService.findBaseFeeById(baseFeeId);

        LOGGER.debug("In findBaseFeeById received GET BaseFee find successfully with id {} ", baseFeeId);

        return ResponseEntity.status(HttpStatus.OK).body(foundBaseFeeDto);
    }

    /**
     * Retrieves all BaseFee entities.
     *
     * @return A {@link ResponseEntity} containing a list of all BaseFee entities or a not found error if the list is empty.
     */
    @GetMapping()
    public ResponseEntity<?> findAllBaseFees() {

        final List<BaseFeeDTO> baseFeeDTOList = baseFeeService.findAllBaseFees();
        if (baseFeeDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty list");
        }

        LOGGER.debug("In findAllBaseFees received GET get all BaseFees successfully {} ", baseFeeDTOList);

        return ResponseEntity.status(HttpStatus.OK).body(baseFeeDTOList);
    }

    /**
     * Deletes an BaseFee entity by its ID.
     *
     * @param baseFeeId The ID of the BaseFee to delete.
     * @return A {@link ResponseEntity} confirming the deletion or a bad request error if the ID is null.
     */
    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteBaseFeeById(@PathVariable("Id") Long baseFeeId) {

        baseFeeService.deleteBaseFeeById(baseFeeId);

        LOGGER.debug("In deleteBaseFeeById received DELETE BaseFee delete successfully with id {} ", baseFeeId);

        return ResponseEntity.status(HttpStatus.OK).body("The BaseFee has deleted successfully");
    }

}
