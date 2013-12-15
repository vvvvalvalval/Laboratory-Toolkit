package integer;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;
import integer.algebra.ConstructingChineseIsomorphism;
import integer.algebra.FormingInvertiblesGroup;
import integer.algebra.FormingIsomorphicProductRing;
import integer.algebra.FormingModuloNRing;
import integer.algebra.FormingPrimePowersRings;
import integer.algebra.Isomorphism;
import integer.algebra.ModuloNRing;
import integer.algebra.PrimePowerModuloNRing;
import integer.algebra.ProductElement;
import integer.decimal.RepresentingInNumeralSystem;
import integer.equipment.IntegerEquipment;
import integer.primes.CalculatingPrimeDecomposition;
import integer.primes.CheckingIfPerfectNumber;
import integer.primes.FindingAllDivisors;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        show(15);
        show(66);
        show(8 * 9 * 5 * 169);
        show(2);
        show(28);
    }
    private static final Analysis<List<Integer>, IntegerEquipment> inBase10 = new RepresentingInNumeralSystem(10);
    private static final Analysis<List<Integer>, IntegerEquipment> inBase16 = new RepresentingInNumeralSystem(16);
    private static final Analysis<List<Integer>, IntegerEquipment> inBase8 = new RepresentingInNumeralSystem(8);
    private static final Analysis<List<Integer>, IntegerEquipment> inBase7 = new RepresentingInNumeralSystem(7);
    private static final Analysis<List<Integer>, IntegerEquipment> inBase12 = new RepresentingInNumeralSystem(12);
    private static final Analysis<List<Integer>, IntegerEquipment> inBase51 = new RepresentingInNumeralSystem(51);

    private static void show(int n) {
        System.out.println("----------------");
        Laboratory<IntegerEquipment> lab = new Laboratory<>(new IntegerEquipment(n));

        System.out.println("");
        System.out.println("We're looking at n=" + n);

        System.out.println("");
        System.out.println("n in base 10:");
        System.out.println(inBase10.getResult(lab));
        System.out.println("n in base 16:");
        System.out.println(inBase16.getResult(lab));
        System.out.println("n in base 8:");
        System.out.println(inBase8.getResult(lab));
        System.out.println("n in base 7:");
        System.out.println(inBase7.getResult(lab));
        System.out.println("n in base 12:");
        System.out.println(inBase12.getResult(lab));
        System.out.println("n in base 51:");
        System.out.println(inBase51.getResult(lab));

        System.out.println("");
        System.out.println("Is " + n + " a perfect number?");
        System.out.println("The answer is " + (CheckingIfPerfectNumber.getInstance().getResult(lab) ? "yes" : "no") + ".");

        System.out.println("");
        System.out.println("Prime decomposition");
        System.out.println(CalculatingPrimeDecomposition.getInstance().getResult(lab));

        System.out.println("");
        System.out.println("Here are the positive divisors of " + n + ":");
        System.out.println(FindingAllDivisors.getInstance().getResult(lab));

        System.out.println("");
        ModuloNRing moduloNRing = FormingModuloNRing.getInstance().getResult(lab);
        System.out.println("Let's do some operations in the modulo-" + n + " ring:");
        System.out.println("Additions :");
        for (int i = 0; i < 5; i++) {
            int a = pickAtRandom(moduloNRing.elements());
            int b = pickAtRandom(moduloNRing.elements());
            System.out.println(a + "+" + b + "=" + moduloNRing.plus(a, b));
        }
        System.out.println("Multiplications :");
        for (int i = 0; i < 5; i++) {
            int a = pickAtRandom(moduloNRing.elements());
            int b = pickAtRandom(moduloNRing.elements());
            System.out.println(a + "*" + b + "=" + moduloNRing.times(a, b));
        }
        System.out.println("Opposites :");
        for (int i = 0; i < 5; i++) {
            int a = pickAtRandom(moduloNRing.elements());
            System.out.println("-" + a + "=" + moduloNRing.opposite(a));
        }

        System.out.println("Now, we'll decompose this ring into its Chinese decomposition:");
        Collection<PrimePowerModuloNRing> powerRings = FormingPrimePowersRings.getInstance().getResult(lab).values();
        System.out.print(FormingModuloNRing.getInstance().getResult(lab) + " = ");
        Iterator<PrimePowerModuloNRing> iterator = powerRings.iterator();
        if (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        while (iterator.hasNext()) {
            System.out.print(" x " + iterator.next());
        }
        System.out.println("");
        System.out.println("Some images by the isomorphism");
        for (int i = 0; i < 5; i++) {
            Isomorphism<Integer, ProductElement<Integer>> iso = ConstructingChineseIsomorphism.getInstance().getResult(lab);

            Integer x1 = pickAtRandom(FormingModuloNRing.getInstance().getResult(lab).elements());
            ProductElement<Integer> y1 = iso.image(x1);
            System.out.println(x1 + " -> " + y1);

            ProductElement<Integer> y2 = pickAtRandom(FormingIsomorphicProductRing.getInstance().getResult(lab).elements());
            Integer x2 = iso.antecendent(y2);
            System.out.println(y2 + " -> " + x2);
        }

        System.out.println("");
        System.out.println("Now, let's play with the group of invertibles of Z/" + n + "Z : ");
        System.out.println("Its elements are : ");
        System.out.println(FormingInvertiblesGroup.getInstance().getResult(lab).elements());
        System.out.println("Some multiplications : ");
        for (int i = 0; i < 5; i++) {
            Integer a = pickAtRandom(FormingInvertiblesGroup.getInstance().getResult(lab).elements());
            Integer b = pickAtRandom(FormingInvertiblesGroup.getInstance().getResult(lab).elements());
            Integer product = FormingInvertiblesGroup.getInstance().getResult(lab).plus(a, b);

            System.out.println(a + " * " + b + " = " + product);
        }
        System.out.println("More interestingly, some inversions :");
        for (int i = 0; i < 5; i++) {
            Integer a = pickAtRandom(FormingInvertiblesGroup.getInstance().getResult(lab).elements());
            Integer product = FormingInvertiblesGroup.getInstance().getResult(lab).opposite(a);

            System.out.println("1/" + a + " = " + product);
        }
        System.out.println("And finally, here's a set of generators for the Group of Invertibles of Z/" + n + "Z :");
        System.out.println(FormingInvertiblesGroup.getInstance().getResult(lab).generators());

        System.out.println("----------------");
    }
    private static final Random RANDOM = new Random();

    private static <E> E pickAtRandom(Collection<E> collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int index = RANDOM.nextInt(collection.size());

        Iterator<E> iterator = collection.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}
