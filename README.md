# ARSW-CALLRETURN

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Sockets](https://img.shields.io/badge/Sockets-4285F4?style=for-the-badge&logo=socket.io&logoColor=white)
![RMI](https://img.shields.io/badge/RMI-007396?style=for-the-badge&logo=java&logoColor=white)
![TCP/IP](https://img.shields.io/badge/TCP%2FIP-0078D4?style=for-the-badge&logo=cisco&logoColor=white)
![UDP](https://img.shields.io/badge/UDP-FF6B6B?style=for-the-badge&logo=protocol&logoColor=white)

</div>

## Descripción

Laboratorio de Arquitecturas de Software enfocado en comunicación de red en Java. Implementación de protocolos de comunicación cliente-servidor utilizando Sockets, Datagramas UDP y RMI (Remote Method Invocation).

## Tabla de Contenidos

- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Ejercicios Implementados](#ejercicios-implementados)
- [Instalación](#instalación)
- [Uso](#uso)
- [Autor](#autor)

## Tecnologías

- **Java SE 8+**
- **TCP Sockets** - Comunicación confiable orientada a conexión
- **UDP Datagramas** - Comunicación sin conexión
- **RMI** - Invocación remota de métodos
- **HTTP** - Servidor web básico

## Estructura del Proyecto

```
ARSW-CALLRETURN/
├── src/
│   ├── ejercicio1/          # Servidor de cuadrados (TCP)
│   │   ├── SquareServer.java
│   │   └── SquareClient.java
│   ├── ejercicio2/          # Servidor trigonométrico (TCP)
│   │   ├── TrigServer.java
│   │   └── TrigClient.java
│   ├── ejercicio3/          # Servidor web HTTP
│   │   └── HttpServer.java
│   ├── ejercicio4/          # Cliente/Servidor de tiempo (UDP)
│   │   ├── DatagramTimeServer.java
│   │   └── DatagramTimeClient.java
│   └── ejercicio5/          # Chat P2P (RMI)
│       ├── ChatService.java
│       └── ChatNode.java
└── www/                     # Archivos estáticos del servidor web
    └── index.html
```

## Ejercicios Implementados

### Ejercicio 1: Servidor de Cuadrados

**Protocolo:** TCP Sockets  
**Puerto:** 35001

Servidor que recibe números y responde con su cuadrado.

**Ejecución:**
```bash
# Terminal 1 - Servidor
java -cp src ejercicio1.SquareServer

# Terminal 2 - Cliente
java -cp src ejercicio1.SquareClient
```

**Uso:**
```
> 5
Cuadrado: 25.0
> 12
Cuadrado: 144.0
> exit
```

---

### Ejercicio 2: Servidor Trigonométrico

**Protocolo:** TCP Sockets  
**Puerto:** 35002

Servidor que calcula funciones trigonométricas (sin, cos, tan). Por defecto calcula coseno.

**Ejecución:**
```bash
# Terminal 1 - Servidor
java -cp src ejercicio2.TrigServer

# Terminal 2 - Cliente
java -cp src ejercicio2.TrigClient
```

**Uso:**
```
> 0
Respuesta: 1.0
> 1.5707963267948966
Respuesta: 0.0
> fun:sin
Respuesta: Función cambiada a: sin
> 1.5707963267948966
Respuesta: 1.0
> exit
```

**Comandos:**
- `fun:sin` - Cambiar a función seno
- `fun:cos` - Cambiar a función coseno
- `fun:tan` - Cambiar a función tangente
- `exit` - Salir

---

### Ejercicio 3: Servidor Web HTTP

**Protocolo:** HTTP sobre TCP  
**Puerto:** 35003

Servidor web que soporta múltiples solicitudes y sirve archivos HTML e imágenes.

**Ejecución:**
```bash
java -cp src ejercicio3.HttpServer
```

**Acceso:**
```
http://localhost:35003
http://localhost:35003/index.html
```

**Características:**
- Soporte para archivos HTML, CSS, JS
- Soporte para imágenes (PNG, JPG, GIF)
- Respuestas HTTP 200 y 404
- Múltiples solicitudes seguidas

---

### Ejercicio 4: Cliente de Tiempo con Datagramas

**Protocolo:** UDP Datagramas  
**Puerto:** 4445

Cliente que solicita la hora al servidor cada 5 segundos. Si no hay respuesta, mantiene la última hora conocida.

**Ejecución:**
```bash
# Terminal 1 - Servidor
java -cp src ejercicio4.DatagramTimeServer

# Terminal 2 - Cliente
java -cp src ejercicio4.DatagramTimeClient
```

**Comportamiento:**
- Actualización automática cada 5 segundos
- Tolerante a fallos del servidor
- Timeout de 3 segundos por solicitud

---

### Ejercicio 5: Chat P2P con RMI

**Protocolo:** RMI (Remote Method Invocation)  
**Puertos:** Configurables

Aplicación de chat peer-to-peer que permite comunicación bidireccional entre nodos.

**Ejecución:**
```bash
# Terminal 1 - Nodo Alice
java -cp src ejercicio5.ChatNode Alice 1099

# Terminal 2 - Nodo Bob
java -cp src ejercicio5.ChatNode Bob 1100
```

**Comandos:**
```
# En Alice
> connect 127.0.0.1 1100 Bob
> send Hola Bob, ¿cómo estás?

# En Bob
> connect 127.0.0.1 1099 Alice
> send Hola Alice, muy bien gracias

> exit
```

**Características:**
- Comunicación bidireccional
- Conexión a múltiples peers
- Mensajería en tiempo real

---

## Instalación

### Prerrequisitos

- Java Development Kit (JDK) 8 o superior
- Sistema operativo: Windows, Linux o macOS

### Compilación

```bash
# Compilar todos los ejercicios
javac -d bin src/ejercicio1/*.java
javac -d bin src/ejercicio2/*.java
javac -d bin src/ejercicio3/*.java
javac -d bin src/ejercicio4/*.java
javac -d bin src/ejercicio5/*.java
```

## Uso

Cada ejercicio puede ejecutarse de forma independiente siguiendo las instrucciones específicas en la sección [Ejercicios Implementados](#ejercicios-implementados).

### Notas Importantes

- Los servidores deben iniciarse antes que los clientes
- Asegúrese de que los puertos especificados estén disponibles
- Para RMI, puede ser necesario configurar políticas de seguridad en versiones antiguas de Java

## Conceptos Implementados

### TCP (Transmission Control Protocol)
- Comunicación orientada a conexión
- Garantía de entrega y orden
- Usado en ejercicios 1, 2 y 3

### UDP (User Datagram Protocol)
- Comunicación sin conexión
- Sin garantía de entrega
- Usado en ejercicio 4

### RMI (Remote Method Invocation)
- Invocación de métodos remotos
- Modelo orientado a objetos distribuidos
- Usado en ejercicio 5

### Sockets
- Abstracción de comunicación de red
- Punto final de comunicación bidireccional
- Base de todos los ejercicios

## Autor

**Escuela Colombiana de Ingeniería Julio Garavito**  
- Arquitecturas de Software (ARSW)
- Carlos Mario Piedrahita Arango
- Manuel Alejandro Guarnizo Garcia

---

<div align="center">

**Laboratorio de Redes y Comunicación en Java**

</div>