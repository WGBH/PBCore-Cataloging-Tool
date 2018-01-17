package digitalbedrock.software.pbcore.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import digitalbedrock.software.pbcore.utils.StringUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    public PBCoreElement() {
        id = System.currentTimeMillis() + UUID.randomUUID().getLeastSignificantBits();
    }

    public PBCoreElement(String fullPath, String screenName, String name, PBCoreElementType elementType) {
        this();
        this.fullPath = fullPath;
        this.screenName = screenName;
        this.name = name;
        this.elementType = elementType;
    }

    private PBCoreElement(long id, String fullPath, String screenName, String name, boolean required, boolean repeatable, String description, String value, PBCoreElementType elementType) {
        this();
        //this.id = id;
        this.fullPath = fullPath;
        this.screenName = screenName;
        this.name = name;
        this.required = required;
        this.repeatable = repeatable;
        this.description = description;
        this.valueProperty.setValue(value);
        this.elementType = elementType;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<PBCoreAttribute> attributes) {
        this.attributes = attributes;
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
            this.attributes.add(attribute);
        }
    }

    public List<PBCoreElement> getSubElements() {
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
                ", required:" + required +
                ", repeatable:" + repeatable +
                ", value:\'" + getValue() + "\'}";
    }

    public void clearOptionalSubElements() {
        List<PBCoreElement> collect = subElements.stream().filter(pbCoreElement -> !pbCoreElement.isRequired()).collect(Collectors.toList());
        this.subElements.removeAll(collect);
        hasChildElements = !subElements.isEmpty();
    }

    @Override
    public PBCoreElement clone() {
        PBCoreElement pbCoreElement = new PBCoreElement(id, fullPath, screenName, name, required, repeatable, description, getValue(), elementType);
        for (PBCoreElement subElement : subElements) {
            pbCoreElement.addSubElement(subElement.clone());
        }
        for (PBCoreAttribute attribute : attributes) {
            pbCoreElement.addAttribute(attribute.clone());
        }
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
        System.out.println("Before:" + subElements.size());
        PBCoreElement pbCoreElement2 = this.subElements.stream().filter(pbCoreElement1 -> (Long.compare(pbCoreElement1.getId(), pbCoreElement.getId()) == 0) && StringUtils.compare(pbCoreElement1.getValue(), pbCoreElement.getValue())).findFirst().orElse(null);
        if (pbCoreElement2 != null) {
            this.subElements.remove(pbCoreElement2);
        }
        System.out.println("After:" + subElements.size());
        hasChildElements = !subElements.isEmpty();
        updateHasMultiple(pbCoreElement);
    }

    public void addSubElement(PBCoreElement element) {
        this.subElements.add(element);
        hasChildElements = !subElements.isEmpty();
        updateHasMultiple(element);
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
}
