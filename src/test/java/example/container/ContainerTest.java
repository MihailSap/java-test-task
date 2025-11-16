package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Реализация тестов для класса {@link Container}
 */
class ContainerTest {

    private Container container;

    /**
     * Явное создание объекта {@link Container} перед каждым тестом
     */
    @BeforeEach
    void setUp(){
        container = new Container();
    }

    /**
     * Тест корректности добавления
     */
    @Test
    void testAddItem(){
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
    void testRemoveItem(){
        Item firstItem = new Item(123L);
        container.add(firstItem);
        Item secondItem = new Item(456L);
        container.add(secondItem);

        boolean isRemoved = container.remove(firstItem);
        Assertions.assertTrue(isRemoved);
        Assertions.assertEquals(1, container.size());
        Assertions.assertFalse(container.contains(firstItem));
        Assertions.assertTrue(container.contains(secondItem));
    }
}