
package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Definition: The subjectStringType schema type allows
 *                 for the addition of a subjectType attribute as well as the standard
 *                 sourceVersionGroup attributes and a startEndTimeGroup or
 *                 attributes.
 * 
 * <p>Java class for subjectStringType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subjectStringType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *       &lt;attribute name="subjectType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectTypeSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectTypeRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subjectTypeAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subjectStringType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "value"
})
public class SubjectStringType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "subjectType")
    protected String subjectType;
    @XmlAttribute(name = "subjectTypeSource")
    protected String subjectTypeSource;
    @XmlAttribute(name = "subjectTypeRef")
    protected String subjectTypeRef;
    @XmlAttribute(name = "subjectTypeVersion")
    protected String subjectTypeVersion;
    @XmlAttribute(name = "subjectTypeAnnotation")
    protected String subjectTypeAnnotation;
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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the subjectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * Sets the value of the subjectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectType(String value) {
        this.subjectType = value;
    }

    /**
     * Gets the value of the subjectTypeSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectTypeSource() {
        return subjectTypeSource;
    }

    /**
     * Sets the value of the subjectTypeSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectTypeSource(String value) {
        this.subjectTypeSource = value;
    }

    /**
     * Gets the value of the subjectTypeRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectTypeRef() {
        return subjectTypeRef;
    }

    /**
     * Sets the value of the subjectTypeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectTypeRef(String value) {
        this.subjectTypeRef = value;
    }

    /**
     * Gets the value of the subjectTypeVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectTypeVersion() {
        return subjectTypeVersion;
    }

    /**
     * Sets the value of the subjectTypeVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectTypeVersion(String value) {
        this.subjectTypeVersion = value;
    }

    /**
     * Gets the value of the subjectTypeAnnotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectTypeAnnotation() {
        return subjectTypeAnnotation;
    }

    /**
     * Sets the value of the subjectTypeAnnotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectTypeAnnotation(String value) {
        this.subjectTypeAnnotation = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the timeAnnotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeAnnotation() {
        return timeAnnotation;
    }

    /**
     * Sets the value of the timeAnnotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeAnnotation(String value) {
        this.timeAnnotation = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

}
