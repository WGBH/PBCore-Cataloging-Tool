package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition: The pbcoreCollectionType schema type allows the addition of
 * attributes that describe the PBCoreCollection. The attributes define the
 * title, the description, the source, the reference and the date of the
 * collection.
 *
 * <p>
 * Java class for pbcoreCollectionType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="pbcoreCollectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}pbcoreDescriptionDocument" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *       &lt;attribute name="collectionTitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionSource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pbcoreCollectionType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "pbcoreDescriptionDocument"
})
public class PbcoreCollectionType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected List<PbcoreDescriptionDocumentType> pbcoreDescriptionDocument;
    @XmlAttribute(name = "collectionTitle")
    protected String collectionTitle;
    @XmlAttribute(name = "collectionDescription")
    protected String collectionDescription;
    @XmlAttribute(name = "collectionSource")
    protected String collectionSource;
    @XmlAttribute(name = "collectionRef")
    protected String collectionRef;
    @XmlAttribute(name = "collectionDate")
    protected String collectionDate;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;

    /**
     * Definition: The pbcoreDescriptionDocument element assembles together all
     * of PBCore knowledge items into a single data record organized in a
     * hierarchical structure. For PBCore these knowledge items are metadata
     * descriptions of media, including all the knowledge items and metadata
     * terms and values associated with its content and containers.Gets the
     * value of the pbcoreDescriptionDocument property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the pbcoreDescriptionDocument property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPbcoreDescriptionDocument().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType }
     *
     *
     */
    public List<PbcoreDescriptionDocumentType> getPbcoreDescriptionDocument() {
        if (pbcoreDescriptionDocument == null) {
            pbcoreDescriptionDocument = new ArrayList<>();
        }
        return this.pbcoreDescriptionDocument;
    }

    /**
     * Gets the value of the collectionTitle property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCollectionTitle() {
        return collectionTitle;
    }

    /**
     * Sets the value of the collectionTitle property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCollectionTitle(String value) {
        this.collectionTitle = value;
    }

    /**
     * Gets the value of the collectionDescription property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCollectionDescription() {
        return collectionDescription;
    }

    /**
     * Sets the value of the collectionDescription property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCollectionDescription(String value) {
        this.collectionDescription = value;
    }

    /**
     * Gets the value of the collectionSource property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCollectionSource() {
        return collectionSource;
    }

    /**
     * Sets the value of the collectionSource property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCollectionSource(String value) {
        this.collectionSource = value;
    }

    /**
     * Gets the value of the collectionRef property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCollectionRef() {
        return collectionRef;
    }

    /**
     * Sets the value of the collectionRef property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCollectionRef(String value) {
        this.collectionRef = value;
    }

    /**
     * Gets the value of the collectionDate property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCollectionDate() {
        return collectionDate;
    }

    /**
     * Sets the value of the collectionDate property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCollectionDate(String value) {
        this.collectionDate = value;
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
