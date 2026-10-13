import com.example.jpa.entities.Reservation;
import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;
import com.example.jpa.service.ServiceGestion;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

// Point d'entree de demo : insere quelques donnees puis les affiche,
// tout en laissant la console H2 ouverte pour les inspecter visuellement.
public class Main {
    public static void main(String[] args) throws SQLException {
        Server h2Console = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        System.out.println("Console H2 : " + h2Console.getURL());
        System.out.println("  JDBC URL  : jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.out.println("  Utilisateur : sa   Mot de passe : (vide)");

        ServiceGestion service = new ServiceGestion();

        try {
            Salle salleA = new Salle("Salle A", 10);
            Salle salleB = new Salle("Salle B", 25);
            service.ajouterSalle(salleA);
            service.ajouterSalle(salleB);

            service.ajouterUtilisateur(new Utilisateur("Alice", "alice@example.com", salleA));
            service.ajouterUtilisateur(new Utilisateur("Bob", "bob@example.com", salleB));

            System.out.println("\nSalles enregistrees :");
            List<Salle> salles = service.listerSalles();
            salles.forEach(System.out::println);

            System.out.println("\nUtilisateurs enregistres :");
            List<Utilisateur> utilisateurs = service.listerUtilisateurs();
            utilisateurs.forEach(System.out::println);

            System.out.println("\nUtilisateurs de la Salle B :");
            service.utilisateursParSalle("Salle B").forEach(System.out::println);

            Reservation reservation = service.reserver(
                    LocalDateTime.of(2026, 10, 20, 9, 0),
                    LocalDateTime.of(2026, 10, 20, 10, 0),
                    "Reunion equipe",
                    5,
                    salleA,
                    utilisateurs.get(0)
            );
            System.out.println("\nReservation creee :");
            System.out.println(reservation);

            System.out.println("\nReservations de la Salle A :");
            service.reservationsParSalle("Salle A").forEach(System.out::println);

            System.out.println("\nAppuyez sur Entree pour fermer l'application et la console H2...");
            new Scanner(System.in).nextLine();
        } finally {
            service.fermer();
            h2Console.stop();
        }
    }
}
