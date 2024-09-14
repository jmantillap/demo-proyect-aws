package work.javiermantilla.fondo.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "FONDOS")
public class FondoEntity implements Serializable {
		
	private static final long serialVersionUID = -6774979436113563108L;
	
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBTyped(DynamoDBAttributeType.S)
    private String id;	
	
	@DynamoDBTyped(DynamoDBAttributeType.S)
	@DynamoDBRangeKey(attributeName = "codigo")
    private String codigo;
	
	@DynamoDBAttribute(attributeName = "categoria")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String categoria;
	
	@DynamoDBAttribute(attributeName = "monto")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String monto;
	
	@DynamoDBAttribute(attributeName = "nombre")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String nombre;

	public FondoEntity(String id) {
		super();
		this.id = id;
	}
	
	

}
