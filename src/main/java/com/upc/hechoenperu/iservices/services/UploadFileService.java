package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.iservices.IUploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService implements IUploadFileService{
    //private final static String UPLOADS_FOLDER = "images";
    private final String UPLOADS_FOLDER;

    public UploadFileService(@Value("${uploads.folder:images}") String uploadsFolder) {
        this.UPLOADS_FOLDER = uploadsFolder;
    }
    @Override
    public Resource load(String filename) throws MalformedURLException{
        Path path = getPath(filename);
        Resource resource = new UrlResource(path.toUri()); // El UrlResource se utiliza para representar un recurso localizado por una URL. En este caso, el recurso es un archivo local en el sistema de archivos.
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Error in path: " + path.toString());
        }
        return resource;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String copy(MultipartFile file) throws IOException {
        // genera un nombre único para el archivo que se va a subir
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // obtiene la ruta del archivo que se va a subir
        Path rootPath = getPath(uniqueFilename);
        // copia el archivo en la ruta especificada
        Files.copy(file.getInputStream(), rootPath);
        // retorna el nombre del archivo que se ha subido
        return uniqueFilename;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String filename) {
        Path imagePath = Paths.get(UPLOADS_FOLDER, filename);
        try {
            // Borra el archivo de la ruta especificada
            Files.delete(imagePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public Path getPath(String filename) {
        // retorna la ruta absoluta del archivo que se va a subir o bajar
        // con get obtenemos la ruta del directorio de carga y con resolve concatenamos el nombre del archivo
        // finalmente con toAbsolutePath obtenemos la ruta absoluta del archivo que incluirá el directorio raiz
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath(); // ejemplo Ruta del archivo: /ruta/a/la/carpeta/de/tu/proyecto/images/ejemplo.jpg
    }*/
    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}