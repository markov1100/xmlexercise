package xmlexercise.xmlexercise.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
 import java.time.LocalDateTime;


public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
  
    @Override
    public LocalDateTime unmarshal(String date) throws Exception {

        return LocalDateTime.parse(date);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
