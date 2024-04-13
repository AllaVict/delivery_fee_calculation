package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.springframework.stereotype.Component;

@Component
public class BaseFeeDtoToBaseFeeConverter implements TypeConverter<BaseFeeDTO, BaseFee> {

    @Override
    public Class<BaseFeeDTO> getSourceClass() {
        return BaseFeeDTO.class;
    }

    @Override
    public Class<BaseFee> getTargetClass() {
        return BaseFee.class;
    }

    /**
     * Converts an {@link BaseFeeDTO} to an {@link BaseFee} entity.
     *
     * @param baseFeeDTO The BaseFeeDTO to convert.
     * @return The converted BaseFee entity.
     */
    @Override
    public BaseFee convert(final BaseFeeDTO baseFeeDTO) {
        final BaseFee baseFee = new BaseFee();
        baseFee.setId(baseFeeDTO.getId());
        baseFee.setCity(baseFeeDTO.getCity());
        baseFee.setVehicleType(baseFeeDTO.getVehicleType());
        baseFee.setFee(baseFeeDTO.getFee());
        baseFee.setStatus(baseFeeDTO.getStatus());
        baseFee.setCreatedDate(baseFeeDTO.getCreatedDate());

        return baseFee;
    }

}
