# Práctica final de Programación Concurrente (PC) - Curso 2020/2021

## Información sobre la práctica

Alumno: Erik Karlgren Domercq

Consiste en hacer algo parecido a BitTorrent (supongo). Hay un servidor con la información de varios usuarios y los nombres de los ficheros que tiene cada uno. Un usuario puede buscar y conectarse con otro usuario a través del servidor, pero la transferencia de los propios ficheros es por conexión peer-to-peer o P2P entre los usuarios.

Nota: no he comprobado si funciona usando 2 ordenadores distintos (uno con el código del cliente y otro con el del servidor), pero sé que funciona bien como 2 procesos distintos en el mismo ordenador.

## ¿Qué es cada carpeta y dónde está el código?

El proyecto lo organicé por modulos usando IntelliJIDEA y por eso no está todo en la misma carpeta. Las carpetas principales son las siguientes:

### ./src

Código compartido entre todos los modulos (Cliente y Servidor). Tiene sobre todo interfaces, algunas excepciones propias, **la clase Mensaje y sus subclases** y clases para "loguear" qué ocurre en cada momento del programa.

### ./Cliente

Código del modulo Cliente. Tiene el main del código del cliente y las clases para comunicarse con el servidor, con el usuario por consola (CLI.java) y con otros clientes
(DownloadManager y UploadManager).

### ./Servidor

Código del modulo Servidor. Tiene el main del código del servidor y las clases para comunicarse con el cliente.

### ./artifacts

Tiene ficheros .jar ejecutables por si quieres probar cómo funcionan.

## ¿Dónde están las clases relacionadas con la concurrencia?

### Common

En *src/ucm/erikkarl/common/concurrency* hay 2 clases:

- ReaderWriterController: clase usada para solucionar el problema de los lectores y escritores
  
- MessagesQueue: Una cola de mensajes implementada como un monitor. La documentación de Java no dice nada de que el OutputStream de un Socket pueda ser usado concurrentemente por varios hilos sin problema, por lo que decidí que tendría un único hilo sacando objetos de la cola y mandándolos por el Socket mientras varios hilos iban añadiendo todos los mensajes que quisieran a una cola de mensajes pendientes, y por eso diseñé esta clase.

### Servidor

En *Servidor/src/ucm/erikkarl/server/datosusuarios* están las clases que almacenan los datos de los usuarios. Ambas usan su propio ReaderWriterController.

- MapaConcurrenteUsuarios: "tabla" del servidor con los datos de todos los usuarios registrados desde que empezó a ejecutarse el servidor.

- EntradaUsuario: cada Usuario tiene una de estas entradas, que son las que conforman la "tabla" MapaConcurrenteUsuarios.
