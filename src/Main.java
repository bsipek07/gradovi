import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //1. Kreiranje DataSource objekta i stvaranje konekcije
        DataSource dataSource = createDataSource();


        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Uspješno spojeni na bazu podataka!");

                Scanner scanner = new Scanner(System.in);
                int opcija;

                do {
                    System.out.println("\n=== IZBORNIK ===");
                    System.out.println("1 - Dodaj novu državu");
                    System.out.println("2 - Izmijeni postojeću državu");
                    System.out.println("3 - Obriši postojeću državu");
                    System.out.println("4 - Prikaži sve države");
                    System.out.println("5 - Izlaz");
                    System.out.print("Odaberi opciju: ");
                    opcija = scanner.nextInt();
                    scanner.nextLine(); // potroši novi red

                    switch (opcija) {
                        case 1:
                            dodajDrzavu(connection);
                            break;
                        case 2:
                            izmijeniDrzavu(connection);
                            break;
                        case 3:
                            obrisiDrzavu(connection);
                            break;
                        case 4:
                            prikaziDrzave(connection);
                            break;
                        case 5:
                            System.out.println("Kraj programa.");
                            break;
                        default:
                            System.out.println("Nepoznata opcija. Pokušaj ponovno.");
                    }

                } while (opcija != 5);

            } catch (SQLException e) {
            System.err.println("Greška prilikom spajanja na bazu podataka:");
            e.printStackTrace();
        }
    }

    private static void dodajDrzavu(Connection conn){
        Scanner input = new Scanner(System.in);
        String naziv = input.nextLine();
        System.out.println("Unesi ime drzave:");

        if(naziv.isEmpty()){
            System.out.println("Naziv ne smije biti prazan");
            return;
        }

        String sql = "INSERT INTO Drzava (Naziv) VALUES(?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,naziv);
            int affectedRows = stmt.executeUpdate();

            if(affectedRows>0){
                System.out.println("Drzava je uspjesno dodana");
            }else {
                System.out.println("Dodavanje drzave nije uspjelo");
            }
        }catch (SQLException e){
            System.out.println("Greška prilikom dodavanja države");
        }
    }
    public static void obrisiDrzavu(Connection conn){
        Scanner input = new Scanner(System.in);
        System.out.println("Unesi ID drzave koje zelis obrisati");
        int id = input.nextInt();

        if(id<=3){
            System.out.println("ID ne smije biti manji ili jednak 3");
            return;
        }

        String sql = "DELETE FROM Drzava WHERE IDDrzava = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id);
            int affectedRows = stmt.executeUpdate();

            if(affectedRows>0){
                System.out.println("Drzava je uspjesno obrisana");
            }else {
                System.out.println("brisanje drzave nije uspjelo");
            }
        }catch (SQLException e){
            System.out.println("Greška prilikom brisanja države");
        }
    }public static void izmijeniDrzavu(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Unesite ID države koju želite izmijeniti: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 3) {
            System.out.println("Dozvoljeno je mijenjati samo države s ID > 3.");
            return;
        }

        System.out.print("Unesite novi naziv države: ");
        String noviNaziv = scanner.nextLine().trim();

        if (noviNaziv.isEmpty()) {
            System.out.println("Naziv ne smije biti prazan.");
            return;
        }

        String upit = "UPDATE Drzava SET Naziv = ? WHERE IdDrzava = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(upit)) {
            pstmt.setString(1, noviNaziv);
            pstmt.setInt(2, id);

            int azurirano = pstmt.executeUpdate();

            if (azurirano > 0) {
                System.out.println("Država je uspješno ažurirana.");
            } else {
                System.out.println("Država s ID " + id + " nije pronađena.");
            }

        } catch (SQLException e) {
            System.err.println("Greška prilikom izmjene države:");
            e.printStackTrace();
        }
    }

    public static void prikaziDrzave(Connection conn){
        String sql = "SELECT IDDrzava, Naziv FROM Drzava ORDER BY Naziv ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Popis država ---");
            while (rs.next()) {
                int id = rs.getInt("IdDrzava");
                String naziv = rs.getString("Naziv");
                System.out.printf("ID: %d | Naziv: %s%n", id, naziv);
            }

        } catch (SQLException e) {
            System.err.println("Greška prilikom dohvaćanja država:");
            e.printStackTrace();
        }
    }



    // Metoda za stvaranje DataSource objekta
    private static DataSource createDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(61522);
        dataSource.setDatabaseName("AdventureWorksOBP");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");
        dataSource.setEncrypt(false);
        return dataSource;
    }
}
