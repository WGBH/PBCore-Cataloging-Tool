package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Definition: The descriptionType schema type is a complex group of attributes
 * that help define the description type, as well as allowing for descriptions
 * of segments and relevant times.
 *
 * <p>
 * Java class for descriptionStringType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="descriptionStringType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *       &lt;attribute name="descriptionType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="descriptionTypeSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="descriptionTypeRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="descriptionTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="descriptionTypeAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="segmentType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="segmentTypeSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="segmentTypeRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="segmentTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="segmentTypeAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "descriptionStringType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "value"
})
public class DescriptionStringType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "descriptionType")
    protected String descriptionType;
    @XmlAttribute(name = "descriptionTypeSource")
    protected String descriptionTypeSource;
    @XmlAttribute(name = "descriptionTypeRef")
    protected String descriptionTypeRef;
    @XmlAttribute(name = "descriptionTypeVersion")
    protected String descriptionTypeVersion;
    @XmlAttribute(name = "descriptionTypeAnnotation")
    protected String descriptionTypeAnnotation;
    @XmlAttribute(name = "segmentType")
    protected String segmentType;
    @XmlAttribute(name = "segmentTypeSource")
    protected String segmentTypeSource;
    @XmlAttribute(name = "segmentTypeRef")
    protected String segmentTypeRef;
    @XmlAttribute(name = "segmentTypeVersion")
    protected String segmentTypeVersion;
    @XmlAttribute(name = "segmentTypeAnnotation")
    protected String segmentTypeAnnotation;
    @XmlAttribute(name = "startTime")
    protected String startTime;
    @XmlAttribute(name = "endTime")
    protected String endTime;
    @XmlAttribute(name = "timeAnnotation")
    protected String timeAnnotation;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the descriptionType property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescriptionType() {
        return descriptionType;
    }

    /**
     * Sets the value of the descriptionType property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescriptionType(String value) {
        this.descriptionType = value;
    }

    /**
     * Gets the value of the descriptionTypeSource property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescriptionTypeSource() {
        return descriptionTypeSource;
    }

    /**
     * Sets the value of the descriptionTypeSource property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescriptionTypeSource(String value) {
        this.descriptionTypeSource = value;
    }

    /**
     * Gets the value of the descriptionTypeRef property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescriptionTypeRef() {
        return descriptionTypeRef;
    }

    /**
     * Sets the value of the descriptionTypeRef property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescriptionTypeRef(String value) {
        this.descriptionTypeRef = value;
    }

    /**
     * Gets the value of the descriptionTypeVersion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescriptionTypeVersion() {
        return descriptionTypeVersion;
    }

    /**
     * Sets the value of the descriptionTypeVersion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescriptionTypeVersion(String value) {
        this.descriptionTypeVersion = value;
    }

    /**
     * Gets the value of the descriptionTypeAnnotation property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescriptionTypeAnnotation() {
        return descriptionTypeAnnotation;
    }

    /**
     * Sets the value of the descriptionTypeAnnotation property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescriptionTypeAnnotation(String value) {
        this.descriptionTypeAnnotation = value;
    }

    /**
     * Gets the value of the segmentType property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSegmentType() {
        return segmentType;
    }

    /**
     * Sets the value of the segmentType property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSegmentType(String value) {
        this.segmentType = value;
    }

    /**
     * Gets the value of the segmentTypeSource property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSegmentTypeSource() {
        return segmentTypeSource;
    }

    /**
     * Sets the value of the segmentTypeSource property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSegmentTypeSource(String value) {
        this.segmentTypeSource = value;
    }

    /**
     * Gets the value of the segmentTypeRef property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSegmentTypeRef() {
        return segmentTypeRef;
    }

    /**
     * Sets the value of the segmentTypeRef property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSegmentTypeRef(String value) {
        this.segmentTypeRef = value;
    }

    /**
     * Gets the value of the segmentTypeVersion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSegmentTypeVersion() {
        return segmentTypeVersion;
    }

    /**
     * Sets the value of the segmentTypeVersion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSegmentTypeVersion(String value) {
        this.segmentTypeVersion = value;
    }

    /**
     * Gets the value of the segmentTypeAnnotation property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSegmentTypeAnnotation() {
        return segmentTypeAnnotation;
    }

    /**
     * Sets the value of the segmentTypeAnnotation property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSegmentTypeAnnotation(String value) {
        this.segmentTypeAnnotation = value;
    }

    /**
     * Gets the value of the startTime property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the timeAnnotation property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTimeAnnotation() {
        return timeAnnotation;
    }

    /**
     * Sets the value of the timeAnnotation property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTimeAnnotation(String value) {
        this.timeAnnotation = value;
    }

    /**
     * Gets the value of the source property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the ref property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the annotation property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

}
