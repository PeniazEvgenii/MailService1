package by.it_academy.jd2.entity;

import java.util.Arrays;
import java.util.Optional;

public enum EStatus {
    LOADED,
    SEND,
    FINISH,
    ERROR;

    public static Optional<EStatus> getStatus(String status) {
        return Arrays.stream(EStatus.values())
                .filter(st -> st.name().equalsIgnoreCase(status))
                .findFirst();
    }

}
