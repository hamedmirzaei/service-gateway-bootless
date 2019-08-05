package ir.navaco.core.gateway.enums.converter;

import ir.navaco.core.gateway.enums.SubSystemCategoryType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SubSystemCategoryTypeConverter implements AttributeConverter<SubSystemCategoryType, String> {

    @Override
    public String convertToDatabaseColumn(SubSystemCategoryType subSystemCategoryEnum) {
        if (subSystemCategoryEnum == null) {
            return null;
        }
        return subSystemCategoryEnum.getCategoryName();
    }

    @Override
    public SubSystemCategoryType convertToEntityAttribute(String categoryName) {
        if (categoryName == null) {
            return null;
        }

        return Stream.of(SubSystemCategoryType.values())
                .filter(c -> c.getCategoryName().equals(categoryName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
