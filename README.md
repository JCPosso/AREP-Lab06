# AREP - Aplicacion distribuida segura en todos sus frentes
La aplicación consiste en dos servidores, ambos servidores cuentan con un certificado con el fin de implementar la seguridad en aplicaciones web. Un servidor se encarga de realizar la autenticación de los usuarios y mantenerla para que puedan acceder a los servicios.
El otro servidor únicamente retorna un Lorem Ipsum
El cliente solo se comunica con el servidor que tiene sistema de login y este, de manera segura, se conectara con el otro para consultar sus servicios y ofrecérselos al cliente si esta autenticado.

Los servidores se comunican de manera segura a través de protocolos de seguridad como SSL, TSL y HTTPS.

## Conceptos

### SSL
SSL es el acrónimo de Secure Sockets Layer (capa de sockets seguros), la tecnología estándar para mantener segura una conexión a Internet, así como para proteger cualquier información confidencial que se envía entre dos sistemas e impedir que los delincuentes lean y modifiquen cualquier dato que se transfiera, incluida información que pudiera considerarse personal.

### TSL
El protocolo TLS (Transport Layer Security, seguridad de la capa de transporte) es solo una versión actualizada y más segura de SSL.

### HTTPS
HTTPS (Hyper Text Transfer Protocol Secure o protocolo seguro de transferencia de hipertexto) aparece en la dirección URL cuando un sitio web está protegido por un certificado SSL. Los detalles del certificado, por ejemplo la entidad emisora y el nombre corporativo del propietario del sitio web, se pueden ver haciendo clic en el símbolo de candado de la barra del navegador.

## Instalación
Para realizar la instalación de nuestro programa debemos ir a la linea de comandos de nuestro sistema operativo y ejecutar el siguiente comando
````
git clone https://github.com/JCPosso/AREP-Lab06.git
````
## Despliegue

### Localhost

Para poder desplegar la aplicacion en localhost primero se crean las llaves de seguridad , el certificado de llave y el almacenamiento de llaves seguro para que ambos servicios se comuniquen correctamente.
para ello dentro de SecureService:
1. Creamos nuestra llave con el siguiente comando
```
keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048-storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
```
Es importante que al momento en que se pida el nombre del certificado informar que será 'localhost'  que es donde ira  a correr el servicio:
![](/img/localhostkeystore.png)
,
2. generamos el certificado con el siguiente comando:
```
keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
```
3. Generamos el almacenamiento de llaves seguro
```
keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
```
después de haber hecho esto debe aparecer lo que sigue a continuación.
![](/img/localhosttruekey.png)

Una vez hecho estos pasos proveemos la llave, certificado y el almacenamiento de llaves al otro servicio
en este caso InfoService con el finde que SecureService confíe en la autenticidad del nuevo servicio con quien interactúa.

### Compilar
InfoService y SecureService Actúan como módulos dentro de la carpeta principal del proyecto , para 
compilar ambos servicios ejecutamos:
```
mvn clean
mvn package
```
Nos debe aparecer un mensaje como el que sigue a continuacion , lo que nos indica que ya está debidamente compilado y
empaquetado cada uno de los Servicios necesarios del aplicativo
```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for AREP-Lab06 1.0-SNAPSHOT:
[INFO] 
[INFO] InfoService ........................................ SUCCESS [  5.359 s]
[INFO] SecureService ...................................... SUCCESS [  1.940 s]
[INFO] AREP-Lab06 ......................................... SUCCESS [  0.089 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.533 s
[INFO] Finished at: 2021-10-09T15:29:04-05:00
[INFO] ------------------------------------------------------------------------
```

### Despliegue localhost
Para hacer el despliegue correcto nos ubicamos dentro de cada una de las carpetas del aplicativo y ejecutamos los siguientes comandos

Secure Service:
```
java -cp target/classes/:target/dependency/* co.edu.escuelaing.arep.app.SecureService

```

InfoService:

```
java -cp target/classes/:target/dependency/* co.edu.escuelaing.arep.app.InfoService
```

### Navegador
Para revisar que se han ejecutado correctamente ambos comandos probamos en el navegador con la siguiente ruta
```
https://localhost:5000
```

Nos informará que vamos a acceder a un sitio no seguro :

![](/img/localhost-sitionoseguro.png)

Por lo cual damos en > avanzado >continuar con sitio no seguro y tenemos la primera vista de la aplicación.

![](/img/localhost-login.png)
Primero ingresamos nuestras credenciales de acceso en este caso éstas se encuentran en la implementación del modelo en persistencia las cuales son:

```
User name: juancamilo
Password: 123

User name: carlos
Password: 456

User name: jhonnatan
Password: 789
```

Nos aparecerá una ventana emergente que nos indicará que hemos sigo Loggeados:

![](/img/localhost-alert-login.png)

Si lo intentamos con credenciales incorrectas el sitio no nos dejará continuar:

![](/img/localhost-alert-login-wrong.png)

Finalmente obtendremos la ventana del Home:
![](/img/localhost-home.png)

Si realizamos una consulta al otro servidor  pulsando 'get me info' obtendremos un mensaje de  respuesta
como el que sigue a continuación.

![](/img/localhost-info.png)

### Despliegue AWS
![](/video.mkv)



## Documentación

Documentación en [docs](docs).

### Javadoc
Para generar javadoc ejecute los siguientes comandos.

*        mvn javadoc:javadoc
*        mvn javadoc:jar

## Autor
[Juan Camilo Posso Guevara](https://github.com/JCPosso)
## Derechos de Autor
**©** _Juan Camilo Posso G., Escuela Colombiana de Ingeniería Julio Garavito._
## Licencia
Licencia bajo  [GNU General Public License](https://github.com/JCPosso/AREP-Lab06/blob/master/LICENSE).
