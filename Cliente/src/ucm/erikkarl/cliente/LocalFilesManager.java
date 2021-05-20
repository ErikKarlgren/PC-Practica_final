package ucm.erikkarl.cliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class responsible for the management of the local files. Creates a folder named "Files"
 * in the current working directory if it didn't exist already.
 */
public class LocalFilesManager {

    private static final Logger LOGGER = SocketReadyLogger.create(LocalFilesManager.class.getName());

    private static final String FILES_FOLDER_NAME = "/Files";
    private final File filesFolder;

    public LocalFilesManager() {
        filesFolder = new File(System.getProperty("user.dir") + FILES_FOLDER_NAME);

        if (filesFolder.exists() && !filesFolder.isDirectory())
            throw new IllegalStateException("Files folder is not a directory");
        else if (filesFolder.mkdir())
            LOGGER.info("Files folder was created");
        else
            LOGGER.info("Files folder already existed");
    }

    public List<String> getDownloadableFilesNames() throws IOException {
        var files = filesFolder.listFiles(File::isFile);
        if (files == null)
            throw new IOException("Error when reading filenames: I/O error or not a directory");

        return Arrays.stream(files).map(File::getName).collect(Collectors.toList());
    }
}
