
package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Definition: The pbcorePartType schema type uses a
 *                 common structure to allow for the repeating of descriptive sub-documents to define
 *                 different segments, episodes etc., just as super-element 'pbcoreDescriptionDocument'
 *                 can be collected and used to describe higher-level media
 *                 programs.
 * 
 * <p>Java class for pbcorePartType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pbcorePartType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}pbcoreDescriptionDocumentType">
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attribute name="partType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="partTypeSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="partTypeRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titleTypeAnnotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pbcorePartType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
public class PbcorePartType
    extends PbcoreDescriptionDocumentType
{

    @XmlAttribute(name = "partType")
    protected String partType;
    @XmlAttribute(name = "partTypeSource")
    protected String partTypeSource;
    @XmlAttribute(name = "partTypeRef")
    protected String partTypeRef;
    @XmlAttribute(name = "titleTypeVersion")
    protected String titleTypeVersion;
    @XmlAttribute(name = "titleTypeAnnotation")
    protected String titleTypeAnnotation;
    @XmlAttribute(name = "startTime")
    protected String startTime;
    @XmlAttribute(name = "endTime")
    protected String endTime;
    @XmlAttribute(name = "timeAnnotation")
    protected String timeAnnotation;

    /**
     * Gets the value of the partType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartType() {
        return partType;
    }

    /**
     * Sets the value of the partType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartType(String value) {
        this.partType = value;
    }

    /**
     * Gets the value of the partTypeSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeSource() {
        return partTypeSource;
    }

    /**
     * Sets the value of the partTypeSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeSource(String value) {
        this.partTypeSource = value;
    }

    /**
     * Gets the value of the partTypeRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeRef() {
        return partTypeRef;
    }

    /**
     * Sets the value of the partTypeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeRef(String value) {
        this.partTypeRef = value;
    }

    /**
     * Gets the value of the titleTypeVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitleTypeVersion() {
        return titleTypeVersion;
    }

    /**
     * Sets the value of the titleTypeVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitleTypeVersion(String value) {
        this.titleTypeVersion = value;
    }

    /**
     * Gets the value of the titleTypeAnnotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitleTypeAnnotation() {
        return titleTypeAnnotation;
    }

    /**
     * Sets the value of the titleTypeAnnotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitleTypeAnnotation(String value) {
        this.titleTypeAnnotation = value;
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

}
