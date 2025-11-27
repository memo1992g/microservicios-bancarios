Este proyecto implementa una solución bancaria basada en microservicios desacoplados, con comunicación asíncrona, persistencia independiente, Kafka como bus de eventos, y despliegue completo en Docker, cumpliendo con todos los requisitos del nivel Senior de la prueba técnica.

📚 Contenido

Arquitectura general

Servicios incluidos

Diagrama de microservicios

Comunicación asíncrona (Kafka)

Estructura del repositorio

Modelo de datos + ERD

Endpoints + ejemplos

Manejo de movimientos y reglas de negocio

Reporte financiero (Estado de Cuenta)

Inicialización automática de BD

Pruebas

Docker y ejecución

Postman

Mejoras recomendadas

🏗️ Arquitectura General

La solución se divide en dos microservicios independientes:

cliente-persona-service
Maneja Personas y Clientes

cuenta-movimientos-service
Maneja Cuentas y Movimientos

Cada uno tiene:

Su propia base de datos PostgreSQL

Su servidor embebido (Spring Boot)

Su Dockerfile

Sus entidades y repositorios

Sus endpoints REST

Su lógica de dominio

🔌 Event-Driven con Kafka

Cuando se crea un Cliente o se realizan operaciones relevantes, el servicio publica eventos en Kafka, y el otro microservicio los consume.

🧩 Diagrama de Arquitectura
                  ┌────────────────────────────────────┐
                  │              CLIENTE                │
                  └────────────────────────────────────┘
                                   │
                                   ▼
                        (REST) Solicitudes HTTP
                                   │
        ┌──────────────────────────────────────────────────────────────┐
        │                        BACKEND                               │
        └──────────────────────────────────────────────────────────────┘
               │                                         │
               ▼                                         ▼
 ┌──────────────────────────┐               ┌────────────────────────────┐
 │ cliente-persona-service  │               │ cuenta-movimientos-service │
 ├──────────────────────────┤               ├────────────────────────────┤
 │ CRUD Personas/Clientes   │               │ CRUD Cuentas/Movimientos   │
 │ Publica eventos Kafka    │──TOPIC──▶     │ Consume eventos Kafka      │
 └──────────────────────────┘               └────────────────────────────┘
               │                                         │
               ▼                                         ▼
 PostgreSQL (bank_clientes)                    PostgreSQL (bank_cuentas)

📦 Servicios incluidos (Docker)
Servicio	Descripción
postgres_clientes	DB del microservicio de clientes
postgres_cuentas	DB del microservicio de cuentas
zookeeper	Coordinación de Kafka
kafka	Broker de mensajería
cliente-persona-service	Microservicio personas/clientes
cuenta-movimientos-service	Microservicio cuentas/movimientos
📁 Estructura del repositorio
microservicios-bancarios/
 ├─ cliente-persona-service/
 │   ├─ src/main/java/...
 │   ├─ Dockerfile
 │   └─ ...
 ├─ cuenta-movimientos-service/
 │   ├─ src/main/java/...
 │   ├─ Dockerfile
 │   └─ ...
 ├─ docker-compose.yml
 ├─ coleccion_postman.json
 ├─ README.md
 └─ (opcional) data.sql

🗄️ Modelo de Datos + ERD
PERSONA
- id (PK)
- nombre
- genero
- edad
- identificacion
- direccion
- telefono

CLIENTE (hereda de Persona)
- clienteId (PK)
- contrasena
- estado

CUENTA
- numeroCuenta (PK)
- tipoCuenta
- saldoInicial
- estado
- clienteId

MOVIMIENTO
- movimientoId (PK)
- fecha
- tipoMovimiento
- valor
- saldoPosterior
- numeroCuenta

📍 Endpoints
cliente-persona-service (8081)
Clientes
Método	Endpoint
GET	/clientes
POST	/clientes
GET	/clientes/{id}
PUT	/clientes/{id}
DELETE	/clientes/{id}
cuenta-movimientos-service (8082)
Cuentas
Método	Endpoint
GET	/cuentas
POST	/cuentas
PUT	/cuentas/{id}
DELETE	/cuentas/{id}
Movimientos
Método	Endpoint
POST	/movimientos
GET	/movimientos?fecha=YYYY-MM-DD,YYYY-MM-DD&cliente=ID
Reporte
GET /reportes?fecha=2022-10-01,2022-10-31&cliente=1

💰 Reglas de Negocio – Movimientos
Depósito

valor > 0

saldoFinal = saldoActual + valor

Retiro

valor < 0

Se valida saldo disponible

Si no hay saldo → excepción:

{
  "error": "Saldo no disponible"
}

Auditoría

Todos los movimientos quedan registrados.

📊 Reporte de Estado de Cuenta

Devuelve:

Información del cliente

Cuentas asociadas

Movimientos filtrados por fecha

Saldos iniciales y finales

Ejemplo:

{
  "fecha": "10/2/2022",
  "cliente": "Marianela Montalvo",
  "numeroCuenta": "225487",
  "tipo": "Corriente",
  "saldoInicial": 100,
  "movimiento": 600,
  "saldoDisponible": 700
}

🗄️ Inicialización Automática de Base de Datos
PostgreSQL en Docker crea las BD automáticamente

Gracias a:

POSTGRES_DB: bank_clientes
POSTGRES_DB: bank_cuentas

Hibernate crea las tablas automáticamente

Con:

spring.jpa.hibernate.ddl-auto=update


No se necesita ningún archivo SQL.

Opcionalmente se pueden usar:

data.sql para datos iniciales

docker-entrypoint-initdb.d/init.sql

Endpoint /seed para pruebas

🧪 Pruebas
✔ Pruebas unitarias

Entidad Cliente

Validaciones

✔ Pruebas de integración

SpringBootTest

Repositorios

Controladores

🐳 Ejecución con Docker
1. Construir contenedores
docker-compose build

2. Levantar todo
docker-compose up -d

3. Ver logs
docker logs cliente-persona-service -f
docker logs cuenta-movimientos-service -f

🧪 Colección Postman

Incluye:

✔ CRUDs
✔ Movimientos
✔ Reportes
✔ Variables para hosts
✔ JSON listo para importar

Archivo: coleccion_postman.json
🟢 Cómo ejecutar todo el sistema correctamente (PASO A PASO)

Funciona en Windows 10/11 + Docker Desktop.

✅ 1. Requisitos

Antes de iniciar, necesitás:

Docker Desktop instalado

Java 17+ (solo si querés correr los servicios localmente)

Git (opcional)

Postman (para pruebas)

🔵 2. Clonar el repositorio
git clone https://github.com/memo1992g/microservicios-bancarios.git
cd microservicios-bancarios


La carpeta debe verse así:

microservicios-bancarios/
 ├─ cliente-persona-service/
 ├─ cuenta-movimientos-service/
 ├─ docker-compose.yml
 ├─ README.md
 └─ coleccion_postman.json

🐳 3. Construir los microservicios (IMPORTANTE)

Este comando construye las imágenes Docker de cada microservicio:

docker-compose build


Esto ejecuta automáticamente los Dockerfiles:

Compila el proyecto con Maven

Copia los .jar dentro de las imágenes

Prepara el entorno

🟢 4. Levantar TODO el ecosistema (bases, kafka, microservicios)
docker-compose up -d


¿Qué levanta esto?

Componente	Estado
PostgreSQL — Clientes	✔ Activo
PostgreSQL — Cuentas	✔ Activo
Zookeeper	✔ Activo
Kafka	✔ Activo
cliente-persona-service	✔ Activo
cuenta-movimientos-service	✔ Activo

Docker asigna automáticamente los puertos:

8081 → cliente-persona-service

8082 → cuenta-movimientos-service

9092 → Kafka

5433 / 5434 → PostgreSQL

Todo corre en localhost.

🔍 5. Verificar que los servicios estén arriba
Ver logs de cada servicio:
docker logs cliente-persona-service -f

docker logs cuenta-movimientos-service -f


Si todo está bien, verás:

Started ClientePersonaServiceApplication
Started CuentaMovimientosServiceApplication
Kafka connection established

🌐 6. Probar microservicios en el navegador
Cliente/Persona (microservicio 1)
http://localhost:8081/clientes

Cuentas/Movimientos (microservicio 2)
http://localhost:8082/cuentas
http://localhost:8082/movimientos


Si estas URLs responden, todo está funcionando.

📄 7. Importar la colección Postman

Abrí Postman

Click en Import

Seleccioná el archivo:

coleccion_postman.json


Confirmá que las variables estén así:

{{host_clientes}} = http://localhost:8081
{{host_cuentas}} = http://localhost:8082


Ahora podés probar:

Crear cliente

Crear cuentas

Registrar movimientos

Consultar reportes

🧪 8. Probar con datos reales de la prueba técnica

Ejemplo para POST de cliente:

{
  "nombre": "Jose Lema",
  "genero": "M",
  "edad": 35,
  "identificacion": "123456789",
  "direccion": "Otavalo sn y principal",
  "telefono": "098254785",
  "contrasena": "1234",
  "estado": true
}


Todo esto ya está en la colección Postman listísima.

🗄️ 9. La base de datos se crea automáticamente

No tenés que hacer NADA.

Docker crea bank_clientes y bank_cuentas

Spring Boot crea las tablas automáticamente

Opcional: data.sql para datos iniciales

✔ Nada manual
✔ No se usa SQL externo
✔ Todo autosuficiente

🟥 10. ¿Cómo apagar todo correctamente?
docker-compose down


Esto:

Detiene contenedores

No borra datos (persisten en los volúmenes Docker)

Si querés borrar TODO:

docker-compose down -v

🟩 11. Si querés correr los servicios localmente (sin Docker)
Microservicio 1 (cliente/persona)

Editar application.properties:

server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5433/bank_clientes

Microservicio 2 (cuentas/movimientos)
server.port=8082
spring.datasource.url=jdbc:postgresql://localhost:5434/bank_cuentas


Luego:

mvn clean install
mvn spring-boot:run

⭐ RESUMEN RÁPIDO PARA QUE FUNCIONE TODO
1️⃣ docker-compose build
2️⃣ docker-compose up -d
3️⃣ Verificar:
http://localhost:8081/clientes
http://localhost:8082/cuentas

4️⃣ Probar con Postman
5️⃣ Apagar con docker-compose down

Y listo — todo funcionando perfectamente.