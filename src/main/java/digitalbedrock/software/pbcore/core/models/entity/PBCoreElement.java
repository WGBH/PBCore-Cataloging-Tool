package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PBCoreElement extends IPBCore implements Serializable {

    private final long id;
    private String fullPath;
    private String pathRepresentation;
    private String screenName;
    private String name;
    private List<PBCoreAttribute> attributes = new ArrayList<>();
    private List<PBCoreElement> subElements = new ArrayList<>();
    private boolean required;
    private boolean repeatable;
    private boolean choice;
    private String description;
    private PBCoreElementType elementType;
    private boolean hasChildElements;
    @JsonIgnore
    public BooleanProperty hasMultipleProperty = new SimpleBooleanProperty();
    @JsonIgnore
    public StringProperty valueProperty = new SimpleStringProperty();

    private boolean supportsAttributes;
    @JsonIgnore
    public BooleanProperty validAttributesProperty = new SimpleBooleanProperty(true);
    @JsonIgnore
    public BooleanProperty validProperty = new SimpleBooleanProperty();
    @JsonIgnore
    public BooleanProperty fatalErrorProperty = new SimpleBooleanProperty();

    private int sequence;

    private ElementValueRestrictionType elementValueRestrictionType = ElementValueRestrictionType.SIMPLE;
    private String patternToFollow;
    private List<String> enumerationValues;

    public PBCoreElement() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreElement(String pathRepresentation) {
        this();
        this.pathRepresentation = pathRepresentation;
    }

    public PBCoreElement(String fullPath, String pathRepresentation, String screenName, String name, boolean required, boolean repeatable, String description, String value, PBCoreElementType elementType,
            boolean supportsAttributes,
            boolean valid, boolean fatalError,
            int sequence,
            ElementValueRestrictionType elementValueRestrictionType,
            String patternToFollow,
            List<String> enumerationValues, boolean hasChildElements, boolean choice) {
        this();
        this.fullPath = fullPath;
        this.pathRepresentation = pathRepresentation;
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
        this.fatalErrorProperty.setValue(fatalError);
        this.sequence = sequence;
        this.elementValueRestrictionType = elementValueRestrictionType;
        this.patternToFollow = patternToFollow;
        this.enumerationValues = enumerationValues;
        this.choice = choice;
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
        hasChildElements = !subElements.isEmpty();
    }

    public void setElementType(PBCoreElementType elementType) {
        this.elementType = elementType;
    }

    @Override
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
        ArrayList<PBCoreElement> pbCoreElements = new ArrayList<>(subElements);
        pbCoreElements.sort(Comparator.comparing(PBCoreElement::getSequence));
        return pbCoreElements;
    }

    public List<PBCoreElement> getRequiredSubElements() {
        return subElements.stream().filter(PBCoreElement::isRequired).collect(Collectors.toList());
    }

    public List<PBCoreElement> getOptionalSubElements() {
        return subElements.stream().filter(pbCoreElement -> !pbCoreElement.isRequired() && pbCoreElement.getValue() != null).collect(Collectors.toList());
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
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

    @Override
    public String getValue() {
        return valueProperty.getValue();
    }

    public void setValue(String value) {
        this.valueProperty.setValue(value);
    }

    @Override
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

    public void clearOptionalSubElements() {
        List<PBCoreElement> collect = subElements.stream().filter(pbCoreElement -> !pbCoreElement.isRequired()).collect(Collectors.toList());
        this.subElements.removeAll(collect);
        hasChildElements = !subElements.isEmpty();
    }

    public PBCoreElement copy() {
        return copy(true);
    }

    public PBCoreElement copy(boolean withOptionalAttributes) {
        PBCoreElement pbCoreElement = new PBCoreElement(fullPath, pathRepresentation, screenName, name, required, repeatable, description, getValue(), elementType, supportsAttributes, isValid(), isFatalError(), sequence, elementValueRestrictionType, patternToFollow, enumerationValues, hasChildElements, choice);
        boolean validAttributes = true;
        for (PBCoreElement subElement : subElements) {
            PBCoreElement copy = subElement.copy(withOptionalAttributes);
            pbCoreElement.addSubElement(copy);
            validAttributes = validAttributes && copy.isValidAttributes();
        }
        for (PBCoreAttribute attribute : attributes) {
            if (attribute.isRequired() || withOptionalAttributes) {
                pbCoreElement.addAttribute(attribute.copy());
                validAttributes = validAttributes && (attribute.getValue() != null && !attribute.getValue().trim().isEmpty());
            }
        }
        pbCoreElement.setValidAttributes(validAttributes);
        return pbCoreElement;
    }

    @Override
    public boolean isHasChildElements() {
        return hasChildElements;
    }

    public void updateStatus() {
        subElements.forEach((subElement) -> {
            long count = subElements.stream().filter(sub -> sub.getName().equals(subElement.getName())).count();
            subElement.setHasMultiple(count > 1);
        });
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

    @Override
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

    public final void setValid(boolean valid) {
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

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    @Override
    public String getPathRepresentation() {
        return pathRepresentation;
    }

    public void setPathRepresentation(String pathRepresentation) {
        this.pathRepresentation = pathRepresentation;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }
}
