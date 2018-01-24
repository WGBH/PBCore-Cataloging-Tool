package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PBCoreElement implements Cloneable {
    private final long id;
    private String fullPath;
    private String screenName;
    private String name;
    private List<PBCoreAttribute> attributes = new ArrayList<>();
    private List<PBCoreElement> subElements = new ArrayList<>();
    private boolean required;
    private boolean repeatable;
    private String description;
    private PBCoreElementType elementType;
    private boolean hasChildElements;
    public BooleanProperty hasMultipleProperty = new SimpleBooleanProperty();

    public StringProperty valueProperty = new SimpleStringProperty();

    private boolean supportsAttributes;
    public BooleanProperty validAttributesProperty = new SimpleBooleanProperty(true);
    public BooleanProperty validProperty = new SimpleBooleanProperty();
    public BooleanProperty fatalErrorProperty = new SimpleBooleanProperty();

    private int sequence;

    private ElementValueRestrictionType elementValueRestrictionType = ElementValueRestrictionType.SIMPLE;
    private String patternToFollow;
    private List<String> enumerationValues;

    public PBCoreElement() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreElement(String fullPath, String screenName, String name, boolean required, boolean repeatable, String description, String value, PBCoreElementType elementType, boolean supportsAttributes, boolean valid, int sequence,
                         ElementValueRestrictionType elementValueRestrictionType,
                         String patternToFollow,
                         List<String> enumerationValues, boolean validAttributesProperty, boolean hasChildElements) {
        this();
        this.fullPath = fullPath;
        this.screenName = screenName;
        this.name = name;
        this.required = required;
        this.hasChildElements = hasChildElements;
        this.repeatable = repeatable;
        this.description = description;
        this.valueProperty.setValue(value);
        this.elementType = elementType;
        this.supportsAttributes = supportsAttributes;
        setValid(valid);
        this.sequence = sequence;
        this.elementValueRestrictionType = elementValueRestrictionType;
        this.patternToFollow = patternToFollow;
        this.enumerationValues = enumerationValues;
        if (elementValueRestrictionType == ElementValueRestrictionType.SIMPLE && !hasChildElements) {
            setValid(getValue() != null && !getValue().trim().isEmpty());
        }
        //this.validAttributesProperty.setValue(validAttributesProperty);
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<PBCoreAttribute> attributes) {
        this.attributes = attributes;
        setSupportsAttributes(!this.attributes.isEmpty());
        attributes.forEach(pbCoreAttribute -> pbCoreAttribute.valueProperty.addListener((observable, oldValue, newValue) -> {
            setValidAttributes(attributes.stream().filter(pbCoreAttribute1 -> pbCoreAttribute1.getValue() == null || pbCoreAttribute1.getValue().trim().isEmpty()).count() == 0);
        }));
    }

    public void setSubElements(List<PBCoreElement> subElements) {
        this.subElements = subElements;
    }

    public void setElementType(PBCoreElementType elementType) {
        this.elementType = elementType;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getName() {
        return name;
    }

    public List<PBCoreAttribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(PBCoreAttribute attribute) {
        if (!attributes.contains(attribute)) {
            attribute.valueProperty.addListener((observable, oldValue, newValue) -> {
                updateValidAttributesStatus(newValue);
            });
            this.attributes.add(attribute);
            updateValidAttributesStatus(attribute.getValue());
        }
    }

    private void updateValidAttributesStatus(String newValue) {
        if (subElements.stream().filter(el -> !el.isValidAttributes()).count() > 1) {
            setValidAttributes(false);
        } else {
            setValidAttributes(attributes.stream().filter(pbCoreAttribute -> pbCoreAttribute.getValue() == null || pbCoreAttribute.getValue().trim().isEmpty()).count() == 0 && newValue != null && !newValue.trim().isEmpty());
        }
    }

    public List<PBCoreElement> getSubElements() {
        return subElements;
    }


    public List<PBCoreElement> getOrderedSubElements() {
        subElements.sort(Comparator.comparing(PBCoreElement::getSequence));
        return subElements;
    }

    public List<PBCoreElement> getRequiredSubElements() {
        return subElements.stream().filter(PBCoreElement::isRequired).collect(Collectors.toList());
    }

    public List<PBCoreElement> getOptionalSubElements() {
        return subElements.stream().filter(pbCoreElement -> !pbCoreElement.isRequired() && pbCoreElement.getValue() != null).collect(Collectors.toList());
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateElementContext(boolean required, String description) {
        this.required = required;
        this.description = description;
    }

    public PBCoreElementType getElementType() {
        return elementType;
    }

    public String getValue() {
        return valueProperty.getValue();
    }

    public void setValue(String value) {
        this.valueProperty.setValue(value);
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public void setHasMultiple(boolean hasMultipleProperty) {
        this.hasMultipleProperty.setValue(hasMultipleProperty);
    }

    public boolean getHasMultiple() {
        return hasMultipleProperty.getValue();
    }

    @Override
    public String toString() {
        return "{fullPath:'" + fullPath + "\', name='" + name + '\'' +
                ", subElements:" + subElements.size() +
                ", supportsAttributes:" + supportsAttributes +
                ", attributes:" + attributes.size() +
                ", required:" + required +
                ", repeatable:" + repeatable +
                ", valid:" + isValid() +
                ", sequence:" + sequence +
                ", value:\'" + getValue() + "\'}";
    }

    public void clearOptionalSubElements() {
        List<PBCoreElement> collect = subElements.stream().filter(pbCoreElement -> !pbCoreElement.isRequired()).collect(Collectors.toList());
        this.subElements.removeAll(collect);
        hasChildElements = !subElements.isEmpty();
    }

    public PBCoreElement copy() {
        return copy(true);
    }

    public PBCoreElement copy(boolean withOptionalAttributes) {
        PBCoreElement pbCoreElement = new PBCoreElement(fullPath, screenName, name, required, repeatable, description, getValue(), elementType, supportsAttributes, isValid(), sequence, elementValueRestrictionType, patternToFollow, enumerationValues, isValidAttributes(), hasChildElements);
        boolean validAttributes = true;
        for (PBCoreElement subElement : subElements) {
            PBCoreElement copy = subElement.copy(withOptionalAttributes);
            pbCoreElement.addSubElement(copy);
            validAttributes = !validAttributes || copy.isValidAttributes();
        }
        for (PBCoreAttribute attribute : attributes) {
            if (attribute.isRequired() || withOptionalAttributes) {
                pbCoreElement.addAttribute(attribute.copy());
                validAttributes = !validAttributes || (attribute.getValue() != null && !attribute.getValue().trim().isEmpty());
            }
        }
        pbCoreElement.setValidAttributes(validAttributes);
        return pbCoreElement;
    }

    public boolean isHasChildElements() {
        return hasChildElements;
    }

    public void updateStatus() {
        for (PBCoreElement subElement : subElements) {
            long count = subElements.stream().filter(sub -> sub.getName().equals(subElement.getName())).count();
            subElement.setHasMultiple(count > 1);
        }
    }

    public void removeSubElement(PBCoreElement pbCoreElement) {
        PBCoreElement pbCoreElement2 = this.subElements.stream().filter(pbCoreElement1 -> (Long.compare(pbCoreElement1.getId(), pbCoreElement.getId()) == 0) && StringUtils.compare(pbCoreElement1.getValue(), pbCoreElement.getValue())).findFirst().orElse(null);
        if (pbCoreElement2 != null) {
            this.subElements.remove(pbCoreElement2);
        }
        hasChildElements = !subElements.isEmpty();
        updateHasMultiple(pbCoreElement);
    }

    public void addSubElement(PBCoreElement element) {
        this.subElements.add(element);
        hasChildElements = !subElements.isEmpty();
        updateHasMultiple(element);
        element.validProperty.addListener((observable, oldValue, newValue) -> setValid(subElements.stream().filter(pbCoreElement -> !pbCoreElement.isValid()).filter(pbCoreElement -> pbCoreElement.getId() != element.getId()).count() == 0 && newValue));
        element.fatalErrorProperty.addListener((observable, oldValue, newValue) -> {
            boolean b = subElements.stream().filter(PBCoreElement::isFatalError).filter(pbCoreElement -> pbCoreElement.getId() != element.getId()).count() > 0;
            switch (elementValueRestrictionType) {
                case PATTERN:
                    Pattern pattern = Pattern.compile(getPatternToFollow());
                    Matcher matcher = pattern.matcher(getValue() == null ? "" : getValue());
                    setFatalError(!matcher.matches() || b || newValue);
                    break;
                case ENUMERATION:
                    setFatalError(b || getValue() == null || getValue().trim().isEmpty() || newValue);
                    break;
                case SIMPLE:
                default:
                    setFatalError(b || newValue);
                    break;
            }
        });
        element.validAttributesProperty.addListener((observable, oldValue, newValue) -> setValidAttributes(attributes.stream().filter(pbCoreAttribute -> pbCoreAttribute.getValue() == null || pbCoreAttribute.getValue().trim().isEmpty()).count() == 0 && newValue));
        setValid(isValid() && element.isValid());
    }

    public void updateHasMultiple(PBCoreElement element) {
        long count = subElements.stream().filter(e -> StringUtils.compare(e.getName(), element.getName())).count();
        subElements.stream().filter(e -> StringUtils.compare(e.getName(), element.getName())).forEach(pbCoreElement -> pbCoreElement.setHasMultiple(count > 1));
        element.setHasMultiple(count > 1);
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public long getId() {
        return id;
    }

    public void setSupportsAttributes(boolean supportsAttributes) {
        this.supportsAttributes = supportsAttributes;
    }

    public boolean isSupportsAttributes() {
        return supportsAttributes;
    }

    public void removeAttribute(PBCoreAttribute pbCoreAttribute) {
        PBCoreAttribute pbCoreElement2 = this.attributes.stream().filter(attributePredicate -> (Objects.equals(attributePredicate.getName(), pbCoreAttribute.getName())) && StringUtils.compare(attributePredicate.getValue(), pbCoreAttribute.getValue())).findFirst().orElse(null);
        if (pbCoreElement2 != null) {
            this.attributes.remove(pbCoreElement2);
        }
        if (subElements.stream().filter(el -> !el.isValidAttributes()).count() > 0) {
            setValidAttributes(false);
        } else {
            setValidAttributes(attributes.stream().filter(attr -> attr.getValue() == null || attr.getValue().trim().isEmpty()).count() == 0);
        }
    }

    public void setValid(boolean valid) {
        this.validProperty.setValue(valid);
        switch (elementValueRestrictionType) {
            case PATTERN:
                Pattern pattern = Pattern.compile(getPatternToFollow());
                Matcher matcher = pattern.matcher(getValue() == null ? "" : getValue());
                setFatalError(!matcher.matches());
                break;
            case ENUMERATION:
                setFatalError(subElements.stream().filter(PBCoreElement::isFatalError).count() > 0 || getValue() == null || getValue().trim().isEmpty());
                break;
            default:
                setFatalError(subElements.stream().filter(PBCoreElement::isFatalError).count() > 0);
                break;
        }
        if (subElements.stream().filter(el -> !el.isValidAttributes()).count() > 0) {
            setValidAttributes(false);
        } else {
            setValidAttributes(attributes.stream().filter(pbCoreAttribute -> pbCoreAttribute.getValue() == null || pbCoreAttribute.getValue().trim().isEmpty()).count() == 0);
        }
    }

    public boolean isValid() {
        return validProperty.getValue();
    }

    public ElementValueRestrictionType getElementValueRestrictionType() {
        return elementValueRestrictionType;
    }

    public void setElementValueRestrictionType(ElementValueRestrictionType elementValueRestrictionType) {
        this.elementValueRestrictionType = elementValueRestrictionType;
    }

    public String getPatternToFollow() {
        return patternToFollow;
    }

    public void setPatternToFollow(String patternToFollow) {
        this.patternToFollow = patternToFollow;
    }

    public List<String> getEnumerationValues() {
        return enumerationValues;
    }

    public void setEnumerationValues(List<String> enumerationValues) {
        this.enumerationValues = enumerationValues;
        if (!enumerationValues.isEmpty()) {
            setValue(enumerationValues.get(0));
            setValid(true);
        }
    }

    public boolean isFatalError() {
        return fatalErrorProperty.getValue();
    }

    public void setFatalError(boolean fatalError) {
        this.fatalErrorProperty.setValue(fatalError);
    }

    public boolean isValidAttributes() {
        return validAttributesProperty.get();
    }

    public void setValidAttributes(boolean validAttributesProperty) {
        this.validAttributesProperty.set(validAttributesProperty);
    }
}
