import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private String email;
  private String password;
  private String name;

  public static User getDefault() {
    return new User("loggi197855@t2est.ru", "jhc4738h3g", "Vasya");
  }

  public static User getWithoutEmail() {
    return new User("", "21f34213f", "Vasya");
  }

  public static User getWithoutPassword() {
    return new User("l2ogi012317@t1est.ru", "", "Vasya");
  }

  public static User getWithoutName() {
    return new User("logi0123175@test.ru", "vf3f4356", "");
  }

  public static User getNonExistent() {
    return new User("nonexistent@test.ru", "g3f43t", "Tanya");
  }

  public static User getNew() {
    return new User("new111@mew.ru", "g3f433", "Denis");
  }
}
