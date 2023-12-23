import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Senha de acesso:");
        String vote = scanner.nextLine();
        Voting voting = new Voting(vote);
        voting.initiaze();
    }
}