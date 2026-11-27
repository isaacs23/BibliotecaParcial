**1. Diagrama de Casos de Uso del Sistema

flowchart LR
    Usuario(["Usuario Normal"])
    Admin(["Administrador"])
    Admin -->|Generalización| Usuario

    subgraph Sistema["Sistema de Biblioteca y Préstamo de Recursos"]
        UC_REG(["Registrar Cuenta de Usuario"])
        UC_LOG(["Iniciar Sesión"])
        UC_CAT(["Consultar y Buscar Recursos en Catálogo<br/>(RF-06, RF-07)"])
        UC_PRE(["Solicitar Préstamo de Recurso<br/>(RF-02)"])
        UC_ESP(["Unirse a Lista de Espera<br/>(RF-05)"])
        UC_DEV(["Registrar Devolución de Recurso<br/>(RF-03)"])
        UC_MUL(["Calcular Multa por Retraso $2.000/día<br/>(RF-04)"])
        UC_NOT(["Notificar Siguiente Usuario en Espera<br/>(RF-05)"])
        UC_VER_NOT(["Consultar Mis Notificaciones<br/>(RF-05)"])
        UC_CRUD(["Gestionar Recursos: Registrar, Actualizar,<br/>Consultar y Eliminar (RF-01)"])
        UC_REP(["Generar Reportes de Más Solicitados<br/>y Más Utilizados (RF-08)"])
    end

    Usuario --> UC_REG
    Usuario --> UC_LOG
    Usuario --> UC_CAT
    Usuario --> UC_PRE
    Usuario --> UC_DEV
    Usuario --> UC_VER_NOT

    Admin --> UC_CRUD
    Admin --> UC_REP

    UC_ESP -.->|"&lt;&lt;extend&gt;&gt;<br/>(si está agotado)"| UC_PRE
    UC_DEV -.->|"&lt;&lt;include&gt;&gt;"| UC_MUL
    UC_NOT -.->|"&lt;&lt;extend&gt;&gt;<br/>(si hay lista de espera)"| UC_DEV
