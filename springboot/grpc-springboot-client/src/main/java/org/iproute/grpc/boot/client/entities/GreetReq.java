package org.iproute.grpc.boot.client.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.grpc.multi.GreetRequest;

import java.util.List;

/**
 * GreetReq
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class GreetReq implements GrpcConvertor<GreetRequest> {
    private int id;
    private String name;
    private Gender gender;
    private List<String> emails;

    public enum Gender {
        MAN(0),
        WOMEN(1);

        private final int value;

        Gender(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return this.value;
        }

        @JsonCreator
        public static Gender fromValue(int value) {
            for (Gender color : values()) {
                if (color.value == value) {
                    return color;
                }
            }
            throw new IllegalArgumentException("Invalid gender value: " + value);
        }

    }

    @Override
    public GreetRequest convert() {
        return GreetRequest.newBuilder()
                .setId(this.getId()).setName(this.name)
                .setGenderValue(this.gender.getValue())
                .addAllEmails(this.getEmails())
                .build();
    }
}
