package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.stereotype.Component;

@Component
public class BaseFeeToBaseFeeDtoConverter implements TypeConverter<BaseFee, BaseFeeDTO> {

    @Override
    public Class<BaseFee> getSourceClass() {
        return BaseFee.class;
    }

    @Override
    public Class<BaseFeeDTO> getTargetClass() {
        return BaseFeeDTO.class;
    }

    /**
     * Converts an {@link BaseFee} entity to an {@link BaseFeeDTO}.
     *
     * @param baseFee The BaseFee entity to convert.
     * @return The converted BaseFeeDTO.
     */
    @Override
    public BaseFeeDTO convert(final BaseFee baseFee) {
        final BaseFeeDTO baseFeeDTO = new BaseFeeDTO();
        baseFeeDTO.setId(baseFee.getId());
        baseFeeDTO.setCity(baseFee.getCity());
        baseFeeDTO.setVehicleType(baseFee.getVehicleType());
        baseFeeDTO.setFee(baseFee.getFee());
        baseFeeDTO.setStatus(baseFee.getStatus());
        baseFeeDTO.setCreatedDate(baseFee.getCreatedDate());

        return baseFeeDTO;
    }

}
