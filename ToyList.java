import java.lang.reflect.Type;
import java.util.*;

public class ToyList {
    private List<Toy> toys = new ArrayList<>();
    private int frequencyAmount = 0;

    public void add(Toy toy) {
        if (toy.getFrequency() < 1 || toy.getFrequency() > 100 || toy.getQuantity() < 1) {
            throw new IllegalArgumentException("Частота выпадеия игрушки должна быть от 1 до 100 и количество больше 1");
        }
        if (toys.contains(toy)) {
            updateToy(toy);
        } else {
            toys.add(toy);
            this.frequencyAmount += toy.getFrequency();
        }
    }

    public void setToyFrequencyById(long id, int frequency) {
        Toy toy = getToy(id);
        this.frequencyAmount += frequency - toy.getFrequency();
        getToy(id).setFrequency(frequency);
    }

    public Toy getToy(long id) {
        Iterator<Toy> iterator = toys.iterator();
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getId() == id) {
                return toy;
            }
        }
        throw new IllegalArgumentException("Нет такого id = " + id);
    }

    public List<Toy> getToys() {
        return toys;
    }

    private void updateToy(Toy toy) {
        Iterator<Toy> iterator = toys.iterator();
        while (iterator.hasNext()) {
            Toy updatedToy = iterator.next();
            if (updatedToy.equals(toy)) {
                updatedToy.setName(toy.getName());
                this.frequencyAmount += toy.getFrequency() - updatedToy.getFrequency();
                updatedToy.setFrequency(toy.getFrequency());
                updatedToy.setQuantity(toy.getQuantity() + updatedToy.getQuantity());
            }
        }
    }

    public Toy generatePrizeToy() {
        int shot = new Random().nextInt(this.frequencyAmount + 1);
        int counter = 0;
        for (Toy toy : toys) {
            counter += toy.getFrequency();
            if (shot <= counter) {
                return toy;
            }
        }
        throw new ArithmeticException("Внутренняя ошибка: что-то не так при калькуляции суммарной частоты в классе " + shot + " " + counter);
    }

    public boolean takeToy(Toy toy) {
        if (!toys.contains(toy)) {
            throw new IllegalArgumentException("Нет такой игрушки");
        }
        Toy takenToy = getToy(toy.getId());
        if (takenToy.getQuantity() == 1) {
            this.frequencyAmount -= takenToy.getFrequency();
            toys.remove(toys.indexOf(takenToy));
            return false;
        }
        takenToy.setQuantity(takenToy.getQuantity() - 1);
        return true;
    }

    public int size() {
        return toys.size();
    }
}
