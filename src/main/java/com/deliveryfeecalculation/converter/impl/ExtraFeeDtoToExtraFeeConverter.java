package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.stereotype.Component;

@Component
public class ExtraFeeDtoToExtraFeeConverter implements TypeConverter<ExtraFeeDTO, ExtraFee> {

    @Override
    public Class<ExtraFeeDTO> getSourceClass() {
        return ExtraFeeDTO.class;
    }

    @Override
    public Class<ExtraFee> getTargetClass() {
        return ExtraFee.class;
    }

    /**
     * Converts an {@link ExtraFeeDTO} to an {@link ExtraFee} entity.
     *
     * @param extraFeeDTO The ExtraFeeDTO to convert.
     * @return The converted ExtraFee entity.
     */
    @Override
    public ExtraFee convert(final ExtraFeeDTO extraFeeDTO) {
        final ExtraFee extraFee = new ExtraFee();
        extraFee.setId(extraFeeDTO.getId());
        extraFee.setName(extraFeeDTO.getName());
        extraFee.setVehicleType(extraFeeDTO.getVehicleType());
        extraFee.setFee(extraFeeDTO.getFee());
        extraFee.setLowerLimit(extraFeeDTO.getLowerLimit());
        extraFee.setUpperLimit(extraFeeDTO.getUpperLimit());
        extraFee.setWeatherPhenomenon(extraFeeDTO.getWeatherPhenomenon());
        extraFee.setForbidden(extraFeeDTO.getForbidden());
        extraFee.setStatus(extraFeeDTO.getStatus());
        extraFee.setCreatedDate(extraFeeDTO.getCreatedDate());

        return extraFee;
    }

}
