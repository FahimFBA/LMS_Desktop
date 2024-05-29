package com.lms.lmsdesktop.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileStorageAdmin {
    private static FileStorageAdmin instance;
    private String path;

    private FileStorageAdmin(String path) {
        this.path = path;
    }

    public static synchronized FileStorageAdmin getInstance(String path) {
        if (instance == null) {
            instance = new FileStorageAdmin(path);
        }
        return instance;
    }

    private static final String STORAGE_DIRECTORY = "com.lms.lmsdesktop.admin.FileStorage";

    public void storeFile(String fileName, Path source) throws IOException {
        Path destination = resolve(fileName);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public List<File> listFiles() {
        File directory = new File(STORAGE_DIRECTORY);
        File[] files = directory.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        } else {
            return new ArrayList<>();
        }
    }

    public static FileStorageAdmin createFileStorageAdmin(String storageDirectoryName) {
        return new FileStorageAdmin(storageDirectoryName);
    }

    public Path resolve(String fileName) {
        return Paths.get(STORAGE_DIRECTORY).resolve(fileName);
    }
    public void delete(String fileName) throws IOException {
        Path path = resolve(fileName);
        Files.delete(path);
    }

}
