# Numerador

1. **Construir imagen docker**
    ```
    $ sudo docker build --no-cache -t numerador-egoveris .
    ```
2. **Ejecutar imagen**
La mejor opción es utilizar docker-compose para poder pasar todos los parametros de forma comoda. Un ejemplo seria el siquiente
    ```
    version: '3.1'
    
    
    services:
      numerador-egoveris:
        image: numerador-egoveris:latest
        environment:
          DB_HOST: docker1-host.egoveris.com
          DB_PORT: 3306
          DB_NAME_PREFIX: QA_
          DB_NAME_SUFIX: _TRADE
          DB_USER: temuco
          DB_PASS: temuco
          NOMBRE_NODO: CJB
          CERTIFICADOS: apache-selfsigned.crt
          DEBUG_PORT: 9011
        ports:
          - 8180:8080
          - 9011:9011
        volumes:
          - /home/javier/proyectos/egoveris/ambientes/certificados/docker:/etc/certs
        extra_hosts:
          - "docker-host.egoveris.com:40.87.103.121"
          - "docker1-host.egoveris.com:40.71.27.78"
          - "webdav.egoveris.com:40.71.27.78"
    ```
    Luego para ejecutar la siguiente linea de comandos en el directorio donde se encuentra el  ```docker-compose.yml```.

    ```
    $ sudo docker-compose up -d
    ```

A continuación se explica la función de cada una de las variables de entorno que configuran el modulo

- **DB_HOST** Host de la base de datos
- **DB_PORT** Puerto de la base de datos
- **DB_USER** Usuario de base de datos
- **DB_PASS** Contraseña de base de datos
- **DB_NAME_PREFIX** Prefijo del nombre de la base de datos
- **DB_NAME_SUFIX** Sufijo del nombre de la base de datos
- **NOMBRE_NODO** Si se requiere configurar nombreNodo para las properties se debe configurar aquí
- **CERTIFICADOS** Se deben especificar los nombres de los archivos ```.crt``` de los certificados que se quieran instalar en el cacerts separados por espacios. Si esta variable es configurada se debe montar el directorio que contiene los archivos ```.crt``` en la ruta ```/etc/certs```.
- **DEBUG_PORT** Configura el puerto JMX para debug remoto. El puerto que se especifique aqui será configurado en la ```JAVA_OPTS``` y se podrá conectar desde Eclipse.

Todos los hosts a los que se hagan referencia dentro de la configuracion del modulo o en las properties deben ser mapeados en ```extra_host``` como se ve en ek ejemplo donde se indicar los host del apache, de la base de datos y de webdav.


