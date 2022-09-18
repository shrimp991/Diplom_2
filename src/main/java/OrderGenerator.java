public class OrderGenerator {
  public static Order getDefault() {
    return new Order(new String[]{"61c0c5a71d1f82001bdaaa72","61c0c5a71d1f82001bdaaa75"});
  }
  public static Order getWithoutIngredients() {
    return new Order(new String[]{});
  }
  public static Order getWithIncorrectHash() {
    return new Order(new String[]{"61c0c5a71d1f82001bdvbn72","61c0c5a71d1f82001bdvbn75"});
  }
}
