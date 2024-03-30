package com.deliveryfeecalculation.controller;

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
@RestController
@RequestMapping(EXTRA_FEE_URL)
public class ExtraFeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFeeController.class);


    private final ExtraFeeRepository extraFeeRepository;

    public ExtraFeeController(final ExtraFeeRepository extraFeeRepository) {
        this.extraFeeRepository = extraFeeRepository;
    }

    /**
     POST    http://localhost:8080/v1.0/extra_fee
     {
     "name": "wind speed",
     "vehicleType": "wind speed",
     "extraFee": 2.0,
     "lowerLimit": 15.0,
     "upperLimit": 25.0,
     "weatherPhenomenon": "wind speed",
     "isForbidden": "false",
     "status": "CURRENT"
     }
     */
    @PostMapping()
    public ResponseEntity<?> createExtraFee(@RequestBody final ExtraFee extraFee) {

        if (extraFee == null || extraFee.getName() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all fields");

        extraFee.setCreatedDate(LocalDateTime.now());
        final ExtraFee savedExtraFee = extraFeeRepository.save(extraFee);

        LOGGER.debug("In createExtraFee received POST extraFee save successfully with id {} ", savedExtraFee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExtraFee);
    }


    /**
     PUT    http://localhost:8080/v1.0/extra_fee/1
     {
     "message": "Das ist ein gutes Auto",
     }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> archiveExtraFee(@PathVariable("id") final Long extraFeeId) {
        if (extraFeeId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please insert ExtraFee id");

        final ExtraFee extraFeeForUpdate = extraFeeRepository.findById(extraFeeId)
              .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " +extraFeeId));

        extraFeeForUpdate.setStatus(Status.ARCHIVE);
        ExtraFee archivedExtraFee = extraFeeRepository.save(extraFeeForUpdate);

        LOGGER.debug("In archiveExtraFee received PUT extraFee archive successfully with id {} ", archivedExtraFee.getId());

        return ResponseEntity.status(HttpStatus.OK).body(archivedExtraFee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findExtraFeeById(@PathVariable("id")final Long extraFeeId) {

       final ExtraFee extraFee = extraFeeRepository.findById(extraFeeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExtraFee not found for id: " +extraFeeId));

        LOGGER.debug("In findExtraFeeById received GET extraFee find successfully with id {} ", extraFee.getId());

        return ResponseEntity.status(HttpStatus.OK).body(extraFee);
    }
    /**
     GET    http://localhost:8080/extra_fee
     */
    @GetMapping()
    public ResponseEntity<?> getAllExtraFees() {

        final List<ExtraFee> extraFeeList = extraFeeRepository.findAll();
        if (extraFeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty list");
        }

        LOGGER.debug("In getAllExtraFees received GET get all ExtraFees successfully {} ", extraFeeList);

        return ResponseEntity.status(HttpStatus.OK).body(extraFeeList);
    }
    /**
     Delete    http://localhost:8080/extra_fee
     */
    @DeleteMapping ("/{Id}")
    public ResponseEntity<?> deleteExtraFee(@PathVariable("Id") Long extraFeeId) {

        extraFeeRepository.deleteById(extraFeeId);

        LOGGER.debug("In deleteExtraFee received DELETE ExtraFee delete successfully with id {} ", extraFeeId);

       return ResponseEntity.status(HttpStatus.OK).body("The ExtraFee has deleted successfully");
    }

}
