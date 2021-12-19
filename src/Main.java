import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    final static String pathSavegames = "Games/GunRunner/savegames";

    public static void main(String[] args) {
        GameProgress[] games = {
            new GameProgress(88, 25, 34, 2356.1),
            new GameProgress(43, 74, 68, 4932.6),
            new GameProgress(100, 83, 7, 100.3)
        };

        String[] saveFiles = new String[games.length];

        File dirSavegames = new File(pathSavegames);
        dirSavegames.mkdirs();

        for (int i=0; i < games.length; i++) {
            String saveFile = pathSavegames + "/save" + i + ".dat";
            saveGame(saveFile, games[i]);
            saveFiles[i] = saveFile;
        }

        zipFiles(pathSavegames + "/saves.zip", saveFiles);

        for (int i=0; i < saveFiles.length; i++) {
            File saveFile = new File(saveFiles[i]);
            saveFile.delete();
        }
    }

    private static void saveGame(String saveFile, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(saveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String zipFile, String[] saveFiles) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (int i=0; i < saveFiles.length; i++) {
                try (FileInputStream fis = new FileInputStream(saveFiles[i])) {
                    ZipEntry entry = new ZipEntry("save" + i + ".dat");
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
