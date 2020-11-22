package app;

import javafx.scene.input.DragEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonParser {
    public String getJsonAsString(DragEvent dragEvent) throws IOException {
        List<File> files = dragEvent.getDragboard().getFiles();
        return Files.readString(Paths.get(files.get(0).toString()));
    }

    public JSONObject getJsonAsObject(String jsonAsString) {
        return new JSONObject(jsonAsString);
    }
}
