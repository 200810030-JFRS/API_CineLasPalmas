# Cine Las Palmas

API REST desarrollada con Java 17, Spring Boot, Spring Data JPA y base de datos H2 para administrar películas, salas, funciones, clientes y boletos del cine Las Palmas.

## Requisitos
- JDK 17 o superior.
- Maven 3.6 o superior.

## Instalación y ejecución
1. Descomprimir el proyecto.
2. Abrir una terminal en la carpeta `Cine_Las_Palmas`.
3. Ejecutar `mvn spring-boot:run`.
4. La API estará disponible en `http://localhost:8080`.

La base H2 se guarda localmente en `./data/cine_db`. La consola está en `http://localhost:8080/h2-console` con JDBC URL `jdbc:h2:file:./data/cine_db`, usuario `sa` y contraseña vacía.

## Endpoints
| Método | Ruta | Función |
|---|---|---|
| GET | `/api/peliculas` | Listar películas; acepta `clasificacion` o `genero` |
| GET | `/api/peliculas/{id}` | Consultar película |
| POST | `/api/peliculas` | Registrar película |
| PUT | `/api/peliculas/{id}` | Actualizar película |
| DELETE | `/api/peliculas/{id}` | Eliminar película si no tiene funciones |
| GET | `/api/salas` | Listar salas |
| POST | `/api/salas` | Registrar sala |
| GET | `/api/clientes` | Listar clientes |
| POST | `/api/clientes` | Registrar cliente |
| GET | `/api/funciones` | Listar funciones |
| GET | `/api/funciones/{id}` | Consultar función |
| POST | `/api/funciones?peliculaId=1&salaId=1` | Programar función |
| GET | `/api/boletos/funcion/{funcionId}` | Listar boletos de una función |
| GET | `/api/boletos/{id}` | Consultar boleto |
| POST | `/api/boletos?funcionId=1&clienteId=1&fila=A&numero=1` | Comprar boleto |

## Ejemplos
### Registrar película
`POST /api/peliculas`
```json
{
  "titulo": "El viaje inesperado",
  "clasificacion": "B",
  "duracion": 110,
  "genero": "Aventura"
}
```

### Registrar cliente
`POST /api/clientes`
```json
{
  "nombre": "Ana García",
  "email": "ana@example.com"
}
```

### Programar función
`POST /api/funciones?peliculaId=1&salaId=1`
```json
{
  "fechaHora": "2026-11-05T18:30:00",
  "precio": 85.00
}
```

### Comprar boleto
`POST /api/boletos?funcionId=1&clienteId=1&fila=A&numero=12`

Se valida que el asiento no esté ocupado y que el número no exceda la capacidad de la sala. Los clientes registrados acumulan un punto por cada diez unidades monetarias del precio.

## Modelo de información
- `Pelicula 1 — 0..* Funcion`: una película puede aparecer en muchas funciones; cada función proyecta una película.
- `Sala 1 — 0..* Funcion`: una sala puede albergar muchas funciones en distintos horarios; cada función se realiza en una sala.
- `Funcion 1 — 0..* Boleto`: una función vende varios boletos; cada boleto pertenece a una función.
- `Cliente 0..1 — 0..* Boleto`: un boleto puede asociarse a cero o un cliente; un cliente puede comprar varios boletos.

## Identificadores
| Clase | Identificador | Tipo |
|---|---|---|
| Pelicula | id | Sustituto (generado) |
| Sala | id | Sustituto (generado); numero es clave candidata natural |
| Funcion | id | Sustituto (generado) |
| Boleto | folio | Natural de negocio, único; id es sustituto interno |
| Cliente | id | Sustituto (generado); email es clave candidata natural |

## Restricciones semánticas
1. La clasificación de una película solo admite `A`, `B`, `B15` y `C`.
2. La duración debe ser mayor que cero; el precio de una función debe ser mayor que cero.
3. Un asiento identificado por fila y número no puede venderse dos veces en la misma función.
4. Solo los clientes registrados acumulan puntos.
5. Una sala no puede tener funciones con horarios que se traslapen.

## Representaciones solicitadas
- `ejemplos/funcion.json`: función con película y sala anidadas.
- `ejemplos/boleto.xml`: boleto con referencias por identificador a función y cliente.
- `ejemplos/funcion.schema.json`: esquema de validación para el ejemplo JSON.
- `ejemplos/boleto.xsd`: esquema de validación para el ejemplo XML.
- `documentacion/diagrama-clases.puml`: diagrama UML en PlantUML.
