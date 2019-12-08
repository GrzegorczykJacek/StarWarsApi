import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static OkHttpClient client = new OkHttpClient();
    private static Request request;
    private static Response response;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        int id = 0;
        Scanner scanner = new Scanner(System.in);
        while ((id < 1) || (id > 87)) {
            System.out.printf("Podaj id bohatera Star Wars z przedziału od 1 do 87, żeby zobaczyć filmy w jakich się pojawił: ");
            id = scanner.nextInt();
        }

        String search = String.valueOf(id);
        System.out.println("Podałeś id wyszukiwanego bohatera o numerze: " + search);
        System.out.println("Wybrany przez Ciebie bohater to: \n");

        request = new Request.Builder().url("http://swapi.co/api/people/" + search).build();
        response = client.newCall(request).execute();
        String resp = response.body().string();
//        System.out.println(resp);
        Person p = objectMapper.readValue(resp, Person.class);
        System.out.println(p.toString());

        System.out.println("Ten bohater pojawił się w poniższych filmach: ");

        for(int i = 0; i < p.getFilms().length; i++){
            request = new Request.Builder().url(p.getFilms()[i]).build();
            response = client.newCall(request).execute();
            resp = response.body().string();
            Film f = objectMapper.readValue(resp, Film.class);
            System.out.println(f.toString());
        }

        client.connectionPool().evictAll(); // closes all connections to close the application
    }
}
/*
1. pobierz ID od użytkownika
2. wyswietl soframotwane dane osoby
3. wyswietl sformatowane dane filmow w ktorych osoba grala
code > 199 & code < 300 - sukces */