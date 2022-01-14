package nl.vpro.magnolia.ui.validation;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ValidatorType("beanValidator")
@Data
public class BeanFieldValidatorDefinition extends ConfiguredFieldValidatorDefinition {


    String bean;
    String property;
    Class<?> beanClass;
    Class<?>[] groups;

    public BeanFieldValidatorDefinition() {
        setFactoryClass(BeanFieldValidatorFactory.class);
    }

    Class<?> getBeanClass() throws ClassNotFoundException {
        if (beanClass == null) {
            beanClass = Class.forName(bean);
        }
        return beanClass;
    }
}
