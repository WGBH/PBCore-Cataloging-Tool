package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition: The essenceTrackType schema type uses a common structure to allow
 * for grouping of the essence related elements and their repeated use.
 *
 * <p>
 * Java class for essenceTrackType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="essenceTrackType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="essenceTrackType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="essenceTrackStandard" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackEncoding" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackDataRate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackFrameRate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackPlaybackSpeed" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackSamplingRate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackBitDepth" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackFrameSize" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackAspectRatio" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackTimeStart" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackDuration" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="essenceTrackLanguage" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}threeLetterStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="essenceTrackAnnotation" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}annotationStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="essenceTrackExtension" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}extensionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "essenceTrackType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "essenceTrackType",
    "essenceTrackIdentifier",
    "essenceTrackStandard",
    "essenceTrackEncoding",
    "essenceTrackDataRate",
    "essenceTrackFrameRate",
    "essenceTrackPlaybackSpeed",
    "essenceTrackSamplingRate",
    "essenceTrackBitDepth",
    "essenceTrackFrameSize",
    "essenceTrackAspectRatio",
    "essenceTrackTimeStart",
    "essenceTrackDuration",
    "essenceTrackLanguage",
    "essenceTrackAnnotation",
    "essenceTrackExtension"
})
public class EssenceTrackType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType essenceTrackType;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStringType> essenceTrackIdentifier;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType essenceTrackStandard;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType essenceTrackEncoding;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackDataRate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackFrameRate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackPlaybackSpeed;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackSamplingRate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackBitDepth;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackFrameSize;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType essenceTrackAspectRatio;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType essenceTrackTimeStart;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType essenceTrackDuration;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ThreeLetterStringType> essenceTrackLanguage;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<AnnotationStringType> essenceTrackAnnotation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ExtensionType> essenceTrackExtension;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;

    /**
     * Gets the value of the essenceTrackType property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getEssenceTrackType() {
        return essenceTrackType;
    }

    /**
     * Sets the value of the essenceTrackType property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setEssenceTrackType(SourceVersionStringType value) {
        this.essenceTrackType = value;
    }

    /**
     * Gets the value of the essenceTrackIdentifier property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the essenceTrackIdentifier property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEssenceTrackIdentifier().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStringType }
     *
     *
     */
    public List<SourceVersionStringType> getEssenceTrackIdentifier() {
        if (essenceTrackIdentifier == null) {
            essenceTrackIdentifier = new ArrayList<>();
        }
        return this.essenceTrackIdentifier;
    }

    /**
     * Gets the value of the essenceTrackStandard property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getEssenceTrackStandard() {
        return essenceTrackStandard;
    }

    /**
     * Sets the value of the essenceTrackStandard property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setEssenceTrackStandard(SourceVersionStringType value) {
        this.essenceTrackStandard = value;
    }

    /**
     * Gets the value of the essenceTrackEncoding property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getEssenceTrackEncoding() {
        return essenceTrackEncoding;
    }

    /**
     * Sets the value of the essenceTrackEncoding property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setEssenceTrackEncoding(SourceVersionStringType value) {
        this.essenceTrackEncoding = value;
    }

    /**
     * Gets the value of the essenceTrackDataRate property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackDataRate() {
        return essenceTrackDataRate;
    }

    /**
     * Sets the value of the essenceTrackDataRate property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackDataRate(TechnicalStringType value) {
        this.essenceTrackDataRate = value;
    }

    /**
     * Gets the value of the essenceTrackFrameRate property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackFrameRate() {
        return essenceTrackFrameRate;
    }

    /**
     * Sets the value of the essenceTrackFrameRate property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackFrameRate(TechnicalStringType value) {
        this.essenceTrackFrameRate = value;
    }

    /**
     * Gets the value of the essenceTrackPlaybackSpeed property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackPlaybackSpeed() {
        return essenceTrackPlaybackSpeed;
    }

    /**
     * Sets the value of the essenceTrackPlaybackSpeed property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackPlaybackSpeed(TechnicalStringType value) {
        this.essenceTrackPlaybackSpeed = value;
    }

    /**
     * Gets the value of the essenceTrackSamplingRate property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackSamplingRate() {
        return essenceTrackSamplingRate;
    }

    /**
     * Sets the value of the essenceTrackSamplingRate property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackSamplingRate(TechnicalStringType value) {
        this.essenceTrackSamplingRate = value;
    }

    /**
     * Gets the value of the essenceTrackBitDepth property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackBitDepth() {
        return essenceTrackBitDepth;
    }

    /**
     * Sets the value of the essenceTrackBitDepth property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackBitDepth(TechnicalStringType value) {
        this.essenceTrackBitDepth = value;
    }

    /**
     * Gets the value of the essenceTrackFrameSize property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackFrameSize() {
        return essenceTrackFrameSize;
    }

    /**
     * Sets the value of the essenceTrackFrameSize property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackFrameSize(TechnicalStringType value) {
        this.essenceTrackFrameSize = value;
    }

    /**
     * Gets the value of the essenceTrackAspectRatio property.
     *
     * @return possible object is {@link TechnicalStringType }
     *
     */
    public TechnicalStringType getEssenceTrackAspectRatio() {
        return essenceTrackAspectRatio;
    }

    /**
     * Sets the value of the essenceTrackAspectRatio property.
     *
     * @param value allowed object is {@link TechnicalStringType }
     *
     */
    public void setEssenceTrackAspectRatio(TechnicalStringType value) {
        this.essenceTrackAspectRatio = value;
    }

    /**
     * Gets the value of the essenceTrackTimeStart property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getEssenceTrackTimeStart() {
        return essenceTrackTimeStart;
    }

    /**
     * Sets the value of the essenceTrackTimeStart property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setEssenceTrackTimeStart(SourceVersionStringType value) {
        this.essenceTrackTimeStart = value;
    }

    /**
     * Gets the value of the essenceTrackDuration property.
     *
     * @return possible object is {@link SourceVersionStringType }
     *
     */
    public SourceVersionStringType getEssenceTrackDuration() {
        return essenceTrackDuration;
    }

    /**
     * Sets the value of the essenceTrackDuration property.
     *
     * @param value allowed object is {@link SourceVersionStringType }
     *
     */
    public void setEssenceTrackDuration(SourceVersionStringType value) {
        this.essenceTrackDuration = value;
    }

    /**
     * Gets the value of the essenceTrackLanguage property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the essenceTrackLanguage property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEssenceTrackLanguage().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ThreeLetterStringType }
     *
     *
     */
    public List<ThreeLetterStringType> getEssenceTrackLanguage() {
        if (essenceTrackLanguage == null) {
            essenceTrackLanguage = new ArrayList<>();
        }
        return this.essenceTrackLanguage;
    }

    /**
     * Gets the value of the essenceTrackAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the essenceTrackAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEssenceTrackAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnotationStringType }
     *
     *
     */
    public List<AnnotationStringType> getEssenceTrackAnnotation() {
        if (essenceTrackAnnotation == null) {
            essenceTrackAnnotation = new ArrayList<>();
        }
        return this.essenceTrackAnnotation;
    }

    /**
     * Gets the value of the essenceTrackExtension property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the essenceTrackExtension property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEssenceTrackExtension().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     *
     *
     */
    public List<ExtensionType> getEssenceTrackExtension() {
        if (essenceTrackExtension == null) {
            essenceTrackExtension = new ArrayList<>();
        }
        return this.essenceTrackExtension;
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
