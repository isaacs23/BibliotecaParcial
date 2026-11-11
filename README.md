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
RF-01: El sistema debe permitir registrar, actualizar, consultar y eliminar recursos disponibles para préstamo.  
RF-02: el sistema debe permitir registrar préstamos de recursos validando la disponibilidad y las reglas asociadas al tipo de recurso.
RF-03: El sistema debe permitir registrar la devolución de recursos prestados y actualizar su estado de disponibilidad.
RF-04:El sistema debe calcular automáticamente las multas por retraso en la devolución utilizando políticas de cálculo configurables.
RF-05: El sistema debe notificar automáticamente al siguiente usuario de la lista de espera cuando un recurso quede disponible.
RF-06: El sistema debe mantener un catálogo único y centralizado de todos los recursos de la aplicación.
RF-07: El sistema debe permitir buscar y consultar recursos dentro del catálogo mediante filtros y criterios de búsqueda.
RF-08:RF-15: El sistema debe generar reportes que identifiquen los recursos más solicitados y más utilizados.
