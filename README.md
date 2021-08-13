# Práctica final de Programación Concurrente (PC) - Curso 2020/2021
Consiste en hacer algo parecido a BitTorrent (supongo).
Hay un servidor con la información de varios usuarios y los nombres de los ficheros que tiene cada uno.
Un usuario puede buscar y conectarse con otro usuario a través del servidor, pero la transferencia de los propios ficheros es por conexión _peer-to-peer_ o P2P entre los usuarios.

**Nota:** no he comprobado si funciona usando 2 ordenadores distintos (uno con el código del cliente y otro con el del servidor), pero sé que funciona bien como
2 procesos distintos en el mismo ordenador.
