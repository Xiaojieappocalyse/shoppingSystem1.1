import java.io.IOException;
import java.util.List;

public interface DataStorage<T> {
    void save(List<T> data) throws IOException;
    List<T> load() throws IOException;
}

