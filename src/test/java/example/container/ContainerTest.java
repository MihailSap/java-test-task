package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Реализация тестов для класса {@link Container}
 */
class ContainerTest {

    /**
     * Контейнер
     * Один объект для всех тестов в классе
     */
    private final Container container = new Container();

    /**
     * Тест корректности добавления
     */
    @Test
    void addTest(){
        Item firstItem = new Item(123L);
        boolean isAddedFirstTime = container.add(firstItem);
        Assertions.assertTrue(isAddedFirstTime);
        Assertions.assertEquals(1, container.size());
        Assertions.assertEquals(firstItem, container.get(0));
        Assertions.assertTrue(container.contains(firstItem));

        Item secondItem = new Item(456L);
        boolean isAddedSecondTime = container.add(secondItem);
        Assertions.assertTrue(isAddedSecondTime);
        Assertions.assertEquals(2, container.size());
        Assertions.assertEquals(secondItem, container.get(1));
        Assertions.assertTrue(container.contains(secondItem));
    }

    /**
     * Тест корректности удаления
     */
    @Test
    void removeTest(){
        Item item = new Item(123L);
        container.add(item);
        boolean isRemoved = container.remove(item);
        Assertions.assertTrue(isRemoved);
        Assertions.assertEquals(0, container.size());
        Assertions.assertFalse(container.contains(item));
    }
}