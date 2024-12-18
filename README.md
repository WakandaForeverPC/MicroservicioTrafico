# Microservicio de Tráfico

Este microservicio simula el movimiento de coches y el cambio de estados de semáforos en una cuadrícula. Utiliza Spring Boot para la configuración del servidor y WebSockets para la comunicación en tiempo real.

## Objetivo

El objetivo de este microservicio es proporcionar una simulación en tiempo real del tráfico, incluyendo el movimiento de coches y el cambio de estados de los semáforos. Los datos se actualizan periódicamente y se envían a los clientes a través de WebSockets.

## Estructura del Proyecto

### `src/main/java/com/proyecto/microserviciotrafico`

- **MicroservicioTraficoApplication.java**: Clase principal que inicia la aplicación Spring Boot.

- **model/Car.java**: Clase que representa un coche en la simulación. Contiene atributos como `id`, `x`, `y`, `direction` y `color`.

- **model/TrafficLight.java**: Clase que representa un semáforo en la simulación. Contiene atributos como `id`, `x`, `y` y `state`.

- **config/WebSocketConfig.java**: Configuración de WebSocket para habilitar la mensajería en tiempo real. Define los endpoints y el broker de mensajes.

- **controller/TrafficController.java**: Controlador REST que proporciona endpoints para obtener la lista de coches y semáforos.

- **service/TrafficService.java**: Servicio que maneja la lógica de negocio para mover coches y actualizar semáforos. Contiene métodos para obtener la lista de coches y semáforos, mover coches y actualizar semáforos.

- **service/TrafficUpdater.java**: Servicio que programa tareas periódicas para mover coches y actualizar semáforos.

- **service/TrafficWebSocketService.java**: Servicio que envía actualizaciones de tráfico a los clientes a través de WebSockets.

### `src/main/resources`

- **application.properties**: Archivo de configuración de la aplicación. Contiene configuraciones para el servidor, Eureka, y Prometheus.

- **static/**: Carpeta donde se colocan los archivos estáticos como CSS, JavaScript e imágenes.

- **templates/**: Carpeta donde se colocan los archivos HTML si se utiliza un motor de plantillas como Thymeleaf.

## Dependencias

El proyecto utiliza las siguientes dependencias:

- Spring Boot Starter Web
- Spring Boot Starter WebSocket
- Spring Boot Starter Actuator
- Spring Cloud Netflix Eureka Client
- Spring Cloud Config
- Micrometer Registry Prometheus

## Ejecución

Para ejecutar el microservicio, utiliza el siguiente comando:

```sh
mvn spring-boot:run
