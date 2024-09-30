import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import entities.Product;

public class Main {
    public static void main(String[] args) {

        String path = "in.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            List<Product> list = new ArrayList<>();
            List<String> names = new ArrayList<>();

            while (line != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                Double price = Double.parseDouble(fields[1]);
                Product p = new Product(name, price);
                list.add(p);
                line = br.readLine();
            }

            Double avg = list.stream().map(x -> x.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();

            System.out.println("Preço médio: " + String.format("%.2f", avg));

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            names = list.stream()
                    .filter(x -> x.getPrice() < avg)
                    .map(x -> x.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());
            System.out.println("Produtos com preço abaixo da média: ");
            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}