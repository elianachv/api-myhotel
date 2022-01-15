
# Prueba Técnica Banco Popular
## Construcción de API REST de un hotel

### Base de datos
La base de datos se encuentra diseñada de la siguiente manera

**Clientes:** 
Tabla en la que se almacena la información personal de los clientes
| Campo | Descripción | Notas |
|--|--|--|
|id| Identificador único | Llave primaria, autoincremental|
|cedula| Número de identificación |único|
|nombre| Nombre del cliente |-|
|correo| correo del cliente  |-|
|telefono| Teléfono del cliente  |-|


**Grupos:** 
Tabla en la que se almacena la información sobre los grupos, número para agrupar a personas que se registren juntas. La idea es que si viene un grupo de personas se cree un grupo en la base de datos y posteriormente este sea asignado a todos los clientes que se registren juntos. 

| Campo | Descripción | Notas |
|--|--|--|
|id| Identificador único | Llave primaria, autoincremental|
|total_integrantes| Cantidad total de personas registradas con el identificador, se actualiza una vez terminado el registro de los clientes |-|


**Servicios:** 
Tabla en la que se almacena la información sobre los servicios que ofrece el hotel  
| Campo | Descripción | Notas |
|--|--|--|
|id| Identificador único | Llave primaria, autoincremental|
|identificador| Combinación de letras alusivas, identificador alfanumérico |único|
|descripción| Breve explicación del servicio |Máximo 70 caracteres|
|precio| Cuanto se cobrará por el servicio  |-|

**Ingresos:** 
Tabla en la que se almacena la información sobre las personas que se registran en el hotel. Se hizo de manera separada porque un cliente puede ingresar varias veces al hotel.
| Campo | Descripción | Notas |
|--|--|--|
|id| Identificador único | Llave primaria, autoincremental|
|id_grupo| Identificador del grupo con el que viene |-|
|cedula| Cedula del cliente |Debe estar previamente registrado en la tabla clientes|
|fecha_ingreso| Fecha de ingreso al hotel  |-|
|fecha_salida| Fecha de salida del hotel  |-|
|total_consumo| Total a pagar una vez finalizada la estadía  |-|

**Registros:** 
Tabla en la que se almacena la información sobre el consumo de servicios por parte los clientes ingresados.
| Campo | Descripción | Notas |
|--|--|--|
|id| Identificador único | Llave primaria, autoincremental|
|fecha| Fecha en la que se toma el servicio |-|
|cedula| Cedula del cliente |Debe estar previamente registrado en la tabla clientes|
|servicio| Identificador del servicio |Debe coincidir con algún servicio de la tabla servicios|

A continuación una imagen del modelo:

![modelo](https://www.lawebdelprogramador.com/usr/160000/160528/56f0db6d7491e-Base-de-datos.png)

En la carpeta configuraciones puede encontrar el script sql para configurar la base de datos

### API
**Servicios**
| Método | Url  |	Descripción | 
|--|--|--|
|POST| /api/servicios	| Agrega otro servicio a la tabla servicios|
| GET | /api/servicios  | Obtienes todos los servicios ofrecidos por el hotel|
| GET | /api/servicios/tipo/{tipo}| Obtienes todos los servicios clasificados por el tipo especificado|
| GET | /api/servicios/id/{id}  | Obtiene el servicio que coincida con el id especificado|
| PUT| /api/servicios/id/{id}  | Modifica el servicio identificado con el id especificado con los datos del servicio recibido en el body|
| DELETE| /api/servicios/id/{id}  | Elimina el servicio identificado con el id especificado|
| GET | /api/servicios/identificador/{identificador}  | Obtiene el servicio que coincida con el identificador especificado|
 PUT| /api/servicios/identificador/{identificador}  | Modifica el servicio identificado con el identificador especificado con los datos del servicio recibido en el body|
 | DELETE| /api/servicios/identificador/{identificador}  | Elimina el servicio identificado con el identificador especificado|

**Clientes**
| Método | Url  |	Descripción | 
|--|--|--|
|POST| /api/clientes| Agrega otro cliente a la tabla clientes|
| GET | /api/clientes| Obtienes todos los clientes registrados en el hotel|
| GET | /api/clientes/id/{id}| Obtiene el cliente que coincide con el id especificado|
| PUT| /api/cliente/id/{id}  | Modifica el cliente identificado con el id especificado con los datos del cliente recibido en el body|
| DELETE| /api/clientes/id/{id}  | Elimina el cliente identificado con el id especificado|
| GET | /api/clientes/cc/{cc}  | Obtiene el cliente que coincida con la cedula especificada|
 PUT| /api/clientes/cc/{cc}  | Modifica el cliente identificado con la cedula especificada con los datos del cliente recibido en el body|
 | DELETE| /api/clientes/cc/{cc}  | Elimina el cliente identificado con la cedula especificada|

**Grupos**
| Método | Url  |	Descripción | 
|--|--|--|
|POST| /api/grupos| Agrega otro grupo a la tabla grupos|
| GET | /api/grupos| Obtienes todos los grupos registrados en el hotel|
| GET | /api/grupos/id/{id}| Obtiene el grupo que coincide con el id especificado|
| PUT| /api/grupos/id/{id}  | Modifica el grupo identificado con el id especificado con los datos del grupo recibido en el body|
| DELETE| /api/grupos/id/{id}  | Elimina el grupo identificado con el id especificado|

**Registros**
| Método | Url  |	Descripción | 
|--|--|--|
|POST| /api/registros| Agrega otro registro a la tabla registros|
| GET | /api/registros| Obtienes todos los registros |
| GET | /api/registros/cliente/{id_cliente}| Obtiene todos los registros de un cliente|
| PUT| /api/registros/id/{id}  | Modifica el registro identificado con el id especificado con los datos del registro recibido en el body|
| DELETE| /api/registros/id/{id}  | Elimina el registro identificado con el id especificado|

**Ingresos**
| Método | Url  |	Descripción | 
|--|--|--|
|POST| /api/ingresos| Agrega otro ingreso a la tabla ingresos|
| GET | /api/ingresos| Obtienes todos los ingresos|
| GET | /api/ingresos/grupo/{grupo}| Obtiene todos los ingresos de un grupo, todas las personas que se registraron juntas|
| GET| /api/ingresos/cc/{cc}  | Obtiene todos los ingresos de cliente|
| GET| /api/ingresos/id/{id}  | Obtiene el registro identificado con el id especificado|
| PUT| /api/ingresos/id/{id}  | Modifica el ingreso identificado con el id especificado con los datos del ingreso recibido en el body|
| DELETE| /api/registros/id/{id}  | Elimina el registro identificado con el id especificado|
| DELETE| /api/registros/cc/{cc}  | Elimina todos los ingresos de un cliente identificado con la cedula especificada|

**Funciones que se podrían automatizar**

* Cada vez que se actualice la fecha de salida de un cliente se podría desarrollar una función que de manera automática  recopile todos los registros que coinciden con el identificador del usuario y calcule el total de los servicios consumidos y actualice el campo de total consumo. 

