# Proyecto: Hecho en Perú

## Enlace a la prueba con Postman: https://app.getpostman.com/join-team?invite_code=73eb172dde3153efa0a8d75dfd79c150&target_code=fb9b4274b6fe3dc7e5cebf36ed30b331

## Información importante:
* Se necesita crear la base de datos hechoenperu2 o cambiar el nombre en el application.properties (usar postgres).
* Se necesita modificar el password en el application.properties.
* Se necesita insertar en la tabla roles los datos ('USER', 'ADMIN'), query para copiar y pegar:
    INSERT INTO roles (name_role) VALUES ('ADMIN'), ('USER');
* Se recomienda borrar todas las imagenes que se encuentran en la carpeta images.
* Si hay problemas con alguna petición http visualizar la consola del IntelliJ.
* Obligatorio: Registrar un usuario de tipo ADMIN directamente en la base de datos o crearlo desde postman y modificar su relación de la tabla users_roles; 
