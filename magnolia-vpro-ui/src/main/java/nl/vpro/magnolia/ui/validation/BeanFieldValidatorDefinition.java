package nl.vpro.magnolia.ui.validation;

import info.magnolia.ui.field.ConfiguredFieldValidatorDefinition;
import info.magnolia.ui.field.ValidatorType;
import lombok.*;

@ValidatorType("beanValidator")
public class BeanFieldValidatorDefinition extends ConfiguredFieldValidatorDefinition {

    @Getter
    @Setter
    private String bean;

    @Getter
    @Setter
    private String property;

    @Getter
    @Setter
    private Class<?>[] groups;

    private Class<?> beanClass;

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
