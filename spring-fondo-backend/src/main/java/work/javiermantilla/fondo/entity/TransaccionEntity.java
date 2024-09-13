package work.javiermantilla.fondo.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "TRANSACCIONES")
public class TransaccionEntity implements Serializable {

	private static final long serialVersionUID = -2223150655408269309L;
	
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBTyped(DynamoDBAttributeType.S)
    private String id;	
	
	@DynamoDBTyped(DynamoDBAttributeType.S)
	@DynamoDBRangeKey(attributeName = "cliente")
    private String cliente;
	
	@DynamoDBAttribute(attributeName = "fondo")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String fondo;
	
	@DynamoDBAttribute(attributeName = "valor")
	@DynamoDBTyped(DynamoDBAttributeType.N)
	private float valor;
	
	@DynamoDBAttribute(attributeName = "fecha")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String fecha;
	
	@DynamoDBAttribute(attributeName = "activa")
	@DynamoDBTyped(DynamoDBAttributeType.BOOL)	
	private boolean activa;

	@DynamoDBAttribute(attributeName = "tipo")
	@DynamoDBTyped(DynamoDBAttributeType.S)	
	private String tipo;

	public TransaccionEntity(String id) {
		super();
		this.id = id;
	}
	
	
	
}
