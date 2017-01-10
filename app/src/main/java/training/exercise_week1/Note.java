package training.exercise_week1;

import java.io.Serializable;

public class Note implements Serializable{
    private String subject;
    private String content;

    public Note(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return subject;
    }

    public String toEntireString() {
        return "Subject= " + subject + "/nContent= " + content + "/n";
    }


}
