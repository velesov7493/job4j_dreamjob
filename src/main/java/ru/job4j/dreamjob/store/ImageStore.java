package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageStore {

    private static final String IMAGES_DIR = "/media/data/sources/job4j/web-images";
    private static final ImageStore INSTANCE = new ImageStore();

    private ImageStore() {
        File folder = new File(IMAGES_DIR);
        boolean result = folder.exists();
        if (!result) {
            result = folder.mkdir();
        }
        if (!result) {
            System.out.println("Ошибка инициализации хранилища картинок!");
            System.out.println("Выключаюсь...");
            System.exit(2);
        }
    }

    private static List<String> getNames() {
        File imgDir = new File(IMAGES_DIR);
        List<String> names = new ArrayList<>();
        for (File f : Objects.requireNonNull(imgDir.listFiles())) {
            names.add(f.getName());
        }
        return names;
    }

    private static String fileNameById(int imageId) {
        StringBuilder number = new StringBuilder(String.valueOf(imageId));
        int nc = 9 - number.length();
        for (int i = 0; i < nc; i++) {
            number.insert(0, "0");
        }
        return IMAGES_DIR + "/img" + number.toString() + ".res";
    }

    public static ImageStore getInstance() {
        return INSTANCE;
    }

    public Image load(int id) {
        Image result = new Image(id);
        try (FileInputStream in = new FileInputStream(new File(fileNameById(id)))) {
            result.setContent(in.readAllBytes());
        } catch (Throwable ex) {
            result = load(0);
        }
        return result;
    }

    public void saveFromStream(int id, InputStream stream) {
        try (FileOutputStream out = new FileOutputStream(new File(fileNameById(id)))) {
            out.write(stream.readAllBytes());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public boolean delete(int id) {
        File nFile = new File(fileNameById(id));
        return nFile.delete();
    }
}
