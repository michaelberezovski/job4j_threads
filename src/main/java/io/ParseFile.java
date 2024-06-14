package io;

import java.io.*;

public class ParseFile {
    private final File file;
    private final InputStream input;

    public ParseFile(File file, InputStream input) {
        this.file = file;
        this.input = input;
    }
}


//- Избавиться от get set за счет передачи File в конструктор.
//
//- Ошибки в многопоточности. Сделать класс Immutable. Все поля final.
//
//- Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.
//
//- Нарушен принцип единой ответственности. Тут нужно сделать два класса.
//
//- Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)