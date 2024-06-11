import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Country> countries = readCountriesFromFile("world.csv");
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Vyberte operaci:");
            System.out.println("1. Vypsat celkovou populaci a rozlohu zadaného kontinentu");
            System.out.println("2. Vypsat názvy zemí s populací nad 180 milionů");
            System.out.println("3. Vypsat průměrné hdp na člověka pro země s hdp větším než 1 bilion");
            System.out.println("4. Konec programu");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    printTotalPopulationAndAreaByContinent(countries, scanner);
                    break;
                case 2:
                    printCountriesWithPopulationOver180M(countries);
                    break;
                case 3:
                    printAverageGdpPerCapitaForHighGdpCountries(countries);
                    break;
                case 4:
                    System.out.println("Konec programu.");
                    break;
                default:
                    System.out.println("Neplatná volba. Zkuste to znovu.");
            }
        } while (choice != 4);

        scanner.close();
    }
    private static List<Country> readCountriesFromFile(String filename) {
        List<Country> countries = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String continent = parts[1];
                int area = Integer.parseInt(parts[2]);
                long population = Long.parseLong(parts[3]);
                long gdp = Long.parseLong(parts[4]);
                countries.add(new Country(name, continent, area, population, gdp));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return countries;
    }
    private static void printCountriesWithPopulationOver180M(List<Country> countries) {
        System.out.println("Země s populací nad 180 milionů:");
        for (Country country : countries) {
            if (country.getPopulation() > 180_000_000) {
                System.out.println(country.getName());
            }
        }
    }
    private static void printTotalPopulationAndAreaByContinent(List<Country> countries, Scanner scanner) {
        System.out.println("Zadejte kontinent:");
        String continent = scanner.nextLine();
        long totalPopulation = 0;
        int totalArea = 0;
        for (Country country : countries) {
            if (country.getContinent().equalsIgnoreCase(continent)) {
                totalPopulation += country.getPopulation();
                totalArea += country.getArea();
            }
        }
        System.out.println("Celková populace kontinentu " + continent + ": " + totalPopulation);
        System.out.println("Celková rozloha kontinentu " + continent + ": " + totalArea);
    }
    private static void printAverageGdpPerCapitaForHighGdpCountries(List<Country> countries) {
        long totalGdp = 0;
        long totalPopulation = 0;
        for (Country country : countries) {
            if (country.getGdp() > 1_000_000_000_000L) {
                totalGdp += country.getGdp();
                totalPopulation += country.getPopulation();
            }
        }
        if (totalPopulation > 0) {
            double averageGdpPerCapita = (double) totalGdp / totalPopulation;
            System.out.println("Průměrné HDP na osobu pro země s HDP větším než 1 bilion: " + averageGdpPerCapita);
        } else {
            System.out.println("Žádné země s HDP větším než 1 bilion nebyly nalezeny.");
        }
    }
    public static class Country {
        private String name;
        private String continent;
        private int area;
        private long population;
        private long gdp;
        public Country(String name, String continent, int area, long population, long gdp) {
            this.name = name;
            this.continent = continent;
            this.area = area;
            this.population = population;
            this.gdp = gdp;
        }
        public String getName() {
            return name;
        }
        public String getContinent() {
            return continent;
        }
        public int getArea() {
            return area;
        }
        public long getPopulation() {
            return population;
        }
        public long getGdp() {
            return gdp;
        }
    }
}