package org.dieschnittstelle.ess.entities.erp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDEntity;
import org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler.KLASSNAME_PROPERTY;

/*
 * TODO JRS3: entfernen Sie die Auskommentierung der Annotationen
 *
 * note that the value of the constant KLASSNAME_PROPERTY in this implementation is "@class". It
 * specifies the name of the json property the value of which will be the classname of the respective
 * concrete subclass of the abstract class, and thus allows to create correctly typed java objects
 * based on the untyped json data.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property=KLASSNAME_PROPERTY)
@JsonbTypeDeserializer(JsonbJsonTypeInfoHandler.class)
@JsonbTypeSerializer(JsonbJsonTypeInfoHandler.class)
@Schema(name = "AbstractProduct")
@Entity
public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(AbstractProduct.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940403029597060153L;

	@Id
	@GeneratedValue
	private long id;

	private String name;
	
	private int price;

	public AbstractProduct() {
		logger.debug("<constructor>");
	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
