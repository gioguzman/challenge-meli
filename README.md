# challenge-favorite-items-coupon

## **Objetivo**
Iplementar el siguiente desafio:

Mercado Libre está implementando un nuevo beneficio para los usuarios que más usan la 
plataforma con un cupón de cierto monto gratis que les permitirá comprar tantos items 
marcados como favoritos que no excedan el monto total. Para esto se está analizando
construir una API que dado una lista de item_id y el monto total pueda darle la lista de items
que maximice el total gastado sin excederlo.

## **Aclaraciones:**
- Sólo se puede comprar una unidad por item_id.
- No hay preferencia en la cantidad total de items siempre y cuando gasten el máximo
posible.
- El precio puede contener hasta 2 decimales.

### **Ejemplo:**

| Item_id | Precio |
| ----- | ---: |
| MLA1  | $100  |
| MLA2  | $210  |
| MLA3  | $260  |
| MLA4  | $80  |
| MLA4  | $90  |

La respuesta sería: [“MLA1”, “MLA2”, “MLA4”, “MLA5”]
___

# **El desafio es:**

## **Nivel 1**:
Programar (en cualquier lenguaje de programación) la funcionalidad mencionada
respetando la siguiente firma:

```java		
List<String> calculate(Map<String, Float> items, Float amount)
```

*Para este desafio se implementó el **Algoritmo Voraz** pues es un algoritmo 
utilizado en escenarios de optimización de un objetivo, el detalle del 
algoritmo es el siguiente:*
- *Ordenar el Map que recibe el método por el valor de forma ascendente*
- *Recorre Map para ir sumando los valores hasta completar el bono y no sobrepasarlo*
- *Cada vez que obtiene un valor valido lo agrega a la nueva lista de items*

```java		
private List<String> calculate (Map<String, Float> items, Float amount) {

    Map<String, Float> sortedMap =  items.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (a, b) -> { throw new AssertionError();},
                    LinkedHashMap::new
            ));

    Float amountList = Float.valueOf(0);
    List<String> listItems = new ArrayList<>();
    for (Map.Entry<String, Float> mapItems : sortedMap.entrySet()){
        if (Float.sum(amountList,mapItems.getValue()) <= amount){
            listItems.add(mapItems.getKey());
            amountList += mapItems.getValue();
        }
    }

    return listItems;
}
```

---

## **Nivel 2:**
Crear una API REST, con el servicio “/coupon/” en donde se pueda enviar la lista de 
item_ids y el monto del cupón y devuelva los items que tendría que comprar el usuario.

**POST:** /coupon/

### **Body:**

```json	
{
    "item_ids": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5"],
    "amount": 500
}
```

### **Response:**

```json	
{
    "item_ids": ["MLA1", "MLA2", "MLA4", "MLA5"],
    "total": 480
}
```

Con status HTTP 200-OK y en caso de que el monto no sea suficiente como para comprar
mínimamente un item devolver 404-NOT_FOUND

*Para este desafio se implementó el **API** con Spring Boot y Lombok y el siguiente 
esquema de paquetes:*

- ***web.controller:*** *Paquete en donde se obtiene la petición Rest del cliente
    y los Handler del error*
- ***model:*** *Paquete donde se encuentran los modelos del API que está expuesta y
    el modelo del API (Request y Response)*
- ***business:*** *Capa de negocio donde estará la lógica del método* ***/coupon/***

- ***busicapa:*** *Capa de intercambio de datos con otros servicios* ***/coupon/***

---

## **Nivel 3:**
Hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc).

**Consideraciones:**

- Hay usuarios que tienen miles de items en favoritos.
- Esta api tendría que escalar para soportar tráfico de hasta 100K rpm.
- Generalmente los usuarios suelen marcar como favoritos a los mismos items.

Test-Automáticos, Code coverage > 80%.

**Recursos:**

Para obtener el precio de un item consultar la API de Items y luego acceder al valor "price".

```	
    curl -X GET https://api.mercadolibre.com/items/$ITEM_ID1
```
* **Ejemplo:**

```	
    curl -X GET https://api.mercadolibre.com/items/MLA599260060
```

* **Response:**

```json	
{
    "id": "MLA599260060",
    "title": "Item De Test - Por Favor No Ofertar",
    "price": 130,
    "site_id": "MLA",
    ...
}
```

*Para este desafio se creo:*
- *Un contenedor en Docker para hostear el microservicio en una maquina linux en AWS 
a través del servicio EC2 con una ip elastica*
- *La imagen se cargó al Elastic Container Registry para obtenerla por **Docekr pull** en la maquina
EC2*
- *TODO: Pruebas de carga, sin embargo el tener hosteada la api en una maquina puede soportar el tráfico*
- *Para el coverage se implementó* ***jacoco con junit y mockito*** *TODO: Completar el porcentaje de coverage*

![jacoco](/doc/assets/jacoco.jpeg)

*Para obtener el reporte es necesario ejecutar el ciclo **test** de Maven*

___

# **Ejecucción del API local**

Para la ejecución local se recomienda abrir el código en el IDE **IntelliJ IDEA** y con el plugin de **Spring Boot** ejecutar el ciclo **run**:

![springboot](/doc/assets/springboot.jpeg)

Deberá ver en consola el siguiente mensaje:

![springboot](/doc/assets/springboot-run.jpeg)

En este momento podemos hacer la prueba por [**Postman**](/doc/assets/challenge.postman_collection.json) al endpoint: http://localhost:8080/coupon/ con el siguiente request:

```json	
{
    "item_ids": ["MLA923198114", "MLA868907697", "MLA884534125", "MLA651394869"],
    "amount": 12000
}
```
___

# **Ejecucción del API en AWS**

Para la ejecución remota podemos hacer la prueba por [**Postman**](/doc/assets/challenge.postman_collection.json) al endpoint: 

```json	
{
    "item_ids": ["MLA923198114", "MLA868907697", "MLA884534125", "MLA651394869"],
    "amount": 12000
}
```

Para el despliegue es necesario ejecutar el ***Lifecycle*** de **Maven** ***package***

![package](/doc/assets/package.jpeg)

Luego probar el contenedor con **Docker** según el [**dockerfile**](code\challenge-favorite-items-coupon\Dockerfile):

```sh
docker build -t springio/challenge-meli .
docker run -p 8080:8080 springio/challenge-meli
```

Deberia verse en ejecución el contenedor:

![docker](/doc/assets/docker.jpeg)

Luego de comprobar el contenedor se puede subir la imagen en [**ECR**](https://docs.aws.amazon.com/AmazonECR/latest/userguide/getting-started-cli.html):


![ecr](/doc/assets/ecr.jpeg)

Finalmente en la intancia EC2 se hace el pull de la imagen del ECR y se ejecuta el contenedor

![ec2-docker-run](/doc/assets/ec2-docker-run.jpeg)

En este momento podemos hacer la prueba por [**Postman**](/doc/assets/challenge.postman_collection.json) al endpoint: http://3.21.136.138:8080/coupon/ con el siguiente request:

```json	
{
    "item_ids": ["MLA923198114", "MLA868907697", "MLA884534125", "MLA651394869"],
    "amount": 23000
}
