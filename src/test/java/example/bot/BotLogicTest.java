package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Реализация тестов для класса {@link BotLogic}
 */
class BotLogicTest {

    /**
     * Id чата по умолчанию
     */
    private final Long CHAT_ID = 0L;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private User user;

    private Bot bot;

    private BotLogic botLogic;

    /**
     * Создание объектов перед каждым тестом
     */
    @BeforeEach
    public void setUp() {
        user = new User(CHAT_ID);
        bot = new ConsoleBot();
        botLogic = new BotLogic(bot);
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Проверка команды /start
     * С этого теста проще всего начать
     */
    @Test
    void startCommandTest(){
        botLogic.processCommand(user, "/start");
        Assertions.assertEquals("Привет!\r\n", outputStream.toString());
    }

    /**
     * Тест на команду /test
     * Проверяет корректность поведения программы при правильных ответах
     */
    @Test
    void testCommandCorrectTest(){
        StringBuilder expectedOutput = new StringBuilder();
        String rightAnswer = "Правильный ответ!\r\n";
        botLogic.processCommand(user, "/start");
        expectedOutput.append("Привет!\r\n");

        botLogic.processCommand(user, "/test");
        expectedOutput.append("Вычислите степень: 10^2\r\n");
        Assertions.assertEquals(State.TEST, user.getState());
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());

        botLogic.processCommand(user, "100");
        expectedOutput.append(rightAnswer);
        expectedOutput.append("Сколько будет 2 + 2 * 2\r\n");
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());

        botLogic.processCommand(user, "6");
        expectedOutput.append(rightAnswer);
        expectedOutput.append("Тест завершен\r\n");
        Assertions.assertEquals(State.INIT, user.getState());
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());
    }

    /**
     * Тест на команду /test
     * Проверяет корректность поведения программы при неправильных ответах
     */
    @Test
    void testCommandIncorrectTest(){
        StringBuilder expectedOutput = new StringBuilder();
        String incorrectAnswer = "Вы ошиблись, верный ответ";
        botLogic.processCommand(user, "/start");
        expectedOutput.append("Привет!\r\n");

        botLogic.processCommand(user, "/test");
        expectedOutput.append("Вычислите степень: 10^2\r\n");

        botLogic.processCommand(user, "0");
        expectedOutput.append(String.format("%s: 100\r\n", incorrectAnswer));
        expectedOutput.append("Сколько будет 2 + 2 * 2\r\n");
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());

        botLogic.processCommand(user, "0");
        expectedOutput.append(String.format("%s: 6\r\n", incorrectAnswer));
        expectedOutput.append("Тест завершен\r\n");
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());
    }

    /**
     * Тест на команду /repeat
     * Проверяет корректность повторения вопросов,
     * ответ на которые изначально был дан некорректно
     */
    @Test
    void repeatCommandAfterIncorrectTest(){
        StringBuilder expectedOutput = new StringBuilder();
        botLogic.processCommand(user, "/start");
        expectedOutput.append("Привет!\r\n");

        botLogic.processCommand(user, "/test");
        expectedOutput.append("Вычислите степень: 10^2\r\n");

        botLogic.processCommand(user, "100");
        expectedOutput.append("Правильный ответ!\r\n");
        expectedOutput.append("Сколько будет 2 + 2 * 2\r\n");

        botLogic.processCommand(user, "0");
        expectedOutput.append("Вы ошиблись, верный ответ: 6\r\n");
        expectedOutput.append("Тест завершен\r\n");

        botLogic.processCommand(user, "/repeat");
        expectedOutput.append("Сколько будет 2 + 2 * 2\r\n");
        botLogic.processCommand(user, "6");
        expectedOutput.append("Правильный ответ!\r\n");
        expectedOutput.append("Тест завершен\r\n");
        Assertions.assertEquals(expectedOutput.toString(), outputStream.toString());
    }
}