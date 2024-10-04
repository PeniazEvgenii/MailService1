package by.it_academy.jd2.controller.servlet.delete;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String fio;

    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
   // @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;


}

class tt {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{ \"username\" :\"Petrovich\", \"password\" :\"1234\", \"fio\" :\"Ivan\", \"date\" : { \"year\" :1991, \"month\" :7, \"day\" :31 } }";

        ObjectMapper mapper = new ObjectMapper();
        UserDto user = mapper.readValue(json, UserDto.class);

        System.out.println(user.getDate()); // Output: 1991-07-31
    }
}
