package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Реализация тестов для класса {@link BotLogic}
 */
class BotLogicTest {

    /**
     * Id чата по умолчанию
     */
    private static final Long CHAT_ID = 0L;

    private User user;

    private FakeBot bot;

    private BotLogic botLogic;

    /**
     * Явное создание объектов перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        user = new User(CHAT_ID);
        bot = new FakeBot();
        botLogic = new BotLogic(bot);
    }

    /**
     * <b>Тест на команду {@code /test}</b>
     * <p>Проверяет корректность поведения программы при правильных ответах</p>
     */
    @Test
    void testCorrectAnswers(){
        botLogic.processCommand(user, "/start");
        Assertions.assertEquals("Привет!", bot.getLastMessage());

        botLogic.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());

        botLogic.processCommand(user, "100");
        Assertions.assertEquals(
                "Правильный ответ!", bot.getNextToLastMessage());
        Assertions.assertEquals(
                "Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "6");
        Assertions.assertEquals(
                "Правильный ответ!", bot.getNextToLastMessage());
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * <b>Тест на команду {@code /test}</b>
     * <p>Проверяет корректность поведения программы при неправильных ответах</p>
     */
    @Test
    void testIncorrectAnswers(){
        botLogic.processCommand(user, "/start");
        botLogic.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());

        botLogic.processCommand(user, "0");
        Assertions.assertEquals(
                "Вы ошиблись, верный ответ: 100", bot.getNextToLastMessage());
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "0");
        Assertions.assertEquals(
                "Вы ошиблись, верный ответ: 6", bot.getNextToLastMessage());
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * <b>Тест на команду {@code /repeat}</b>
     * <p>Проверяется сохранение вопроса с неправильным ответом</p>
     * <p>Также, проверяется отсутствие сохранения вопроса с правильным ответом</p>
     */
    @Test
    void testRepeatAddOnlyIncorrectAnswer(){
        botLogic.processCommand(user, "/start");
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "0");
        botLogic.processCommand(user, "6");

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());

        botLogic.processCommand(user, "100");
        Assertions.assertEquals(
                "Правильный ответ!", bot.getNextToLastMessage());
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * <b>Тест на команду {@code /repeat}</b>
     * <p>Проверяется отсутствие удаления вопроса с повторным неправильным ответом</p>
     * <p>Также, проверяется удаление вопроса с повторным правильным ответом</p>
     */
    @Test
    void testRepeatRemoveOnlyCorrectAnswer(){
        botLogic.processCommand(user, "/start");
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "0");
        botLogic.processCommand(user, "6");

        botLogic.processCommand(user, "/repeat");
        botLogic.processCommand(user, "0");
        Assertions.assertEquals(
                "Вы ошиблись, верный ответ: 100", bot.getNextToLastMessage());
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());

        botLogic.processCommand(user, "/repeat");
        botLogic.processCommand(user, "100");
        Assertions.assertEquals(
                "Правильный ответ!", bot.getNextToLastMessage());
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }

    /**
     * <b>Тест на команду {@code /notify}</b>
     * <p>Проверяет корректность работы напоминания спустя секунду</p>
     */
    @Test
    void testNotify() throws InterruptedException {
        String notifyText = "Выполнить домашнее задание";

        botLogic.processCommand(user, "/start");
        botLogic.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", bot.getLastMessage());

        botLogic.processCommand(user, notifyText);
        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getLastMessage());

        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Напоминание установлено", bot.getLastMessage());
        Thread.sleep(950L);
        Assertions.assertEquals("Напоминание установлено", bot.getLastMessage());
        Thread.sleep(100L);
        Assertions.assertEquals(
                String.format("Сработало напоминание: '%s'", notifyText), bot.getLastMessage());
    }
}