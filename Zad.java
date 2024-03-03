public class Zad {
    String tytul;
    double Netto;
    int Vat;

    public Zad(String tytul, double Netto, int Vat) {
        this.Netto = Netto;
        this.Vat = Vat;
    }

    public static void main(String[] args) {
        Zad x1 = new Zad("Clean Code, Robert C. Martin", 100, 8);
        Zad x2 = new Zad("Applying UML and Patterns, C. Larman", 300, 8);
        Zad x3 = new Zad("Shipping", 50, 23);

        System.out.println("|               | Total netto |     X     |");
        System.out.println("|---------------|-------------|-----------|");
        System.out.println(
                "|    VAT 8% " + "    |    " + (x1.Netto + x2.Netto) + "    |    "
                        + ((((x1.Vat * 0.01) * x1.Netto)) + ((x2.Vat * 0.01) * x2.Netto)) + "   |");

        System.out.println(
                "|    VAT 23%  " + "  |    " + (x3.Netto) + "     |    " + ((x3.Netto * (x3.Vat * 0.01))) + "   |");

    }
}
