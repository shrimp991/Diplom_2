import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private String[] ingredients;

  public void setIngredients(String[] ingredients) {
    this.ingredients = ingredients;
  }

  public static Order getDefault() {
    return new Order(new String[]{"61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa75"});
  }

  public static Order getWithoutIngredients() {
    return new Order(new String[]{});
  }

  public static Order getWithIncorrectHash() {
    return new Order(new String[]{"61c0c5a71d1f82001bdvbn72", "61c0c5a71d1f82001bdvbn75"});
  }
}
