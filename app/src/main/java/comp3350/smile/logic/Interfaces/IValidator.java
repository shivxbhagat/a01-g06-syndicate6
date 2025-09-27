package comp3350.smile.logic.Interfaces;

public interface IValidator<T> {
    void validate(T object);
    boolean emptyFields(String... field);
}
