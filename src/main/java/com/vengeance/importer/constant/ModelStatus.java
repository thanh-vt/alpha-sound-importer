package com.vengeance.importer.constant;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanhvt
 * @created 06/06/2021 - 5:18 CH
 * @project vengeance
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public enum ModelStatus {
    INACTIVE(0),
    ACTIVE(1),
    REMOVED(2);

    private static final Map<Integer, ModelStatus> STATUS_MAP = new HashMap<>();

    private final int value;

    public static ModelStatus fromValue(Integer integer) {
        if (integer == null) {
            return ModelStatus.INACTIVE;
        }
        ModelStatus gender = STATUS_MAP.get(integer);
        if (gender == null) {
            return ModelStatus.INACTIVE;
        }
        return gender;
    }

    public static class StatusAttributeConverter implements AttributeConverter<ModelStatus, Integer> {

        @Override
        public Integer convertToDatabaseColumn(ModelStatus attribute) {
            if (attribute == null) {
                return ModelStatus.INACTIVE.value;
            }
            return attribute.value;
        }

        @Override
        public ModelStatus convertToEntityAttribute(Integer dbData) {
            return ModelStatus.fromValue(dbData);
        }
    }

}
