# Propuesta Biblioteca
Parcial Tercer Corte - Ingeniería de Software II

Propuesta 5: Biblioteca o préstamo de recursos

## 1. Alcance:
Dejaremos implementadas las siguientes funcionalidades:
  
  1. Varios tipos de recurso con reglas de préstamo diferentes (plazo, cantidad máxima, renovaciones)
  2. Cálculo de multas con políticas intercambiables.
  3. Lista de espera: avisar al siguiente usuario cuando un recurso queda disponible.
  4. Un catálogo único y consistente en toda la aplicación.
  5. Reportes de uso y de recursos más solicitados.

Sacaremos la siguiente funcionalidad:

  6. Varios tipos de usuario (estudiante, docente, externo) con privilegios distintos.
  
**Razón:** Consideramos que tener varios tipos de usuarios agrega una capa de complejidad que no es necesaria, ya que a la larga limita el alcance del sistema, encasillándolo a solo una institución educativa y por la naturaleza misma de esos arquetipos (estudiante, docente y externo) se arrebata la posibilidad de aplicarlo a cualquier otro entorno, así que decidimos modificarlo por solo usuarios genéricos con mismos privilegios que puedan pedir prestamos y hacer uso de las demás funcionalidades y un usuario administrador que pueda gestionar el catálogo y demás funciones propias de un admin.

Funcionalidad modificada:

  6. Usuarios genéricos con acceso a las funcionalidades previamente descritas y un usuario administrador que gestione el catalogo y demás apartados del sistema


## 2. Requisitos:
   
  **// REQUISITOS FUNCIONALES:**

  RF-01: El sistema debe permitir registrar, actualizar, consultar y eliminar recursos disponibles para préstamo.

  RF-02: el sistema debe permitir registrar préstamos de recursos validando la disponibilidad y las reglas asociadas al tipo de recurso.

  RF-03: El sistema debe permitir registrar la devolución de recursos prestados y actualizar su estado de disponibilidad.

  RF-04:El sistema debe calcular automáticamente las multas por retraso en la devolución utilizando políticas de cálculo configurables.

  RF-05: El sistema debe notificar automáticamente al siguiente usuario de la lista de espera cuando un recurso quede disponible.

  RF-06: El sistema debe mantener un catálogo único y centralizado de todos los recursos de la aplicación.

  RF-07: El sistema debe permitir buscar y consultar recursos dentro del catálogo mediante filtros y criterios de búsqueda.

  RF-08: El sistema debe generar reportes que identifiquen los recursos más solicitados y más utilizados.



  **// REQUISITOS NO FUNCIONALES:**

  RNF-01: El sistema debe tener una interfaz gráfica sencilla e intuitiva, permitiendo que los usuarios realicen préstamos, devoluciones y consultas de forma fácil y comprensible
  
  RNF-02: El sistema debe responder a las consultas del catálogo, registros de préstamos y devoluciones en un tiempo máximo de 3 segundos.
  
  RNF-03: El sistema debe implementar autenticación de usuarios y controlar el acceso correctamente
  
  RNF-04: El sistema debe garantizar que la información de los recursos, usuarios, préstamos y multas sea consistente y evitar registros duplicados.
  
  RNF-05: El sistema debe permitir aumentar la cantidad de usuarios y recursos registrados sin afectar significativamente el rendimiento de la aplicación.
  
  RNF-06: 	El sistema debe tener el código organizado en módulos independientes para facilitar la modificación de las políticas de préstamo, cálculo de multas y reglas de renovación sin afectar otras funcionalidades.
  
  RNF-07: El sistema debe registrar las operaciones importantes, como préstamos, devoluciones, renovaciones y modificaciones de recursos
  
  RNF-08: El sistema debe garantizar que los cálculos de multas, fechas de vencimiento y períodos de préstamo se realicen correctamente, evitando errores en los valores presentados al usuario.

## 3.	Problemas de diseño:
   Cálculo de multas rígido (Difícil de cambiar): Si el sistema usara una lista larga de condiciones ("si pasa esto, cobra aquello") para calcular las multas, habría que modificar y volver a probar el código principal cada vez que la biblioteca cambie sus tarifas. Con el patrón Strategy, cada forma de cobrar (tarifa fija, progresiva o con día de gracia) funciona como una ficha intercambiable: cuando un usuario entrega un libro con retraso, el administrador puede cambiar la regla de cobro en cualquier momento sin tocar el resto del programa.
    
  Reglas e inventario duplicados (Código repetido): Si la versión de consola y la ventana gráfica manejaran sus propias listas y validaciones por separado, habría que escribir las mismas reglas dos veces y el inventario se desincronizaría. Al usar Facade y Singleton, ambas pantallas se conectan a un único catálogo compartido y aplican exactamente los mismos controles, como impedir renovar un préstamo si alguien más espera ese ejemplar o prestar máximo un equipo tecnológico por persona.
    
  Registro manual de cada material (Propenso a errores): Pedirle al administrador que escriba a mano los días de plazo y el límite de renovaciones cada vez que registra un ítem provoca errores humanos y dificulta añadir nuevos tipos de materiales. Con Factory Method, al registrar un equipo tecnológico el sistema le asigna automáticamente sus reglas fijas (3 días de plazo, máximo 1 por usuario y 0 renovaciones), permitiendo agregar nuevas categorías en el futuro sin alterar lo que ya funciona.
    
  Avisos atados a la pantalla (Código rígido): Hacer que el inventario lance directamente ventanas emergentes o mensajes en consola cuando se devuelve un ejemplar ataría las reglas del negocio a la parte visual. Gracias al patrón Observer, cuando alguien devuelve el único portátil disponible, el sistema avisa automáticamente en orden de llegada al primer usuario de la lista de espera en su bandeja personal, sin importar qué interfaz esté abierta.

## 4.	Patrones de diseño elegidos:
   en los patrones de diseno seleccionamos 5
   siendo el primero el factory method el cual se uso porque permite añadir nuevos tipos de recurso mediante nuevas subclases creadoras sin modificar el código existente, permitiendo cumplir el principio open/close pero como desventaja terminaremos con bastantes clases 
    
  tambien usaremos el patron strategy porque se descartó aplicar Template Method porque la política de sanción debe poder cambiarse  por el administrador sobre préstamos que ya están activos, lo cual sería inviable si el algoritmo estuviera fijado por herencia al momento de crear el préstamo.
    
   tambien usaremos el patron observer para que se pueda notificar automáticamente un cambio de estado a otros objetos interesados (los Observadores) sin necesidad de estar acoplado a ellos ni conocer sus detalles internos, lo que implica que Exige controlar explícitamente la estructura de suscripción como una cola (LinkedList) en lugar de una lista o conjunto genérico para garantizar la equidad FIFO lo cual queda perfecto ya que se debe tener un catalogo unico
    
  usamos los patrones singleton y facade, singleton para evitar inconsistencias en el stock y facade para la coordinacion entre clases

## 5.	Arquitectura general:  
El sistema está estructurado bajo una arquitectura en capas desacopladas con núcleo único compartido, diseñada para que la interfaz por consola y la interfaz gráfica operen exactamente sobre las mismas reglas de negocio y el mismo estado en memoria sin duplicar código.
