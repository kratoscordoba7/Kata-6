package kata.pkg6;

import Model.ISBN;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import static java.util.stream.Collectors.joining;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Kata6 {
    public static void main(String[] args) throws MalformedURLException, IOException, JAXBException {
        ISBN resultPojo = GMethod.init(new URL("https://openlibrary.org/api/books?bibkeys=ISBN:0201558025,LCCN:93005405&format=json"));
        System.out.println(resultPojo);
        JMethod.init(resultPojo);
    }
    
    public class GMethod{
        public static ISBN init(URL url) throws IOException{
            Gson gson = new Gson();
            JsonElement jsonObject = gson.fromJson(read(url), JsonObject.class).getAsJsonObject().get("LCCN:93005405");
            ISBN code = gson.fromJson(jsonObject, ISBN.class);
            return code;
        }

        private static String read(URL url) throws IOException {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return reader.lines().collect(joining());
            }           
        }
    
    } 
    
    public class  JMethod{      
        public static void init(ISBN code) throws IOException, JAXBException{
            JAXBContext context = JAXBContext.newInstance(ISBN.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(code, System.out);
        }
    }  
}
