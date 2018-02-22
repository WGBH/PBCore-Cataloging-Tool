package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Definition: The titleStringType schema type allows for the addition of a
 * titleType attribute as well as the standard sourceVersionGroup attributes and
 * a startEndTimeGroup or attributes.
 *
 * <p>
 * Java class for titleStringType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="titleStringType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attribute name="titleType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "titleStringType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "value"
})
public class TitleStringType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "titleType")
    protected String titleType;
    @XmlAttribute(name = "titleTypeSource")
    protected String titleTypeSource;
    @XmlAttribute(name = "titleTypeRef")
    protected String titleTypeRef;
    @XmlAttribute(name = "titleTypeVersion")
    protected String titleTypeVersion;
    @XmlAttribute(name = "titleTypeAnnotation")
    protected String titleTypeAnnotation;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;
    @XmlAttribute(name = "startTime")
    protected String startTime;
    @XmlAttribute(name = "endTime")
    protected String endTime;
    @XmlAttribute(name = "timeAnnotation")
    protected String timeAnnotation;

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
     * Gets the value of the titleType property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitleType() {
        return titleType;
    }

    /**
     * Sets the value of the titleType property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitleType(String value) {
        this.titleType = value;
    }

    /**
     * Gets the value of the titleTypeSource property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitleTypeSource() {
        return titleTypeSource;
    }

    /**
     * Sets the value of the titleTypeSource property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitleTypeSource(String value) {
        this.titleTypeSource = value;
    }

    /**
     * Gets the value of the titleTypeRef property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitleTypeRef() {
        return titleTypeRef;
    }

    /**
     * Sets the value of the titleTypeRef property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitleTypeRef(String value) {
        this.titleTypeRef = value;
    }

    /**
     * Gets the value of the titleTypeVersion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitleTypeVersion() {
        return titleTypeVersion;
    }

    /**
     * Sets the value of the titleTypeVersion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitleTypeVersion(String value) {
        this.titleTypeVersion = value;
    }

    /**
     * Gets the value of the titleTypeAnnotation property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitleTypeAnnotation() {
        return titleTypeAnnotation;
    }

    /**
     * Sets the value of the titleTypeAnnotation property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitleTypeAnnotation(String value) {
        this.titleTypeAnnotation = value;
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

}
