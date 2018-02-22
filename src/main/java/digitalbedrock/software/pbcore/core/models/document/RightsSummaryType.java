package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Definition: The rightsSumaryType schema type allows the use of rights at the
 * asset level and the instantiation level. The rights can be expressed as a
 * summary or a link or an embedded XML record. These can also contain time
 * relations.
 *
 * <p>
 * Java class for rightsSummaryType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="rightsSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="rightsSummary" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="rightsLink" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}rightsLinkType" minOccurs="0"/>
 *         &lt;element name="rightsEmbedded" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}embeddedType" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rightsSummaryType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "rightsSummary",
    "rightsLink",
    "rightsEmbedded"
})
public class RightsSummaryType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType rightsSummary;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected RightsLinkType rightsLink;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected EmbeddedType rightsEmbedded;
    @XmlAttribute(name = "startTime")
    protected String startTime;
    @XmlAttribute(name = "endTime")
    protected String endTime;
    @XmlAttribute(name = "timeAnnotation")
    protected String timeAnnotation;

    /**
     * Gets the value of the rightsSummary property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getRightsSummary() {
        return rightsSummary;
    }

    /**
     * Sets the value of the rightsSummary property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setRightsSummary(SourceVersionStringType value) {
        this.rightsSummary = value;
    }

    /**
     * Gets the value of the rightsLink property.
     *
     * @return possible object is {@link RightsLinkType }
     *
     */
    public RightsLinkType getRightsLink() {
        return rightsLink;
    }

    /**
     * Sets the value of the rightsLink property.
     *
     * @param value allowed object is {@link RightsLinkType }
     *
     */
    public void setRightsLink(RightsLinkType value) {
        this.rightsLink = value;
    }

    /**
     * Gets the value of the rightsEmbedded property.
     *
     * @return possible object is {@link EmbeddedType }
     *
     */
    public EmbeddedType getRightsEmbedded() {
        return rightsEmbedded;
    }

    /**
     * Sets the value of the rightsEmbedded property.
     *
     * @param value allowed object is {@link EmbeddedType }
     *
     */
    public void setRightsEmbedded(EmbeddedType value) {
        this.rightsEmbedded = value;
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
