import co.csmith.JFlex;
import co.csmith.annotations.CUpdate;

public class Main {

    public static class Wow{
        @CUpdate(value = "cool") String cooli;
        String value;
        int score;
        boolean isCool;
        float f;
        double d;
    }

    public static void main(String[] args) {
        try {
            Wow wow = (Wow) JFlex.fromJson(Wow.class, "{ \"cool\": \"hello\", \"value\":\"myCoolValue\", \"score\":5 , \"f\":0.2 , \"d\":0.62 , \"isCool\": true}");
            System.out.println(wow.cooli);
            System.out.println(wow.value);
            System.out.println(wow.score);
            System.out.println(wow.isCool);
            System.out.println(wow.d);
            System.out.println(wow.f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
