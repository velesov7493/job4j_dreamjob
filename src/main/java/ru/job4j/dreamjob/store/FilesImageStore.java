package ru.job4j.dreamjob.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesImageStore implements ImageStore {

    private static final Logger LOG = LoggerFactory.getLogger(FilesImageStore.class.getName());
    private static final String IMAGES_DIR = AppSettings.loadProperties().getProperty("dir.images");
    private static final FilesImageStore INSTANCE = new FilesImageStore();

    private FilesImageStore() {
        File folder = new File(IMAGES_DIR);
        boolean result = folder.exists();
        if (!result) {
            result = folder.mkdir();
        }
        if (!result) {
            String defaultDir = AppSettings.loadProperties().getProperty("dir.images.default");
            folder = new File(defaultDir);
            result = folder.exists();
            if (result) {
                LOG.warn("Ошибка инициализации хранилища картинок пользователя!");
                LOG.warn("Хранение перенаправлено в каталог");
            } else {
                LOG.error("Критическая ошибка инициализации хранилища картинок!");
                LOG.info("Выключаюсь...");
                System.exit(2);
            }
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

    public static FilesImageStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Image load(int id) {
        Image result = new Image(id);
        try (FileInputStream in = new FileInputStream(new File(fileNameById(id)))) {
            result.setContent(in.readAllBytes());
        } catch (Throwable ex) {
            if (id == 0) {
                throw new IllegalStateException(
                        "Изображение отсутствующей фотографии (img000000000.res) не найдено!"
                );
            }
            result = load(0);
        }
        return result;
    }

    @Override
    public void saveFromStream(int id, InputStream stream) {
        try (FileOutputStream out = new FileOutputStream(new File(fileNameById(id)))) {
            out.write(stream.readAllBytes());
        } catch (Throwable ex) {
            LOG.error("Ошибка при записи загруженного изображения: ", ex);
        }
    }

    @Override
    public boolean delete(int id) {
        File nFile = new File(fileNameById(id));
        return nFile.delete();
    }
}