# clinica

Pasos a seguir para desplegar la aplicación:

	1. Tener instalado JDK y JRE, asi como configurar las variables de entorno
		correspondientes. JAVA_HOME y JRE_HOME.

	2. Tener instalado un contenedor de aplicaciones web, como Apache Tomcat y
		asignarle permisos de administrador.
			(Solución rapida: copiar la carpeta del Tomcat en el escritorio)

	3. Crear el war de la aplicación. Si dispones de Netbeans, éste te lo generará
		automáticamente y lo almacenará dentro del mismo proyecto en la carpeta
			denominada (dist).

	4. Copiar el war en el directorio:
		<directorioTomcat>/webapps

	5. Iniciar el tomcat:
		<directorioTomcat>/bin/startup.sh

	6. Abrir la aplicación en el navegador:
		localhost:8080/clinica

Por defecto, la conexión esta configurada para:
	Base de Datos: clinica
	Usuario: root
	Pass: 

En caso de que tengas instalado mysql con otras credenciales, deberás modificar
la clave y la contraseña dentro de la APP, en el método conectar() 
que se encuentra en el archivo Services.java. 

Sustituir "root" por nombre de usuario propio y "" por la password de mysql.

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.setConn(DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica?useSSL=false", "root",
                    ""));
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

Aporto junto al proyecto, script con la base de datos completa.
En caso de ejecutar dicho script, debes saber que hay un usuario creado y que podrás
acceder a la aplicación con:
	- usuario: admin
	- contraseña: admin

Durante el proceso de despliegue el servidor podría lanzar una excepción, la cuál será
solucionada cambiando la configuración de mysql:

	mysql > SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
