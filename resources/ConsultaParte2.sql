SELECT DISTINCT c.nombre,c.apellidos
FROM cliente c 
	INNER JOIN incripcion i ON (c.id=idCliente)
	INNER JOIN producto p ON (i.idProducto=p.id)
	INNER JOIN disponibilidad d ON (d.idProducto = p.id )
	INNER JOIN sucursal s ON (s.id=d.idSucursal)
WHERE EXISTS (SELECT 1
			  FROM visitan v
			  WHERE v.idCliente=c.id AND v.idSucursal=s.id
			  )
