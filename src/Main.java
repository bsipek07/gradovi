import java.util.*;

public class Main {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);


        Map<String, Set<String>> drzave = new HashMap<>();
        drzave.put("Hrvatska",new TreeSet<>());
        drzave.put("Italija", new TreeSet<>());
        drzave.put("Njemacka",new TreeSet<>());

        while(true){
            System.out.println("\nUnesi ime grada: ");
            String grad = input.nextLine().trim();

            if(grad.equalsIgnoreCase("kraj")){
                break;
            }
            System.out.println("Unesite drzavu kojoj pripada grad");
            String drzava = input.nextLine().trim();

            if(!drzave.containsKey(drzava)){
                System.out.println("Nepoznata drzava");
                continue;
            }
            drzave.get(drzava).add(grad);
        }
        for(String drzava : drzave.keySet()){
            System.out.println(drzava+":");
            for(String grad : drzave.get(drzava)){
                System.out.println(" - "+grad);
            }
        }
        System.out.print("\nUnesi drzavu za prikaz njezinih gradova: ");
        String trazenaDrzava = input.nextLine().trim();

        if(drzave.containsKey(trazenaDrzava)){
            Set<String> gradovi = drzave.get(trazenaDrzava);
            if(gradovi.isEmpty()){
                System.out.println("Nema unesenih gradova");
            }else{
                System.out.println("Gradovi u drzavi"+trazenaDrzava);
                for(String c: gradovi){
                    System.out.println("-"+c);
                }
            }
        }else{
            System.out.println("Nepoznata drzava");
        }

    }
}
