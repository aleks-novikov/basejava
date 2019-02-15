package ru.topjava.basejava.util;

import org.junit.Test;
import ru.topjava.basejava.model.AbstractSection;
import ru.topjava.basejava.model.Resume;
import ru.topjava.basejava.model.TextSection;

import static org.junit.Assert.assertEquals;
import static ru.topjava.basejava.util.ResumeTestData.RESUME_1;

public class JsonParserTest {

    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume2);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective 1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        assertEquals(section1, section2);
    }
}