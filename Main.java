public class Main {
    public static void main(String[] args) {
        Partie p = new Partie(2);
        for(int i = 0; i < p.Decks.get(0).size();i++)
        {
            System.out.println(p.Decks.get(0).get(i).toString()+" "+i);
        }

        for(int i = 0; i < p.Decks.get(1).size();i++)
        {
            System.out.println(p.Decks.get(1).get(i).toString()+" "+i);
        }

        System.out.println(p.actuel.toString());

        for(int i = 0; i < p.Mains.get(0).size();i++)
        {
            System.out.println(p.Mains.get(0).get(i).toString()+" "+i);
        }

        for(int i = 0; i < p.Mains.get(1).size();i++)
        {
            System.out.println(p.Mains.get(1).get(i).toString()+" "+i);
        }
    }
}
