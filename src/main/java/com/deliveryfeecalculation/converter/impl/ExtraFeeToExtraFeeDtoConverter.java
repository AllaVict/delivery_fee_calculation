package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.stereotype.Component;

@Component
public class ExtraFeeToExtraFeeDtoConverter implements TypeConverter<ExtraFee, ExtraFeeDTO> {


    @Override
    public Class<ExtraFee> getSourceClass() {
        return ExtraFee.class;
    }

    @Override
    public Class<ExtraFeeDTO> getTargetClass() {
        return ExtraFeeDTO.class;
    }

    /**
     * Converts an {@link ExtraFee} entity to an {@link ExtraFeeDTO}.
     *
     * @param extraFee The ExtraFee entity to convert.
     * @return The converted ExtraFeeDTO.
     */
    @Override
    public ExtraFeeDTO convert(final ExtraFee extraFee) {
        final ExtraFeeDTO extraFeeDTO = new ExtraFeeDTO();
        extraFeeDTO.setId(extraFee.getId());
        extraFeeDTO.setName(extraFee.getName());
        extraFeeDTO.setVehicleType(extraFee.getVehicleType());
        extraFeeDTO.setFee(extraFee.getFee());
        extraFeeDTO.setLowerLimit(extraFee.getLowerLimit());
        extraFeeDTO.setUpperLimit(extraFee.getUpperLimit());
        extraFeeDTO.setWeatherPhenomenon(extraFee.getWeatherPhenomenon());
        extraFeeDTO.setForbidden(extraFee.getForbidden());
        extraFeeDTO.setStatus(extraFee.getStatus());
        extraFeeDTO.setCreatedDate(extraFee.getCreatedDate());

        return extraFeeDTO;
    }

}
