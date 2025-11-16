package example.note;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <b>Реализация тестов для класса {@link NoteLogic}.</b>
 * Тесты не проходят, так как логика по работе с заметками реализована некорректно
 */
class NoteLogicTest {

    private NoteLogic noteLogic;

    /**
     * Явное создание объекта {@link NoteLogic} перед каждым тестом
     */
    @BeforeEach
    void setUp(){
        noteLogic = new NoteLogic();
    }

    /**
     * <b>Тест на создание и получение двух заметок - {@code /add} и {@code /notes}</b>
     * <p>Необходимо тестировать добавление нескольких заметок,
     * так как заглушку для создания одной заметки тоже можно написать,
     * что позволит обойти тест с добавлением одной заметки</p>
     */
    @Test
    void testAddAndGetNotes(){
        Assertions.assertEquals("Note added!", noteLogic.handleMessage("/add hello"));
        Assertions.assertEquals("""
                Your notes:
                1. hello
                """, noteLogic.handleMessage("/notes"));
        noteLogic.handleMessage("/add world");
        Assertions.assertEquals("""
                Your notes:
                1. hello
                2. world
                """, noteLogic.handleMessage("/notes"));
    }


    /**
     * <b>Тест на редактирование заметки - {@code /edit}</b>
     * <p>Допустим, после добавления заметки "hello" ей присваивается id=1
     * С помощью этого id можно указать, какую именно заметку нужно отредактировать</p>
     */
    @Test
    void testEditNote(){
        noteLogic.handleMessage("/add hello");
        Assertions.assertEquals("Note edited!", noteLogic.handleMessage("/edit 1. world"));
        Assertions.assertEquals("""
                Your notes:
                1. world
                """, noteLogic.handleMessage("/notes"));
    }

    /**
     * <b>Тест на удаление заметки - {@code /delete}</b>
     * <p>Допустим, после добавления заметки "hello" ей присваивается id=1
     * С помощью этого id можно указать, какую именно заметку нужно удалить</p>
     */
    @Test
    void testDeleteNote(){
        noteLogic.handleMessage("/add hello");
        noteLogic.handleMessage("/add world");
        Assertions.assertEquals("Note deleted!", noteLogic.handleMessage("/delete 1"));
        Assertions.assertEquals("""
                Your notes:
                2. world
                """, noteLogic.handleMessage("/notes"));
    }
}