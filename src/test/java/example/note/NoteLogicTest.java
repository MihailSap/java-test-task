package example.note;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Реализация тестов для класса {@link NoteLogic}
 * Тесты не проходят, так как логика по работе с заметками реализована некорректно
 */
class NoteLogicTest {

    private final NoteLogic noteLogic = new NoteLogic();

    /**
     * Тест проверяет создание и получение двух заметок (/add и /notes)
     * Необходимо тестировать добавление нескольких заметок,
     * так как заглушку для создания одной заметки тоже можно написать,
     * что позволит обойти тест с добавлением одной заметки
     */
    @Test
    void addAndGetNotesTest(){
        noteLogic.handleMessage("/add hello");
        String notes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("Your notes: hello", notes);

        noteLogic.handleMessage("/add world");
        String updatedNotes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("Your notes: hello world", updatedNotes);
    }


    /**
     * Тест на редактирование заметки (/edit)
     * Допустим, после добавления заметки "hello" ей присваивается id=1
     * С помощью этого id можно указать, какую именно заметку нужно отредактировать
     */
    @Test
    void editNoteTest(){
        noteLogic.handleMessage("/add hello");
        noteLogic.handleMessage("/edit 1 goodbye");
        String notes = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("Your notes: goodbye", notes);
    }

    /**
     * Тест на удаление заметки
     * Допустим, после добавления заметки "hello" ей присваивается id=1
     * С помощью этого id можно указать, какую именно заметку нужно удалить
     */
    @Test
    void deleteNoteTest(){
        noteLogic.handleMessage("/add hello");
        String notesAfterAdd = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("Your notes: hello", notesAfterAdd);

        noteLogic.handleMessage("/del 1");
        String notesAfterDel = noteLogic.handleMessage("/notes");
        Assertions.assertEquals("Your notes:", notesAfterDel);
    }
}