package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Фейковый бот, реализация {@link Bot}</b>
 * <p>Позволяет сохранять сообщения, отправленные {@link BotLogic} и получать их</p>
 */
public class FakeBot implements Bot {

    private final List<String> list = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        list.add(message);
    }

    /**
     * Получение последнего сообщения
     */
    public String getLastMessage(){
        return list.getLast();
    }

    /**
     * Получение предпоследнего сообщения
     */
    public String getNextToLastMessage(){
        return list.get(list.size() - 2);
    }
}
