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
@DynamoDBTable(tableName = "CLIENTES")
public class ClienteEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6581821531243920049L;
	
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBTyped(DynamoDBAttributeType.S)
    private String id;	
	
	@DynamoDBTyped(DynamoDBAttributeType.S)
	@DynamoDBRangeKey(attributeName = "email")
    private String email;
	
	@DynamoDBAttribute(attributeName = "nombre")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String nombre;
	
	@DynamoDBAttribute(attributeName = "saldo")
	@DynamoDBTyped(DynamoDBAttributeType.S)
	private String saldo;

	public ClienteEntity(String id) {
		super();
		this.id = id;
	}
	
	
}
