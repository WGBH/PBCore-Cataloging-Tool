
package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Definition: The affiliatedStringType adds attributes of
 *                 affiliation and time relevance.
 * 
 * <p>Java class for affiliatedStringType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="affiliatedStringType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *       &lt;attribute name="affiliation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="affiliationSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="affiliationRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="affiliationVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="affiliationAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "affiliatedStringType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "value"
})
public class AffiliatedStringType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "affiliation")
    protected String affiliation;
    @XmlAttribute(name = "affiliationSource")
    protected String affiliationSource;
    @XmlAttribute(name = "affiliationRef")
    protected String affiliationRef;
    @XmlAttribute(name = "affiliationVersion")
    protected String affiliationVersion;
    @XmlAttribute(name = "affiliationAnnotation")
    protected String affiliationAnnotation;
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
     * Gets the value of the affiliation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets the value of the affiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliation(String value) {
        this.affiliation = value;
    }

    /**
     * Gets the value of the affiliationSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliationSource() {
        return affiliationSource;
    }

    /**
     * Sets the value of the affiliationSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliationSource(String value) {
        this.affiliationSource = value;
    }

    /**
     * Gets the value of the affiliationRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliationRef() {
        return affiliationRef;
    }

    /**
     * Sets the value of the affiliationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliationRef(String value) {
        this.affiliationRef = value;
    }

    /**
     * Gets the value of the affiliationVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliationVersion() {
        return affiliationVersion;
    }

    /**
     * Sets the value of the affiliationVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliationVersion(String value) {
        this.affiliationVersion = value;
    }

    /**
     * Gets the value of the affiliationAnnotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliationAnnotation() {
        return affiliationAnnotation;
    }

    /**
     * Sets the value of the affiliationAnnotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliationAnnotation(String value) {
        this.affiliationAnnotation = value;
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
