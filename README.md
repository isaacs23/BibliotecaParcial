# BibliotecaParcial
Parcial Tercer Corte - Ingeniería de Software II

Propuesta 5: Biblioteca o préstamo de recursos

**1. Alcance:**
Dejaremos implementadas las siguientes funcionalidades:
  
  1. Varios tipos de recurso con reglas de préstamo diferentes (plazo, cantidad máxima, renovaciones)
  2. Cálculo de multas con políticas intercambiables.
  3. Lista de espera: avisar al siguiente usuario cuando un recurso queda disponible.
  4. Un catálogo único y consistente en toda la aplicación.
  5. Reportes de uso y de recursos más solicitados.

Sacaremos la siguiente funcionalidad:
  •	Varios tipos de usuario (estudiante, docente, externo) con privilegios distintos.
  
Razón: Consideramos que no es indispensable saber el tipo de usuario, es suficiente con que haya un usuario que quiera sacar un recurso y ya

2. Requisitos:
   
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
