import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ToysGenerator {
    private ToyList toyList = new ToyList();
    private List<Toy> prizeToys = new ArrayList<>();

    public void add(Toy toy) {
        toyList.add(toy);
    }

    public void generatePrizeToyList(int generations) {
        for (int i = 0; i < generations; i++) {
            prizeToys.add(toyList.generatePrizeToy());
        }
    }

    public boolean hasNextPrizeToy() {
        return !prizeToys.isEmpty();
    }

    public Toy takeNextPrizeToy() {
        if (prizeToys.isEmpty()) {
            throw new NoSuchElementException();
        }
        Toy prizeToy = prizeToys.get(0);
        boolean toyIsOver = !toyList.takeToy(prizeToy);
        prizeToys.remove(0);
        if (toyIsOver) {
            while (prizeToys.contains(prizeToy)) {
                prizeToys.remove(prizeToy);
                if (toyList.size() > 0) {
                    prizeToys.add(toyList.generatePrizeToy());
                }
            }
        }
        return prizeToy;
    }

    public static void main(String[] args) {
        ToysGenerator toysGenerator = new ToysGenerator();
        toysGenerator.add(new Toy(1, "cat", 2, 100));
        toysGenerator.add(new Toy(2, "dog", 5, 1));
        toysGenerator.add(new Toy(3, "horse", 5, 30));
        toysGenerator.add(new Toy(4, "pig", 5, 40));

        toysGenerator.generatePrizeToyList(25);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"))) {
            while (toysGenerator.hasNextPrizeToy()) {
                bw.write(toysGenerator.takeNextPrizeToy().toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}